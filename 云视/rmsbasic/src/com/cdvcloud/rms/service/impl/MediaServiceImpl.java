package com.cdvcloud.rms.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.common.QueryOperators;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.common.vo.MediaVo;
import com.cdvcloud.rms.dao.IMediaDao;
import com.cdvcloud.rms.dao.ITaskDao;
import com.cdvcloud.rms.domain.HistoricalTask;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.domain.Task;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IHistoricalTaskService;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.service.IScreenShotService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.ITranscodeService;
import com.cdvcloud.rms.service.IUserService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.FileUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.LogUtil;
import com.cdvcloud.rms.util.MD5Util;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;
import com.cdvcloud.rms.util.UserUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

@Service
public class MediaServiceImpl extends BasicService implements IMediaService {
	private static final Logger logger = Logger.getLogger(MediaServiceImpl.class);
	@Autowired
	private ITranscodeService transcodeService;
	@Autowired
	private IScreenShotService screenShotService;
	@Autowired
	private IMediaDao mediaDao;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IHistoricalTaskService historicalTaskService;
	@Autowired
	private ITaskDao taskDao;
	@Override
	public ResponseObject findOne(CommonParameters queryById, String strJson) {
		Map<String, Object> json = JsonUtil.readJSON2Map(strJson);
		if (!StringUtil.isEmpty(json.get(MediaVo.ID))) {
			Map<String, Object> backMap = new HashMap<String, Object>();
			Map<String, Object> media = mediaDao.queryOne(String.valueOf(json.get(MediaVo.ID)));
			backMap.put("result", media);
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, media);
		} else {
			return new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject findList(CommonParameters query, String json) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(json);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(query, mapJson, Media.NAME, Media.CTIME);
		// 记录日志查询类型
		StringBuffer log_mtype = new StringBuffer();
		// id
		String mediaIds = String.valueOf(mapJson.get("ids"));
		if (null != mediaIds && !"null".equals(mediaIds) && !"".equals(mediaIds)) {
			String[] materialIdArray = mediaIds.split(",");
			BasicDBList basicDBList = new BasicDBList();
			for (String materialId : materialIdArray) {
				basicDBList.add(new ObjectId(materialId));
			}
			whereMap.put(Media.ID, new BasicDBObject("$in", basicDBList));
		}
		// 循环查询类型条件
		if (null != mapJson.get(MediaVo.MTYPE) && !"".equals(String.valueOf(mapJson.get(MediaVo.MTYPE)))) {
			List<String> mtype = (List<String>) mapJson.get(MediaVo.MTYPE);
			if (null != mtype && !"".equals(mtype)) {
				BasicDBList basicDBList = new BasicDBList();
				for (String mtypein : mtype) {
					if (!"all".equals(mtypein)) {
						basicDBList.add(mtypein);
						if ("0".equals(mtypein)) {
							log_mtype.append("视频");
						}
						if ("1".equals(mtypein)) {
							log_mtype.append("音频");
						}
						if ("2".equals(mtypein)) {
							log_mtype.append("图片");
						}
						if ("3".equals(mtypein)) {
							log_mtype.append("文件");
						}
					} else {
						log_mtype.append("全部");
						basicDBList.clear();
						break;
					}
				}
				if (basicDBList.size() > 0) {
					whereMap.put(Media.MTYPE, new BasicDBObject("$in", basicDBList));
				}
			}
		} else {
			log_mtype.append("全部");
		}
		// 来源
		if (!StringUtil.isEmpty(mapJson.get(MediaVo.SRC))) {
			whereMap.put(Media.SRC, String.valueOf(mapJson.get(MediaVo.SRC)));
		}
		// 状态
		if (!StringUtil.isEmpty(mapJson.get(MediaVo.STATUS))) {
			whereMap.put(Media.STATUS, Integer.valueOf(String.valueOf(mapJson.get(MediaVo.STATUS))));
		}
		// else{
		// BasicDBList basicDBList = new BasicDBList();
		// basicDBList.add(GeneralStatus.failure.status);
		// whereMap.put(Media.STATUS, new BasicDBObject(QueryOperators.NIN,
		// basicDBList));
		// }
		whereMap.put(Media.ISDEL, Constants.ZERO);
		// 共享标识
		if (!StringUtil.isEmpty(mapJson.get(MediaVo.SHARE))) {
			whereMap.put(Media.SHARE, Integer.valueOf(String.valueOf(mapJson.get(MediaVo.SHARE))));
		}
		// 回收站
		if (!StringUtil.isEmpty(mapJson.get(MediaVo.RECYCLE))) {
			whereMap.put(Media.RECYCLE, Integer.valueOf(String.valueOf(mapJson.get(MediaVo.RECYCLE))));
		}

		// 排序参数
		Map<String, Object> sortFilter = getSortParam(Media.UUTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapJson);
		int pageNum = getPageNum(mapJson);
		Long total = mediaDao.count(whereMap);
		List<Map<String, Object>> lists = null;
		String appCode = query.getAppCode();
		if (appCode.equals(Constants.APPCODEPHONE)) {// 区分手机端和pc端
			Map<String, Object> mapBack = getMediaMapPhone();
			lists = mediaDao.query(sortFilter, whereMap, mapBack, currentPage, pageNum);
		} else {
			lists = mediaDao.query(sortFilter, whereMap, currentPage, pageNum);
		}
		// 保存日志
		// systemLogService.inset(SystemLogUtil.getMap(query, "0", "查询",
		// "查询《"+log_mtype+"》"));
		if (null != lists && lists.size() >= 0) {
			Pages pages = new Pages(pageNum, total, currentPage, lists);
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, pages);
		} else {
			return new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject registerResource(CommonParameters insert, String strJson, String ytpe) {

		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		// boolean bool = false;
		List<String> ids = new ArrayList<String>();
		if (null != mapJson.get("videos")) {
			List<Map<String, Object>> videos = (List<Map<String, Object>>) mapJson.get("videos");
			for (Map<String, Object> map : videos) {
				// 获取路径
				String fileDepositUrl = String.valueOf(map.get("url"));
				// 获取文件名
				String fileName = String.valueOf(map.get("name"));
				// 获取上传文件类型
				String type = FileUtil.getMaterialType(fileDepositUrl);
				// ---------------获取md5-----------------------
				String isMd5 = configurationService.getIsMd5();
				String md5 = "";
				if (null != isMd5 && "0".equals(isMd5)) {
					File md5File = new File(fileDepositUrl);
					md5 = MD5Util.getFileMD5String(md5File);
				}
				// ---------------获取md5-----------------------
				String mtype = FileUtil.getMtype(type);
				// -------------记录历史任务--------------
				Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
				historicalTaskMap.put(HistoricalTask.APPCODE, insert.getAppCode());
				historicalTaskMap.put(HistoricalTask.COMPANYID, insert.getCompanyId());
				historicalTaskMap.put(HistoricalTask.VERSIONID, insert.getVersionId());
				historicalTaskMap.put(HistoricalTask.USERID, insert.getUserId());
				historicalTaskMap.put(HistoricalTask.SERVICECODE, insert.getServiceCode());
				// historicalTaskMap.put(HistoricalTask.CONSUMERID,
				// productCode);
				historicalTaskMap.put(HistoricalTask.FILENAME, fileName);
				// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
				historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
				historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
				historicalTaskMap.put(HistoricalTask.STATUS, "2");
				historicalTaskMap.put(HistoricalTask.TYPE, type);
				historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
				historicalTaskMap.put(HistoricalTask.SRC, "接口上传：registerResource");
				String taskid = historicalTaskService.inset(historicalTaskMap);
				logger.info("接口上传：registerResource历史任务记录数据插入返回id=" + taskid);
				// -------------记录历史任务--------------
				// 视音频流程
				Map<String, Object> transcodeMap = new HashMap<String, Object>();
				transcodeMap.put("common", insert);
				transcodeMap.put("fileName", fileName);
				transcodeMap.put("mtype", mtype);
				transcodeMap.put("fileDepositUrl", fileDepositUrl);
				transcodeMap.put("taskid", taskid);
				transcodeMap.put("md5", md5);
				transcodeMap.put("src", ytpe);
				ids.add(transcodeService.addTranscodeAutomatic(transcodeMap));
			}
		}
		if (null != mapJson.get("audios")) {
			List<Map<String, Object>> audios = (List<Map<String, Object>>) mapJson.get("audios");
			for (Map<String, Object> map : audios) {
				// 获取路径
				String fileDepositUrl = String.valueOf(map.get("url"));
				// 获取文件名
				String fileName = String.valueOf(map.get("name"));
				// 获取上传文件类型
				String type = FileUtil.getMaterialType(fileDepositUrl);
				// ---------------获取md5-----------------------
				String isMd5 = configurationService.getIsMd5();
				String md5 = "";
				if (null != isMd5 && "0".equals(isMd5)) {
					File md5File = new File(fileDepositUrl);
					md5 = MD5Util.getFileMD5String(md5File);
				}
				// ---------------获取md5-----------------------
				String mtype = FileUtil.getMtype(type);
				// -------------记录历史任务--------------
				Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
				historicalTaskMap.put(HistoricalTask.APPCODE, insert.getAppCode());
				historicalTaskMap.put(HistoricalTask.COMPANYID, insert.getCompanyId());
				historicalTaskMap.put(HistoricalTask.VERSIONID, insert.getVersionId());
				historicalTaskMap.put(HistoricalTask.USERID, insert.getUserId());
				historicalTaskMap.put(HistoricalTask.SERVICECODE, insert.getServiceCode());
				// historicalTaskMap.put(HistoricalTask.CONSUMERID,
				// productCode);
				historicalTaskMap.put(HistoricalTask.FILENAME, fileName);
				// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
				historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
				historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
				historicalTaskMap.put(HistoricalTask.STATUS, "2");
				historicalTaskMap.put(HistoricalTask.TYPE, type);
				historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
				historicalTaskMap.put(HistoricalTask.SRC, "接口上传：registerResource");
				String taskid = historicalTaskService.inset(historicalTaskMap);
				logger.info("接口上传：registerResource历史任务记录数据插入返回id=" + taskid);
				// -------------记录历史任务--------------
				// 视音频流程
				Map<String, Object> transcodeMap = new HashMap<String, Object>();
				transcodeMap.put("common", insert);
				transcodeMap.put("fileName", fileName);
				transcodeMap.put("mtype", mtype);
				transcodeMap.put("fileDepositUrl", fileDepositUrl);
				transcodeMap.put("taskid", taskid);
				transcodeMap.put("md5", md5);
				transcodeMap.put("src", ytpe);
				ids.add(transcodeService.addTranscodeAutomatic(transcodeMap));
			}
		}
		if (null != mapJson.get("images")) {
			List<Map<String, Object>> images = (List<Map<String, Object>>) mapJson.get("images");
			for (Map<String, Object> map : images) {
				// 获取路径
				String fileDepositUrl = String.valueOf(map.get("url"));
				// 获取文件名
				String fileName = String.valueOf(map.get("name"));
				// 获取上传文件类型
				String type = FileUtil.getMaterialType(fileDepositUrl);
				// ---------------获取md5-----------------------
				String isMd5 = configurationService.getIsMd5();
				String md5 = "";
				if (null != isMd5 && "0".equals(isMd5)) {
					File md5File = new File(fileDepositUrl);
					md5 = MD5Util.getFileMD5String(md5File);
				}
				// ---------------获取md5-----------------------
				String mtype = FileUtil.getMtype(type);
				// -------------记录历史任务--------------
				Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
				historicalTaskMap.put(HistoricalTask.APPCODE, insert.getAppCode());
				historicalTaskMap.put(HistoricalTask.COMPANYID, insert.getCompanyId());
				historicalTaskMap.put(HistoricalTask.VERSIONID, insert.getVersionId());
				historicalTaskMap.put(HistoricalTask.USERID, insert.getUserId());
				historicalTaskMap.put(HistoricalTask.SERVICECODE, insert.getServiceCode());
				// historicalTaskMap.put(HistoricalTask.CONSUMERID,
				// productCode);
				historicalTaskMap.put(HistoricalTask.FILENAME, fileName);
				// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
				historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
				historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
				historicalTaskMap.put(HistoricalTask.STATUS, "2");
				historicalTaskMap.put(HistoricalTask.TYPE, type);
				historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
				historicalTaskMap.put(HistoricalTask.SRC, "接口上传：registerResource");
				String taskid = historicalTaskService.inset(historicalTaskMap);
				logger.info("接口上传：registerResource历史任务记录数据插入返回id=" + taskid);
				// -------------记录历史任务--------------
				// 图片流程
				ids.add(screenShotService.addScreenShotAutomatic(insert, fileName, mtype, fileDepositUrl, ytpe, null, taskid, md5));
			}
		}
		if (null!=ids&&ids.size()>0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("ids", ids);
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, data);
		} else {
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}

	}

	@Override
	public String inset(Map<String, Object> map) {
		return mediaDao.insertMedia(map);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject update(CommonParameters update, String strJson) {

		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		Map<String, Object> whereMap = new HashMap<String, Object>();
		// 获取修改数据主键
		String id = String.valueOf(mapJson.get(MediaVo.ID));
		if (null != id && !"".equals(id)) {
			whereMap.put(Media.ID, new ObjectId(id));

			// 查询数据库
			Map<String, Object> media = mediaDao.queryOne(id);

			// 修改条件
			Map<String, Object> set = new HashMap<String, Object>();
			if (!StringUtil.isEmpty(String.valueOf(mapJson.get(MediaVo.NAME)))) {
				set.put(Media.NAME, mapJson.get(MediaVo.NAME));
			}
			if (!StringUtil.isEmpty(String.valueOf(mapJson.get(MediaVo.CATAIDS)))) {
				set.put(Media.CATAIDS, mapJson.get(MediaVo.CATAIDS));
			}
			if (!StringUtil.isEmpty(String.valueOf(mapJson.get(MediaVo.REMARK)))) {
				set.put(Media.REMARK, mapJson.get(MediaVo.REMARK));
			}
			if (!StringUtil.isEmpty(String.valueOf(mapJson.get(MediaVo.EXTEND)))) {
				set.put(Media.EXTEND, mapJson.get(MediaVo.EXTEND));
			}
			if (!StringUtil.isEmpty(String.valueOf(mapJson.get(MediaVo.THUMBNAILURL)))) {
				set.put(Media.VSLT, mapJson.get(MediaVo.THUMBNAILURL));
				// 判断缩略图
				String vslt = String.valueOf(mapJson.get(MediaVo.THUMBNAILURL));
				List<Map<String, Object>> thumbnails = (List<Map<String, Object>>) media.get(Media.THUMBNAILS);
				// 判断是否有缩略图
				if (null != thumbnails && thumbnails.size() > 0) {
					List<Map<String, Object>> thumbnails_new = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < thumbnails.size(); i++) {
						Map<String, Object> thmbnail = thumbnails.get(i);
						if (String.valueOf(thmbnail.get(Media.TRANSCODE_ATTR_WANURL)).equals(vslt)) {
							thmbnail.put(Media.TRANSCODE_ATTR_TYPE, "1");
						} else {
							thmbnail.put(Media.TRANSCODE_ATTR_TYPE, "");
						}
						thumbnails_new.add(thmbnail);
					}
					set.put(Media.THUMBNAILS, thumbnails_new);
				}
			}
			if (!StringUtil.isEmpty(String.valueOf(mapJson.get(MediaVo.DESCRIBE)))) {
				set.put(Media.DESCRIBE, mapJson.get(MediaVo.DESCRIBE));
			}
			if (!StringUtil.isEmpty(String.valueOf(mapJson.get(MediaVo.OTHERMSG)))) {
				set.put(Media.OTHERMSG, mapJson.get(MediaVo.OTHERMSG));
			}
			set.put(Media.UUSERID, update.getUserId());
			set.put(Media.UUSERNAME, update.getUserName());
			set.put(Media.UUTIME, DateUtil.getCurrentDateTime());

			long index = mediaDao.updateBySet(whereMap, set);
			/** 开始记录日志 */
			Map<String, Object> LogMap = new HashMap<String, Object>();
			LogMap.put(CommonParameters.APPCODE, update.getAppCode());
			LogMap.put(CommonParameters.VERSIONID, update.getVersionId());
			LogMap.put(CommonParameters.COMPANYID, update.getCompanyId());
			LogMap.put(CommonParameters.USERID, update.getUserId());
			LogMap.put(CommonParameters.SERVICECODE, update.getServiceCode());
			LogMap.put("info", set);
			logger.info(LogUtil.getLogMessage(LogMap, "OnAirLogR", "ytl"));
			/** 开始记录日志完成 */
			// 保存日志
			systemLogService.inset(SystemLogUtil.getMap(update, "0", "修改", "修改媒体id《" + id + "》"));
			if (index > 0) {
				return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
			}
		} else {
			logger.info("修改选中的素材id集合为null！");
		}
		return new ResponseObject(GeneralStatus.update_error.status, GeneralStatus.update_error.enDetail, "");
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject deleteList(CommonParameters delete, String strJson) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);

		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<String> ids = (List<String>) mapJson.get(MediaVo.IDS);
		if (null != ids && !"".equals(ids)) {
			BasicDBList basicDBList = new BasicDBList();
			for (String id : ids) {
				basicDBList.add(new ObjectId(id));
			}
			whereMap.put(Media.ID, new BasicDBObject("$in", basicDBList));
			Map<String, Object> set = new HashMap<String, Object>();
			set.put(Media.ISDEL, Constants.ONE);
			Long media = mediaDao.updateBySet(whereMap, set);
			List<Map<String, Object>> lists = mediaDao.query(whereMap);
			StringBuffer sb = new StringBuffer();
			if (null != lists && lists.size() > 0) {
				for (Map<String, Object> map : lists) {
					String name = String.valueOf(map.get(Media.NAME));
					sb.append("《" + name + "》");
				}
			}
			if (media > 0) {
				// 保存日志
				systemLogService.inset(SystemLogUtil.getMap(delete, "0", "删除", "删除媒体" + sb.toString()));
				return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, media);
			}
		} else {
			logger.info("删除选中的素材id集合为null！");
		}
		return new ResponseObject(GeneralStatus.delete_error.status, GeneralStatus.delete_error.enDetail, "");
	}

	@Override
	public ResponseObject recycleList(CommonParameters update, String strJson) {
		// TODO Auto-generated method stub
		return new ResponseObject();
	}

	@Override
	public ResponseObject sharedList(CommonParameters update, String strJson) {
		// TODO Auto-generated method stub
		return new ResponseObject();
	}

	@Override
	public ResponseObject insetMedia(CommonParameters common, String fileName, String fileSize, String file) {
		String productCode = configurationService.getCompany();// 设置企业
		// 获取路径
		String fileDepositUrl = "";
		if (null != file && !"".equals(file)) {
			fileDepositUrl = file;
		} else {
			fileDepositUrl = FileUtil.getFileDepositUrl(fileName, fileSize, productCode);

		}
		// ---------------获取md5-----------------------
		String isMd5 = configurationService.getIsMd5();
		String md5 = "";
		if (null != isMd5 && "0".equals(isMd5)) {
			File md5File = new File(fileDepositUrl);
			md5 = MD5Util.getFileMD5String(md5File);
		}
		// ---------------获取md5-----------------------
		logger.info("文件名称大小：" + fileName + ";" + fileSize + ";productCode=" + productCode + "文件上传地址：" + fileDepositUrl);
		// 获取上传文件类型
		String type = FileUtil.getMaterialType(fileDepositUrl);
		String mtype = FileUtil.getMtype(type);
		// -------------记录历史任务--------------
		Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
		historicalTaskMap.put(HistoricalTask.APPCODE, common.getAppCode());
		historicalTaskMap.put(HistoricalTask.COMPANYID, common.getCompanyId());
		historicalTaskMap.put(HistoricalTask.VERSIONID, common.getVersionId());
		historicalTaskMap.put(HistoricalTask.USERID, common.getUserId());
		historicalTaskMap.put(HistoricalTask.SERVICECODE, common.getServiceCode());
		historicalTaskMap.put(HistoricalTask.CONSUMERID, productCode);
		historicalTaskMap.put(HistoricalTask.FILENAME, fileName);
		historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
		historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
		historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
		historicalTaskMap.put(HistoricalTask.STATUS, "2");
		historicalTaskMap.put(HistoricalTask.TYPE, type);
		historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
		historicalTaskMap.put(HistoricalTask.SRC, "页面上传：insetMedia");
		String taskid = historicalTaskService.inset(historicalTaskMap);
		logger.info("页面上传：insetMedia；历史任务记录数据插入返回id=" + taskid);
		// -------------记录历史任务--------------
		String id = "";
		Map<String, Object> transcodeMap = new HashMap<String, Object>();
		transcodeMap.put("common", common);
		transcodeMap.put("fileName", fileName);
		transcodeMap.put("mtype", mtype);
		transcodeMap.put("fileDepositUrl", fileDepositUrl);
		transcodeMap.put("taskid", taskid);
		transcodeMap.put("md5", md5);
		// 图片流程
		if ("picture".equals(type)) {
			id = screenShotService.addScreenShotAutomatic(common, fileName, mtype, fileDepositUrl, "页面上传", null, taskid, md5);
		}
		// 视音频流程
		if (!"text".equals(type) && !"picture".equals(type)) {
			transcodeMap.put("src", "页面上传");
			id = transcodeService.addTranscodeAutomatic(transcodeMap);

		}
		if (!StringUtil.isEmpty(id)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", id);
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, data);
		} else {
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}
	}

	@Override
	public ResponseObject insetHttpMedia(CommonParameters common, String strJson) {
		Map<String, Object> jsonMap = JsonUtil.readJSON2Map(strJson);
		String httpFile = String.valueOf(jsonMap.get("url"));
		String fileName = String.valueOf(jsonMap.get("name"));
		String MD5 = String.valueOf(jsonMap.get("md5"));
		String productCode = configurationService.getCompany();// 设置企业
		// 获取路径
		String fileDepositUrl = "";
		if (null != httpFile && !"".equals(httpFile)) {
			fileDepositUrl = httpFile;
		}
		// ---------------获取md5-----------------------
		String isMd5 = configurationService.getIsMd5();
		String md5 = "";
		if (null != isMd5 && "0".equals(isMd5)) {
			md5 = MD5;
		}
		// ---------------获取md5-----------------------
		logger.info("文件名称：" + fileName + ";productCode=" + productCode + "文件上传地址：" + fileDepositUrl);
		// 获取上传文件类型
		String type = FileUtil.getMaterialType(fileDepositUrl);
		String mtype = FileUtil.getMtype(type);
		// -------------记录历史任务--------------
		Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
		historicalTaskMap.put(HistoricalTask.APPCODE, common.getAppCode());
		historicalTaskMap.put(HistoricalTask.COMPANYID, common.getCompanyId());
		historicalTaskMap.put(HistoricalTask.VERSIONID, common.getVersionId());
		historicalTaskMap.put(HistoricalTask.USERID, common.getUserId());
		historicalTaskMap.put(HistoricalTask.SERVICECODE, common.getServiceCode());
		historicalTaskMap.put(HistoricalTask.CONSUMERID, productCode);
		historicalTaskMap.put(HistoricalTask.FILENAME, fileName);
		// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
		historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
		historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
		historicalTaskMap.put(HistoricalTask.STATUS, "2");
		historicalTaskMap.put(HistoricalTask.TYPE, type);
		historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
		historicalTaskMap.put(HistoricalTask.SRC, "页面上传：insetMedia");
		String taskid = historicalTaskService.inset(historicalTaskMap);
		logger.info("页面上传：insetMedia；历史任务记录数据插入返回id=" + taskid);
		systemLogService.inset(SystemLogUtil.getMap(common, "0", "上传", "上传文件《" + fileName + "》"));
		LogUtil.printIntegralLog(common, "uploadfile", "上传文件《" + fileName + "》");
		// -------------记录历史任务--------------
		String id = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("common", common);
		map.put("fileName", fileName);
		map.put("mtype", mtype);
		map.put("fileDepositUrl", fileDepositUrl);
		map.put("taskid", taskid);
		map.put("md5", md5);
		// 图片流程
		if ("picture".equals(type)) {
			map.put("src", "页面上传");
			id = screenShotService.addScreenShotAutomatic(common, fileName, mtype, fileDepositUrl, "页面上传", null, taskid, md5);
		}
		// 视音频流程
		if (!"text".equals(type) && !"picture".equals(type)) {
			map.put("src", "页面上传");
			// id = transcodeService.addTranscodeAutomatic(common,
			// fileName,mtype,fileDepositUrl,"页面上传",null,taskid,md5);
			id = transcodeService.addTranscodeAutomatic(map);

		}
		if (!StringUtil.isEmpty(id)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", id);
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, data);
		} else {
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseObject count(CommonParameters count, String strJson) {
		Map<String, Object> jsonMap = JsonUtil.readJSON2Map(strJson);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = getVommonalityParam(count, jsonMap, null, null);
		whereMap.put(Media.STATUS, GeneralStatus.success.status);
		whereMap.put(Media.ISDEL, Constants.ZERO);
		List<String> mtype = (List<String>) jsonMap.get(MediaVo.MTYPE);
		if (null != mtype && !"".equals(mtype)) {
			BasicDBList basicDBList = new BasicDBList();
			for (String mtypein : mtype) {
				basicDBList.add(mtypein);
			}
			whereMap.put(Media.MTYPE, new BasicDBObject("$in", basicDBList));
		}
		Long counts = mediaDao.count(whereMap);
		// 保存日志
		// systemLogService.inset(SystemLogUtil.getMap(count, "0", "统计",
		// "统计媒体类型《"+mtype+"》"));
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("count", counts);
		return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, data);
	}

	@Override
	public ResponseObject statistics(CommonParameters count, String strJson) {
		try {
			Map<String, Object> jsonMap = JsonUtil.readJSON2Map(strJson);

			// 获取业务参数,并进行相关业务操作
			Map<String, Object> whereMap = getVommonalityParam(count, jsonMap, null, null);
			whereMap.put(Media.STATUS, GeneralStatus.success.status);
			whereMap.put(Media.ISDEL, Constants.ZERO);
			List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();
			List<Object> counts = new ArrayList<Object>();
			String dateType = String.valueOf(jsonMap.get("dateType"));
			// 开始时间
			String startTime = "";
			String endTime = "";
			if ("week".equals(dateType)) {
				String[] dates = DateUtil.getBeginAndEndTime("本周").split(",");
				startTime = dates[0];
				endTime = dates[1];
			}
			if ("month".equals(dateType)) {
				String[] dates = DateUtil.getBeginAndEndTime("本月").split(",");
				startTime = dates[0];
				endTime = dates[1];
			}
			if ("year".equals(dateType)) {
				String[] dates = DateUtil.getBeginAndEndTime("今年").split(",");
				startTime = dates[0];
				endTime = dates[1];
			}
			if (!StringUtil.isEmpty(jsonMap.get("startTime")) && !StringUtil.isEmpty(String.valueOf(jsonMap.get("startTime")))) {
				startTime = String.valueOf(jsonMap.get("startTime"));
			}
			// 结束时间
			if (!StringUtil.isEmpty(jsonMap.get("endTime")) && !StringUtil.isEmpty(String.valueOf(jsonMap.get("endTime")))) {
				endTime = String.valueOf(jsonMap.get("endTime"));
			}
			Date bgDate = DateUtil.stringToDate(startTime);
			Date egDate = DateUtil.stringToDate(endTime);
			List<Date> datelists = DateUtil.findDates(bgDate, egDate);// 获取开始时间到结束时间内的时间点
			// 时间算法 小于等于14天 安天算 15到60按周算 60以上按月算
			if (null != datelists && datelists.size() > 61) {// 时间超过两个月的按照月为单位进行统计
				List<Map<String, Object>> maplists = DateUtil.findGroupByMonth(startTime, endTime);// 按月统计
				for (int i = 0; i < maplists.size(); i++) {

					String strdate = null;
					Map<String, Object> maps = maplists.get(i);
					BasicDBObject dateCondition = new BasicDBObject();
					String datest = "";
					for (String key : maps.keySet()) {
						if ("stime" == key || "stime".equals(key)) {
							strdate = String.valueOf(maps.get(key));
							strdate = strdate.substring(0, strdate.lastIndexOf("-"));
							datest = String.valueOf(maps.get(key));
							String starDate = maps.get(key) + " 00:00:00";
							dateCondition.append(QueryOperators.GTE, starDate);
						}
						if ("etime" == key || "etime".equals(key)) {
							String endDate = maps.get(key) + " 23:59:59";
							dateCondition.append(QueryOperators.LTE, endDate);// 查询昨天的日期
						}
					}
					Map<String, Object> date = new HashMap<String, Object>();
					date.put(datest, dateCondition);
					dateList.add(date);
				}

			} else if (null != datelists && datelists.size() > 7) {// 时间超过7天的按照七天为单位进行统计
				List<Map<String, Object>> maplists = DateUtil.findWeekDate(startTime, endTime);// 按周统计
				for (int i = 0; i < maplists.size(); i++) {
					String strdate = null;
					Map<String, Object> maps = maplists.get(i);
					BasicDBObject dateCondition = new BasicDBObject();
					String datest = "";
					for (String key : maps.keySet()) {
						if ("stime" == key || "stime".equals(key)) {
							strdate = String.valueOf(maps.get(key));
							strdate = strdate.substring(0, strdate.lastIndexOf("-") + 3);
							datest = String.valueOf(maps.get(key));
							String starDate = maps.get(key) + " 00:00:00";
							dateCondition.append(QueryOperators.GTE, starDate);
						}
						if ("etime" == key || "etime".equals(key)) {
							String endDate = maps.get(key) + " 23:59:59";
							strdate = strdate + "~" + maps.get(key);
							dateCondition.append(QueryOperators.LTE, endDate);// 查询昨天的日期
						}
					}
					Map<String, Object> date = new HashMap<String, Object>();
					date.put(datest, dateCondition);
					dateList.add(date);
				}
			} else {
				for (Date dates : datelists) {
					String strdate = DateUtil.dateToString(dates);
					String starDate = strdate + " 00:00:00";
					String endDate = strdate + " 23:59:59";
					BasicDBObject dateCondition = new BasicDBObject();
					dateCondition.append(QueryOperators.GTE, starDate);
					dateCondition.append(QueryOperators.LTE, endDate);// 查询昨天的日期
					Map<String, Object> date = new HashMap<String, Object>();
					date.put(strdate, dateCondition);
					dateList.add(date);
				}
			}

			// List<Map<String, Object>> st = new
			// ArrayList<Map<String,Object>>();
			for (int i = 0; i < dateList.size(); i++) {
				for (String key : dateList.get(i).keySet()) {
					whereMap.put(Media.CTIME, dateList.get(i).get(key));
					List<String> mtype = new ArrayList<String>();
					mtype.add("video");// 视频
					mtype.add("audio");// 音频
					mtype.add("pic");// 图片
					Map<String, Object> sts = new HashMap<String, Object>();
					sts.put("date", key);
					for (int j = 0; j < mtype.size(); j++) {
						if ("video".equals(mtype.get(j))) {
							whereMap.put(Media.MTYPE, "0");
						} else if ("audio".equals(mtype.get(j))) {
							whereMap.put(Media.MTYPE, "1");
						} else if ("pic".equals(mtype.get(j))) {
							whereMap.put(Media.MTYPE, "2");
						}
						Long intex = mediaDao.count(whereMap);
						sts.put(mtype.get(j), intex);
					}
					counts.add(sts);
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("statistics", counts);
			data.put("startTime", startTime);
			data.put("endTime", endTime);
			// 保存日志
			systemLogService.inset(SystemLogUtil.getMap(count, "0", "统计", "素材报表统计"));
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseObject registerResource(CommonParameters insert, String mType, String fileDepositUrl, String name, String remark) {
		// 获取用户信息
		Map<String, Object> userMap = userService.getUserInforById(insert.getUserId());
		UserUtil.getUserInfo(insert, userMap);
		systemLogService.inset(SystemLogUtil.getMap(insert, "0", "手机上传", "上传文件《" + name + "》"));
		// boolean bool = false;
		String bool = "";
		// 获取上传文件类型
		String type = FileUtil.getMaterialType(fileDepositUrl);
		// ---------------获取md5-----------------------
		String isMd5 = configurationService.getIsMd5();
		String md5 = "";
		if (null != isMd5 && "0".equals(isMd5)) {
			File md5File = new File(fileDepositUrl);
			md5 = MD5Util.getFileMD5String(md5File);
		}
		// ---------------获取md5-----------------------
		// -------------记录历史任务--------------
		Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
		historicalTaskMap.put(HistoricalTask.APPCODE, insert.getAppCode());
		historicalTaskMap.put(HistoricalTask.COMPANYID, insert.getCompanyId());
		historicalTaskMap.put(HistoricalTask.VERSIONID, insert.getVersionId());
		historicalTaskMap.put(HistoricalTask.USERID, insert.getUserId());
		historicalTaskMap.put(HistoricalTask.SERVICECODE, insert.getServiceCode());
		// historicalTaskMap.put(HistoricalTask.CONSUMERID, productCode);
		historicalTaskMap.put(HistoricalTask.FILENAME, name);
		// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
		historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
		historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
		historicalTaskMap.put(HistoricalTask.STATUS, "2");
		historicalTaskMap.put(HistoricalTask.TYPE, type);
		historicalTaskMap.put(HistoricalTask.MTYPE, mType);
		historicalTaskMap.put(HistoricalTask.SRC, "手机上传：registerResource");
		String taskid = historicalTaskService.inset(historicalTaskMap);
		logger.info("手机上传：registerResource历史任务记录数据插入返回id=" + taskid);
		// -------------记录历史任务--------------
		Map<String, Object> transcodeMap = new HashMap<String, Object>();
		transcodeMap.put("common", insert);
		transcodeMap.put("fileName", name);
		transcodeMap.put("fileDepositUrl", fileDepositUrl);
		transcodeMap.put("taskid", taskid);
		transcodeMap.put("md5", md5);
		transcodeMap.put("remark", remark);
		if (null != mType && "video".equals(mType)) {
			String mtype = FileUtil.getMtype(type);
			transcodeMap.put("mtype", mtype);
			transcodeMap.put("src", "手机上传");
			// 视音频流程
			bool = transcodeService.addTranscodeAutomatic(transcodeMap);
		}
		if (null != mType && "audio".equals(mType)) {
			String mtype = FileUtil.getMtype(type);
			transcodeMap.put("mtype", mtype);
			transcodeMap.put("src", "手机上传");
			// 视音频流程
			bool = transcodeService.addTranscodeAutomatic(transcodeMap);
		}
		if (null != mType && "image".equals(mType)) {
			String mtype = FileUtil.getMtype(type);
			// 图片流程
			bool = screenShotService.addScreenShotAutomatic(insert, name, mtype, fileDepositUrl, "手机上传", remark, taskid, md5);
		}
		if (!StringUtil.isEmpty(bool)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", bool);
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, data);
		} else {
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}
	}

	@Override
	public ResponseObject registerResource(CommonParameters insert, String mType, String fileDepositUrl, String name, String remark, String uploadUUID) {
		// 获取用户信息
		Map<String, Object> userMap = userService.getUserInforById(insert.getUserId());
		UserUtil.getUserInfo(insert, userMap);
		systemLogService.inset(SystemLogUtil.getMap(insert, "0", "手机上传", "上传文件《" + name + "》"));
		boolean bool = false;
		// 获取上传文件类型
		String type = FileUtil.getMaterialType(fileDepositUrl);
		// ---------------获取md5-----------------------
		String isMd5 = configurationService.getIsMd5();
		String md5 = "";
		if (null != isMd5 && "0".equals(isMd5)) {
			File md5File = new File(fileDepositUrl);
			md5 = MD5Util.getFileMD5String(md5File);
		}
		// ---------------获取md5-----------------------
		// -------------记录历史任务--------------
		Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
		historicalTaskMap.put(HistoricalTask.UPLOADUUID, uploadUUID);
		historicalTaskMap.put(HistoricalTask.APPCODE, insert.getAppCode());
		historicalTaskMap.put(HistoricalTask.COMPANYID, insert.getCompanyId());
		historicalTaskMap.put(HistoricalTask.VERSIONID, insert.getVersionId());
		historicalTaskMap.put(HistoricalTask.USERID, insert.getUserId());
		historicalTaskMap.put(HistoricalTask.SERVICECODE, insert.getServiceCode());
		// historicalTaskMap.put(HistoricalTask.CONSUMERID, productCode);
		historicalTaskMap.put(HistoricalTask.FILENAME, name);
		// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
		historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
		historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
		historicalTaskMap.put(HistoricalTask.STATUS, "2");
		historicalTaskMap.put(HistoricalTask.TYPE, type);
		historicalTaskMap.put(HistoricalTask.MTYPE, mType);
		historicalTaskMap.put(HistoricalTask.SRC, "手机上传：registerResource");
		String taskid = historicalTaskService.inset(historicalTaskMap);
		logger.info("手机上传：registerResource历史任务记录数据插入返回id=" + taskid);
		// -------------记录历史任务--------------
		if (null != mType && "video".equals(mType)) {
			String mtype = FileUtil.getMtype(type);
			// 视音频流程
			bool = transcodeService.addTranscodeAutomatic(insert, name, mtype, fileDepositUrl, "手机上传", remark, taskid, md5, uploadUUID);
		}
		if (null != mType && "audio".equals(mType)) {
			String mtype = FileUtil.getMtype(type);
			// 视音频流程
			bool = transcodeService.addTranscodeAutomatic(insert, name, mtype, fileDepositUrl, "手机上传", remark, taskid, md5, uploadUUID);
		}
		if (null != mType && "image".equals(mType)) {
			String mtype = FileUtil.getMtype(type);
			// 图片流程
			bool = screenShotService.addScreenShotAutomatic(insert, name, mtype, fileDepositUrl, "手机上传", remark, taskid, md5, uploadUUID);
		}
		if (bool) {
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
		} else {
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}
	}

	@Override
	public ResponseObject findList(Map<String, Object> mapUploadIds) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> backMap = new HashMap<String, Object>();
		backMap.put(Media.NAME, 1);
		backMap.put(Media.STATUS, 1);
		backMap.put(Media.FMT, 1);
		backMap.put(Media.UPLOADUUID, 1);
		backMap.put(Media.VSLT, 1);
		backMap.put(Media.WANURL, 1);
		backMap.put(Media.MTYPE, 1);
		String mediaIds = String.valueOf(mapUploadIds.get("uploadIds"));
		if (null != mediaIds && !"null".equals(mediaIds) && !"".equals(mediaIds)) {
			String[] materialIdArray = mediaIds.split(",");
			BasicDBList basicDBList = new BasicDBList();
			for (String materialId : materialIdArray) {
				basicDBList.add(materialId);
			}
			whereMap.put(Media.UPLOADUUID, new BasicDBObject("$in", basicDBList));
		}
		// 排序参数
		Map<String, Object> sortFilter = getSortParam(Media.CTIME, Constants.SZERO);
		// 分页参数
		int currentPage = getCurrentPage(mapUploadIds);
		int pageNum = getPageNum(mapUploadIds);
		List<Map<String, Object>> lists = mediaDao.query(sortFilter, whereMap, backMap, currentPage, pageNum);
		ResponseObject resObj = new ResponseObject();
		executeSuccess(resObj, lists);
		return resObj;
	}

	/** 移动端查询素材时返回的字段 */
	private Map<String, Object> getMediaMapPhone() {
		Map<String, Object> mapBack = new HashMap<String, Object>();
		mapBack.put(Media.MTYPE, 1);
		mapBack.put(Media.NAME, 1);
		mapBack.put(Media.STATUS, 1);
		mapBack.put(Media.CTIME, 1);
		mapBack.put(Media.VSLT, 1);
		mapBack.put(Media.HEIGHT, 1);
		mapBack.put(Media.WIDTH, 1);
		mapBack.put(Media.DESCRIBE, 1);
		mapBack.put(Media.OTHERMSG, 1);
		mapBack.put(Media.SRC, 1);
		mapBack.put(Media.REMARK, 1);
		mapBack.put(Media.DESCRIBE, 1);
		mapBack.put(Media.FMT, 1);
		mapBack.put(Media.SIZE, 1);
		mapBack.put(Media.UUTIME, 1);
		mapBack.put(Media.RATE, 1);
		mapBack.put(Media.WANURL, 1);
		mapBack.put("defaults.fmt", 1);
		mapBack.put("defaults.ctype", 1);
		mapBack.put("defaults.wanurl", 1);
		mapBack.put("defaults.duration", 1);
		return mapBack;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject scanRegistration(CommonParameters insert, String strJson) {

		Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
		String ytpe = String.valueOf(mapJson.get("src"));
		// boolean bool = false;
		String bool = "";
		if (null != mapJson.get("video")) {
			Map<String, Object> videos = (Map<String, Object>) mapJson.get("video");
			if (null != videos && videos.size() > 0) {
				// 获取路径
				String fileDepositUrl = String.valueOf(videos.get("url"));
				// 获取文件名
				String fileName = String.valueOf(videos.get("name"));
				// 获取上传文件类型
				String type = FileUtil.getMaterialType(fileDepositUrl);
				// ---------------获取md5-----------------------
				String isMd5 = configurationService.getIsMd5();
				String md5 = "";
				if (null != isMd5 && "0".equals(isMd5)) {
					File md5File = new File(fileDepositUrl);
					md5 = MD5Util.getFileMD5String(md5File);
				}
				// ---------------获取md5-----------------------
				String mtype = FileUtil.getMtype(type);
				// -------------记录历史任务--------------
				Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
				historicalTaskMap.put(HistoricalTask.APPCODE, insert.getAppCode());
				historicalTaskMap.put(HistoricalTask.COMPANYID, insert.getCompanyId());
				historicalTaskMap.put(HistoricalTask.VERSIONID, insert.getVersionId());
				historicalTaskMap.put(HistoricalTask.USERID, insert.getUserId());
				historicalTaskMap.put(HistoricalTask.SERVICECODE, insert.getServiceCode());
				// historicalTaskMap.put(HistoricalTask.CONSUMERID,
				// productCode);
				historicalTaskMap.put(HistoricalTask.FILENAME, fileName);
				// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
				historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
				historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
				historicalTaskMap.put(HistoricalTask.STATUS, "2");
				historicalTaskMap.put(HistoricalTask.TYPE, type);
				historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
				historicalTaskMap.put(HistoricalTask.SRC, "接口上传：registerResource");
				String taskid = historicalTaskService.inset(historicalTaskMap);
				logger.info("扫描上传：registerResource历史任务记录数据插入返回id=" + taskid);
				// -------------记录历史任务--------------
				Map<String, Object> transcodeMap = new HashMap<String, Object>();
				transcodeMap.put("common", insert);
				transcodeMap.put("fileName", fileName);
				transcodeMap.put("fileDepositUrl", fileDepositUrl);
				transcodeMap.put("taskid", taskid);
				transcodeMap.put("md5", md5);
				transcodeMap.put("mtype", mtype);
				transcodeMap.put("src", ytpe);
				// 视音频流程
				bool = transcodeService.addTranscodeAutomatic(transcodeMap);
			}
		}
		if (null != mapJson.get("audio")) {
			Map<String, Object> audios = (Map<String, Object>) mapJson.get("audio");
			if (null != audios && audios.size() > 0) {
				// 获取路径
				String fileDepositUrl = String.valueOf(audios.get("url"));
				// 获取文件名
				String fileName = String.valueOf(audios.get("name"));
				// 获取上传文件类型
				String type = FileUtil.getMaterialType(fileDepositUrl);
				// ---------------获取md5-----------------------
				String isMd5 = configurationService.getIsMd5();
				String md5 = "";
				if (null != isMd5 && "0".equals(isMd5)) {
					File md5File = new File(fileDepositUrl);
					md5 = MD5Util.getFileMD5String(md5File);
				}
				// ---------------获取md5-----------------------
				String mtype = FileUtil.getMtype(type);
				// -------------记录历史任务--------------
				Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
				historicalTaskMap.put(HistoricalTask.APPCODE, insert.getAppCode());
				historicalTaskMap.put(HistoricalTask.COMPANYID, insert.getCompanyId());
				historicalTaskMap.put(HistoricalTask.VERSIONID, insert.getVersionId());
				historicalTaskMap.put(HistoricalTask.USERID, insert.getUserId());
				historicalTaskMap.put(HistoricalTask.SERVICECODE, insert.getServiceCode());
				// historicalTaskMap.put(HistoricalTask.CONSUMERID,
				// productCode);
				historicalTaskMap.put(HistoricalTask.FILENAME, fileName);
				// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
				historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
				historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
				historicalTaskMap.put(HistoricalTask.STATUS, "2");
				historicalTaskMap.put(HistoricalTask.TYPE, type);
				historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
				historicalTaskMap.put(HistoricalTask.SRC, "接口上传：registerResource");
				String taskid = historicalTaskService.inset(historicalTaskMap);
				logger.info("扫描上传：registerResource历史任务记录数据插入返回id=" + taskid);
				// -------------记录历史任务--------------
				Map<String, Object> transcodeMap = new HashMap<String, Object>();
				transcodeMap.put("common", insert);
				transcodeMap.put("fileName", fileName);
				transcodeMap.put("fileDepositUrl", fileDepositUrl);
				transcodeMap.put("taskid", taskid);
				transcodeMap.put("md5", md5);
				transcodeMap.put("mtype", mtype);
				transcodeMap.put("src", ytpe);
				// 视音频流程
				bool = transcodeService.addTranscodeAutomatic(transcodeMap);
			}
		}
		if (null != mapJson.get("picture")) {
			Map<String, Object> images = (Map<String, Object>) mapJson.get("picture");
			if (null != images && images.size() > 0) {
				// 获取路径
				String fileDepositUrl = String.valueOf(images.get("url"));
				// 获取文件名
				String fileName = String.valueOf(images.get("name"));
				// 获取上传文件类型
				String type = FileUtil.getMaterialType(fileDepositUrl);
				// ---------------获取md5-----------------------
				String isMd5 = configurationService.getIsMd5();
				String md5 = "";
				if (null != isMd5 && "0".equals(isMd5)) {
					File md5File = new File(fileDepositUrl);
					md5 = MD5Util.getFileMD5String(md5File);
				}
				// ---------------获取md5-----------------------
				String mtype = FileUtil.getMtype(type);
				// -------------记录历史任务--------------
				Map<String, Object> historicalTaskMap = new HashMap<String, Object>();
				historicalTaskMap.put(HistoricalTask.APPCODE, insert.getAppCode());
				historicalTaskMap.put(HistoricalTask.COMPANYID, insert.getCompanyId());
				historicalTaskMap.put(HistoricalTask.VERSIONID, insert.getVersionId());
				historicalTaskMap.put(HistoricalTask.USERID, insert.getUserId());
				historicalTaskMap.put(HistoricalTask.SERVICECODE, insert.getServiceCode());
				// historicalTaskMap.put(HistoricalTask.CONSUMERID,
				// productCode);
				historicalTaskMap.put(HistoricalTask.FILENAME, fileName);
				// historicalTaskMap.put(HistoricalTask.FILESIZE, fileSize);
				historicalTaskMap.put(HistoricalTask.FILEDEPOSITURL, fileDepositUrl);
				historicalTaskMap.put(HistoricalTask.CTIME, DateUtil.getCurrentDateTime());
				historicalTaskMap.put(HistoricalTask.STATUS, "2");
				historicalTaskMap.put(HistoricalTask.TYPE, type);
				historicalTaskMap.put(HistoricalTask.MTYPE, mtype);
				historicalTaskMap.put(HistoricalTask.SRC, "接口上传：registerResource");
				String taskid = historicalTaskService.inset(historicalTaskMap);
				logger.info("扫描上传：registerResource历史任务记录数据插入返回id=" + taskid);
				// -------------记录历史任务--------------
				// 图片流程
				bool = screenShotService.addScreenShotAutomatic(insert, fileName, mtype, fileDepositUrl, ytpe, null, taskid, md5);
			}
		}
		if (!StringUtil.isEmpty(bool)) {
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, "");
		} else {
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}

	}

	@Override
	public ResponseObject queryProgress(CommonParameters common, String json) {
		Map<String, Object> mapJson = JsonUtil.readJSON2Map(json);

		// 获取业务参数,并进行相关业务操作
		Map<String, Object> whereMap = new HashMap<String, Object>();
		// id
		String mediaIds = String.valueOf(mapJson.get("ids"));
		if (null != mediaIds && !"null".equals(mediaIds) && !"".equals(mediaIds)) {
			String[] materialIdArray = mediaIds.split(",");
			List<Map<String, String>> progress = new ArrayList<Map<String,String>>();
			int tasksize =0;
			for (String string : materialIdArray) {
				Map<String, String> taskmap = new HashMap<String, String>();
				taskmap.put("id", string);
				whereMap.put(Task.MID, string);
				List<Map<String, Object>> task = taskDao.queryList(whereMap);
				DecimalFormat    df   = new DecimalFormat("######0.00");  
				double count = 0;
				for (Map<String, Object> map : task) {
					if(null!=map&&map.containsKey(Task.OUTPUT)){
						tasksize+=1;
						String taskid = String.valueOf(map.get(Task.TASKID));
						String index = transcodeService.TranscodeQuery(common, taskid);
						count+=Double.valueOf(index);
//						logger.info("调用接口获得：index="+index+"|count="+count);
					}
				}
				if(count>0){
					count=	count/tasksize;
				}
//				logger.info("count="+count+"|tasksize="+tasksize);
				taskmap.put("progress", df.format(count)+"%");
				progress.add(taskmap);
			}
			ResponseObject resObj = new ResponseObject();
			executeSuccess(resObj, progress);
			return resObj;
		}
		
		return queryError("null");
	}
}
