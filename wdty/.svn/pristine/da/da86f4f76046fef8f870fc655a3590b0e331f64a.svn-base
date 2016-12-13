<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=p0XjbdHG9kv7xrxxLacDSnjbKHXK8pPT"></script>
	<title>浏览器定位</title>
  </head>
  
  <body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">


	 var map = null;
	 var point = null;
	 var lng = null;
	 var lat = null;
	 var geolocation = null;
	 var geoc = null;
     function handleSuccess(position){
         // 获取到当前位置经纬度  本例中是chrome浏览器取到的是google地图中的经纬度
        lng = position.coords.longitude;
        lat = position.coords.latitude;
         alert(lng+","+lat);
      	// 百度地图API功能
     	map = new BMap.Map("allmap");
     	point = new BMap.Point(lng,lat);
         
     	map.centerAndZoom(point,12);
     	var geoc = new BMap.Geocoder();
		geoc.getLocation(point, function(rs){
			var addComp = rs.addressComponents;
			alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
		});
 
     }
     
     function handleError(error){
     
     }
	
 	
	 if (window.navigator.geolocation) {
         var options = {
             enableHighAccuracy: true,
         };
         window.navigator.geolocation.getCurrentPosition(handleSuccess, handleError, options);
     } else {
         alert("浏览器不支持html5来获取地理位置信息");
     }
</script>
