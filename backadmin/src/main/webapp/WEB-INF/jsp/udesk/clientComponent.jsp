<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>客服呼叫</title>
<style>
html,body,header,footer,nav,article,section,aside,time,code,div,p,ul,ol,li,dl,dd,dt,h1,h2,h3,a,span,strong,em,small,form,label,input,select,textarea{margin:0;padding:0;font-weight:normal;list-style:none;}
article,aside,audio,footer,header,nav,section,video{display:block;}
:focus{outline:0;}
em{font-style:normal;}
ol,ul{list-style:none;}
img{display:block;}
input{border-radius:0;/*-webkit-appearance:none;*/border:0;list-style:none;}
body{font-family:Microsoft YaHei,sans-serif;color:#333;background:#fff;}
input:focus {
     outline:none;
}
a,a:active{ text-decoration:none;}
input[type="button"], input[type="submit"], input[type="reset"] {
-webkit-appearance: none;
}
/* 禁用iPhone中Safari的字号自动调整 */
html { -webkit-text-size-adjust: none;height:100%;}
.hujiao-con{max-width:1160px;border:1px solid #ccc;padding:20px;}
.hujiao-top{width:100%;overflow:hidden;border-bottom:2px dashed #ebebeb;padding-bottom:10px;}
/*@media (min-width: 557px) and (max-width: 800px){
	.hujiao-top li{font-size:14px;}
	}
@media (min-width: 447px) and (max-width: 556px){
	.hujiao-top li{font-size:13px;}
	}*/	
.hujiao-top li{float:left;height:30px;line-height:30px;font-size:13px;width:25%;color:#666;}
/*.hujiao-top li:nth-child(odd){width:30%;}
.hujiao-top li:nth-child(even){width:70%;}*/ 
.hujiao-top li span.right{color:#000;}
.hujiao-bottom{padding-top:10px;}
.bottom-tit{height:40px;line-height:40px;width:100%;}
.bottom-tit li{display:inline-block;cursor:pointer;margin:0 20px 0 7px;font-size:18px;color:#666;}
.bottom-tit li:last-child{margin-left:13px;}
.bottom-tit li.cur{color:#00a1e9;}
.bottom-con{width:1150px;margin:0 auto;}
.bottom-part1 span,.bottom-part2 input,.bottom-part2 select{display:inline-block;margin:5px 50px 5px 0;color:#333;}
.bottom-part2 input,.bottom-part2 select{border:1px solid #999;color:#999;height:32px;line-height:32px;margin-top:20px;}
.bottom-part2 input.box-dif,.bottom-part5 input.last-p{background:#00a1e9;border:0;color:#fff;padding:0 30px;cursor:pointer;}
.bottom-part3 table{border:1px solid #999;border-bottom:0;border-right:0;margin-top:20px;}
.bottom-part3 table th,.bottom-part3 table td{font-size:13px;padding:10px 0;text-align:center;border:1px solid #999;border-top:0;border-left:0;}
.bottom-part4 label{margin-right:30px;}
.bottom-part4 p{padding-top:10px;}
.bottom-part5 p{margin-top:15px;display:inline-block;width:30%;}
.bottom-part5 p.dif-p{position:relative;height:50px;}
.bottom-part5 p span,.bottom-part5 p input,.bottom-part5 p textarea{display:inline-block;}
.bottom-part5 p span{width:90px;text-align:right;padding-right:10px;}
.bottom-part5 p input{border:1px solid #999;border-radius:4px;height:30px;line-height:30px;}
.bottom-part5 p.dif-p{display:block;}
.bottom-part5 p.dif-p span{position:absolute;top:0;left:0;}
.bottom-part5 p textarea{width:520px;height:50px;resize: none;border-radius:4px;position:absolute;top:0;left:100px;}
.bottom-part5 input.last-p{margin-top:15px;}
.bottom-part6{margin-top:15px;}
.bottom-part6 table th{background:#ccc;}
.bottom-part6 table th,.bottom-part6 table td{text-align:left;height:40px;line-height:40px;padding-left:5px;}
.bottom-part6 table tr td:last-child{color:#00a1e9;}
.bottom-part3 table tr td:last-child{position:relative;cursor:pointer;}
.xuanfu{position:absolute;top:34px;left:-151px;padding:10px;border-radius:4px;border:1px solid #F3B582;background:#fff;width:200px;z-index:9999;}
.xuanfu b{display:block;font-weight:normal;color:#666;line-height:20px;}
.arrow{ position:absolute; width:14px; height:7px; top:-7px; right:26px;}
.arrow *{ position:absolute;}
.arrow em{ width: 0;
    height: 0;
    border-left: 7px solid transparent;
    border-right: 7px solid transparent;
    border-bottom: 7px solid #F3B582;}
.arrow span{position:absolute;width: 0;
    height: 0;
    border-left: 7px solid transparent;
    border-right: 7px solid transparent;
    border-bottom: 7px solid #fff; top:1px; left:7px;}

  
</style>

<link type="text/css" href="${ctx}/css/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/css/jquery-ui-timepicker-addon.css" rel="stylesheet" /> 
<script src="/js/jquery-2.1.1.js"></script>
<!-- <script src="/js/Tooltip/js/jquery/jquery.js"></script> -->
<script src="/js/Tooltip/js/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="/js/Tooltip/css/jquery-ui.css">
<script>
jQuery(document).ready(function(){					
	 jQuery('#datepicker').datepicker({dateFormat: 'yy-mm-dd'});				
	jQuery('#datepicker1').datepicker({dateFormat: 'yy-mm-dd'}); 
	func();
});
	//标签切换
	function tabs(id, cur, s) {
		var content = "_main_";
		if (jQuery("#" + id).length) {
			function closeContent(id, length) {
				for (var i = 1; i <= length; i++) {
					jQuery("#" + id + content + i).hide();
				}
			}
			var obj = jQuery("#" + id + "  " + s);
			var length = obj.length;
			obj.each(function(i) {
				jQuery(this).hover(function() {
					obj.removeClass(cur);
					closeContent(id, length);
					jQuery(this).addClass(cur);
					jQuery("#" + id + content + (i + 1)).show();
				});
			});
		}//end length
	}
	
</script>
</head>

<body>
	<div class="hujiao-con">
		<ul class="hujiao-top">
		<c:forEach items="${pageInfo.results}" var="item">
			<li><span class="left">姓名：</span><span class="right">${item.realname }</span></li>
			<li><span class="left">身份证号码：</span><span class="right">${item.idCard }</span></li>
			<li><span class="left">归属地：</span><span class="right">${item.phoneNoCity}</span></li>
			<li><span class="left">手机号码：</span><span class="right">${item.mobileNumber }</span></li>
			<li><span class="left">状态：</span><span class="right">
			<c:choose>
			<c:when test="${item.status eq '1'}">
                   		 正常
                   </c:when>
			<c:when test="${item.status eq '0'}">
                   		 禁用
                   </c:when>
			<c:otherwise>
                    	${item.status}
             </c:otherwise>
		  </c:choose>
          </span></li>
			<li><span class="left">注册时间：</span><span class="right">
			<fmt:formatDate value="${item.registerTime }" pattern="yyyy-MM-dd HH:mm" /></span></li>
			<li><span class="left">用户类型：</span><span class="right">
			<c:if test="${item.userInterior == 'duanrongw' }">内部员工</c:if>
		    <c:if test="${item.userInterior == 'furen' }">辅仁客户</c:if> 
		    <c:if test="${item.userInterior == 'nelson' }">离职员工</c:if>
            </span></li>
			<li><span class="left">实名认证：</span><span class="right">
			<c:choose>
			<c:when test="${item.authenname eq '实名认证'}">
				已认证
			</c:when>
			<c:otherwise>
				未认证
			</c:otherwise>
		  </c:choose></span></li>
			<li><span class="left">来源：</span><span class="right">${item.userOtherInfo.userSource }</span></li>
			<li><span class="left">成为大客户时间：</span><span class="right">
			<fmt:formatDate value="${item.investTime }" pattern="yyyy-MM-dd HH:mm" /></span></li>
			<li><span class="left">经理推荐人：</span><span class="right">${item.oreferrer}</span></li>
			<li><span class="left">推荐人：</span><span class="right">${item.referrer}</span></li>
		</c:forEach>
		</ul>
		<div class="hujiao-bottom">
			<ul class="bottom-tit" id="news_title1">
				<li class="cur">历史投资记录</li>|
				<li>用户备注</li>
			</ul>
			<div class="bottom-con" id="news_title1_main_1">
				<div class="bottom-part1">
					<span>当前投资总额：￥<fmt:formatNumber value="${user.totalCurrMoney}"
								maxFractionDigits="2" /></span> <span>历史投资总额：￥<fmt:formatNumber value="${user.totalMoney}"
								maxFractionDigits="2" /></span> <span>投资次数：<c:forEach items="${pageInfo.results}" var="item">
					${item.investNum}次
					</c:forEach></span>
					<span>当前天天赚投资总额：￥<fmt:formatNumber value="${demandMoney}"
								maxFractionDigits="2" /></span>
				</div>
				<form class="form-inline"  action="${postUrl}" method="post">	
				<div class="bottom-part2">
					<input name="loanName" placeholder="项目名称" value="${loanName}"/>    <input type="text"  name="startTime" value="${startTime}" placeholder="开始时间" id="datepicker" >               
                 -- 
                <input type="text"  name="endTime" value="${endTime}" placeholder="结束时间" id="datepicker1" >
    
                 <select id="status" name="status" class="select" >
                     	<c:if test="${status != null && status != '' }">
                      		<option value="${status}">${status}</option>
                     	</c:if>
                      <option value="">请选择...</option>                
                      <option value="还款中">还款中</option>
                      <option value="投标成功">投标成功</option>
                      <option value="完成">完成</option>
                      <option value="流标">流标</option>                   
                    </select>              
				 <input value="查询" type="submit" class="box-dif" />
				</div>
				</form>
				<div class="bottom-part3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th width="19%" scope="col">项目名称</th>
						    <th width="11%" scope="col">投资周期</th>
						    <th width="13%" scope="col">投资本金</th>
						    <th width="12%" scope="col">年化利率</th>
						    <th width="18%" scope="col">投资时间</th>
						    <th width="15%" scope="col">投资状态</th>
						    <th width="12%" scope="col">加息券</th>
						</tr>
					</table>
					<div style="height:300px;overflow-y:scroll;width:1167px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="margin-top:0;border-top:0;">
							 <c:forEach  var="invest" items="${investList}">
							<tr>
								<td width="19%">${invest.loan.name }</td>
								<td width="11%">
								<c:if test="${'月' eq invest.loan.operationType }">
								${invest.loan.deadline }个月
						        </c:if>
						        <c:if test="${'天' eq invest.loan.operationType }">
							    ${invest.loan.day }天											
					            </c:if></td>
								<td width="13%"><fmt:formatNumber currencySymbol="￥" type="currency" value=" ${invest.money }" /></td>
								<td width="12%">${invest.rate*100}%</td>
								<td width="18%"><fmt:formatDate value="${invest.time}" type="both"/></td>
								<td width="15%">${invest.status}</td>
								<td width="12%" >								
		                        <div class="photo"  >
								     <a href="javascript:void(0);"  data-geo="" title="${invest.redpacketId}" >${invest.redpacketId}</a>
								</div>	 												    							    
								 </td>
							</tr>
							</c:forEach>
						</table>
						<script type="text/javascript">
						  $(function() {																  
							     $(".photo").tooltip({
								   position: {
								        my: "left top",
								        at: "right-20 top-10"
								      },
							      items: "[data-geo]",
							      content: function() {
							        var element = $( this );
							        if ( element.is( "[data-geo]" ) ) {
							          var text = element.attr( "title" );
							  	        var html = "<div class=\"xuanfu\" id=\"tankuang\"><div class=\"arrow\"><em></em><span></span></div>";
										$.ajax({
											  url: '/UdeskInter/getRedPacket/'+paramUrl,
										     type: 'POST',
										    async: false,
										     data: {
										    	redPacketId:element.text()
										    },
										    dataType: 'json',
										    success: function(data){
										    	if( data != null ){								 
										    		html += " <b>有效时间:"+getDate(data.createTime)+" ~ "+getDate(data.deadline)+"</b>";
										    		html += "<b>活动名称:"+data.name+"</b><b>投资限额:￥"+data.investMoney+"</b>";
										    		if( data.type == 'money' ){
										    			html += "<b>现金券："+data.money+"   元</b>";	
										    		}else if( data.type == 'rate' ){
										    			html += "<b>加息券："+(data.rate * 100).toFixed(2)+"%</b>";
										    		}
										    		
										    	}else{
										    		html += "<b>未使用加息券！</b>";
										    	}
										    }
										});
										html += "</div>";
						 	            return html;
							        }
							      }
							    });  
							  });
						  
							    </script>
					</div>

				</div>
			</div>
			 <form id="basicForm"  class="form-horizontal ">
			<div class="bottom-con" style="display:none;" id="news_title1_main_2">
				<div class="bottom-part4">
				<input type="hidden" name="id" id="userid"  value="${userId}">
						<p>
						 <label><input type="radio" name="p2p" id="radioDefault" value="1" checked="checked">投资过p2p</label> 
						<label><input type="radio" name="p2p" value="0" id="radioPrimary">未投资过p2p</label>
						</p>
						<p>
							<label><input type="radio"name="notice" id="radioDefault" value="1" checked="checked">有新标通知</label> <label><input
								type="radio" name="notice" value="0" id="radioPrimary" >有新标不通知</label>
						</p>
						<p >
						<label><input id="visitType" name="visitType" type="checkbox" value="激活回访" />激活回访</label>
					</p>
					
				</div>
				<div class="bottom-part5">
					<div>
						<p>
							<span>用户来源：</span><input name="source" id="btc"/>
						</p>
						<p>
							<span>QQ</span><input name="qq" id="q" />
						</p>
					</div>
					<div>
						<p>
							<span>微信</span><input name="weixin" id="weixin"/>
						</p>
						<p>
							<span>备注人ID</span><input name="userId" id="userId"/>
						</p>
					</div>
					<p class="dif-p">
						<span>备注</span>
						<textarea name="remark" id="remark"></textarea>
					</p>
					<p>
						<span></span><input id="save" type="button" value="保存" class="last-p" />
					</p>
				</div>
				<div class="bottom-part6">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>						
							<th scope="col">备注内容</th>
							<th scope="col">备注时间</th>
							<th scope="col">回访类型</th>
							<th scope="col">备注人</th>
							<th scope="col">操作</th>
						</tr>
					</thead>
					<tbody id="ttab">
						<!-- <tr>
							<td>撸羊毛</td>
							<td>2016-7-21 10:58:10</td>
							<td>添了</td>
							<td>删除</td>
						</tr> -->
				</tbody>				
					</table>

				</div>
			</div>
			</form>
			<script>
				tabs("news_title1", "cur", "li");
				var id='${userId}';
				var paramUrl='${paramUrl}';
				function func(){
					var str='';
					$.ajax({
						type : "POST",
						url : '/UdeskInter/rmarkList/'+paramUrl,
						data:{userid:id},
						async : false,
						dataType : 'json',
						error : function(e) {
							alert("异常");
						},
						success : function(data) {
							list = eval(data);
							var hasP2p=list[0].hasP2p;
							var notice=list[0].notice;
							var visitSource=list[0].visitSource;
							var q=list[2].qq;
							var weixin=list[2].weixin;
							var realname=list[2].realname;
							var remark=list[2].remark;
							if(visitSource!=''){
								if(visitSource=='1'){
								$("#btc").val("来自网络");
								}else if(visitSource=='0'){
									$("#btc").val("来自朋友");
								}else{
								$("#btc").val(visitSource);
								}
								
							}
							if(hasP2p!=''){
								$("input[name=p2p][value="+hasP2p+"]").attr("checked",true);
							}
							if(notice!=''){
								$("input[name=notice][value="+notice+"]").attr("checked",true);
							}
							if(q!=null && q!=''){
								$("#q").val(q);
							}
							if(weixin!=null && weixin!=''){
								$("#weixin").val(weixin);
							}
							if(remark!=null && remark!=''){
								$("#remark").html(remark);
							}
							 $.each(list[1], function (n, user) { 				
								 str += '<tr>';
				/* 					str += '<td>' + user.userid + '</td>';
				 */					str += '<td>' + user.remark + '</td>';
									str += '<td>'+user.time.substr(0, 19)+'</td>';
									str += '<td>' + user.visitType + '</td>';
									str += '<td>' + user.realname + '</td>';
									str += '<td><a href="javascript:del('+user.id+')">删除</a></td>';
									str += '</tr>';
					          });
							$("#ttab").html(str);
						}
					});
				}
				$("#save").click(function(){
					var userId=$('#userId').val();
					if(userId==null ||userId==''){
						alert("备注人Id不能为空");
						return false;
					}
					$.ajax({
						type : "POST",
						url : '/UdeskInter/tormark/'+paramUrl,
						data:$('#basicForm').serialize(),
						error : function(e) {
							alert("异常");
						},
						success : function(data) {
							if(data == 'ok'){
							alert("保存成功!");
							func();
						 }else if(data == 'userIdError'){
							 alert("没有改备注人ID，请重新填写!");
						 }else{						 
							 alert("保存失败!");
						 }
						}
					});
				});
				function del(id) {
					$.ajax({
						type : "POST",
						url : '/UdeskInter/delrmark/'+paramUrl,
						data:{id:id},
						error : function(e) {
							alert("异常");
						},
						success : function(data) {
							if(data == 'ok'){
							alert("删除成功!");
							func();
						 }else{
							 alert("删除失败!");
						 }
						}
					});
				}
				
	      	function getDate(tm){ 
	     	    var tt=new Date(parseInt(tm)).toLocaleDateString()
	     	    return tt; 
	     	}
	      
			</script>
		
		</div>
	</div>
</body>
</html>