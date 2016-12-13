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
  <title>添加渠道银行</title>  
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
      <h2><i class="fa fa-pencil"></i> 添加渠道银行 </h2>
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">               
                </div>
                <h4 class="panel-title">添加支付渠道银行</h4>
              </div>
              <div class="panel-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">银行名称 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text"  name="bankName" id="bankName" value="${payMentBankInfo.bankName}" width="10px" class="form-control" />
                  </div>
                  <div id="errorbankName" style="display: none;">银行名称不能为空</div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-3 control-label">银行简称<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="code" id="code" value="${payMentBankInfo.code}" placeholder="银行简称"  class="form-control"/>
                  </div>   
                   <div id="errorcode" style="display: none;">银行简称不能为空</div>                
                </div>
                <div class="form-group">
								<label class="col-sm-3 control-label">LOGO<span class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="logo" id="logo" width="10px" 
										class="form-control" value="${payMentBankInfo.logo}"/>
									<div id="errorlogo" style="display: none;">logo不能为空</div>  
										<div id="preview"><img src="https://drwebdemo.oss-cn-qingdao.aliyuncs.com/${payMentBankInfo.logo}" width="50" height="50" alt="" /></div>									
								</div>
								<div class="col-sm-3">
									<form id="upload"   >
											<input id="fileToUpload2" name="file" type="file" />
											<input  type="button"  onclick="sufile()" value="上传">
										<span class="asterisk"> 请上传正方形并小于100*100尺寸的logo图片</span>	
									</form>
									
								</div>
							</div>           
	           
              </div><!-- panel-body -->
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3">
                    <input type="button" class="btn btn-primary" onclick="sub();" value="保存"/>
                    <button type="reset" class="btn btn-default">重置</button>
                  </div>/
                </div>
              </div>
            
          </div><!-- panel -->
      </form> 
    </div><!-- contentpanel -->
    
  </div><!-- mainpanel -->
 
</section>

<script>
	
	function sub(){
		$('#errorbankName').attr("style", "display:none;");
		$('#errorcode').attr("style", "display:none;");
		
	
		var reg = /^\d{19}$/g; // 以19位数字开头，以19位数字结尾
		if($("#bankName").val() == ''){//银行名称
			alert("银行名称不能为空");
			return false;
		}
		if($("#code").val() == ''){//银行简称
			alert("银行简称不能为空");		
			return false;
		}
		if($("#logo").val() == ''){//银行简称
			alert("银行logo不能为空");	
			return false;
		}
		$.ajax({
	        type: "POST",
	        url:'/payMentChannel/addPayMentBankInfo',
	        data:$('#basicForm').serialize(),// 你的formid              
	        error: function( e ) {
	        	alert("异常");
	        },
	        success: function(data) {
	        	if(data == 'ok'){
	                 alert('添加成功');
	                 window.location = "/payMentChannel/payMentShowBankInfo";
	             }else{
	            	 alert('添加失败');
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
			url : '/payMentChannel/payMentBankInfoLogo',						
			data:formdata,
			contentType: false,
			 processData: false,
			success:function(data) {
				$('#logo').val(data);
				$('#preview').html('<img src=\"${pageContext.request.contextPath}'+data+'\" width=\"100\" height=\"100\" alt=\"\" />');
				$('#errorlogo').attr("stype", "display:none;");
			},
			error:function(e){ 
				alert(e);
			}
		});
	}
	
</script>

</body>
</html>
    