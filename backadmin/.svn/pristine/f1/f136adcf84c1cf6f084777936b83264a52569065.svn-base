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
<style type="text/css">
.ui-datepicker-title {
	text-transform: uppercase;
	font-family: 'LatoBold';
	color: #1CAF9A;
}

.ui-datepicker {
	background: #1D2939;
	margin-top: 1px;
	border-radius: 2px;
	width: 280px;
}

.ui-datepicker-next {
	background: url(../../images/calendar-arrow.png) no-repeat 10px 5px;
}

.ui-datepicker-prev {
	background: url(../../images/calendar-arrow.png) no-repeat 8px -80px;
}

.ui-datepicker table {
	color: #ffffff;
}
</style>

<title>投资信息</title>
</head>
<body>
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<%@include file="../invest/redPacketForDiaglogOpen.jsp"%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>

			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：投资记录
				</h2>
				<div class="breadcrumb-wrapper">
					<ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
						<li><a href=""></a></li>
					</ol>
				</div>
			</div>
			<div class="contentpanel ">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form class="form-inline" id="f1"
							action="/netLoanEye/investRecords" method="post">
							<div class="form-group">
								<input type="text" id="loanId" name="loanId" value="${loanId}"
									class="form-control" placeholder="项目编号" style="width:160px">
							</div>
							<div class="form-group">
								<input type="text" id="loanName" name="loanName"
									value="${loanName}" class="form-control" placeholder="项目名称"
									style="width:160px">
							</div>
							<div class="form-group">
								<label class="sr-only" for="exampleInputmobile">状态</label> <select
									class="select2" name="status" id="stLoanType"
									data-placeholder="状态...">
									<option value="">--状态</option>
									<option value="等待确认">等待确认</option>
									<option value="投标成功">投标成功</option>
									<option value="还款中">还款中</option>
									<option value="完成">完成</option>
									<option value="流标">流标</option>
								</select>
							</div>
							<div class="form-group">
								<label class="sr-only" for="exampleInputmobile">状态</label> <select
									class="select2" name="whetherNew" id="stUserType"
									data-placeholder="状态...">
									<option value="">--全部用户类型--</option>
									<option value="0">新用户</option>
									<option value="1">老用户</option>
								</select>
							</div>
							<div class="form-group">
								<input type="text" name="userId" id="userId" value="${userId}"
									class="form-control" placeholder="投资人编号" style="width:160px">
							</div>
							<div class="form-group">
								<input type="text" name="mobile" id="mobile" value="${mobile}"
									class="form-control" placeholder="投资人手机号" style="width:160px">
							</div>
							<div class="form-group">
								<input type="text" name="realname" id="realname"
									value="${realname}" class="form-control" placeholder="投资人姓名"
									style="width:160px">
							</div>

							</br>
							</br>
							<div class="input-group">
								<input type="text" class="form-control" name="start"
									value="${start}" placeholder="投资开始时间" id="datepicker">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>
							<div class="input-group">
								<input type="text" class="form-control" name="end"
									value="${end}" placeholder="投资结束时间" id="datepicker1"> <span
									class="input-group-addon"><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>

							<div class="input-group">
								<input type="text" class="form-control" name="regStart"
									value="${regStart}" placeholder="注册开始时间" id="datepickerReg">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>
							--
							<div class="input-group">
								<input type="text" class="form-control" name="regEnd"
									value="${regEnd}" placeholder="注册结束时间 " id="datepickerReg2">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-calendar"></i></span>
							</div>
							<script type="text/javascript">
	             jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
				 jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
				 jQuery('#datepickerReg').datepicker({dateFormat: 'yy-mm-dd'});				
				 jQuery('#datepickerReg2').datepicker({dateFormat: 'yy-mm-dd'});	
	            </script>
							<button type="button" id="search" class="btn btn-primary">查询</button>
							<button type="button" class="btn btn-primary"
								onclick="exportInvestBill()">下载</button>
						</form>
					</div>
					<script type="text/javascript">
	    $("#stUserType").val('${whetherNew}');
	    $("#stLoanType").val('${status}');
	    //导出已备注的用户信息
					function exportInvestBill(){
						var url="/netLoanEye/exportUserInvestInfo";
						var loanId=$("#loanId").val();
						var loanName=$("#loanName").val();
						var stLoanType=$("#stLoanType").val();
						var stUserType=$("#stUserType").val();
						var userId=$("#userId").val();
						var mobile=$("#mobile").val();
						var realname=$("#realname").val();
						var datepicker=$("#datepicker").val();
						var datepicker1=$("#datepicker1").val();
						var datepickerReg=$("#datepickerReg").val();
						var datepickerReg2=$("#datepickerReg2").val();
						var str = "";
						if(loanId!=""){
							str+="&loanId="+loanId;
						}
						if(loanName!=""){
							str+="&loanName="+loanName;
						}
						if(stLoanType!=""){
							str+="&stLoanType="+stLoanType;
						}
						if(stUserType!=""){
							str+="&stUserType="+stUserType;
						}
						if(userId!=""){
							str+="&userId="+userId;
						}
						if(mobile!=""){
							str+="&mobile="+mobile;
						}
						if(realname!=""){
							str+="&realname="+realname;
						}
						if(datepicker!=""){
							str+="&datepicker="+datepicker;
						}
						if(datepicker1!=""){
							str+="&datepicker1="+datepicker1;
						}
						if(datepickerReg!=""){
							str+="&datepickerReg="+datepickerReg;
						}
						if(datepickerReg2!=""){
							str+="&datepickerReg2="+datepickerReg2;
						}
						str = "?"+str.substring(1);
											
					window.location.href=url+=str;
					}
	    </script>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover">
							<thead>

								<tr class="font">
									<th>项目ID</th>
									<th>推送状态</th>
									<th>项目名称</th>
									<th>项目周期</th>
									<th>投资编号</th>
									<th>投资状态</th>
									<th>投资人编号</th>
									<th>投资人姓名</th>
									<base:active activeId="USER_LOOK">
										<th>手机号</th>
									</base:active>
									<base:no_active>
										<base:active activeId="USER_MUMBER">
											<th>手机号</th>
										</base:active>
									</base:no_active>
									<th>投资时间</th>
									<th>投资本金</th>
									<th>预期收益</th>
									<th>已回收收益</th>
									<th>已回收本金</th>
									<th>投标方式</th>
									<th>投标来源</th>
									<th>加息券</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="invest" items="${pageInfo.results}">
									<tr class="font">
										<td>${invest.loanId}</td>

										<td><c:if test="${invest.isPush==1 }">
												<a href="javascript:void(0);"
													onclick="pushNetLoan('${invest.loanId}');"><font
													style="color: red">已推送</font></a>
											</c:if> <c:if test="${invest.isPush!=1 }">
												<a href="javascript:void(0);"
													onclick="pushNetLoan('${invest.loanId}');">推送</a>
											</c:if></td>
										<td>${invest.loanName}</td>
										<td><c:if test="${invest.loan.operationType=='月'}">${invest.loan.deadline}月</c:if>
											<c:if test="${invest.loan.operationType=='天'}">${invest.loan.day}天</c:if>
										</td>
										<td>${invest.id}</td>
										<td>${invest.status}</td>
										<td>${invest.investUserID}</td>

										<td>${invest.investUserName}</td>

										<base:active activeId="USER_LOOK">
											<td>${invest.userMobileNumber}</td>
										</base:active>
										<base:no_active>
											<base:active activeId="USER_MUMBER">
												<td>....... ${fn:substring(invest.userMobileNumber, 7, 11)}</td>
											</base:active>
										</base:no_active>
										<td><fmt:formatDate value="${invest.time }"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td><c:if test="${invest.money ge 10000}">
												<fmt:formatNumber value="${invest.money / 10000}"
													maxFractionDigits="3" />万
									</c:if> <c:if test="${invest.money lt 10000}">
												<fmt:formatNumber value="${invest.money}"
													maxFractionDigits="3" />元
			</c:if></td>
										<td><c:if test="${invest.interest ge 10000}">
												<fmt:formatNumber value="${invest.interest / 10000}"
													maxFractionDigits="3" />万
										</c:if> <c:if test="${invest.interest lt 10000}">
												<fmt:formatNumber value="${invest.interest}"
													maxFractionDigits="3" />元
				</c:if></td>
										<td><c:if test="${invest.paidInterest ge 10000}">
												<fmt:formatNumber value="${invest.paidInterest / 10000}"
													maxFractionDigits="3" />万
										</c:if> <c:if test="${invest.paidInterest lt 10000}">
												<fmt:formatNumber value="${invest.paidInterest}"
													maxFractionDigits="3" />元
				</c:if></td>
										<td><c:if test="${invest.paidMoney ge 10000}">
												<fmt:formatNumber value="${invest.paidMoney / 10000}"
													maxFractionDigits="3" />万
										</c:if> <c:if test="${invest.paidMoney lt 10000}">
												<fmt:formatNumber value="${invest.paidMoney}"
													maxFractionDigits="3" />元
				</c:if></td>

										<td><c:choose>
												<c:when test="${invest.isAutoInvest eq true}">
					自动投标
				</c:when>
												<c:otherwise>
					手动投标
				</c:otherwise>
											</c:choose></td>
										<td>${invest.userSource}</td>
										<base:active activeId="REDPACKET_UPDATE">
											<td>
												<div class="photo">
													<div class="ui-widget-header ui-corner-all">
														<a href="javascript:void(0);" data-geo=""
															onclick="openDialog('${invest.id}','${invest.redpacketId}','${invest.userMobileNumber}','${invest.status}')">${invest.redpacketId}</a>
													</div>
												</div>
											</td>
										</base:active>
										<base:no_active>
											<td>
												<div class="photo">
													<div class="ui-widget-header ui-corner-all">
														<a href="javascript:void(0);" data-geo=""
															onclick="javascript:void(0);">${invest.redpacketId}</a>
													</div>
												</div>
											</td>
										</base:no_active>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						累计投资金额：￥${totalInvestMoney}元 累计投资用户数量：${totalInvestNum }
						<%@ include file="../base/page2.jsp"%>
					</div>
				</div>
			</div>
		</div>

	</section>
	<script type="text/javascript">
                 $("#st").val('${status}');
                 jQuery(document).ready(function(){					
                	// Date Picker
               	  jQuery('#datepicker').datepicker();
               	  jQuery('#datepicker1').datepicker();	  
               	  jQuery('#datepicker-inline').datepicker();
               	  
               	  jQuery('#datepicker-multiple').datepicker({
               	    numberOfMonths: 3,
               	    showButtonPanel: true
               	  });		 
				});
                    	jQuery(".select").select2({
							width : '100%',
							minimumResultsForSearch : -1
						});
                    	function datecheck(){
                    		 var time1 = new Date($("#datepicker1").val()); //结束时间
                    		 var time2 = new Date($("#datepicker").val());  //开始时间
                    		 var date3 = time1.getTime() - time2.getTime();   //时间差的毫秒数
                    		//计算出相差天数
                    	     var days = Math.floor(date3 / (24 * 3600 * 1000));
                    		 if($("#datepicker").val()=="" || $("#datepicker1").val()==""){
                    			 alert("查询的开始时间和结束时间不能为空");
                    			 return false;
                    		 }else if(date3<0){
                    			 alert("开始时间不能大于结束时间");
                    			 return false;
                    		 }else if(days>31){
                    			 alert("只能查询一月内的数据，日期超出范围");
                    			 return false;                   			 
                    		 }
                    		 return true;
                    	}
                    	
                    	 $("button[id=search]").click(function(){
//                     		 if(!datecheck()){return false};
                     		$("#f1").attr("action","/netLoanEye/investRecords");
         				    $("#f1").submit();
                     	});
        	           
                    	 
                    	//推送天眼项目
                    		function pushNetLoan(id){
                    		console.info(id);
                    			if( confirm("是否向网贷天眼推送此项目？") ){
                    				$.ajax({				
                    					type : 'POST',
                    					url : '/netLoanEye/pushNetLoan',
                    					data:{
                    						id:id
                    					},
                    					success : function(data) {
                    						if( data=='success' ){
                    							alert("推送成功！");
                    							location.reload();
                    						}else{
                    							alert("推送失败，请重试！");
                    							location.reload();
                    						}
                    					},
                    					error:function(){
                    						alert("操作失败！");
                    						location.reload();
                    					}
                    				});
                    			}
                    		}
                    	
                    	
                    	
                    	 
       </script>
</body>
</html>
