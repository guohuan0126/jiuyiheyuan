<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<style type="text/css">
.act-newpages{text-align:center;margin:10px auto 35px;overflow:hidden;}
.act-newpages li{width:36px;height:36px;line-height:36px;/*display:inline-block;*/float:left;color:#7f7f7f;font-size:16px;
border:1px solid #d6d6d6;text-align:center;margin-left:6px;color:#7f7f7f;cursor:pointer;-moz-border-radius:2px;-webkit-border-radius:2px;border-radius:2px;background-color:#fff;/*padding:4px 12px;*/}
.act-newpages li img{display:block;margin:11px auto;}
.act-newpages li.cur{background-color:#3da1e8;}
.act-newpages li:hover{border:1px solid #57a4ff;}
.act-newpages li:hover{color:#57a4ff;}
.act-newpages li.cur:hover{background-color:#42a9f2;}
.act-newpages li.cur{color:#fff;}
.act-newpages li.act-newpages-tz{width:160px;border:none;background-color:transparent;}
.act-newpages li.act-newpages-tz:hover{color:#7f7f7f;}
.act-newpages li.act-newpages-tz label{font-size:14px;}
.act-newpages-input{display:inline-block;width:48px;height:16px;padding:9px 0;border:1px solid #d6d6d6;-moz-border-radius:2px;
-webkit-border-radius:2px;border-radius:2px;margin:0 10px 0 3px;color:#7f7f7f;text-align:center;}
.act-newpages-go{display:inline-block;border:1px solid #d6d6d6;width:36px;height:36px;line-height:36px;font-size:14px;color:#7f7f7f;-moz-border-radius:2px;
-webkit-border-radius:2px;border-radius:2px;background-color:#fff;font-weight:bold;cursor:pointer;/*padding:3px 5px;*/}
.act-newpages li.act-newpages-oth{border:none;width:13px; background-color:transparent;padding:0;}
.act-newpages li.act-newpages-oth:hover{color:#7f7f7f;}

</style>

<c:if test="${pageInfo.totalRecord > 0 || true}">
   <ul class="act-newpages">
     <c:if test="${pageInfo.pageNo > 1 }">
        <li onclick="redirect(${pageInfo.pageNo-1})"><img src="/../images/activity/prev.png" /></li>
     </c:if>
     <c:if test="${pageInfo.pageNo-3 > 3 && pageInfo.totalPage > 7 }">
           <li onclick="redirect(1)">1</li>
           <li onclick="redirect(2)">2</li>
           <li class="act-newpages-oth">&#183;&#183;&#183; </li>
     </c:if>
	<c:choose>
	    <c:when test="${pageInfo.totalPage > 7}">
            <c:forEach var="i" step="1" begin="${pageInfo.pageNo-3 <= 3 ? 1 : pageInfo.pageNo-3}" 
			    end="${pageInfo.pageNo+3 < 7 ? 7:pageInfo.pageNo+3 }" >
			      <c:if test="${i >=1 && i <= pageInfo.totalPage}">
			         <li ${i == pageInfo.pageNo ? 'class="cur"':'' } onclick="redirect(${i})">${i}</li>
			      </c:if>
			 </c:forEach>
	    </c:when>
	    <c:otherwise>
		    <c:forEach var="i" step="1" begin="1" end="${pageInfo.totalPage}" >
		         <li ${i == pageInfo.pageNo ? 'class="cur"':'' } onclick="redirect(${i})">${i}</li>
		   </c:forEach>
	    </c:otherwise>
	</c:choose>

	   <c:if test="${pageInfo.pageNo+3 < pageInfo.totalPage && pageInfo.totalPage > 7 }">
	         <c:if test="${pageInfo.pageNo+3 != pageInfo.totalPage-1}">
	            <li class="act-newpages-oth">&#183;&#183;&#183; </li>
	         </c:if>
             <li onclick="redirect(${pageInfo.totalPage })" >${pageInfo.totalPage }</li>
       </c:if>
	 <c:if test="${pageInfo.pageNo < pageInfo.totalPage }">
	        <li onclick="redirect(${pageInfo.pageNo+1})"><img src="/../images/activity/next.png" /></li>
	 </c:if>
	  <li class="act-newpages-tz"><label>跳转到:</label><input id="pageNum" type="text" value="${pageInfo.pageNo}" class="act-newpages-input" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" onkeydown="javascript:if(event.keyCode==13){tiaozhuan();return false;}"/><input type="button" value="GO" class="act-newpages-go" onclick="tiaozhuan()"/></li>
   </ul> 
   
   <script>
   
   function redirect(pageNo){
	   var url = window.location.href;
	   url = url.replace(/pageNo=[^&]*(&)?/g,"pageNo="+pageNo+"&");
	   url = url.indexOf("?") > 0 ? url : url+"?pageNo="+pageNo;
	   url = url.indexOf("pageNo") > 0 ? url : url+"&pageNo="+pageNo;
	   window.location.href = url;
   }
   function tiaozhuan(){
	   var pageNum = $("#pageNum").val();
	   if(pageNum == ''){
		   pageNum=1;
  		}else if(pageNum >${pageInfo.totalPage }){
  			pageNum =${pageInfo.totalPage };
  		}
	   redirect(pageNum);
   }
   </script>
  </c:if>
