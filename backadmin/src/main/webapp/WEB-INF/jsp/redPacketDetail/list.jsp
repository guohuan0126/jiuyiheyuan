<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
  <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
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
  <title>活动详情列表</title>
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
      <h2><i class="fa fa-table"></i>当前位置：活动详情列表</h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	   <form class="form-inline" id="form1" action="/redPacketDetail/list" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	              <input type="text" name="userId" value="${userId }" class="form-control" id="exampleInputEmail2" placeholder="用户编号/手机号/邮箱/身份证号">
	            </div>
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">活动名</label>
	                <select class="select2" name="name" id="name" data-placeholder="活动名...">
	                  <option value="">--请选择活动名称</option>
	                  <c:forEach items="${rules}" var="item">
	                 	<option value="${item.id}">${item.name}</option>  
	                  </c:forEach>
	                </select>
	            </div>
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">发送状态</label>
	                <select class="select2" name="status" id="sendstatus" data-placeholder="发送状态...">
	                  <option value="">--请选择发送状态</option>
	                  <option value="unused">unused</option>
	                  <option value="used">used</option>
	                  <option value="expired">expired</option>
	                  <option value="sended">sended</option>
	                </select>
	            </div>
	             <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">券类型</label>
	                <select class="select2" name="moneyType" id="moneyType" data-placeholder="券类型...">
	                  <option value="">--请选择券类型</option>
	                  <option value="rate">加息券</option>
	                  <option value="money">现金券</option>
	                </select>
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">可用类型</label>
	                <select class="select2" name="investType" id="investType" data-placeholder="可用类型...">
	                  <option value="">--请选择可用类型</option>
	                  <option value="invest">投资可用</option>
	                  <option value="withdraw">可申请提现</option>
	                </select>
	            </div>
	           
                
            <button type="submit" id="search" class="btn btn-primary">查询</button>
	          </form>
	</div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th>编号</th>
                <base:active activeId="USER_LOOK"><th>手机号</th></base:active>
                <th>用户ID</th>
                <th>微信号</th>                
                <th>活动开始时间</th>
                <th>活动结束时间</th>
                <th>金额</th>
                <th>活动利率</th>
                <th>券类型</th>
                <th>可用类型</th>
                <th>投资金额限制</th>
                <th>投资利率限制</th>
                <th>发送时间</th>
                <th>发送状态</th>
                <th>分享次数</th>
                <th>活动名</th>
                <th>活动</th>
                <th>操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="detail" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${detail.id}</td>
				 <base:active activeId="USER_LOOK"><td>${detail.mobile}</td></base:active>
				 <td>${detail.userId}</td>
				 <td>${detail.openId}</td>
				 <td><fmt:formatDate value="${detail.createTime}" type="both"/></td>
				 <td><fmt:formatDate value="${detail.deadline}" type="both"/></td>
				 <td>${detail.money}</td>
				 <td>${detail.rate}</td>
				 <td>
				 <c:if test="${detail.type eq 'money' }">
				 现金券
				 </c:if>
				  <c:if test="${detail.type eq 'rate' }">
				 加息券
				 </c:if>
				 </td>
				 <td>
				 <c:if test="${detail.usageDetail eq 'invest' }">
				投资可用
				 </c:if>
				  <c:if test="${detail.usageDetail eq 'withdraw' }">
				 可申请提现
				 </c:if>
				 </td>
				 <td>${detail.investMoney}</td>
				 <td>${detail.investRate*100}%</td>
				 <td><fmt:formatDate value="${detail.sendTime}" type="both"/></td>
				 <td>${detail.sendStatus}</td>
				 <td>${detail.shareCount}</td>
				 <td>${detail.name}</td>
				 <td>${detail.roleId}</td>
				 <td><a href="javascript:del(${detail.id});" >删除</a></td>
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>       	
        <%@ include file="../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>

 <script type="text/javascript">
 jQuery(document).ready(function(){
		$("#name").val('${name}');
		$("#sendstatus").val('${status}');
		$("#moneyType").val('${moneyType}');
		$("#investType").val('${investType}');
		
 });
	function del(id) {
		if(confirm("确认删除吗")){
			$.ajax({
				type : "POST",
				url : '/redPacketDetail/del',
				data:{id:id},
				async : false,
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'ok') {
						alert('删除成功');
						window.location = "/redPacketDetail/list";
					}else{
						alert('删除失败');
					}
				}
			});
		}
	} 
 </script>
</section>

</body>
</html>