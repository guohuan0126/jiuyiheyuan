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
		<form class="form-inline" id="form1"  action="/redPacketDetail/redpacketCouponList" method="post">		    	 
	     <div class="contentpanel">
          		<div class="input-group" >
          		 <input type="text" class="form-control"  name="pid" value="${pid}" placeholder="批量编号" id="pid">
					</div>
				<div class="input-group" >              
                <input type="text" class="form-control"  name="start" value="${start}" placeholder="创建开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> 
                <div class="input-group" >
                <input type="text" class="form-control"   name="end" value="${end}" placeholder="创建结束时间" id="datepicker1">
                </div>
              <div class="input-group" >
             <span>是否导出</span>
                 	<select id="flag" name="flag">
                 		<option value="">全部</option>
                 		<option value="0">未导出</option>
                 		<option value="1">已导出</option>
                 	</select>
              </div>
            <input type="submit" id="query" class="btn btn-primary"  style="display:inline-block;" value="查询"/>
	    	<input type="button" id="add" class="btn btn-primary" onclick="addRedpacketCoupon()" value="添加" style="foat:right"/>
	     </div>
	          </form>
	          <script type="text/javascript">
	          function addRedpacketCoupon(){
	        	  window.location.href="/redPacketDetail/toAddredpacketCoupon";
	          }
	         $("#flag").val('${flag}');
	          </script>
	         
	         
	          <script type="text/javascript">	
	          jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
	          jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'});	
	          </script>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>批量编号</th>
									<th>创建时间</th>
									<th>创建人编号</th>
									<th>生成次数</th>
									<th>是否导出</th>
									<th>操作</th>
								</tr>
							</thead>
								<c:forEach var="ActivateCouponRecord" items="${pageInfo.results}">
								<tr>
									
									<td>${ActivateCouponRecord.id}</td>
									<td>
									<fmt:formatDate value="${ActivateCouponRecord.createTime}" type="both"/>
									</td>	
									<td>${ActivateCouponRecord.createId}</td>	
									<td>${ActivateCouponRecord.num}</td>	
									<td>
									<c:if test="${ActivateCouponRecord.flag==1}">已导出</c:if>
										<c:if test="${ActivateCouponRecord.flag==0}">未导出</c:if>
									</td>
									<td><a href="/redPacketDetail/redpacketCouponListByPid?pid=${ActivateCouponRecord.id}&flag=${ActivateCouponRecord.flag}">优惠券明细</a></td>
								</tr>
							</c:forEach>	
							
						</table>
					</div>
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


