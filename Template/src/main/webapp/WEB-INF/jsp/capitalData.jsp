<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理业务系统</title>
<link type="text/css" rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="/css/jquery.ui.slider.css" />
<link rel="stylesheet" href="/css/jquery-ui-timepicker-addon.css" />
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script type="text/javascript" src="/js/jquery.ui.slider.js"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="/js/menu.js"></script>
<style type="text/css">
	.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }  
.ui-timepicker-div dl { text-align: left; }  
.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }  
.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }  
.ui-timepicker-div td { font-size: 90%; }  
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }  
</style>


</head>
<body>
	<!--<div class="top"></div>
-->
	<script src="/js/hcharts/highcharts.js"></script>
	<script src="/js/hcharts/exporting.js"></script>
	<script type="text/javascript">
		

	$(function(){
		$("#today").click();
		});
	
	
		//查询方法
		function queryData(id) {
			/* alert(id); */
			$.ajax({
				type : "post",
				url : "/userData/showTUserData",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(data) {

				},
				error : function() {

					console.info("调取失败");
				}
			});
		}

		$(function() {

			$(".but-search").click(function() {
				var id = $(this).attr("id");
				queryData(id);
			});
			/* todayStart */
			$("#today")
					.click(
							function() {

								var arr = new Array();
								var data1 = new Array();
								var colors = Highcharts.getOptions().colors;
								var categories;
								name = 'Browser brands'
								$
										.ajax({
											url : '/capitalData/byType',
											data : {
												"type" : 0
											},
											success : function(data) {
												for (var i = 0; i < data.length; i++) {
													arr[i] = data[i].argtype;
													data1[i] = {
														y : eval(data[i].count),
														color : colors[i]
													};

												}
												/*todayStart  */

												categories = arr;

												function setChart(name,
														categories, data1,
														color) {
													chart.xAxis[0]
															.setCategories(
																	categories,
																	false);
													chart.series[0]
															.remove(false);
													chart.addSeries({
														name : name,
														data : data1,
														color : color
																|| 'white'
													}, false);
													chart.redraw();
												}

												var chart = $('#container')
														.highcharts(
																{
																	chart : {
																		type : 'column'
																	},
																	title : {
																		text : '借款项目数据'
																	},
																	subtitle : {
																		text : 'duanrong.com'
																	},
																	xAxis : {
																		categories : categories
																	},
																	yAxis : {
																		title : {
																			text : '数量（个）'
																		}
																	},
																	plotOptions : {
																		column : {
																			cursor : 'pointer',
																			point : {
																				events : {
																					click : function() {
																						var drilldown = this.drilldown;
																						if (drilldown) { // drill down
																							setChart(
																									drilldown.name,
																									drilldown.categories,
																									drilldown.data,
																									drilldown.color);
																						} else { // restore
																							setChart(
																									name,
																									categories,
																									data);
																						}
																					}
																				}
																			},
																			dataLabels : {
																				enabled : true,
																				color : colors[2],
																				style : {
																					fontWeight : 'bold'
																				},
																				formatter : function() {
																					return this.y;
																				}
																			}
																		}
																	},
																	tooltip : {
																		formatter : function() {
																			var point = this.point, s = this.x
																					+ ':<b>'
																					+ this.y
																					+ '个</b><br/>';
																			if (point.drilldown) {
																				s += 'Click to view '
																						+ point.category
																						+ ' versions';
																			} else {
																				s += '';
																			}
																			return s;
																		}
																	},
																	series : [ {
																		name : name,
																		data : data1,
																		color : 'white'
																	} ],
																	exporting : {
																		enabled : false
																	}
																}).highcharts(); // return chart
												/*todayEnd */

											},
											error : function() {
												console.info("调用失败");
											}

										});

							});
			/* todayEnd */

		});
	</script>
	<jsp:include page="base/header.jsp" />
	<div id="content">
		<jsp:include page="base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>借款项目数据查询</li>
				</ul>
			</div>
			<div class="main">
				<div class="bound">
					<div class="box">
						<div class="block-box">
							<div class="input-list" style="position: relative;">
								开始时间<input type="text" class="time-begin datepicker" id="beginTimer">
							</div>
							<div class="input-list small-list" style="position: relative;">
								结束时间<input type="text" class="time-over datepicker" id="endTimer">
								<div id="calendar" style="display: none;"></div>
							</div>
							<div class="input-list small-list">
								<input type="button" id="query" value="查询"
									class="but-search small-but">
							</div>
							<div class="input-list small-list-right">
								<input type="button" id="today" value="今日"
									class="but-search small-but">
							</div>
							<div class="input-list small-list-right">
								<input type="button" id="week" value="最近一周"
									class="but-search small-but">
							</div>
							<div class="input-list small-list-right">
								<input type="button" id="month" value="最近一月"
									class="but-search small-but">
							</div>
						</div>
						<script type="text/javascript">
                        	$(".datepicker").datepicker({
                        		
                        		dateFormat:'yy-mm-dd'
                        	});
								
							</script>
						<div class="block-box1">
							<form name="form1" method="post" action="">
								<p>
									<label> <input type="radio" name="RadioGroup1"
										value="柱状图" checked="checked" id="RadioGroup1_0"> 柱状图
									</label> 
								</p>
							</form>
						</div>
					</div>
					<div class="box">
						<div id="container" style="min-width: 800px; height: 400px"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		navList(12);
	</script>
	<script type="text/javascript">
		var argtype = new Array();
		var timer = new Array();
		var count = new Array();
		var data2 = new Array();
		var timerArr = new Array();
		var typeCount = 0;
		var contentByTimer = new Array();
		var contentByTimernew = new Array();
		var s = 0;
		
		$(function() {
			
			/*折线图Start  */
			$("#RadioGroup1_1").click(
					function() {

						$('#container').highcharts(
								{
									title : {
										text : 'Monthly Average Temperature',
										x : -20
									//center
									},
									subtitle : {
										text : 'Source: WorldClimate.com',
										x : -20
									},
									xAxis : {
										categories : [ 'Jan', 'Feb', 'Mar',
												'Apr', 'May', 'Jun', 'Jul',
												'Aug', 'Sep', 'Oct', 'Nov',
												'Dec' ]
									},
									yAxis : {
										title : {
											text : 'Temperature (°C)'
										},
										plotLines : [ {
											value : 0,
											width : 1,
											color : '#808080'
										} ]
									},
									tooltip : {
										valueSuffix : '°C'
									},
									legend : {
										layout : 'vertical',
										align : 'right',
										verticalAlign : 'middle',
										borderWidth : 0
									},
									series : [
											{
												name : 'Tokyo',
												data : [ 7.0, 6.9, 9.5, 14.5,
														18.2, 21.5, 25.2, 26.5,
														23.3, 18.3, 13.9, 9.6 ]
											},
											{
												name : 'New York',
												data : [ -0.2, 0.8, 5.7, 11.3,
														17.0, 22.0, 24.8, 24.1,
														20.1, 14.1, 8.6, 2.5 ]
											},
											{
												name : 'Berlin',
												data : [ -0.9, 0.6, 3.5, 8.4,
														13.5, 17.0, 18.6, 17.9,
														14.3, 9.0, 3.9, 1.0 ]
											},
											{
												name : 'London',
												data : [ 3.9, 4.2, 5.7, 8.5,
														11.9, 15.2, 17.0, 16.6,
														14.2, 10.3, 6.6, 4.8 ]
											} ]
								});
					});

			/*折线图end  */

			/*条形图start  */
			$("#RadioGroup1_0").click(function() {
				pub();
			});

			/*条形图end  */
			$("#week").click(function() {
				
				var qydk = new Array();
				var fd = new Array();
				var cd = new Array();
				var qydk1 = new Array();
				var fd = new Array();
				var cd = new Array();
				var gyb = new Array();
				var jnb = new Array();
				var fd1 = new Array();
				var cd1 = new Array();
				var fff = new Array();
				var mingcheng = new Array();
				$.ajax({
					url : '/capitalData/monOrWeek',
					data : {
						"type" : 1
					},
					success : function(data) {
						  
						for ( var key in data) {
							timerArr.push(key);
							typeCount = data[key].length;

						}

						for ( var key in data) {
							data[key];
							console.info(data[key]);
							contentByTimer.push(data[key]);
							fff.push(data[key]);
							/* if (data[key].argtype == "企业贷") {

								qydk.push(data[key].count);
							}
							if (data[key].argtype == "房贷") {
								fd.push(data[key].count);
							}
							if (data[key].argtype == "车贷") {
								cd.push(data[key].count);
							}
							if (data[key].argtype == "金农宝") {
								jnb.push(data[key].count);
							}
							if (data[key].argtype == "供应宝") {
								gyb.push(data[key].count);
							} */
							if (data[key].argtype == "车贷") {

								cd.push(data[key].count);
							}
							if (data[key].argtype == "房贷") {
								fd.push(data[key].count);
							}
							if (data[key].argtype == "企业贷") {
								qydk.push(data[key].count);
							}
							if (data[key].argtype == "供应宝") {
								gyb.push(data[key].count);
							}
							if (data[key].argtype == "金农宝") {
								jnb.push(data[key].count);
							} 
							fd = new Array();

						}
						for (var f = 0; f < typeCount; f++) {
							for (var d = 0; d < contentByTimer.length; d++) {
								var shuliang = new Array();

								shuliang.push(contentByTimer.count);

							}

						}
						for (var i = 0; i < fff.length; i++) {

							cd.push(eval(fff[i][0].count));
							fd.push(eval(fff[i][1].count));
							qydk.push(eval(fff[i][2].count));
							gyb.push(eval(fff[i][3].count));
							jnb.push(eval(fff[i][4].count));
						}
						console.info(qydk + "=========" + cd + "=====" + fd);
						
						
						console.info(qydk1 + "    ad   " + fd);
						data2[0] = {
							name : "车贷",
							data : cd
						};
						data2[1] = {
							name : "房贷",
							data : fd
						};
						data2[2] = {
							name : "企业贷",
							data : qydk
						}
						data2[3] = {
								name : "供应宝",
								data : gyb
							}
						data2[4] = {
								name : "金农宝",
								data : jnb
							};

						pub(argtype, timer, count);
					},
					error : function() {
						console.info("周调去失败");
					}

				});
				pub();
			});
			
			$("#query").click(function(){

				
				var qydk = new Array();
				var fd = new Array();
				var cd = new Array();
				var qydk1 = new Array();
				var fd = new Array();
				var cd = new Array();
				var jnb = new Array();
				var gyb = new Array();
				var fd1 = new Array();
				var cd1 = new Array();
				var fff = new Array();
				var mingcheng = new Array();
				var beginTimer=$("#beginTimer").val();
				var endTimer=$("#endTimer").val();
				$.ajax({
					url : '/capitalData/monOrWeek',
					data : {
						"type" : 3,
						"beginTimer":beginTimer,
						"endTimer":endTimer
					},
					success : function(data) {
						  
						for ( var key in data) {
							timerArr.push(key);
							typeCount = data[key].length;

						}

						for ( var key in data) {
							data[key];
							contentByTimer.push(data[key]);
							fff.push(data[key]);
							if (data[key].argtype == "车贷") {

								cd.push(data[key].count);
							}
							if (data[key].argtype == "房贷") {
								fd.push(data[key].count);
							}
							if (data[key].argtype == "企业贷") {
								qydk.push(data[key].count);
							}
							if (data[key].argtype == "供应宝") {
								gyb.push(data[key].count);
							}
							if (data[key].argtype == "金农宝") {
								jnb.push(data[key].count);
							} 
							fd = new Array();

						}
						for (var f = 0; f < typeCount; f++) {
							for (var d = 0; d < contentByTimer.length; d++) {
								var shuliang = new Array();

								shuliang.push(contentByTimer.count);

							}

						}
						for (var i = 0; i < fff.length; i++) {

							
							cd.push(eval(fff[i][0].count));
							fd.push(eval(fff[i][1].count));
							qydk.push(eval(fff[i][2].count));
							gyb.push(eval(fff[i][3].count));
							jnb.push(eval(fff[i][4].count));

						}
						console.info(qydk + "=========" + cd + "=====" + fd);
						
						
						console.info(qydk1 + "    ad   " + fd);
						data2[0] = {
								name : "车贷",
								data : cd
							};
							data2[1] = {
								name : "房贷",
								data : fd
							};
							data2[2] = {
								name : "企业贷",
								data : qydk
							}
							data2[3] = {
									name : "供应宝",
									data : gyb
								}
							data2[4] = {
									name : "金农宝",
									data : jnb
								};
						pub(argtype, timer, count);
					},
					error : function() {
						console.info("周调去失败");
					}

				});
				pub();
				
			});
			$("#month").click(function(){
				
				var qydk = new Array();
				var fd = new Array();
				var cd = new Array();
				var qydk1 = new Array();
				var fd = new Array();
				var cd = new Array();
				var jnb = new Array();
				var gyb= new Array();
				var fd1 = new Array();
				var cd1 = new Array();
				var fff = new Array();
				var mingcheng = new Array();
				$.ajax({
					url : '/capitalData/monOrWeek',
					data : {
						"type" : 2
					},
					success : function(data) {
						  
						for ( var key in data) {
							timerArr.push(key);
							typeCount = data[key].length;

						}

						for ( var key in data) {
							data[key];
							contentByTimer.push(data[key]);
							fff.push(data[key]);
							if (data[key].argtype == "车贷") {

								cd.push(data[key].count);
							}
							if (data[key].argtype == "房贷") {
								fd.push(data[key].count);
							}
							if (data[key].argtype == "企业贷") {
								qydk.push(data[key].count);
							}
							if (data[key].argtype == "供应宝") {
								gyb.push(data[key].count);
							}
							if (data[key].argtype == "金农宝") {
								jnb.push(data[key].count);
							} 
							fd = new Array();

						}
						for (var f = 0; f < typeCount; f++) {
							for (var d = 0; d < contentByTimer.length; d++) {
								var shuliang = new Array();

								shuliang.push(contentByTimer.count);

							}

						}
						for (var i = 0; i < fff.length; i++) {

							
							cd.push(eval(fff[i][0].count));
							fd.push(eval(fff[i][1].count));
							qydk.push(eval(fff[i][2].count));
							gyb.push(eval(fff[i][3].count));
							jnb.push(eval(fff[i][4].count));

						}
						console.info(qydk + "=========" + cd + "=====" + fd);
						
						
						console.info(qydk1 + "    ad   " + fd);
						data2[0] = {
								name : "车贷",
								data : cd
							};
							data2[1] = {
								name : "房贷",
								data : fd
							};
							data2[2] = {
								name : "企业贷",
								data : qydk
							}
							data2[3] = {
									name : "供应宝",
									data : gyb
								}
							data2[4] = {
									name : "金农宝",
									data : jnb
								};


						pub(argtype, timer, count);
					},
					error : function() {
						console.info("周调去失败");
					}

				});
				pub();
			});

		});
		/*公共方法  */
		function pub(argtype, timer, count) {

			$('#container')
					.highcharts(
							{
								chart : {
									type : 'column'
								},
								title : {
									text : '借款项目数据'
								},
								subtitle : {
									text : 'duanrong.com'
								},
								xAxis : {
									categories : timerArr
								},
								yAxis : {
									min : 0,
									title : {
										text : '数量（个）'
									}
								},
								tooltip : {
									headerFormat : '<span style="font-size:30px">{point.key}</span><table>',
									pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
											+ '<td style="padding:0"><b>{point.y:.1f}个 </b></td></tr>',
									footerFormat : '</table>',
									shared : true,
									useHTML : true
								},
								 plotOptions: {
							            series: {
							                dataLabels: {
							                    enabled: true
							                }
							            }
							        },
								series : data2
							});
		}

		document.getElementById("RadioGroup1_1").onclick;
	</script>

	<script type="text/javascript">
	$(function(){
		$('#today').click();
	});
   

	</script>
</body>
</html>
