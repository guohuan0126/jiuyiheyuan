<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="${ctx}/images/favicon.png" type="image/png">
  <link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
  <link rel="stylesheet" type="text/css" href="${ctx}/css/dsdialog.css" />
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
</style>
  <title>借款管理</title>
</head>
<body>
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<section> 
 <%@include file="../base/leftMenu.jsp"%> 
  <div class="mainpanel">   
	<%@include file="../base/header.jsp"%>      
    <div class="pageheader">
      <h2><i class="fa fa-table"></i>当前位置：借款项目列表 </h2>     
      <div class="breadcrumb-wrapper">
        <ol class="breadcrumb" style="padding: 16px 16px; font-size:16px;">
          <li><a href=""></a></li>
        </ol>
      </div>
    </div>  
   <div class="contentpanel ">
    <div class="panel panel-default"> 
    <div class="panel-heading">
	    	 <form class="form-inline"  action="/loan/loanList" method="post">		    	 
		    	<div class="form-group">              
	            	<input type="text" name="id" id = "id"  class="form-control" placeholder="编号" style="width:160px">
	            </div>		    	 
	            <div class="form-group">	              
	            	<input type="text" name="name"  id="name" class="form-control" placeholder="名称" style="width:160px">
	            </div>
	              <label class="control-label">项目类型 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="loanType" name="loanType" class="select" required data-placeholder="请选择...">
                      <option value="">请选择...</option>                
                      <option value="金农宝" <c:if test="${loanType eq '金农宝' }">selected</c:if> >金农宝</option>
                      <option value="车贷" <c:if test="${loanType eq '车贷' }">selected</c:if>>车贷</option>
                      <option value="房贷" <c:if test="${loanType eq '房贷' }">selected</c:if>>房贷</option>
                      <option value="供应宝" <c:if test="${loanType eq '供应宝' }">selected</c:if>>供应宝</option>
                      <option value="企业贷" <c:if test="${loanType eq '企业贷' }">selected</c:if>>企业贷</option>                   
                    </select>              
                </div>	
	            <label class="control-label">项目状态 ：</label>
	            <div class="form-group" style="width: 110px;">              	           
                    <select id="status" name="status" class="select" required data-placeholder="请选择...">
                      <option value="">请选择...</option>                
                      <option value="还款中" <c:if test="${status eq '还款中' }">selected</c:if>>还款中</option>
                      <option value="筹款中" <c:if test="${status eq '筹款中' }">selected</c:if>>筹款中</option>
                      <option value="等待复核" <c:if test="${status eq '等待复核' }">selected</c:if>>等待复核</option>
                      <option value="完成" <c:if test="${status eq '完成' }">selected</c:if>>完成</option>
                      <option value="流标" <c:if test="${status eq '流标' }">selected</c:if>>流标</option>                   
                    </select>              
                </div>	
                <label class="control-label">测试项目：</label>
                <div class="form-group" style="width: 65px;">                            
                    <select id="textItem" name="textItem" class="select" required data-placeholder="请选择...">               
                      <option value="">全部</option>
                      <option value="是" <c:if test="${textItem eq '是' }">selected</c:if>>是</option>
                      <option value="否" <c:if test="${textItem eq '否' }">selected</c:if>>否</option>                   
                     </select>
                 </div>  
                 <label class="control-label">新手标：</label>
                 <div class="form-group" style="width: 65px;">                            
                    <select id="newbieEnjoy" name="newbieEnjoy" class="select" required data-placeholder="请选择...">               
                      <option value="">全部</option>
                      <option value="是" <c:if test="${newbieEnjoy eq '是' }">selected</c:if>>是</option>
                      <option value="否" <c:if test="${newbieEnjoy eq '否' }">selected</c:if>>否</option>                   
                     </select>
                 </div> 
                 <label class="control-label">周期：</label>
                 <div class="form-group" style="width:90px;">                            
                    <select id="deadline" name="deadline" class="select" required data-placeholder="请选择...">               
                      <option value="">全部</option>
                      <option value="1" <c:if test="${deadline eq '1' }">selected</c:if>>1个月</option>
                      <option value="2" <c:if test="${deadline eq '2' }">selected</c:if>>2个月</option>
                      <option value="3" <c:if test="${deadline eq '3' }">selected</c:if>>3个月</option>
                      <option value="4" <c:if test="${deadline eq '4' }">selected</c:if>>4个月</option>
                      <option value="5" <c:if test="${deadline eq '5' }">selected</c:if>>5个月</option>
                      <option value="6" <c:if test="${deadline eq '6' }">selected</c:if>>6个月</option>
                      <option value="7" <c:if test="${deadline eq '7' }">selected</c:if>>7个月</option>
                      <option value="8" <c:if test="${deadline eq '8' }">selected</c:if>>8个月</option>
                      <option value="9" <c:if test="${deadline eq '9' }">selected</c:if>>9个月</option>
                      <option value="10" <c:if test="${deadline eq '10' }">selected</c:if>>10个月</option>
                      <option value="11" <c:if test="${deadline eq '11' }">selected</c:if>>11个月</option>
                      <option value="12" <c:if test="${deadline eq '12' }">selected</c:if>>12个月</option>
                                    
                     </select>
                 </div>
                  <label class="control-label">是否多车：</label>
                 <div class="form-group" style="width: 65px;">                            
                    <select id="companyno" name="companyno" class="select" required data-placeholder="请选择...">               
                      <option value="">全部</option>
                      <option value="1" <c:if test="${companyno eq '1' }">selected</c:if>>是</option>
                      <option value="0" <c:if test="${companyno eq '0' }">selected</c:if>>否</option>                   
                     </select>
                 </div>    
                 <script type="text/javascript">                  	          
                 jQuery(".select").select2({
							width : '100%',
							minimumResultsForSearch : -1
						});								         
                 </script>
                <br><br>
                <label class="control-label">项目金额：</label>             
	            <div class="input-group" >
                <input type="text" class="form-control" name="minMoney" id= "minMoney" placeholder="最小金额" style="width:140px" value="${minMoney}">
                </div> -- 
	            <div class="input-group">
                <input type="text" class="form-control" name="maxMoney" id = "maxMoney" placeholder="最大金额" style="width:140px" value="${maxMoney}">  
                </div>
                <label class="control-label" style="margin-left:14px;">项目提交时间：</label>             
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" name="start" id="start"  placeholder="开始时间" style="width:140px" value="${start}"/>
               
                </div> -- 
	            <div class="input-group" >
                <input type="text" class="form-control datepicker" name="end" id = "end" placeholder="结束时间" style="width:140px"  value="${end}" />
                </div>
	            <script type="text/javascript">	            		            
	            jQuery(document).ready(function(){					
					 jQuery('.datepicker').datetimepicker({
						showSecond : true,
						timeFormat : 'hh:mm:ss',
						stepHour : 1,
						stepMinute : 1,
						stepSecond : 1,
						hour: 13,
	  					minute: 15
					});				 
				});

            	</script>                       
	            <button type="button" id ="queryButton" class="btn btn-primary" onclick="javascript:submit()" style="margin-left:16px;">查询</button>	           
	            <base:active activeId="loan_export">
	            <a href="javascript:exportLoan();" class="btn btn-primary" style="margin-left:6px;">导出</a>
	            <span style="color: red; word-wrap: break-word; display: inline-block; width: 370px; float:right;">只能导出一种类型项目，默认导出车贷，默认导出按项目发布时间到处当天的数据</span>
	            </base:active>
	             <script type="text/javascript">
	              		function exportLoan(){
	              			var str = "id="+$("#id").val()+"&name="+$("#name").val() 
	              			+"&loanType="+$("#loanType").val()+"&status="+$("#status").val()
	              			+"&textItem="+$("#textItem").val()+"&newbieEnjoy="+$("#newbieEnjoy").val()
	              			+"&deadline="+$("#deadline").val()+"&minMoney="+$("#minMoney").val()
	              			+"&maxMoney="+$("#maxMoney").val()+"&start="+$("#start").val()+"&end="+$("#end").val()
	              			+"&companyno="+$("#companyno").val();            	      			
	              			location = encodeURI(encodeURI("${ctx}/loan/exportLoan?"+str));              		
	              		}          
	              </script>
	          </form>     
	    </div>  
     <div class="panel-body"> 
	  <table class="table table-primary table-striped table-hover">
          <thead>
             <tr class="font">              
                <th >编号</th>
                <th >项目名称</th>
                <th >还款方式</th>                
                <th >周期/利率</th>              
                <th >借款人</th>
                <th >借款总金额</th>              
                <th >项目状态</th>
                <th >项目发起时间</th>
                <base:active activeId="19dfc6318d3d43708f4ffedac44b71df">
                	<th >项目排序</th>
               </base:active>             
                <th >操作</th>
             </tr>
          </thead>
          <tbody>
		  <c:forEach  var="loan" items="${pageInfo.results}">
		     <tr class="font">		     	
				 <td>${loan.id}
					 <c:if test="${loan.newbieEnjoy == '是'}"><img src="${ctx}/images/newbie.png"></c:if>
					 <c:if test="${loan.organizationExclusive == '是'}"><img src="${ctx}/images/org.png"></c:if>
					 <c:if test="${loan.specialType == 'olympic'}"><img src="${ctx}/images/olympic.jpg" style="width: 35px; height: 12px;"></c:if>
				 </td>
				 <td>${loan.name}
				 <c:if test="${not empty loan.emailSend and loan.emailSend != '' }"><span style="color:red; font-size:12px;">(${loan.emailSend})</span> </c:if></td>
				 <td>
					 <c:if test="${'天' eq loan.operationType }">天标  | <span style="color:red;">${loan.repayType}</span>
					 	<c:choose>
						 <c:when test="${'是' eq loan.beforeRepay and not empty loan.symbol}"> | 可提前还款 ${loan.symbol}天</c:when>
						 <c:otherwise>
							 | 不可提前还款
						 </c:otherwise>
						 </c:choose>
					 </c:if>
					 <c:if test="${'月' eq loan.operationType }">月标 | <span style="color:red;">${loan.repayType}</span> </c:if>
					 <c:if test="${loan.repayType == '等额本息'}">(可提前还款)</c:if>
				 </td>
				
				 <td>				 
				 <c:if test="${'月' eq loan.operationType }">
							${loan.deadline }个月
						</c:if>
						<c:if test="${'天' eq loan.operationType }">
							<c:choose>
								<c:when test="${'是' eq loan.beforeRepay and not empty loan.symbol}">
									${loan.day + loan.symbol }天
								</c:when>
								<c:otherwise>
									${loan.day }天
								</c:otherwise>
							</c:choose>													
					</c:if>
					<fmt:formatNumber value="${loan.ratePercent }" pattern="#,##0.##"/>%
				 </td>						 								 
				 <td>${loan.borrowMoneyUserID}<br>${loan.borrowMoneyUserName}</td>
				 <td><fmt:formatNumber value="${loan.totalmoney}" type="currency"/></td>
				 <td>${loan.status}<c:if test="${loan.status == '完成' and loan.isBeforeRepay == '1'}"><span style="color:red;">(提前还款)</span></c:if></td>
				 <td><fmt:formatDate value="${loan.commitTime}" type="both"/></td>
				<base:active activeId="19dfc6318d3d43708f4ffedac44b71df">
				 	<td><input type="text" name="sortNum" id= "sortNum_${loan.id}" style="width: 30px" value="${loan.sortNum }">
				 		<input type="button" onclick="editSort('${loan.id}')" value="保存"></input>
				 	</td>
				</base:active>
				 <td>
				 	<a href="${ctx}/invest/investByLoan?loanId=${loan.id}">投资信息</a> | 
				 	<base:active activeId="LOAN_EDIT"><a href="${ctx}/loan/toEditLoanView?loanId=${loan.id}">编辑基本信息</a> | 
				 	<c:if test="${loan.loanType eq '车贷' }">
				 		<a href="${ctx}/loan/toCreateLoanDetailView?param=${loan.id},<fmt:formatDate value="${loan.commitTime}" type="both"/>,vehicle">编辑附加信息</a> | 
				 	</c:if>
				 	<c:if test="${loan.loanType eq '房贷' }">
				 		<a href="${ctx}/loan/toCreateLoanDetailView?param=${loan.id},<fmt:formatDate value="${loan.commitTime}" type="both"/>,house">编辑附加信息</a> | 
				 	</c:if>
				 	<c:if test="${loan.loanType eq '企业贷' }">
				 		<a href="${ctx}/loan/toCreateLoanDetailView?param=${loan.id},<fmt:formatDate value="${loan.commitTime}" type="both"/>,enterprise">编辑附加信息</a> | 
				 	</c:if>
				 	<c:if test="${loan.loanType eq '供应宝' }">
				 		<a href="${ctx}/loan/toCreateLoanDetailView?param=${loan.id},<fmt:formatDate value="${loan.commitTime}" type="both"/>,supplychain">编辑附加信息</a> | 
				 	</c:if>
				 	<c:if test="${loan.loanType eq '金农宝' }">
				 		<a href="${ctx}/loan/toCreateLoanDetailView?param=${loan.id},<fmt:formatDate value="${loan.commitTime}" type="both"/>,ruralfinance">编辑附加信息</a> | 
				 	</c:if></base:active>
				 	<base:active activeId="Loan_Recheck"><a href="${ctx}/loan/toVerifyLoanView?loanId=${loan.id}">复核</a> |</base:active> 
				 	<base:active activeId="Loan_Recheck">
				 		<c:if test="${loan.organizationExclusive eq '是' and loan.status eq '还款中'}">
				 			<a href="javascript:updateOrganizationExclusiveStatus('${loan.id}');" >修改机构专享项目状态</a> |
				 		</c:if>
				 	</base:active> 
				 	<base:active activeId="AutoInvestButton">
				 		<c:if test="${(loan.status eq '筹款中' or loan.status eq '贷前公告') and '月' eq loan.operationType}">		
				 			<c:if test="${loan.specialType != 'olympic'}"><a href="javascript:autoInvest('${loan.id}');"> 自动投标</a> |</c:if><%-- <c:if test="${loan.opendAuto == false}">   --%><%-- </c:if> --%>  
				 		</c:if>
				 	</base:active>
				 	<base:active activeId="loan_del">
				 	<c:if test="${loan.textItem eq '是'}">	
				 	  <a href="#" onClick="delLoan('${loan.id}');">删除</a>|
				 	</c:if>
				  </base:active>
				 	<base:active activeId="LOAN_REPAY"><a href="${ctx}/loan/toRepayView?param=${loan.id}">还款计划</a></base:active>
					<c:if test="${loan.specialType eq 'VIP'}">		
				 			 <a href="/loan/vipList?id=${loan.id }">编辑VIP</a>  
				 	</c:if>
				 </td>
		     </tr>
		 </c:forEach>
         </tbody>          
        </table>       	
        <%@ include file="../base/page.jsp"%>
 </div>
 </div>
 </div>
 </div>
<script type="text/javascript">
function editSort(loanId){
	var sortNum=$("#sortNum_"+loanId).val();
	$.ajax({
		type : "post",
		url : "${ctx}/loan/editSortNum",
		data : {
			"id" : loanId,
			"sortNum":sortNum
		},
		dataType : "text",
		success : function(data) {
			if(data=="success"){
				alert("修改成功");
			}else{
				alert("修改失败");
			}
			
		},
		error : function() {

			console.info("调取失败");
		}
	});
}

function delLoan(loanId){
	if(confirm("确认删除该数据吗?")){
		$.ajax({
			type : 'POST',
			url:"${ctx}/loan/delLoan",		
			data:{
				'loanId':loanId
			},
			beforeSend:function(){
							xval=getBusyOverlay('viewport',
							{color:'blue', opacity:0.5, text:'正在处理...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
							{color:'blue', size:25, type:'o'});	
			},
			error:function(){
				window.clearInterval(xval.ntime); 
				xval.remove();
				status == true;
			},		
			success : function(msg) {				
					window.clearInterval(xval.ntime); 
					xval.remove();
					if(msg == 'ok'){							
						alert("操作成功!");	
						$("#queryButton").click();		
					}else{
						alert(msg);					
					}
			}
		});
	}
}
function updateOrganizationExclusiveStatus(loanId){
	if(confirm("确认修改状态吗?")){
		$.ajax({
			type : 'POST',
			url:"${ctx}/loan/updateOrganizationExclusiveStatus",		
			data:{
				'loanId':loanId
			},
			beforeSend:function(){
							xval=getBusyOverlay('viewport',
							{color:'blue', opacity:0.5, text:'正在处理...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
							{color:'blue', size:25, type:'o'});	
			},
			error:function(){
				window.clearInterval(xval.ntime); 
				xval.remove();
				status == true;
			},		
			success : function(msg) {				
					window.clearInterval(xval.ntime); 
					xval.remove();
					if(msg == 'ok'){							
						alert("操作成功!");	
						location.reload();			
					}else{
						alert(msg);					
					}
			}
		});
	}
}




function autoInvest(loanId){
	var status = true;
	if(status == true){
		if(confirm("确认要开启自动投标吗？")){
			status == false;
			$.ajax({
				type : 'POST',
				url:"${ctx}/invest/autoInvest",		
				data:{
					'loanId':loanId
				},
				beforeSend:function(){
								xval=getBusyOverlay('viewport',
								{color:'blue', opacity:0.5, text:'自动投标中...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
								{color:'blue', size:25, type:'o'});	
				},
				error:function(){
					window.clearInterval(xval.ntime); 
					xval.remove();
					status == true;
				},		
				success : function(msg) {				
						window.clearInterval(xval.ntime); 
						xval.remove();
						if(msg == 'ok'){							
							alert("自动投标成功!");	
							location.reload();			
						}else{
							alert("自动投标失败!");					
						}
					status == true;
				}
			});
		}
	}
}


</script>
 
</section>

</body>
</html>