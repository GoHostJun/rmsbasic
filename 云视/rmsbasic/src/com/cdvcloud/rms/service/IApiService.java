package com.cdvcloud.rms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.rms.common.ResponseObject;


public interface IApiService {

	public Map<String, Object> getFilednewsById(String id);
	
	public Map<String, Object> getMaterialSetById(String id);
	public String sendlog(String json);
	public List<Map<String, Object>> getscanTask(String json);
	public ResponseObject getCDVCueById( CommonParameters common,String json);
	public ResponseObject getZYCueById( CommonParameters common,String json);
	public ResponseObject getSBCueById( CommonParameters common,String json);
	public ResponseObject addClubTask( CommonParameters common,String json);
	public ResponseObject addDocsFromZY( CommonParameters common,String json);
	public void getOpenWX(String shareId,String type,HttpServletRequest request);
	public ResponseObject addlive(CommonParameters common);
	

}
