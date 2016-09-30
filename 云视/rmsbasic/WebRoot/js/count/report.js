var line_videototal = new Array(); // 素材-视频个数集合
var line_audiototal = new Array(); // 素材-音频个数集合
var line_audiototal = new Array(); // 素材-音频个数集合
var line_time = new Array(); // 素材-音频个数集合
var line_materialtotal = new Array(); // 素材-音频个数集合
var line_newstotal = new Array(); // 素材-音频个数集合
$(function(){
	$("#ascrail2000").remove();
	$("#indexPop").remove();
	$("#indexJX").remove();
	$("#search_id").click(function(){
		loadMediaCount();
		loadMaterialCount();
		loadNewsCount();
	})
	loadMediaCount("week");
	loadMaterialCount("week");
	loadNewsCount("week");
	
	
})

function loadMediaCount(dateType){
	
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
		toastr.error("开始时间不能大于结束时间！");
		//$.Mark.hide();
		return;
	}
	 var index = layer.load();
	var jsonStr={"startTime":beginDate,"endTime":endDate,"dateType":(dateType==undefined?"":dateType),"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	 jsonStr= JSON.stringify(jsonStr);
	 $.ajax({
		 url : ctx + pathValue+"media/statistics/",
		 headers:{"Content-Type":"application/json"},
		 data : jsonStr,
		 type : "post",
		 cache : false,
		 dataType : "json",
		 success : function(response) {
			 layer.close(index);
			 var code = response.code;
			 var data = response.data;
			 $("#beginDate").val(data.startTime);
			 $("#endDate").val(data.endTime);
			 if(code == 0){
				 	line_videosize = []; // 视频集合
					line_audiosize = []; // 音频集合
					line_picsize = []; // 图片集合
				 	line_time = []; // 时间点集合
						var len = data.length;
						if (len == 0) {
							line_videosize.push('-');
							line_audiosize.push('-');
							line_picsize.push('-');
						}
						$(data.statistics).each(function(i,item) {
							line_time.push(item.date);
							if (item.video != null) {
								line_videosize.push(item.video);
							} else {
								line_videosize.push('-');
							}
							if (item.audio != null) {
								line_audiosize.push(item.audio);
							} else {
								line_audiosize.push('-');
							}
							if (item.pic != null) {
								line_picsize.push(item.pic);
							} else {
								line_picsize.push('-');
							}
						});
						var echardata = [{
							name: '视频',
							type: 'line',
							data: line_videosize
						}, {
							name: '音频',
							type: 'line',
							data: line_audiosize
						}, {
							name: '图片',
							type: 'line',
							data: line_picsize
						}];
						var ledata = ['视频', '音频','图片'];
						var formt ="个";
						presentEchart(echardata, "list_cont", '素材数量', ledata,formt,false);
				 
			 }else{
				 $.Mark.hide();
					return;
				 toastr.error("加载素材报表失败,请重试！");
			 }
		 },
		 error : function() {
			 layer.close(index);
			 toastr.error("加载素材报表失败，请重试！");
		}
	});
}


function loadMaterialCount(dateType){
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
//		toastr.error("开始时间不能大于结束时间！");
		return;
	}
	var index = layer.load();
	var jsonStr={"startTime":beginDate,"endTime":endDate,"dateType":(dateType==undefined?"":dateType),"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	 jsonStr= JSON.stringify(jsonStr);
	 $.ajax({
		 url : ctx + pathValue+"presentation/statistics/",
		 headers:{"Content-Type":"application/json"},
		 data : jsonStr,
		 type : "post",
		 cache : false,
		 dataType : "json",
		 success : function(response) {
			 layer.close(index);
			 var code = response.code;
			 var data = response.data;
			 if(code == 0){
				 		line_materialtotal = []; // 视频集合
				 	    line_time=[];
						var len = data.length;
						if (len == 0) {
							line_materialtotal.push('-');
						}
						$(data.statistics).each(function(i,item) {
							line_time.push(item.date);
							if (item.presentation != null) {
								line_materialtotal.push(item.presentation);
							} else {
								line_materialtotal.push('-');
							}
							
						});
						var echardata = [{
							name: '文稿',
							type: 'line',
							data: line_materialtotal
						}];
						var ledata = ['文稿'];
						var formt ="个";
						presentEchart(echardata, "list_cont2", '文稿数量', ledata,formt,false);
				 
			 }else{
				 $.Mark.hide();
					return;
				 toastr.error("加载文稿失败,请重试！");
			 }
		 },
		 error : function() {
			 layer.close(index);
			 toastr.error("加载文稿失败，请重试！");
		}
	});
}

function loadNewsCount(dateType){

	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	if (beginDate > endDate) {
//		toastr.error("开始时间不能大于结束时间！");
		return;
	}
	var index = layer.load();
	var jsonStr={"startTime":beginDate,"endTime":endDate,"dateType":(dateType==undefined?"":dateType),"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	 jsonStr= JSON.stringify(jsonStr);
	 $.ajax({
		 url : ctx + pathValue+"report/countNewsByTime/",
		 headers:{"Content-Type":"application/json"},
		 data : jsonStr,
		 type : "post",
		 cache : false,
		 dataType : "json",
		 success : function(response) {
			 layer.close(index);
			 var code = response.code;
			 var data = response.data;
			 if(code == 0){
				 		line_materialtotal = []; // 视频集合
				 	    line_time=[];
						var len = data.length;
						if (len == 0) {
							line_materialtotal.push('-');
						}
						$(data.statistics).each(function(i,item) {
							line_time.push(item.strDate);
							if (item.newsNum != null) {
								line_materialtotal.push(item.newsNum);
							} else {
								line_materialtotal.push('-');
							}
							
						});
						var echardata = [{
							name: '通联',
							type: 'line',
							data: line_materialtotal
						}];
						var ledata = ['通联'];
						var formt ="个";
						presentEchart(echardata, "list_cont3", '通联数量', ledata,formt,false);
				 
			 }else{
				 $.Mark.hide();
					return;
				 toastr.error("加载通联失败,请重试！");
			 }
		 },
		 error : function() {
			 layer.close(index);
			 toastr.error("加载通联失败，请重试！");
		}
	});
}

/** 渲染Echars报表 */
function presentEchart(echardata, divId, titleval, legdata,formt,boundaryGapFlag) {
	require.config({
		paths: {
			echarts: 'js/echarts/example/www/js'
		}
	});

	require(['echarts', 'echarts/chart/bar', 'echarts/chart/line'

	], function(ec) {
		// --- 折柱 ---
		var myChart = ec.init(document.getElementById(divId));
		var option = {
			tooltip: {
				trigger: 'axis'
			},
			title: {
				text: titleval
			},
			legend: {
				orient: 'horizontal',
				x: 'center',
				y: 'bottom',
				data: legdata
			},
			toolbox: {
				show: true,
				feature: {
//					mark: {
//						show: true
//					},
//					dataView: {
//						show: true,
//						readOnly: false
//					},
//					magicType: {
//						show: true,
//						type: ['line', 'bar']
//					},
					restore: {
						show: true
					},
					saveAsImage: {
						show: true
					}
				}
			},
			calculable: true,
			xAxis: [{
				type: 'category',
				boundaryGap: boundaryGapFlag,//柱状图改为true,折线图设置为false
				data: line_time
			}],
			yAxis: [{
				type: 'value',
				 axisLabel : {
                     formatter: '{value} '+formt
                 }

			}],
			series: echardata
		};
		myChart.setOption(option);

	});

}