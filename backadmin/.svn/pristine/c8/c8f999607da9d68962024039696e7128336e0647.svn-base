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
  <title>添加信息披露</title>  
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
      <h2><i class="fa fa-pencil"></i> 添加信息披露</h2>
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">               
                </div>
                <h4 class="panel-title">添加信息披露</h4>
              </div>
              <div class="panel-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">当前逾期总额 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">            
                   <input type="text"  name="currentTotalOverdue" id="currentTotalOverdue"  width="10px" class="form-control" />
                  </div>
                  <div id="errorCurrent" style="display: none;">当前逾期总额只能输入数字，小数点后最多两位</div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-3 control-label">历史逾期总额<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="historyTotalOverdue" id="historyTotalOverdue"  class="form-control"/>
                  </div>   
                   <div id="errorHistory" style="display: none;">历史逾期只能输入数字，小数点后最多两位</div>                
                </div>	                       
	            <div class="form-group">
                  <label class="col-sm-3 control-label">逾期坏账总额<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="badDebtOverdue" id="badDebtOverdue"    class="form-control"/>
                  </div>   
                   <div id="errorBadDebt" style="display: none;">逾期坏账总额只能输入数字，小数点后最多两位</div>                
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
//验证金额，小数点后两位
function validateNum(val){
	 var patten = /^[-]?([1-9]{1}[0-9]{0,}(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|\.[0-9]{1,2})$/g;
    return patten.test(val);
}
	function sub(){
		/* $('#errorId').attr("stype", "display:none;");
		$('#errorcardNo1').attr("stype", "display:none;"); */
		if(!validateNum($("#currentTotalOverdue").val())){
			$('#errorCurrent').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else if(!validateNum($("#historyTotalOverdue").val())){
			$('#errorHistory').attr("style", "display:block;font-size:14px;color:red");	
			$('#errorCurrent').attr("style", "display:none;");
			return false;
		}else if(!validateNum($("#badDebtOverdue").val())){
			$('#errorBadDebt').attr("style", "display:block;font-size:14px;color:red");	
			$('#errorHistory').attr("style", "display:none;");
			return false;
		}else{
			$('#errorCurrent').attr("style", "display:none;");
			$('#errorHistory').attr("style", "display:none;");
			$('#errorBadDebt').attr("style", "display:none;");
		}
		$.ajax({
	        type: "POST",
	        url:'/risk/saveInfoDisclosure',
	        data:$('#basicForm').serialize(),// 你的formid              
	        error: function( e ) {
	        	alert("异常");
	        },
	        success: function(data) {
	        	if(data == 'ok'){
	                 alert('添加成功');
	                 window.location = "/risk/infoDisclosureList";
	             }else{
	            	 alert('添加失败');
	             }                
	            }
	        });		 		
				
	}
	
</script>

</body>
</html>
    