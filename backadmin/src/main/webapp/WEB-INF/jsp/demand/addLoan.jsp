<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />

<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>添加活期宝资产</title>
<style type="text/css">
.ui-datepicker-title {
	text-transform: uppercase;
	font-family: 'LatoBold';
	color: #1CAF9A;
}

.ui-datepicker {
	background: #1D2939;
	margin-top: 1px;
	border-radius: 2px;
	width: 280px;
}

.ui-datepicker-next {
	background: url(../../images/calendar-arrow.png) no-repeat 10px 5px;
}

.ui-datepicker-prev {
	background: url(../../images/calendar-arrow.png) no-repeat 8px -80px;
}

.ui-datepicker table {
	color: #ffffff;
}
</style>
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
					<i class="fa fa-pencil"></i> 添加活期宝资产
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form1" name="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">添加活期宝资产</h4>
						</div>
						<div class="panel-body">
							<!-- <div class="form-group">
								<label class="col-sm-3 control-label">项目名称 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="loanName" width="10px"
										class="form-control" />
								</div>
							</div> -->
							<div class="form-group">
								<label class="col-sm-3 control-label">借款金额 <span
									class="asterisk">*</span></label>
								<div class="col-sm-3">
									<input type="text" name="totalMoney" id="totalMoney"
										width="10px" class="form-control" />
									<div id="errortotalMoney" style="display: none;">借款金额不能为空</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">计算方式 <!-- <span
									class="asterisk">*</span> --></label> <select class="select2"
									name="operationType" onchange="change()" id="operationType"
									data-placeholder="选择计算方式...">
									<option value="天">天</option>
									<option value="月">月</option>
								</select>
							</div>
							<div class="form-group" id="month" style="display: none;">
								<label class="col-sm-3 control-label">借款期限 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" id="month" name="month" width="10px"
										class="form-control" placeholder="月" />
								</div>
							</div>
							<div class="form-group" id="day">
								<label class="col-sm-3 control-label">借款期限<!--  <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" id="day" name="day" width="10px"
										class="form-control" placeholder="天" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">项目类型 <!-- <span
									class="asterisk">*</span> --></label> <select class="select2"
									name="loanType" id="loanType" data-placeholder="选择项目类型...">
									<option value="车押宝">车押宝</option>
									<option value="房押宝">房押宝</option>
									<option value="供应宝">供应宝</option>
									<!-- 	<option value="金农宝">金农宝</option> -->
								</select>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">项目状态 <!-- <span
									class="asterisk">*</span> --></label> <select class="select2"
									name="loanStatus" id="loanStatus" data-placeholder="选择项目状态...">
									<option value="repay">还款中</option>
									<option value="finish">已完成</option>
								</select>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">还款方式 <!-- <span
									class="asterisk">*</span> --></label>
								</label>
								<div class="col-sm-3">
									<select name="repayType" id="repayType" >
										<option value="先息后本">先息后本</option>
										<option value="等额本息">等额本息</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">项目地点 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="loanAddr" id="loanAddr" width="10px"
										class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">开始时间<!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-1">
									<input type="text" name="sstartTime" id="startTime"
										class="ui_timepicker" onchange="getEndTime()" />
								</div>
							</div>
					<div id="div_show_personInfo">
							<div class="form-group">
								<label class="col-sm-3 control-label">借款人 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="borrower" width="10px"
										class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">身份证号 <!-- <span
									class="asterisk">*</span> --></label>
								<div class="col-sm-3">
									<input type="text" name="idCard" id="idCard" width="10px"
										class="form-control" />
									<div id="erroridCard" style="display: none;">格式不正确</div>
								</div>
							</div>
					</div>
							<div id="div_show_cheyabao">
								<div class="form-group">
									<label class="col-sm-3 control-label">品牌和型号 <!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-3">
										<input type="text" name="brand" width="10px"
											class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">车牌号 <!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-3">
										<input type="text" name="licensePlateNumber" width="10px"
											class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">初次登记日期<!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-1">
										<input type="text" name="sbuyTime" id="buyTime"
											class="ui_timepicker" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">公里数 <!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-3">
										<input type="text" name="kilometreAmount" width="10px"
											class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">评估价格 <!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-3">
										<input type="text" name="assessPrice" width="10px"
											class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">抵押方式 <!-- <span
									class="asterisk">*</span> --></label> <select class="select2"
										name="guaranteeType" id="guaranteeType"
										data-placeholder="抵押方式...">
										<option value="抵押">抵押</option>
										<option value="质押">质押</option>
									</select>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">抵/质押率(%) <!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-3">
										<input type="text" name="guaranteeRate" width="10px"
											class="form-control" />
									</div>
								</div>	
							</div>
							<div id="div_show_fangyabao">
								<div class="form-group">
									<label class="col-sm-3 control-label">楼房性质 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<select name="buildingProperty" id="buildingProperty">
											<option value="住宅">住宅</option>
											<option value="商铺">商铺</option>
											<option value="写字楼">写字楼</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">建筑面积 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<input type="text" name="buildingArea" id="buildingArea"
											width="10px" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">评估价格 <!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-3">
										<input type="text" name="assessPrice" width="10px"
											class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">抵/质押率(%) <!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-3">
										<input type="text" name="guaranteeRate" width="10px"
											class="form-control" />
									</div>
								</div>							
							</div>
							<div id="div_show_gongyingbao">
								<div class="form-group">
									<label class="col-sm-3 control-label">借款公司 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<input type="text" name="borrowingCompany"
											id="borrowingCompany" width="10px" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">营业执照号 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<input type="text" name="businessLicenseNumber"
											id="businessLicenseNumber" width="10px" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">经营年限<!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-3">
										<input type="text" name="operationYear" width="10px"
											class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">主营产品或服务 <!-- <span
									class="asterisk">*</span> --></label>
									<div class="col-sm-3">
										<input type="text" name="operationProduction" width="10px"
											class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">注册资本 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<input type="text" name="registeredCapital"
											id="registeredCapital" width="10px" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">实收资本 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<input type="text" name="realIncomeCapital"
											id="realIncomeCapital" width="10px" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">员工人数 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<input type="text" name="staffNumber"
											id="staffNumber" width="10px" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">全国法院被执行人信息 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<input type="text" name="nationalCourtsReport"
											id="nationalCourtsReport" width="10px" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">失信被执行人名单查询 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<input type="text" name="dishonstPersonReport"
											id="dishonstPersonReport" width="10px" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">公开渠道查询负面信息 <!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<input type="text" name="publicChannelNegativeInfo"
											id="publicChannelNegativeInfo" width="10px" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">其他负面信息<!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<textarea  name="otherNegativeInfo"
										id="otherNegativeInfo" ></textarea>
									</div>
								</div>								
							</div>
							<div class="form-group">
									<label class="col-sm-3 control-label">其他说明<!-- <span class="asterisk">*</span> -->
									</label>
									<div class="col-sm-3">
										<textarea  name="otherInfo"
										id="otherInfo" ></textarea>
									</div>
								</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-sm-9 col-sm-offset-3">
										<button class="btn btn-primary" type="button" onclick="sub()">保存</button>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" onclick="reset()"
											class="btn btn-default">重置</button>
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
		<!-- mainpanel -->
	</section>
	<script type="text/javascript">
		jQuery(document).ready(function() {
		/* 	$(".ui_timepicker")
			.datepicker();	 */
			 $(".ui_timepicker").datetimepicker({
				//showOn: "button",
				//buttonImage: "./css/images/icon_calendar.gif",
				//buttonImageOnly: true,
				showSecond : true,
				//minDate: new Date(),
				timeFormat : 'hh:mm:ss',
				stepHour : 1,
				stepMinute : 1,
				stepSecond : 1
			}); 
		});
		function change() {
			var operationType = $("#operationType").val();
			if (operationType == "月") {
				$("#month").attr("style", "display:block;");
				$("#day").attr("style", "display:none;");
			} else {
				$("#month").attr("style", "display:none;");
				$("#day").attr("style", "display:block;");
			}
		}
		function sub() {
	
			$('#errorfinishTime').attr("style",
					"display:none;font-size:14px;color:red");
			$('#errortotalMoney').attr("style",
					"display:none;font-size:14px;color:red");
			$('#erroridCard').attr("style",
					"display:none;font-size:14px;color:red");
			var totalMoney = $("#totalMoney").val();
			if (totalMoney == null || totalMoney == '') {
				$('#errortotalMoney').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}
			var img = /^[0-9]*[1-9][0-9]*$/;
			if (!img.test(totalMoney) || isNaN(totalMoney) || totalMoney <= 0) {
				$('#errortotalMoney').text("金额不正确");
				$('#errortotalMoney').attr("style",
						"display:block;font-size:14px;color:red");
				return false;
			}
			if ($("#startTime").val() != '' && $("#finishTime").val() != '') {
				if ($("#startTime").val() > $("#finishTime").val()) {
					$('#errorfinishTime').attr("style",
							"display:block;font-size:14px;color:red");
					return false;
				} else {
					$('#errorfinishTime').attr("style",
							"display:none;font-size:14px;color:red");
				}
			}
			if ($("#idCard").val() != '') {
				var length = $("#idCard").val().length;
				if (length != 15 && length != 18) {
					$('#erroridCard').attr("style",
							"display:block;font-size:14px;color:red");
					return false;
				} else {
					$('#erroridCard').attr("style",
							"display:none;font-size:14px;color:red");
				}
			}

		$.ajax({
				type : "POST",
				url : '/demand/addLoan',
				data : $('#form1').serialize(),// 你的formid
				async : false,
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'ok') {
						alert('添加成功');
						window.location = "/demand/loanList";
					} else {
						alert('添加失败');
					}
				}
			}); 

		}

		$(function() {
			var selType = $("#loanType").val()
			div_show(selType);

			$("#loanType").change(function() {
				var type = $("#loanType").val()
				div_show(type);
			});
		});

		function div_show(type) {
			if ('车押宝' == type) {
				$("#div_show_cheyabao").show();
				$("#div_show_fangyabao").hide();
				$("#div_show_gongyingbao").hide();
				clear_div('div_show_fangyabao','div_show_gongyingbao', 'div_show_cheyabao');
			} else if ('房押宝' == type) {
				$("#div_show_fangyabao").show();
				$("#div_show_cheyabao").hide();
				$("#div_show_gongyingbao").hide();
				clear_div('div_show_cheyabao','div_show_gongyingbao', 'div_show_fangyabao');
			} else if ('供应宝' == type) {
				$("#div_show_gongyingbao").show();
				//借款人和身份证在供应宝的时候隐藏并且清空文本框里面的内容
				$("#div_show_personInfo").hide();
				$("#div_show_personInfo input").attr('value', '');
				$("#div_show_personInfo input").attr('disabled', true);
				$("#div_show_cheyabao").hide();
				$("#div_show_fangyabao").hide();
				clear_div('div_show_cheyabao','div_show_fangyabao', 'div_show_gongyingbao');
			} else {//默认显示车押宝
				$("#div_show_cheyabao").show();
				$("#div_show_fangyabao").hide();
				$("#div_show_gongyingbao").hide();
				clear_div('div_show_fangyabao','div_show_gongyingbao', 'div_show_cheyabao');
			}
		}

		function clear_div(hideId,hideId2,showId) {
			//清空隐藏域内容，属性置为失效
			$("#" + hideId + " input").attr('value', '');
			$("#" + hideId + " select").attr('disabled', true);
			$("#" + hideId + " input").attr('disabled', true);
			
			$("#" + hideId2 + " input").attr('value', '');
			$("#" + hideId2 + " select").attr('disabled', true);
			$("#" + hideId2 + " input").attr('disabled', true);
			//恢复显示域，属性置为可用
			$("#" + showId + " select").attr('disabled', false);
			$("#" + showId + " input").attr('disabled', false);
		}
	</script>

</body>
</html>
