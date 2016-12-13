﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<style type="text/css">
.cos {
	width: 20% !important;
}

.del {
	cursor: pointer;
}

.edit {
	cursor: pointer;
}
</style>
<title>借款项目附加信息</title>
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
					<i class="fa fa-pencil"></i> 借款项目附加信息
				</h2>
			</div>
			<div class="contentpanel">

				<!-- 创建借款表单 -->
				<form id="form1" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">借款项目附加信息--车贷信息</h4>
						</div>
						<div class="panel-body">
							<input type="hidden" name="loanId" id="loanId" value="${loan.id}" />
							<input type="hidden" name="loanType" id="loanType"
								value="${loan.loanType}" />
							<c:if test="${vehicles !=  null}">
								<c:set var="v" value="${vehicles[0]}" />
							</c:if>
							<c:choose>
								<c:when test="${loan.companyno == '1'}">
									<c:forEach var="vehicle" items="${vehicles}" varStatus="status">
										<div name="vehicle2">
											<div style="color:blue; font-size:14px;">质押车${status.index + 1}</div>
											<div class="form-group">
												<label class="col-sm-3 control-label cos"
													style="color: red;">品牌和型号 </label>
												<div class="col-sm-3 cos">
													<input type="text" name="brand" width="10px"
														id="brand${vehicle.id}" class="form-control verify"
														value="${vehicle.brand }" />
												</div>
												<label class="col-sm-3 control-label cos"
													<c:if test="${vehicle.money > 0}">style="color: red;"</c:if>>车牌号
													<span class="asterisk">*</span>
												</label>
												<div class="col-sm-3 cos">
													<input type="text" name="licensePlateNumber"
														id="licensePlateNumber${vehicle.id}"
														value="${vehicle.licensePlateNumber }" width="10px"
														class="form-control <c:if test="${vehicle.money > 0}">verify</c:if>" />
												</div>
											</div>

											<div class="form-group">
												<label class="cos col-sm-3 control-label"
													<c:if test="${vehicle.money > 0}">style="color: red;"</c:if>>行驶公里数</label>
												<div class="cos col-sm-3">
													<input type="text" name="kilometreAmount" width="10px"
														id="kilometreAmount${vehicle.id}"
														class="form-control <c:if test="${vehicle.money > 0}">verify</c:if>"
														value="${vehicle.kilometreAmount }" />
												</div>

												<label class="cos col-sm-3 control-label"
													<c:if test="${vehicle.money > 0}">style="color: red;"</c:if>>子标金额</label>
												<div class="cos col-sm-3">
													<input type="text" name="money" width="10px"
														id="money${vehicle.id}"
														class="form-control <c:if test="${vehicle.money > 0}">verify</c:if>"
														value="${vehicle.money }" />
												</div>
											</div>
											<div class="form-group">
												<label class="cos col-sm-3 control-label"
													<c:if test="${vehicle.money > 0}">style="color: red;"</c:if>>评估价格
													<span class="asterisk">*</span>
												</label>
												<div class="cos col-sm-3">
													<input type="text" name="assessPrice" width="10px"
														id="assessPrice${vehicle.id}"
														class="form-control <c:if test="${vehicle.money > 0}">verify</c:if>"
														value="${vehicle.assessPrice }" />
												</div>
												<c:if test="${vehicle.money > 0}">
												<label class="cos col-sm-3 control-label">借款人身份证</label>
												<div class="cos col-sm-3">
													<input type="text" name="borrowerIdCard" width="10px"
														class="form-control" value="${vehicle.borrowerIdCard }" />
												</div>
												</c:if>
											</div>
											<c:if test="${vehicle.money > 0}">
												<div class="form-group">
													<label class="cos col-sm-3 control-label">借款人</label>
													<div class="cos col-sm-3">
														<input type="text" name="borrowerName" width="10px"
															class="form-control" value="${vehicle.borrowerName }" />
													</div>

													<label class="col-sm-3 control-label cos">项目地点</label>
													<div class="cos col-sm-3">
														<input type="text" name="itemAddress" class="form-control"
															width="10px" value="${vehicle.itemAddress }" />
													</div>
												</div>
												<div class="form-group">
													<label class="cos col-sm-3 control-label">新增展期</label>
													<div class="cos col-sm-3">
														<select name="remark">
															<option value="">请选择</option>
															<option value="新增"
																<c:if test="${v.remark == '新增'}">selected</c:if>>新增</option>
															<option value="展期"
																<c:if test="${v.remark == '展期'}">selected</c:if>>展期</option>
														</select>
													</div>
													<label class="col-sm-3 control-label cos">押车GPS </label>
													<div class="cos col-sm-3">
														<select name="yaCarAndGPS">
															<option value="">请选择</option>
															<option value="GPS"
																<c:if test="${v.yaCarAndGPS == 'GPS'}">selected</c:if>>GPS</option>
															<option value="全款质押"
																<c:if test="${v.yaCarAndGPS == '全款质押'}">selected</c:if>>全款质押</option>
															<option value="分期质押"
																<c:if test="${v.yaCarAndGPS == '分期质押'}">selected</c:if>>分期质押</option>
															<option value="商品车质押"
																<c:if test="${v.yaCarAndGPS == '商品车质押'}">selected</c:if>>商品车质押</option>
														</select>
													</div>
												</div>
											</c:if>
											<div class="form-group">
												<label class="cos col-sm-3 control-label">登记日期</label>
												<div class="cos col-sm-3">
													<input type="text" id="registrationDate${vehicle.id}"
													name="registrationDate" class="ui_timepicker form-control"
													width="10px"
													value="<fmt:formatDate value="${vehicle.registrationDate}" type="date"/>" />
												</div>
												<label class="cos col-sm-3 control-label">排量 </label>
												<div class="cos col-sm-3">
													<input type="text" name="displacement"
													 width="10px" id="displacement${vehicle.id}"
													class="form-control" value="${vehicle.displacement }" />
												</div>
											</div>
                                         <div class="form-group">
												<label class="cos col-sm-3 control-label">变速器</label>
												<div class="cos col-sm-3">
													<select name="transmission" id="transmission${vehicle.id}">
                                                    <option value="">请选择</option>
													<option value="手动挡"
														<c:if test="${vehicle.transmission == '手动挡'}">selected</c:if>>手动挡</option>
													<option value="自动挡"
														<c:if test="${vehicle.transmission == '自动挡'}">selected</c:if>>自动挡</option>
													<option value="手自一体"
														<c:if test="${vehicle.transmission == '手自一体'}">selected</c:if>>手自一体</option>														
									           </select>
												</div>
												<label class="cos col-sm-3 control-label">车况评估</label>
												<div class="cos col-sm-3">
													<select name="conditionAssessment" id="conditionAssessment${vehicle.id}">
                                                    <option value="">请选择</option>
													<option value="优"
														<c:if test="${vehicle.conditionAssessment == '优'}">selected</c:if>>优</option>
													<option value="良好"
														<c:if test="${vehicle.conditionAssessment == '良好'}">selected</c:if>>良好</option>
													<option value="一般"
														<c:if test="${vehicle.conditionAssessment == '一般'}">selected</c:if>>一般</option>
													<option value="较差"
												        <c:if test="${vehicle.conditionAssessment == '较差'}">selected</c:if>>较差</option>																														
									           </select>
												</div>
											</div>	
											 <div class="form-group">
												<label class="cos col-sm-3 control-label">借款用途</label>
												<div class="cos col-sm-3">
													<textarea style="width: 315px;" type="tel" name="borrowingPurposes" id="borrowingPurposes${vehicle.id}">${vehicle.borrowingPurposes }</textarea>											
												</div>
											</div>										
											<div class="form-group">
												<label class="cos col-sm-3 control-label">其他说明 </label>
												<div class="cos col-sm-3">
													<textarea type="tel" name="otherInfo">${vehicle.otherInfo }</textarea>
												</div>
												<div class="cos col-sm-3 control-label">
													<a style="font-size:15px; color:blue;" class="edit"
														data="${vehicle.id}">质押车修改</a>&nbsp;&nbsp;&nbsp;
													<c:if
														test="${loan.status != '还款中' and loan.status != '已完成'}">
														<a style="font-size:15px; color:blue;" class="del"
															data="${vehicle.id}">删除-</a>
													</c:if>
												</div>
											</div>
										</div>

									</c:forEach>
									<c:if
										test="${loan.organizationExclusive == '是' or loan.status != '还款中' and loan.status != '已完成'}">
										<div id="addvehicle">
											<a href="javascript:add();"
												style="margin-left:180px; color:blue">添加质押物+</a>
										</div>
									</c:if>
								</c:when>
								<c:otherwise>
									<div name="vehicle">
										<input type="hidden" id="id" value="${v.id}">
										<div class="form-group">
											<label class="col-sm-3 control-label" style="color: red;">品牌和型号
												<span class="asterisk">*</span>
											</label>
											<div class="col-sm-3">
												<input type="text" name="brand" id="brand" width="10px"
													class="form-control verify" value="${v.brand}" />
											</div>
										</div>


										<div class="form-group">
											<label class="col-sm-3 control-label" style="color: red;">车牌号
												<span class="asterisk">*</span>
											</label>
											<div class="col-sm-3">
												<input type="text" name="licensePlateNumber"
													value="${v.licensePlateNumber}" id="licensePlateNumber"
													width="10px" class="form-control verify" />
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-3 control-label" style="color: red;">公里数
												<span class="asterisk">*</span>
											</label>
											<div class="col-sm-3">
												<input type="text" name="kilometreAmount"
													id="kilometreAmount" width="10px"
													class="form-control verify" value="${v.kilometreAmount}" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">购买时间 </label>
											<div class="col-sm-3">
												<input type="text" id="buyTime" name="buyTime"
													class="ui_timepicker form-control" width="10px"
													value="<fmt:formatDate value="${v.buyTime}" type="date"/>" />
											</div>
								
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">二手车市场价格 </label>
											<div class="col-sm-3">
												<input type="text" name="secondHandPrice"
													id="secondHandPrice" width="10px"
													class="form-control verify" value="${v.secondHandPrice }" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label" style="color: red;">评估价格
												<span class="asterisk">*</span>
											</label>
											<div class="col-sm-3">
												<input type="text" name="assessPrice" id="assessPrice"
													width="10px" class="form-control verify"
													value="${v.assessPrice }" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">车辆类型 </label>
											<div class="col-sm-3">
	                                         <select name="vehicleType" id="vehicleType">
                                               <option value="">请选择</option>
											   <option value="小型汽车"
											   <c:if test="${v.vehicleType == '小型汽车'}">selected</c:if>>小型汽车</option>
											    <option value="小型自动挡汽车"
												<c:if test="${v.vehicleType == '小型自动挡汽车'}">selected</c:if>>小型自动挡汽车</option>
												<option value="大型客车"
												<c:if test="${v.vehicleType == '大型客车'}">selected</c:if>>大型客车</option>
											    <option value="牵引车"
												<c:if test="${v.vehicleType == '牵引车'}">selected</c:if>>牵引车</option>	
												<option value="城市公交车"
												<c:if test="${v.vehicleType == '城市公交车'}">selected</c:if>>城市公交车</option>
											    <option value="中型客车"
												<c:if test="${v.vehicleType == '中型客车'}">selected</c:if>>中型客车</option>	
												<option value="大型货车"
												<c:if test="${v.vehicleType == '大型货车'}">selected</c:if>>大型货车</option>																																									
									           </select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">使用性质 </label>
											<div class="col-sm-3">								
                                              <select name="usingProperties" id="usingProperties">
                                                    <option value="">请选择</option>
													<option value="运营"
														<c:if test="${v.usingProperties == '运营'}">selected</c:if>>运营</option>
													<option value="非运营"
														<c:if test="${v.usingProperties == '非运营'}">selected</c:if>>非运营</option>																																								
									           </select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">出厂日期 </label>
											<div class="col-sm-3">
												<input type="text" id="manufactureDate"
													name="manufactureDate" class="ui_timepicker form-control"
													width="10px"
													value="<fmt:formatDate value="${v.manufactureDate}" type="date"/>" />
											</div>										
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">登记日期</label>
											<div class="col-sm-3">
												<input type="text" id="registrationDate"
													name="registrationDate" class="ui_timepicker form-control"
													width="10px"
													value="<fmt:formatDate value="${v.registrationDate}" type="date"/>" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">车辆识别代号 </label>
											<div class="col-sm-3">
												<input type="text" name="identificationNumber"
													id="identificationNumber" width="10px"
													class="form-control verify" value="${v.identificationNumber }" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">发动机号 </label>
											<div class="col-sm-3">
												<input type="text" name="engineNo"
													id="engineNo" width="10px"
													class="form-control verify" value="${v.engineNo }" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">变速器 </label>
											<div class="col-sm-3">
													<select name="transmission" id="transmission">
                                                    <option value="">请选择</option>
													<option value="手动挡"
														<c:if test="${v.transmission == '手动挡'}">selected</c:if>>手动挡</option>
													<option value="自动挡"
														<c:if test="${v.transmission == '自动挡'}">selected</c:if>>自动挡</option>
													<option value="手自一体"
														<c:if test="${v.transmission == '手自一体'}">selected</c:if>>手自一体</option>														
									           </select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">排量 </label>
											<div class="col-sm-3">
												<input type="text" name="displacement"
													id="displacement" width="10px"
													class="form-control verify" value="${v.displacement }" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">车况评估 </label>
											<div class="col-sm-3">
											  <select name="conditionAssessment" id="conditionAssessment">
                                                    <option value="">请选择</option>
													<option value="优"
														<c:if test="${v.conditionAssessment == '优'}">selected</c:if>>优</option>
													<option value="良好"
														<c:if test="${v.conditionAssessment == '良好'}">selected</c:if>>良好</option>
													<option value="一般"
														<c:if test="${v.conditionAssessment == '一般'}">selected</c:if>>一般</option>
													<option value="较差"
												        <c:if test="${v.conditionAssessment == '较差'}">selected</c:if>>较差</option>																														
									           </select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">燃油 </label>
											<div class="col-sm-3">
	                                      <select name="fuel" id="fuel">		
                                               <option value="">请选择</option>
											   <option value="汽油"
											   <c:if test="${v.fuel == '汽油'}">selected</c:if>>汽油</option>
											    <option value="柴油"
												<c:if test="${v.fuel == '柴油'}">selected</c:if>>柴油</option>
												<option value="混合油"
												<c:if test="${v.fuel == '混合油'}">selected</c:if>>混合油</option>
											    <option value="液化石油气"
												<c:if test="${v.fuel == '液化石油气'}">selected</c:if>>液化石油气</option>	
												<option value="天然气"
												<c:if test="${v.fuel == '天然气'}">selected</c:if>>天然气</option>
											    <option value="电"
												<c:if test="${v.fuel == '电'}">selected</c:if>>电</option>																																																
									           </select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">交强险有效期</label>
											<div class="col-sm-3">
												<input type="text" id="trafficInsuranceValidity"
													name="trafficInsuranceValidity" class="ui_timepicker form-control"
													width="10px"
													value="<fmt:formatDate value="${v.trafficInsuranceValidity}" type="date"/>" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">年检有效期</label>
											<div class="col-sm-3">
												<input type="text" id="inspectionValidity"
													name="inspectionValidity" class="ui_timepicker form-control"
													width="10px"
													value="<fmt:formatDate value="${v.inspectionValidity}" type="date"/>" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">预计违章扣分 </label>
											<div class="col-sm-3">
												<input type="text" name="lllegalDeduction" id="lllegalDeduction" width="10px"
													class="form-control verify" value="${v.lllegalDeduction }" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">预计违章罚金 </label>
											<div class="col-sm-3">
												<input type="text" name="violationFines" id="violationFines" width="10px"
													class="form-control verify" value="${v.violationFines }" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">借款用途 </label>
											<div class="col-sm-3">
											<textarea style="width: 400px;" type="tel" name="borrowingPurposes" id="borrowingPurposes">${v.borrowingPurposes }</textarea>
											</div>
										</div>
								</c:otherwise>
							</c:choose>
							<div style="text-align:center">--------------------------------------------------------------------------------------------------------------------------------------------------------------</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;"><span
									style="color: black;font-size: 18px;margin-right: 24px;">项目信息</span>担保方式(抵押方式)
									<span class="asterisk">*</span> </label>
								<div class="col-sm-3">
									<select name="guaranteeType" id="guaranteeType">
										<option value="抵押"
											<c:if test="${v.guaranteeType == '抵押'}">selected</c:if>>抵押</option>
										<option value="质押"
											<c:if test="${v.guaranteeType == '质押'}">selected</c:if>>质押</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" style="color: red;">抵/质押率
									<span class="asterisk">*</span>
								</label>
								<div class="col-sm-3">
									<input type="text" name="guaranteeRate" id="guaranteeRate"
										width="10px" class="form-control verify"
										value="${v.guaranteeRate }" />
								</div>
							</div>

							<c:if test="${status eq 'less'}">
								<div class="form-group">
									<label class="col-sm-3 control-label" style="color: red;">风控提示
										<span class="asterisk">*</span>
									</label>
									<div class="col-sm-3">
										<input type="text" name="reminderInfo" id="reminderInfo"
											width="10px" class="form-control verify"
											value="${v.reminderInfo }" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label" style="color: red;">风控措施
										<span class="asterisk">*</span>
									</label>
									<div class="col-sm-3">
										<input type="text" name="measuresInfo" id="measuresInfo"
											width="10px" class="form-control verify"
											value="${v.measuresInfo }" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label" style="color: red;">逾期处理方式
										<span class="asterisk">*</span>
									</label>
									<div class="col-sm-3">
										<textarea type="tel" name="overdueInfo" id="overdueInfo"
											class="form-control verify">${v.overdueInfo }</textarea>
									</div>
								</div>
							</c:if>

							<c:if test="${status eq 'more'}">
								<div class="form-group">
									<label class="col-sm-3 control-label">抵押权人 </label>
									<div class="col-sm-3">
										<input type="text" name="mortgagee" id="mortgagee"
											width="10px" class="form-control verify"
											value="${v.mortgagee }" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label" style="color: red;">监管方式
										<span class="asterisk">*</span>
									</label>
									<div class="col-sm-3">
										<textarea type="tel" name="supervisionMode"
											class="form-control verify" id="supervisionMode">${v.supervisionMode }</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label" style="color: red;">逾期处理方式
										<span class="asterisk">*</span>
									</label>
									<div class="col-sm-3">
										<textarea type="tel" name="overdueHandling"
											id="overdueHandling" class="form-control verify">${v.overdueHandling }</textarea>
									</div>
								</div>
							</c:if>

							<c:choose>

								<c:when
									test="${loan.totalmoney > 0 and (empty v.money  or v.money <= 0)}">
									<div class="form-group">
										<label class="col-sm-3 control-label">项目地点</label>
										<div class="col-sm-3">
											<input type="text" name="itemAddress" id="itemAddress"
												width="10px" class="form-control" value="${v.itemAddress}" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">借款人姓名</label>
										<div class="col-sm-3">
											<input type="text" name="borrowerName" id="borrowerName"
												width="10px" class="form-control" value="${v.borrowerName }" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">借款人身份证</label>
										<div class="col-sm-3">
											<input type="text" name="borrowerIdCard" id="borrowerIdCard"
												width="10px" class="form-control"
												value="${v.borrowerIdCard}" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">新增/展期 </label>
										<div class="col-sm-3">
											<select name="remark" id="remark">
												<option value="">请选择</option>
												<option value="新增"
													<c:if test="${v.remark == '新增'}">selected</c:if>>新增</option>
												<option value="展期"
													<c:if test="${v.remark == '展期'}">selected</c:if>>展期</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">押车/GPS</label>
										<div class="col-sm-3">
											<select name="yaCarAndGPS" id="yaCarAndGPS">
												<option value="">请选择</option>
												<option value="GPS"
													<c:if test="${v.yaCarAndGPS == 'GPS'}">selected</c:if>>GPS</option>
												<option value="全款质押"
													<c:if test="${v.yaCarAndGPS == '全款质押'}">selected</c:if>>全款质押</option>
												<option value="分期质押"
													<c:if test="${v.yaCarAndGPS == '分期质押'}">selected</c:if>>分期质押</option>
												<option value="商品车质押"
													<c:if test="${v.yaCarAndGPS == '商品车质押'}">selected</c:if>>商品车质押</option>
											</select>
										</div>
									</div>
									<c:if test="${loan.companyno == 0}">
										<div class="form-group">
											<label class="col-sm-3 control-label">其他说明 </label>
											<div class="col-sm-3">
												<textarea type="tel" name="otherInfo" id="otherInfo">${v.otherInfo }</textarea>
											</div>
										</div>
									</c:if>

								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							<div class="form-group">
								<label class="col-sm-3 control-label">项目说明 </label>
								<div class="col-sm-3">
									<textarea type="tel" name="otherLoanInfo" id="otherLoanInfo">${v.otherLoanInfo}</textarea>
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
	
		
	
		var html = '<div name="vehicle"><div style="color:blue; font-size:14px;">质押车#[i]</div><div class="form-group"><label class="cos col-sm-3 control-label" style="color: red;">品牌和型号<span class="asterisk">*</span></label><div class="col-sm-3 cos"><input type="text" name="brand" id="#[brand]" width="10px" class="form-control verify"/></div>'
				+ '<label class="cos col-sm-3 control-label">车牌号</label><div class="cos col-sm-3"><input type="text" name="licensePlateNumber" id="#[licensePlateNumber]" width="10px" class="form-control" /></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">行驶公里数</label><div class="cos col-sm-3"><input type="text" name="kilometreAmount" id="#[kilometreAmount]" width="10px" class="form-control"/></div>'
				+ '<label class="col-sm-3 control-label cos">子标金额 </label><div class="cos col-sm-3"><input type="text" id="#[money]" name="money" class="form-control" width="10px" readOnly="true"/></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">评估价格</label><div class="cos col-sm-3"><input type="text" name="assessPrice" id="#[assessPrice]" width="10px" class="form-control"/></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">登记日期</label><div class="cos col-sm-3"><input type="text" name="registrationDate" id="#[registrationDate]" class="ui_timepicker form-control" width="10px" type="date"/></div>'
				+ '<label class="cos col-sm-3 control-label">排量 </label><div class="cos col-sm-3"><input type="text" name="displacement" id="#[displacement]" width="10px" class="form-control" /></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">变速器</label><div class="cos col-sm-3"><select name="transmission" id="#[transmission]"><option value="">请选择</option><option value="手动挡">手动挡</option><option value="自动挡">自动挡</option><option value="手自一体">手自一体</option></select></div>'
				+ '<label class="cos col-sm-3 control-label">车况评估</label><div class="cos col-sm-3"><select name="conditionAssessment" id="#[conditionAssessment]"><option value="">请选择</option><option value="优">优</option><option value="良好">良好</option><option value="一般">一般</option><option value="较差">较差</option></select></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">借款用途</label><div class="col-sm-3 cos"><textarea style="width: 315px;" type="tel" name="borrowingPurposes" id="#[borrowingPurposes]"></textarea></div></div>'			
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">其他说明 </label><div class="col-sm-3 cos"><textarea type="tel" name="otherInfo" id="#[otherInfo]"></textarea></div>'								
				+ '<div class="cos col-sm-3 control-label"><a href="javascript:del(0)" style="font-size:15px; color:blue;" class="del" data="0" >删除-</a></div></div></div>';				
		var htmls = '<div name="vehicle"><div style="color:blue; font-size:14px;">质押车#[i]</div><div class="form-group"><label class="cos col-sm-3 control-label" style="color: red;">品牌和型号<span class="asterisk">*</span></label><div class="col-sm-3 cos"><input type="text" name="brand" id="#[brand]" width="10px" class="form-control verify"/></div>'
				+ '<label class="cos col-sm-3 control-label" style="color: red;">车牌号<span class="asterisk">*</span></label><div class="cos col-sm-3"><input type="text" name="licensePlateNumber" id="#[licensePlateNumber]" width="10px" class="form-control verify"/></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label" style="color: red;">行驶公里数<span class="asterisk">*</span></label><div class="cos col-sm-3"><input type="text" name="kilometreAmount" id="#[kilometreAmount]" width="10px" class="form-control verify"/></div>'
				+ '<label class="col-sm-3 control-label cos" style="color: red;">子标金额 </label><div class="cos col-sm-3"><input type="text" id="#[money]" name="money" class="form-control verify" width="10px"/></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label" style="color: red;">评估价格<span class="asterisk">*</span></label><div class="cos col-sm-3"><input type="text" name="assessPrice" id="#[assessPrice]" width="10px" class="form-control verify"/></div>'
				+ '<label class="cos col-sm-3 control-label">借款人身份证</label><div class="cos col-sm-3"><input type="text" id = "#[borrowerIdCard]" name="borrowerIdCard" width="10px" class="form-control"/></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">借款人</label><div class="cos col-sm-3"><input type="text" name="borrowerName" id="#[borrowerName]" width="10px" class="form-control"/></div>'
				+ '<label class="col-sm-3 control-label cos">项目地点</label><div class="cos col-sm-3"><input type="text" id="#[itemAddress]" name="itemAddress" class="ui_timepicker form-control" width="10px" /></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">新增展期</label><div class="cos col-sm-3"><select name="remark" id="#[remark]"><option value="">请选择</option><option value="新增">新增</option><option value="展期">展期</option></select></div>'
				+ '<label class="col-sm-3 control-label cos">押车GPS </label><div class="cos col-sm-3"><select name="yaCarAndGPS" id="#[yaCarAndGPS]"><option value="">请选择</option><option value="GPS">GPS</option><option value="全款质押">全款质押</option><option value="分期质押">分期质押</option><option value="商品车质押">商品车质押</option></select></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">登记日期</label><div class="cos col-sm-3"><input type="text" name="registrationDate" id="#[registrationDate]" class="ui_timepicker form-control" width="10px" type="date"/></div>'
				+ '<label class="cos col-sm-3 control-label">排量 </label><div class="cos col-sm-3"><input type="text" name="displacement" id="#[displacement]" width="10px" class="form-control" /></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">变速器</label><div class="cos col-sm-3"><select name="transmission" id="#[transmission]"><option value="">请选择</option><option value="手动挡">手动挡</option><option value="自动挡">自动挡</option><option value="手自一体">手自一体</option></select></div>'
				+ '<label class="cos col-sm-3 control-label">车况评估</label><div class="cos col-sm-3"><select name="conditionAssessment" id="#[conditionAssessment]"><option value="">请选择</option><option value="优">优</option><option value="良好">良好</option><option value="一般">一般</option><option value="较差">较差</option></select></div></div>'
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">借款用途</label><div class="col-sm-3 cos"><textarea style="width: 315px;" type="tel" name="borrowingPurposes" id="#[borrowingPurposes]"></textarea></div></div>'							
				+ '<div class="form-group"><label class="cos col-sm-3 control-label">其他说明 </label><div class="col-sm-3 cos"><textarea type="tel" name="otherInfo" id="#[otherInfo]"></textarea></div>'
				+ '<div class="cos col-sm-3 control-label"><a href="javascript:del(0)" style="font-size:15px; color:blue;" class="del" data="0" >删除-</a></div></div></div>';

		var len = $("div[name='vehicle2']").length;

		/* $(".verify").blur(function(){
			alert("---");
			verifyParam($(this).attr("id"));
		}); */
		
		$("input[name='money']").each(function() {
			if (!$(this).hasClass("verify") || '${loan.status}' == '还款中') {
				$(this).attr("readOnly", "true");
			}

		});

		function add() {
			var divH = '';

			if ('${loan.totalmoney}' > 0 && '${v.money}' <= 0) {

				divH = html;

			} else {
				divH = htmls;
			}
			len++;
			divH = divH.replace("#[i]", len);
			divH = divH.replace("#[brand]", "brand" + len);
			divH = divH.replace("#[licensePlateNumber]", "licensePlateNumber"
					+ len);
			divH = divH.replace("#[money]", "money" + len);
			divH = divH.replace("#[kilometreAmount]", "kilometreAmount" + len);
			divH = divH.replace("#[buyTime]", "buyTime" + len);
			divH = divH.replace("#[assessPrice]", "assessPrice" + len);	
			divH = divH.replace("#[registrationDate]", "registrationDate" + len);
			divH = divH.replace("#[transmission]", "transmission" + len);
			divH = divH.replace("#[displacement]", "displacement" + len);
			divH = divH.replace("#[conditionAssessment]", "conditionAssessment" + len);
			divH = divH.replace("#[borrowingPurposes]", "borrowingPurposes" + len);
			divH = divH.replace("#[otherInfo]", "otherInfo" + len);
			$("#addvehicle").before(divH);
			$(".ui_timepicker")
			.datepicker();
		}

		$(".del").live("click", function() {

			var id = $(this).attr("data");
			if (id > 0) {
				if (confirm("确认要删除吗？")) {
					$.ajax({
						type : 'POST',
						url : '${ctx}/loan/vehicle/del',
						data : {
							id : id
						},

						success : function(data) {
							if (data == "ok") {
								alert("删除成功");
							} else if (data == "no") {
								alert("质押车不存在");
							} else {
								alert("删除失败");
							}
						},
						error : function() {
							alert("异常！");
						}
					});
					
				}
			}
			$(this).parent('div').parent('div').parent('div').remove();
		});

		$(".edit").live("click", function() {
							var id = $(this).attr("data");
							var div = $(this).parent('div').parent('div').parent('div');
							var brand = div.find("input[name='brand']");

							if (brand.hasClass("verify")) {
								verifyParam(brand.attr("id"));
							}
							var licensePlateNumber = div.find(
									"input[name='licensePlateNumber']");
							if (licensePlateNumber.hasClass("verify")) {
								verifyParam(licensePlateNumber.attr("id"));
							}
							var assessPrice = div.find(
									"input[name='assessPrice']");
							if (assessPrice.hasClass("verify")) {
								verifyParam(assessPrice.attr("id"));
							}
							
							var kilometreAmount =div.find(
									"input[name='kilometreAmount']");
							if (kilometreAmount.hasClass("verify")) {
								verifyParam(kilometreAmount.attr("id"));
							}
							var money = div.find("input[name='money']");
							if (money.hasClass("verify")) {
								verifyParam(money.attr("id"));
							}
							var assessPrice = div.find(
									"input[name='assessPrice']");
							if (assessPrice.hasClass("verify")) {
								verifyParam(assessPrice.attr("id"));
							}
							var registrationDate =div.find(
							"input[name='registrationDate']");							
							var displacement =div.find(
							"input[name='displacement']");							
							var transmission =div.find(
							"select[name='transmission']");							
							var conditionAssessment =div.find(
							"select[name='conditionAssessment']");
							var borrowingPurposes = div.find(
							"textarea[name='borrowingPurposes']");							
							var otherInfo = div.find(
									"textarea[name='otherInfo']");
							var borrowerName = "";
							var itemAddress = "";
							var remark = "";
							var yaCarAndGPS = ""
							if (money.val() > 0) {
								borrowerName = div.find(
										"input[name='borrowerName']").val();
								itemAddress = div.find(
										"input[name='itemAddress']").val();
								remark = div.find(
										"select[name='remark']").val();
								yaCarAndGPS = div.find(
										"select[name='yaCarAndGPS']").val();
								borrowerIdCard = div.find(
										"input[name='borrowerIdCard']").val();
							}

							if (id > 0) {
								if (confirm("确认要修改吗？")) {
									$.ajax({
										type : 'POST',
										url : '${ctx}/loan/vehicle/edit',
										data : {
											id : id,
											brand:brand.val(),
											licensePlateNumber : licensePlateNumber.val(),
											kilometreAmount:kilometreAmount.val(),
											money:money.val(),
											otherInfo:otherInfo.val(),
											registrationDate:registrationDate.val(),
											displacement:displacement.val(),
											transmission:transmission.val(),
											conditionAssessment:conditionAssessment.val(),
											borrowingPurposes:borrowingPurposes.val(),
											borrowerName:borrowerName,
											itemAddress:itemAddress,
											remark:remark,
											yaCarAndGPS:yaCarAndGPS,
											assessPrice:assessPrice.val(),
											borrowerIdCard:borrowerIdCard
										},
										success : function(data) {
											if (data == "ok") {
												alert("保存成功");
											} else if (data == "no") {
												alert("质押车不存在");
											} else {
												alert("保存失败");
											}
										},
										error : function() {
											alert("异常！");
										}
									});
								}
							}
						});
	</script>


	<script type="text/javascript">
		var flag = true;
		var b = true;

		function compare() {
			var vehicles = $("div[name='vehicle']");
			if(vehicles.length <= 0 && $("div[name='vehicle2']").length <= 0){
				alert("没有有效的质押车信息");
				flag = false;
				return ;
			}
			var array = new Array();
			if (${loan.companyno} == 1) {
				var arr = "";
				vehicles
						.each(function() {
							flag = true;
							var brand = $(this).find("input[name='brand']");
							if (brand.hasClass("verify")) {
								verifyParam(brand.attr("id"));
							}
							var licensePlateNumber = $(this).find(
									"input[name='licensePlateNumber']");
							if (licensePlateNumber.hasClass("verify")) {
								verifyParam(licensePlateNumber.attr("id"));
							}
							var kilometreAmount = $(this).find(
									"input[name='kilometreAmount']");
							if (kilometreAmount.hasClass("verify")) {
								verifyParam(kilometreAmount.attr("id"));
							}
							var money = $(this).find("input[name='money']");
							if (money.hasClass("verify")) {
								verifyParam(money.attr("id"));
							}
							var assessPrice = $(this).find(
									"input[name='assessPrice']");
							if (assessPrice.hasClass("verify")) {
								verifyParam(assessPrice.attr("id"));
							}
							var registrationDate =$(this).find(
							"input[name='registrationDate']");							
							var displacement =$(this).find(
							"input[name='displacement']");							
							var transmission =$(this).find(
							"select[name='transmission']");							
							var conditionAssessment =$(this).find(
							"select[name='conditionAssessment']");
							var borrowingPurposes = $(this).find(
							"textarea[name='borrowingPurposes']");								
							var otherInfo = $(this).find(
									"textarea[name='otherInfo']");
							arr += "brand=" + brand.val()
									+ ",licensePlateNumber="
									+ licensePlateNumber.val()
									+ ",kilometreAmount="
									+ kilometreAmount.val() + ",money="
									+ money.val() + ",assessPrice="
									+ assessPrice.val() + ",otherInfo="
									+ otherInfo.val()+ ",registrationDate="
									+ registrationDate.val()+ ",displacement="
									+ displacement.val()+ ",transmission="
									+ transmission.val()+ ",conditionAssessment="
									+ conditionAssessment.val()+ ",borrowingPurposes="
									+ borrowingPurposes.val();
							if (money.val() > 0) {
								b = false;
								var borrowerName = $(this).find(
										"input[name='borrowerName']");
								var itemAddress = $(this).find(
										"input[name='itemAddress']");
								var remark = $(this).find(
										"select[name='remark']");
								var yaCarAndGPS = $(this).find(
										"select[name='yaCarAndGPS']");
								var borrowerIdCard = $(this).find(
										"input[name='borrowerIdCard']");
								arr += ",borrowerName=" + borrowerName.val()
										+ ",itemAddress=" + itemAddress.val()
										+ ",remark=" + remark.val()
										+ ",yaCarAndGPS=" + yaCarAndGPS.val()
										+ ",borrowerIdCard=" + borrowerIdCard.val();
							}
							arr += ";";
						});
				arr = arr.substr(0, arr.length - 1);
				array.push(arr);
			} else {
				flag = true;
				var arr = "";
				var brand = $("#brand");
				if (brand.hasClass("verify")) {
					verifyParam(brand.attr("id"));
				}
				var licensePlateNumber = $("#licensePlateNumber");
				if (licensePlateNumber.hasClass("verify")) {
					verifyParam(licensePlateNumber.attr("id"));
				}
				var kilometreAmount = $("#kilometreAmount");
				if (kilometreAmount.hasClass("verify")) {
					verifyParam(kilometreAmount.attr("id"));
				}
				var assessPrice = $("#assessPrice");
				if (assessPrice.hasClass("verify")) {
					verifyParam(assessPrice.attr("id"));
				}
				var buyTime = $("#buyTime");
				if (buyTime.hasClass("verify")) {
					verifyParam(buyTime.attr("id"));
				}
				var secondHandPrice = $("#secondHandPrice");
				if (secondHandPrice.hasClass("verify")) {
					verifyParam(secondHandPrice.attr("id"));
				}
				var vehicleType = $("#vehicleType");
				if (vehicleType.hasClass("vehicleType")) {
					verifyParam(vehicleType.attr("id"));
				}
				var usingProperties = $("#usingProperties");
				if (usingProperties.hasClass("verify")) {
					verifyParam(usingProperties.attr("id"));
				}
				var manufactureDate = $("#manufactureDate");
				if (manufactureDate.hasClass("verify")) {
					verifyParam(manufactureDate.attr("id"));
				}
				var registrationDate = $("#registrationDate");
				if (registrationDate.hasClass("verify")) {
					verifyParam(registrationDate.attr("id"));
				}
				var identificationNumber = $("#identificationNumber");
				if (identificationNumber.hasClass("vehicleType")) {
					verifyParam(identificationNumber.attr("id"));
				}
				var engineNo = $("#engineNo");
				if (engineNo.hasClass("verify")) {
					verifyParam(engineNo.attr("id"));
				}
				var transmission = $("#transmission");
				if (transmission.hasClass("verify")) {
					verifyParam(transmission.attr("id"));
				}
				var displacement = $("#displacement");
				if (displacement.hasClass("verify")) {
					verifyParam(displacement.attr("id"));
				}
				var conditionAssessment = $("#conditionAssessment");
				if (conditionAssessment.hasClass("vehicleType")) {
					verifyParam(conditionAssessment.attr("id"));
				}
				var fuel = $("#fuel");
				if (fuel.hasClass("verify")) {
					verifyParam(fuel.attr("id"));
				}
				var trafficInsuranceValidity = $("#trafficInsuranceValidity");
				if (trafficInsuranceValidity.hasClass("verify")) {
					verifyParam(trafficInsuranceValidity.attr("id"));
				}
				var inspectionValidity = $("#inspectionValidity");
				if (inspectionValidity.hasClass("verify")) {
					verifyParam(inspectionValidity.attr("id"));
				}
				var lllegalDeduction = $("#lllegalDeduction");
				if (lllegalDeduction.hasClass("verify")) {
					verifyParam(lllegalDeduction.attr("id"));
				}
				var violationFines = $("#violationFines");
				if (violationFines.hasClass("verify")) {
					verifyParam(violationFines.attr("id"));
				}
				var borrowingPurposes = $("#borrowingPurposes");
				if (borrowingPurposes.hasClass("verify")) {
					verifyParam(borrowingPurposes.attr("id"));
				}
				arr += "brand=" + brand.val() + ",licensePlateNumber="
						+ licensePlateNumber.val() + ",kilometreAmount="
						+ kilometreAmount.val() + ",money=" + 0
						+ ",assessPrice=" + assessPrice.val() + ",buyTime="
						+ buyTime.val() + ",secondHandPrice="
						+ secondHandPrice.val()+ ",vehicleType="
						+ vehicleType.val()+ ",usingProperties="
						+ usingProperties.val()+ ",manufactureDate="
						+ manufactureDate.val()+ ",registrationDate="
						+ registrationDate.val()+ ",identificationNumber="
						+ identificationNumber.val()+ ",engineNo="
						+ engineNo.val()+ ",transmission="
						+ transmission.val()+ ",displacement="
						+ displacement.val()+ ",conditionAssessment="
						+ conditionAssessment.val()+ ",fuel="
						+ fuel.val()+ ",trafficInsuranceValidity="
						+ trafficInsuranceValidity.val()+ ",inspectionValidity="
						+ inspectionValidity.val()+ ",lllegalDeduction="
						+ lllegalDeduction.val()+ ",violationFines="
						+ violationFines.val()+ ",borrowingPurposes="
						+ borrowingPurposes.val();
				array.push(arr);
			}
			var loan = "";
			var guaranteeType = $("#guaranteeType");
			if (guaranteeType.hasClass("verify")) {
				verifyParam(guaranteeType.attr("id"));
			}
			loan += "guaranteeType=" + guaranteeType.val() + ",";
			var guaranteeRate = $("#guaranteeRate");
			if (guaranteeRate.hasClass("verify")) {
				verifyParam(guaranteeRate.attr("id"));
			}
			loan += "guaranteeRate=" + guaranteeRate.val() + ",";

			var otherLoanInfo = $("#otherLoanInfo").val();

			loan += "otherLoanInfo=" + otherLoanInfo + ",";
			if ('${status}' == 'less') {
				var reminderInfo = $("#reminderInfo");
				if (reminderInfo.hasClass("verify")) {
					verifyParam(reminderInfo.attr("id"));
				}
				loan += "reminderInfo=" + reminderInfo.val() + ",";
				var measuresInfo = $("#measuresInfo");
				if (measuresInfo.hasClass("verify")) {
					verifyParam(measuresInfo.attr("id"));
				}
				loan += "measuresInfo=" + measuresInfo.val() + ",";
				var overdueInfo = $("#overdueInfo");
				if (overdueInfo.hasClass("verify")) {
					verifyParam(overdueInfo.attr("id"));
				}
				loan += "overdueInfo=" + overdueInfo.val() + ",";
			} else {
				var mortgagee = $("#mortgagee");
				if (mortgagee.hasClass("verify")) {
					verifyParam(mortgagee.attr("id"));
				}
				loan += "mortgagee=" + mortgagee.val() + ",";
				var supervisionMode = $("#supervisionMode");
				if (supervisionMode.hasClass("verify")) {
					verifyParam(supervisionMode.attr("id"));
				}
				loan += "supervisionMode=" + supervisionMode.val() + ",";
				var overdueHandling = $("#overdueHandling");
				if (overdueHandling.hasClass("verify")) {
					verifyParam(overdueHandling.attr("id"));
				}
				loan += "overdueHandling=" + overdueHandling.val() + ",";
			}
			if (b) {
			
				var borrowerName = $("#borrowerName").val();
				var itemAddress = $("#itemAddress").val();
				var remark = $("#remark").val();
				var yaCarAndGPS = $("#yaCarAndGPS").val();
				var otherInfo = $("#otherInfo").val();
				var borrowerIdCard = $("#borrowerIdCard").val();
				loan += "borrowerName=" + borrowerName + ",itemAddress="
						+ itemAddress + ",remark=" + remark + ",yaCarAndGPS="
						+ yaCarAndGPS + ",otherInfo=" + otherInfo + ",borrowerIdCard=" + borrowerIdCard;
			}
			array.push(loan);
			return array;
		}

		function sub() {
			var result = compare();
			var v = result[0];
			var l = result[1];
			
			if (flag) {
				if (window.confirm('确认操作借款项目附加信息?')) {
					$
							.ajax({
								cache : false,
								type : "POST",
								dataType : 'json',
								url : "${ctx}/loan/createLoanDetail",
								data : {
									vehicles : v,
									loan : l,
									loanId : '${loan.id}',
									loanType : "vehicle",
									id : $("#id").val()
								}, //要发送的是ajaxFrm表单中的数据
								async : false,
								beforeSend : function() {
									xval = getBusyOverlay(
											'viewport',
											{
												color : 'black',
												opacity : 0.3,
												text : '正在处理，请稍后...',
												style : 'text-shadow: 0 0 3px black;font-size:18px;'
											}, {
												color : 'blue',
												size : 25,
												type : 'o'
											});
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
										} else {
											var errs = dataObj.error;
											errs = errs.split(",");
											for (var i = 0; i < errs.length; i++) {
												var v = errs[i].trim();
												if (v.length > 0) {
													verifyParam(v);
												}
											}
										}
									} else if (dataObj.status == 'updateOk') {
										alert('附加信息编辑成功!');
										location.replace(document.referrer);
									}
								}
							});
				}
			} else {
				alert('当前借款项目附加信息有误,请检查');
			}
		}

		//显示或隐藏错误信息
		function baseError(id, message) {
			$("#" + id + "Error p").remove();
			$str = "<span style=\"font-size:14px;color:red\" id=\""+id+"Error\"><p>"
					+ message + "</p></span>";
			$("#" + id).after($str);
		}

		$(".verify").live("blur", function() {
			verifyParam($(this).attr("id"));
		});
		//校验参数
		function verifyParam(vp) {
			if (vp.indexOf('brand') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '品牌和型号不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}
			} else if (vp.indexOf('licensePlateNumber') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '车牌号不能为空');
					flag = false;
					return false;
				} /* else {
					var id = $("#" + vp).val();
					if(id == "**·*****"){
						baseError(vp, '');
						return true;
					}
					/* var carID = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[·]{1}[A-Za-z0-9]{5}$/;
					if (!carID.test(id)) {
						baseError(vp, '车牌号格式不正确');
						flag = false;
						return false;
					} else {
						baseError(vp, '');
						return true;
					}
				} */
			} else if (vp.indexOf('kilometreAmount') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '公里数不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}
			} /* else if (vp.indexOf('secondHandPrice') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '二手市场价格不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}
			} */ else if (vp.indexOf('money') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '字标金额不能为空');
					flag = false;
					return false;
				} else if($("#" + vp).val() <= 0){
					baseError(vp, '字标金额不正确');
					flag = false;
					return false;
				}else{
					baseError(vp, '');
					return true;
				}
			}else if (vp.indexOf('assessPrice') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '评估价格不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}
			} else if (vp.indexOf('guaranteeRate') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '抵/质押率不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}

			} else if (vp.indexOf('reminderInfo') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '风控提示不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}
			} else if (vp.indexOf('measuresInfo') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '风控措施不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}
			} else if (vp.indexOf('overdueInfo') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '逾期处理方式不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}
			} else if (vp.indexOf('supervisionMode') == 0) {
				if ($("#" + vp).val().length == 0) {
					baseError(vp, '监管方式不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}
			} else if (vp.indexOf('overdueHandling') == 0) {

				if ($("#" + vp).val().length == 0) {
					baseError(vp, '逾期处理方式不能为空');
					flag = false;
					return false;
				} else {
					baseError(vp, '');
					return true;
				}
			}

		}
	</script>
	<script type="text/javascript">
	$(function() {
		$(".ui_timepicker")
				.datepicker();
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