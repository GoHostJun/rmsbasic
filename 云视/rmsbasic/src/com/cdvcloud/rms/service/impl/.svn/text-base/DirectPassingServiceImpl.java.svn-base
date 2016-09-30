package com.cdvcloud.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Constants;
import com.cdvcloud.rms.common.GeneralStatus;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.dao.IDirectPassingDao;
import com.cdvcloud.rms.dao.IDirectPassingPathDao;
import com.cdvcloud.rms.domain.DirectPassing;
import com.cdvcloud.rms.domain.DirectPassingPath;
import com.cdvcloud.rms.domain.MaterialTemplate;
import com.cdvcloud.rms.domain.Media;
import com.cdvcloud.rms.service.ConfigurationService;
import com.cdvcloud.rms.service.IAnalysisService;
import com.cdvcloud.rms.service.IDirectPassingService;
import com.cdvcloud.rms.service.IMaterialTemplateService;
import com.cdvcloud.rms.service.IMediaService;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.service.IWeChatService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.FileUtil;
import com.cdvcloud.rms.util.HttpUtil;
import com.cdvcloud.rms.util.JsonUtil;
import com.cdvcloud.rms.util.StringUtil;
import com.cdvcloud.rms.util.SystemLogUtil;

@Service
public class DirectPassingServiceImpl extends BasicService implements IDirectPassingService {
	private static final Logger logger = Logger.getLogger(DirectPassingServiceImpl.class);
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private IDirectPassingDao directPassingDao;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private IMediaService materialService;
	@Autowired
	private IAnalysisService analysisService;
	@Autowired
	private IMaterialTemplateService materialTemplateService;
	@Autowired
	private IDirectPassingPathDao directPassingPathDao;
	@Autowired
	private IWeChatService weChatService;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject saveHttpMaterial(CommonParameters insert, String strJson) {
		Map<String, Object> jsonMap = JsonUtil.readJSON2Map(strJson);
		// 直传方法
		String httpFile = String.valueOf(jsonMap.get("url"));
		String fileName = String.valueOf(jsonMap.get("name"));
		String size = String.valueOf(jsonMap.get("size"));
		String md5 = String.valueOf(jsonMap.get("md5"));
		String directIds = String.valueOf(jsonMap.get("directIds"));
		logger.info("进入直传servcie参数httpFile:" + httpFile);
		String type = FileUtil.getMaterialType(httpFile);
		String mtype = FileUtil.getMtype(type);
		String destFile = "\\input\\" + insert.getLoginName() + "\\" + DateUtil.toISODateString(new Date()) + "\\" + fileName;
		logger.info("directIds:" + directIds + "mtype:" + mtype);
		// 直传设置： other里包含2个参数， 1：toNewp是直传需要推送newsphere ,2：directIds是直传选择推送id
		Map<String, Object> other = new HashMap<String, Object>();
		other.put("toNewp", "true");
		if ("0".equals(mtype)) {
			// 判断页面是否选择了直传
			if (!StringUtil.isEmpty(directIds)) {
				// -----------------------判断文件-----------------------
				boolean bo = verdict(insert, httpFile);
				if (bo) {
					logger.info("文件验证通过，调用迁移，入云通联库");
					// 文件验证通过，调用迁移，入云通联库
					postRemove(httpFile, fileName, size, md5, directIds, mtype, destFile, insert);
				} else {
					logger.info("文件验证不通过，先转码，在调用迁移");
					other.put("directIds", directIds);
				}
				systemLogService.inset(SystemLogUtil.getMap(insert, "0", "直传", "直传文件《" + fileName + "》"));
			}
		}
		// 入云通联
		insert.setOther(other);
		ResponseObject res = materialService.insetHttpMedia(insert, strJson);
		Map<String, Object> data = (Map<String, Object>) res.getData();
		String id = String.valueOf(data.get("id"));
		if (!StringUtil.isEmpty(id)) {
			return new ResponseObject(GeneralStatus.success.status, GeneralStatus.success.enDetail, data);
		} else {
			return new ResponseObject(GeneralStatus.media_failure.status, GeneralStatus.media_failure.enDetail, "");
		}

	}

	/**
	 * 统一调用迁移
	 */
	@Override
	public void postRemove(String httpFile, String fileName, String size, String md5, String directIds, String mtype, String destFile,
			CommonParameters insert) {
		Map<String, Object> directPassingMap = new HashMap<String, Object>();
		directPassingMap.put(DirectPassing.CTIME, DateUtil.getCurrentDateTime());
		directPassingMap.put(DirectPassing.NAME, fileName);
		directPassingMap.put(DirectPassing.SIZE, size);
		directPassingMap.put(DirectPassing.TYPE, mtype);
		directPassingMap.put(DirectPassing.DESTFILE, destFile);
		directPassingMap.put(DirectPassing.SRCFILE, httpFile);
		commonDirectPassing(insert, directPassingMap);
		directPassingMap.put(DirectPassing.COMPLIANCE, "0");
		String[] directPathIds = directIds.split(",");
		for (String directPathId : directPathIds) {
			dealpostRemove(httpFile, fileName, size, md5, directPathId, destFile, directPassingMap);
		}
	}

	/**
	 * 
	 * @param httpFile
	 * @param fileName
	 * @param size
	 * @param md5
	 * @param directPathId
	 * @param destFile
	 * @param directPassingMap
	 */
	private void dealpostRemove(String httpFile, String fileName, String size, String md5, String directPathId, String destFile,
			Map<String, Object> directPassingMap) {
		Map<String, Object> pathMap = directPassingPathDao.findOne(directPathId);
		String otherid = UUID.randomUUID().toString();
		String id = directPassing(fileName, httpFile, md5, destFile, pathMap, otherid, true);
		if (!StringUtil.isEmpty(id)) {
			directPassingMap.put(DirectPassing.TASKID, id);
			directPassingMap.put(DirectPassing.STATUS, 2);
		} else {
			directPassingMap.put(DirectPassing.STATUS, 1);
		}
		directPassingMap.put(DirectPassing.ISTASK, "0");
		directPassingMap.put(DirectPassing.DIRECTPATHNAME, pathMap.get(DirectPassingPath.TARPATHNAME));
		directPassingMap.put(DirectPassing.OTHERID, otherid);
		directPassingMap.put(DirectPassing.DIRECTID, directPathId);
		directPassingDao.insertObject(directPassingMap);
	}

	/**
	 * 公共参数拼接
	 * 
	 * @param insert
	 * @param directPassingMap
	 */
	private void commonDirectPassing(CommonParameters insert, Map<String, Object> directPassingMap) {
		directPassingMap.put(DirectPassing.CONSUMERID, insert.getCompanyId());
		directPassingMap.put(DirectPassing.CUSENAME, insert.getUserName());
		directPassingMap.put(DirectPassing.CUSERID, insert.getUserId());
		directPassingMap.put(DirectPassing.CONSUMERNAME, insert.getConsumerName());
		directPassingMap.put(DirectPassing.DEPARTMENTID, insert.getDepartmentId());
	}

	@SuppressWarnings("unchecked")
	private String directPassing(String fileName, String httpFile, String md5, String destFile, Map<String, Object> directPassingPath,
			String otherid, boolean isSend) {
		// 获取直传token
		StringBuffer params = new StringBuffer();
		params.append("accessKey=" + configurationService.getDirectPassing_accessKey());
		params.append("&timeStamp=" + String.valueOf(System.currentTimeMillis()));
		String token = "";
		String tokenUrl = configurationService.getDirectPassing_getaccesstoken();
		logger.info("直传获取token url=" + tokenUrl + ";参数：" + params.toString());
		String accessResultJson = HttpUtil.sendPost(tokenUrl, params.toString());
		logger.info("直传获取token返回=" + accessResultJson);
		Map<String, Object> accessResultMap = JsonUtil.readJSON2Map(accessResultJson);
		Map<String, String> accessdata = (HashMap<String, String>) accessResultMap.get("data");
		token = String.valueOf(accessdata.get("accessToken"));
		// 获取直传token
		StringBuffer moveparams = new StringBuffer();
		moveparams.append("accessToken=" + token);
		moveparams.append("&timeStamp=" + System.currentTimeMillis());
		String backPara = JsonUtil.writeMap2JSON(generationMoveParams(fileName, httpFile, md5, destFile, directPassingPath, otherid));
		moveparams.append("&data=" + backPara);
		String id = "";
		String moveUrl = configurationService.getDirectPassing_move();
		if (isSend) {
			logger.info("下发直传任务url=" + moveUrl + ";参数：" + moveparams.toString());
			String moveResultJson = HttpUtil.sendPost(moveUrl, moveparams.toString());
			logger.info("下发直传任务返回=" + moveResultJson);
			Map<String, Object> result = JsonUtil.readJSON2Map(moveResultJson);
			if (Integer.valueOf(String.valueOf(result.get("code"))) == 0) {
				id = String.valueOf(((Map<String, Object>) result.get("data")).get("id"));
			}
			return id;
		} else {
			return backPara;
		}

	}

	private Map<String, Object> generationMoveParams(String name, String url, String md5, String destFile, Map<String, Object> directPassingPath,
			String otherid) {
		String tarpathid = String.valueOf(directPassingPath.get(DirectPassingPath.TARPATHID));
		Map<String, Object> params = new HashMap<String, Object>();
		String moveTaskPriority = String.valueOf(5); // 任务优先级，值越大优先级越高。取值范围0-10。建议5
		params.put("name", name);
		params.put("priority", moveTaskPriority);
		String pushpathaddr = "";
		String tarpathaddr = String.valueOf(directPassingPath.get(DirectPassingPath.TARPATHADDR));
		if (directPassingPath.containsKey(DirectPassingPath.PUSHPATHADDR)) {
			pushpathaddr = String.valueOf(directPassingPath.get(DirectPassingPath.PUSHPATHADDR));
			params.put("to_url", pushpathaddr); // 文件从外网迁移到内网以后，通知目标系统进行对应操作处理。（目前没有业务需要，暂时不填）
		} else {
			params.put("to_url", ""); // 文件从外网迁移到内网以后，通知目标系统进行对应操作处理。（目前没有业务需要，暂时不填）
		}
		String backUrl = "";
		if ("ntm".equals(directPassingPath.get(DirectPassingPath.PUSHPATHNAME))) {
			backUrl = configurationService.getCallback() + "/api/NTMCallBack/";
			params.put("notification_request", getNTM(name, destFile, backUrl, otherid, tarpathaddr)); // 文件从外网迁移到内网以后，通知目标系统进行对应操作处理时，携带的请求参数。（目前没有业务需要，暂时不填）
		}
		String moveTaskCallBackUrl = configurationService.getCallback() + "/api/moveTaskCallBack/"; // 任务回调url
		params.put("callback_url", moveTaskCallBackUrl); // (-----------------重要---------------)处理完成后，调用的回调地址。（暂时不填）

		Map<String, Object> inputMap = new HashMap<String, Object>();
		// String moveTaskDestSectionId =
		// configurationService.getDirectPassing_movetaskdestsectionid();
		inputMap.put("dest_section_id", tarpathid); // (-----------------重要---------------)迁移任务的目标版块ID，由公有云迁移到私有云，为固定值。如：
		inputMap.put("continue_on_error", false); // 部分文件失败是否继续任务，否
		inputMap.put("action_if_exists", 1); // 目标文件已存在应该采取的操作,1:覆盖

		List<Map<String, Object>> transferList = new ArrayList<Map<String, Object>>();
		Map<String, Object> transfer = new HashMap<String, Object>();
		// ftp://cdvcloudmtv:mtv1233qcv@121.43.168.180/www/New_media/2016-1-25/votes/fcwr/info.txt
		transfer.put("src_file", url); // (-----------------重要---------------)源文件HTTP、共享或FTP全路径地址
		transfer.put("dest_file", destFile); // 目标文件路径，dest_section_id指向了根目录，dest_file定义了存储目录结构
		transferList.add(transfer);
		inputMap.put("transfers", transferList);
		params.put("input", inputMap);
		return params;
	}

	private String getNTM(String name, String destFile, String backUrl, String otherid, String tarpathaddr) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> srcSystem = new HashMap<String, Object>();
		srcSystem.put("name", "srcSystem");
		srcSystem.put("value", "CDVcloud");
		list.add(srcSystem);
		Map<String, Object> clipType = new HashMap<String, Object>();
		clipType.put("name", "clipType");
		clipType.put("value", "高清");
		list.add(clipType);
		Map<String, Object> catalogue = new HashMap<String, Object>();
		catalogue.put("name", "catalogue");
		catalogue.put("value", "CDVcloud");
		list.add(catalogue);
		Map<String, Object> caption = new HashMap<String, Object>();
		caption.put("name", "caption");
		caption.put("value", name);
		list.add(caption);
		Map<String, Object> notifyBackUrl = new HashMap<String, Object>();
		notifyBackUrl.put("name", "notifyBackUrl");
		notifyBackUrl.put("value", backUrl);
		list.add(notifyBackUrl);
		Map<String, Object> cdv_taskid = new HashMap<String, Object>();
		cdv_taskid.put("name", "cdv_taskid");
		cdv_taskid.put("value", otherid);
		list.add(cdv_taskid);
		List<Map<String, Object>> filegroups = new ArrayList<Map<String, Object>>();
		Map<String, Object> video = new HashMap<String, Object>();
		video.put("video", tarpathaddr + destFile);
		filegroups.add(video);
		Map<String, Object> filegroupMap = new HashMap<String, Object>();
		filegroupMap.put("filegroups", filegroups);
		list.add(filegroupMap);
		params.put("params", list);
		return JsonUtil.writeMap2JSON(params);

	}

	@Override
	public void moveTaskCallBack(String strJson) {
		try {
			Map<String, Object> callback = JsonUtil.readJSON2Map(strJson);
			// 更新任务回调的状态 代码未实现
			String taskId = String.valueOf(callback.get("id"));
			// 根据返回值更新迁移状态
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put(DirectPassing.TASKID, taskId);
			Map<String, Object> update = new HashMap<String, Object>();
			if (callback.containsKey("status")) {
				String status = String.valueOf(callback.get("status"));
				// 1成功
				if ("succeeded".equals(status)) {
					update.put(DirectPassing.STATUS, 0);
				} else if ("canceled".equals(status) || "failed".equals(status)) {
					update.put(DirectPassing.STATUS, 1);
				}
			} else {
				update.put(DirectPassing.STATUS, 1);
			}
			update.put(DirectPassing.ERRORMESSAGE, callback.get("message"));
			directPassingDao.updateManyBySet(filter, update, false);
		} catch (Exception e) {
			logger.error("更新推送日志出现错误，errMsg=[" + e.getMessage() + "].");
			e.printStackTrace();
		}
	}

	@Override
	public ResponseObject findDirectPassAll(CommonParameters commonPara, String strJson) {
		ResponseObject resObj;
		resObj = new ResponseObject(GeneralStatus.query_error.status, GeneralStatus.query_error.enDetail, "");
		try {
			// 解析json对象
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(strJson);
			// 获取业务参数,并进行相关业务操作
			Map<String, Object> whereMap = getVommonalityParam(commonPara, mapJson, DirectPassing.NAME, DirectPassing.CTIME);
			// 查询只有任务
			whereMap.put(DirectPassing.ISTASK, "0");
			if (!StringUtil.isEmpty(String.valueOf(mapJson.get(DirectPassing.DIRECTID)))) {
				// 根据路径id
				whereMap.put(DirectPassing.DIRECTID, mapJson.get(DirectPassing.DIRECTID));
			}
			// 排序参数
			Map<String, Object> sortMap = getSortParam(DirectPassing.CTIME, Constants.SZERO);
			// 分页参数
			int currentPage = getCurrentPage(mapJson);
			int pageNum = getPageNum(mapJson);
			List<Map<String, Object>> results = null;
			results = directPassingDao.find(sortMap, whereMap, currentPage, pageNum);
			// 获取总数
			long totalRecord = directPassingDao.countObject(whereMap);
			Map<String, Object> resMap = getPages(results, totalRecord, currentPage, pageNum);
			executeSuccess(resObj, resMap);
		} catch (Exception e) {
			logger.error("获取媒体直传列表失败，errMsg=[" + e.getMessage() + "].");
			e.printStackTrace();
		}
		return resObj;
	}

	/**
	 * 验证文件是否合规
	 * 
	 * @param insert
	 * @param url
	 * @return
	 */

	private boolean verdict(CommonParameters insert, String url) {
		boolean bo = true;
		try {
			Map<String, Object> analysis = analysisService.getAnalysis(insert, url);// 分析
			logger.info("直传分析文件analysis" + analysis);
			// -----------------------判断文件-----------------------
			// 判断文件是否分析出来，如果没分析出来，直接进入转码
			if (null != analysis && analysis.size() > 0) {
				Map<String, Object> whereMap = new HashMap<String, Object>();
				whereMap.put(MaterialTemplate.ISUSED, "0");

				Map<String, Object> verdict = materialTemplateService.findObject(insert, whereMap);
				// 判断筛选表
				if (null != verdict && verdict.size() > 0) {
					// 视频码率
					if (analysis.containsKey(Media.RATE)) {
						if (verdict.containsKey(MaterialTemplate.RATE) && !StringUtil.isEmpty(String.valueOf(verdict.get(MaterialTemplate.RATE)))) {
							double rate = Double.valueOf(String.valueOf(analysis.get(Media.RATE)));
							double verdict_rate = Double.valueOf(String.valueOf(verdict.get(MaterialTemplate.RATE)));
							if (rate < verdict_rate) {
								bo = false;
							}
						}
					}
					// 视频编码格式
					if (analysis.containsKey(Media.FORMAT)) {
						if (verdict.containsKey(MaterialTemplate.TYPE) && !StringUtil.isEmpty(String.valueOf(verdict.get(MaterialTemplate.TYPE)))) {
							String format = String.valueOf(analysis.get(Media.FORMAT));
							String verdict_format = String.valueOf(verdict.get(MaterialTemplate.TYPE));
							if (!(verdict_format.toUpperCase()).equals(format.toUpperCase())) {
								bo = false;
							}
						}
					}
					// 视频帧率
					if (analysis.containsKey(Media.FRAME)) {
						if (verdict.containsKey(MaterialTemplate.VFRAME) && !StringUtil.isEmpty(String.valueOf(verdict.get(MaterialTemplate.VFRAME)))) {
							double frame = Double.valueOf(String.valueOf(analysis.get(Media.FRAME)));
							double verdict_frame = Double.valueOf(String.valueOf(verdict.get(MaterialTemplate.VFRAME)));
							if (frame < verdict_frame) {
								bo = false;
							}
						}
					}
					// 封装格式
					if (analysis.containsKey(Media.FMT)) {
						if (verdict.containsKey(MaterialTemplate.FMT) && !StringUtil.isEmpty(String.valueOf(verdict.get(MaterialTemplate.FMT)))) {
							String fmt = String.valueOf(analysis.get(Media.FMT));
							String verdict_fmt = String.valueOf(verdict.get(MaterialTemplate.FMT));
							if (!(verdict_fmt.toUpperCase()).equals(fmt.toUpperCase())) {
								bo = false;
							}
						}
					}
					// 分辨率
					if (analysis.containsKey(Media.HEIGHT)) {
						if (verdict.containsKey(MaterialTemplate.HEIGHT) && !StringUtil.isEmpty(String.valueOf(verdict.get(MaterialTemplate.HEIGHT)))) {
							String height = String.valueOf(analysis.get(Media.HEIGHT));
							String verdict_height = String.valueOf(verdict.get(MaterialTemplate.HEIGHT));
							if (!verdict_height.equals(height)) {
								bo = false;
							}
						}
					}
					if (analysis.containsKey(Media.WIDTH)) {
						if (verdict.containsKey(MaterialTemplate.WIDTH) && !StringUtil.isEmpty(String.valueOf(verdict.get(MaterialTemplate.WIDTH)))) {
							String width = String.valueOf(analysis.get(Media.WIDTH));
							String verdict_width = String.valueOf(verdict.get(MaterialTemplate.WIDTH));
							if (!verdict_width.equals(width)) {
								bo = false;
							}
						}
					}
					// 画面比例
					if (analysis.containsKey(Media.VEDIOSIZE)) {
						if (verdict.containsKey(MaterialTemplate.PROPORTION)
								&& !StringUtil.isEmpty(String.valueOf(verdict.get(MaterialTemplate.PROPORTION)))) {
							String vedioSize = String.valueOf(analysis.get(Media.VEDIOSIZE));
							String verdict_vedioSize = String.valueOf(verdict.get(MaterialTemplate.PROPORTION));
							if (!verdict_vedioSize.equals(vedioSize)) {
								bo = false;
							}
						}
					}
				}
			} else {
				bo = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bo = false;
		}
		return bo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void NTMCallBack(String strJson) {
		try {
			logger.info("strJson" + strJson);
			strJson = strJson.replaceAll("\\\\", "").replace("\"{", "{").replace("}\"", "}");
			Map<String, Object> jsonMap = JsonUtil.readJSON2Map(strJson);
			// notification_request
			Map<String, Object> notificationRequestMap = (Map<String, Object>) jsonMap.get("notification_request");
			List<Map<String, Object>> params = (List<Map<String, Object>>) notificationRequestMap.get("params");
			if (null != params && params.size() > 0) {
				String id = "";
				String result = "";
				for (Map<String, Object> map : params) {

					if (map.containsValue("cdv_taskid")) {
						id = String.valueOf(map.get("value"));
					}
					logger.info("map.containsValue(result)" + map.containsValue("result") + "String.valueOf(map.get(value))"
							+ String.valueOf(map.get("value")));
					if (map.containsValue("result")) {
						result = String.valueOf(map.get("value"));
					}
				}
				// 数据入库
				Map<String, Object> filter = new HashMap<String, Object>();
				filter.put(DirectPassing.OTHERID, id);
				Map<String, Object> update = new HashMap<String, Object>();
				update.put(DirectPassing.OTHERSTATUS, result);
				directPassingDao.updateManyBySet(filter, update, false);
				List<Map<String, Object>> maps = directPassingDao.findObjectAll(filter, 1, 10);
				Map<String, Object> directPassingMap = maps.get(0);
				String status = ("0".equals(result)) ? "成功" : "失败";
				weChatService.pushDirectWx(directPassingMap, status);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ntm回调出现错误" + e.getMessage());
		}
	}

}
