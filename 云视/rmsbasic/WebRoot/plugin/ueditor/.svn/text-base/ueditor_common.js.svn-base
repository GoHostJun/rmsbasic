function initUeditor(){
	 ue = UE.getEditor('docsEditor', {
         "initialFrameHeight": "200",
         toolbars: [['fullscreen',"paragraph","fontfamily","fontsize","forecolor","backcolor","|","bold","italic","strikethrough"
                     ,"underline","fontborder","|","insertunorderedlist","insertorderedlist"
                     ,"|","justifyleft","justifyright","justifycenter","justifyjustify","|"
                     ,"undo","redo","|","searchreplace","print","preview","|","date","time","|","contentbody","contentinstruction","contentafterwords"
                     ,"contentsubtitle","contentactualsound","contentsplitmark","|","onekeytypeset"]],
         enterTag: "&nbsp;"
     }); //回车的时候用换行不用段落标签
     //实现插件的功能代码
     baidu.editor.commands['contentbody'] = { execCommand: function() { this.execCommand('insertHtml', "<span style='color: green;'>【正文】</span><br>"); return true; }, queryCommandState: function() { } };
     baidu.editor.commands['contentinstruction'] = { execCommand: function() { this.execCommand('insertHtml', "<span style='    color: blue;'>【导语】</span><br>"); return true; }, queryCommandState: function() { } };
     baidu.editor.commands['contentafterwords'] = { execCommand: function() { this.execCommand('insertHtml', "<span style='color: black'>【编后】</span><br>"); return true; }, queryCommandState: function() { } };
     baidu.editor.commands['contentsubtitle'] = { execCommand: function() { this.execCommand('insertHtml', "<span style='color: #FF33FF'>【字幕】</span><br>"); return true; }, queryCommandState: function() { } };
     baidu.editor.commands['contentactualsound'] = { execCommand: function() { this.execCommand('insertHtml', "<span style='    color: #CC6600;'>【同期】</span><br>"); return true; }, queryCommandState: function() { } };
     baidu.editor.commands['contentsplitmark'] = { execCommand: function() { this.execCommand('insertHtml', "<span style='    color: #FF0000'>【分标】</span><br>"); return true; }, queryCommandState: function() { } };
}