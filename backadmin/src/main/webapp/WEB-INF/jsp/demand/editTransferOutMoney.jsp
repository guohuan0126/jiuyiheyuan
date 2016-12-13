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
<link rel="shortcut icon" href="${ctx}/images/favicon.png"
	type="image/png">

<title>活期宝修改赎回金额</title>
<style type="text/css">
.xgshje{font-size:14px;color:#2b2b2b;line-height:40px;float:left;}
.xgshje span{color:red;}
.xgshje-ts{color:red;font-size:14px;margin-left:20px;}
</style>
</head>

<body>
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
					<i class="fa fa-table"></i>当前位置：活期修改赎回金额
				</h2>				
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
					
						<form class="form-inline" action="${ctx}/demand/editTransferOutMoney" method="post">
							<div class="form-group">
								<div class="col-sm-1">
									<input type="text"  class="form-control"  name="userId" value="${bill.userId}" placeholder="用户编号/手机号/身份证号"/>
								</div>
							</div>								           
	            			<button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
						</form>					
					</div>
					<div class="panel-body">
						<c:if test="${not empty accounts}">
						活期宝总本金：${accounts[11]}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可提本金：${accounts[3]}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可提利息 ：${accounts[4] }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请赎中的金额：${accounts[5]} 
						</c:if><br>
					</div>
					<div class="panel-body">
		   <span style="font-size: 15px;margin-bottom: 15px;">赎回记录：</span>
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
							   <th>用户编号</th>
							    <th>用户姓名</th>
							    <th>用户手机号</th>
							    <th>金额</th>
							    <th>本金</th>
							    <th>利息</th>
							    <th>发起时间</th>
							    <th>途径</th>
							    <th>状态</th>
							    <th>资产是否匹配</th>
								</tr>
							</thead>
							<tbody>
			 <c:forEach  var="b" items="${pageInfo.results}">
		       <tr class="font">		     	
				 <td>${b.userId}</td>
				 <td>${b.user.realname}</td>
				 <td>${b.user.mobileNumber}</td>
				 <td>${b.money}</td>
				 <td>${b.principal}</td>
				 <td>${b.interest}</td>
				 <td><fmt:formatDate value="${b.sendedTime }" type="both"/></td>
				 <td>${b.transferWay}</td>
				 <td>
				 	<c:if test="${b.status == 'sended'}">发起转出</c:if>
				 	<c:if test="${b.status == 'fail'}">失败</c:if>
				 	<c:if test="${b.status == 'success'}">成功</c:if>
				 </td>
				<td>
				 	<c:if test="${b.fork =='1'}">已匹配</c:if>
				 	<c:if test="${b.fork =='0'}">未匹配</c:if>
				 </td>
		     </tr>
		 </c:forEach>
							</tbody>
						</table>
						记录数量：${pageInfo.totalRecord}&nbsp;&nbsp;&nbsp;
	
						
						<%-- <%@ include file="../base/page.jsp"%> --%>
			      <div class="form-group">
					<label class="xgshje">修改赎回金额 <span>*</span></label>
					<div class="col-sm-3">
					 <c:forEach  var="b" items="${pageInfo.results}"  begin="0" end="0" step="1">
					 <input type="hidden" id="bid" name="id" value="${b.id}">
					 <input type="hidden" id="userId" name="userId" value="${b.userId}">
					 <input type="hidden" id="binterest" name="binterest" value="${b.interest}">
					 <input type="hidden" id="bprincipal" name="principal" value="${b.principal}">
					</c:forEach>
					<input type="hidden" id="extractMoney" name="extractMoney" value="${accounts[3]}">
						<input type="text" name="totalMoney" id="totalMoney" value="${demandtreasureLoan.totalMoney}" 
							class="form-control" placeholder="赎回金额 " />
						
					</div>
					<button class="btn btn-primary" type="button" onclick="sub()">保存</button>
					<span class="xgshje-ts" id="errortotalMoney" style="display: none;">赎回金额不能为空</span>
				</div>
                   									
				   
					</div>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>
	<script type="text/javascript">
	 //判断空
	   function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		};
	function sub(){	
		var pagesize='${pageInfo.results.size()}';
		var totalMoney=parseFloat($("#totalMoney").val());
		var extractMoney=parseFloat($("#extractMoney").val());
		var bprincipal=parseFloat($("#bprincipal").val());
		var binterest=parseFloat($("#binterest").val());		
		var userId=$("#userId").val();
		var bid=$("#bid").val();
		var money=parseFloat(extractMoney+bprincipal).toFixed(2);
		
		if(isNull(pagesize)){
			$("#errortotalMoney").html("请查询赎回信息后再操作").show();
			return false;
		}else if(pagesize=="0"){
			$("#errortotalMoney").html("该用户没有赎回记录，不能修改赎回金额").show();
			return false;
		}else if(isNull(totalMoney)){
			$("#errortotalMoney").html("请输入赎回的金额").show();
			return false;
		}else if(totalMoney>money){
			$("#errortotalMoney").html("修改的赎回金额不能超过用户当前有效的总本金("+money+")").show();
			return false;
		}						
		if(confirm('确认修改赎回金额吗?')){			
			$.ajax({
				type : 'POST',
				dataType:'text',
				url : "/demand/updateTransferOutMoney",						
				data: {"id":bid,"totalMoney":totalMoney,"extractMoney":extractMoney,"bprincipal":bprincipal,"binterest":binterest,"userId":userId},
				success:function(data) {
					if(data=='success'){
						alert("修改成功");
						window.location.href="/demand/editTransferOutMoney?userId="+userId;
					}else if(data=='moneyError'){
						alert("修改的赎回金额不能超过用户当前有效的总本金");
						window.location.href="/demand/editTransferOutMoney?userId="+userId;
					}else{
						alert("修改失败");
						window.location.href="/demand/editTransferOutMoney?userId="+userId;
					}
				}
			});	
		}
	}	
	</script>
</body>
</html>
