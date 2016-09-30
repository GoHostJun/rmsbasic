package com.cdvcloud.rms.web.api;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.Pages;
import com.cdvcloud.rms.common.ResponseObject;
import com.cdvcloud.rms.domain.Logs;
import com.cdvcloud.rms.service.ISystemLogService;
import com.cdvcloud.rms.util.DateUtil;
import com.cdvcloud.rms.util.ExcelUtil;
import com.cdvcloud.rms.util.JsonUtil;

/**
 * 导出
 * @author syd
 *
 */
@Controller
@RequestMapping(value = "{companyId}/{appCode}/{userId}/{serviceCode}/{versionId}/api/export")
public class ExportController {
	
	private static Logger logger = Logger.getLogger(ExportController.class);
	
	@Autowired
	private ISystemLogService systemLogService;
	
	/**
	 * 将日志导出为Excel，只导出前2w条
	 * @param request
	 * @throws IOException 
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "exportLogs/")
	public void exportLogs(@PathVariable String companyId,
			@PathVariable String appCode,@PathVariable String userId,
			@PathVariable String serviceCode,@PathVariable String versionId,
			@Valid CommonParameters query,
			@RequestParam("strJson") String strJson,HttpServletRequest request, HttpServletResponse response) throws IOException{
			OutputStream os = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);
			try {
			//校验参数
		    String str = "日志列表"+ DateUtil.currentDateTimeStr();	
		   
		    String filename =str+".xlsx";
			Map<String,Object> map = new HashMap<String, Object>();
			map.put(Logs.ACTION, "标题");
			map.put(Logs.LOGDESC, "描述");
			map.put(Logs.CTIME, "创建时间");
			map.put(Logs.CUSENAME, "创建人");
			map.put(Logs.IP, "IP");
			Map<String, Object> jsonMap =  JsonUtil.readJSON2Map(strJson);
			Map<String, Object> columnWidthsMap=(Map<String, Object>) jsonMap.get("columnWidths");
			String titles =Logs.ACTION+","+Logs.LOGDESC+","+Logs.CTIME+","+Logs.CUSENAME+","+Logs.IP;
			
			//查询数据
			ResponseObject responseObject = systemLogService.query(query,strJson);
			Pages pages = (Pages) responseObject.getData();
			List<Map<String, Object>> maps = (List<Map<String, Object>>) pages.getResults();
			//生成excel
			ExcelUtil  excelUtil = new ExcelUtil();
			byte[] excelData = excelUtil.createNewExcel(maps, titles, map,columnWidthsMap,str);
			response.setHeader("Content-disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "ISO8859-1" ));
			bos.write(excelData);
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("日志查询接口错误："+e);
		}finally{
			if (bos != null) {
				bos.close();
			}
		}
	}
	
	
}
