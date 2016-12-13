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

<title>活期宝流水记录</title>

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
					<i class="fa fa-table"></i>当前位置：活期宝流水记录
				</h2>				
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
					
						<form class="form-inline" action="${ctx}/demand/toDemandBillView" method="post">
							<div class="form-group">
								<div class="col-sm-1">
									<input type="text"  class="form-control" name="userId" value="${bill.userId}" placeholder="用户编号/邮箱/手机号/身份证号"/>
								</div>
							</div>
							<div class="form-group">
								<select id="type" name="type" class="form-control" style="width:140px;">
									<c:if test="${bill.type == 'in' }">
				                      <option value="in">转入</option>
									</c:if>
									<c:if test="${bill.type == 'out' }">
										<option value="out">转出</option>
									</c:if>
									<c:if test="${bill.type == 'interest'}">
										 <option value="interest">派息</option>
									</c:if>
									<c:if test="${bill.type == 'outinterest'}">
										<option value="outinterest">转出利息</option>  
									</c:if>
									<option value="">全部</option>
									<option value="in">转入</option>
				                    <option value="out">转出</option>
				                    <option value="interest">派息</option>
				                    <option value="outinterest">转出利息</option>                                        
                   	 			</select>             
							</div>
							<div class="input-group" >
				                <input type="text" class="form-control" name="startTime" placeholder="开始时间" id="datepicker" <c:if test="${not empty bill.startTime}"> value="<fmt:formatDate value="${bill.startTime}" type="date"/>"</c:if> >
				                <span class="input-group-addon"></span>
				            </div> -- 
				            <div class="input-group" >
				                <input type="text" class="form-control" name="endTime" placeholder="结束时间" id="datepicker1" <c:if test="${not empty bill.endTime}"> value="<fmt:formatDate value="${bill.endTime}" type="date"/>"</c:if>>
				                <span class="input-group-addon"></span>
				                
				            </div>
					            <script type="text/javascript">	            		            	       
									 jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
									 jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	  				 				
				            	</script>
	            			<button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	            			<button type="button" class="btn btn-primary" onclick="javascript:reset()">重置</button>
						</form>					
					</div>
					<div class="panel-body">
					总金额：￥ <c:if test="${not empty pageInfo.results}"><fmt:formatNumber value="${pageInfo.results[0].totalMoney}" pattern="#.##"/></c:if>
						<c:if test="${not empty accounts}">
						活期宝总金额：${accounts[0] } 昨日收益：${accounts[1] } 累计收益：<fmt:formatNumber value="${accounts[2] }" pattern="#.##"/> 本金：${accounts[3] } 可提利息 ：${accounts[4] }申赎中的金额：${accounts[5] } 转入中金额：${accounts[6] }
						</c:if><br/>
					TransferIn中累计转入金额：${accounts[7] } ,TransferOut中累计转出金额:${accounts[8] },DemandBill中累计转入金额:${accounts[9] }，demandbill中累计转出成功的金额：${accounts[10] }
					<br/>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>用户编号</th>
									<th>真实姓名</th>
									<base:active activeId="USER_LOOK">
									<th>手机号</th>
									</base:active>
									<th>金额</th>
									<th>类型</th>
									<th>时间</th>
									<th>来源</th>
									<th>状态</th>
									<th>明细</th>
									<th>备注</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="b">
									<tr>
										<td>${b.userId}</td>
										<td>${b.user.realname}</td>
										<base:active activeId="USER_LOOK">
										<td>${b.user.mobileNumber}</td>
										</base:active>
										<td><fmt:formatNumber value="${b.money}" pattern="#.##"/></td>
										<td><c:if test="${b.type == 'in'}">
												转入
											</c:if>
											<c:if test="${b.type == 'out'}">
												转出
											</c:if>
											<c:if test="${b.type == 'interest'}">
												派息
											</c:if>
											<c:if test="${b.type == 'outinterest'}">
												转出利息
											</c:if></td>
										<td><fmt:formatDate value="${b.createTime }"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${b.tranferWay}</td>
										<td>${b.status }</td>
										<td>${b.detail}</td>
										<td>${b.info}</td>												
									</tr>
								</c:forEach>
							</tbody>
						</table>
						记录数量：${pageInfo.totalRecord}&nbsp;&nbsp;&nbsp;
						
						<%@ include file="../base/page.jsp"%>

					</div>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>

	<script>
		jQuery(document).ready(function() {
			$(".ui_timepicker").datetimepicker({				
				showSecond : true,
				minDate: new Date(),
				timeFormat : 'hh:mm:ss',
				stepHour : 1,
				stepMinute : 1,
				stepSecond : 1
			});
			
		});				
	</script>
</body>
</html>
