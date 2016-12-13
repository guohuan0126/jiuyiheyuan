<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href='${ctx}/ueditor/themes/default/css/ueditor.min.css' type="text/css" rel="stylesheet" >
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>  
<!-- 编辑器源码文件 -->  
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>  
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) 此处可要可不要 -->   
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/js/validate.js"></script>
<title>复核借款用户信息</title>
</head>
<body>	
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-pencil"></i>复核借款用户信息
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h5 class="panel-title">复核借款用户信息</h5>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="asterisk"></span></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">借款人姓名  <font style="color: red">*</font></label>
								<div class="col-sm-3">
									<input type="text" name="realname" disabled="disabled" value="${loanerinfo.userName}" id="realname"  width="10px"
										class="form-control"/>	
										<input id="loanerId" type="hidden" value="${loanerinfo.id}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">性别 </label>
								<div class="col-sm-3">
									<input type="radio" name="sex" value="0" disabled="disabled" id="sex" style="display: inline-block; margin: 9px;"/>男
									<input type="radio" value="1" name="sex" disabled="disabled" id="sex" style="display: inline-block; margin: 9px;"/>女							
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">年龄 </label>
								<div class="col-sm-3">
									<input type="text" name="age" id="age" disabled="disabled" value="${loanerinfo.age}" width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group"> 
									
								<label class="col-sm-3 control-label">身份证号 <font style="color: red">*</font> </label>
								<div class="col-sm-3">
									<input type="text" name="idCard" disabled="disabled" value="${base:decrypt(loanerinfo.idCard)}" id="idCard"  width="10px"
										class="form-control" onchange="checkIdcard()"/>
										<span id="idCardShow" style="font-size: 15px;color: red;display: none;">身份证号已存在</span>										
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">手机号 <font style="color: red">*</font></label>
									
								<div class="col-sm-3">
									<input type="text" name="mobileNumber" disabled="disabled" value="${base:decrypt(loanerinfo.mobileNumber)}" id="mobileNumber"  width="10px"
										class="form-control" onchange="check()"/>
										<span id="mobileNumberShow"  style="font-size: 15px;color: red;display: none;">手机号已存在</span>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 婚姻状况 </label>
								<div class="col-sm-3">
									<input type="radio" name="maritalStatus" disabled="disabled" value="0" id="maritalStatus" style="display: inline-block; margin: 9px;"/>未婚
									<input type="radio" name="maritalStatus" disabled="disabled" value="1" id="maritalStatus" style="display: inline-block; margin: 9px;"/>已婚
									<input type="radio" name="maritalStatus" disabled="disabled" value="2"  id="maritalStatus" style="display: inline-block; margin: 9px;"/>离异
									<input type="radio" name="maritalStatus" disabled="disabled" value="3"  id="maritalStatus" style="display: inline-block; margin: 9px;"/>丧偶							
								</div>
							</div>
						
						<div style="margin-left: 332px;">
						<div>
                        <label for="provinceId">选择省份:</label>
                        <select data-placeholder="省份" disabled="disabled" id="provinceId" name="province">
                        	<c:forEach items="${province}" var="province">
                        	<option value="${province.name}" prID="${province.id}" >${province.name}	
                        	</option>
                       </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label for="cityId">选择城市:</label>
                        <select data-placeholder="城市" name="city" disabled="disabled" id="cityId"  data-rel="chosen">
                            <option>选择城市</option>
                        </select>
                    </div>
                    <div>
                        <label for="areaId">选择区域:</label>
                        <select data-placeholder="区域" name="area" disabled="disabled"  id="areaId" data-rel="chosen">
                            <option>选择区域</option>
                        </select>
                    </div>
					</div>		
							<div class="form-group">
								<label class="col-sm-3 control-label">详细地址</label>
								<div class="col-sm-3">
									<input type="text" name="address" disabled="disabled" value="${loanerinfo.address}" id="address"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">年收入</label>
								<div class="col-sm-3">
									<input type="text" name="annualIncome" disabled="disabled" value="${loanerinfo.annualIncome}" id="annualIncome"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">客户经理人</label>
								<div class="col-sm-3">
									<input type="text" name="offlineReferrer" disabled="disabled" value="${loanerinfo.offlineReferrer}" id="offlineReferrer"  width="10px"
										class="form-control"/>									
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">备注信息</label>
								<div class="col-sm-3">
									<input type="text" name="remark" id="remark" disabled="disabled" value="${loanerinfo.remark}" width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div style="margin-left: 332px;">
		                        <label for="areaId">复核状态</label>
		                        <select data-placeholder="复核状态" name="checkType"  id="checkType" >
		                            <option value="checked">审核通过</option>
		                            <option value="reject">驳回</option>
		                        </select>
                    		</div>
                    		<div style="margin-left: 332px;">
                    		<label for="areaId">审核理由</label>
							<input id="reason" type="text" style="height: 60px;width: 395px;">
							</div>
							</div>
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</div>
						</div>
					</div>
					</div>
					<!-- panel -->
				</form>
			</div>
			<!-- contentpanel -->

		</div>
		
		<script language="javascript">
			//获取网页中name=angel的单选按钮，取第一个，添加属性checked=checked，也就是让其选中
			$("input[name=sex][value='${loanerinfo.sex}']").attr("checked",'checked');
			$("input[name=maritalStatus][value='${loanerinfo.maritalStatus}']").attr("checked",'checked');
			var reason=$("#checkType").val();
 		    if(reason=='uncheck'){
 		    $("#reason").attr("disabled","disabled");
 		    }
			$("#checkType").change(function(){
    		    var reason=$("#checkType").val();
    		    if(reason=='uncheck'){
    		    	$("#reason").attr("disabled","disabled");
    		    }else{
    		    	$("#reason").attr("disabled",false);
    		    }
    		});
			
			
		</script>
		<!-- 城市三级联动 -->
		<script type="text/javascript">
    		$("#provinceId").click(function(){
    		    var id = $(this).find("option[value='"+this.value+"']").attr("prID");
    			getCity(id,null);
    		});
    		$("#cityId").click(function(){
    			 var id = $(this).find("option[value='"+this.value+"']").attr("prID");
    			getArea(id,null);
    		});
    		function getCity(val,str){
    		var city=val;
    			$.ajax({
					type : 'POST',
					url : "/agricultural/getCity",						
					dataType:'json',
					data: {
						"city":city
					},
					success:function(data) {
					    var htmlStr = "";
						for(i=0;i<data.length;i++){
							htmlStr += "<option value="+data[i].name+" prID="+data[i].id+" >"+data[i].name+"</option>";
						}
						console.log(htmlStr);
						$("#cityId").html(htmlStr);
						if(str != null){
							var cid = $("#cityId").find("option[value='"+str+"']").attr("prID");
							$("#cityId").val(str);
							getArea(cid,"${loanerinfo.area}");
						}
					}
					
				});	
			}
    		function getArea(val,str){
    		var city=val;
    			$.ajax({
					type : 'POST',
					url : "/agricultural/getArea",						
					dataType:'json',
					data: {
						"city":city
					},
					success:function(data) {
					    var htmlStr = "";
						for(i=0;i<data.length;i++){
							htmlStr += "<option value="+data[i].name+" prID="+data[i].id+" >"+data[i].name+"</option>";
						}
						console.log(htmlStr);
						$("#areaId").html(htmlStr);
						if(str != null){
							$("#areaId").val(str);
						}
					}
					
				});	
    			
    		}
		</script>
		
		<script type="text/javascript">
			function sub(){
				var checkType=$("#checkType").val();
				var id= $("#loanerId").val();
				var reason=$("#reason").val();
				
				if(confirm('确认审核借款人信息?')){
				$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/agricultural/checkAgainLoanerInfo",						
					data: {
						"checkType":checkType,
						"id":id,
						reason:reason
					},
					success:function(data) {
					
						if(data=='success'){
							alert("审核成功");
							window.location.href="/agricultural/loanerinfo";
						}else{
							alert("审核失败");
						}
					}
				});			
			}
		}
		</script>		
		<script type="text/javascript">
							$("#provinceId").val('${loanerinfo.province}');
							var id = $("#provinceId").find("option[value='${loanerinfo.province}']").attr("prID");
 			             getCity(id,"${loanerinfo.city}");
    			             
		</script>
	</section>
</body>
</html>
