<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<ul class="act-newpages" style="list-style:none; padding-left:10px">
	<li style="width:200px;">共${pageInfo.totalPage }页/${pageInfo.totalRecord}条记录 当前第${pageInfo.pageNo}页</li>
	<c:if test="${pageInfo.pageNo > 1 }">
		<li onclick="redirect(${pageInfo.pageNo-1})"><img
			src="${ctx}/images/activity/prev.png" /></li>
	</c:if>
	<c:if test="${pageInfo.pageNo-3 > 3 && pageInfo.totalPage > 7 }">
		<li onclick="redirect(1)">1</li>
		<li onclick="redirect(2)">2</li>
		<li class="act-newpages-oth">&#183;&#183;&#183;</li>
	</c:if>
	<c:choose>
		<c:when test="${pageInfo.totalPage > 7}">
			<c:forEach var="i" step="1"
				begin="${pageInfo.pageNo-3 <= 3 ? 1 : pageInfo.pageNo-3}"
				end="${pageInfo.pageNo+3 < 7 ? 7:pageInfo.pageNo+3 }">
				<c:if test="${i >=1 && i <= pageInfo.totalPage}">
					<li ${i == pageInfo.pageNo ? 'class="cur"':'' }
						onclick="redirect(${i})">${i}</li>
				</c:if>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:forEach var="i" step="1" begin="1" end="${pageInfo.totalPage}">
				<li ${i == pageInfo.pageNo ? 'class="cur"':'' }
					onclick="redirect(${i})">${i}</li>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	<c:if
		test="${pageInfo.pageNo+3 < pageInfo.totalPage && pageInfo.totalPage > 7 }">
		<c:if test="${pageInfo.pageNo+3 != pageInfo.totalPage-1}">
			<li class="act-newpages-oth">&#183;&#183;&#183;</li>
		</c:if>
		<li onclick="redirect(${pageInfo.totalPage })">${pageInfo.totalPage }</li>
	</c:if>
	<c:if test="${pageInfo.pageNo < pageInfo.totalPage }">
		<li onclick="redirect(${pageInfo.pageNo+1})"><img
			src="${ctx}/images/activity/next.png" /></li>
	</c:if>
	<li class="act-newpages-tz">
	<label style="float:left;">跳转到:</label>
	<input value="${pageInfo.pageNo}" class="act-newpages-input" type="type" style="float:left;" id="pageNum" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"
		onkeydown="javascript:if(event.keyCode==13){tiaozhuan();return false;}"/>
	
		<input type="button" value="GO" class="act-newpages-go" onclick="tiaozhuan()" style="float:left;"/></li>

</ul>

<script>
	function redirect(pageNo) {
		pageNo = pageNo < 1 ? 1 : pageNo;
		var url = window.location.href;
		if(url.concat("?")){
			url = url.substring(0, url.indexOf("?"));
		}
		url = url.replace(/pageNo=[^&]*(&)?/g, "pageNo=" + pageNo < 1 ? 1 : pageNo + "&");
		url = url.indexOf("?") > 0 ? url : url + "?pageNo=" + pageNo;
		url = url.indexOf("pageNo") > 0 ? url : url + "&pageNo=" + pageNo;
		$(".form_page").attr("action",url); 
		$(".form_page").submit();
	}
	function tiaozhuan() {
		var pageNum = $("#pageNum").val();
		if (pageNum == '') {
			pageNum = 1;
		} else if (pageNum > ${pageInfo.totalPage}) {
			pageNum = ${pageInfo.totalPage };
		}
		redirect(pageNum);
	}
</script>
