<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%
	String suffix = String.valueOf(request.getAttribute("suffix"));
%>
<!DOCTYPE html>
<html>
<head>
<title>上传插件</title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>

<body>
	<script src="${ctx }/common/plugins/upload/js/stream-v1.js"></script>
	<div style="width: 100%; margin-left: 10px;">
		<div class="container">
			<div id="i_select_files" class="stream-browse-files stream-browse-drag-files-area" style="width: 600px; height: 20px; line-height: 20px;"></div>
			<div id="i_stream_files_queue" style="width: 600px; margin-bottom: 7px;  height: 290px;"></div>
		</div>
	</div>
	<script type="text/javascript">
		var suffix = "<%=suffix%>";
		var suffixs = suffix.split("*");
		Array.prototype.remove = function(val) {
			var index = this.indexOf(val);
			if (index > -1) {
				this.splice(index, 1);
			}
		};
		/**
		 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
		 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
		 * 是在ID为i_stream_message_container的页面元素中写日志
		 */
		var config = {
			/** 选择文件的ID, 默认: i_select_files */
			browseFileId : "i_select_files",
			/** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
			browseFileBtn : "<div>点击这里选择『素材』</div>",
			/** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
			dragAndDropArea : "i_select_files",
			/** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
			dragAndDropTips : "<span>&nbsp;</span>",
			/** 文件上传容器的ID, 默认: i_stream_files_queue */
			filesQueueId : "i_stream_files_queue",
			/** 文件上传容器的高度（px）, 默认: 450 */
			filesQueueHeight : 290,
			/** 允许的文件扩展名, 默认: [] */
			extFilters : suffixs,
			/** 单个文件的最大大小，默认:2G */
			maxSize : 2147483648,
			/** 根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌） */
			tokenURL : ctx + "/tk?product=ytl",
			/** Flash上传的URI */
			frmUploadURL : ctx + "/fd",
			/** HTML5上传的URI */
			uploadURL : ctx + "/upload",
			/** SWF文件的位置 */
		    swfURL : ctx + "/common/plugins/upload/swf/FlashUploader.swf",
			/** 多个文件一起上传, 默认: false */
			multipleFiles : true,
			/** HTML5上传失败的重试次数 */
			retryCount : 5,
			/** 单次最大上传文件个数 */
			simLimit : 200,
			/** 文件的扩展名不匹配的响应事件 */
			onExtNameMismatch : function(name, filters) {
				alert("『" + name.name + "』类型不匹配，无法上传");
			},
			/** 单个文件上传完毕的响应事件 */
			onComplete : function(file) {
				upload_save(file);
			},
			/** 选择文件后的响应事件 */
			onSelect : function(list) {
				//fileUploading();
			},
			/** 文件上传出错的响应事件 */
			onUploadError : function(status, msg) {
				alert("上传出错了！\n错误码：" + status + "\n错误信息：" + msg);
			},
			/** 取消上传文件的响应事件 */
			onCancel : function(file) {
			},
			/** 所有文件上传完毕的响应事件 */
			onQueueComplete : function() {
				//alert("文件已经全部上传完毕！");
				//fileUploaded();
				upload_close();
			},
			/** 文件大小超出的响应事件 */
			onMaxSizeExceed : function(size, limited, name) {
				alert("文件大小超过了指定大小！");
			},
			/** 文件数量超出的响应事件 */
			onFileCountExceed : function(selected, limit) {
				alert("文件数量超过了指定大小！");
			}
		//					autoUploading: false, /** 选择文件后是否自动上传, 默认: true */
		//					autoRemoveCompleted : true, /** 是否自动删除容器中已上传完毕的文件, 默认: false */
		//					postVarsPerFile : { /** 上传文件时传入的参数，默认: {} */
		//						param1: "val1",
		//						param2: "val2"
		//					},
		};
		var _t = new Stream(config);
	</script>

</body>
</html>
