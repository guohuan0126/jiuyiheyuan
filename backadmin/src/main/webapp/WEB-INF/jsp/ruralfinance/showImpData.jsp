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

				<style>
#tj,#isAdvance{
 background:#F0F0F0 repeat-x;
 padding-top:3px;
 border-top:1px solid #708090;
 border-right:1px solid #708090;
 border-bottom:1px solid #708090;
 border-left:1px solid #708090; 
 width:120px;
 height:37px;
 font-size:10pt;
 cursor:hand;
 margin:15px 0 0 20px;
 border-radius:3px;
}

.zhezhao{width:100%;height:100%;background-color:rgba(0,0,0,0.6); position:fixed;top:0;left:0;z-index:101;}
.zhezhao-all{width:300px;padding-bottom:30px;border-radius:4px;border:10px solid #6d6d6d;background:#fff;position:absolute;top:50%;left:50%;margin-top:-163px;margin-left:-150px;}
.zhezhao-all h1{text-align:center;font-family:"微软雅黑";margin:45px 0 15px;font-size:16px;}
.zhezhao-all img{position:absolute;top:-11px;right:-11px; cursor:pointer;}

.div {width:300px; overflow:hidden;padding:20px;height:68px;float:left;} 
.line { position:relative; margin:0 auto; width:300px; text-align:left } 
.line span.span { float:left; padding-top:2px; } 
.file { position:absolute; left:0; width:250px; top:0;
 height:28px; filter:alpha(opacity=0); opacity:0; cursor: pointer } 
.file1 { float:left; margin-left:8px; z-index:1; width:66px;
 height:28px; line-height:28px; background:url(/images/liulan.gif) no-repeat 0 0;
 text-indent:-9999px; cursor: pointer } 
.inputstyle { border:1px solid #BEBEBE; width:170px; float:left;
 height:23px; line-height:23px; background:#FFF; z-index:99 } 
</style>		

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
	  <script type="text/javascript">
			$(function(){  
				var fileContent="";
				$("#yc").click(function(){
					
					$(".zhezhao").hide();
				});
				
				$("#isAdvance").click(function(){
					 fileContent = $("#viewfile").val();
					if(fileContent==null||fileContent==""){
						alert("请选择excel模板文件");
						return false;
					}else{
						console.info(1112);
					$(".form-inline").attr({action:"/isAdvance"});
					$(".form-inline").submit();
					}
						
				});
				
				
				
				
				$("#tj").click(function(){
					fileContent = $("#viewfile").val();
					if(fileContent==null||fileContent==""){
						alert("请选择excel模板文件");
						return false;
					}
				});
			});
		</script>
		
			<div class="pageheader">
				<h2>
					<i class="fa fa-table"></i>当前位置：导入数据
				</h2>
			</div>
	          <form  action= "/showData"  class="form-inline" enctype="multipart/form-data"  method= "post"  > 
				 		   		<!-- <div style="float:left;margin:20px;"><input   type= "file"   name="file" id="zx" /> </div>
								<div style="float:left;"><input   type= "submit"   value= "提交" id="tj"/> </div> -->
								
									<div class="div"> 
				<div class="line"> 
				<span class="span"> 
				<input name="" type="text" id="viewfile"
				 onmouseout="document.getElementById('upload').style.display='none';" 
				 class="inputstyle" /> 
				</span> 
				<label for="unload"
				 onmouseover="document.getElementById('upload').style.display='block';"
				 class="file1">浏览...</label> 
				<input type="file"
				 onchange="document.getElementById('viewfile').value=this.value;this.style.display='none';"
				 class="file" id="zx"  name="file"/> 
				 
				 
				</div> 
				</div> 
		    <%-- <base:active activeId="AGRICULTURAL_EXPORT_SUBMIT"> --%> <div style="float:left;"><input   type= "submit"   value= "提交" id="tj"/> </div>  <%-- </base:active>  --%>  
			<%-- <base:active activeId="AGRICULTURAL_PROJECTLOANS_LOOK"> --%>  <div style="float:left;"><input   type= "button"   value= "排查重复放款项目" id="isAdvance" class="bt"/> </div> <%-- </base:active> --%>
	
	</form>
		<div class="panel-body">
			
					<c:if test="${ empty  contractDataErrList and  empty contractHTErrList and type ne 1 }">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>id</th>
									<th>上传者用户名</th>
									<th>导入时间</th>
									<th>下载</th>
								</tr> 
							</thead>
								<c:forEach var="list" items="${list}"  varStatus="idx">
								<tr>
								<td>${idx.index+1 }</td>
								 <td>${list.uploadUser}</td>
								   <td><fmt:formatDate value="${list.createTime}"  type="both" /></td>
								   <!--线上的  -->
									<%--  <td><a href="https://duanrongweb.oss-cn-qingdao.aliyuncs.com/${list.uploadAddress}">下载</a></td> --%>
									<!--测试的  -->
									<td><a href="https://drwebdemo.oss-cn-qingdao.aliyuncs.com/${list.uploadAddress}">下载</a></td> 
								</tr>
							</c:forEach>	
						</table>
						<%@ include file="../base/page.jsp"%>
						</c:if>
		
		
		<!--错误数据Begin  -->
			<c:choose>
				<c:when test="${! empty  contractDataErrList}">
					<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>合同编号</th>
									<th>用户名</th>
									<th>手机号</th>
									<th>银行卡号</th>
									<th>身份证号</th>
									<th>银行名称</th>
									<th>支行名称</th>
									<th>支行所在省份</th>
									<th>支行所在城市</th>
									<th>合同金额</th>
									<th>放款金额</th>
									<th>服务费</th>
									<th>借款期限</th>
									<th>利率</th>
									<th>放款日期</th>
									<th>金额和</th>
									<th>综合利率 </th>
									<th>核算部门 </th>
									<th>还款方式 </th>
									<th>项目类型 </th>
								</tr>
							</thead>
							<c:forEach items="${contractDataErrList}" var="contractDataErrList">
								<tr>
									<td>${contractDataErrList.contractId}</td>
									<td><c:if test="${contractDataErrList.yhm==1}">
									${contractDataErrList.name}用户名有问题
									</c:if>
									</td>
									<td>
									<c:if test="${contractDataErrList.sjh==1}">
									${contractDataErrList.mobileNumber}手机号有问题
									</c:if>
									</td>
									
									<td>	
									<c:if test="${contractDataErrList.yhkh==1}">
									${contractDataErrList.bankcard}银行卡号有问题
									</c:if>
									</td>
									
									<td><c:if test="${contractDataErrList.sfzh==1}">
									${contractDataErrList.idCard}身份证号有问题
									</c:if>
									</td>
									
									<td><c:if test="${contractDataErrList.yhmc==1}">
									${contractDataErrList.bank}银行名称有问题
									</c:if>
									</td>
									
									<td><c:if test="${contractDataErrList.yhfh==1}">
									${contractDataErrList.branchname}支行名称有问题
									</c:if>
									</td>
									
									
									<td><c:if test="${contractDataErrList.sf==1}">
									
									${contractDataErrList.province}支行所在省份有问题
									</c:if>
									</td>
									
									
									<td><c:if test="${contractDataErrList.cs==1}">
									${contractDataErrList.city}支行所在城市有问题
									</c:if>
									</td>
									
									
									<td><c:if test="${contractDataErrList.htje==1}">
									${contractDataErrList.money}合同金额有问题
									</c:if>
									</td>
									
									
									<td><c:if test="${contractDataErrList.fkje==1}">
									${contractDataErrList.loanMoney}放款金额有问题
									</c:if>
									</td>
									
									
									
									<td><c:if test="${contractDataErrList.fwf==1}">
									${contractDataErrList.serviceMoney}服务费有问题
									</c:if>
									</td>
									
									<td><c:if test="${contractDataErrList.jkqx==1}">
									${contractDataErrList.loanTerm}借款期限有问题
									</c:if>
									</td>
									
									
									<td><c:if test="${contractDataErrList.ll==1}">
									${contractDataErrList.rate}利率有问题
									</c:if>
									</td>
									
									
									<td><c:if test="${contractDataErrList.fkrq==1}">
									${contractDataErrList.giveMoneyTime}放款日期有问题
									</c:if>
									</td>
									
									
									<td><c:if test="${contractDataErrList.jeh==1}">
									合同金额不等于放款金额加服务费 请修改
									</c:if></td>
									
									<td><c:if test="${contractDataErrList.zhll==1}">
									综合利率 有问题 请修改
									</c:if></td>
									<td><c:if test="${contractDataErrList.depart==1}">
									核算部门 有问题 请修改
									</c:if></td>
									
									<td><c:if test="${contractDataErrList.hkfs==1}">
									 还款方式 有问题 请修改
									</c:if></td>
									<td><c:if test="${contractDataErrList.xmlx==1}">
									 项目类型 有问题 请修改
									</c:if></td>
								</tr>
							</c:forEach>
						</table>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<!--错误数据End  -->
			
			<!--合同编号错误Begin  -->
			<c:choose>
				<c:when test="${! empty contractHTErrList}">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>合同编号重复</th>
								</tr>
							</thead>
						<c:forEach items="${contractHTErrList}" var="contractHTErrList">
							<tr>
								<td>${contractHTErrList}</td>
							</tr>
						</c:forEach>
						</table>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			
			<!--合同编号错误Begin  -->
			
			<c:if test="${! empty contractIsFK}">
			
			<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>已放过款的合同编号</th>
								</tr>
							</thead>
						<c:forEach items="${contractIsFK}" var="contractIsFK">
							<tr>
								<td>${contractIsFK}</td>
							</tr>
						</c:forEach>
						</table>
			
			</c:if>
			<c:if test="${empty contractIsFK}">
			
			<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>已放过款的合同编号</th>
								</tr>
							</thead>
							<tr>
								<td>excel中没有已放过款的项目</td>
							</tr>
						</table>
			
			</c:if>
		</div>
	</div>
	</section>
		<c:if test="${sc=='success'}">
			<div class="zhezhao" style="display:block;">
				<div class="zhezhao-all">
				<h1>全部导入成功</h1>
				<img src="/images/x.png" id="yc">
		
		</div>
	</div>
	</c:if> 
</body>
</html>


