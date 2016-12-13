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

  <title>转账列表</title> 

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
      <h2><i class="fa fa-table"></i>当前位置：转账列表 </h2>
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/trans/authList" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputEmail2">编号</label>
	              <input type="text" name="userId" value="${userId }" class="form-control" id="exampleInputEmail2" placeholder="用户编号/手机号/邮箱/身份证号">
	            </div>
	            <button type="submit" class="btn btn-primary">查询</button>
	         </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>编号</th>
                       <base:active activeId="USER_LOOK"> <th>用户编号</th></base:active>
                        <th>过期时间</th>
                        <th>提交时间</th>
                        <th>状态</th>
                        <th>金额</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                   <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                       <td>${item.id}</td>
                     <base:active activeId="USER_LOOK">  <td>${item.userId}</td></base:active>
                       <td><fmt:formatDate
									value="${item.expired }"
									pattern="yyyy-MM-dd HH:mm" /></td>
						<td><fmt:formatDate value="${item.commitTime }"
								pattern="yyyy-MM-dd HH:mm" /></td>
						 <td>${item.status }</td>
						 <td>${item.amount }</td>
						 <td>
						 <a href="/trans/authdetail?tranid=${item.id}">详情</a>
	                           <c:if test="${item.status eq 'PREAUTH'}">
	                       
	                           | <a href="javascript:authconfirm('${item.id}','CONFIRM');">确认转账</a>
	                           | <a href="javascript:authconfirm('${item.id}','CANCEL');">取消转账</a>
	                           </c:if>
						 </td>
                      </tr>
                    </c:forEach>
                  </tbody>
        </table>
 		<%@ include file="../base/page.jsp"%>

 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
<script type="text/javascript">

function authconfirm(id,flag){
	var status = true;var str='';
	if(status == true){
		if(flag=='CONFIRM'){
			str='确认要转账吗？';
		}else{
			str='确认要取消转账吗？';
		}
		if(confirm(str)){
			status == false;
			$.ajax({
				type : 'POST',
				url:"${ctx}/trans/authconfirm",		
				data:{
					'tranid':id,
					'flag':flag
				},
				beforeSend:function(){
								xval=getBusyOverlay('viewport',
								{color:'blue', opacity:0.5, text:'进行中...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
								{color:'blue', size:25, type:'o'});	
				},
				error:function(){
					window.clearInterval(xval.ntime); 
					xval.remove();
					status == true;
				},		
				success : function(msg) {				
						window.clearInterval(xval.ntime); 
						xval.remove();
						if(msg == 'ok'){							
							alert("操作成功!");	
							window.location = "/trans/authList";
							//location.reload();			
						}else{
							alert("操作失败!");					
						}
					status == true;
				}
			});
		}
	}
}


</script>
</section>
</body>
</html>
