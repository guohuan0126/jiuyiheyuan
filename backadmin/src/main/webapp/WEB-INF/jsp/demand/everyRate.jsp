<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <title>每日收益率管理</title>
</head>

<body>



<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>


<section>
  
 <%@include file="../base/leftMenu.jsp"%>
  
  <div class="mainpanel">
    
<%@include file="../base/header.jsp"%>
        
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：每日收益率管理 </h2>     
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline"  action="/demand/everyRate" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">年</label>
	                <select class="select2" name="year" id="year" data-placeholder="年...">
	                  <option value="">--请选择</option>
	                  <option value="2015">2015年</option>
	                  <option value="2016">2016年</option>
	                  <option value="2017">2017年</option>
	                  <option value="2018">2018年</option>
	                  <option value="2019">2019年</option>
	                  <option value="2020">2020年</option>
	                </select>
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">月</label>
	                <select class="select2" name="month" id="month" data-placeholder="月...">
	                  <option value="">--请选择</option>
	                  <option value="01">1月</option>
	                  <option value="02">2月</option>
	                  <option value="03">3月</option>
	                  <option value="04">4月</option>
	                  <option value="05">5月</option>
	                  <option value="06">6月</option>
	                  <option value="07">7月</option>
	                  <option value="08">8月</option>
	                  <option value="09">9月</option>
	                  <option value="10">10月</option>
	                  <option value="11">11月</option>
	                  <option value="12">12月</option>
	                </select>
	            </div>
	           
	            <button type="submit" class="btn btn-primary">查询</button></br></br></form>
	             <form class="form-inline"  id="form1" action="" method="post">
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">年</label>
	                <select class="select2" name="year1" id="year1" data-placeholder="年...">
	                  <option value="2015">2015年</option>
	                  <option value="2016">2016年</option>
	                  <option value="2017">2017年</option>
	                  <option value="2018">2018年</option>
	                  <option value="2019">2019年</option>
	                  <option value="2020">2020年</option>
	                </select>
	            </div>
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">月</label>
	                <select class="select2" name="month1" id="month1" data-placeholder="月...">
	                  <option value="01">1月</option>
	                  <option value="02">2月</option>
	                  <option value="03">3月</option>
	                  <option value="04">4月</option>
	                  <option value="05">5月</option>
	                  <option value="06">6月</option>
	                  <option value="07">7月</option>
	                  <option value="08">8月</option>
	                  <option value="09">9月</option>
	                  <option value="10">10月</option>
	                  <option value="11">11月</option>
	                  <option value="12">12月</option>
	                </select>
	                <input
					type="text" name="rate" id="rate" value="5" 
				/>%
					<div id="errorRate" style="display: none;">利率不能为空</div>
	              
	            </div>
	            
	            <button type="button" class="btn btn-primary" onclick="sub()">生成某月利率数据</button>
	          </form>
	    </div>
	  <div class="panel-body"> 
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>日期</th>
                        <th>利率</th>
                        <th>每万份收益</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                      <td><fmt:formatDate
									value="${item.date }"
									pattern="yyyy-MM-dd" /></td>
                       <td><fmt:formatNumber value="${item.rate*100}" pattern="#.##"/>%</td>
                       <td>${item.interest }</td>
                        <c:set var="nowDate">
						<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" type="date"/>
						</c:set>
                       <c:set var="createDate">
						<fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd" type="date"/>
						</c:set>
                       <td>
                       <c:if test="${nowDate<= createDate}">
                       <a style="cursor:pointer" href="/demand/toeditRate?id=${item.id }">编辑</a>
                       	</c:if>
                       </td>
                      </tr>
                    </c:forEach>
                    </tbody>
        </table>
 		<%@ include file="../base/page.jsp"%>

 </div><!-- panel-body -->
 </div><!-- panel --> 
</div><!-- contentpanel -->
</div><!-- mainpanel -->
</section>
<script>
jQuery(document).ready(function(){
	$("#year").val('${year}');
	$("#month").val('${month}');
});
function sub() {
	var rate=$("#rate").val();
	$('#errorRate').attr("style", "display:none;font-size:14px;color:red");
	if(rate==null || rate==''){
		$('#errorRate').attr("style", "display:block;font-size:14px;color:red");
		return false;
	}
	var img = /^[0-9]+(.[0-9]{0,2})?$/;
	if (!img.test(rate) || isNaN(rate) || rate <0) {
		$('#errorRate').text("利率不正确");
		$('#errorRate').attr("style", "display:block;font-size:14px;color:red");
		return false;
	}
	
	  $.ajax({
			type : "POST",
			url : '/demand/addRate',
			data : $('#form1').serialize(),// 你的formid
			async : false,
			beforeSend:function(){
				xval=getBusyOverlay('viewport',
				{color:'blue', opacity:0.5, text:'进行中...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
				{color:'blue', size:25, type:'o'});	
			},
			error : function(e) {
				alert("异常");
			},
			success : function(data) {
				window.clearInterval(xval.ntime); 
				xval.remove();
				if (data == 'ok') {
					alert('操作成功');
					window.location = "/demand/everyRate";
				}else if(data == 'fail'){
					alert('操作失败');
				}else{
					alert(data);
				}
			}
		});
	} 
</script>
</body>
</html>
