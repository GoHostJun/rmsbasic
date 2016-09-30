$(function(){
	$("#form").validationEngine();
	initUpload();
	//加载来源
	getScanList();
})

/**
 * 加载客户列表
 */
function getScanList() {
	$("[name=src]").html('');
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", 1);
	onairMap.put("pageNum", 10000000);
	onairMap.put("isused", "1");
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "scan/findScanAll/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				if(data.data.results.length>0){
					$.each(data.data.results,function(i,item){
						$("[name=src]").append("<option value="+item.scantype+">"+item.scantype+"</option>")
					})
				}
				
			} else{
				 toastr.error("查询扫描来源失败");
			}
		},
		error : function() {
			layer.close(index);
			 toastr.error("查询扫描来源失败");
		}
	});
}

function addMaterial(){
	var bool = $('#form').validationEngine('validate');
    if (!bool) {
    	return;
    }
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("title", $("#materialtitle").val());
	onairMap.put("src", $("[name=src]").val());
	onairMap.put("remark", $("#remark").val());
	onairMap.put("thumbnailurl", $("#thumbnailurl").attr("src"));
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "materialSet/savematerialSet/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加成功！');
				layer.closeAll('page');
				//showScanList();
			} else {
				layer.msg('添加错误！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('添加错误！');
		}
	});

}
/**
 * 初始化上传插件
 */
function initUpload() {
	$("#fileUpload").uploadify(
			{
				'debug' : false,
				'swf' : ctx + '/common/plugins/uploadify3.1/uploadify.swf',
				'fileTypeExts' : '*.jpg;*.png;*.gif;*.png;',
				'buttonImage' : ctx+ '/common/plugins/uploadify3.1/img/uploadThumb.png',
				'uploader' : ctx +"/upload/thumbnail/?id="+1,
				'fileObjName' : 'fileUpload',
				'queueID' : 'fileQueue',
				'fileSizeLimit' : 1024,
				'auto' : true,
				'multi' : false,
				'width' : 160,
				'height' : 38,
				'progressData' : 'speed',
				'preventCaching' : true,
				'successTimeout' : 99999,
				'removeTimeout' : '1',
				'removeCompleted' : true,
				'removeTimeout' : 3,
				'onUploadSuccess' : function(file, data, response) {
					if(data){
						$("#thumbnailurl").attr('src',data); 
					}
				}
			});
}