<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../base/base.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>黑名单管理</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../base/header.jsp" />
		<jsp:include page="../base/menu.jsp" />	
		<!-- /. NAV SIDE  -->
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<h2 class="page-header">
							当前位置： <small>黑名单列表</small>
						</h2>
					</div>
				</div>
				
				<!--  搜索部分 -->
				<div class="row">
					<div class="col-md-12">						
						<div class="panel panel-default">
							<div class="panel-heading"></div>
							<div class="panel-body">
								<div class="table-responsive">
									<div class="row">
										<div class="col-sm-12 form-inline">									
											<label>访问错误次数： </label>
											<div class="form-group ">
		                                    	<input type="text" id="reqNum" class="form-control" placeholder="content" style="width:160px">		                                           
                                        	</div>  
								        	<button class="btn btn-success form-control" onclick="javascript:reqCount();" style="margin-left:26px"><i class="fa fa-search"></i></button>			
								        	<button class="btn btn-success form-control" onclick="javascript:clearCount();" style="margin-left:26px"><i class="glyphicon glyphicon-trash"></i></button>				                										
										</div>
										<div style="margin-left: 16px;">次数:<lable id="count" style="color:red;"></lable></div>
									
										<div class="col-sm-12" style="margin-top:40px;">
											<form role="form" class="form-inline form_page" action="${ctx}/sys/toBlackManager" method="post">
												<div class="form-group">
		                                           	<input type="text" name="content"  id="content" class="form-control" placeholder="content" value="${black.content}" style="width:160px">		                                           
                                        		</div>  
                                        		<label class="lable-inline">黑名单类型 ：</label>  
                                        		<div class="form-group" style="width: 110px;">                                         			      	           
								                    <select id="type" name="type" class="form-control" placeholder="缓存类型">     
								                        <option value="">ALL</option>   
								                    	<option value="IP" <c:if test="${black.type eq 'IP' }">selected</c:if>>IP</option>
								                    	<option value="MOBILE_NO" <c:if test="${black.type eq 'MOBILE_NO' }">selected</c:if>>MOBILE_NO</option>								                   
								                    </select>              
								                </div>
								                
								                <button class="btn btn-success form-control" type="submit" style="margin-left:26px"><i class="fa fa-search"></i></button>							                
											</form>
										</div>
									</div>
									
									<!-- 添加按钮 -->
									<div class="row">
										<div class="col-sm-12">
										    <a class="btn btn-link" href="${ctx}/sys/toAddblack" style="float:right; font-size:15px;"><i class="fa fa-plus"></i>添加黑名单</a>
											<a class="btn btn-link" href="javascript:init();" style="float:right; font-size:15px;"><i class="glyphicon glyphicon-hand-left"></i>初始化黑名单缓存</a>							                
										</div>
									</div>
									
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th style="width:6%">id</th>
												<th style="width:12%">黑名单</th>
												<th style="width:10%">类型</th>
												<th>说明</th>											
												<th style="width:18%">添加时间</th>												
												<th style="width:10%">操作</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="black" items="${pageInfo.results}">
											<tr class="odd gradeX">
												<td>${black.id}</td>
												<td>${black.content}</td>
												<td>${black.type}</td>
												<td>${black.info}</td>
												<td><fmt:formatDate value="${black.createTime}" type="both" /></td>										
												<td><a href="javascript:del('${black.id}', '${black.content}')">删除</a></td>
											</tr>
										</c:forEach>
										</tbody>
									</table>				
									<div class="row">
										<jsp:include page="../base/pageInfo.jsp"/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>				
			</div>
		</div>
		<footer></footer>
	</div>
	</div>
	<script type="text/javascript">
	
	function del(id,  content){
		if(confirm("确认删除吗？")){
			$.ajax({						
				type : "POST",
				url : "${ctx}/sys/black/del",
				data : {id:id,
						content:content
				},
				async : false,						
				error:function(e){
					alert(e);
				},
				success : function(data) {
					if(data == "success"){
						alert("删除成功！");
						location = '${ctx}/sys/toBlackManager';
					}else{
						alert("删除失败！");
					}
				}
			});
		}
	}
	
	function clearCount(){
		if(confirm("确认清除错误访问次数吗？")){
			$("#count").html("");
			$.ajax({						
				type : "POST",
				url : "${ctx}/sys/black/clear",
				data : {
					content:$("#reqNum").val()
				},					
				error:function(e){
					alert(e);
				},
				success : function(data) {
					alert(data)
				}
			});
		}
	}
	
	function reqCount(){
		$("#count").html("");
		$.ajax({						
			type : "POST",
			url : "${ctx}/sys/black/req",
			data : {
				content:$("#reqNum").val()
			},				
			error:function(e){
				alert(e);
			},
			success : function(data) {
			alert(data)
				$("#count").html(data);
			}
		});
	}

	function init(){
	
		$.ajax({type : "POST",
				url : "${ctx}/sys/black/init",
				beforeSend:function(){
				xval=getBusyOverlay('viewport',
					{color:'blue', opacity:0.5, text:'正在初始化黑名单缓存...', style:'text-shadow: 0 0 3px black;font-size:18px;'},
					{color:'blue', size:25, type:'o'});	
				},	
				error:function(e){
					window.clearInterval(xval.ntime); 
					xval.remove();
					alert(e);
				},
				success : function(data) {
					window.clearInterval(xval.ntime); 
					xval.remove();
					alert(data);
				}
		});
	}
</script>
</body>
</html>
