<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${request.contextPath}" />
<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
<link href="${ctx}/css/custom-styles.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />
<link href="${ctx}/css/base.css" rel="stylesheet" />
<script src="${ctx}/js/jquery-1.10.2.js"></script>
<%-- <script src="${ctx}/js/json.js"></script> --%>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/morris/raphael-2.1.0.min.js"></script>
<script src="${ctx}/js/morris/morris.js"></script>
<script src="${ctx}/js/cvi_busy_lib.js"></script>