<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
  <head>
    <title>活动参与情况</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<!-- 可选的Bootstrap主题文件（一般不用引入） -->
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
   <script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.1.4/highcharts.js"></script>
   <script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.1.4/modules/exporting.js"></script>
 
	
    <script type="text/javascript">
    /**
     * 初始化折线图
     */
    function initZhexiantu(obj){
   	 
   	 
   	 $('#'+obj.id).highcharts({
	        title: {
	            text: obj.title,
	            x: -20 //center
	        },
	        subtitle: {
	            text: obj.subTitle,
	            x: -20
	        },
	        xAxis: {
	            categories: obj.arrX
	        },
	        yAxis: {
	            title: {
	                text: obj.arrYTitle
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: obj.tipName
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: [{
	            name: obj.yName,
	            data: obj.arrY
	        }],
	        exporting: {
	            
	        },
	    });
   	 
    }
    
    /**
     * 初始化折线图
     */
    function initZhuZhuangtu(obj){
   	 
   	 
    	$('#'+obj.id).highcharts({
            chart: {
                type: 'column'
            },
            title: {
            	text: obj.title,
            },
            subtitle: {
            	text: obj.subTitle,
            },
            xAxis: {
            	 categories: obj.arrX
            },
            yAxis: {
                min: 0,
                title: {
                	text: obj.arrYTitle
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y}'+obj.tipName+'</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
	        series: [{
	            name: obj.yName,
	            data: obj.arrY
	        }],
        });
   	 
    }
    
    /**
     * 初始化饼图
     */
    function initBingtu(obj){
    	
    	$('#'+obj.id).highcharts({
    	        chart: {
    	            plotBackgroundColor: null,
    	            plotBorderWidth: null,
    	            plotShadow: false
    	        },
    	        title: {
    	        	text: obj.title
    	        },
    	        tooltip: {
    	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    	        },
    	        plotOptions: {
    	            pie: {
    	                allowPointSelect: true,
    	                cursor: 'pointer',
    	                dataLabels: {
    	                    enabled: true,
    	                    color: '#000000',
    	                    connectorColor: '#000000',
    	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
    	                }
    	            }
    	        },
    	        series: [{
    	            type: 'pie',
    	            name: obj.yName,
    	            data: obj.arrY
    	        }]
    	    });
    	
    }
    
      $(function () {
    	    
          var actionDataJSON = ${actionDataJSON};
          //console.log(actionDataJSON);
          
          //1.访问量折线统计图
          var goIndexArrx = new Array();
          var goIndexArry = new Array();
          var goIndexCount = 0;
          for(var i =0;i<actionDataJSON.goIndexList.length;i++){
        	  goIndexArrx.push(actionDataJSON.goIndexList[i].day);
        	  goIndexArry.push(actionDataJSON.goIndexList[i].amount);
        	  goIndexCount += actionDataJSON.goIndexList[i].amount; 
          }
          
          initZhexiantu({id:"goIndexShow",
        	             title:"该活动被访问了"+goIndexCount+"次",
        	             subTitle:"",
        	             arrX:goIndexArrx,
        	             arrYTitle:"访问量",
        	             tipName:"次",
        	             yName:"访问量",
        	             arrY:goIndexArry});
          
          //2.分享次数统计折线图
          var shareArrx = new Array();
          var shareArry = new Array();
          var shareCount = 0;
          for(var i =0;i<actionDataJSON.shareList.length;i++){
        	  shareArrx.push(actionDataJSON.shareList[i].day);
        	  shareArry.push(actionDataJSON.shareList[i].amount);
        	  shareCount += actionDataJSON.shareList[i].amount;
          }
          
          initZhexiantu({id:"shareShow",
	             title:"活动共被分享了"+shareCount+"次",
	             subTitle:"",
	             arrX:shareArrx,
	             arrYTitle:"分享次数",
	             tipName:"次",
	             yName:"分享次数",
	             arrY:shareArry});
          
          //3.按城市统计访问量
/*           var cityArrx = new Array();
          var cityArry = new Array();
          for(var i =0;i<actionDataJSON.cityList.length;i++){
        	  cityArrx.push(actionDataJSON.cityList[i].city);
        	  cityArry.push(actionDataJSON.cityList[i].amount);
          }
          
          initZhuZhuangtu({id:"cityShow",
                 title:"按城市统计访问量",
                 subTitle:"",
                 arrX:cityArrx,
                 arrYTitle:"访问量",
                 tipName:"次",
                 yName:"访问次数",
                 arrY:cityArry}); */
      
      //4.统计优惠劵发放情况
      var redArrx = new Array();
      var redArry = new Array();
      var newUser = 0; //未注册
      var noOpenUser = 0; //未开户 
      var oldUser01 = 0; //投资小于5万
      var oldUser02 = 0; //投资大于5万
      var redCount = 0;  //奖励数量
      var moneyCount = 0;  //现金数量
      var moneySum = 0; //现金金额 
      for(var i =0;i<actionDataJSON.rateList.length;i++){
    	  var rate = actionDataJSON.rateList[i].rate;
    	  var amount = actionDataJSON.rateList[i].amount;
    	  if(rate == 0.01){
        	  redArrx.push(rate*100+"%加息劵");
        	  redArry.push(amount);
        	  oldUser02 += amount;
        	  redCount += amount;
    	  }

      }
      for(var i =0;i<actionDataJSON.moneyRedList.length;i++){
    	  var money = actionDataJSON.moneyRedList[i].money;
    	  var amount = actionDataJSON.moneyRedList[i].amount;
    	  redArrx.push(money+"元红包");
    	  redArry.push(amount);
    	  if(money == 10){
    		  noOpenUser += amount;
    	  }else if(money > 10){
    		  oldUser01 += amount;
    	  }
    	  redCount += amount;
      }
      for(var i =0;i<actionDataJSON.moneyList.length;i++){
    	  var money = actionDataJSON.moneyList[i].money;
    	  var amount = actionDataJSON.moneyList[i].amount;
    	  redArrx.push(money+"元现金");
    	  redArry.push(amount);
    	  if(money == 2){
    		  newUser += amount;
    	  }
    	  moneyCount += amount;
    	  moneySum += money*amount;
      }
      
      initZhuZhuangtu({id:"redShow",
             title: "奖励总数量："+(moneyCount+redCount)+",现金总额："+moneySum+"元",
             subTitle:"",
             arrX:redArrx,
             arrYTitle:"数量",
             tipName:"个",
             yName:"数量",
             arrY:redArry});
      var userCount = newUser+noOpenUser+oldUser01+oldUser02;
      //用户分类统计
      var canyuData = [
                        ['未注册', newUser/userCount],
                        ['未投资',noOpenUser/userCount],
                        ['单笔投资不满5万',oldUser01/userCount],
                        ['单笔投资满5万',oldUser02/userCount]
                    ];
      
      initBingtu({id:"calssShow",
          title:"各用户占比",
          subTitle:"",
          arrX:shareArrx,
          arrYTitle:"参与比例",
          tipName:"人",
          yName:"占比",
          arrY:canyuData});
	  
	 });
     

    </script>
  </head>
  <body>
  <div class="container">
      <div class="row" >

<div class="page-header">
    <h1>活动时实统计</h1>
<%--   <h1>已发放金额：<fmt:formatNumber value="${json.sumMoney }" pattern="###,###,###.##" />元<small>上限金额：<fmt:formatNumber value="${json.rule.totalMoney }" pattern="###,###,###.##" />元</small>
       已领取数量：${json.redCount }<small>上限数量：${json.rule.count }</small>
  <a href="/pages/login.jsp" class="btn btn-success" style="float: right;" >退出</a> 
  </h1>--%>
</div>
     
 </div>
 
<div class="row" >
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">访问量：</h3>
  </div>
  <div class="panel-body">
   <div id="goIndexShow" style="min-width:700px;height:400px"></div>
  </div>
</div>
  </div>
  
  <div class="row" >
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">分享次数：</h3>
  </div>
  <div class="panel-body">
   <div id="shareShow" style="min-width:700px;height:400px"></div>
  </div>
</div>
  </div>
  
<!-- <div class="row" >
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">每个城市访问量：</h3>
  </div>
  <div class="panel-body">
   <div id="cityShow" style="min-width:700px;height:400px"></div>
  </div>
</div>
</div> -->

<div class="row" >
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">红包现金发放统计：</h3>
  </div>
  <div class="panel-body">
   <div id="redShow" style="min-width:700px;height:400px"></div>
  </div>
</div>
</div>

<div class="row" >
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">用户统计：</h3>
  </div>
  <div class="panel-body">
   <div id="calssShow" style="min-width:700px;height:400px"></div>
  </div>
</div>
</div>
      <%-- <div class="row" >
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">领取红包用户列表：</h3>
  </div>
  <div class="panel-body">
   <table class="table table-bordered">
    <thead>
    <tr>
      <th>序号</th>
      <th>userId</th>
      <th>姓名</th>
      <th>手机号</th>
      <th>注册时间</th>
      <th>领劵详情</th>
    </tr>
    </thead>
    <tbody>
      <c:forEach var="red" items="${json.showList }" varStatus="status" >
         <tr>
         <td>${status.index+1}</td>
         <td>${red.userId }</td>
         <td>${red.realname }</td>
         <td>${red.mobile}</td>
         <td><fmt:formatDate value="${red.registerTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
         <td>
           <c:forEach var="obj" items="${red.list }" >
              <p><fmt:formatNumber value="${obj.money }" pattern="###,###,##0.##" />元&nbsp;&nbsp;<fmt:formatDate value="${obj.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></p>
           </c:forEach>
         </td>
         </tr>
      </c:forEach>
    </tbody>
    </table>
  </div>
</div>
  </div> --%>
  
  
  
  </div>
  </body>
</html>
