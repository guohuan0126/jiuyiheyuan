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
<link href='${ctx}/ueditor/themes/default/css/ueditor.min.css' type="text/css" rel="stylesheet" >
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>  
<!-- 编辑器源码文件 -->  
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>  
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) 此处可要可不要 -->   
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/js/validate.js"></script>
<title>创建纸质优惠券</title>
</head>
<body>	
	<div id="preloader">
		<div id="status">
			<i class="fa fa-spinner fa-spin"></i>
		</div>
	</div>
	<section>
		<%@include file="../base/leftMenu.jsp"%>
		<div class="mainpanel">
			<%@include file="../base/header.jsp"%>
			<div class="pageheader">
				<h2>
					<i class="fa fa-pencil"></i>创建纸质优惠券
				</h2>				
			</div>
			<div class="contentpanel">
				<form id="form" method="post" class="form-horizontal">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="panel-btns"></div>
							<h5 class="panel-title">创建纸质优惠券</h5>
						</div>
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="asterisk"></span></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">类型 </label>
								<div class="col-sm-3">
									<select name="type" id="type" class="form-control" style="width:50%;">
										<option value="1">现金</option>
										<option value="2">加息券</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">金额 </label>
								<div class="col-sm-3">
									<select name="money" id="money" class="form-control" style="width:50%;">
										<option value="18">18元</option>
										<option value="68">68元</option>
										<option value="88">88元</option>
										<option value="188">188元</option>
										<option value="200">200元</option>
										<option value="600">600元</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">生成次数 </label>
								<div class="col-sm-3">
									<input style="width: 191px" id="num"/>
								</div>
						<!-- panel-body -->
					</div>
					<div class="form-group">
								<label class="col-sm-3 control-label">备注 </label>
								<div class="col-sm-3">
									<input style="width: 191px;height: 66px;" id="remark"/>
								</div>
							</div>	
					<div class="panel-footer">
							<div class="row">
								<div class="col-sm-9 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="addRedpacketCoupon()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<script type="text/javascript">
		function addRedpacketCoupon(){
			var type=$("#type").val();	
			var money=$("#money").val();
			var num=$("#num").val();
			
			$.ajax({
					type : 'POST',
					dataType:'text',
					url : "/redPacketDetail/addRedpacketCoupon",						
					data: {
						"type":type,
						"money":money,
						"num":num
					},
					success:function(data) {
					
						if(data=='error'){
							alert("添加失败");
						}else{
							alert("添加成功");
							window.location.href="/redPacketDetail/redpacketCouponList";
						}
					}
				});		
			}
		</script>
	</section>
</body>
</html>