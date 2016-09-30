package com.cdvcloud.rms.service;

import java.util.List;
import java.util.Map;

import com.cdvcloud.rms.common.CommonParameters;
import com.cdvcloud.vo.MediasVo;

public interface IFastCodeService {
	public String getXml(CommonParameters common,Map<String, Object> map);
	public String saveFastcode(CommonParameters common,String fastXml,String fileName ,String formatSample,String fixedInfo,String from);
	public String addFastcode(CommonParameters common,String catalogType,String data ,String name,String from);
	public String updateFastcode(CommonParameters common,String catalogType,String data ,String fileKEY,String from);
	public String deleteFastcode(CommonParameters common,String catalogType,String fileKEY ,String from);
	
	
	public MediasVo medias(List<Map<String, Object>> list,String catalogType,Long validTotal,String pageNo,Integer pageSize); 
}
