<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="images/favicon.png" type="image/png">

  <title>Bracket Responsive Bootstrap3 Admin</title>

  <link href="${pageContext.request.contextPath}/css/style.default.css" rel="stylesheet">

</head>

<body class="notfound">

	
<section>
	<div class="notfoundpanel">
    <h1>${message}</h1>
    <p class="sucesscont">
				<span class="timeClock" id="jumpTo">5</span>秒后自动跳转<br>
				如果没有跳转，请手动<a href="${pageContext.request.contextPath}/index">点击这里跳转</a>
	</p>
    <h3>The page you are looking for has not been found!</h3>
    <h4>The page you are looking for might have been removed, had its name changed, or unavailable. <br />Maybe you could try a search:</h4>
    </div><!-- notfoundpanel -->
</section>

</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	function countDown(secs, surl) {
		var jumpTo = document.getElementById('jumpTo');
		jumpTo.innerHTML = secs;
		if (--secs > 0) {
			setTimeout("countDown(" + secs + ",'" + surl + "')", 1000);
		} else {
			location.href = surl;
		}
	}
</script>
<script type="text/javascript">
	countDown(100, '${pageContext.request.contextPath}/');
</script>
</html>

