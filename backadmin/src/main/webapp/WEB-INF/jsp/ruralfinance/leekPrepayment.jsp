﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <title>农贷等额本息提前还款</title>
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
      <h2><i class="fa fa-table"></i>当前位置：农贷等额本息提前还款 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
			<p style="font-size:14px;color:red;">1、当天若有提前还款项目，则需在当天活期宝补充续投资产之前操作提前还款</p>
			<p style="font-size:14px;color:red;padding-bottom: 20px;">2、还款日当天不可提前还款，如果还款日当天操作提前还款，则实际借款期限需往后延一期。</p>
			 <form class="form-inline" id="form1" method="post">
			 <div class="form-group">
	             <label class="sr-only" for="exampleInputcontractId">农贷总合同编号</label>
	             <input type="text" name="contractId" value="${contractId}" class="form-control" id="contractId" placeholder="农贷总合同编号">
	         </div>
	         <div class="form-group">
	              <label class="sr-only" for="exampleInputloanTerm">实际还款期限</label>
	                <select class="select2" name="loanTerm" id="loanTerms" data-placeholder="实际还款期限">
	                  <option value="">--实际借款期限</option>
	                  <option value="1">第1期</option>
	                  <option value="2">第2期</option>
	                  <option value="3">第3期</option>
	                  <option value="4">第4期</option>
	                  <option value="5">第5期</option>
	                  <option value="6">第6期</option>
	                  <option value="7">第7期</option>
	                  <option value="8">第8期</option>
	                  <option value="9">第9期</option>
	                  <option value="10">第10期</option>
	                </select>
	          </div>
	     <button type="submit" id="search" class="btn btn-primary">查询</button>	 
	   <%--   <base:active activeId="0eef87c0bb4e419a8529601850d2c056"> --%>
		 <c:if test="${error=='sucess'}">
		  <div class="input-group" >
		  			<span style="font-size: 15px;padding-left: 60px;">线下是否已结清：</span>
		                 	<select id="settle" name="settle">
		                        <option value="否">否</option>
		                 		<option value="是">是</option>                 		
		                 	</select>
		  </div>
		 <span style="padding-left: 20px;">
		 <button class="btn btn-primary" onclick="editRepament()" type="button">确认提前还款</button>
		 </span>
		 </c:if> 
		<%--  </base:active> --%>
	       <c:if test="${error=='loanTermError'}">	   
	      <span style="font-size:14px;color:red;padding-left: 20px;">实际借款期限不足</span>   
	      </c:if>
	       <c:if test="${error=='contractIdNull'}">	   
	      <span style="font-size:14px;color:red;padding-left: 20px;">合同编号错误</span>   
	      </c:if>
	       <c:if test="${error=='loanTermNull'}">	   
	      <span style="font-size:14px;color:red;padding-left: 20px;">实际借款期限不能为空</span>   
	      </c:if>         
         </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                       <th>还款编号</th>
                       <th>还款人姓名</th>
						<th>还款方式</th>
						<th>还款日期（应还日期）</th>
						<th>实际还款日期</th>
						<th>每月应还金额</th>
						<th>每月实际还金额</th>
						<th>应还本金</th>
						<th>应还利息</th>
						<th>逾期罚息</th>
						<th>服务费</th>
						<th>退还服务费</th>
						<th>期数（第几期还款）</th>
					    <th>状态</th>				       
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo}" var="item"  varStatus="status">
                      <tr>                                            
						<td>${item.id}</td>
						<td>${item.name}</td>						
						<td>${item.repayType}</td>
						<td><fmt:formatDate value="${item.repayDate }" pattern="yyyy-MM-dd" /></td>
						<td>
						<c:choose>
						<c:when test="${item.repayDate<startTime }">
						<fmt:formatDate value="${item.repayDate }" pattern="yyyy-MM-dd" />
						</c:when>
						<c:otherwise>
						<span style="color:red;"><fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd" />
						</span>
						</c:otherwise>						
						</c:choose>
						</td>  
						<td>${item.monthMoney}</td>	
						<td>${item.realrepayMoney}</td>	
						<td>${item.corpus}</td>	
						<td>${item.intereat}</td>
						<td>${item.latePenalty}</td>
						<td>${item.serviceFee}</td>
						<td>${item.returnMoney}</td>
						<td>${item.period}个月</td>    
						<td>
						<c:if test="${item.repayStatus=='unrepay'}">未还</c:if>
						<c:if test="${item.repayStatus=='repayfail'}">还款失败</c:if>
						<c:if test="${item.repayStatus=='finish'}">还清</c:if>
						</td>	      												
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
 

 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>
<script>
$('#loanTerms').val('${loanTerm}');
$("button[id=search]").click(function(){
	$("#form1").attr("action","/ruralfinance/leekPrepayment");
	$("#form1").submit();
});
function editRepament(){
var contractId=$("#contractId").val();
var loanTerm=$("#loanTerms").val();
if(confirm("您确定要提前还款吗？")){
	 $.ajax({
		type : "POST",
		url : '/ruralfinance/editLeekPrepayment',
		data : $('#form1').serialize(),// 你的formid
		async : false,
		error : function(e) {
			alert("异常");
		},
		success : function(data) {
			if (data == 'ok') {
				alert('提前还款修改完成');
				window.location = "/ruralfinance/leekPrepayment?contractId="+contractId+"&loanTerm="+loanTerm;
			}else{		
				alert('提前还款修改失败');
			}
		}
	});
  }
}
</script>
</body>
</html>
