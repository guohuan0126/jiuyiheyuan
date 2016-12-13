<%@ page language="java" pageEncoding="UTF-8"%>
       <div class="table-pages">
								<span class="pages-left">共有${pageInfo.totalRecord}条，每页显示  ${pageInfo.pageSize}条，当前页:${pageInfo.pageNo}</span><!-- <select
									class="pages-sec">
									<option value="1">20条</option>
									<option value="2">30</option>
									<option value="3">50</option>
									<option value="4">100条</option>
								</select> --> 
								<span class="pages-all">
								<c:if test="${pageInfo.hasPreviousPage}">
									<a href="javascript:void(0);" style="width:100px;" onclick="subForm('${pageInfo.pageNo-1 }');">上一页</a>
								</c:if>  
								 
								<c:if test="${pageInfo.hasNextPage}">
									<a href="javascript:void(0);" style="width:100px;"  onclick="subForm('${pageInfo.pageNo+1 }');">下一页</a>
								</c:if>
								<input type="hidden"  name="pageNo" id="pageNo" style="width:50px; " />
								</span> 
							
								<!-- <span class="pages-right"> <input type="text"
									class="input-txt"><input type="button" value="GO"
									class="input-but">
								</span> -->
								
								
							</div>
	   <script type="text/javascript">   
	   //表单提交
	   function subForm(pageNo){
		   
		   document.getElementById("pageNo").value=pageNo;
		   $("#f1").submit();
	   }
	   /**
	   	*	分页页面跳转  
	   	*	使用：定义form id为f1
	    */
	   </script>
 </ul>