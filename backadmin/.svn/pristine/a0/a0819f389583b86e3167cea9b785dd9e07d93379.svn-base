<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div id="pics">
	<table>
		<tbody>
			<tr>
				<td>借款合同:<a
					href="${pageContext.request.contextPath}${loan.contract}"
					target="_blank">${loan.contractName }</a>
				</td>
			</tr>
		</tbody>
	</table>
	<table>
		<thead>
			<tr>
				<th width="20%">图片</th>
				<th width="20%">位置</th>
				<th width="20%">名称</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pics}" var="pic">
				<tr>
					<td><img
						src="${pageContext.request.contextPath}${pic.picture}" width="220"
						height="140" alt="" /></td>
					<td><input style="width: 60px" type="text" id="${pic.id}"
						value="${pic.seqNum}" onchange="updateImageSeqNum('${pic.id}')" /></td>
					<td>${pic.title }</td>
					<td><a onclick="updateImage('del','${pic.id}')">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>


