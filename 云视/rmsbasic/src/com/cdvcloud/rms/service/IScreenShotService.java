package com.cdvcloud.rms.service;


import com.cdvcloud.rms.common.CommonParameters;

public interface IScreenShotService {
	/**
	 * 首帧截图
	 * @param videoUrl     文件地址
	 * @param fixedInfo    固定参数
	 * @param resolution   图片分辨率
	 * @return
	 */
	public String getShotByFirst(CommonParameters common,String videoUrl,String fixedInfo,String resolution);
	/**
	 * 定量截图
	 * @param videoUrl    文件地址
	 * @param count       截图数量
	 * @param fixedInfo   固定参数
	 * @param resolution  图片分辨率
	 * @return
	 */
	public String getShotByCount(CommonParameters common,String videoUrl,String count,String fixedInfo,String resolution,String historicalTaskId);
	
	public String addScreenShotAutomatic(CommonParameters common,
			String name ,String mtype,String filePath,String src,String remark,String taskId,String md5);
	
	public boolean addScreenShotAutomatic(CommonParameters common,
			String name ,String mtype,String filePath,String src,String remark,String taskId,String md5,String uploadUUID);
}
