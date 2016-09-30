// JavaScript Document


$(function(){
	//初始化
	initDoc();
	//服务选择
	$(".service_cont .service_div").click(function(){
		$(this).toggleClass("active");	
	})
	
	//提交要求
	$("#sub").click(function(){
	
//		Vanadium.loadVanadium();
//		if(!Vanadium.validForm("form"))return;
		
		var joinjson=new Array();
		var choseI=0;
		 $(".service_div").each(function (i) {
			 if($(this).hasClass("active")){
				 if(i==0){
					 choseI++;
					joinjson.push({"userId":"568e15baeeb14b70c7d0833e","realname":"字幕服务组","position":"通联人"})
		         }
				 if(i==1){
					 choseI++;
					joinjson.push({"userId":"568e15d9eeb14b70c7d0833f","realname":"配音服务组","position":"通联人"})
		         }
				if(i==2){
					 choseI++;
					joinjson.push({"userId":"568e1601eeb14b70c7d08340","realname":"剪辑服务组","position":"通联人"})
				}
			 }
	         
	     });
		 if(choseI==0){
			 toastr.warning("请至少选择一种服务");
			 return ;
		 }
		 var index = layer.load();
		 var content=$("#require").val();
		 content = $("#require").text(content).html();
		var strJson={"joinjson":joinjson,"othermsg":content,"catalogueId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
		strJson= JSON.stringify(strJson);
		$.ajax({
			url : ctx + pathValue+"news/createService/",
			headers:{"Content-Type":"application/json"},
			data : strJson,
			type : "post",
			cache : false,
			dataType : "json",
			success : function(response) {
				layer.close(index);
				var code = response.code;
				var data = response.data;
				if(code == 0){
					toastr.success("服务创建成功！");
					$("#require2").html("要求："+content);
					$(".requir_div").hide();
					$(".requir_succ_div").show();
					 //uCont('news/toNewsMyCreateList/');
				}else{
					toastr.error("服务创建失败，请重试！");
					return;
				}
			},
			error : function() {
				layer.close(index);
				toastr.error("服务创建失败，请重试！");
				return;
			}
		});
	})
	
	$("#require").keyup(function(){
		$("#requireCount").text($(this).val().length);
	})
	
})
function initDoc(){
	 var index = layer.load();
	 var jsonStr={"keyWord":$("#searchInput").val(),"id":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	 jsonStr= JSON.stringify(jsonStr);
	 
	 $.ajax({
	        url:ctx+pathValue+"presentation/queryById/", //服务器的地址
	        dataType:'json', //返回数据类型
	        headers: {'Content-Type': 'application/json'},
	        type:'POST', //请求类型
	        data:jsonStr,
	        success:function(data){
		        layer.close(index);
		       	if(data.code==0){
		       		var data=data.data.result;
		       		var duration=getAVPinfo(data).duration;
		    		var durationDate="";
		    		if(duration!=0){
		    			durationDate=MillisecondToDate(duration)
		    		}
		    		$('#docDiv').html('');
		       		$('#docDiv').append(
		       				'<div class="inner">'+
		       		        '<a href="'+ctx+pathPage+'material/toViewDocs/?docId='+data._id+'" target="_blank">'+
		       		        ''+getImg(data,durationDate)+''+
		       		        '</a>'+
		       		       ' <div class="title_right">'+
		       		        	'<h4 title="'+(data.title==undefined?'':data.title)+'">'+cutStr((data.title==undefined?'':data.title),11,"...")+'</h4>'+
		       		            '<p style="margin:10px 0;">供片台：<span>'+(data.template.tvStationName==undefined?'':data.template.tvStationName)+'</span> 记者：<span>'+(data.template.reporter==undefined?'':data.template.reporter)+'</span>  关键词：<span>'+(data.template.keyWords==undefined?'':data.template.keyWords)+'</span> 分类：'+
		       		            '<span>'+(data.template.customType==undefined?'':data.template.customType)+'</span></p>'+
		       		            '<p style="margin:10px 0;">视音频：<span>'+getAVPinfo(data).avCount+'</span> 图片：<span>'+getAVPinfo(data).pCount+'</span> </p>'+
		       		            '<p class=\"jianjie_p\" style="margin:20px 0;" title="'+(data.template.content==undefined?'':data.template.content)+'">简介：<span>'+jianjie((data.template.content==undefined?'':data.template.content),46,76)+'</span></p>'+
		       		            '<p class="time"><b>更新时间：<span>'+(data.uutime==undefined?'':data.uutime)+'</span></b></p>'+
		       		       ' </div>'+
		       		        '<div class="clear"></div>'+
		       		    '</div>');
		       		//素材下载、通联创建圆圈居中的样式
		       		var big_icon_w = $(".tonglian_title .title_img i").width();
		       		$(".tonglian_title .title_img i").css({
		       			"margin-left" : -big_icon_w / 2
		       		});
				 }else{
					 toastr.error("查询失败");
				 }
		    },
			error : function(){
				layer.close(index);
				toastr.error("查询失败");
			}
		    
		});
}

function getImg(data,durationDate){
	var imgVar="";
	 if(data.thumbnailurl!=undefined){
		 imgVar= '<div class="title_img noimg_div ">'+
		 ''+getThumbnailurl(data)+''+
		 (durationDate==""?"":' <p>'+durationDate+'</p>')+
		 ' </div>'
	 }
	 return imgVar;
}

function getThumbnailurl(data){
	if(data.thumbnailurl=='undefined'||data.thumbnailurl==undefined||data.thumbnailurl==''){
		return  '<img src="'+ctx+'/common/images/wengao_img.png">';
	}else{
		return  '<img src="'+data.thumbnailurl+'"> <i></i>';
	}
	
}