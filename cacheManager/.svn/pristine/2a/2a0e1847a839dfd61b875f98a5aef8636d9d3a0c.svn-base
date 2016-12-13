<%@ page language="java" pageEncoding="UTF-8"%>
<nav class="navbar navbar-default top-navbar" role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".sidebar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="index.html"> <strong><img
				src="/images/logo.png" alt=""><label style="padding-left:6px;">--缓存管理系统
			</label></strong></a>
	</div>

	<ul class="nav navbar-top-links navbar-right">
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#" aria-expanded="false"> <i
				class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#"><i class="fa fa-user fa-fw"></i> User
						Profile</a></li>
			
			</ul></li>
		<!-- /.dropdown -->
	</ul>
</nav>

<script>
	(function() {

		// Menu Toggle
		jQuery('.toggle-btn').click(function() {
			$(".left-side").getNiceScroll().hide();

			if ($('body').hasClass('left-side-collapsed')) {
				$(".left-side").getNiceScroll().hide();
			}
			var body = jQuery('body');
			var bodyposition = body.css('position');

			if (bodyposition != 'relative') {

				if (!body.hasClass('left-side-collapsed')) {
					body.addClass('left-side-collapsed');
					jQuery('.custom-nav ul').attr('style', '');

					jQuery(this).addClass('menu-collapsed');

				} else {
					body.removeClass('left-side-collapsed chat-view');
					jQuery('.custom-nav li.active ul').css({
						display : 'block'
					});

					jQuery(this).removeClass('menu-collapsed');

				}
			} else {

				if (body.hasClass('left-side-show'))
					body.removeClass('left-side-show');
				else
					body.addClass('left-side-show');

				mainContentHeightAdjust();
			}

		});
	})(jQuery);
</script>
