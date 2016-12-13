<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
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
  <title>转入查询</title>
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
      <h2><i class="fa fa-table"></i>当前位置：转入查询</h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="${ctx }/demand/getListPageForTranferIn" method="post" id="f1">		    	 
                <label class="control-label">用户：</label>
	            <div class="form-group" style="width: 190px;">              	           
                    <input type="text" name="user.userId"  class="form-control" placeholder="用户姓名/编号/手机号" value="${transferIn.user.userId }">          
                </div>
                <div class="input-group" >
                <label class="sr-only" for="exampleInputmobile">用户来源</label>
                <input type="text" class="form-control" name="userSource" value="${transferIn.userSource}" placeholder="用户来源" id="userSource">
                </div> 
                <div class="input-group" >
                <label for="exampleInputmobile">用户来源是否为空</label>
               <input <c:if test="${not empty transferIn.userSourceIsNull}">checked</c:if> type="checkbox"  name="userSourceIsNull" value="userSourceIsNull" id="userSourceIsNull" style="vertical-align: middle;margin:0 0 0 5px;">
                </div>
                <label class="control-label">状态：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="status" name="status" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="sended"  <c:if test="${transferIn.status == 'sended'}">selected="selected"</c:if> >发起转入</option>
                      <option value="freeze"  <c:if test="${transferIn.status == 'freeze'}">selected="selected"</c:if> >转入中</option>
                      <option value="confirm"  <c:if test="${transferIn.status == 'confirm'}">selected="selected"</c:if> >成功</option>
                      <option value="fail" <c:if test="${transferIn.status == 'fail'}">selected="selected"</c:if> >失败</option>   
                      <option value="cancel" <c:if test="${transferIn.status == 'cancel'}">selected="selected"</c:if> >取消转入</option>                   
                    </select>              
                </div>
                
                <label class="control-label">途径：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="transferWay" name="transferWay" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="pc"  <c:if test="${transferIn.transferWay == 'pc'}">selected="selected"</c:if> >pc</option>
                      <option value="mobile" <c:if test="${transferIn.transferWay == 'mobile'}">selected="selected"</c:if> >mobile</option>
                      <option value="ios" <c:if test="${transferIn.transferWay == 'ios'}">selected="selected"</c:if> >ios</option>
                      <option value="android" <c:if test="${transferIn.transferWay == 'android'}">selected="selected"</c:if> >android</option>               
                    </select>              
                </div>	
                
                 <label class="control-label">资产是否已经匹配：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="fork" name="fork" class="select" required data-placeholder="请选择...">
                      <option value="">全部</option>                
                      <option value="0">未匹配</option>
                      <option value="1">已匹配</option>               
                    </select>              
                </div>	
                
                <label class="control-label">发起时间：</label>             
	            <div class="input-group" >
                	<input type="text" class="form-control datepicker" value="${transferIn.sendedTimeBeg }" id="sendedTimeBeg" name="sendedTimeBeg" placeholder="开始时间" style="width:165px">
                </div> 
	            <div class="input-group" >
                	<input type="text" class="form-control datepicker" value="${transferIn.sendedTimeEnd }" id="sendedTimeEnd" name="sendedTimeEnd" placeholder="结束时间" style="width:165px">
                </div>
                
	            <script type="text/javascript">	            		            
		           $("#fork").val('${fork}');
	            
	            jQuery(document).ready(function(){					
						 jQuery('.datepicker').datetimepicker({
							showSecond : true,
							timeFormat : 'hh:mm:ss',
							stepHour : 1,
							stepMinute : 1,
							stepSecond : 1,
						});				 
					});
		            jQuery(".select").select2({
						width : '100%',
						minimumResultsForSearch : -1
					});  
            	</script> 
            	<input type="hidden" name="pageSize" value="20" />                      
	            <button type="button" class="btn btn-primary" onclick="javascript:submit()">查询</button>
	          </form>
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th>用户编号</th>
                <th>用户姓名</th>
                <th>注册时间</th>
                <th>用户来源</th> 
                <base:active activeId="USER_LOOK"><th>用户手机号</th></base:active>
                <th>金额</th>
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
				 <td><fmt:formatDate value="${b.registerTime}" type="both"/></td>
				 <td>${b.userSource}</td>				 
				 <base:active activeId="USER_LOOK"><td>${b.user.mobileNumber}</td></base:active>
				 <td>${b.money}</td>
				 <td><fmt:formatDate value="${b.sendedTime }" type="both"/></td>
				 <td>${b.transferWay}</td>
				 <td>
					<c:if test="${b.status =='sended'}">发起转入</c:if>
				 	<c:if test="${b.status =='confirm'}">成功</c:if>
				 	<c:if test="${b.status =='fail'}">失败</c:if>
				 	<c:if test="${b.status =='freeze'}">转入中</c:if>
				 	<c:if test="${b.status =='cancel'}">取消转入</c:if>
				 </td>
				 <td>
				 	<c:if test="${b.fork =='1'}">已匹配</c:if>
				 	<c:if test="${b.fork =='0'}">未匹配</c:if>
				 </td>
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>       	
               成功转入金额：￥
		<c:if test="${summoneySuccess ge 10000}">
			<fmt:formatNumber value="${summoneySuccess / 10000}" maxFractionDigits="2"/><i>万</i>
		</c:if>
		<c:if test="${summoneySuccess lt 10000}">
			<fmt:formatNumber value="${summoneySuccess}" maxFractionDigits="2"/><i>元</i>
		</c:if>
	  成功转入人数:${sumPeopleSuccess}</br>
	  失败转入金额:￥<c:if test="${summoneyFail ge 10000}">
			<fmt:formatNumber value="${summoneyFail / 10000}" maxFractionDigits="2"/><i>万</i>
		</c:if>
		<c:if test="${summoneyFail lt 10000}">
			<fmt:formatNumber value="${summoneyFail}" maxFractionDigits="2"/><i>元</i>
		</c:if>
	 失败转入人数:${sumPeopleFail}
<%--         <%@ include file="../base/page.jsp"%> --%>
        <%@ include file="../base/page2.jsp"%>
 </div>
 </div>
 </div>
 </div>
</section>

</body>
</html>
