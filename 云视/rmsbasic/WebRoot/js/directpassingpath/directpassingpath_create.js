/**
 * 新增直传路径
 */
function addDirectPassingPath() {
	if (!$.trim($("#tarpathname").val())) {
		$("#tarpath_name").html("不能为空");
		$("#tarpathname").focus();
		return;
	}else if($("#tarpathname").val().length>10){
		$("#tarpath_name").html("最多10个字符");
		$("#tarpathname").focus();
		return;
	}else{
		$("#tarpath_name").html("");
	}
	if (!$.trim($("#tarpathid").val())) {
		$("#tarpath_id").html("不能为空");
		$("#tarpathid").focus();
		return;
	}else{
		$("#tarpath_id").html("");
	}
	if (!$.trim($("#tarpathaddr").val())) {
		$("#tarpath_addr").html("不能为空");
		$("#tarpathaddr").focus();
		return;
	}else{
		$("#tarpath_addr").html("");
	}
	
	var onairMap = new OnairHashMap();
	onairMap.put("tarpathname", $("#tarpathname").val());
	onairMap.put("tarpathid", $("#tarpathid").val());
	onairMap.put("tarpathaddr", $("#tarpathaddr").val());
	onairMap.put("pushpathaddr", $("#pushpathaddr").val());
	onairMap.put("pushpathname", $("#pushpathname").val());
	onairMap.put("othermsg", $("#othermsg").val());
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "directpassingpath/addDirectPassingPath/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加直传路径成功！');
				layer.closeAll('page');
				showdirectpassingpathList();
			} else {
				layer.msg('添加直传路径失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}