<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <style>
   .photo {
    width: 67px;
    text-align: center;
  }
  .photo .ui-widget-header {
    margin: 0em 0;
  }
  .ui-tooltip {
    max-width: 500px;
    background:#3B3B3B;
    border: 2px solid white;
    padding: 10px 20px;
    color: #FFEC8B;
    border-radius: 20px;
    font: bold 14px "Helvetica Neue", Sans-Serif;
  }
  .ui-dialog .ui-dialog-titlebar-close span { display: block;margin: -8px;}
  #tabledailogmy td{
  	color:#ECB159
  }
  </style>

 
 	<script type="text/javascript">
 		
 	$(function() {
 	    $(".photo").tooltip({
 	      items: "img, [data-geo], [title]",
 	      content: function() {
 	        var element = $( this );
 	        if ( element.is( "[data-geo]" ) ) {
 	          var text = element.text();
			  var html = "<div ><table id='tabledailogmy'>";
				$.ajax({
					  url: '/redPacketDetail/getRedPacket',
				     type: 'POST',
				    async: false,
				     data: {
				    	redPacketId:element.text()
				    },
				    dataType: 'json',
				    success: function(data){
				    	if( data != null ){	
				    		html += "<tr><td style='color:#ECB159'>编号：</td><td>"+data.id+"</td>";
				    		<permissionAct:active activeId="USER_LOOK">
				    		html += "<td>手机号：</td><td>"+data.mobile+"</td></tr>";
				    		</permissionAct:active>
				    		html += "</tr>";
				    		html += "<tr><td>用户ID：</td><td>"+data.userId+"</td><td></td><td></td></tr>";
				    		html += "<tr><td>有效时间：</td><td  colspan='3'>"+getDate(data.createTime)+" ~ "+getDate(data.deadline)+"</td></tr>";
				    		html += "<tr><td>活动名称：</td><td>"+data.name+"</td><td>投资限额:</td><td>"+data.investMoney+"   元</td></tr>";
				    		if( data.type == 'money' ){
				    			html += "<tr><td>现金券：</td><td>"+data.money+"   元</td><td></td><td></td></tr>";	
				    		}else if( data.type == 'rate' ){
				    			html += "<tr><td>加息券：</td><td>"+data.rate * 100+"%</td><td></td><td></td></tr>";
				    		}
				    		
				    	}else{
				    		html += "<tr class='font'><td>未使用加息券！</td></tr>";
				    	}
				    }
				});
				html += "</table>";
				html += "</div>";
 	            return html;
 	        }
 	      }
 	    });
 	  });
 	
 	function getDate(tm){ 
 	    var tt=new Date(parseInt(tm)).toLocaleDateString()
 	    return tt; 
 	} 

 	function openDialog(investId,redpacketId,mobileNumber,investStatus,loanStatus){
 		alert("investStauts:"+investStatus)
 		if(investStatus !='投标成功'){
 			alert("只有投资记录状态为投标成功才可以使用加息券！");
 			return false;
 		}
 		if(loanStatus !='筹款中'){
 			alert("只有项目状态为筹款中才可以使用加息券！");
 			return false;
 		}
 		if( !checkInvestNum(investId) ){
 			alert("您已对该项目使用过优惠券！");
 			return false;
 		}
 		
 		var url = "${ctx}/redPacketDetail/toRedPacketUse/"+investId+"/"+redpacketId+"/"+mobileNumber;
 		alert("url:"+url);
 		$("#iframeId").attr("src",url);
 		 $( "#dialog-confirm" ).dialog( "open" );
 		 
 	}
 	$(function(){
 		$( "#dialog-confirm" ).dialog({
		      autoOpen: false,
		      height: 570,
		      width:870,
		      modal: true,
		      buttons: {
		    	  "确认使用此加息券":function(){
		    		  var packetId =  $("#iframeId").contents().find("input[type='radio']:checked").val();
		    		  var investid =  $("#iframeId").contents().find("#investId").val();
		    		  if( packetId == null ){
		    			  alert("加息券选择为空！");
		    			  return false;
		    		  }
		    		  updatePacketUse(packetId,investid);
		    		  $( this ).dialog( "close" );
		    		  location.reload();
		    	  },
		    	  "取消所使用加息券":function(){
		    		  var investid =  $("#iframeId").contents().find("#investId").val();
		    		  if( confirm("确认取消对此用户使用的加息券？") ){
		    			  updatePacketUse(null,investid);
		    			  $( this ).dialog( "close" );
		    			  location.reload();
		    		  }
		    	  },
		          Cancel: function() {
		            $( this ).dialog( "close" );
		          }
		        }
		});
 	});
 	
 	function updatePacketUse(id,investid){
 		$.ajax({
			  url: '/redPacketDetail/updatePacketUse',
		     type: 'POST',
		    async: false,
		     data: {
		    	packetId:id,
		    	investId:investid
		    },
		    success: function(data){
		    	if( data == 'fail' ){
		    		alert("操作失败，请重试！");
		    	}
		    },
		    error:function(data){
		    	alert("操作失败，请重试！");
		    }
 		});
 	}
 	
 	function checkInvestNum(investId){
 		var ret = false;
 		$.ajax({
			  url: '/redPacketDetail/checkInvestNum',
		     type: 'POST',
		    async: false,
		     data: {
		    	 investId:investId
		    },
		    success: function(data){
		    	if( data  == 'true' )
		    		ret=true;
		    },
		    error:function(data){
		    	alert("操作失败，请重试！");
		    }
		});
 		return ret;
 	}
 	
 	</script>
 	
	<div id="dialog-confirm" style="display:none;overflow:hidden;padding:3px">
	    <iframe id="iframeId" src="" frameborder="no" border="0" marginwidth="0" marginheight="0" width="100%" height="100%"  scrolling="no"></iframe>
	</div>