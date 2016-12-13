<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
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
width: 280px;}

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

  <title>还款列表</title>
</head>
<body>
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section> 
 <%@include file="../base/leftMenu.jsp"%> 
  <div class="mainpanel">   
	<%@include file="../base/header.jsp"%>   
	 
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：还款列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline" id="form1"  action="/loan/repayList/operationTime" method="post">		    	 
		    	<div class="form-group">              
	            	<input type="text" name="loanId"  value="${loanId}" class="form-control" placeholder="项目编号" style="width:160px">
	            </div>
	            <div class="form-group">              
	            	<input type="text" name="loanName"  value="${loanName}" class="form-control" placeholder="项目名称" style="width:160px">
	            </div>
	            <div class="form-group">
					 <input type="text" name="nameOrMobile" value="${nameOrMobile}" class="form-control" placeholder="用户姓名/手机号" style="width:160px">
				</div>		    	 
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">状态</label>
	                <select class="select2" name="status" id="st" data-placeholder="状态...">
	                  <option value="">--状态</option>
	                  <option value="还款中">还款中</option>
	                  <option value="完成">完成</option>
	                </select>
	            </div>
               
                
                <label class="control-label">应还款时间：</label>             
	            <%-- <div class="input-group" >
                <input type="text" class="form-control datepicker" name="start" value="${start }" placeholder="开始时间" style="width:140px">
               
                </div> -- 
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" name="end" value="${end }" placeholder="结束时间" style="width:140px">
                
                </div> --%>
                <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                <br/>
                <br/>
                 <label class="control-label">上线时间：</label>   
                <div class="input-group" >
                <input type="text" class="form-control" name="onLinestart" value="${onLinestart}" placeholder="开始时间" id="datepicker2">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="onLineend" value="${onLineend}" placeholder="结束时间" id="datepicker3">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
	                                 
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	            <button id="operationTime" type="button"  class="btn btn-primary">实际还款日排序</button>
	            <button id="repayDay" type="button"  class="btn btn-primary">还款日排序</button>
	            <button id="export" type="button"  class="btn btn-primary">导出数据</button>
	           <!--  <input type="button" class="btn btn-primary" id="operationTime" value="实际还款日排序">
	            <input type="button" class="btn btn-primary" id="repayDay"  value="应还款日排序"> -->
	           <!--  <button type="button" class="btn btn-primary" onclick="javascript:reset()">重置</button> -->
	          </form>
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
               <base:active activeId="REPAY_ID"> <th >ID</th></base:active>
                <th >项目ID/项目名称</th>
                 <th >用户编号</th>
                <th >真实姓名</th>
                <th >本金</th>
                <th >利息</th>
                <th >期号</th>
                <th >起息日</th> 
                <th >还款日</th> 
                <th >实际还款日</th> 
                <th>上线时间</th>               
                <th >状态</th>
                <!-- <th >补息状态</th>
                <th >补息时间</th>
                <th >加息券状态</th>
                <th >加息券时间 </th>  -->           
                <th>操作</th>  
                                                    
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="repay" items="${pageInfo.results}">
		     <tr class="font">		     	
				<base:active activeId="REPAY_ID"> <td>${repay.id}</td></base:active>
		    <td>
			 ${repay.loanId}/${repay.loan.name}
			</td>
			
			<td>
			 ${repay.userId}
			</td>
			<td>
			 ${repay.user.realname}
			</td>
				 <td>
				 <c:if test="${repay.corpus ge 10000}">
									<fmt:formatNumber value="${repay.corpus / 10000}" maxFractionDigits="3"/>万
									</c:if>
			<c:if test="${repay.corpus lt 10000}">
				<fmt:formatNumber value="${repay.corpus}" maxFractionDigits="3"/>元
			</c:if>
				 
				 </td>
			<td>
			<fmt:formatNumber value="${repay.interest}" maxFractionDigits="3"/>元
					<%--  <c:if test="${repay.interest ge 10000}">
										<fmt:formatNumber value="${repay.interest / 10000}" maxFractionDigits="3"/>万
										</c:if>
				<c:if test="${repay.interest lt 10000}">
					<fmt:formatNumber value="${repay.interest}" maxFractionDigits="3"/>元
				</c:if> --%>
			</td>
			<td>
			 ${repay.period}
			</td>
			<td><fmt:formatDate value="${repay.time }"
			pattern="yyyy-MM-dd HH:mm" /></td>
			<td><fmt:formatDate value="${repay.repayDay }"
												pattern="yyyy-MM-dd HH:mm" /></td>
			<td><fmt:formatDate value="${repay.operationTime }"
			pattern="yyyy-MM-dd HH:mm" /></td>
			<td><fmt:formatDate value="${repay.loan.commitTime}"
			pattern="yyyy-MM-dd HH:mm" /></td>	
			 <td>
			 ${repay.status}
			</td>
			<%--
			<td>
			<c:if test="${repay.sendAllowancStatus eq 0}">
				未发送
			</c:if>
			<c:if test="${repay.sendAllowancStatus eq 1}">
				发送成功
			</c:if>
			<c:if test="${repay.sendAllowancStatus eq -1}">
				发送失败
			</c:if>
			</td>
			<td><fmt:formatDate value="${repay.sendAllowanceTime }"
			pattern="yyyy-MM-dd HH:mm" /></td>
			<td>
			<c:if test="${repay.sendRedpacketStatus eq 0}">
				未发送
			</c:if>
			<c:if test="${repay.sendRedpacketStatus eq 1}">
				发送成功
			</c:if>
			<c:if test="${repay.sendRedpacketStatus eq -1}">
				发送失败
			</c:if>
			</td>
			<td>
			<fmt:formatDate value="${repay.sendRedpacketTime }" pattern="yyyy-MM-dd HH:mm" />
			</td> --%>
			<td><a href="/loan/repayInvest?id=${repay.id}">查看</a></td>	
										
		     </tr>
		 </c:forEach>
         </tbody>          
        </table> 
        <div style="font-size: 15px;color: red; ">
        	本金总计： ￥<fmt:formatNumber value="${principalAnd}"
								maxFractionDigits="2" />元
						<%-- <c:if test="${principalAnd ge 10000}">
							<fmt:formatNumber value="${principalAnd / 10000}"
								maxFractionDigits="4" />
							<i>万</i>
						</c:if>
						<c:if test="${principalAnd lt 10000}">
							<fmt:formatNumber value="${principalAnd}"
								maxFractionDigits="4" />
							<i>元</i>
						</c:if> --%>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp; 利息总计：￥<fmt:formatNumber value="${interestAnd}"
								maxFractionDigits="2" />元
						<%-- <c:if test="${interestAnd ge 10000}">
							<fmt:formatNumber value="${interestAnd / 10000}"
								maxFractionDigits="4" />
							<i>万</i>
						</c:if>
						<c:if test="${interestAnd lt 10000}">
							<fmt:formatNumber value="${interestAnd}"
								maxFractionDigits="4" />
							<i>元</i>
						</c:if>   --%>
		</div>    	
        <%@ include file="../base/page.jsp"%>
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
               	 jQuery('#datepicker2').datepicker();
               	 jQuery('#datepicker3').datepicker();
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
                    	
                    	 $("button[id=operationTime]").click(function(){
                    		$("#form1").attr("action","/loan/repayList/operationTime");
        				    $("#form1").submit();
                    	});
        	            $("button[id=repayDay]").click(function(){
        	            	$("#form1").attr("action","/loan/repayList/repayDay");
        				    $("#form1").submit();
        				}); 
        	            $("button[id=export]").click(function(){
        	           			$("#form1").attr("action","/loan/repayExport");
        	           		    $("#form1").submit();
        	            });
        	           
       </script>  
</body>
</html>
