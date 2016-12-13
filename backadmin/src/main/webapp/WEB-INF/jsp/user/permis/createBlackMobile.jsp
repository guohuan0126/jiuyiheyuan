<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="${ctx}/css/flow/flow.css" rel="stylesheet" />
<link rel="shortcut icon" href="images/favicon.png" type="image/png">
<title>添加IP</title>	
</head>
<body>
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../../base/leftMenu.jsp"%>
		<div class="mainpanel">
			<%@include file="../../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-pencil"></i> 添加手机号
				</h2>
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h4 class="panel-title">添加手机号</h4>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label">添加手机号</label>
								<div class="col-sm-3">
									<a style="font-size:30px;cursor:pointer;" onclick="operationInput( 'add' )">+</a>&nbsp;&nbsp;&nbsp;
									<a id="del" style="font-size:30px;cursor:pointer;" onclick="operationInput( 'del' )">-</a>
								</div>
							</div>
						</div>
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button"
										onclick="sub()">保存</button>
									<button type="reset" class="btn btn-default">重置</button>
								</div>
							</div>
						</div>

					</div>
					<!-- panel -->
				</form>
			</div>
			<!-- contentpanel -->
		</div>
		<!-- mainpanel -->
	</section>
	
	<script type="text/javascript">
		var num = 1;
		
		/* 添加节点  */
		function operationInput( ope ) {
			if(ope == "add"){		
				$str = '';
				$str += "<div class='form-group'><label class='col-sm-3 control-label'>手机号：</label><div class='col-sm-3'>";
				$str += "<input type='text' name='ip' id='ip"+num+"' class='form-control' placeholder='手机号不能为空'/><div id='errorIp"+num+"' style='display: none;'>手机号不能为空</div>";
				$str += "</div></div>";
				$('.panel-body').append($str);
				num++;
			}else if(ope == "del"){
				if(num > 1){
					$('.panel-body div.form-group:last').remove();
					num--;
				}
			}
			
		}	
		
		
		function sub(){
			var status = "";
			for(var i = 1; i<num; i++ ){
				$("#errorIp"+i).attr("style", "display:none;");
			}						
			for(var i = 1; i<num; i++){
				var ip = $("#ip"+i).val();
				if(ip == ''){
					$("#errorIp"+i).attr("style", "display:block;font-size:14px;color:red;");
					return false;
				}else if(!ismobile(ip)){
					$("#errorIp"+i).text("手机号格式不正确");
					$("#errorIp"+i).attr("style", "display:block;font-size:14px;color:red;");
					return false;
					
				}		
			}			
			
			$.ajax({
				type : 'POST',
				url : '${cxf}/permis/black/madd',
				data:$('#form').serialize(),
				dataType:"json",
				success : function(msg) {
					if(msg.status == 'ok'){
						alert(msg.info);
						window.location = "${cxf}/permis/black/mlist";
					}else{
						alert(msg.info);
					}
				}
			});
		};
		
		function ismobile(strMobile) {
			
			var re=/^1\d{10}$/gi;  
			if(re.test(strMobile)){			
				return true;
			
			}
			return false;
		}
	</script>

</body>
</html>
