<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<title>纸质优惠券查询</title>

</head>
<body>
	<!-- Preloader -->
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<jsp:include page="../invest/redPacketForDiaglogOpen.jsp"></jsp:include>
		<%-- <%@include file="../invest/redPacketForDiaglogOpen.jsp"%> --%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：纸质优惠券查询
				</h2>
			</div>
		<form class="form-inline" id="form1"  action="/redPacketDetail/redpacketCouponListByPid" method="post">		    	 
	     <div class="contentpanel">
				<input id="pid" value="${pid}" name="pid" type="hidden"/>
				<div class="input-group" >              
                <input type="text" class="form-control"  name="start" value="${start}" placeholder="创建开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control"   name="end" value="${end}" placeholder="创建结束时间" id="datepicker1">
                </div>
            <div class="input-group" >
             <span>使用状态</span>
                 	<select id="usedStatus" name="usedStatus">
                 		<option value="">全部</option>
                 		<option value="0">未使用</option>
                 		<option value="1">已使用</option>
                 	</select>
              </div>
              <div class="input-group" >
             <span>发行状态</span>
                 	<select id="releaseStatus" name="releaseStatus">
                 		<option value="">全部</option>
                 		<option value="0">未发行</option>
                 		<option value="1">已发行</option>
                 	</select>
              </div>
            <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"/>
	     	<input type="hidden" id="pid" value="" />
	     	<c:if test="${flag==0}">
	     	<input type="button" id="export" class="btn btn-primary"  onclick="exportRedpacketCoupon()" style="display:inline-block;" value="导出"/>
	     	</c:if>
	     </div>
	          </form>
	          <script type="text/javascript">
	          function addRedpacketCoupon(){
	        	  window.location.href="/redPacketDetail/toAddredpacketCoupon";
	          }
	          </script>
	          <script type="text/javascript">	
	          $("#usedStatus").val('${usedStatus}');
	          $("#releaseStatus").val('${releaseStatus}');
	          jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
			  jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
			</script>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>编号</th>
									<th>优惠券码</th>
									<th>创建时间</th>
									<th>使用状态</th>
									<th>激活人编号</th>
									<th>发行状态</th>
									<th>操作人编号</th>
									<th>红包类型</th>
									<th>金额</th>
									<th>有效期截止时间</th>
									<th>激活时间</th>
								</tr>
							</thead>
								<c:forEach var="ActivateCoupon" items="${pageInfo.results}">
								<tr>
									
								   <td>${ActivateCoupon.id}
								   </td>
									<td>
									${base:decrypt(ActivateCoupon.redPacketCode)}
									</td>
									<td>
									<fmt:formatDate value="${ActivateCoupon.createTime}" type="both"/>
									</td>	
									<td>
									<c:if test="${ActivateCoupon.useStatus==0}">未使用</c:if>
									<c:if test="${ActivateCoupon.useStatus==1}">已使用</c:if>
									</td>	
									<td>${ActivateCoupon.userId}</td>	
									<td>
									<c:if test="${ActivateCoupon.releaseStatus==0}">未发行</c:if>
									<c:if test="${ActivateCoupon.releaseStatus==1}">已发行</c:if>
									</td>	
									<td>${ActivateCoupon.createId}</td>	
									<td>
									<c:if test="${ActivateCoupon.type==1}">现金</c:if>
									<c:if test="${ActivateCoupon.type==2}">加息</c:if>
									</td>
									<td>${ActivateCoupon.value}</td>
									<td>
									<fmt:formatDate value="${ActivateCoupon.endTime}" type="both"/>
									</td>
									<td>
									<fmt:formatDate value="${ActivateCoupon.activeTime}" type="both"/>
										</td>	
								</tr>
							</c:forEach>	
							
						</table>
					</div>
					<script type="text/javascript">
					function exportRedpacketCoupon(){
						var pid=$("#pid").val();
						window.location.href="/redPacketDetail/exportRedpacketCoupon?pid="+pid;
					}
					
					</script>
					
					<%@ include file="../base/page.jsp"%>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>
</body>
</html>


