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
<title>修改借款用户信息</title>
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
					<i class="fa fa-pencil"></i>修改借款用户信息
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h5 class="panel-title">修改借款用户信息</h5>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="asterisk"></span></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">借款人姓名  <font style="color: red">*</font></label>
								<div class="col-sm-3">
									<input type="text" name="realname" value="${loanerinfo.userName}" id="realname"  width="10px"
										class="form-control"/>	
										<input id="loanerId" type="hidden" value="${loanerinfo.id}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">性别 </label>
								<div class="col-sm-3">
									<input type="radio" name="sex" value="0" id="sex" style="display: inline-block; margin: 9px;"/>男
									<input type="radio" value="1" name="sex" id="sex" style="display: inline-block; margin: 9px;"/>女							
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">年龄 <font style="color: red">*</font> </label>
								<div class="col-sm-3">
									<input type="text" name="age" id="age" value="${loanerinfo.age}" width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group"> 
									
								<label class="col-sm-3 control-label">身份证号 <font style="color: red">*</font> </label>
								<div class="col-sm-3">
									<input type="text" name="idCard" value="${base:decrypt(loanerinfo.idCard)}" id="idCard"  width="10px"
										class="form-control" onchange="checkIdcard()"/>
										<span id="idCardShow" style="font-size: 15px;color: red;display: none;">身份证号已存在</span>										
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">手机号 <font style="color: red">*</font></label>
									
								<div class="col-sm-3">
									<input type="text" name="mobileNumber" value="${base:decrypt(loanerinfo.mobileNumber)}" id="mobileNumber"  width="10px"
										class="form-control" onchange="check()"/>
										<span id="mobileNumberShow"  style="font-size: 15px;color: red;display: none;">手机号已存在</span>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 婚姻状况 </label>
								<div class="col-sm-3">
									<input type="radio" name="maritalStatus" value="0" id="maritalStatus" style="display: inline-block; margin: 9px;"/>未婚
									<input type="radio" name="maritalStatus" value="1" id="maritalStatus" style="display: inline-block; margin: 9px;"/>已婚
									<input type="radio" name="maritalStatus" value="2"  id="maritalStatus" style="display: inline-block; margin: 9px;"/>离异
									<input type="radio" name="maritalStatus" value="3"  id="maritalStatus" style="display: inline-block; margin: 9px;"/>丧偶							
								</div>
							</div>
						
						<div style="margin-left: 332px;">
						<div>
                        <label for="provinceId">选择省份:</label>
                        <select data-placeholder="省份" id="provinceId" name="province">
                        	<c:forEach items="${province}" var="province">
                        	<option value="${province.name}" prID="${province.id}" >${province.name}	
                        	</option>
                       </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label for="cityId">选择城市:</label>
                        <select data-placeholder="城市" name="city" id="cityId"  data-rel="chosen">
                            <option>选择城市</option>
                        </select>
                    </div>
                    <div>
                        <label for="areaId">选择区域:</label>
                        <select data-placeholder="区域" name="area" id="areaId" data-rel="chosen">
                            <option>选择区域</option>
                        </select>
                    </div>
					</div>		
							<div class="form-group">
								<label class="col-sm-3 control-label">详细地址</label>
								<div class="col-sm-3">
									<input type="text" name="address" value="${loanerinfo.address}" id="address"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">年收入</label>
								<div class="col-sm-3">
									<input type="text" name="annualIncome" value="${loanerinfo.annualIncome}"id="annualIncome"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">客户经理人</label>
								<div class="col-sm-3">
									<input type="text" name="offlineReferrer" value="${loanerinfo.offlineReferrer}" id="offlineReferrer"  width="10px"
										class="form-control"/>									
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">备注信息</label>
								<div class="col-sm-3">
									<input type="text" name="remark" id="remark" value="${loanerinfo.remark}" width="10px"
										class="form-control"/>									
								</div>
							</div>
							<input id="type" type="hidden" value="${loanerinfo.status}"/>
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
						var id = $("#cityId").find("option[value='"+$("#cityId").val()+"']").attr("prID");
						getArea(id);
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
		<!-- 验证手机号是否存在 -->
		<script type="text/javascript">
		var passNum=true;
		function check(){
			passNum=false;
			var omobileNumber='${loanerinfo.mobileNumber}';
			var mobileNumber=$("#mobileNumber").val();	
			if(omobileNumber==mobileNumber){
				passNum = true; 
				return true;
				
			}	
			if(!regCheckMobile(mobileNumber)){
					alert("请输入正确的手机号");
					return false;
				}
				
				$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/agricultural/checkLoanerMobileNumber",						
					data: {
						"mobileNumber":mobileNumber
					},
					success:function(data) {
					
						if(data=='error'){
							$("#mobileNumberShow").show();
							passNum=false;
							return false;
						}else{
							$("#mobileNumberShow").hide();
							passNum=true;
							return true;
						}
					}
				});		
			}
			
		</script>
		<!-- 验证身份证号是否存在 -->
		<script type="text/javascript">
		var pass=true;
		var oidCard = '${loanerinfo.idCard}';
		function checkIdcard(){
			pass = false;
			var idCard=$("#idCard").val();	
			if(oidCard==idCard){ 
				pass = true; 
				$("#idCardShow").hide();
				return true;
				}
				if(!checkCard(idCard)){
					alert("请输入正确的身份证号");
					return false;
				}
				$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/agricultural/checkLoanerIdCard",						
					data: {
						"idCard":idCard
					},
					success:function(data) {
					//console.log(data);
						if(data=='error'){
							$("#idCardShow").show();
							pass=false;
							return false;
							
						}else{
							$("#idCardShow").hide();
							pass=true;
							return true;
						}
					}
				});		
			}
		</script>
		<script type="text/javascript">
			
		function sub(){
				var realname=$("#realname").val();
				var loanerId=$("#loanerId").val();
				if(realname==''){
					alert("借款人姓名不能为空");
					return false;
				}
				var sex=$("input[name='sex']:checked").val();
				var age=$("#age").val();
				if(age==''){
					alert("年龄不能为空");
					return false;
				}else if(!checkBankCard(age)){
					alert("年龄格式不正确");	
					return false;
				}
				var idCard=$("#idCard").val();
				if(idCard==''){
					alert("身份证号不能为空");
					return false;
				}else if(!checkCard(idCard)){
					alert("请输入正确的身份证号");
					return false;
				}else if(!pass){
					alert("身份证号已重复");
					return false;
				}
				var mobileNumber=$("#mobileNumber").val();
				if(mobileNumber==''){
					alert("手机号不能为空");
					return false;
				}else if(!regCheckMobile(mobileNumber)){
					alert("请输入正确的手机号");	
					return false;
				}else if(!passNum){
					alert("手机号已重复");
					return false;
				}
				var status=$("#type").val();
				var maritalStatus=$("input[name='maritalStatus']:checked").val();
				var province=$("#provinceId").val();
				var city=$("#cityId").val();
				var area=$("#areaId").val();
				var address=$("#address").val();
				var offlineReferrer=$("#offlineReferrer").val();
				var remark=$("#remark").val();
				var annualIncome=$("#annualIncome").val();
				
			if(confirm('确认操作借款人信息?')){
			$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/agricultural/updateLoanerInfo",						
					data: {
						"loanerId":loanerId,
						"realname":realname,
						"sex":sex,
						"age":age,
						"idCard":idCard,
						"mobileNumber":mobileNumber,
						"maritalStatus":maritalStatus,
						"province":province,
						"city":city,
						"area":area,
						"address":address,
						"annualIncome":annualIncome,
						"offlineReferrer":offlineReferrer,
						"remark":remark,
						"status":status
					},
					success:function(data) {
					
						if(data=='success'){
							alert("修改成功");
							
							window.location.href="/agricultural/loanerinfo";
					
						}else{
							alert("修改失败");
						}
					}
				});			
			}	
		}
			//校验手机号石头正确
			var regCheckMobile = function(s){
				var regu =/^0?1[3|4|5|7|8][0-9]\d{8}$/;
				var re = new RegExp(regu);
				if (re.test(s)) {
					return true;
				}else{
					
					return false;
				}
			}
			
			// 输入:str  字符串
			// 返回:true 或 flase; true表示格式正确
				var checkCard = function (str) {
				var re = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
				if (re.test(str)) {
				   return true;
				}else{
				  return false;
				}
			};
			 var checkBankCard = function(content) {  
		            var regex = /^[0-9]*[1-9][0-9]*$/;  
		            if (regex.test(content)&&content.length<=50) {  
		                return true;  
		            }  
		            return false;  
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
