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
<title>借款项目附加信息</title>
</head>
<body>
	<!-- 配置文件 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
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
					<i class="fa fa-pencil"></i> 借款项目附加信息
				</h2>
			</div>
			<div class="contentpanel">

				<!-- 创建借款表单 -->
				<form id="form1" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">借款项目附加信息--供应宝信息</h4>
						</div>
						<div class="panel-body">

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">成立时间 </label>
								<div class="col-sm-3">
									<input type="text" id="buildDate" name="buildDate"
										class="ui_timepicker" width="10px"> 
									<input type="hidden" name="loanId" id="loanId" value="${loanId}" />
									<input type="hidden" name="loanType" id="loanType" value="${loanType}" />
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">经营范围 </label>
								<div class="col-sm-3">
									<input type="text" name="operationBusiness" id="operationBusiness"
										width="10px" class="form-control" value="${supplychain.operationBusiness }"/>
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">注册资本 </label>
								<div class="col-sm-3">
									<input type="text" name="registeredCapital" value="${supplychain.registeredCapital }"
										id="registeredCapital" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">实收资本 </label>
								<div class="col-sm-3">
									<input type="text" name="realIncomeCapital" id="realIncomeCapital" width="10px"
										class="form-control"  value="${supplychain.realIncomeCapital }"/>
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">已经营年限 </label>
								<div class="col-sm-3">
									<input type="text" name="operationYear" id="operationYear"
										width="10px" class="form-control" value="${supplychain.operationYear }"/>
								</div>
							</div>
							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">公司简介 </label>
								<div class="col-sm-3">
									<textarea type="tel" name="companyDesc"
										id="companyDesc">${supplychain.companyDesc }</textarea>
								</div>
							</div>		

							<div class="form-group" style="background:#FFEC8B;">
								<label class="col-sm-3 control-label">组织结构 </label>
								<div class="col-sm-3">
									<input type="text" id="organization" value="${supplychain.organization }"
										name="organization" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFEC8B;">
								<label class="col-sm-3 control-label">员工人数 </label>
								<div class="col-sm-3">
									<input type="text" id="staffNumber" name="staffNumber"
									value="${supplychain.staffNumber }" width="10px" class="form-control" />
								</div>
							</div>
							<div class="form-group" style="background:#FFEC8B;">
								<label class="col-sm-3 control-label">主营产品或服务 </label>
								<div class="col-sm-3">
									<input type="text" id="operationProduction"
									value="${supplychain.operationProduction }"	name="operationProduction" width="10px"
										class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#BFEFFF;">
								<label class="col-sm-3 control-label">财务数据 </label>
								<div class="col-sm-3">
									<!-- 实例化编辑器name属性的值为后台获取响应参数的key -->
									<script id="financeData" name="financeData"
										type="text/plain">${supplychain.financeData }
									</script>
									<script type="text/javascript">
									     var ue = UE.getEditor('financeData');
									</script>
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">信用报告 </label>
								<div class="col-sm-3">
									<input type="text" id="creditReport" value="${supplychain.creditReport }"
										name="creditReport" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">全国法院被执行人信息(个人) </label>
								<div class="col-sm-3">
									<input type="text" id="nationalCourtsReport" name="nationalCourtsReport"
									value="${supplychain.nationalCourtsReport }"	width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">失信被执行人名单查询(个人) </label>
								<div class="col-sm-3">
									<input type="text" id="dishonstPersonReport"
									value="${supplychain.dishonstPersonReport }"	name="dishonstPersonReport" width="10px"
										class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">公开渠道查询负面信息(个人) </label>
								<div class="col-sm-3">
									<input type="text" id="publicChannelNegativeInfo" name="publicChannelNegativeInfo"
									value="${supplychain.publicChannelNegativeInfo }"	width="10px" class="form-control" />
								</div>
							</div>

							
							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">核心企业信息 </label>
								<div class="col-sm-3">
									<input type="text" id="coreEnterpriseInfo" name="coreEnterpriseInfo" width="10px"
									value="${supplychain.coreEnterpriseInfo }"	class="form-control" />
								</div>
							</div>
                            <div class="form-group">
								<label class="col-sm-3 control-label">其他负面信息
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="otherNegativeInfo"
										id="otherNegativeInfo" >${supplychain.otherNegativeInfo }</textarea>
								</div>
							</div>
							<div class="form-group" >
								<label class="col-sm-3 control-label">担保措施 </label>
								<div class="col-sm-3">
									<input type="text" id="guaranteeMeasures"
									value="${supplychain.guaranteeMeasures }"	name="guaranteeMeasures" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">借款用途 </label>
								<div class="col-sm-3">
									<input type="text" id="loanApplication" name="loanApplication"
									value="${supplychain.loanApplication }"	width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">还款来源 </label>
								<div class="col-sm-3">
									<input type="text" id="repaymentSource"
									value="${supplychain.repaymentSource}"	name="repaymentSource" width="10px" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">风控提示
								</label>
								<div class="col-sm-3">
									<input type="text" name="reminderInfo" id="reminderInfo"
										width="10px" class="form-control" value="${supplychain.reminderInfo }"
									/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">风控措施
								</label>
								<div class="col-sm-3">
									<input type="text" name="measuresInfo" id="measuresInfo"
										width="10px" class="form-control" value="${supplychain.measuresInfo }"
									 />
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">逾期处理方式
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="overdueInfo" 
										id="overdueInfo">${supplychain.overdueInfo }</textarea>
								</div>
							</div>
							
							<div class="form-group">
										<label class="col-sm-3 control-label">项目地点</label>
										<div class="col-sm-3">
											<input type="text" name="itemAddress" id="itemAddress"
												width="10px" class="form-control" value="${supplychain.itemAddress}" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">借款人姓名</label>
										<div class="col-sm-3">
											<input type="text" name="borrowerName" id="borrowerName"
												width="10px" class="form-control" value="${supplychain.borrowerName }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">借款人身份证</label>
										<div class="col-sm-3">
											<input type="text" name="borrowerIdCard" id="borrowerIdCard"
												width="10px" class="form-control" value="${supplychain.borrowerIdCard }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">新增/展期 </label>
										<div class="col-sm-3">
											<select name="remark" id="remark">
												<option value="">请选择</option>
												<option value="新增"
													<c:if test="${supplychain.remark == '新增'}">selected</c:if>>新增</option>
												<option value="展期"
													<c:if test="${supplychain.remark == '展期'}">selected</c:if>>展期</option>
											</select>
										</div>
									</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">其他说明
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="otherInfo"
										id="otherInfo" >${supplychain.otherInfo }</textarea>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button"
											onclick="sub()">保存</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		</div>
	</section>

	<script type="text/javascript">
		function sub(){
			if(window.confirm("确认操作借款项目附加信息?")){
				$.ajax({
					cache : false,
					type : "POST",
					dataType : 'json',
					url : "<%=request.getContextPath()%>/loan/createLoanDetail",
				data : $("#form1").serialize(), //要发送的是ajaxFrm表单中的数据
				async : false,
				beforeSend:function(){
					xval=getBusyOverlay('viewport',
					{color:'black', opacity:0.3, text:'正在处理，请稍后...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
				},
				success : function(data) {
					window.clearInterval(xval.ntime); 
					xval.remove();
					var dataObj = eval(data);
					if (dataObj.status == 'ok') {
						alert('附加信息创建成功!');
						location = "/loan/loanList";
					} else if (dataObj.status == 'fail') {
						if (dataObj.error == 'loanIdNULL') {
							alert('创建失败!借款项目id为空');
						} else if (dataObj.error == 'loanNULL') {
							alert('创建失败!借款项目为空');
						} else if (dataObj.error == 'loanTypeNULL') {
							alert('创建失败!借款类型不正确');
						} 
					}else if(dataObj.status == 'updateOk'){
						alert('附加信息编辑成功!');
						location = "/loan/loanList";
					}
				}
			});
			}
		}
		
		function Todate(num){
	        //Fri Oct 31 18:00:00 UTC+0800 2008  
	        //Thu Mar 05 14:40:00 CST 2015
	        num=num+"";
	        var date="";
	        var month=new Array();
	        month["Jan"]='01';month["Feb"]='02';month["Mar"]='03';month["Apr"]='04';month["May"]='05';month["Jun"]='06';
	        month["Jul"]='07';month["Aug"]='08';month["Sep"]='09';month["Oct"]=10;month["Nov"]=11;month["Dec"]=12;
	        var week=new Array();
	        week["Mon"]="一";week["Tue"]="二";week["Wed"]="三";week["Thu"]="四";week["Fri"]="五";week["Sat"]="六";week["Sun"]="日";
	        str=num.split(" ");
	        date=str[5]+"-";
	        date=date+month[str[1]]+"-"+str[2]+" "+str[3];
	        //date=date+" 周"+week[str[0]];
	        return date;
		}

		$(function() {
			//方便测试,创建企业贷初始化赋值.
// 			var sv = new Array(
// 					"buildDate","operationBusiness","registeredCapital","realIncomeCapital","operationYear","paidInCapital","affiliatedEnterprise","companyDesc","customerName","customerAge","registeredPermanentResidence","locaResidenceTime","housingConditionsDetail","productionAddress","organization","staffNumber","operationProduction","creditReport","nationalCourtsReport","dishonstPersonReport","publicChannelNegativeInfo","enterpriseCreditReport","ncriByEnterprise","lttoftpstelqByEnterprise","ocniqByEnterprise","coreEnterpriseInfo","guaranteeMeasures","loanApplication","repaymentSource","netEarnings","sinkingFund","debtRevenue","assetsRealization","externalSupport");
// 			for ( var i = 0; i < sv.length; i++) {
// 				var v = sv[i].trim();
// 				if (v.length > 0) {
// 					$("#"+v).val(i);
// 				}
// 			}


			var buildDate = '${supplychain.buildDate}';
			if(buildDate != '' && buildDate.length > 0){
				$("#buildDate").val(Todate(buildDate));
			}
			$(".ui_timepicker").datetimepicker({
				//showOn: "button",
				//buttonImage: "./css/images/icon_calendar.gif",
				//buttonImageOnly: true,
				showSecond : true,
				timeFormat : 'hh:mm:ss',
				stepHour : 1,
				stepMinute : 1,
				stepSecond : 1
			});
		});
	</script>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/dropzone.css" />
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/bootstrap-editable.css">
	<link type="text/css"
		href="${pageContext.request.contextPath}/css/jquery-ui-1.8.17.custom.css"
		rel="stylesheet" />
	<link type="text/css"
		href="${pageContext.request.contextPath}/css/jquery-ui-timepicker-addon.css"
		rel="stylesheet" />

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-1.8.17.custom.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>

</body>
</html>
