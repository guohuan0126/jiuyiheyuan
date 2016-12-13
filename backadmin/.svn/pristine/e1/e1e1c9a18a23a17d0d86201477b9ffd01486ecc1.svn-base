<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>创建借款人信息</title>
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
					<i class="fa fa-pencil"></i>创建借款人信息
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h5 class="panel-title">创建借款人信息</h5>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="asterisk"></span></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">借款人姓名  <font style="color: red">*</font></label>
								<div class="col-sm-3">
									<input type="text" name="realname" id="realname"  width="10px"
										class="form-control"/>	
																
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">性别 </label>
								<div class="col-sm-3">
									<input type="radio" name="sex" checked="checked"  value="0" id="sex" style="display: inline-block; margin: 9px;"/>男
									<input type="radio" value="1" name="sex" id="sex" style="display: inline-block; margin: 9px;"/>女							
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">年龄  <font style="color: red">*</font> </label>
								<div class="col-sm-3">
									<input type="text" name="age" id="age"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group"> 
									
								<label class="col-sm-3 control-label">身份证号 <font style="color: red">*</font> </label>
								<div class="col-sm-3">
									<input type="text" name="idCard" id="idCard"  width="10px"
										class="form-control" onchange="checkIdcard()"/>
										<span id="idCardShow" style="font-size: 15px;color: red;display: none;">身份证号已存在</span>										
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">手机号 <font style="color: red">*</font></label>
									
								<div class="col-sm-3">
									<input type="text" name="mobileNumber" id="mobileNumber"  width="10px"
										class="form-control" onchange="check()"/>
										<span id="mobileNumberShow"  style="font-size: 15px;color: red;display: none;">手机号已存在</span>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"> 婚姻状况 </label>
								<div class="col-sm-3">
									<input type="radio" name="maritalStatus" value="0" checked="checked" id="maritalStatus" style="display: inline-block; margin: 9px;"/>未婚
									<input type="radio" name="maritalStatus" value="1" id="maritalStatus" style="display: inline-block; margin: 9px;"/>已婚
									<input type="radio" name="maritalStatus" value="2"  id="maritalStatus" style="display: inline-block; margin: 9px;"/>离异
									<input type="radio" name="maritalStatus" value="3"  id="maritalStatus" style="display: inline-block; margin: 9px;"/>丧偶							
								</div>
							</div>
						
						<div style="margin-left: 332px;">
						<div>
                        <label for="provinceId">选择省份:</label>
                        <select data-placeholder="省份" id="provinceId" name="province">
							 <option >选择省份	
                        	</option>                      	
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
									<input type="text" name="address" id="address"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">年收入</label>
								<div class="col-sm-3">
									<input type="text" name="annualIncome" id="annualIncome"  width="10px"
										class="form-control"/>									
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">客户经理人</label>
								<div class="col-sm-3">
									<input type="text" name="offlineReferrer" id="offlineReferrer"  width="10px"
										class="form-control"/>									
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">备注信息</label>
								<div class="col-sm-3">
									<input type="text" name="remark" id="remark"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							
							</div>
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="sub()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="reset" class="btn btn-default">重置</button>
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
		<!-- 城市三级联动 -->
		<script type="text/javascript">
		$("#provinceId").click(function(){
    		    var id = $(this).find("option[value='"+this.value+"']").attr("prID");
    			getCity(id);
		});
    		$("#cityId").click(function(){
    			 var id = $(this).find("option[value='"+this.value+"']").attr("prID");
    			getArea(id);
    		});
    		function getCity(val){
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
						var id = $("#cityId").find("option[value='"+$("#cityId").val()+"']").attr("prID");
						getArea(id);
					}
					
				});	
			}
    		function getArea(val){
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
					}
					
				});	
    			
    		}
		</script>
		<!-- 验证手机号是否存在 -->
		<script type="text/javascript">
		var passNum=false;
		function check(){
			passNum=false;
			var mobileNumber=$("#mobileNumber").val();	
			$("#mobileNumberShow").hide();
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
		var pass=false;
		function checkIdcard(){
			pass = false;
			var idCard=$("#idCard").val();	
			$("#idCardShow").hide();	
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
			var maritalStatus=$("input[name='maritalStatus']:checked").val();
			var province=$("#provinceId").val();
			var city=$("#cityId").val();
			var area=$("#areaId").val();
			var address=$("#address").val();
			var offlineReferrer=$("#offlineReferrer").val();
			var remark=$("#remark").val();
			var annualIncome=$("#annualIncome").val();
			if(confirm('确认添加借款人信息?')){
			$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/agricultural/addLoanerInfo",						
					data: {
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
						"remark":remark
					},
					success:function(data) {
						if(data=='success'){
							location.reload();
							alert("添加成功");
						}else{
							alert("添加失败");
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
	</section>
</body>
</html>
