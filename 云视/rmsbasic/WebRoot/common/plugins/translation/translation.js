/**
 * 数组去重
 * @param arr
 * @returns {Array}
 */
function unique(arr) {
	if(arr!=null&&arr.length>0){
		var result = [], hash = {};
		for (var i = 0, elem; (elem = arr[i]) != null; i++) {
			if (!hash[elem]) {
				result.push(elem);
				hash[elem] = true;
			}
		}
	}else{
		return arr;
	}
    return result;
}
/**
 * 国际化方法，传入需要翻译的html快id，即可翻译该html里的内容（需要配置语言包）
 * @param divId
 */
function translationinit(divId){
    var h = $("#"+divId).html();
    if(h!=undefined){
    	var mode=/[\u4e00-\u9faf]+/g
		var s=h.match(mode);
    	s=unique(s);//去重
    	var ch = $("#"+divId).children();
    	recursion(ch,s);
    }
}
/**
 * 递归
 * @param obj
 * @param s
 */
function recursion(obj,s){
	if(s!=null&&obj.length!=0){
		obj.each(function(i){  
			var mode=/[\u4e00-\u9faf]+/
			var s1=$.trim($(this).text()).match(mode);
			if(s1==null){
				return true;
			}
			if(s1 in languageMap){
				var text = $(this).html().replace(s1,languageMap[s1]);
				$(this).html(text);
			}
		});
			recursion(obj.children(),s);
	}
	
}
/**
 * 国际化方法  传入json，把json里的中文替换成国际化语言（试用与ztree菜单）
 * @param json数组 内容是object元素
 * @returns {Array}返回数组，数组内容是object元素
 */
function initjsonObj(json){
	var h = json;
	var h2=[];
	for(var j = 0;j < h.length; j++){
		var str = JSON.stringify(h[j])
		var mode=/[\u4e00-\u9faf]+/g
		var s=str.match(mode)
		for(var i = 0;i < s.length; i++){
			if(languageMap[s[i]]!=undefined){
				var obj= eval("(" + str.replace(s[i],languageMap[s[i]])+ ")");
				h2.push(obj);
			}else{
				var obj= eval("(" + str+ ")");
				h2.push(obj);
			}
		}
	}
	return h2;
}
/**
 * 国际化方法  传入json，把json里的中文替换成国际化语言
 * @param json数组 
 * @returns {Array}返回数组
 */
function initjson(json){
	var h = json;
	var h2=[];
	for(var j = 0;j < h.length; j++){
		var str = h[j]
		var mode=/[\u4e00-\u9faf]+/g
		var s=str.match(mode)
		for(var i = 0;i < s.length; i++){
			if(languageMap[s[i]]!=undefined){
				var obj=  str.replace(s[i],languageMap[s[i]]);
				h2.push(obj);
			}else{
				var obj=  str;
				h2.push(obj);
			}
		}
	}
	return h2;
}
/**
 * 国际化方法  传入String，把String替换成国际化语言
 * @param str
 * @returns
 */
function initString(str){
	var h = str;
		var mode=/[\u4e00-\u9faf]+/g
		var s=str.match(mode)
		if(s!=null){
			for(var i = 0;i < s.length; i++){
				if(languageMap[s[i]]!=undefined){
					return languageMap[s[i]];
				}else{
					return h;
				}
			}
		}
	return h;
}




// 获取地址栏的参数数组
function getUrlParams()
{
    var search = window.location.search ;
    // 写入数据字典
    var tmparray = search.substr(1,search.length).split("&");
    var paramsArray = new Array;
    if( tmparray != null)
    {
        for(var i = 0;i<tmparray.length;i++)
        {
            var reg = /[=|^==]/;    // 用=进行拆分，但不包括==
            var set1 = tmparray[i].replace(reg,'&');
            var tmpStr2 = set1.split('&');
            var array = new Array ;
            array[tmpStr2[0]] = tmpStr2[1] ;
            paramsArray.push(array);
        }
    }
    // 将参数数组进行返回
    return paramsArray ;    
}

// 根据参数名称获取参数值
function getParamValue(name)
{
    var paramsArray = getUrlParams();
    if(paramsArray != null)
    {
        for(var i = 0 ; i < paramsArray.length ; i ++ )
        {
            for(var  j in paramsArray[i] )
            {
                if( j == name )
                {
                    return paramsArray[i][j] ;
                }
            }
        }
    }
    return null ;
} 
/**
 * 同步加载js脚本
 * @param id   需要设置的<script>标签的id
 * @param url   js文件的相对路径或绝对路径
 * @return {Boolean}   返回是否加载成功，true代表成功，false代表失败
 */
function loadJS(id,url){
    var  xmlHttp = null;
    if(window.ActiveXObject)//IE
    {
        try {
            //IE6以及以后版本中可以使用
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (e) {
            //IE5.5以及以后版本可以使用
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    else if(window.XMLHttpRequest)//Firefox，Opera 8.0+，Safari，Chrome
    {
        xmlHttp = new XMLHttpRequest();
    }
    //采用同步加载
    xmlHttp.open("GET",url,false);
    //发送同步请求，如果浏览器为Chrome或Opera，必须发布后才能运行，不然会报错
    xmlHttp.send(null);
    //4代表数据发送完毕
    if ( xmlHttp.readyState == 4 )
    {
        //0为访问的本地，200到300代表访问服务器成功，304代表没做修改访问的是缓存
        if((xmlHttp.status >= 200 && xmlHttp.status <300) || xmlHttp.status == 0 || xmlHttp.status == 304)
        {
            var myHead = document.getElementsByTagName("HEAD").item(0);
            var myScript = document.createElement( "script" );
            myScript.language = "javascript";
            myScript.type = "text/javascript";
            myScript.id = id;
            try{
                //IE8以及以下不支持这种方式，需要通过text属性来设置
                myScript.appendChild(document.createTextNode(xmlHttp.responseText));
            }
            catch (ex){
                myScript.text = xmlHttp.responseText;
            }
            myHead.appendChild( myScript );
            return true;
        }
        else
        {
            return false;
        }
    }
    else
    {
        return false;
    }
}



function getCookie(name)
{
         var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
       if(arr=document.cookie.match(reg))
                  return unescape(arr[2]);
         else
                   return null;
}