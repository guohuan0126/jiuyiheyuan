<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="images/favicon.png" type="image/png">
  <title>银行卡管理</title>  
</head>
<body>
<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section>
<%@include file="../base/leftMenu.jsp"%>  
  <div class="mainpanel">
<%@include file="../base/header.jsp"%>
    <div class="pageheader">
      <h2><i class="fa fa-pencil"></i> 银行卡管理 </h2>
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">               
                </div>
                <h4 class="panel-title">添加银行卡</h4>
              </div>
              <div class="panel-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">银行名称 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <input type="hidden" name="id" value="${bankcard.id }">
                    <input type="text"  name="name" id="name" value="${bankcard.name}" width="10px" class="form-control" />
                  </div>
                  <div id="errorId" style="display: none;">银行名称不能为空</div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-3 control-label">银行简称<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="bank" id="bank" value="${bankcard.bank}" placeholder="银行简称"  class="form-control"/>
                  </div>   
                   <div id="errorcardNo1" style="display: none;">银行简称不能为空</div>                
                </div>
	            <div class="form-group">
	                <label class="col-sm-3 control-label">支付平台<span class="asterisk">*</span></label>
	                  <div class="col-sm-3">
	                   <select class="select2" name="paymentId" id="paymentId" data-placeholder="支付平台...">
		                  <option value="Yeepay">易宝</option>
		                  <option value="JDpay">京东</option>	    
		                </select>
	                  </div> 	               
	            </div>               
                <div class="form-group">
								<label class="col-sm-3 control-label">LOGO<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="logo" id="logo" width="10px" 
										class="form-control" value="${bankcard.url}"/>
										<div id="preview"><img src="https://duanrongweb.oss-cn-qingdao.aliyuncs.com/${bankcard.url}" width="50" height="50" alt="" /></div>									
								</div>
								<div class="col-sm-3">
									<form id="upload"   >
											<input id="fileToUpload2" name="file" type="file" />
											<input  type="button"  onclick="sufile()" value="上传">
										<span class="asterisk"> 请上传正方形并小于100*100尺寸的logo图片</span>	
									</form>
									
								</div>
							</div>           
                <div class="form-group">
                <label class="col-sm-3 control-label">绑卡<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <select class="select2" name="tiecard" id="tiecard" data-placeholder="绑卡状态...">	            
	                  <option value="1">支持绑卡</option>
	                  <option value="0">不支持绑卡</option>
	                </select>
                  </div>               
	            </div>
	              
                <div class="form-group">
                <label class="col-sm-3 control-label">网银<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <select class="select2" name="onlineBank" id="onlineBank" data-placeholder="网银状态...">	            
	                  <option value="1">支持网银</option>
	                  <option value="0">不支持网银</option>
	                </select>
                  </div>               
	            </div>
               <div class="form-group">
                <label class="col-sm-3 control-label">快捷<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <select class="select2" name="quickRecharge" id="quickRecharge" data-placeholder="快捷状态...">	            
	                  <option value="1">支持快捷</option>
	                  <option value="0">不支持快捷</option>
	                </select>
                  </div>               
	            </div>	
               <div class="form-group">
                <label class="col-sm-3 control-label">是否显示<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <select class="select2" name="whetherDelete" id="st" data-placeholder="显示状态...">	            
	                  <option value="1">显示</option>
	                  <option value="0">不显示</option>
	                </select>
                  </div>               
	            </div>
	            <div class="form-group">
                  <label class="col-sm-3 control-label">排序<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="sortNo" value="${bankcard.sortNo}"  style="width:70px;"/>
                </div>                
                </div> 
               <div class="form-group">
                  <label class="col-sm-3 control-label">快捷限额说明<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <textarea rows="3" name="rechargeDesc"  style="width:400px;height: 100px;">${bankcard.rechargeDesc}</textarea>
                 </div>                
               </div> 	  	      	                        
              </div><!-- panel-body -->
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3">
                    <input type="button" class="btn btn-primary" onclick="sub();" value="保存"/>
                    <button type="reset" class="btn btn-default">重置</button>
                  </div>
                </div>
              </div>
            
          </div><!-- panel -->
      </form> 
    </div><!-- contentpanel -->
    
  </div><!-- mainpanel -->
 
</section>

<script>
	
	 jQuery(document).ready(function(){	
			
		if('${addUrl}'=='/bankinfo/save/editCard'){			
			 $("#paymentId").val('${bankcard.paymentId}');
			 $("#tiecard").val('${bankcard.tiecard}');
			 $("#onlineBank").val('${bankcard.onlineBank}');
			 $("#quickRecharge").val('${bankcard.quickRecharge}');
			 $("#st").val('${bankcard.whetherDelete}');
		}		
		
	});
	
	
	
	function sub(){
		$('#errorId').attr("stype", "display:none;");
		$('#errorcardNo1').attr("stype", "display:none;");
	
		var reg = /^\d{19}$/g; // 以19位数字开头，以19位数字结尾
		if($("#name").val() == ''){
			$('#errorId').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}
		if($("#bank").val() == ''){
			$('#errorcardNo1').attr("style", "display:block;font-size:14px;color:red");		
			return false;
		}else{
			$('#errorcardNo1').attr("stype", "display:none;");
		}
		$.ajax({
	        type: "POST",
	        url:'${addUrl}',
	        data:$('#basicForm').serialize(),// 你的formid              
	        error: function( e ) {
	        	alert("异常");
	        },
	        success: function(data) {
	        	if(data == 'ok'){
	                 alert('操作成功');
	                 window.location = "/bankinfo/bankinfoList";
	             }else{
	            	 alert('操作失败');
	             }                
	            }
	        });					
				
	}
	function sufile(){
		 var formdata = new FormData();               
		    var fileObj = document.getElementById("fileToUpload2").files;               
		        for (var i = 0; i < fileObj.length; i++)                   
		        formdata.append("file", fileObj[0]);   
		$.ajax({
			type : 'POST',
			url : '/bankinfo/uploadBankData',						
			data:formdata,
			contentType: false,
			 processData: false,
			success:function(data) {
				$('#logo').val(data);
				$('#preview').html('<img src=\"${pageContext.request.contextPath}'+data+'\" width=\"100\" height=\"100\" alt=\"\" />');
				
			},
			error:function(e){
				alert(e);
			}
		});
	}
	
</script>

</body>
</html>
    