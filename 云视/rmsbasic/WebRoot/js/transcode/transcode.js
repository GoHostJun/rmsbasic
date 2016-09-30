$(function(){
	getTranscodeTemplets();
	/*转码模板*/
	var veConZm_sel=$("#veConZm_sel");
	var veConZmConD1_icon=$(".veConZmConD1_icon");
	//全选	
	veConZm_sel.click(function(e){
		e.stopPropagation();
		//改变自身状态
		$(this).children("i").toggleClass("active");
		if($(this).children("i").attr("class") == "fl active"){
		veConZmConD1_icon.find("i").addClass("active");
		}else{
			veConZmConD1_icon.find("i").removeClass("active");
		}
		});
	
	//选一个	
	veConZmConD1_icon.click(function(e){
		e.stopPropagation();
		//改变自身状态
		$(this).children("i").toggleClass("active");
		//改变父项的状态
		var childs4i = $("#template_list_id ul li .veConZmConD1_icon i");    //总共有多少个子项
		var childs4ai = $("#template_list_id ul li .veConZmConD1_icon .active");//有多少个被选择的子项
		if(childs4i != undefined && childs4ai != undefined){
			//所有子项全被选中了，则将父项置为全选中状态
			if(childs4i.length == childs4ai.length){  
				veConZm_sel.find("i").addClass("active");
			}else{
				veConZm_sel.find("i").removeClass("active");
			}
		}
		});
	
	var veConZm_cont=$(".veConZm_cont");
	veConZm_cont.find("li").hover(function(){		
		$(this).children(".veConZmCon_div1").hide();
		$(this).children(".veConZmCon_div2").show();
		},function(){
			$(this).children(".veConZmCon_div1").show();
			$(this).children(".veConZmCon_div2").hide();
			});
			
});

function getTranscodeTemplets(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "set/query/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var code = data.code;
			if(code != 0){
				toastr.error("查询失败，请重试！");
				return;
			}
			if(code == 0 && data.data.data.templates.length > 0){
				writeData(data.data.data.templates);
			}else{
				$("#template_list_id").append("<div class='no_data'></div>");
			}
			
		}
	});
}

	function writeData(results){
		var html = [];
		html.push("<ul>");
		$(results).each(function(i,item){
			//alert(item.templateName);
			html.push("<li>");
			html.push("<div class='veConZmConD1_icon'>");
			html.push("<i class=''></i>");			
			html.push("</div>");					
			html.push("<div class='veConZmCon_div1 veConZmCon_col1'>");			
			html.push("<div class='veConZmConD1_word'>");					
			html.push("<i></i>");						
			html.push("<p>PC标清</p>");					
			html.push("</div>");						
			html.push("</div>");		
			html.push("<div class='veConZmCon_div2 hide'>");	
			html.push("<p>PC标清</p>");				
			html.push("<table border=0 cellpadding=0 cellspacing=0>");						
			html.push("<tr>");				
			html.push("<td class='w1'>码率(kbps)</td>");							
			html.push("<td class='w2'>"+toString(item.vBitrate)+"</td>");							
			html.push("</tr>");								
			html.push("</tr>");									
			html.push("<td class='w1'>宽度</td>");						
			html.push("<td class='w2'>"+toString(item.width)+"</td>");						
			html.push("</tr>");							
			html.push("</tr>");	
			html.push("<td class='w1'>高度</td>");	
			html.push("<td class='w2'>"+toString(item.height)+"</td>");							
			html.push("</tr>");						
			html.push("<tr>");							
			html.push("<td class='w1'>编码格式</td>");							
			html.push("<td class='w2'>"+toString(item.vCodec)+"</td>");								
			html.push("</tr>");								
			html.push("<tr>");							
			html.push("<td class='w1'>音频码率</td>");							
			html.push("<td class='w2'>"+toString(item.aBitrate)+"</td>");							
			html.push("</tr>");							
			html.push("<tr>");		
			html.push("<td class='w1'>打包格式</td>");
			html.push("<td class='w2'>"+(item.format)+"</td>");						
			html.push("</tr>");						
			html.push("</table>");								
			html.push("</div>");						
			html.push("</li>");				
		});
		html.push("</ul>");
		$("#template_list_id").html(html.join(""));
	}

function toString(str){
	if(str == null || str == undefined){
		str = '';
	}
	return str;
}
