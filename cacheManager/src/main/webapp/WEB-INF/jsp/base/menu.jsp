<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!--/. NAV TOP  -->
<nav class="navbar-default navbar-side" role="navigation">
	<div class="sidebar-collapse">
		<ul class="nav menu-font" id="main-menu"></ul>
	</div>
</nav>
<script>
	$.ajax({
				url : "/menus",
				type : "post",
				success : function(data) {
					var menuObject = JSON.parse(data);
					var htmlStr = "";
					for (var i = 0; i < menuObject.length; i++) {
						if (menuObject[i].parentId == 0) {
							htmlStr += "<li><a href='#'><i class='fa fa-sitemap'></i>"
									+ menuObject[i].menuName
									+ "<span class='fa arrow'></span></a><ul class='nav nav-second-level collapse'>";
							var parentMenId = menuObject[i].id;
							for (var j = 0; j < menuObject.length; j++) {
								if (menuObject[j].parentId == parentMenId) {
									htmlStr += "<li><a href='"+menuObject[j].url+"'>"
											+ menuObject[j].menuName
											+ "</a></li>"
								}
							}
							htmlStr += "</ul></li>"
						}
					}
				
					if (htmlStr != "") {
						$("#main-menu").append(htmlStr);
					}
				}
			})
		
			$("#main-menu").delegate("li", "click", function() {
				if ($(this).hasClass("active")) {
					$(this).removeClass("active");
					$(this).find("ul").removeClass("in");
				}else{
					$(this).addClass("active");
					$(this).find("ul").addClass("in");
				}
			});
		
</script>
