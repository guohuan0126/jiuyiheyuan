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
<link rel="shortcut icon" href="${ctx}/images/favicon.png"
	type="image/png">

<title>冻结解冻</title>

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
					<i class="fa fa-table"></i>当前位置：冻结/解冻
				</h2>				
			</div>
			<div class="contentpanel">
				<div class="panel panel-default">
					<div class="panel-heading">
						<form id="baseform" class="form-inline" action="" method="post">
							<div class="form-group">
								<label class="sr-only" for="exampleInputEmail2">查询条件</label> <input
									type="text" name="username" id="username" class="form-control"
									id="exampleInputEmail2" placeholder="用户编号|手机号|姓名">
								<div id="errorusername" style="display: none;">查询条件不能空</div>
							</div>
							<button class="btn btn-primary" onclick="checksearch()"
								type="button">查询</button>
						</form>
						<br />
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>操作</th>
									<th>用户编号</th>
									<th>手机号</th>
									<th>用户真实姓名</th>
									<th>可用余额</th>
									<th>冻结金额</th>
								</tr>
							</thead>
							<tbody id="ttab">								
							</tbody>
						</table>
						<form id="freezeForm" class="form-horizontal ">
							<div class="form-group">
								<label class="col-sm-1 control-label">冻结金额（元）<span
									class="asterisk">*</span></label>
								<div class="col-sm-1">
									<input type="hidden" id="ids" name="userId"> <input
										type="text" name="money" id="money" placeholder="冻结金额..."
										class="form-control" />
								</div>
								<label class="col-sm-1 control-label">自动解冻日期<span
									class="asterisk">*</span></label>
								<div class="col-sm-1">
									<input type="text" name="expiredTime"  id="expiredTime"
										class="ui_timepicker" />
								</div>
								<label class="col-sm-1 control-label">冻结原因 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<textarea rows="1" name="description" id="description"
										class="form-control" placeholder="冻结原因..."></textarea>
									<div id="erroruserId" style="display: none;">请选择用户</div>
									<div id="erroruser" style="display: none;">不能选择多个用户</div>
								</div>
								<div class="col-sm-9 col-sm-offset-3">
				                 <input
										class="btn btn-primary" type="button" value="保存" onclick="return sub()">
									<button type="reset" class="btn btn-default">重置</button>
				               </div>
								<!-- <label class="col-sm-2 control-label"><input
										class="btn btn-primary" type="button" value="保存" onclick="return sub()">
									<button type="reset" class="btn btn-default">重置</button> </label> -->

							</div>
						</form>
					</div>
					<div class="panel-body">
						<table class="table table-primary table-striped table-hover ">
							<thead>
								<tr>
									<th>冻结用户</th>
									<th>冻结金额</th>
									<th>冻结原因</th>
									<th>冻结时间</th>
									<th>冻结管理员</th>
									<th>解冻时间</th>
									<th>解冻管理员</th>
									<th>状态</th>
									<th>本地账户</th>
									<th>易宝账户</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.results}" var="fundFreeze">
									<tr>
										<td>${fundFreeze.userId}</td>
										<td>${fundFreeze.money}</td>
										<td>${fundFreeze.description}</td>
										<td><fmt:formatDate value="${fundFreeze.freezeTime }"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td>${fundFreeze.freezeUserId}</td>
										<td><fmt:formatDate value="${fundFreeze.unFreezeTime }"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td>${fundFreeze.unfreezeUserId}</td>

										<td><c:if test="${fundFreeze.status eq 1}">
	                           		 冻结未成功
	                           </c:if> <c:if test="${fundFreeze.status eq 0}">
	                       		             已冻结
	                           </c:if> <c:if test="${fundFreeze.status eq 2}">
		                       		 已解冻
		                        </c:if></td>
										<td>可用余额：${fundFreeze.balance}
											冻结金额：${fundFreeze.freezeMoney}</td>
										<td>可用余额：${fundFreeze.yeepaybalance}
											冻结金额：${fundFreeze.yeepayfreezeMoney}</td>
									  <td>
									  <c:if test="${fundFreeze.status eq 0}">
									   <div id="unf">  <a style="cursor:pointer"  onclick="unfreeze('/unfreeze/tounfreeze?id=${fundFreeze.id}');">解冻</a></div>	         
									  </c:if>
									  </td>
									  
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<%@ include file="../base/page.jsp"%>

					</div>
					<!-- panel-body -->
				</div>
				<!-- panel -->
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>

	<script>
		jQuery(document).ready(function() {
			$(".ui_timepicker").datetimepicker({
				//showOn: "button",
				//buttonImage: "./css/images/icon_calendar.gif",
				//buttonImageOnly: true,
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
			var d=new Date().format("yyyy-MM-dd hh:mm:ss"); 
			//d.setMonth(d.getMonth()+1);
//	d.format("yyyy-MM-dd hh:mm:ss");
			$("#expiredTime").val(d);
		});
		function checksearch() {

			var username = $("#username").val();
			var list;
			var str = '';
			if (username == '') {
				$('#errorusername').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			} else {
			$('#errorusername').attr("style",
						"display:none;font-size:14px;color:red");
				$.ajax({
							type : "POST",
							url : '/user/getusers',
							data : $('#baseform').serialize(),// 你的formid
							async : false,
							dataType : 'json',
							error : function(e) {
								alert("异常");
							},
							success : function(data) {
								list = eval(data);
								if(list==''){
								str='<span style="width:150px;font-size:20px;color:red;text-align:center;display:block">没有数据</span>';
								}
								for ( var k in list) {
									str += '<tr>';
									str += '<td><input name=\"userId\" value="'+list[k].userId+'" id="'+list[k].userId+'" type=\"checkbox\"></td> ';
									str += '<td>' + list[k].userId + '</td>';
									str += '<td>' + list[k].mobileNumber
											+ '</td>';
									str += '<td>' + list[k].realname + '</td>';
									str += '<td>' + list[k].balance + '</td>';
									str += '<td>' + list[k].frozenMoney + '</td>';
									str += '</tr>';
								}
								$("#ttab").html(str);
							}
						});
				//  $("#baseform").submit();
			}
		}
		function sub() {
			var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
			var money = $("#money").val();
			var expiredTime = $("#expiredTime").val();
			var description = $("#description").val();
			var count = 0;
			$("[name = userId]:checkbox")
					.each(
							function() {
								if ($(this).is(":checked")) {
								    count++;
									if (count == 1) {
										$("#ids").val($(this).attr("value"));
									} else {
										/* $('#erroruser')
												.attr("style",
														"display:block;font-size:14px;color:red"); */
										return false;
									}
									
								}
							});
			if (count == 0) {
				$('#erroruserId').attr("style",
						"display:block;font-size:14px;color:red");
				$('#erroruser').attr("style","display:none;font-size:14px;color:red");
				return false;
			}else if(count>1){
			$('#erroruserId').attr("style",
						"display:none;font-size:14px;color:red");
			$('#erroruser').attr("style","display:block;font-size:14px;color:red");
			return false;
			}else if(count==1){
			var flag1=true;var flag2=true;var flag3=true;var flag4=true;
			$('#erroruserId').attr("style",
						"display:none;font-size:14px;color:red");
			  $('#erroruser').attr("style","display:none;font-size:14px;color:red");
			  if (money == '') {
				baseError('money', '冻结金额不能空',"error");
				flag1=false;
				return false;
			}else{
			 flag1=true;
			baseError('money', '冻结金额不能空',"none");
			}
			
			 if (!exp.test(money) || money <= 0) {
			 flag2=false;
				baseError('money', '冻结金额不正确',"error");
				return false;
			} else{
			flag2=true;
			baseError('money', '冻结金额不正确',"none");
			}
			
			if (expiredTime == '') {
			flag3=false;
				baseError('expiredTime', '自动解冻日期不能是空',"error");
				return false;
			} else{
			flag3=true;
			baseError('expiredTime', '自动解冻日期不能是空',"none");
			
			}
			if (description == '') {
			flag4=false;
				baseError('description', '冻结原因不能是空',"error");
				return false;
			}else{
			flag4=true;
			baseError('description', '冻结原因不能是空',"none");
			} 
			if(flag1==true && flag2==true && flag3==true && flag4==true){
			  $.ajax({
					type : "POST",
					url : '/freeze/freezeMoney',
					data : $('#freezeForm').serialize(),// 你的formid
					async : false,
					error : function(e) {
						alert("异常");
					},
					success : function(data) {
						if (data == 'ok') {
							alert('操作成功');
							window.location = "/freeze/freezeList";
						}else if(data=='fail') {
							alert('操作失败');
						}else if(data=='balance'){
							alert('余额不足,操作失败');
						}else if(data=='unfreeze'){
						    alert('解冻金额大于冻结金额,操作失败');
						} else {
							alert(data);
						}
					}
				});
			}
			
			} 
		}
		//显示或隐藏错误信息
		function baseError(id, message,type) {
		    if(type=='none'){
		      $("#" + id + "Error").remove();
		    }else{
		    $("#" + id + "Error").remove();
			$str = "<span style=\"font-size:14px;color:red\" id=\""+id+"Error\">"
					+ message + "</span>";
			$("#" + id).after($str);
		    }
			
		}
		function unfreeze(url){
		 var bool = window.confirm("您确定要解冻吗?");
		 if(bool==true){
		 
		 $("#unf").attr("style","display:none"); 
		  $.ajax({			
			type : 'POST',			
			url : url, 			
			success : function(msg) {
					if(msg == 'ok'){
						alert("解冻成功!");	
						window.location = "/freeze/freezeList";				
					}else if(msg=='fail') {
					
					 $("#unf").attr("style","display:block"); 
							alert('操作失败');
						}else if(msg=='balance'){
						 $("#unf").attr("style","display:block"); 
							alert('余额不足,操作失败');
						}else if(msg=='unfreeze'){
						 $("#unf").attr("style","display:block"); 
						    alert('解冻金额大于冻结金额,操作失败');
						}else {
							alert(msg);
						}
					
				},
				error : function() {
					alert("异常！");
				}
		});	
		 }
		
	}

		
	</script>
</body>
</html>
