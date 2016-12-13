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
							<h4 class="panel-title">借款项目附加信息--企业贷信息</h4>
						</div>
						<div class="panel-body">

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">成立时间 </label>
								<div class="col-sm-3">
									<input type="text" id="establishTime" name="establishTime"
										class="ui_timepicker" width="10px"> <input
										type="hidden" name="loanId" id="loanId" value="${loanId}" />
									<input type="hidden" name="loanType" id="loanType"
										value="${loanType}" />
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">经营范围 </label>
								<div class="col-sm-3">
									<input type="text" name="businessScope" id="businessScope"
										width="10px" class="form-control" value="${enterprise.businessScope }"/>
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">注册资本 </label>
								<div class="col-sm-3">
									<input type="text" name="registeredCapital" value="${enterprise.registeredCapital }"
										id="registeredCapital" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">借款人持股比例 </label>
								<div class="col-sm-3">
									<input type="text" name="inshold" id="inshold" width="10px"
										class="form-control"  value="${enterprise.inshold }"/>
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">已经营年限 </label>
								<div class="col-sm-3">
									<input type="text" name="operatingPeriod" id="operatingPeriod"
										width="10px" class="form-control" value="${enterprise.operatingPeriod }"/>
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">实收资本 </label>
								<div class="col-sm-3">
									<input type="text" name="paidInCapital" id="paidInCapital"
										width="10px" class="form-control" value="${enterprise.paidInCapital }"/>
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">关联企业 </label>
								<div class="col-sm-3">
									<textarea type="tel" name="affiliatedEnterprise"
										id="affiliatedEnterprise">${enterprise.affiliatedEnterprise }</textarea>
								</div>
							</div>

							<div class="form-group" style="background:yellow;">
								<label class="col-sm-3 control-label">公司简介 </label>
								<div class="col-sm-3">
									<textarea type="tel" name="companyIntroduction"
										id="companyIntroduction">${enterprise.companyIntroduction }</textarea>
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">客户姓名 </label>
								<div class="col-sm-3">
									<input type="text" name="customerName" id="customerName"
										width="10px" class="form-control" value="${enterprise.customerName }"/>
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">客户性别 </label>
								<div class="col-sm-3">
									<select name="customerGender" id="customerGender">
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">客户年龄 </label>
								<div class="col-sm-3">
									<input type="text" name="customerAge" id="customerAge"
										width="10px" class="form-control" value="${enterprise.customerAge }"/>
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">户籍所在地 </label>
								<div class="col-sm-3">
									<input type="text" name="registeredPermanentResidence"
										id="registeredPermanentResidence" width="10px"
										class="form-control" value="${enterprise.registeredPermanentResidence }"/>
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">婚姻状况 </label>
								<div class="col-sm-3">
									<select name="maritalStatus" id="maritalStatus">
										<option value="">请选择</option>
										<option value="已婚">已婚</option>
										<option value="未婚">未婚</option>
										<option value="离异">离异</option>
										<option value="丧偶">丧偶</option>
									</select>
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">教育程度 </label>
								<div class="col-sm-3">
									<select name="educationalStatus" id="educationalStatus">
										<option value="">请选择</option>
										<option value="小学">小学</option>
										<option value="初中">初中</option>
										<option value="高中">高中</option>
										<option value="大专">大专</option>
										<option value="本科">本科</option>
										<option value="硕士">硕士</option>
										<option value="博士">博士</option>
									</select>
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">本地居住年限 </label>
								<div class="col-sm-3">
									<input type="text" name="locaResidenceTime"
										id="locaResidenceTime" width="10px" class="form-control" value="${enterprise.locaResidenceTime }"/>
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">住房状况 </label>
								<div class="col-sm-3">
									<select name="housingConditions" id="housingConditions"
										onchange="elseOption()">
										<option value="">请选择</option>
										<option value="自有">自有</option>
										<option value="租赁">租赁</option>
										<option value="按揭">按揭</option>
										<option value="经营场所">经营场所</option>
										<option value="与亲属共住">与亲属共住</option>
										<option value="其他">其他</option>
									</select>
								</div>
							</div>

							<div class="form-group" style="display: none;background:orange;" id="hcdDIV">
								<label class="col-sm-3 control-label">其他详情 </label>
								<div class="col-sm-3">
									<input type="text" name="housingConditionsDetail" width="10px" value="${enterprise.housingConditions }"
										class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">生产经营地址 </label>
								<div class="col-sm-3">
									<input type="text" name="productionAddress" value="${enterprise.productionAddress }"
										id="productionAddress" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">负债历史 </label>
								<div class="col-sm-3">
									<!-- 实例化编辑器name属性的值为后台获取响应参数的key -->
									<script id="debtHistory" name="debtHistory"
										type="text/plain">${enterprise.debtHistory }
									</script>
									<script type="text/javascript">
									     var ue = UE.getEditor('debtHistory');
									</script>
								</div>
							</div>
							
							<div class="form-group" style="background:orange;">
								<label class="col-sm-3 control-label">资产状况 </label>
								<div class="col-sm-3">
									<script id="financialStatus" name="financialStatus"
										type="text/plain">${enterprise.financialStatus }
									</script>
									<script type="text/javascript">
									     var ue = UE.getEditor('financialStatus');
									</script>
								</div>
							</div>

							<div class="form-group" style="background:#FFEC8B;">
								<label class="col-sm-3 control-label">组织结构 </label>
								<div class="col-sm-3">
									<input type="text" id="organizationStructure" value="${enterprise.organizationStructure }"
										name="organizationStructure" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFEC8B;">
								<label class="col-sm-3 control-label">员工人数 </label>
								<div class="col-sm-3">
									<input type="text" id="numberEmployees" name="numberEmployees"
									value="${enterprise.numberEmployees }" width="10px" class="form-control" />
								</div>
							</div>
							<div class="form-group" style="background:#FFEC8B;">
								<label class="col-sm-3 control-label">主营产品或服务 </label>
								<div class="col-sm-3">
									<input type="text" id="specializedProductsAndServices"
									value="${enterprise.specializedProductsAndServices }"	name="specializedProductsAndServices" width="10px"
										class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#BFEFFF;">
								<label class="col-sm-3 control-label">财务数据 </label>
								<div class="col-sm-3">
									<!-- 实例化编辑器name属性的值为后台获取响应参数的key -->
									<script id="financialData" name="financialData"
										type="text/plain">${enterprise.financialData }
									</script>
									<script type="text/javascript">
									     var ue = UE.getEditor('financialData');
									</script>
								</div>
							</div>

							<div class="form-group" style="background:#BFEFFF;">
								<label class="col-sm-3 control-label">偿债能力分析 </label>
								<div class="col-sm-3">
									<!-- 实例化编辑器name属性的值为后台获取响应参数的key -->
									<script id="solvencyAnalysis" name="solvencyAnalysis"
										type="text/plain">${enterprise.solvencyAnalysis }
									</script>
									<script type="text/javascript">
									     var ue = UE.getEditor('solvencyAnalysis');
									</script>
								</div>
							</div>

							<div class="form-group" style="background:#BFEFFF;">
								<label class="col-sm-3 control-label">盈利能力分析 </label>
								<div class="col-sm-3">
									<!-- 实例化编辑器name属性的值为后台获取响应参数的key -->
									<script id="profitabilityAnalysis" name="profitabilityAnalysis"
										type="text/plain">${enterprise.profitabilityAnalysis }
									</script>
									<script type="text/javascript">
									     var ue = UE.getEditor('profitabilityAnalysis');
									</script>
								</div>
							</div>

							<div class="form-group" style="background:#BFEFFF;">
								<label class="col-sm-3 control-label">运营能力分析 </label>
								<div class="col-sm-3">
									<!-- 实例化编辑器name属性的值为后台获取响应参数的key -->
									<script id="capabilitiesAnalysis" name="capabilitiesAnalysis"
										type="text/plain">${enterprise.capabilitiesAnalysis }
									</script>
									<script type="text/javascript">
									     var ue = UE.getEditor('capabilitiesAnalysis');
									</script>
								</div>
							</div>


							<div class="form-group" style="background:#BFEFFF;">
								<label class="col-sm-3 control-label">发展能力分析 </label>
								<div class="col-sm-3">
									<!-- 实例化编辑器name属性的值为后台获取响应参数的key -->
									<script id="developmentAbilityAnalysis"
										name="developmentAbilityAnalysis" type="text/plain">${enterprise.developmentAbilityAnalysis }
									</script>
									<script type="text/javascript">
									     var ue = UE.getEditor('developmentAbilityAnalysis');
									</script>
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">个人信用报告 </label>
								<div class="col-sm-3">
									<input type="text" id="personalCreditReport" value="${enterprise.personalCreditReport }"
										name="personalCreditReport" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">全国法院被执行人信息(个人) </label>
								<div class="col-sm-3">
									<input type="text" id="ncriByPersonal" name="ncriByPersonal"
									value="${enterprise.ncriByPersonal }"	width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">失信被执行人名单查询(个人) </label>
								<div class="col-sm-3">
									<input type="text" id="lttoftpstelqByPersonal"
									value="${enterprise.lttoftpstelqByPersonal }"	name="lttoftpstelqByPersonal" width="10px"
										class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">公开渠道查询负面信息(个人) </label>
								<div class="col-sm-3">
									<input type="text" id="ocniqByPersonal" name="ocniqByPersonal"
									value="${enterprise.ocniqByPersonal }"	width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">企业信用报告 </label>
								<div class="col-sm-3">
									<input type="text" id="enterpriseCreditReport"
									value="${enterprise.enterpriseCreditReport }"	name="enterpriseCreditReport" width="10px"
										class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">全国法院被执行人信息(企业) </label>
								<div class="col-sm-3">
									<input type="text" id="ncriByEnterprise"
									value="${enterprise.ncriByEnterprise }"	name="ncriByEnterprise" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">失信被执行人名单查询(企业) </label>
								<div class="col-sm-3">
									<input type="text" id="lttoftpstelqByEnterprise"
									value="${enterprise.lttoftpstelqByEnterprise }"	name="lttoftpstelqByEnterprise" width="10px"
										class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">公开渠道查询负面信息(企业) </label>
								<div class="col-sm-3">
									<input type="text" id="ocniqByEnterprise"
									value="${enterprise.ocniqByEnterprise }"	name="ocniqByEnterprise" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group" style="background:#FFA500;">
								<label class="col-sm-3 control-label">行政处罚信息(企业) </label>
								<div class="col-sm-3">
									<input type="text" id="api" name="api" width="10px"
									value="${enterprise.api }"	class="form-control" />
								</div>
							</div>

							<div class="form-group" >
								<label class="col-sm-3 control-label">担保措施 </label>
								<div class="col-sm-3">
									<input type="text" id="guaranteesMeasures"
									value="${enterprise.guaranteesMeasures }"	name="guaranteesMeasures" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">借款用途 </label>
								<div class="col-sm-3">
									<input type="text" id="loanPurpose" name="loanPurpose"
									value="${enterprise.loanPurpose }"	width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">经营性现金收入 </label>
								<div class="col-sm-3">
									<input type="text" id="operatingCashIncome"
									value="${enterprise.operatingCashIncome }"	name="operatingCashIncome" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">净盈利 </label>
								<div class="col-sm-3">
									<input type="text" id="netEarnings" name="netEarnings"
									value="${enterprise.netEarnings }"	width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">偿债准备金 </label>
								<div class="col-sm-3">
									<input type="text" id="sinkingFund" name="sinkingFund"
									value="${enterprise.sinkingFund }"	width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">债务收入 </label>
								<div class="col-sm-3">
									<input type="text" id="debtRevenue" name="debtRevenue"
									value="${enterprise.debtRevenue }"	width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">资产变现 </label>
								<div class="col-sm-3">
									<input type="text" id="assetsRealization" value="${enterprise.assetsRealization }"
										name="assetsRealization" width="10px" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">外部支持 </label>
								<div class="col-sm-3">
									<input type="text" id="externalSupport" name="externalSupport"
									value="${enterprise.externalSupport }"	width="10px" class="form-control" />
								</div>
							</div>
						
							<div class="form-group">
								<label class="col-sm-3 control-label">其他说明
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="otherDescription"
										id="otherDescription" >${enterprise.otherDescription }</textarea>
								</div>
							</div>
						<div class="form-group">
								<label class="col-sm-3 control-label">风控提示
								</label>
								<div class="col-sm-3">
									<input type="text" name="reminderInfo" id="reminderInfo"
										width="10px" class="form-control" value="${enterprise.reminderInfo }"
									/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">风控措施
								</label>
								<div class="col-sm-3">
									<input type="text" name="measuresInfo" id="measuresInfo"
										width="10px" class="form-control" value="${enterprise.measuresInfo }"
									 />
								</div>
							</div>
								<div class="form-group">
								<label class="col-sm-3 control-label">逾期处理方式
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="overdueInfo" 
										id="overdueInfo">${enterprise.overdueInfo }</textarea>
								</div>
							</div>
							<div class="form-group">
										<label class="col-sm-3 control-label">项目地点</label>
										<div class="col-sm-3">
											<input type="text" name="itemAddress" id="itemAddress"
												width="10px" class="form-control" value="${enterprise.itemAddress}" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">借款人姓名</label>
										<div class="col-sm-3">
											<input type="text" name="borrowerName" id="borrowerName"
												width="10px" class="form-control" value="${enterprise.borrowerName }" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label">借款人身份证号</label>
										<div class="col-sm-3">
											<input type="text" name="borrowerIdCard" id="borrowerIdCard"
												width="10px" class="form-control" value="${enterprise.borrowerIdCard}" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label">新增/展期 </label>
										<div class="col-sm-3">
											<select name="remark" id="remark">
												<option value="">请选择</option>
												<option value="新增"
													<c:if test="${enterprise.remark == '新增'}">selected</c:if>>新增</option>
												<option value="展期"
													<c:if test="${enterprise.remark == '展期'}">selected</c:if>>展期</option>
											</select>
										</div>
									</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">其他说明
								</label>
								<div class="col-sm-3">
									<textarea type="tel" name="otherInfo"
										id="otherInfo" >${enterprise.otherInfo }</textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button"
											onclick="return sub()">保存</button>
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
			if(window.confirm('确认操作借款项目附加信息?')){
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

		// 		function subVerify() {
		// 			var num = 0;
		// 			var sv = new Array("brand", "licensePlateNumber",
		// 					"kilometreAmount", "buyTime", "secondHandPrice",
		// 					"assessPrice", "guaranteeType", "guaranteeRate",
		// 					"supervisionMode", "overdueHandling");
		// 			for ( var i = 0; i < sv.length; i++) {
		// 				var v = sv[i].trim();
		// 				if (v.length > 0) {
		// 					if (!verifyParam(v)) {
		// 						++num;
		// 					}
		// 				}
		// 			}
		// 			return num;
		// 		}

		function elseOption() {
			var hcVal = $("#housingConditions").val();
			if (hcVal == '其他') {
				$("#hcdDIV").attr("style", "display: block;background:orange;");
			} else {
				$("#hcdDIV").attr("style", "display: none;background:orange;");
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
// 					"establishTime","businessScope","registeredCapital","inshold","operatingPeriod","paidInCapital","affiliatedEnterprise","companyIntroduction","customerName","customerAge","registeredPermanentResidence","locaResidenceTime","housingConditionsDetail","productionAddress","organizationStructure","numberEmployees","specializedProductsAndServices","personalCreditReport","ncriByPersonal","lttoftpstelqByPersonal","ocniqByPersonal","enterpriseCreditReport","ncriByEnterprise","lttoftpstelqByEnterprise","ocniqByEnterprise","api","guaranteesMeasures","loanPurpose","operatingCashIncome","netEarnings","sinkingFund","debtRevenue","assetsRealization","externalSupport");
// 			for ( var i = 0; i < sv.length; i++) {
// 				var v = sv[i].trim();
// 				if (v.length > 0) {
// 					$("#"+v).val(i);
// 				}
// 			}


			var establishTime = '${enterprise.establishTime}';
			if(establishTime != '' && establishTime.length > 0){
				$("#establishTime").val(Todate(establishTime));
			}
			
			var customerGender = '${enterprise.customerGender}';
			if(customerGender != '' && customerGender.length > 0){
				$("#customerGender").val(customerGender);
			}
			
			var maritalStatus = '${enterprise.maritalStatus}';
			$("#maritalStatus").val(maritalStatus);
			
			var educationalStatus = '${enterprise.educationalStatus}';
			$("#educationalStatus").val(educationalStatus);
			
			var housingConditions = '${enterprise.housingConditions}';
			$("#housingConditions").val(housingConditions);

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
