<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<style type="text/css">
.jkrxx-title {
	height: 30px;
	font-size: 16px;
	color: #333;
	padding-left: 23px;
}

.jnb-screen {
	height: 40px;
	line-height: 40px;
	padding-left: 0px;
}

.jnb-xmxq {
	width: 1295px;
	border: 1px solid #ddd;
}

.jnb-xmxq td,.jnb-xmxq th {
	height: 40px;
	line-height: 40px;
	text-align: center;
	font-weight: normal;
}

.jnb-xmxq th {
	background-color: #e8e8e8;
}

.jnb-zje {
	overflow: hidden;
	width: 320px;
	margin: 10px 0;
}

.jnb-zje label {
	width: 100px;
	padding: 5px 0;
	text-align: right;
	float: left
}

.jnb-zje input {
	width: 200px;
	height: 17px;
	padding: 5px;
	float: right
}
</style>
<title>添加活期宝资产</title>
</head>
<body>
	<!-- 配置文件 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
	<!-- Preloader -->
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-pencil"></i> 添加活期宝资产
				</h2>
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="panel-btns"></div>
						<h4 class="panel-title">添加活期宝资产</h4>
					</div>

					<form id="form1" method="post" class="form-horizontal"
						style="padding-left:23px;">
						<div class="jnb-screen">
							<label>筛选周期</label> <select name="loanterm" id="loanterm">
							    <option value="6">6个月</option>
								<option value="7">7个月</option>
								<option value="8">8个月</option>
								<option value="9">9个月</option>
								<option value="10">10个月</option>
								<option value="11">11个月</option>
								<option value="12">12个月</option>
								<option value="13">13个月</option>
								<option value="14">14个月</option>
								<option value="15">15个月</option>
								<option value="16">16个月</option>
								<option value="17">17个月</option>
								<option value="18">18个月</option>
								<option value="19">19个月</option>
								<option value="20">20个月</option>
								<option value="21">21个月</option>
								<option value="22">22个月</option>
								<option value="23">23个月</option>
								<option value="24">24个月</option>
							</select> <label style="padding-left:223px;">打包总金额：</label> <span
								id="totalMoney"></span> <input name="LoandataIds"
								id="LoandataIds" type="hidden" />
						</div>

						<!-- js获取打包的数据 -->
						<div id="packaging"></div>
						<div style="padding-bottom:10px;">
							<button id="savebut" class="btn btn-primary" type="button"
								onclick="return sub()" style="margin:10px 600px 0;">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		</div>
	</section>

	<script type="text/javascript">
	var deadline = $("#loanterm").val();
	$(function(){

		//totalMoney.value = 1111;
		
	});
	//按项目周期查询打包项目 
findInfo(1,10000,deadline,'${loanId}','demand');
$("#loanterm").change( function() {
	  var loanTerm=$("#loanterm").val();
	  findInfo(0,10000,loanTerm,'${loanId}','demand');
	  $("#totalMoney").html("");
	
});
function findInfo(no,size,loanTerm,loanId,type){	  
		  
		  $.ajax({
			  url:"/agricultural/PackagingLoan",
			  type:"post",
			  data:{pageNo:no,pageSize:size,loanTerm:loanTerm,loan_id:loanId,type:type},
			  dataType:"json",
			  success:function(pageInfo){
             	  var htmlStr = "<table class=\"jnb-xmxq\" cellpadding=\"0\" cellspacing=\"0\">";
             	 htmlStr += "<thead style=\"display:block;\">";
            	 htmlStr += "<th width=\"42px\"><input type=\"checkbox\" id=\"checkall\" class=\"choose-all\" money=\""+0+"\"/><label>全选</label></th>";
                 htmlStr += "<th width=\"71px\">借款人</th><th width=\"90px\">手机号</th><th width=\"137px\">身份证号</th><th width=\"162px\">子合同编号</th><th width=\"77px\">子标金额</th> <th width=\"72px\">子标期限</th><th width=\"158px\">父标合同编号</th>";
                 htmlStr += "<th width=\"81px\">合同金额</th><th width=\"79px\">借款金额</th><th width=\"79px\">服务费用</th><th width=\"79px\">借款期限</th> <th width=\"74px\">利率</th><th width=\"92px\">还款方式</th>";                 
                 htmlStr += " </thead><tbody style=\"width:1293px;height:600px;overflow-y:scroll;display:block;\">";                 
				  var arr = pageInfo.list;
				  for(var i in arr){
					  var obj = arr[i];	
		              htmlStr += "<tr>";
					  htmlStr += "<td width=\"42px\"><input type=\"checkbox\" name=\"checkname\" value="+obj.id+" class=\"choose-all\" money=\""+obj.money+"\" />&nbsp;&nbsp;</td>";
					  htmlStr += " <td width=\"71px\">"+obj.username+"</td>";
					  htmlStr += " <td width=\"124px\">"+obj.mobileNum+"</td>";
					  htmlStr += " <td width=\"137px\">"+obj.idCard+"</td>";
					  htmlStr += " <td width=\"162px\">"+obj.contractid+"</td>";
					  htmlStr += " <td width=\"77px\">"+obj.money+"</td>";
					  htmlStr += " <td width=\"72px\">"+obj.loan_term+"个月</td>";
					  htmlStr += " <td width=\"158px\">"+obj.parentContractId+"</td>";
					  htmlStr += " <td width=\"81px\">"+obj.parentMoney+"</td>";
					  htmlStr += " <td width=\"79px\">"+obj.loanMoney+"</td>";
					  htmlStr += " <td width=\"79px\">"+obj.serviceMoney+"</td>";
					  htmlStr += " <td width=\"79px\">"+obj.parentLoanTerm+"个月</td>";
					  htmlStr += " <td width=\"74px\">"+obj.rate+"</td>";
					  htmlStr += " <td width=\"103px\">"+obj.repay_type+"</td>";
					  htmlStr += " </tr>"; 					  
				  }
				  htmlStr += "</tbody></table>";  
				  $("#packaging").html(htmlStr);  //表格
				  $(":checkbox").change(function(){
	            	  moneychange();
	              });
				  function checkLoan(){
						var arr = new Array();
						$("input:checkbox[name=checkname]:checked").each(function(i){						
							  arr.push($(this).val());
						}); 
						return arr;
					}
				  
				  function moneychange(){
					  var money = 0;
	            	  $(":checkbox").each(function(){
	            		  if($(this).is(':checked')){
	            			  money += parseFloat($(this).attr("money"));
	            		  }
	            	  });        	 
	            	  $("#totalMoney").html(parseFloat(money).toFixed(2));			
	            	  $("#LoandataIds").attr("value",checkLoan());
					  
				  }
	              $("#checkall").click(function(){ 
						 if(this.checked){ 
						        $("input:checkbox[name=checkname]").attr('checked', true);
						    }else{ 
						        $("input:checkbox[name=checkname]").attr('checked', false);
						    } 
						 moneychange();
				  });
			  }
		  });
		  
	  }
	
	  var regu =/^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
	  var re = new RegExp(regu);
	  function checkMoney(){
			var totalMoney = $("#totalMoney").html();
			totalMoney = parseFloat(totalMoney).toFixed(2);
			if (!re.test(totalMoney) || totalMoney <= 0) {
				alert("请勾选要添加的资产");
				return false;
			}else{				
				return true;
			}
	  }

	function sub(){	
		var ids= $("#LoandataIds").val();
	
		if(checkMoney()){			
			if(confirm('确认对项目进行批量添加资产吗?')){	
				 $("#savebut").attr("disable",true); 
				$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/demand/addForkLoans",						
					data: {"ids":ids},
					beforeSend:function(){
						xval=getBusyOverlay('viewport',
						{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
						{color:'blue', size:25, type:'o'});	
					},
					error : function(e) {
						alert("异常");
						window.clearInterval(xval.ntime); 
						xval.remove();
					},
					success:function(data) {
						window.clearInterval(xval.ntime); 
						xval.remove();
						if(data=='success'){
							alert("资产批量添加成功");
							 $("#savebut").attr("disable",false); 
							window.location.href="/demand/loanList";
						}else{
							alert("资产批量添加失败");
							 $("#savebut").attr("disable",false); 
							window.location.href="/demand/agricultureLoan";
						}
					}
				});	
			}
		}else{
			return false;
		}	
	}
	</script>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/dropzone.css" />
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/bootstrap-editable.css">
	<link type="text/css"
		href="${pageContext.request.contextPath}/css/jquery-ui-1.8.17.custom.css"
		rel="stylesheet" />
	<link type="text/css"
		href="${pageContext.request.contextPath}/css/jquery-ui-timepicker-addon.css"
		rel="stylesheet" />

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-1.8.17.custom.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>

</body>
</html>
