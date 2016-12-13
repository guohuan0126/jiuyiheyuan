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
  <title>农贷客户银行卡变更</title>  
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
      <h2><i class="fa fa-pencil"></i> 农贷客户银行卡变更 </h2>
    </div>
    
    <div class="contentpanel">
      <div class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">               
                </div>
                <h4 class="panel-title">农贷客户银行卡变更</h4>
						              
              </div>			
              <div class="panel-body"> 
						<form action="/ruralfinance/agricultureEditcard" method="get">
				<table class="input-list" border="0" width="80%" cellspacing="10" cellpadding="5">
					<tr>
					<td width="20%">
							<input type="text" id="ssid" name="ssid"  value="${ssid}"  placeholder="合同编号/身份证号" style="width:250px; " >
						</td>
						<td >
							<button type="submit" id="search" class="btn btn-primary">查询</button>
						</td>
					</tr>
				</table>
				</form>

				<div id="display" style="display: none">
				  <div class="form-group">
                  <label class="col-sm-3 control-label">合同编号 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <input type="hidden" name="id" id="id" value="${Loaninfo.id }">
                    <input type="text"  name="contractId" id="contractId" value="${Loaninfo.contractId}" width="10px" class="form-control"  disabled="disabled"/>
                  </div>
                </div> 
                 <div class="form-group">
                  <label class="col-sm-3 control-label">客户姓名 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text"  name="name" id="name" value="${Loaninfo.name}" width="10px" class="form-control"  disabled="disabled"/>
                  </div>
                </div>                
                <div class="form-group">
                  <label class="col-sm-3 control-label">身份证号 <span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text"  name="idCard" id="idCard" value="${Loaninfo.idCard}" width="10px" class="form-control"  disabled="disabled"/>
                  </div>
                  <div id="errorId" style="display: none;">身份证号不能为空</div>
                </div>

                <div class="form-group">
                  <label class="col-sm-3 control-label">银行卡号<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="cardNo" id="cardNo" value="${Loaninfo.bankcard}" placeholder="银行卡号"  class="form-control"/>
                  </div>   
                   <div id="errorcardNo1" style="display: none;">银行卡号不能为空</div>
                    <div id="errorcardNo2" style="display: none;">银行卡号格式不正确</div>             
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">银行名称<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="bank" id="bank" value="${Loaninfo.bank}"   class="form-control"/>
                  </div> 
                  <div id="errorbank" style="display: none;">银行名称不能为空</div>               
                </div>
                 <div class="form-group">
                  <label class="col-sm-3 control-label">支行名称<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text" name="branchname" id="branchname" value="${Loaninfo.branchname}"  class="form-control"/>
                  </div>   
                   <div id="errorbranchname" style="display: none;">支行名称不能为空</div>             
                </div>
                
             </div>
              </div><!-- panel-body -->
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3"  style="display: none" id="footer">
                    <input type="button" class="btn btn-primary" onclick="sub();" value="保存"/>
                  </div>
                </div>
              </div>
            
          </div><!-- panel -->
      </div> 
    </div><!-- contentpanel -->
    
  </div><!-- mainpanel -->
 
</section>

<script>		
 var result=$("#id").val();
	if(result!=''){
		$("#display").show();
		$("#footer").show();
	}		
	
	function sub(){
		$('#errorId').attr("style", "display:none;");
		$('#errorcardNo1').attr("style", "display:none;");
		$('#errorcardNo2').attr("style", "display:none;");
		var reg = /^\d{19}$/g; // 以19位数字开头，以19位数字结尾
		if($("#idCard").val() == ''){
			$('#errorId').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			$('#errorId').attr("style", "display:none;");
		}
		if($("#cardNo").val() == ''){
			$('#errorcardNo1').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			$('#errorcardNo1').attr("style", "display:none;");
		}
		if($("#bank").val() == ''){
			$('#errorbank').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			$('#errorbank').attr("style", "display:none;");
		}
		if($("#branchname").val() == ''){
			$('#errorbranchname').attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			$('#errorbranchname').attr("style", "display:none;");
		}
		var id=$("#id").val();
		var cardNo=$("#cardNo").val();
		var bank=$("#bank").val();
		var branchname=$("#branchname").val();
		$.ajax({
			type : "POST",
			dataType:'text',
			url : '/updateLoaninfobank',
			data : {id:id,cardNo:cardNo,bank:bank,branchname:branchname},// 你的formid
			success : function(data) {
				if (data == 'success') {
					alert('银行卡变更成功');
					window.location = "/ruralfinance/agricultureEditcard";
				}else{
					alert('银行卡变更失败');
				}
			}
		});	
				
	}
</script>

</body>
</html>
    