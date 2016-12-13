<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <title>活期宝车贷等额本息提前还款</title>
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
      <h2><i class="fa fa-table"></i>当前位置：车贷等额本息提前还款 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
			<p style="font-size:14px;color:red;">1、当天若有提前还款项目，则需在当天活期宝补充续投资产之前操作提前还款</p>
			<p style="font-size:14px;color:red;padding-bottom: 20px;">2、还款日当天不可提前还款，如果还款日当天操作提前还款，则实际借款期限需往后延一期。</p>
			 <form class="form-inline" id="form1" method="post">
			 <div class="form-group">
	             <label class="sr-only" for="exampleInputparentId">主标编号</label>
	             <input type="text" name="parentId" value="${parentId}" class="form-control" id="parentId" placeholder="主标编号">
	         </div>
	         <div class="form-group">
	              <label class="sr-only" for="exampleInputloanTerm">实际还款期限</label>
	                <select class="select2" name="loanTerm" id="loanTerm" data-placeholder="实际还款期限">
	                  <option value="">--实际借款期限</option>
	                  <option value="1">1个月</option>
	                  <option value="2">2个月</option>
	                  <option value="3">3个月</option>
	                  <option value="4">4个月</option>
	                  <option value="5">5个月</option>
	                  <option value="6">6个月</option>
	                  <option value="7">7个月</option>
	                  <option value="8">8个月</option>
	                  <option value="9">9个月</option>
	                  <option value="10">10个月</option>
	                </select>
	          </div>
	     <button type="submit" id="search" class="btn btn-primary">查询</button>	
 <c:if test="${error=='sucess'}">
 <span style="padding-left: 35px;">
 <button class="btn btn-primary" onclick="editRepament()" type="button">确认提前还款</button>
 </span>
 </c:if> 
	       <c:if test="${error=='loanTermError'}">	   
	      <span style="font-size:14px;color:red;padding-left: 20px;">实际借款期限不足</span>   
	      </c:if>
	       <c:if test="${error=='parentIdNull'}">	   
	      <span style="font-size:14px;color:red;padding-left: 20px;">主标编号错误</span>   
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
                       <th>序号</th>
				        <th>子标编号</th>
				        <th>子标金额</th>
				        <th>子标期限</th>
				        <th>子标状态</th>
				        <th>原子标结束日期</th>
				        <th>子标实际结束日期</th>
				       
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo}" var="item"  varStatus="status">
                      <tr>                                            
						<td>${status.index + 1}</td>
						<td>${item.id}</td>						
						<td>${item.totalMoney}</td>
						<td>${item.month}个月</td>
						<td>
						<c:if test="${item.loanStatus eq 'finish'}">
                      	已完成
                     	</c:if>
                     	 <c:if test="${item.loanStatus eq 'repay'}">
                      	还款中
                     	</c:if>
						</td>   
						<td><fmt:formatDate value="${item.finishTime }" pattern="yyyy-MM-dd" /></td>
						<td>
						<c:choose>
						<c:when test="${item.finishTime<=startTime }">
						<fmt:formatDate value="${item.finishTime }" pattern="yyyy-MM-dd" />
						</c:when>
						<c:otherwise>
						<span style="color:red;"><fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd" />
						</span>
						</c:otherwise>						
						</c:choose>
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
$('#loanTerm').val('${loanTerm}');
$("button[id=search]").click(function(){
	$("#form1").attr("action","/demand/prepayment");
	$("#form1").submit();
});
function editRepament(){
var parentId=$("#parentId").val();
var loanTerm=$("#loanTerm").val();
var readDemadfork='${pageInfo}';	
/* alert(readDemadfork); */
if(readDemadfork==null){
	alert("查询信息有错，不能修改");
	return false;
}
if(confirm("您确定要提前还款吗？")){
	 $.ajax({
		type : "POST",
		url : '/demand/editPrepayment',
		data : $('#form1').serialize(),// 你的formid
		async : false,
		error : function(e) {
			alert("异常");
		},
		success : function(data) {
			if (data == 'ok') {
				alert('提前还款完成');
				window.location = "/demand/prepayment?parentId="+parentId+"&loanTerm="+loanTerm;
			}else{		
				alert('提前还款失败');
			}
		}
	});
  }
}
</script>

</body>
</html>
