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
<%@include file="../../base/leftMenu.jsp"%>  
  <div class="mainpanel">
<%@include file="../../base/header.jsp"%>
    <div class="pageheader">
      <h2><i class="fa fa-pencil"></i> 银行卡 </h2>
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">               
                </div>
                <h4 class="panel-title">银行卡操作</h4>
              </div>
              <div class="panel-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">用户编号 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <input type="hidden" name="id" value="${bankcard.id }">
                  <input type="hidden" name="paymentId" value="${bankcard.paymentId }">
                    <input type="text"  name="userId" id="userId" value="${bankcard.userId}" width="10px" class="form-control" placeholder="用户编号..."/>
                  </div>
                  <div id="errorId" style="display: none;">用户编号不能为空</div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-3 control-label">银行卡号</label>
                  <div class="col-sm-3">
                    <input type="text" name="cardNo" id="cardNo" value="${bankcard.cardNo}" placeholder="银行卡号"  class="form-control"/>
                  </div>   
                   <div id="errorcardNo1" style="display: none;">银行卡号不能为空</div>
                    <div id="errorcardNo2" style="display: none;">银行卡号格式不正确</div>             
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">开户行中文</label>
                  <div class="col-sm-3">
                    <input type="text" name="name" value="${bankcard.name}" placeholder="开户行中文"  class="form-control"/>
                  </div>                
                </div>
                 <div class="form-group">
                  <label class="col-sm-3 control-label">开户行英文</label>
                  <div class="col-sm-3">
                    <input type="text" name="bank" value="${bankcard.bank}" placeholder="开户行英文"  class="form-control"/>
                  </div>                
                </div>
                
                <div class="form-group">
                <label class="col-sm-3 control-label">卡状态</label>
                  <div class="col-sm-3">
                  <select class="select2" name="status" id="st" data-placeholder="绑卡状态...">
	                  <option value="VERIFIED" selected = "selected">审核通过</option>
	                  <option value="VERIFYING">审核中</option>
	                  <option value="FAIL">审核失败</option>
	                </select>
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
			
		if('${addUrl}'=='/bankcard/save/editCard'){			
			 $("#userId").attr("readonly","readonly");
			 $("#st").val('${bankcard.status}');
		}		
		
	});
	
	
	
	function sub(){
		$('#errorId').attr("stype", "display:none;");
		$('#errorcardNo1').attr("stype", "display:none;");
		$('#errorcardNo2').attr("stype", "display:none;");
		var reg = /^\d{19}$/g; // 以19位数字开头，以19位数字结尾
		if($("#userId").val() == ''){
			$('#errorId').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}
		if($("#cardNo").val() == ''){
			$('#errorcardNo1').attr("style", "display:block;font-size:14px;color:red");
			//$('#errorcardNo2').attr("stype", "display:none;");
			return false;
		}/* else if(!reg.test($("#cardNo").val())){
			$('#errorcardNo2').attr("style", "display:block;font-size:14px;color:red");
			$('#errorcardNo1').attr("stype", "display:none;");
			return false;
		} */else{
			$('#errorcardNo1').attr("stype", "display:none;");
			//$('#errorcardNo2').attr("stype", "display:none;");
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
	                 location.replace(document.referrer);
	             }else if(data=='exist'){
	            	 alert('用户编号不存在');
	             }else if(data=='isnull'){
	            	 alert('用户编号不能是空');
	             }else if(data=='ok'){
	            	 alert('操作成功');
	            	 window.location = "/bankcard/bankcardList";
	             }else{
	            	 alert('操作失败');
	             }                
	            }
	        });					
				
	}
</script>

</body>
</html>
    