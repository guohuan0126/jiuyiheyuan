<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="base/base.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>缓存key管理</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="base/header.jsp" />
		<jsp:include page="base/menu.jsp" />	
		<!-- /. NAV SIDE  -->
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<h2 class="page-header">
							当前位置： <small>缓存key列表</small>
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
										<div class="col-sm-12">
											<form role="form" class="form-inline form_page" action="${ctx}/cache/toCacheKeyManager" method="post">
												<div class="form-group">
		                                           	<input type="text" name="key"  id="key" class="form-control" placeholder="key" value="${cacheKey.key}" style="width:160px">		                                           
                                        		</div>  
                                        		<label class="lable-inline">缓存类型 ：</label>  
                                        		<div class="form-group" style="width: 110px;">                                         			      	           
								                    <select id="type" name="type" class="form-control" required placeholder="缓存类型">            
								                      <option value="0" <c:if test="${cacheKey.type eq '0' }">selected</c:if>>all</option>
								                      <option value="1" <c:if test="${cacheKey.type eq '1' }">selected</c:if>>string</option>
								                      <option value="2" <c:if test="${cacheKey.type eq '2' }">selected</c:if>>hash</option>
								                      <option value="3" <c:if test="${cacheKey.type eq '3' }">selected</c:if>>list</option>
								                      <option value="4" <c:if test="${cacheKey.type eq '4' }">selected</c:if>>set</option>
								                      <option value="5" <c:if test="${cacheKey.type eq '5' }">selected</c:if>>sortedSet</option>                   
								                    </select>              
								                </div>
								                <label class="lable-inline">系统 ：</label>
									            <div class="form-group" style="width: 110px;">              	           
								                    <select id="domain" name="domain" class="form-control" required data-placeholder="系统">
								                      <option value="0" <c:if test="${cacheKey.domain eq '0' }">selected</c:if>>all</option>
								                      <option value="1" <c:if test="${cacheKey.domain eq '1' }">selected</c:if>>pc</option>
								                      <option value="2" <c:if test="${cacheKey.domain eq '2' }">selected</c:if>>m</option>
								                      <option value="3" <c:if test="${cacheKey.domain eq '3' }">selected</c:if>>soa-app</option>
								                      <option value="4" <c:if test="${cacheKey.domain eq '4' }">selected</c:if>>soa-yeepay</option>
								                      <option value="5" <c:if test="${cacheKey.domain eq '5' }">selected</c:if>>soa-pc</option>       
								                      <option value="6" <c:if test="${cacheKey.domain eq '6' }">selected</c:if>>admin</option>
								                      <option value="7" <c:if test="${cacheKey.domain eq '7' }">selected</c:if>>cps</option>                   
								                    </select>              
								                </div>
								                <button class="btn btn-success form-control" type="submit" style="margin-left:26px"><i class="fa fa-search"></i></button>							                
											</form>
										</div>
									</div>
									
									<!-- 添加按钮 -->
									<div class="row">
										<div class="col-sm-12">
											<a class="btn btn-link" href="${ctx}/cache/toAddCacheKey" style="float:right; font-size:15px;"><i class="fa fa-plus"></i> 添加key</a>							                
										</div>
									</div>
									
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th style="width:8%">key</th>
												<th style="width:6%">类型</th>
												<th style="width:6%">系统</th>
												<th>说明</th>
												<th>描述</th>
												<!-- <th>申请人</th> -->
												<th style="width:12%">创建人/创建时间</th>
												<th style="width:18%">最后更新人/最后更新时间</th>									
												<th style="width:8%">备注</th>
												<th style="width:10%">操作</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="cache" items="${pageInfo.results}">
											<tr class="odd gradeX">
												<td>${cache.key}</td>
										<td><c:if test="${cache.type == 1}">string</c:if> <c:if
												test="${cache.type == 2}">hash</c:if> <c:if
												test="${cache.type == 3}">list</c:if> <c:if
												test="${cache.type == 4}">set</c:if> <c:if
												test="${cache.type == 5}">sortedSet</c:if></td>
										<td><c:if test="${cache.domain == 1}">pc</c:if> <c:if
												test="${cache.domain == 2}">m</c:if> <c:if
												test="${cache.domain == 3}">soa-app</c:if> <c:if
												test="${cache.domain == 4}">soa-yeepay</c:if> <c:if
												test="${cache.domain == 5}">soa-pc</c:if> <c:if
												test="${cache.domain == 6}">admin</c:if> <c:if
												test="${cache.domain == 7}">cps</c:if></td>
										<td>${cache.info}</td>
										<td>${cache.descrip}</td>
										<%-- <td>${cache.applicant}</td> --%>
										<td>${cache.createUser}<br>
											<fmt:formatDate value="${cache.createTime}" type="both" /></td>										
										<td>${cache.updateUser}<br>
										<fmt:formatDate value="${cache.updateTime}"
												type="both" /></td>									
										<td>${cache.remark}</td>
										<td><a href="${ctx}/cache/toAddCacheKey?id=${cache.id}">编辑</a>
											| <a href="javascript:del('${cache.id}', '${cache.key}')">删除</a>
											| <a href="javascript:clear('${cache.key}')">清空</a>
										</td>
										</tr>
										</c:forEach>
										</tbody>
									</table>				
									<div class="row">
										<jsp:include page="base/pageInfo.jsp"/>
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
	function del(id,  key){
		if(confirm("确认删除吗？")){
			$.ajax({						
				type : "POST",
				url : "${ctx}/cache/cacheKey/del",
				data : {id:id,
						key:key
				},
				async : false,						
				error:function(e){
					alert(e);
				},
				success : function(data) {
					if(data == "ok"){
						location = '${ctx}/cache/toCacheKeyManager';
					}else{
						alert("删除失败！");
					}
				}
			});
		}
	}
	
	function clear(key){
		if(confirm("确认清空key吗？")){
			$.ajax({						
				type : "POST",
				url : "${ctx}/cache/cacheManager/del",
				data : {
					key:key,															
					type : 1,			
				},
				async : false,						
				error:function(e){
					alert(e);
				},
				success : function(data) {				
					alert("已清除！");				
				}
			});
		}
	}
</script>
</body>
</html>
