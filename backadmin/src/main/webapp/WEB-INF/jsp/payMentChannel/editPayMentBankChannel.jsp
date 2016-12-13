﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="images/favicon.png" type="image/png">
  <title>添加渠道银行关联</title>  
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
      <h2><i class="fa fa-pencil"></i>添加渠道银行关联 </h2>
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">               
                </div>
                <h4 class="panel-title">添加渠道银行关联</h4>
              </div>
              <div class="panel-body">
                	<input type="hidden"  name="id" id="id" value="${paymentBankChannel.id}" width="10px" class="form-control" />
                <div class="form-group">
                  <label class="col-sm-3 control-label">银行名称 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                     <select class="select2" name="bankName" id="bankName" >	            
	                  <c:forEach var="bank" items="${bankList}">
	                  <option value="${bank.id}">${bank.name}</option>
					 </c:forEach>
	                </select>
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-3 control-label">渠道名称<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
					 <select class="select2" name="channelName" id="channelName" >	            
	                  <c:forEach var="channel" items="${channelList}">
	                  <option value="${channel.id}">${channel.name}</option>
					 </c:forEach>
	                </select>
                  </div>   
                                   
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">单笔限额<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="oneMoney" id="oneMoney" value="${paymentBankChannel.oneMoney}" placeholder="单笔限额"  class="form-control"/>
                  </div>   
                   <div id="erroroneMoney" style="display: none;">单笔限额不能为空</div>                
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">单日限额<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="dayMoney" id="dayMoney"  value="${paymentBankChannel.dayMoney}" placeholder="单日限额"  class="form-control"/>
                  </div>   
                   <div id="errordayMoney" style="display: none;">单日限额不能为空</div>                
                </div>
                 <div class="form-group">
                  <label class="col-sm-3 control-label">单月限额<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="monthMoney" id="monthMoney" value="${paymentBankChannel.monthMoney}"  placeholder="单月限额"  class="form-control"/>
                  </div>   
                   <div id="errormonthMoney" style="display: none;">单月限额不能为空  </div>                
                </div>
	            <div class="form-group">
                <label class="col-sm-3 control-label">是否支持网银<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <select class="select2" name="onlineBanking" id="onlineBanking" data-placeholder="是否可用">	            
	                  <option value="1">支持</option>
	                  <option value="2">不支持</option>
	                </select>
                  </div>               
	            </div>
	            <div class="form-group">
                <label class="col-sm-3 control-label">是否支持快捷<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <select class="select2" name="quickRecharge" id="quickRecharge" data-placeholder="是否可用">	            
	                   <option value="1">支持</option>
	                  <option value="2">不支持</option>
	                </select>
                  </div>               
	            </div>
	            <div class="form-group">
                <label class="col-sm-3 control-label">是否支持绑卡<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <select class="select2" name="tiecard" id="tiecard" data-placeholder="是否可用">	            
	                   <option value="1">支持</option>
	                  <option value="2">不支持</option>
	                </select>
                  </div>               
	            </div>
	            <div class="form-group">
                <label class="col-sm-3 control-label">是否支持加急提现<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <select class="select2" name="urgentWithdrawals" id="urgentWithdrawals" data-placeholder="是否可用">	            
	                   <option value="1">支持</option>
	                  <option value="2">不支持</option>
	                </select>
                  </div>               
	            </div>	
	            <div class="form-group">
                  <label class="col-sm-3 control-label">排序<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" id="sort" name="sort" value="${paymentBankChannel.sort}"  style="width:70px;"/>
                </div> 
                <div id="errorsort" style="display: none;">排序不能为空</div>                   
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
$('#bankName').val('${paymentBankChannel.bankId}'); 
$('#channelName').val('${paymentBankChannel.channelId}'); 
$('#onlineBanking').val('${paymentBankChannel.onlineBanking}'); 
$('#quickRecharge').val('${paymentBankChannel.quickRecharge}'); 
$('#tiecard').val('${paymentBankChannel.tiecard}'); 
$('#urgentWithdrawals').val('${paymentBankChannel.urgentWithdrawals}'); 
	
	
	function sub(){
		$('#errorId').attr("stype", "display:none;");
		$('#errorcardNo1').attr("stype", "display:none;");
	
		var reg = /^\d{19}$/g; // 以19位数字开头，以19位数字结尾
		if($("#oneMoney").val() == ''){//渠道名称
			alert("单笔限额不能为空");
			return false;
		}
		if($("#dayMoney").val() == ''){//渠道简称
			alert("单日限额不能为空");
			return false;
		}
		if($("#monthMoney").val() == ''){//普通充值费率
			alert("单月限额不能为空");
			return false;
		}
		if($("#sort").val() == ''){
			alert("排序限额不能为空");
			return false;
		}
		$.ajax({
	        type: "POST",
	        url:'/payMentChannel/editPayMentBankChannel',
	        data:$('#basicForm').serialize(),// 你的formid              
	        error: function( e ) {
	        	alert("异常");
	        },
	        success: function(data) {
	        	if(data == 'ok'){
	                 alert('添加成功');
	                 window.location = "/payMentChannel/payMentShowBankChannel";
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
			url : '/payMentChannel/payMentChannelLogo',						
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
    