<%@ page language="java"  contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>导入数据管理</title>

</head>
<body>
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
	
  <form  action= "/showData"  enctype="multipart/form-data"  method= "post"   > 
				 		   		文件: <input   type= "file"   name="file" id="zx"> 
								<input   type= "submit"   value= "提交" > 
						</form>
						
						<c:if test="${ts =='success' }">
						
						恭喜您完全成功导入数据
						</c:if>
						<c:if test="${ts !='success' }">
						<table>
						
						<tr>
						<td>姓名</td>
						<td>身份证</td>
						<td>手机号</td>
						</tr>
						<c:forEach items="${rl}" var="rl">
						<tr>
						
						ruralfinance_loanerinfoError.setUserName(ruralfinance_loanerinfo.getUserName());
            			ruralfinance_loanerinfoError.setIdCard(ruralfinance_loanerinfo.getIdCard());
            			ruralfinance_loanerinfoError.setMobileNumber(ruralfinance_loanerinfo.getMobileNumber());
            			ruralfinance_loanerinfoErr.add(ruralfinance_loanerinfoError);
						<td>${rl.userName }</td>
						<td>${rl.idCard }</td>
						<td>${rl.mobileNumber }</td>
						
						</tr>
						</c:forEach>
						
						</table>
						
						</c:if>

</body>
</html>


