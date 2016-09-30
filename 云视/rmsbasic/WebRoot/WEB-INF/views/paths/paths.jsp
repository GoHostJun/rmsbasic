<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script src="${ctx }/js/paths/paths.js" type="text/javascript" charset="utf-8"></script>
		<title>系统设置--路径</title>
        
		<div id="content" class="sys_cont">
            <div class="sys_cont_inner">
                <div class="gg_div sm_list">
                    <h5><i></i>扫描配置</h5>
                    <div class="gg_cont">
                        <div class="gg_div sm_cont">
                            <h5><i></i>扫描配置</h5>
                            <div class="sm_btn_div" id="scan_id">
                                <i></i>	
                            </div>
                            <div class="gg_cont" id="config_id">
                                <i></i>
                                <p><span>扫描用户名称：</span><input type="text" id="yh_name" value="云南电视台" disabled="disabled" ></p>
                                <p><span>所属商：</span><input type="text" id="sss" value="云南电视台" disabled="disabled" ></p>
                                <p><span>源路径：</span><input type="text" id="ylj" value="/www/file" disabled="disabled" ></p>
                                <p><span>目标路径：</span><input type="text" id="mbly" value="/www/file" disabled="disabled" ></p>
                                <p><span>选择自动流程：</span>
                                    <select disabled="disabled" >
                                        <option>手动流程</option>
                                    </select>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>




