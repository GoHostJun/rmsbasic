// global.js 提供了全局方法和变量.
/*获取上下文路径-开始*/
var pathValue = getPathValue();
var pathPage = getPathPage();
function init() {
	// video小图标位置 视音频、素材信息的样式
	var icon_w = $(".video_audio_div .img_div i").width();
	$(".video_audio_div .img_div i").css({
		"margin-left" : -icon_w / 2
	});
	// 素材下载、通联创建 的样式
	var big_icon_w = $(".tonglian_title .title_img i").width();
	$(".tonglian_title .title_img i").css({
		"margin-left" : -big_icon_w / 2
	});
	// 文稿、素材、通联 的列表信息样式
	var sobig_icon_w = $(".news_div .list_img_div i").width();
	$(".news_div .list_img_div i").css({
		"margin-left" : -sobig_icon_w / 2
	});

	// 分页
	$("ul.page li a").click(function() {
		$("ul.page li a").removeClass("active");
		$(this).addClass("active");
	})

	// checkbox选中
	$(".news_div .choice_icon").click(function() {
		$(this).toggleClass("active");
	})
	// 列表全选的样式
	$(".list_cont .check_p .choice_all").click(function() {
		var flag = $(this).attr("flag");
		if (flag != "1") {
			$(this).addClass("active");
			$(".news_div .choice_icon").addClass("active");
			$(this).attr("flag", "1");
		} else if (flag != "2") {
			$(this).removeClass("active");
			$(".news_div .choice_icon").removeClass("active");
			$(this).attr("flag", "2");
		}
	})

	// 头部导航下拉列表
	$('.nav_div a').click(function() {
		$('.nav_div a').removeClass("active").attr("flag", "1");
		$(this).addClass("active").attr("flag", "2");
	});
	$('.down_list>li a').click(function() {
		$(this).find('li').addClass('hold');
		$(this).siblings().find('li').removeClass('hold');
	});
	// 点击列表内元素，导航加样式
	var list_menu1 = $('.nav_div .i1').parent('a')
	var list_menu2 = $('.nav_div .i2').parent('a')
	var list_menu4 = $('.nav_div .i4').parent('a')
	$('.down_list>li:eq(0) a').click(function() {
		$('.nav_div a').removeClass("active").attr("flag", "1");
		list_menu1.addClass("active").attr("flag", "2");
		$('.down_list>li:eq(1) li,.down_list>li:eq(2) li').removeClass('hold')
	})
	$('.down_list>li:eq(1) a').click(function() {
		$('.nav_div a').removeClass("active").attr("flag", "1");
		;
		list_menu2.addClass("active").attr("flag", "2");
		$('.down_list>li:eq(0) li,.down_list>li:eq(2) li').removeClass('hold')// 点击列表2中信息移除列表1中样式
	})
	$('.down_list>li:eq(2) a').click(function() {
		$('.nav_div a').removeClass("active").attr("flag", "1");
		;
		list_menu4.addClass("active").attr("flag", "2");
		$('.down_list>li:eq(0) li,.down_list>li:eq(1) li').removeClass('hold')// 点击列表2中信息移除列表1中样式
	})
	// 移除点击其他导航时列表中去除样式
	$('.nav_div a').click(function() {
		$('.down_list>li li').removeClass('hold')
	})
	// 点击导航，列表第一个变色
	$('.nav_div .i1').parent('a').click(function() {
		$('.down_list>li:eq(0) li:eq(0)').addClass('hold');
		$('.down_list>li:eq(1) li,.down_list>li:eq(2) li').removeClass('hold')// 移除第二列中表被选中的
	});
	$('.nav_div .i2').parent('a').click(function() {
		$('.down_list>li:eq(1) li:eq(0)').addClass('hold');
		$('.down_list>li:eq(0) li,.down_list>li:eq(2) li').removeClass('hold')// 移除第一列表中被选中的
	});
	$('.nav_div .i4').parent('a').click(function() {
		$('.down_list>li:eq(2) li:eq(0)').addClass('hold');
		$('.down_list>li:eq(0) li,.down_list>li:eq(1) li').removeClass('hold')// 移除第一列表中被选中的
	});

	// 鼠标滑过菜单
	var list_menu1 = $('.nav_div .i1').parent('a')
	var list_down1 = $('.down_list>li:eq(0)')
	var list_menu2 = $('.nav_div .i2').parent('a')
	var list_down2 = $('.down_list>li:eq(1)')
	var list_menu4 = $('.nav_div .i4').parent('a')
	var list_down4 = $('.down_list>li:eq(2)')
	list_menu1.hover(function() {
		list_down1.show().hover(function() {
			$(this).show();
			list_menu1.addClass("active");
		}, function() {
			$(this).hide();
			if (list_menu1.attr("flag") == "2") {
			} else {
				list_menu1.removeClass("active");
			}

		});
	}, function() {
		list_down1.hide();
	});

	list_menu2.hover(function() {
		list_down2.show().hover(function() {
			$(this).show();
			list_menu2.addClass("active");
		}, function() {
			$(this).hide();
			if (list_menu2.attr("flag") == "2") {
			} else {
				list_menu2.removeClass("active");
			}
		});
	}, function() {
		list_down2.hide();
	});

	list_menu4.hover(function() {
		list_down4.show().hover(function() {
			$(this).show();
			list_menu4.addClass("active");
		}, function() {
			$(this).hide();
			if (list_menu4.attr("flag") == "2") {
			} else {
				list_menu4.removeClass("active");
			}
		});
	}, function() {
		list_down4.hide();
	});

	// 素材页素材筛选
	$(".material_choice span").click(function() {
		$(".material_choice span").removeClass("active");
		$(this).addClass("active");
		var a = $(this).html();
		$(".material_url .p_url_change").html(a);
	})

	// 通联查看页编辑按钮不可点击状态
	$(".check_cont .check_div h3 a.a_hui").attr({
		"href" : "javascript:;",
		"target" : "_self"
	});

	// 创建通联页面双击变色
	var auditing_man = $('.create_cont .auditing_man')
	var tonglian_man = $('.create_cont .tonglian_man')
	var ture = $('.create_cont .modify_left .ture_btn');
	var no = $('.create_cont .modify_left .no_btn');
	auditing_man.dblclick(function() {
		auditing_man.removeClass('active');
		tonglian_man.addClass('active');
		ture.addClass('active')
		no.removeClass('active')
	})
	tonglian_man.dblclick(function() {
		tonglian_man.removeClass('active');
		auditing_man.addClass('active');
		no.addClass('active')
		ture.removeClass('active')
	})
	no.click(function() {
		tonglian_man.removeClass('active');
		auditing_man.addClass('active');
	})
	ture.click(function() {
		auditing_man.removeClass('active');
		tonglian_man.addClass('active');
	})

	// 创建通联单选框
	$(".create_cont .make_sel i").click(function() {
		$(this).addClass('active');
		$(this).siblings().removeClass("active")
	});
	// 修改通联右侧导航收起
	$('.menu1,.menu2').click(function() {
		$(this).find('i').toggleClass('active');
		$(this).show();
		$(this).siblings().toggle();
	})

	// 文稿引用单选框
	$(".news_div .choiceone_icon").click(function() {
		$(".news_div .choiceone_icon").removeClass("active");
		$(this).addClass("active");
	})

	//全局提示框
	toastr.options = {
		  "closeButton": true,
		  "debug": false,
		  "progressBar": true,
		  "positionClass": "toast-top-right",
		  "showDuration": "400",
		  "hideDuration": "1000",
		  "timeOut": "5000",
		  "extendedTimeOut": "1000",
		  "showEasing": "swing",
		  "hideEasing": "linear",
		  "showMethod": "fadeIn",
		  "hideMethod": "fadeOut"
    }
	initIndex();
	getUserInfo();
}

/**
 * 本页面更新content的内容
 * 
 * @param pagePath
 */
function uCont(pagePath) {
	var index = layer.load();
	$.ajax({
		type : 'POST',
		url : ctx + pathPage + pagePath,
		cache : false,
		success : function(data) {
			layer.close(index);
			//除去js云通联的首页样式修改为隐藏，所以切换时候要显示
			$("#footer").show();
			$("#content").html(data);
		},
		error : function() {
			layer.close(index);
		}
	});
}

/**
 * 获取公共参数信息
 * 
 * @returns {String}
 */
function getPathValue() {
	return "/"+consumerId+"/ytl/"+userId+"/566fd73c84e353224410c0b6/v1/api/";
}

/**
 * 获取公共参数信息(跳转路径)
 * 
 * @returns {String}
 */
function getPathPage() {
	return "/"+consumerId+"/ytl/"+userId+"/566fd73c84e353224410c0b6/v1/rms/";
}


/**
 * 打开一个新标签，跳转到指定页面
 * 
 * @param pagePath
 */
function toPage(pagePath) {
	window.open(ctx + pathPage + pagePath);
}

/**
 * 获取项目的访问路径(只有html开发的时候使用)
 * 
 * @returns {String}
 */
function getPath() {
	var location = (window.location + '').split('/');
	return location[0] + '//' + location[2] + '/' + location[3];
}
/* 获取上下文路径-结束 */

/**
 * 回到首页
 */
function toHome() {
	window.location = ctx + "/index.jsp";
}
/**
 * Created by yumingjun on 2015/12/9. 将数字转化为小时时分秒
 */
function MillisecondToDate(msd) {
	var time = parseFloat(msd) / 1000;
	if (null != time && "" != time) {
		if (time > 60 && time < 60 * 60) {
			time = "00:" + foramt(parseInt(time / 60.0)) + ":" + foramt(parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60)) + "";
		} else if (time >= 60 * 60 && time < 60 * 60 * 24) {
			time = foramt(parseInt(time / 3600.0) + ":" + foramt(parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)))
					+ ":"
					+ foramt(parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) - parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60))
					+ "";
		} else {
			time = "00:00:" + foramt(parseInt(time)) + "";
		}
	}
	return time;
}
// 数字小于10加0
function foramt(num) {
	return num < 10 ? ("0" + num) : num;
}

/**
 * 将加载的页面显示到指定div中
 * 
 * @param pagePath
 * @param divName
 */
function uContDiv(pagePath, divName) {
	var index = layer.load();
	$.ajax({
		type : 'POST',
		url : ctx + pathPage + pagePath,
		cache : false,
		success : function(data) {
			layer.close(index);
			$("#" + divName).html(data);
		},
		error : function() {
			layer.close(index);
		}
	});
}

/**
 * 客户管理用户请求不需要公共参数
 * 
 * @param pagePath
 * @param divName
 */
function uCustomDiv(pagePath, divName) {
	var index = layer.load();
	$.ajax({
		type : 'POST',
		url : ctx  + pagePath,
		cache : false,
		success : function(data) {
			layer.close(index);
			$("#" + divName).html(data);
		},
		error : function() {
			layer.close(index);
		}
	});
}

/**
 * 打开一个层
 * 
 * @param utitle
 *            标题
 * @param udata
 *            要显示的数据信息
 */
function openPop(utitle, udata) {
	layer.open({
		type : 1, // page层
		area : [ '600px' ],
		title : utitle,
		shade : 0.5, // 遮罩透明度
		moveType : 1, // 拖拽风格，0是默认，1是传统拖动
		shift : 0, // 0-6的动画形式，-1不开启
		maxmin : true,
		scrollbar : false,
		content : udata
	});
}

/**
 * 注销登录
 */
function doLogout() {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	var logouturi = ctx + "/rms/user/logout/";
	$.ajax({
		url : ctx +"/rms/user/logout/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data.code == 0) {
				window.location=doLogoutUrl;
			}
		},
		error : function(e){}
	});
}

function getUserInfo() {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("userId", userId);
	$.ajax({
		url : ctx + pathValue + "org/getUserByUserId/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data.code == 0) {
				headImgEmail=data.data.email
				if(!data.data){
					$("#head_img_b").html(uname.substring(uname.length-2,uname.length));
					return ;
				}
				var departmentList = data.data.department;
				var jigou = "";
				if(departmentList != undefined && departmentList.length > 0){
					for(var i=0;i<departmentList.length;i++){
						jigou += data.data.department[0].name + " ";
					}
				}
				$("#userNameId").val(data.data.name);
				$("#gonghao").html(data.data.empNo);
				$("#phoneId").val(data.data.phone);
				$("#emailId").html(cutStr(data.data.emailaddress,17,"..."));
				//$("#emailId").attr("title",data.data.email);
				$("#jiGouId").html(jigou);
				//$("#head_img_b1").html(data.data.name.substring(data.data.name.length-2,data.data.name.length));
				//当前头像颜色---
				//$(".head_img #head_img_b1").css("background",data.data.headcolour);
			//	$("#head_img_b").css("background",data.data.headcolour);
				//微信绑定状态
				if(data.data.weChatUnionid!=undefined&&data.data.weChatUnionid!=""){
					$("#weChatBind").addClass("active");
					$("#weChatBind").text("已绑定");
					$("#weChatNotBind").text("解绑");
					$("#weChatNotBind").removeClass("active");
					$("#weChatNotBind").unbind("click");
					$("#weChatNickName").text("微信昵称："+data.data.weChatNickName);
					$("#weChatNotBind").bind("click",function(){
						unbindWeixin();
					})
				}else{
					$("#weChatNotBind").addClass("active");
					$("#weChatBind").removeClass("active");
					$("#weChatBind").text("未绑定");
					$("#weChatNotBind").text("绑定");
					$("#weChatNotBind").unbind("click");
					$("#weChatNotBind").bind("click",function(){
						openWeixin();
					})
					$("#weChatNickName").remove();
				}
				//qq绑定状态
				if(data.data.qqOpenid!=undefined&&data.data.qqOpenid!=""){
					$("#qqBind").addClass("active");
					$("#qqBind").text("已绑定");
					$("#qqNotBind").text("解绑");
					$("#qqNotBind").removeClass("active");
					$("#qqNotBind").unbind("click");
					//$("#qqNickName").text("qq昵称："+data.data.qqNickName);
					$("#qqNotBind").bind("click",function(){
					//	unbindqq();
					})
					
				}else{
					$("#qqNotBind").addClass("active");
					$("#qqBind").removeClass("active");
					$("#qqBind").text("未绑定");
					$("#qqNotBind").text("绑定");
					$("#qqNotBind").unbind("click");
					$("#qqNotBind").bind("click",function(){
					//	openqq();
					})
					$("#qqNickName").remove();
				}
				if(data.data.smallhead&&data.data.smallhead!=""){
					$(".head_img #head_img_b1").css("background-image","url("+data.data.smallhead+")");
					$("#head_img_b").css("background-image","url("+data.data.smallhead+")");
				}else{
					$("#head_img_b").html(data.data.name.substring(data.data.name.length-2,data.data.name.length));
					$("#head_img_b1").html(data.data.name.substring(data.data.name.length-2,data.data.name.length));
					//当前头像颜色---
					$("#head_img_b").css("background-image",data.data.headcolour==undefined?"":"#5cb1e7");
					$(".head_img #head_img_b1").css("background-image",data.data.headcolour==undefined?"":"#5cb1e7");
				}
			}
		},
		error : function(e){}
	});
}

/**
 * 修改保存
 */
function saveUserInfo() {
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("txtName", $("#userNameId").val());
	onairMap.put("txtPhone", $("#phoneId").val());
	onairMap.put("emailAddress", $("#emailId").val());
	onairMap.put("userId", userId);
	onairMap.put("txtColour", $(".choice_color .active").attr("title"));     //选择的头像颜色
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/updateUserByUserId/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				toastr.success("修改信息成功!");
				layer.closeAll('page');
				$("#user_menu_span").html($("#userNameId").val());
			//	alert(data.data.smallhead)
//				if(data.data.smallhead!=undefined&&data.data.smallhead!=""&&data.data.smallhead!="undefined"){
//				}else{
//					$("#head_img_b").html($("#userNameId").val().substring($("#userNameId").val().length-2,$("#userNameId").val().length));
//					
//				}
			} else {
				toastr.error("修改信息失败!");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("修改信息失败!");
		}
	});
}

/**
 * 获取授权令牌
 * 
 * @returns {String}
 */
function getAccessToken() {
	return accessToken;
}

/**
 * 获取时间戳
 * 
 * @returns
 */
function getTimeStamp() {
	return new Date().getTime()+"";
}

function initIndex() {
    //用户下拉
    	var listDown=$('.header .user_div .user_pop');
    	$('#user_menu').click(function(){
    	listDown.toggle();
        });
   	    listDown.hover(function(){
    	},function(){
    	$(this).hide();
        });
    	listDown.find('li').click(function(){
    		listDown.hide();
    	}); 	
    //用户信息弹窗
    	var user_popup=$('.user_message_popup').hide();
    	$('.close_btn').click(function(){
    	user_popup.hide()
    	})
    	$('#personal_m').click(function(){
    		getMyScore();
    		getUserInfo();
    		user_popup.show()
    	})
    	$('#save_id').click(function(){
    	//	saveUserInfo();
    		user_popup.hide();
    	})
    	$('#reset_id').click(function(){
    		user_popup.hide();
    	})
}

/**
 * 字符串截取 （"aaaa",12,"...."）
 * @param str
 * @param len
 * @param suffix
 * @returns
 */
function cutStr(str,len,suffix){  
  	if(!str) return "";  
  	if(len<= 0) return "";  
    if(!suffix) suffix = "";  
   	var templen=0;
   	if(str.length<=len){
   		return str;  
   	}
   	for(var i=0;i<str.length;i++){  
           templen++  
        if(templen == len){  
           return str.substring(0,i+1)+suffix;  
        }else if(templen >len){  
           return str.substring(0,i)+suffix;  
        }  
    }  
    return str;  
}
/**
 * 字符串截取 （"aaaa",12,"...."）一个汉字代表2个字节
 * @param str
 * @param len
 * @param suffix
 * @returns
 */
//title = cutString(title,15,"...");
function cutTitleString(str,len,suffix){  
  	if(!str) return "";  
  	if(len<= 0) return "";  
    if(!suffix) suffix = "";
   	var templen=0;  
   	for(var i=0;i<str.length;i++){  
        if(str.charCodeAt(i)>255){  
           templen+=2;  
       	}else{  
           templen++  
        }  
    }  
   	if(templen<len){
   		return str;  
   	}else{
   		templen=0;
   		for(var i=0;i<str.length;i++){  
   	        if(str.charCodeAt(i)>255){  
   	           templen+=2;  
   	       	}else{  
   	           templen++  
   	        }  
   	       
   	        if(templen == len){  
   	           return str.substring(0,i+1)+suffix;  
   	        }else if(templen >len){  
   	           return str.substring(0,i)+suffix;  
   	        }  
   	    }  
   	}
    return str;  
} 
/**
 * 简介长文本显示截取字符用
 * @param jianjie
 * @param wordFirstCutNum 换行前截取的字符数
 * @param wordTotalCutNum 总共需要截取的字符数
 * @returns
 */
function jianjie(jianjie,wordFirstCutNum,wordTotalCutNum){
	if(jianjie.length<=wordFirstCutNum-1){
		return jianjie.substring(0,wordFirstCutNum)
	}else{
		if(jianjie.length<=wordTotalCutNum){
			return 	jianjie.substring(0,wordFirstCutNum)+"<br>"+jianjie.substring(wordFirstCutNum,jianjie.length);
		}else{
			return 	jianjie.substring(0,wordFirstCutNum)+"<br>"+jianjie.substring(wordFirstCutNum,wordTotalCutNum)+"...";
			
		}
	}
}
/**
 * 对导航菜单的选中状态进行重新渲染
 * @param navDivIndex 第一级导航菜单的序号 从0开始
 * @param downListIndex 第二季菜单序号 从0开始，注意html的li结构序号！
 */
function navChangeClass(navDivIndex,downListIndex){
	$(".nav_div a").eq(navDivIndex).addClass("active");
	$(".nav_div a").eq(navDivIndex).attr("flag",2);
    $(".nav_div a").eq(navDivIndex).siblings().removeClass("active");
    $(".nav_div a").eq(navDivIndex).siblings().attr("flag",1);
    $(".down_list li:eq("+downListIndex+")").addClass("hold");
    $(".down_list li:eq("+downListIndex+")").siblings().removeClass("hold");
	    $(".down_list li").each(function(i,item){
		   if(i!=downListIndex){
			  $(this).removeClass("hold");
		   }
	    })
}

function navJXChangeClass(navDivIndex,downListIndex){
	$(".jx_nav li").each(function(i,item){
		if(i==navDivIndex||i==downListIndex){
			$(this).addClass("active")
		}else{
			$(this).removeClass("active")
		}
	})
	
}

/**
 * 打开顶部更新密码页面
 */
function pwdUpdateOpen() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=organize/user/pwd_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("修改密码", data);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}
/**
 * ajax的进一步封装
 * @param ele 当前对象this
 * @param options ajax参数
 * @returns
 */
var $ajax=function(ele,options){
	 this.$ele=ele;
	 this.defaults={
			url:'',
			dataType:'json',
			headers:{'Content-Type': 'application/json'},
			type:'POST',
			data:null,
			success:null,
			error:null
	};
	this.settings=$.extend({},this.defaults,options);
	var that=this;
	$.ajax({
       url:that.settings.url, //服务器的地址
       dataType:that.settings.dataType, //返回数据类型
       headers: that.settings.headers,
       type: that.settings.type, //请求类型
       data:that.settings.data,
       success:function(data){
    	   that.settings.success(data);
	    },
	   error : function(){
		   that.settings.error;
	   }
	});
};

/**
 * 加载地区列表
 */
function areaMap() {
	var keyWord = $("#title").val();
	if (!keyWord) {
		keyWord = "";
	}
	var page;
	var pageNum;
	page=1;
	pageNum=100;
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	var resultMap=new OnairHashMap();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/getAreasByAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			if (0 == data.code) {
				$.each(data.data.results,function(i,item){
					resultMap.put(item.code,item.tvStation);
				})
			} else {
				toastr.error("查询供片台失败");
			}
		},
		error : function() {
			 toastr.error("查询供片台失败");
		}
	});
	return resultMap._entry;
}

function getMaterialResolution(){
	return "@320w_100q.src";
}
var tempURL = window.location.href;
if(tempURL.indexOf("jsessionid") > 0){
	window.location.href = tempURL.split(";jsessionid")[0];
}

/*$(function(){
	
	if(getUrlConfig.nameTV=="JS"){
		$("#indexJX").remove();
	}else{
		$("#indexPop").remove();
		
	}
})*/

