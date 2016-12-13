<%@ page language="java" pageEncoding="UTF-8"%>
<ul class="pagination pagination-lg  nomargin pull-right" style="width:760px;">
		<c:if test="${pageInfo.pageNo <= 1 }">
			<li class="disabled"><a href="#"><i class="fa fa-angle-left"></i></a></li>
		</c:if>
		<c:if test="${pageInfo.pageNo > 1 }">
		     <c:if test="${not empty str}">
		   
		        <li><a href="javascript:location=encodeURI(encodeURI('${ctx}${url}?pageNo=${pageInfo.pageNo-1 }${str}'));"><i class="fa fa-angle-left"></i></a></li>
		     </c:if>
		      <c:if test="${empty str}">
		      	<li><a href="${ctx}${url}?pageNo=${pageInfo.pageNo-1 }"><i class="fa fa-angle-left"></i></a></li>
		      </c:if>
		</c:if>
		<c:if test="${(pageInfo.pageNo-3)>0}">
			<c:if test="${not empty str}">
				<li><a href="javascript:location=encodeURI(encodeURI('${ctx}${url}?pageNo=${pageInfo.pageNo-3 }${str}'));">${pageInfo.pageNo-3}</a></li>
			</c:if>
			 <c:if test="${empty str}">
			  <li><a href="${ctx}${url}?pageNo=${pageInfo.pageNo-3 }">${pageInfo.pageNo-3}</a></li>
			 </c:if>
		</c:if>
		<c:if test="${(pageInfo.pageNo-2)>0}">
		    <c:if test="${not empty str}">
		    <li><a href="javascript:location=encodeURI(encodeURI('${ctx}${url}?pageNo=${pageInfo.pageNo-2 }${str}'));">${pageInfo.pageNo-2}</a></li>
			</c:if>
			<c:if test="${empty str}">
			  <li><a href="${ctx}${url}?pageNo=${pageInfo.pageNo-2 }">${pageInfo.pageNo-2}</a></li>
			</c:if>
		</c:if>
		<c:if test="${(pageInfo.pageNo-1)>0}">
		    <c:if test="${not empty str}">
		    <li><a href="javascript:location=encodeURI(encodeURI('${ctx}${url}?pageNo=${pageInfo.pageNo-1 }${str}'));">${pageInfo.pageNo-1}</a></li>
			</c:if>
			<c:if test="${empty str}">
				<li><a href="${ctx}${url}?pageNo=${pageInfo.pageNo-1 }">${pageInfo.pageNo-1}</a></li>
			</c:if>
		</c:if>
	       <li class="active"><a>${pageInfo.pageNo}</a></li>
	     <c:if test="${pageInfo.pageNo + 1  <= pageInfo.totalPage  }">
	        <c:if test="${not empty str}">
	        <li><a href="javascript:location=encodeURI(encodeURI('${ctx}${url}?pageNo=${pageInfo.pageNo+1 }${str}'));">${pageInfo.pageNo+1}</a></li>
			</c:if>
			<c:if test="${empty str}">
			<li><a href="${ctx}${url}?pageNo=${pageInfo.pageNo+1 }">${pageInfo.pageNo+1}</a></li>
			</c:if>
		</c:if>
		<c:if test="${pageInfo.pageNo + 2  <= pageInfo.totalPage  }">
		    <c:if test="${not empty str}">
		    <li><a href="javascript:location=encodeURI(encodeURI('${ctx}${url}?pageNo=${pageInfo.pageNo+2 }${str}'));">${pageInfo.pageNo+2}</a></li>
			</c:if>
			<c:if test="${empty str}">
			<li><a href="${ctx}${url}?pageNo=${pageInfo.pageNo+2 }">${pageInfo.pageNo+2}</a></li>
			</c:if>
		</c:if>
		<c:if test="${pageInfo.pageNo + 3  <= pageInfo.totalPage  }">
		    <c:if test="${not empty str}">
		    <li><a href="javascript:location=encodeURI(encodeURI('${ctx}${url}?pageNo=${pageInfo.pageNo+3 }${str}'));">${pageInfo.pageNo+3}</a></li>
			</c:if>
			<c:if test="${empty str}">
			 <li><a href="${ctx}${url}?pageNo=${pageInfo.pageNo+3 }">${pageInfo.pageNo+3}</a></li>
			</c:if>
		</c:if>
		<c:if test="${pageInfo.pageNo<=(pageInfo.totalPage-1) }">
		 <c:if test="${not empty str}">
		 <li><a href="javascript:location=encodeURI(encodeURI('${ctx}${url}?pageNo=${pageInfo.pageNo+1 }${str}'));"><i class="fa fa-angle-right"></i></a></li>
		</c:if>
		<c:if test="${empty str}">
		<li><a href="${ctx}${url}?pageNo=${pageInfo.pageNo+1 }"><i class="fa fa-angle-right"></i></a></li>
		</c:if>
	   </c:if>
	   <c:if test="${pageInfo.pageNo>=pageInfo.totalPage }">
			 <li class="disabled"><a href="#"><i class="fa fa-angle-right"></i></a></li>
	   </c:if>	
	   <li class="disabled"><a href="javascript:void(0);">第</a></li>
	   <li><a href="javascript:void(0);" style="padding:0; margin:0; border:0px; line-height: 46px;"><input type="text" id="pageNo" style="width:50px; " required/></a></li>
	   <li class="disabled"><a href="javascript:void(0);">页</a></li>
	   <li><a href="javascript:tiaozhuan();">跳转</a></li>	  
	   <li style="width:50px; line-height:50px">共${pageInfo.totalPage }页/${pageInfo.totalRecord }条记录<li>
	   <script type="text/javascript">   
	   	function tiaozhuan(){ 		
	   		var pageNo=document.getElementById("pageNo").value;
	   		if(pageNo == ''){
	   			pageNo=1;
	   		}		   		 		
	   		window.location=encodeURI(encodeURI("${ctx}${url}?pageNo="+pageNo+"${str}"));
	   	}
	   		
	   
	   </script>
 </ul>