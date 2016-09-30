//审核信息
function dealStatus(data){
	var statusDiv="";
	if(data.status==3){
		statusDiv='<i class="chulizhong">待审核</i>';
	}else if(data.status==5||data.status==8){
		statusDiv='<i class="keyong">审核通过</i>';
	}else if(data.status==6){
		statusDiv='<i class="shibai">审核打回</i>';
	}
//	else if(data.status==8){
//		statusDiv='<i class="keyong">已推送</i>';
//	}
	return statusDiv;
}
//获取缩略图
function getThumbnailurl(data){
	var thumbnailurl="";
	if(data.thumbnailurl==undefined||data.thumbnailurl=="undefined"||data.thumbnailurl==""){
//		if(data.videos!=undefined){
//			thumbnailurl=ctx+"/common/images/img1.png";
//		}else if(data.audios!=undefined){
//			thumbnailurl=ctx+"/common/images/img1.png";
//		}else{
//			thumbnailurl=ctx+"/common/images/wengao_img.png";
//		}
		if(data.audios!=undefined&&data.audios.length>0){
			thumbnailurl="<div class='list_img_div  fl'><img src='"+ctx+"/common/images/audio.png'>";
		}else{
			thumbnailurl="<div class='list_img_div noimg_div fl'>";
		}
	}else{
		thumbnailurl="<div class='list_img_div  fl'><img src='"+data.thumbnailurl+getMaterialResolution()+"'>" ;
	}
	return thumbnailurl;
}
//获取时长 视频音频和图片数量
function getAVPinfo(data){
	var duration=0;
	var avCount=0;
	var pCount=0;
	if(data.videos!=undefined&&data.videos.length>0){
		$.each(data.videos,function(i,item){
			if(item.duration!=undefined){
				duration+=item.duration;
			}
			avCount++;
		})
	}
	if(data.audios!=undefined&&data.audios.length>0){
		$.each(data.audios,function(i,item){
			if(item.duration!=undefined){
				if(duration==0){
					duration+=item.duration;
				}
			}
		
			avCount++;
		})
	}
	if(data.pics!=undefined&&data.pics.length>0){
		$.each(data.pics,function(i,item){
			pCount++;
		})
	}
	return {"duration":duration,"avCount":avCount,"pCount":pCount};
}
function getIcon(data){
	if(data.videos!=undefined&&data.videos.length>0){
		var icon='<i><b class="video_icon"></b></i>';
	}else if(data.audios!=undefined&&data.audios.length>0){
		var icon='<i><b class="audio_icon"></b></i>';
	}else if(data.pics!=undefined&&data.pics.length>0){
		var icon='<b class="img_icon"></b>';
	}else {
		var icon='';
	}
	return icon;
}
//new图标显示与否
function newOrNot(data){
	var newOrnot="";
	$.each(data.checkuser,function(i,item){
		if(item.userId==userId){
			if(item.readstatus!=1){
				newOrnot= '<div class="pos_new"></div>'
			}
		}
	})
	return newOrnot;
}

function newOrNotShare(data){
	var newOrnot="";
	$.each(data.shareuser,function(i,item){
		if(item.userId==userId){
			if(item.readstatus!=1){
				newOrnot= '<div class="pos_new"></div>'
			}
		}
	})
	return newOrnot;
}

function checkShareRead(data){
	var read=0;
	$.each(data.shareuser,function(i,item){
		if(item.userId==userId){
			if(item.readstatus!=1){
				read=1
			}
		}
	})
	return read;
}

function checkCheckRead(data){
	var read=0;
	$.each(data.checkuser,function(i,item){
		if(item.userId==userId){
			if(item.readstatus!=1){
				read=1
			}
		}
	})
	return read;
}