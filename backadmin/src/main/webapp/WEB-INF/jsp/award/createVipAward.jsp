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
<link rel="shortcut icon" href="images/favicon.png" type="image/png">

<title>Vip专享券发送</title>
<style type="text/css">
.box {width:50%; float:left; display:inline;}
</style>
</head>
<body>
	<!-- Preloader -->
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
					<i class="fa fa-pencil"></i> Vip专享券发送 
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form1" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">Vip专享券发送</h4>
						</div>
						<div class="panel-body">
						<div class="form-group" id="groupCountDiv">
								<label class="col-sm-3 control-label">填写奖励组数 </label>
								<div class="col-sm-3">
										<input type="text" id="groupCountIpt" name="groupCountIpt"
											onchange="groupCount()" placeholder="请填写数字"
											class="form-control" style="display:inline-block;width:60%;"/>
											<span style="color:red;">(*填写奖励人数后回车*)</span>																			
								</div>
								</div>
								<div class="form-group" >
								<label class="col-sm-3 control-label"></label>
								<div class="col-sm-3">								
								<a style="font-size:30px" onclick="operationInput('add')">+</a>
							    <a style="font-size:30px" onclick="operationInput('del')">-</a>
							    <div id="errorGroupCount" style="display: none;">组数不正确</div>
								</div>
							</div>
							<div class="form-group">
							<table  id="groupDetailTable" style="display: none;">
								<label class="col-sm-3 control-label" id="mobileAndmoney" style="display:none;">请填写用户手机号
								</label>
								<div id="groupDetail"></div>
							</table>
							</div>											 
							
						</div>
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="return sub()">保存</button>
									<!-- <button type="button" class="btn btn-default" onclick="return res()">重置</button> -->
								</div>
							</div>
						</div>

					</div>
					<!-- panel -->
				</form>
			</div>
			<!-- contentpanel -->

		</div>
		<!-- mainpanel -->

	</section>

	<script type="text/javascript">

	function operationInput(op){
		var gc = $("#groupCountIpt").val();
		var groupCount = parseInt(gc);
		var MinusNum = groupCount-1;
		var AddNum = groupCount+1;
		if(op == 'add'){
			$("#groupDetailTable").attr("style", "display:block;")
			$("#mobileAndmoney").attr("style", "display:block;")
			$("#groupDetail").attr("style", "display:block;")
			//如果此值大于0 ,则在此值基础上追加一行
			if(groupCount > 0){
				$str = '';
				$str += "<tr>";
				$str += "<td id=\"td"+groupCount+"\">";
				$str += "用户"
						+ (groupCount + 1)
						+ ":<input placeholder=\"手机号\"    onblur=\"queryUser("+groupCount+")\" style=\"width:220px;font-size:14px;margin-top: 10px;\" type=\"text\" id="+groupCount+" name=\"userMobileNum" + groupCount + "\"/>"
				$str += "</td></tr>";
				
				$str += "<tr id=\"tr"+groupCount+"\">";
				$str += "<td>";
				$str += "<span style=\"display:none;font-size:14px;color:red\" id=\"span"+groupCount+"\"><p></p></span>"
				$str += "</td>";
				$str += "</tr>";
				
				
				$("#tr" + MinusNum).after($str);
			}else if(groupCount = 'NaN'){
				var i = 0;
				$str = '';
				$str += "<tr>";
				$str += "<td id=\"td"+i+"\">";
				$str += "用户"
						+ (i + 1)
						+ ":<input placeholder=\"手机号\"    onblur=\"queryUser("+i+")\" style=\"width:220px;font-size:14px;margin-top: 10px;\" type=\"text\" id="+i+" name=\"userMobileNum" + i + "\"/>"
				$str += "</td></tr>";				
				
				$str += "<tr id=\"tr"+i+"\">";
				$str += "<td>";
				$str += "<span style=\"display:none;font-size:14px;color:red\" id=\"span"+i+"\"><p></p></span>"
				$str += "</td>";
				$str += "</tr>";
				$("#groupDetail").append($str);
				AddNum = 1;
			}
			$("#groupCountIpt").val(AddNum);
		}else if(op == 'del'){
			if(groupCount > 0){
			$("#groupDetail").find("tr").last().remove();
			$("#groupDetail").find("tr").last().remove();
			$("#groupCountIpt").val(MinusNum);
			}
		}
	}
	
	function repetitionMobile(num,mn){//num第几组用户,mn这组用户对应的手机号
		//1,得到用户所填写的总组数
		var groupCount = $("#groupCountIpt").val();
		//3,根据总组数,遍历所有组数,并得到该组对应的手机号
		for(var i = 0; i < groupCount; i++){
			if(num == i){//4,如果当前遍历的手机号与需要检查的手机号输入同一个输入框则结束本次循环
				continue;
			}else{
				var mobileNum = $("#" + i).val();//当前正在遍历的手机号
				if(mobileNum == ''){
					$("#span"+num).attr("style", "display:none;");
					return true;
				}else{
					if(mobileNum.length == 11){
						if(mobileNum == mn){
							$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
							$("#span"+num+" p").text("该手机号已经填写过了！");
							return false;
						}
					}else{
						$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
						$("#span"+num+" p").text("手机号不正确！");
						return false;
					}
			  }
			}
		}
		return true;
	}
	//根据手机号查询用户信息并显示
	function queryUser(num,from) {
		var data = false;
		var mn = $("#" + num).val();//当前需要检查的
		var falg = true;
	
			if(mn != '' && mn.length != 11){
				$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
				$("#span"+num+" p").text("手机号不正确！");
				return false;
			}
		
		
		if('from' != from){
			falg = repetitionMobile(num,mn);
		}
		if(mn != ''&& falg){
				$.ajax({
					type : "POST",
					cache: false,
					url : "<%=request.getContextPath()%> /queryUser",
					data : {
						'mn' : mn,
						'type':"vip"
					},
				dataType : "text",
				async : false,
				success : function(result) {
					if(result == 'paramError'){
						$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
						$("#span"+num+" p").text("手机号不正确！");
					}else if(result == 'fail'){
						$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
						$("#span"+num+" p").text("用户不存在！");
					}else if(result == 'mobileExist'){
						$("#span"+num).attr("style", "display:block;font-size:14px;color:red");
						$("#span"+num+" p").text("改手机号已发送过！");
					} else {
						data = true;
						$("#span"+num).attr("style", "display:block;font-size:14px;color:green");
						$("#span"+num+" p").text(result);
					}
				}
			});
		}
		return data;
	}
	//拼接输入框
	function groupCount() {
		var groupCount = $("#groupCountIpt").val();
		$str = '';
		$("#groupDetail tr").remove();
		$("#groupDetail a").remove();
		if($.trim(groupCount) == ''){
			$("#mobileAndmoney").attr("style", "display:none;");
		}else{
			$("#mobileAndmoney").attr("style", "display:block;");
		}
		var re = /^[1-9]+[0-9]*]*$/;
		if(re.test(groupCount)){
			$("#errorGroupCount").attr("style", "display:none;color: red;");
			for ( var i = 0; i < groupCount; i++) {
				//<![CDATA[
				$str += "<tr>";
				$str += "<td id=\"td"+i+"\">";
				$str += "用户"
						+ (i + 1)
						+ ":<input placeholder=\"手机号\"    onblur=\"queryUser("+i+")\" style=\"width:220px;font-size:14px;margin-top: 10px;\" type=\"text\" id="+i+" name=\"userMobileNum" + i + "\"/>"
				$str += "</td></tr>";
				
				$str += "<tr id=\"tr"+i+"\">";
				$str += "<td>";
				$str += "<span style=\"display:none;font-size:14px;color:red\" id=\"span"+i+"\"><p></p></span>"
				$str += "</td>";
				$str += "</tr>";
				//]]> 
			}
			$("#groupDetail").append($str);
			$("#groupDetailTable").attr("style", "display:block;");
		}else{
			$("#errorGroupCount").attr("style", "display:block;color: red;");
			$("#mobileAndmoney").attr("style", "display:none;")
			$("#groupDetailTable").attr("style", "display:none;color: red;");
		}
	}
	
	function sub(){
		var details = "";	
		var num = $("#groupCountIpt").val();
		for(var i = 0; i<num; i++){
			var mn = $("#" + i).val();//当前需要检查的
			if(mn !=''){
				if(queryUser(i)){
					details += mn+",";	
				}else{
					return false;
				}
			}
			
		}
		//alert(details);
		if(details==''){
			alert("请至少填写一个用户手机号");
			return false;
		}
		 $.ajax({
			type : 'POST',
			url : '/award/createVipAward',
			data:{				
				'details':details		
			},
			async : false,
			beforeSend:function(){
				xval=getBusyOverlay('viewport',
				{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
				{color:'blue', size:25, type:'o'});	
			},
			success : function(msg) {
				window.clearInterval(xval.ntime); 
				xval.remove();
				if(msg == 'ok'){
					alert("发放vip新客专享券成功!");
					window.location = "/redPacketDetail/list";
				}else{
					alert("发放vip新客专享券失败!");
				}
			},
			error : function() {
				alert("异常！");
			}
		});			
	}
	
	</script>

</body>
</html>
