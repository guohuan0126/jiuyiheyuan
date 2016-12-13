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
  <title>编辑页面</title>  
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
      <h2><i class="fa fa-pencil"></i> 编辑页面</h2>
    </div>
    
    <div class="contentpanel">
        <form id="basicForm" class="form-horizontal">
          <div class="panel panel-default">
              <div class="panel-heading">
                <div class="panel-btns">               
                </div>
                <h4 class="panel-title">
                <c:if test="${risk.type eq 'house' }">
                	房押宝
                </c:if>
                 <c:if test="${risk.type eq 'vehicle' }">
                	车押宝
                </c:if>
                 <c:if test="${risk.type eq 'enterprise' }">
                	企业押宝
                </c:if>
                <c:if test="${risk.type eq 'all' }">
                	所有押宝
                </c:if>
                </h4>
                
              </div>
              <div class="panel-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">交易总量：<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                  <input type="hidden" id="riskid" name="id" value="${risk.id }">
                    <input type="text"  name="totalmoney" id="totalmoney" onkeyup="changerate(this.value)" value="<fmt:formatNumber value="${totalmoney}" maxFractionDigits="2" pattern="#.##"/>" width="10px" class="form-control" placeholder="交易总量..."/>
                  </div>
                  <div id="errortotalmoney" style="display: none;">交易总量格式不正确</div>
               </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">待收总额：<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text"  name="remoney" id="remoney" value="<fmt:formatNumber value="${remoney}" maxFractionDigits="2" pattern="#.##"/>" width="10px" class="form-control" placeholder="待收总额..."/>
                  </div>
                  <div id="errorremoney" style="display: none;">待收总额格式不正确</div>
               </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">累计逾期金额：<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text"  name="overmoney" id="overmoney" value="<fmt:formatNumber value="${risk.overmoney}" maxFractionDigits="2" pattern="#.##"/>" width="10px" class="form-control" disabled="disabled" placeholder="累计逾期金额..."/>
                  </div>
               </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">累计垫付金额：<span class="asterisk">*</span></label>
                  <div class="col-sm-3">
                    <input type="text"  name="spotmoney" id="spotmoney" value="${risk.spotmoney}" width="10px" class="form-control" placeholder="累计垫付金额..."/>
                  </div>
                  <div id="errorspotmoney" style="display: none;">累计垫付金额格式不正确</div>
               </div>
               <h4>逾期情况：</h4>
                <table class="table table-primary table-striped table-hover ">
                    <thead>
                      <tr>
                      <th></th>
                        <th>逾期时间</th>
                        <th>逾期金额</th>
                        <th>逾期率</th>
                        <th>回收中金额</th>
                        <th>已回收金额</th>
                        <th>详情</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:if test="${details.size() > 0 }">
                    <c:forEach items="${details}" var="detail" varStatus="vstatus">
                     <tr>
                     <td><input type="hidden" name="detailid${vstatus.index }" value=${detail.id } id="detailid${vstatus.index }"></td>
                       <td>
                       <input type="text"  name="dovertime${vstatus.index }" id="dovertime${vstatus.index }" value="${detail.overtime}" width="10px" class="form-control" disabled="disabled" placeholder="逾期时间..."/>
                       </td>
                       <td>
                       <input type="text"  name="dovermoney${vstatus.index }" id="dovermoney${vstatus.index }" onkeyup="rate(${vstatus.index },this.value)" value="<fmt:formatNumber value="${detail.overmoney}" maxFractionDigits="2" pattern="#.##"/>" width="10px" class="form-control"  placeholder="逾期金额..."/>
                       <div id="errordovermoney${vstatus.index }" style='display: none;'>逾期金额格式不正确</div>
                       </td>
                       <td>
                       <input type="text"  name="doverrate${vstatus.index }" id="doverrate${vstatus.index }" value="<fmt:formatNumber value="${detail.overmoney/totalmoney}" maxFractionDigits="2" pattern="#.##"/>" width="10px" class="form-control" disabled="disabled" placeholder="逾期率..."/>
                          <div id="errordoverrate${vstatus.index }" style='display: none;'>逾期率格式不正确</div>
                       </td>
                       <td>
                       <input type="text"  name="drecyclingmoney${vstatus.index }" id="drecyclingmoney${vstatus.index }" value="${detail.recyclingmoney}" width="10px" class="form-control"  placeholder="回收中金额..."/>
                          <div id="errordrecyclingmoney${vstatus.index }" style='display: none;'>回收中金额格式不正确</div>
                       </td>
                       <td>
                       <input type="text"  name="drecyledmoney${vstatus.index }" id="drecyledmoney${vstatus.index }" value="${detail.recyledmoney}" width="10px" class="form-control"  placeholder="已回收金额..."/>
                        <div id="errordrecyledmoney${vstatus.index }" style='display: none;'>已回收金额格式不正确</div>
                       </td>
                       <td><a href="/risk/todetail?id=${detail.id}">项目详情</a></td>
					  </tr>
				   </c:forEach>
					</c:if>
                    </tbody>
                   </table>
              </div><!-- panel-body -->
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-9 col-sm-offset-3">
                    <input type="button" class="btn btn-primary" onclick="sub();" value="保存"/>
                    <button type="reset" class="btn btn-default">重置</button>
                  </div>
                </div>
              </div>
            
          </div><!-- panel -->
      </form> 
    </div><!-- contentpanel -->
    
  </div><!-- mainpanel -->
 
</section>

<script>
function changerate(value){
	var exp = /^([1-9][\d]{0,10}|0)(\.[\d])?$/;
	var num='${num}';
	if(value==''){
		value=0;
	}else if(!exp.test(value)){
		$("#errortotalmoney").attr("style", "display:block;font-size:14px;color:red;");
		return false;
	}else{
		$("#errortotalmoney").attr("style", "display:none;");
	}
	for(var i = 0; i<num; i++){
		var dovermoney = $("#dovermoney"+i).val();
		if(dovermoney==''){
			dovermoney=0;
		}else if(!exp.test(dovermoney)){
			$("#errordovermoney"+i).attr("style", "display:block;font-size:14px;color:red;");
			return false;
		}else{
			$("#errordovermoney"+i).attr("style", "display:none;");
			
		}
		if(value==0){
			$("#doverrate"+i).val(0);
		}else{
			$("#doverrate"+i).val(parseFloat(dovermoney/value).toFixed(4));
		}
	}
}
function rate(index,value){
	var exp = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/;
	var totalmoney = $('#totalmoney').val();
	
	if(totalmoney==''){
		totalmoney=0;
	}else if(!exp.test(totalmoney)){
		$("#errortotalmoney").attr("style", "display:block;font-size:14px;color:red;");
		return false;
	}else{
		totalmoney=totalmoney;
		$("#errortotalmoney").attr("style", "display:none;");
	}
	if(value==''){
		value=0;
	}else if(!exp.test(value)){
		$("#errordovermoney"+index).attr("style", "display:block;font-size:14px;color:red;");
		return false;
	}else{
		$("#errordovermoney"+index).attr("style", "display:none;");
	}
	
	if(totalmoney!=0){
		$("#doverrate"+index).val(parseFloat(value/totalmoney).toFixed(4));
	}else{
		$("#doverrate"+index).val(0);
	}
	
}
	
	function sub(){
		var num='${num}';
		var riskid = $('#riskid').val();
		var totalmoney = $('#totalmoney').val();
		var remoney = $('#remoney').val();
		var overmoney = $('#overmoney').val();
		var spotmoney = $('#spotmoney').val();
		var exp = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/;
		if(totalmoney==''){
			totalmoney=0;
		}else if(!exp.test(totalmoney)){
			$("#errortotalmoney").attr("style", "display:block;font-size:14px;color:red;");
			return false;
		}else{
			$("#errortotalmoney").attr("style", "display:none;");
		}
		if(remoney==''){
			remoney=0;
		}else if(!exp.test(remoney)){
			$("#errorremoney").attr("style", "display:block;font-size:14px;color:red;");
			return false;
		}else{
			$("#errorremoney").attr("style", "display:none;");
		}
		if(spotmoney==''){
			spotmoney=0;
		}else if(!exp.test(spotmoney)){
			$("#errorspotmoney").attr("style", "display:block;font-size:14px;color:red;");
			return false;
		}else{
			$("#errorspotmoney").attr("style", "display:none;");
		}
		var details = "";
		if(num>0){
			overmoney=0;
		}
		for(var i = 0; i<num; i++){
			var detailid = $("#detailid"+i).val();
			var dovertime = $("#dovertime"+i).val();
			
			var dovermoney = $("#dovermoney"+i).val();
			var doverrate = $("#doverrate"+i).val();
			var drecyclingmoney = $("#drecyclingmoney"+i).val();
			var drecyledmoney = $("#drecyledmoney"+i).val();
			
			if(dovermoney==''){
				dovermoney=0;
			}else if(!exp.test(dovermoney)){
				$("#errordovermoney"+i).attr("style", "display:block;font-size:14px;color:red;");
				return false;
			}else{
				$("#errordovermoney"+i).attr("style", "display:none;");
				
			}
			overmoney=parseFloat(dovermoney)+parseFloat(overmoney);
			if(doverrate==''){
				doverrate=0;
			}else if(!exp.test(doverrate)){
				//$("#errordoverrate"+i).attr("style", "display:block;font-size:14px;color:red;");
				//return false;
			}else{
				$("#errordoverrate"+i).attr("style", "display:none;");
			}
			
			if(drecyclingmoney==''){
				drecyclingmoney=0;
			}else if(!exp.test(drecyclingmoney)){
				$("#errordrecyclingmoney"+i).attr("style", "display:block;font-size:14px;color:red;");
				return false;
			}else{
				$("#errordrecyclingmoney"+i).attr("style", "display:none;");
			}
			if(drecyledmoney==''){
				drecyledmoney=0;
			}else if(!exp.test(drecyledmoney)){
				$("#errordrecyledmoney"+i).attr("style", "display:block;font-size:14px;color:red;");
				return false;
			}else{
				$("#errordrecyledmoney"+i).attr("style", "display:none;");
			}
			details += detailid+"、"+dovertime+"、"+dovermoney+"、"+doverrate+"、"+drecyclingmoney+"、"+drecyledmoney+";";			
		}
		$.ajax({
			type : 'POST',
			url : '/risk/updateRisk',
			data:{
				'riskid':riskid,
				'overmoney':overmoney,
				'totalmoney':totalmoney,
				'remoney':remoney,
				'spotmoney':spotmoney,
				'details':details
			},
			success : function(msg) {
				if(msg == 'ok'){
					alert("编辑成功!");
					window.location = "/risk/riskList";
				}else{
					alert("编辑失败!");
					window.location = "/risk/riskList";
				}
			},
			error : function() {
				alert("异常！");
			}
		});			
	}
</script>

</body>
</html>
    