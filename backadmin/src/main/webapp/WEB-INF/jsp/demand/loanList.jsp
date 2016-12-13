﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">

  <title>活期宝资产列表</title>


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
width: 280px;}

.ui-datepicker-next {
background: url(../../images/calendar-arrow.png) no-repeat 10px 5px;
}

.ui-datepicker-prev {
background: url(../../images/calendar-arrow.png) no-repeat 8px -80px;
}

.ui-datepicker table {
    color: #ffffff;
}
.only p{width:400px;height:100px;word-wrap:break-word;overflow-y:auto;}
.only_1{width:100px;display:block;word-wrap:break-word;    }

</style>
<script type="text/javascript">
	
	
	function getReason1(rule){
		$('#rule').dialogBox({
			width: 500,
			height: 300,
			title: '车贷父标编号',
			hasClose: true,
			content: rule
		});
	}
	

</script>
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
<script src="/js/jquery.dialogBox.js"></script>
<link rel="stylesheet" href="/css/jquery.dialogbox.css" />        
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：活期宝资产列表 </h2>
      
    </div>
    
    <div class="contentpanel">
    
	    <div class="panel panel-default">
	    <div class="panel-heading">
	    	 <form class="form-inline" id="form1" method="post">
	   	    <div class="form-group">
	             <label class="sr-only" for="exampleInputloanName">项目名称</label>
	             <input type="text" name="loanName" value="${loanName}" class="form-control" id="exampleInputloanName" placeholder="项目名称">
	           </div>
	           
	           <div class="form-group">
	             <label class="sr-only" for="exampleInputborrower">借款人</label>
	             <input type="text" name="borrower" value="${borrower }" class="form-control" id="exampleInputborrower" placeholder="借款人">
	           </div>
	    	  <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">项目状态</label>
	                <select class="select2" name="status" id="st" data-placeholder="项目状态...">
	                  <option value="">--项目状态</option>
	                  <option value="finish">已完成</option>
	                  <option value="repay">还款中</option>
	                </select>
	            </div>
	    	  <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">项目类型</label>
	                <select class="select2" name="loanType" id="loanType" data-placeholder="项目类型...">
	                  <option value="">--项目类型</option>
	                  <option value="车押宝">车押宝</option>
	                  <option value="房押宝">房押宝</option>
	                  <option value="供应宝">供应宝</option>
	                  <option value="金农宝">金农宝</option>
	                </select>
	            </div>	            	           
	            <div class="form-group">
	              <label>排序规则：</label>
	                <select class="select2" name="type" id="sequence" data-placeholder="排序...">
	                  <option value="creatTime">创建时间</option>
	                  <option value="itemNumber">项目编号</option>
	                  
	                </select>
	            </div>	
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">开放状态</label>
	                <select class="select2" name="openStatus" id="openStatus" data-placeholder="开放状态...">
	                  <option value="">--开放状态</option>
	                  <option value="opened">已开放</option>
	                  <option value="notopen">未开放</option>
	                </select>
	            </div> 
	            <div class="form-group">
	              <label class="sr-only" for="exampleInputmobile">还款方式</label>
	                <select class="select2" name="repayType" id="repayType" data-placeholder="还款方式...">
	                  <option value="">--还款方式</option>
	                  <option value="先息后本">先息后本</option>
	                  <option value="等额本息">等额本息</option>
	                </select>
	            </div>
	            <br>
	            <br>
	            <label class="control-label">开始时间：</label>             
                <div class="input-group" >
                <input type="text" class="form-control" name="start" value="${start}" placeholder="开始时间" id="datepicker">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="end" value="${end}" placeholder="结束时间" id="datepicker1">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>
                 <label class="control-label">实际结束时间：</label>   
                <div class="input-group" >
                <input type="text" class="form-control" name="actualStart" value="${actualStart}" placeholder="开始时间" id="datepicker2">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div> -- 
                <div class="input-group" >
                <input type="text" class="form-control" name="actualEnd" value="${actualEnd}" placeholder="结束时间" id="datepicker3">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                </div>         	                    
	            <button type="submit" id="search" class="btn btn-primary">查询</button>
	        <%--  <base:active activeId="Action_Hqbloanlist_AddCarloan">  --%>
	            <button type="button" id="add" class="btn btn-primary">添加车贷房贷供应宝资产</button>	
	         <%-- </base:active>    --%>   
          <%--    <base:active activeId="Action_Hqbloanlist_addRural"> --%>
                <button type="button" id="addAgriculture" class="btn btn-primary">添加农贷资产</button>	
           <%--  </base:active>  --%>    
            <button type="button" id="addTimelyAgriculture" class="btn btn-primary">添加先息后本资产</button>
            <button type="button" id="export" class="btn btn-primary">导出数据</button>	
         		<div class="form-group" style="margin-left: 40px;">
         			<span style="font-size: 20px">活期宝明日到期资产金额:  </span> <span style="font-size: 20px" >
         			 <fmt:formatNumber currencySymbol="￥" type="currency" value="${money}"  />
         			</span>
         		</div>
	          </form>
	    </div>
	  <div class="panel-body"> 
	  <div id="rule"></div>
   			 <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                        <th>项目名称</th>
                        <th>项目状态</th>
                        <th>项目类型</th>
                        <th>借款金额</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>计算方式</th>
                        <th>借款期限</th>
                       
                        <th>借款人</th>
                        <th>身份证号</th>
                        <th>品牌和型号</th>
                        <th>车牌号</th>
                        <th>公里数</th>
                        <th>购买时间</th>
                        <th>评估价格</th>
                        <th>抵押方式</th>
                        <th>抵/质押率</th>
                        
                         <th>项目地点</th>
                        <th>楼房性质</th>
                        <th>建筑面积</th>
                        
                         <th>借款公司</th>
                       <!--  <th>营业执照号</th>
                        <th>经营年限</th> -->
                        <th>注册资本</th>
                        <th>实收资本</th>
                      <!--   <th>主营产品或服务</th>
                        <th>员工人数</th> -->
                        
                        <th>借款用途</th>
                        <th>所在地</th>
                        <th>婚姻状况</th>
                        <th>还款来源</th>
                        <th>车贷父标ID</th>
                        <th>还款方式</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo.results}" var="item">
                      <tr>
                       <td>${item.loanName }</td>
                       <td>
                       <c:if test="${item.loanStatus eq 'finish'}">
                      	已完成
                     	</c:if>
                     	 <c:if test="${item.loanStatus eq 'repay'}">
                      	还款中
                     	</c:if>
                     	
                       </td>
                       <td>${item.loanType }</td>
                       <td>${item.totalMoney}</td>
                       <td><fmt:formatDate
									value="${item.startTime}"
									pattern="yyyy/MM/dd" />
						<%-- ${item.showstartTime} --%>
						</td>
						<td><fmt:formatDate
									value="${item.finishTime }"
									pattern="yyyy/MM/dd" />
						<%-- ${item.showfinishTime} --%>
						</td>
						<td>${item.operationType}</td>
                       <td>
                       <c:if test="${item.operationType eq '天'}">
                        ${item.day }
                       </c:if>
                        <c:if test="${item.operationType eq '月'}">
                        ${item.month }
                       </c:if>
                      
                       </td>
                      
						<td>${item.borrower}</td>
						<td>${item.idCard}</td>
						<td>${item.brand}</td>
						<td>${item.licensePlateNumber}</td>
						<td>${item.kilometreAmount}</td>
						<td><fmt:formatDate
									value="${item.buyTime }"
									pattern="yyyy-MM-dd HH:mm" /></td>
						<td>${item.assessPrice}</td>
						<td>${item.guaranteeType}</td>
						<td>${item.guaranteeRate}</td>
						
						<td>${item.loanAddr}</td>
						<td>${item.buildingProperty}</td>
						<td>${item.buildingArea}</td>
					
						<td>${item.borrowingCompany}</td>
						<%-- <td>${item.businessLicenseNumber}</td>
						<td>${item.operationYear}</td> --%>
						<td>${item.registeredCapital}</td>
						<td>${item.realIncomeCapital}</td>
						<%-- <td>${item.operationProduction}</td>
						<td>${item.staffNumber}</td> --%>
						
						<td>${item.borrowingPurposes}</td>
						<td>${item.location}</td>
						<td>
							<c:if test="${item.maritalStatus == 0}">未婚</c:if>
							<c:if test="${item.maritalStatus == 1}">已婚</c:if>
							<c:if test="${item.maritalStatus == 2}">离异</c:if>
							<c:if test="${item.maritalStatus == 3}">丧偶</c:if>
						</td>
						<td>${item.sourceOfRepayment}</td>
						<td>
						<c:if test="${ not empty item.parentId and item.parentId != ''}">
						<a href="javascript:getReason1('${item.parentId}');">父标</a>
						</c:if>
                        </td>
                        <td>
						<c:if test="${item.repayType=='先息后本'}">
							先息后本
						</c:if>
						<c:if test="${item.repayType=='等额本息'}">
							等额本息
						</c:if>	
						</td>
						<td>
						<c:if test="${item.openStatus=='notopen'}">
							<a href="javascript:del('${item.id}');" >删除</a>
						</c:if>
							|<a href="/demand/toeditLoan?id=${item.id}">修改</a>
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
$("#sequence").change( function() {
	$("#form1").attr("action","/demand/loanList");
	$("#form1").submit();
});
$("button[id=search]").click(function(){
	$("#form1").attr("action","/demand/loanList");
	$("#form1").submit();
});
$("button[id=export]").click(function(){
	$("#form1").attr("action","/demand/demandExport");
	$("#form1").submit();
});

jQuery(document).ready(function(){
    // Date Picker
	  jQuery('#datepicker').datepicker();
	  jQuery('#datepicker1').datepicker();
	  jQuery('#datepicker2').datepicker();
	  jQuery('#datepicker3').datepicker();
	  jQuery('#datepicker-inline').datepicker();
	  
	  jQuery('#datepicker-multiple').datepicker({
	    numberOfMonths: 3,
	    showButtonPanel: true
	  });		  
	$("#st").val('${status}');
	$("#sequence").val('${type}');
	$("#loanType").val('${loanType}');
	$("#openStatus").val('${openStatus}');
	$("#repayType").val('${repayType}');
});
function del(id){
	  if(confirm("您确定要删除吗？")){
		  $.ajax({
				type : "POST",
				url : '/demand/delLoan',
				data : {id:id},// 你的formid
				async : false,
				error : function(e) {
					alert("异常");
				},
				success : function(data) {
					if (data == 'ok') {
						alert('操作成功');
						//window.location = "/demand/loanList";
					}else{
						alert('操作失败');
					}
				}
			});
	  }
}
$("button[id=add]").click(function(){
	window.location.href="/demand/toLoan";
});
$("button[id=addAgriculture]").click(function(){
	window.location.href="/demand/agricultureLoan";
});
$("button[id=addTimelyAgriculture]").click(function(){
	window.location.href="/demand/addTimelyAgriculture";
});

</script>
</body>
</html>