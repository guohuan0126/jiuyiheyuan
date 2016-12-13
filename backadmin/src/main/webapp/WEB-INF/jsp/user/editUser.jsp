<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="images/favicon.png" type="image/png">

  <title>用户管理</title>
  <style type="text/css">
  #tabs1{  
 text-align:left;  
 width:100%;  
}  
.menu1box{  
 position:relative;  
 overflow:hidden;  
 height:50px;  
 width:100%;  
 text-align:left;  
}  
#menu1{  
 position:absolute;  
 top:0;  
 left:0;  
 z-index:1;  
}  
#menu1 li{  
 float:left;  
 display:block;  
 cursor:pointer;  
 width:132px;  
 text-align:center;  
 line-height:21px;  
 height:21px;  
}  
.main1box{  
 clear:both;  
 margin-top:-1px;    
 height:100%;  
 width:100%;  
}  
#main1 ul{  
 display: none;  
}  
#main1 ul.block{  
 display: block;  
}  
  
  
  </style>

</head>

<body>



<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>


<section>

<%@include file="../base/leftMenu.jsp"%>
  
  <div class="mainpanel">
<%@include file="../base/header.jsp"%>
    <div class="pageheader">
      <h2><i class="fa fa-pencil"></i>用户信息修改</h2>      
    </div>  
    <div class="contentpanel">
        
          <div class="panel panel-default">
<!--               <div class="panel-heading"> -->
<!--                 <div class="panel-btns"> -->
                 
<!--                 </div> -->
<!--                 <h4 class="panel-title">修改用户</h4> -->
<!--               </div> -->
             
             
             <div id="tabs1">  
             <div class="pageheader">
 <div class="menu1box">  
  <ul id="menu1">  
    
 
   <li onclick="setTab(1,0)"><a href="#"><i class="fa fa-pencil"></i>基本信息修改</a></li>  

<base:active activeId="USER_ROLE">
<li onclick="setTab(1,1)"><a href="#"><i class="fa fa-pencil"></i>角色信息修改</a></li> 
</base:active>
  </ul>  
 </div>  
 </div>
 <div class="main1box">  
  <div class="main" id="main1">  
  
   <ul>
   
   <form id="basicForm"  class="form-horizontal ">
<div>
           <div class="panel-body">
                <div class="form-group">
                   <label class="col-sm-1 control-label">用户名<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="hidden" name="username" value="${user.userId}" />
                    <input type="text" name="userId" value="${user.userId}" placeholder="用户名..."  class="form-control" disabled="disabled"/>
                  </div>
                   <label class="col-sm-1 control-label">电子邮件<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="email" value="${user.email}" placeholder="电子邮件..."  class="form-control" disabled="disabled"/>
                  </div>
                  
                  <label class="col-sm-1 control-label">正在使用红包数量<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="used" id="used" value="${used}" placeholder="已使用（未到账）红包数量..."  class="form-control" disabled="disabled"/>
                  </div>
                  
                </div>
                <div class="form-group">
                   <label class="col-sm-1 control-label">真实姓名<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="realname" value="${user.realname}" placeholder="真实姓名..."  class="form-control" disabled="disabled"/>
                  </div>
                  <label class="col-sm-1 control-label">昵称<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="nickname" value="${user.nickname}" placeholder="昵称..." disabled="disabled" class="form-control"/>
                  </div>
                  
                  <label class="col-sm-1 control-label">当前可用红包数量<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="unused" id="unused" value="${unused}" placeholder="当前可用红包数量..."  class="form-control" disabled="disabled"/>
                  </div>
                  
                </div>
                <div class="form-group">
                   <label class="col-sm-1 control-label">身份证号码<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="idCard" value="${user.idCard}" placeholder="身份证号码..." disabled="disabled" class="form-control"/>
                  </div>
                   <label class="col-sm-1 control-label">目前地址<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="postAddress" value="${other.postAddress}" placeholder="目前地址..." disabled="disabled" class="form-control"/>
                  </div>
                  
                   <label class="col-sm-1 control-label">账户可用金额<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="bbalance" value="${bbalance}"   class="form-control" disabled="disabled"/>
                  </div>
                
                  
                </div>
                
                <div class="form-group">
                <label class="col-sm-1 control-label">微信<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                  
                    <input type="text" name="winXin" value="${user.weixin}" placeholder="微信号..."  class="form-control" />
                  </div>
                
                  <label class="col-sm-1 control-label">注册时间<span class="asterisk">*</span></label>
                   <div class="col-sm-2">
								<input type="text" class="form-control datepicker" name="registerTime" id="datepicker" 
									value=<fmt:formatDate value="${user.registerTime}" pattern="yyyy-MM-dd HH:mm:ss" /> placeholder="注册时间" >
								 <input type="hidden" name="registerTime1" value="${user.registerTime}" />
							</div>
                   
                 
                 
                 
                  			
					   <label class="col-sm-1 control-label">账户冻结金额<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="bfreezeAmount" value="${bfreezeAmount}"   class="form-control" disabled="disabled"/>
                  </div>		
                  
                </div>
              
                 <div class="form-group">
                   
				 <label class="col-sm-1 control-label">手机号归属省<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    
                    <input type="text" name="phoneNoAttribution" value="${user.phoneNoAttribution}" placeholder="手机号归属省..."  class="form-control" />
                  </div>
                   <label class="col-sm-1 control-label">手机号归属市<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    <input type="text" name="phoneNoCity" value="${user.phoneNoCity}" placeholder="手机号归属市..."  class="form-control" />
                  </div>
						  <label class="col-sm-1 control-label">是否开通易宝账户</label>
                   <div class="col-sm-2">
	                <select class="form-control" name="investor" id="investor" onchange="investorchange(this.value)"  data-placeholder="状态...">	                  
	                  <option value="0">未开通</option>
	                  <option value="1">已开通</option>
	                </select>
	              </div>	
<!--                   <div class="col-sm-2"> -->
<!--                     <input type="text" name="registerTime" value="${user.registerTime}" placeholder="注册时间..."  class="form-control" /> -->
<!--                   </div> -->
                </div>
                   <div class="form-group">
                   <label class="col-sm-1 control-label">邮编<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                
                    <input type="text" name="postCode" value="${user.postCode}" placeholder="邮编..."  class="form-control" />
                  </div>
                  
                  
                  
                  <label class="col-sm-1 control-label">手机号码</label>
                  <div class="col-sm-2">
                    <input type="text" id="mobileNumber" name="mobileNumber" value="${user.mobileNumber}" onchange="mobileonchange(this.value)" placeholder="手机号码..."  class="form-control"/>
                  </div>
                  <div id="errormobileNumber" style="display: none;">手机号不能为空</div>
                  <div id="errorNumber" style="display: none;">手机号格式不正确</div>
                  <label class="col-sm-1 control-label">用户来源</label>
                  <div class="col-sm-2">
                    <input type="text" id="userSource" name="userSource" value="${other.userSource}" placeholder="用户来源..."  class="form-control"/>
                  </div>
                  
                  
                </div>
                 <div class="form-group">
                   <label class="col-sm-1 control-label">状态</label>
                   <div class="col-sm-2">
	                <select class="form-control" name="bct" id="bct" data-placeholder="状态...">
	                  <option value="1">正常</option>
	                  <option value="0">禁用</option>
	                </select>
	              </div>
                  <label class="col-sm-1 control-label">用户类型</label>
                   <div class="col-sm-2">
	                <select class="form-control" name="userType" id="usertype" data-placeholder="状态...">
	                  <option value="user">个人</option>
	                  <option value="enterprise">企业</option>
	                </select>
	              </div>
                </div>
                
                
                
                
                
                
                <c:if test="${user.userType eq 'enterprise' }">
                 <div class="form-group">
                   <label class="col-sm-1 control-label">企业名称<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    ${user.enterpriseName }
                  </div>
                   <label class="col-sm-1 control-label">开户银行<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                   ${user.bankLicense}
                  </div>
                </div>
                <div class="form-group">
                   <label class="col-sm-1 control-label">组织机构代码证<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    ${user.orgNo }
                  </div>
                   <label class="col-sm-1 control-label">营业执照编号<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                   ${user.businessLicense}
                  </div>
                </div>
                <div class="form-group">
                   <label class="col-sm-1 control-label">税务登记号<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    ${user.taxNo }
                  </div>
                   <label class="col-sm-1 control-label">法人姓名<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                   ${user.legal}
                  </div>
                </div>
                 <div class="form-group">
                   <label class="col-sm-1 control-label">法人身份证<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                    ${user.legalIdcard }
                  </div>
                   <label class="col-sm-1 control-label">联系人<span class="asterisk">*</span></label>
                  <div class="col-sm-2">
                   ${user.contact}
                  </div>
                </div>
                </c:if>

              </div><!-- panel-body -->
             <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3">
                 
                  
                    <button class="btn btn-primary" onclick="submitUser()" type="button">保存</button>
                    
                    <button type="reset" class="btn btn-default">重置</button>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</td>
                     <button class="btn btn-primary" onclick="submitqq()" type="button">注销</button>
                  </div>
                </div>
              </div>
   
   </div>
   
   
      </form> 
</ul>  
 <ul>
   
<form id="basicForm1"  class="form-horizontal ">

<div>
  <div class="panel panel-default">   
              <div class="panel-body">
                 <input type="hidden" name="username" value="${user.userId}" />
                 <base:active activeId="USER_ROLE"> 
                 <div class="form-group">
                  <input type="hidden" name="ids" id="ids">
				  <label class="col-sm-1 control-label" for="checkbox">角色</label>
				  <c:forEach items="${list}" var="item" varStatus="status">
				  <c:if test="${status.index % 10 eq 0}">
				  <div class="col-sm-2">
				  <input type="hidden" id="newroles" name="newroles" value="" />
				  <input type="hidden" id="oldroles" name="oldroles" value="" />
				  </c:if>
					 <div class="checkbox block"><label><input name="roles" value="${item.id }" type="checkbox"> ${item.name }</label></div>
				  <c:if test="${(status.index+1) % 10 eq 0}">
				  </div>
				  </c:if>
				  </c:forEach>
				</div>
				</base:active> 
              </div><!-- panel-body -->
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3">
                    <button class="btn btn-primary" onclick="submitRole()" type="button">保存</button>

                    <button type="reset" class="btn btn-default">重置</button>
                  </div>
                </div>
              </div>
</div>
<script type="text/javascript">
								window.onload = function() {        
									var oldroles="";
									var r=document.getElementsByName("roles"); 
								    for(var i=0;i<r.length;i++){
								         if(r[i].checked){
								        	 oldroles= oldroles+ r[i].nextSibling.nodeValue+",";
								       }
								    }      
								    $("#oldroles").val(oldroles);
								}  
									</script> 
</form>
   </ul>  
  </div>  
 </div>  
</div>  
<br />  
<br />
             
             
             
            
          </div><!-- panel -->
   
    </div><!-- contentpanel -->
    
  </div><!-- mainpanel -->
 
</section>




<script>
jQuery(document).ready(function(){
	

	$("#bct").val('${user.status}');
	$("#investor").val('${investor}');
	$("#usertype").val('${user.userType}');
	jQuery(".select2").select2({
		width : '30%',
		minimumResultsForSearch : -1
	});
	setTab(1,0);
	
	
	jQuery('#registerTime').datepicker();

	jQuery('#datepicker').datetimepicker({
		showSecond : true,
		timeFormat : 'hh:mm:ss',
		stepHour : 1,
		stepMinute : 1,
		stepSecond : 1,
	});
	
	
	
	var pids='${pids}';
	 if(pids!='')  {
		  var str=pids.split(",");
		  //以逗号将字符串转化为数组 
		  for(var j=0;j<str.length;j++){
			  $(":checkbox[value='"+str[j]+"']").attr("checked",true);  
		}
	}
});
function submitUser(){
	
	
	var investor=${investor};
	var val=$("#investor").val();
	if(investor==1){
		if(val==0){
			alert("易宝账户不可由已开通变为未开通!");
			$("#investor").val('${investor}');
			return false;
		}
	}
	if ($("#mobileNumber").val() != '' && !$("#mobileNumber").val().match("^[1][3,5,7,8]+\\d{9}")) {
		$('#errorNumber').attr("style", "display:block;font-size:14px;color:red");
		return false;
	}else{
		
	}
	var result = new Array();
		var ids="";
//	$("[name = roles]:checkbox").each(function () {
  //      if ($(this).is(":checked")) {
  //          result.push($(this).attr("value"));
  //      }
//	});
	ids=result.join(","); 
	$("#ids").val(ids);
	$.ajax({
            type: "POST",
            url:'/user/edituser',
            data:$('#basicForm').serialize(),// 你的formid
            async: false,
            error: function( e ) {
                alert("异常");
            },
            success: function(data) {
              if(data == 'ok'){
              	alert('操作成功');
              	window.location='${url}';
              }else{
              	alert(data);
              }                 
            }
        });		
}

function submitRole(){
	
	
	var result = new Array();
		var ids="";
	$("[name = roles]:checkbox").each(function () {
        if ($(this).is(":checked")) {
            result.push($(this).attr("value"));
        }
	});

	//	str = $("input:checkbox[name='roles']:checked").next().text();
	//	alert(str);
		
		
		var newroles="";
		var r=document.getElementsByName("roles"); 
	    for(var i=0;i<r.length;i++){
	         if(r[i].checked){
	        	 newroles= newroles+ r[i].nextSibling.nodeValue+",";
	       }
	    }      
	    $("#newroles").val(newroles);
	ids=result.join(","); 
	$("#ids").val(ids);
	$.ajax({
            type: "POST",
            url:'/user/editrole',
            data:$('#basicForm1').serialize(),// 你的formid
            async: false,
            error: function( e ) {
                alert("异常");
            },
            success: function(data) {
              if(data == 'ok'){
              	alert('操作成功');
              	window.location='${url}';
              }else{
              	alert(data);
              }                 
            }
        });		
}
function submitqq(){
	if ($("#used").val() != 0) {
		alert("当前有正在使用的红包，不可注销用户！");
		return false;
	}
	
if(${bbalance}!=0.0){
	    alert("账户可用金额不为0，不可注销用户！");
		return false;
	}
if(${bfreezeAmount}!=0.0){
//	alert('${bfreezeAmount}');
    alert("账户冻结金额不为0，不可注销用户！");
	return false;
}
	
	if ($("#unused").val() != 0){
	if(window.confirm("还有可用红包未使用,确定要注销用户吗？")){
	
		$.ajax({
	            type: "POST",
	            url:'/user/canceluser',
	            data:$('#basicForm').serialize(),// 你的formid
	            async: false,
	            error: function( e ) {
	                alert("异常");
	            },
	            success: function(data) {
	              if(data == 'ok'){
	              	alert('操作成功');
	              	window.location='${url}';
	              }else{
	              	alert(data);
	              }                 
	            }
	        });		
		   
		
		}
	}else{
		if(window.confirm("确定要注销用户吗？")){
			
			$.ajax({
	            type: "POST",
	            url:'/user/canceluser',
	            data:$('#basicForm').serialize(),// 你的formid
	            async: false,
	            error: function( e ) {
	                alert("异常");
	            },
	            success: function(data) {
	              if(data == 'ok'){
	              	alert('操作成功');
	              	window.location='${url}';
	              }else{
	              	alert(data);
	              }                 
	            }
	        });		
			   
			
			}
		
	}

}
function mobileonchange(val){
	var used =${used};
    var investor=${investor};
	if(used!=0){
		alert("此手机号有正在使用的红包,不可修改手机号!");
		$("#mobileNumber").val('${user.mobileNumber}');
	}
	if(investor==1){
		alert("此手机号已经开通易宝账户,请去网站前台修改手机号!");
		$("#mobileNumber").val('${user.mobileNumber}');
	}

}
function investorchange(val){
	
	var investor=${investor};
	if(investor==1){
		if(val==0){
			alert("易宝账户不可由已开通变为未开通!");
			$("#investor").val('${investor}');
		}
	}

}

function setTab(m,n){  
	 var tli=document.getElementById("menu"+m).getElementsByTagName("li");  
	 var mli=document.getElementById("main"+m).getElementsByTagName("ul");  
	 for(i=0;i<tli.length;i++){  
	  tli[i].className=i==n?"hover":"";  
	  mli[i].style.display=i==n?"block":"none";  
	 }  
	}
</script>

</body>
</html>
    