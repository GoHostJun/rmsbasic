/**
 * 修改公告类型列表
 */
function updatePushPath() {
	if (!$.trim($("#pushName").val())) {
		$("#push_name").html("不能为空");
		$("#pushName").focus();
		return;
	}
	if (!$.trim($("#pushUrl").val())) {
		$("#push_url").html("不能为空");
		$("#pushUrl").focus();
		return;
	}
	if (!$.trim($("#uniqueName").val())) {
		$("#unique_name").html("不能为空");
		$("#uniqueName").focus();
		return;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("_id", $("#_id").val());
	onairMap.put("pushName", $("#pushName").val());
	onairMap.put("uniqueName", $("#uniqueName").val());
	onairMap.put("pushUrl", $("#pushUrl").val());
	onairMap.put("queryUrl", $("#queryUrl").val());
	onairMap.put("otherMsg", $("#otherMsg").val());
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "push/updatePushSet/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('修改成功！');
				layer.closeAll('page');
				showPushPathList();
			} else {
				layer.msg('修改失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}