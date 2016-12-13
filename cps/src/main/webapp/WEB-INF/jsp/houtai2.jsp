<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台数据分析系统</title>
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
	<script src="/js/hcharts/highcharts.js"></script>
	<script src="/js/hcharts/exporting.js"></script>
	<script type="text/javascript">
	
	$(function(){
		$("#today").click();
		});
	
	$(function() {
		/* todayStart */
	    $("#today").click(function(){
	    	var arr= new Array();
	    	var data2=new Array();
	    	var cat=new Array();
	    	var colors = Highcharts.getOptions().colors;
	        var categories = ['用户注册','用户开户','用户投资'];
	       // name = '用户数据'
	    	$.ajax({
	    	    type:"post",
	    		url:"/userData/showTUserData",
	    		data:{"id":"today"},
	    		dataType:"json",
	    		success:function(json){
	    			cat[0]=json.newAllUserCount;
	    			cat[1]=json.newAllUserRegisterCount;
	    			cat[2]=json.newInvestCount;
	    		 		for(var t=0;t<cat.length;t++){
	    		 			data2[t]={y:eval(cat[t]),color:colors[t]};
	    		 		}
	    			/*todayStart  */
	    			console.log(data2);
	    			function setChart(name, categories, data2, colors) {
	    	    	 	chart.xAxis[0].setCategories(categories, false);
	    	    	 	chart.series[0].remove(false);
	    	    	 	chart.addSeries({
	    	    	 		name: name,
	    	    	 		color: color || 'white'
	    	    	 	}, false);
	    	    	 	chart.redraw();
	    	    	     }
	    	    	     var chart = $('#container').highcharts({
	    	    	         chart: {
	    	    	             type: 'column'
	    	    	         },
	    	    	         title: {
	    	    	             text: '用户数据'
	    	    	         },
	    	    	         subtitle: {
	    	    	             text: ''
	    	    	         },
	    	    	         xAxis: {
	    	    	             categories: categories
	    	    	         },
	    	    	         yAxis: {
	    	    	             title: {
	    	    	                 text: '人数(位)'
	    	    	             }
	    	    	         },
	    	    	         plotOptions: {
	    	    	             column: {
	    	    	                 cursor: 'pointer',
	    	    	                 point: {
	    	    	                     events: {
	    	    	                         click: function() {
	    	    	                             var drilldown = this.drilldown;
	    	    	                             if (drilldown) { // drill down
	    	    	                                 setChart(drilldown.name, drilldown.categories, drilldown.data, drilldown.color);
	    	    	                             } else { // restore
	    	    	                                 setChart(name, categories, data);
	    	    	                             }
	    	    	                         }
	    	    	                     }
	    	    	                 },
	    	    	                 dataLabels: {
	    	    	                     enabled: true,
	    	    	                     size:12,
	    	    	                     color: colors[2],
	    	    	                     style: {
	    	    	                         fontWeight: 'bold'
	    	    	                     },
	    	    	                     formatter: function() {
	    	    	                         return this.y;
	    	    	                     }
	    	    	                 }
	    	    	             }
	    	    	         },
	    	    	         tooltip: {
	    	    	             formatter: function() {
	    	    	                 var point = this.point,
	    	    	                     s = this.x +':<b>'+ this.y +'位</b><br/>';
	    	    	                 if (point.drilldown) {
	    	    	                     s += 'Click to view '+ point.category +' versions';
	    	    	                 } else {
	    	    	                     s += '';
	    	    	                 }
	    	    	                 return s;
	    	    	             }
	    	    	         },
	    	    	         series: [{
	    	    	             name: name,
	    	    	             data: data2,
	    	    	             color: 'white'
	    	    	         }],
	    	    	         exporting: {
	    	    	             enabled: false
	    	    	         }
	    	    	     }).highcharts(); // return chart
	    	    	     
	    	    }
	        });
	     });
	});
/*todayEnd */
</script>
	<jsp:include page="base/header.jsp" />
	<div id="content">
		<jsp:include page="base/left.jsp" />
		<div class="m-right">
			<div class="right-nav">
				<ul>
					<li>站内用户数据查询</li>
				</ul>
			</div>
			<div class="main">
				<div class="bound">
					<div class="box">
						<div class="block-box">
							<div class="input-list" style="position:relative;">
							开始时间<input id="sTime" type="text" class="time-begin datepicker"></div>                        
                            <div id="dateDiv" class="input-list small-list" style="position:relative;">
                           	 结束时间<input id="eTime" type="text" class="time-over datepicker"></div>
                            <div class="input-list small-list">
                            <input type="button" id="query"  value="查询" class="but-search small-but" >
                            </div>
                            <div class="input-list small-list-right">
                            <input type="button" id="today" value="今日" class="but-search small-but" >
                            </div>
                        	<div class="input-list small-list-right">
                        	<input type="button"  id="week"value="最近一周" class="but-search small-but" >
                        	</div>
                        	<div class="input-list small-list-right">
                        	<input type="button" id="month" value="最近一月" class="but-search small-but" >
                        	</div>
                        	<script type="text/javascript">
                        	$(".datepicker").datepicker({
                        		
                        		dateFormat:'yy-mm-dd'
                        	});
								
							</script>
						</div>
						<div class="block-box1">
							<form name="form1" method="post" action="">
								<p>
									<label> <input type="radio" name="RadioGroup1"
										value="柱状图"  checked="checked" id="RadioGroup1_0"> 柱状图
									</label> 
								</p>
							</form>
						</div>
					</div>
					<div class="box">
						<div id="container" style="min-width:800px;height:400px"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>navList(12);</script>
	<script type="text/javascript">
var sTime;
var eTime;
var arr=new Array();
var arrNEW=new Array();
var data1=new Array();
$(function () {
	/*折线图Start  */
	$("#RadioGroup1_1").click(function(){
		
		 $('#container').highcharts({
		        title: {
		            text: 'Monthly Average Temperature',
		            x: -20 //center
		        },
		        subtitle: {
		            text: 'Source: WorldClimate.com',
		            x: -20
		        },
		        xAxis: {
		            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
		        },
		        yAxis: {
		            title: {
		                text: 'Temperature (°C)'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: '°C'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: 'Tokyo',
		            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
		        }, {
		            name: 'New York',
		            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
		        }, {
		            name: 'Berlin',
		            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
		        }, {
		            name: 'London',
		            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
		        }]
		    });
	});
	
	/*折线图end  */
	
	/*条形图start  */
	$("#RadioGroup1_0").click(function(){
		pub();
	});
    
   /*条形图end  */
   
   
  
   $("#month").click(function(){
	    var arrData=new Array();
		var arrData1=new Array();
		var arrData2=new Array();
		arrNEW.length = 0;
		arr.length = 0;
	   $.ajax({
		    type:"post",
   			url:"/userData/showTUserData",
   			data:{"id":"month"},
   			dataType:"json",
		   success:function(json){
			   console.info(json);
   					json=eval(json);
			   for (var key in json) {  
				   arr.push(key);
		            console.log(json[key]); 
		            arrData.push(json[key][0].count);
		            arrData1.push(json[key][1].count);
		            arrData2.push(json[key][2].count);
		            data1[0]={name: "用户注册",data: arrData};
		            data1[1]={name: "用户开户",data: arrData1};
		            data1[2]={name: "用户投资",data: arrData2}
		        }  
			   //console.info("注册=="+arrData+" kh== "+arrData1+"  tz="+arrData2);
			   for(var s=0;s<arr.length;s++){
				   arrNEW[s]= arr[s];
			   }
			   pub();
		   },error:function(){
			   console.info("周调去失败");
		   }
	   });
	   pub();
   });
   
   $("#query").click(function(){
	    var arrData=new Array();
		var arrData1=new Array();
		var arrData2=new Array();
		arrNEW.length = 0;
		arr.length = 0;
		sTime=$("#sTime").val();
		eTime=$("#eTime").val();
	   $.ajax({
		    type:"post",
   			url:"/userData/showTUserData",
   			data:{"id":"query",
   				  "sTime":sTime,
   				  "eTime":eTime,
   			},
   			dataType:"json",
		   success:function(json){
			   console.info(json);
   					json=eval(json);
			   for (var key in json) {  
				   arr.push(key);
		            console.log(json[key]); 
		            arrData.push(json[key][0].count);
		            arrData1.push(json[key][1].count);
		            arrData2.push(json[key][2].count);
		            data1[0]={name: "用户注册",data: arrData};
		            data1[1]={name: "用户开户",data: arrData1};
		            data1[2]={name: "用户投资",data: arrData2}
		        }  
			   //console.info("注册=="+arrData+" kh== "+arrData1+"  tz="+arrData2);
			   for(var s=0;s<arr.length;s++){
				   arrNEW[s]= arr[s];
				   
			   }
			   //console.info(arrNew);
			   
				  
			   
			   pub();
		   },error:function(){
			   console.info("周调去失败");
		   }
	   });
	   pub();
   });
});


$("#week").click(function(){
	var arrData=new Array();
	var arrData1=new Array();
	var arrData2=new Array();
	arrNEW.length = 0;
	arr.length = 0;
	   $.ajax({
		    type:"post",
			url:"/userData/showTUserData",
			data:{"id":"week"},
			dataType:"json",
		   success:function(json){
			   json=eval(json);
			   
			   for (var key in json) {  
				   arr.push(key);
		            console.log(json[key]); 
		            
		            arrData.push(json[key][0].count);
		            arrData1.push(json[key][1].count);
		            arrData2.push(json[key][2].count);
		            data1[0]={name: "用户注册",data: arrData};
		            data1[1]={name: "用户开户",data: arrData1};
		            data1[2]={name: "用户投资",data: arrData2}
		        }  
			   //console.info("注册=="+arrData+" kh== "+arrData1+"  tz="+arrData2);
			   for(var s=0;s<arr.length;s++){
				   arrNEW[s]= arr[s];
				   
			   }
			   pub();
		   },error:function(){
			   console.info("周调去失败");
		   }
		   
	   });
	   pub();
});
function pub(){
	
	$('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '用户数据'
        },
        subtitle: {
            text: 'duanrong.com'
        },
        xAxis: {
        	//日期
            categories: arrNEW
               
        },
        yAxis: {
            min: 0,
            title: {
                text: '人数(个)'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:30px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f}位</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        series: data1
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
