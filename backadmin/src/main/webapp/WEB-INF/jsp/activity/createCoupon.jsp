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

<title>手动发放加息券</title>
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
					<i class="fa fa-pencil"></i> 手动发放加息券 
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form1" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">手动发放加息券</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">活动类型 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select name="itemType" id="itemType">
										<option value="">--请选择--</option>
										 <c:forEach var="redPacketRule" items="${redPacketRules}">
										 	<option value="${redPacketRule.id}">${redPacketRule.name}</option>
										 </c:forEach>
									</select>
									<div id="errorItemType" style="display: none;">
										活动类型不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">加息券类型 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select name="redpacketType" id="redpacketType">
										<option value="0">没有限制</option>
										<option value="1">新手标不可用</option>
										<option value="2">app专享</option>
										<option value="3">app专享且新手标不可用</option>
									</select>
									<div id="errorMoneyType1" style="display: none;">
										金额类型不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">金额类型 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select name="type" id="moneyType1">
										<option value="money">固定金额</option>
										<option value="rate">百分比金额</option>
									</select>
									<div id="errorMoneyType1" style="display: none;">
										金额类型不能为空</div>
								</div>
							</div>
							<div class="form-group">
							<label class="col-sm-3 control-label">开始时间<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="createTime"  id="createTime"
										class="ui_timepicker" />
										<div id="errorCreateTime" style="display: none;">
										开始时间不能为空</div>
								</div>
							</div>
							<div class="form-group">
							<label class="col-sm-3 control-label">到期时间<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="expiredTime"  id="expiredTime"
										class="ui_timepicker" />
										<div id="errorExpiredTime" style="display: none;">
										到期时间不能为空</div>
								</div>
							</div>
							<div class="form-group">
							<label class="col-sm-3 control-label">标的周期<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select name="timeType" id="timeType">
										<option value="0">默认</option>
										<option value="1">一个月</option>
										<option value="2">两个月</option>
										<option value="3">三个月</option>
										<option value="4">四个月</option>
										<option value="5">五个月</option>
										<option value="6">六个月</option>
										<option value="7">七个月</option>
										<option value="8">八个月</option>
										<option value="9">九个月</option>
										<option value="10">十个月</option>
										<option value="11">十一个月</option>
										<option value="12">十二个月</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">应用类型<span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<select name="usageDetail" id="usageDetail" onchange="change()">
										 	<option value="invest">投资可用</option>
										 	<option value="withdraw">提现可用</option>
									</select>
									<div id="errorusageDetail" style="display: none;">
										只能选投资可用</div>
								</div>
							</div>
							<div id="invest">
							 <div class="form-group">
								<label class="col-sm-3 control-label">满多少元可用<span class="asterisk">*</span></label>
								<div class="col-sm-1">
									<input type="text" name="investMoney" id="investMoney"  class="form-control" placeholder="满多少元可用"/>									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">低于多少利率可用<span class="asterisk">*</span></label>
								<div class="col-sm-1">
									<input type="text" name="investRate" id="investRate"  class="form-control" placeholder="低于多少利率可用"/>									
								</div>
							</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">添加手机号/加息券金额</label>
								<div class="col-sm-3">
									<a style="font-size:30px;cursor:pointer;" onclick="operationInput( 'add' )">+</a>&nbsp;&nbsp;&nbsp;
									<a id="del" style="font-size:30px;cursor:pointer;" onclick="operationInput( 'del' )">-</a>
								</div>
							</div>
							
						</div>
						<!-- panel-body -->

						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="return sub()">保存</button>
									<button type="button" class="btn btn-default" onclick="return res()">重置</button>
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
	function change(){
		var usageDetail=$("#usageDetail").val();
		if(usageDetail=="invest"){
			$("#invest").attr("style", "display:block;");
		}else{
			$("#invest").attr("style", "display:none;");
		}
	}
	var num = 1;
	
	/* 添加节点  */
	function operationInput( ope ) {
		var moneyType1=$("#moneyType1").val();
		if(ope == "add"){		
			$str = '';
			$str += "<div class='form-group'><label class='col-sm-3 control-label'>手机号/加息券：</label><div class='col-sm-3'>";
			$str += "<input type='text' name='mobile' id='mobile"+num+"'  placeholder='用户编号/手机号不能为空' onblur='queryUser("+num+")'/>";
			if(moneyType1=='money'){
				$str += "<input type='text' name='money' id='money"+num+"'  placeholder='金额不能为空'/>";
			}else{
			 $str += "<input type='text' name='money' id='money"+num+"' placeholder='利率不能为空'/>%";
			}
			$str += "<div  class='box' id='errorMobile"+num+"' style='display: none;'><p></p></div><div  class='box' id='errorMoney"+num+"' style='display: none;'><p></p></div></div></div>";
			$('.panel-body').append($str);
			num++;
		}else if(ope == "del"){
			if(num > 1){
				$('.panel-body div.form-group:last').remove();
				num--;
			}
		}
		
	}
	//根据手机号查询用户信息并显示
	function queryUser(num) {
		var data = false;
		var mn = $("#mobile" + num).val();//当前需要检查的
		var falg = true;
		if(mn==null || mn==''){
			$("#errorMobile"+num).attr("style", "display:block;font-size:14px;color:red");
			$("#errorMobile"+num+" p").text("手机号不能为空！");
			return false;
		}
		if(falg){
				$.ajax({
					type : "POST",
					cache: false,
					url : "<%=request.getContextPath()%> /queryUser",
					data : {
						'mn' : mn,
					},
				dataType : "text",
				async : false,
				success : function(result) {
					if(result == 'fail'){
						$("#errorMobile"+num).attr("style", "display:block;font-size:14px;color:red");
						$("#errorMobile"+num+" p").text("用户不存在！");
					} else {
						data = true;
						$("#errorMobile"+num).attr("style", "display:block;font-size:14px;color:green");
						$("#errorMobile"+num+" p").text(result);
					}
				}
			});
		}
		return data;
	}

	function verifyMoney(num){
		var money = $("#money"+num).val();
		money = $.trim(money);
		if(money==null || money==''){
			$("#errorMoney"+num).attr("style", "display:block;font-size:14px;color:red");
			$("#errorMoney"+num+" p").text('金额不能空');
			return false;
		}
		var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		if(!exp.test(money) || money <= 0){
			$("#errorMoney"+num).attr("style", "display:block;font-size:14px;color:red");
			$("#errorMoney"+num+" p").text('金额不正确');
			return false;
		}else{
			$("#errorMoney"+num).attr("style", "display:none;font-size:14px;color:red");
			$("#errorMoney"+num+" p").text('');
			return true;
		}
	}
	function sub(){
		var details = "";
		var itemType = $("#itemType").val();
		var moneyType1 = $("#moneyType1").val();
		var createTime = $("#createTime").val();
		var expiredTime = $("#expiredTime").val();
		var usageDetail = $("#usageDetail").val();
		var investMoney = $("#investMoney").val();
		var investRate = $("#investRate").val();
		var redpacketType=$("#redpacketType").val();
		var timeType=$("#timeType").val();
		if(itemType==null || itemType==''){
			$("#errorItemType").attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			$("#errorItemType").attr("style", "display:none");
		}
		if(moneyType1==null || moneyType1==''){
			$("#errorMoneyType1").attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			$("#errorMoneyType1").attr("style", "display:none");
		}
		if(createTime==null || createTime==''){
			$("#errorCreateTime").attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			$("#errorCreateTime").attr("style", "display:none");
		}
		if(expiredTime==null || expiredTime==''){
			$("#errorExpiredTime").attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			$("#errorExpiredTime").attr("style", "display:none");
		}
		if(moneyType1=='rate' && usageDetail=='withdraw'){
			$("#errorusageDetail").attr("style", "display:block;font-size:14px;color:red");
			return false;
		}else{
			$("#errorusageDetail").attr("style", "display:none");
		}
		var exp = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/;
		for(var i = 1; i<num; i++){
			var mobile = $("#mobile"+i).val();
			var money = $("#money"+i).val();
			if(verifyMoney(i) && queryUser(i)){
				details += mobile+"、"+money+";";			
			}else{
				return false;
			}
		}
		$.ajax({
			type : 'POST',
			url : '/award/createCoupon',
			data:{
				'itemType':itemType,
				'moneyType1':moneyType1,
				'createTime':createTime,
				'expiredTime':expiredTime,
				usageDetail:usageDetail,
				investMoney:investMoney,
				investRate:investRate,
				'details':details,
				'redpacketType':redpacketType,
				'timeType':timeType
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
					alert("发放加息券成功!");
					window.location = "/redPacketDetail/list";
				}else{
					alert("发放加息券失败!");
				}
			},
			error : function() {
				alert("异常！");
			}
		});	
		
		
		
		
		
		
		
		
	}
	jQuery(document).ready(function() {
		$(".ui_timepicker").datetimepicker({
			showSecond : true,
			minDate: new Date(),
			timeFormat : 'hh:mm:ss',
			stepHour : 1,
			stepMinute : 1,
			stepSecond : 1
		})
		Date.prototype.format = function(format){
var o = {
"M+" : this.getMonth()+2, //month
"d+" : this.getDate(), //day
"h+" : this.getHours(), //hour
"m+" : this.getMinutes(), //minute
"s+" : this.getSeconds(), //second
"q+" : Math.floor((this.getMonth()+3)/3), //quarter
"S" : this.getMilliseconds() //millisecond
}

if(/(y+)/.test(format)) {
format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
}

for(var k in o) {
if(new RegExp("("+ k +")").test(format)) {
format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
}
}
return format;
} 
		Date.prototype.format1 = function(format){
			var o = {
			"M+" : this.getMonth()+1, //month
			"d+" : this.getDate(), //day
			"h+" : this.getHours(), //hour
			"m+" : this.getMinutes(), //minute
			"s+" : this.getSeconds(), //second
			"q+" : Math.floor((this.getMonth()+3)/3), //quarter
			"S" : this.getMilliseconds() //millisecond
			}

			if(/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
			}

			for(var k in o) {
			if(new RegExp("("+ k +")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
			}
			}
			return format;
			} 
		var d=new Date().format("yyyy-MM-dd hh:mm:ss");
		var d1=new Date().format1("yyyy-MM-dd hh:mm:ss");
		$("#expiredTime").val(d);$("#createTime").val(d1);
	});	
	</script>

</body>
</html>
