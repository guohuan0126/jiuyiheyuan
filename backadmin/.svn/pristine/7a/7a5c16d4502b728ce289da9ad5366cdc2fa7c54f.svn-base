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
<title>创建银行卡</title>
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
					<i class="fa fa-pencil"></i>创建银行卡
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h5 class="panel-title">创建银行卡</h5>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="asterisk"></span></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">借款人手机号 <font style="color: red">*</font></label>
								<div class="col-sm-3">
									<input type="text" name="mobileNumber" onchange="checkMobileNumber()" id="mobileNumber"  width="10px"
										class="form-control"/>	
									<input type="hidden" id="loanerId">
										<span id="mobileNumberShow"  style="font-size: 15px;color: red;display: none;">手机号不存在 请创建借款人信息</span>
										<span id="mobileNumberShow1"  style="font-size: 15px;color: red;display: none;"></span>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">银行卡号 <font style="color: red">*</font></label>
								<div class="col-sm-3">
									<input type="text" name="bankCard" onchange="checkBankCardNum()" id="bankCard"  width="10px"
										class="form-control"/>	
										<span id="bankCardShow"  style="font-size: 15px;color: red;display: none;">银行卡号已存在</span>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">所属银行 <font style="color: red">*</font> </label>
								<div class="col-sm-3">
										<select name="bankCode" id="bankCode" class="form-control" style="width:50%;">
										<c:forEach items="${banklist}" var="bankInfo">
										<option value="${bankInfo.bankNo}">${bankInfo.name}</option>
										
										</c:forEach>
									</select>
																	
								</div>
							</div>
							<div class="form-group"> 
									
								<label class="col-sm-3 control-label">支付通道 <font style="color: red">*</font> </label>
								<div class="col-sm-3">
										<select name="paymentChannel" id="paymentChannel" class="form-control" style="width:50%;">
										<option value="Zhongjin">中金支付</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">证件类型 <font style="color: red">*</font></label>
								<div class="col-sm-3">
									<select name="CaedType" id="cardSelect" class="form-control" style="width:50%;">
										<option value="idCard">身份证</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">证件号<font style="color: red">*</font></label>
								<div class="col-sm-3">
									<input type="text" name="idCard" id="idCard"  width="10px"
										class="form-control"/>									
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
					</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">支行名称 <font style="color: red">*</font></label>
								<div class="col-sm-3">
									<input type="text" name="branchName" id="branchName"  width="10px"
										class="form-control"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">银行预留手机号 <font style="color: red">*</font></label>
								<div class="col-sm-3">
									<input type="text" name="bankMobile" id="bankMobile"  width="10px"
										class="form-control"/>									
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">备注</label>
								<div class="col-sm-3">
									<input type="text" name="remark" id="remark"  width="10px"
										class="form-control"/>									
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">银行卡优先级</label>
								<div class="col-sm-3">
									<select name="bankNo" id="bankNo" class="form-control" style="width:50%;">
										<option value="1">一级</option>
									</select>							
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
		
		
		<script type="text/javascript">
		var passNum=false;
		function checkBankCardNum(){
			passNum=false;
			var bankCard=$("#bankCard").val();	
			$("#bankCardShow").hide();
				
				$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/agricultural/checkBankCard",						
					data: {
						"bankCard":bankCard
					},
					success:function(data) {
					
						if(data=='error'){
							$("#bankCardShow").show();
							passNum=false;
							return false;
						}else{
							$("#bankCardShow").hide();
							passNum=true;
							return true;
						}
					}
				});		
			}
		</script>
		
		
		<script type="text/javascript">
		function checkMobileNumber(){
			
			var mobileNumber=$("#mobileNumber").val();
				if(!regCheckMobile(mobileNumber)){
					$("#mobileNumberShow").hide();
					$("#mobileNumberShow1").html("请输入正确的手机号").show();
					return false;
				}
				$.ajax({
					type : 'POST',
					dataType:'json',
					url : "/agricultural/checkCardLoanerMobileNumber",						
					data: {
						"mobileNumber":mobileNumber
					},
					success:function(data) {
						console.log(data.id);
						var id=data.id;
						$("#mobileNumberShow1").html("请输入正确的手机号").hide();
						$("#loanerId").attr("value",data.id);
						if(id!=null){

							$("#mobileNumberShow").hide();
							return true;
							
						}else{
							$("#mobileNumberShow").show();
							return false;
						}
					}
				});		
			}
			
		</script>
		
		<script type="text/javascript">
			function sub(){
			
			var loanerId =$("#loanerId").val();
			var mobileNumber=$("#mobileNumber").val();
			if(mobileNumber==''){
				alert("手机号不能为空");
				return false;
			}else if(!regCheckMobile(mobileNumber)){
				alert("请输入正确的手机号");
				return false;
			}
			var bankCard=$("#bankCard").val();
			if(bankCard==''){
				alert("银行卡号不能为空");
				return false;
			}else if(!checkBankCard(bankCard)){
				alert("银行卡格式不正确");
				return false;
			}else if (!passNum){
				alert("银行卡号已重复");
				return false;
			}
			var bankName=$("#bankCode").find("option:selected").text();
			var bankCode=$("#bankCode").val();
			var paymentChannel=$("#paymentChannel").val();
			var cardSelect=$("#cardSelect").val();
			var idCard=$("#idCard").val();
			if(idCard==''){
				alert("证件号不能为空");
				return false;
			}else if(!checkCard(idCard)){
				alert("证件号格式不正确");
				return false;
			}
			var branchName=$("#branchName").val();
			if(branchName==''){
				alert("支行名称不能为空");
				return false;
			}
			var remark=$("#remark").val();
			var bankMobile=$("#bankMobile").val();
			if(bankMobile==''){
				alert("预留手机号不能为空");
				return false;
			}else if(!regCheckMobile(bankMobile)){
				alert("预留手机号格式不正确");
				return false;
			}
			var province=$("#provinceId").val();
			var city=$("#cityId").val();
			var bankNo=$("#bankNo").val();
			if(confirm('确认添加银行卡信息?')){
			$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/agricultural/addBankCardInfo",						
					data: {
						"loanerId":loanerId,
						"mobileNumber":mobileNumber,
						"bankName":bankName,
						"bankCard":bankCard,
						"bankCode":bankCode,
						"paymentChannel":paymentChannel,
						"cardSelect":cardSelect,
						"idCard":idCard,
						"branchName":branchName,
						"bankMobile":bankMobile,
						"remark":remark,
						"bankNo":bankNo,
						"province":province,
						"city":city
					},
					success:function(data) {
					
						if(data=='success'){
							alert("添加成功");
							location.reload();
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
			// 正则验证银行卡方法  
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
