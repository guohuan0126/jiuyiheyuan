
var setp = 1; //步骤
var begin = true;  //判断是否停止
var awardjson = null;  //用户奖品信息
var isFirstShare = true; //是否为第一次分享
var openId = null;
var mobile = null;
var weixinConfig = null;
var resultIndex = 1;  //结果索引
/**
 * 初始化页面
 */
function activityInit(openIdStr,mobileStr,weixinConfigStr){
	weixinConfig = weixinConfigStr;
	//初始化分享
	weixinShareInit(weixinConfig,function(){ saveUserAction(openId,mobile,"Share"); });
	openId = openIdStr;
	mobile = mobileStr;
	saveUserAction(openId,mobile,"goIndex");
	//1.第一步操作
	//弹出输入手机号窗口
	$("#telBut").click(function(){
		$("#telButDiv").hide();
		$("#click-tel").show();
	});
	//关闭输入手机号窗口
	$("#closePhoneDiv").click(function(){
		$("#telButDiv").show();
		$("#click-tel").hide();
	});
	
	//检查手机号并领取奖励
	$("#checkMobileBut").click(function(){
		submit();
	});
	
	//2.第二步操作
	//弹出分享层
	$("#shareBut01,#shareBut02").click(function(){
		setShareInfo(); //第一步完成后设置分享信息
		$("#share-box").show();
		
	});
	//隐藏分享弹出层
	$("#share-box").click(function(){
		$(this).hide();
	});
	
	//3.通用
	//查看奖品
	$("#showAwardBut01,#showAwardBut02").click(function(){
		
	    $("#sectionOne,#redAward,#moneyAward").hide();
	    $("#sectionTwo,#showAwardDiv").show();
	});
	//返回查看奖品
	$("#goBackBut").click(function(){
		
		if(resultIndex == 2){
			 $("#moneyAward").show();
		}else{
			$("#redAward").show();
		}
	    $("#showAwardDiv").hide();
	});
	//去登陆
	$("#goLoginBut").click(function(){
		  if(typeof(awardjson.user) != "undefined"){
			   window.location.href=mWebUrl+"memberLogin"; 
		  }else{
			   window.location.href=mWebUrl+"regist";
		  }
	});

	//显示活动规则
	$("#showRuleBut").click(function(){
		$("#showRuleDiv").show();
	});
	$("#showRuleClose").click(function(){
		$("#showRuleDiv").hide();
	});
	
	//初始化一下，防止网速直接显示刮奖结果
	//$("#lotteryContainer").show();
    //var iniLottery = new Lottery('lotteryContainer', '/images/images-8.jpg', 'image', 100, 50, function(){}).init('/images/images-7.jpg', 'image');
    //$("#lotteryContainer").hide();
}

/**
 * 提交表单
 * @returns {Boolean}
 */
function submit(){
	
	 mobile = $("#mobileInput").val();
	 //判断手机是否null
	 if(isNull(mobile)){
		 alert("请输入手机号");
		 return false;
	 }
	 //判断手机号是否正确
	 if(!checkMobile(mobile)){
		 alert("手机号格式不正确");
		 return false;
	 }
	 
	$("#checkMobileBut").attr("disabled", true);
	saveUserAction(openId,mobile,"inputMobile");
	$.ajax({
		type:"post",
		url:"/weixin/getReward",
		data:{"openId":openId,"mobile":mobile},
		dataType:"json",
		success:function(json){
			if(json.status == "isNotGetEd"){
				//初始化奖品
				awardjson = json;
				initGuajiang(awardjson.firstJSON);  //初始化第一次刮奖
			}else if(json.status == "isGetEd"){
				alert("此手机号已刮过奖");
			}else if(json.status == "inputError"){
				alert("输入有误");
			}else if(json.status == "activityIsEnd"){
				alert("对不起活动已结束");
			}else{
				alert("服务器异常，请刷新页面重试");
				console.log(json);
			}
			$("#checkMobileBut").attr("disabled", false);
		}
	});
}


/**
 * 初始化刮奖
 */
function initGuajiang(obj){
       //初始化刮奖
       
       var imgUrl01 = '/images/images-8.jpg';
       var imgUrl02 = '/images/images-7.png';
	   if(obj.setp == 2){
	      imgUrl01 = '/images/images-9.jpg';
	      imgUrl02 = '/images/images-7.png';
	   }
	   begin = true; //初始化为true
       $("#sectionOne").show();
       $("#sectionTwo").hide();
       $("#telButDiv,#click-tel").hide();
       var lottery = new Lottery('lotteryContainer',imgUrl01,'image',100,50,function(percent){
	       if(percent > 10 && begin){
			   begin = false;
			   showResult(obj); //显示结果
			   saveAward(obj);//异步保存优惠劵
			   showAward(obj); //初始化奖品展示
	       }
       });
       lottery.init(imgUrl02,'image');
       $("#lotteryContainer").show();

}


/**
 * 显示刮奖结果
 */
function showResult(obj){
       
	   $("#moneyAward,#redAward").hide();
	   if(obj.type == "money"){
		  $("#moneyAward").find("p").eq(0).html("恭喜您获得<b>"+obj.moneyStr+"元现金</b>");
		  if(obj.isOpen){
			  $("#moneyAward").find("p").eq(1).hide();
		  }else{
			  $("#moneyAward").find("p").eq(1).show();
		  }
		  $("#moneyAward").find("p").eq(2).html("现金已充值到<font>"+obj.mobile+"</font>账户");
		  $("#moneyAward").show();
		  resultIndex = 2;
	   }else if(obj.type == "moneyRed"){
		  $("#redAward").find("p").eq(0).html("恭喜您获得<b>"+obj.moneyStr+"元红包</b>已放到<font>"+obj.mobile+"</font>账户");
		  $("#redAward").show();
		  resultIndex = 1;
	   }else if(obj.type == "rateRed"){
		  $("#redAward").find("p").eq(0).html("恭喜您获得<b>"+obj.rateStr+"%加息劵</b>已放到<font>"+obj.mobile+"</font>账户");
		  $("#redAward").show();
		  resultIndex = 1; 
	   }
	   if(obj.setp == 2){
		  $("#moneyAward").find("div").addClass("index-prize-but2").find("input").eq(1).hide();
		  $("#redAward").find("div").addClass("index-prize-but2").find("input").eq(1).hide();
	   }else{
 		  $("#moneyAward").find("div").removeClass("index-prize-but2").find("input").eq(1).show();
		  $("#redAward").find("div").removeClass("index-prize-but2").find("input").eq(1).show();
	   }
    
    $("#sectionOne").hide();
    $("#sectionTwo").show();
}

/**
 * 显示奖品
 */
function showAward(obj){
    
	   var redTitle = "";  
	   var redContent = "";
	   obj.index = obj.setp-1; //优惠劵索引
	   if(obj.type == "rateRed"){
		   redTitle = "加息劵";
		   redContent = parseInt(obj.rate*100)+"%加息劵";
	   }else if(obj.type == "moneyRed"){
		   redTitle = "红包";
		   redContent = obj.money+"元现金红包";
	   }else if(obj.type == "money"){
		   redTitle = "现金";
		   redContent = obj.money+"元现金";
	   }
	   $("#showAwardDiv").find("div").eq(obj.index).find("span").eq(0).html(redTitle);
	   $("#showAwardDiv").find("div").eq(obj.index).find("span").eq(1).html(redContent);
	   $("#showAwardDiv").find("div").eq(obj.index).show();
	   if(obj.index == 1){
	 	  $("#showAwardDiv").removeClass("prize-check-one");
	 	  $("#showAwardDiv").find("div").addClass("index-prize-but2");
	 	  $("#showAwardDiv").find("div").eq(2).find("input").eq(1).hide();
	   }else{
	 	  $("#showAwardDiv").addClass("prize-check-one"); 
	 	  $("#showAwardDiv").find("div").removeClass("index-prize-but2");
	 	  $("#showAwardDiv").find("div").eq(2).find("input").eq(1).show();
	   }
	   //$("#sectionOne").show();
	   //$("#sectionTwo").hide();
	
 }
 
 
/**
 * 保存奖品
 */
function saveAward(obj){
	
	//alert("openId:"+openId+",mobile:"+mobile+",setp:"+setp+",money:"+money);
	//{"openId":openId,"mobile":mobile,"setp":setp,"money":money}
	$.ajax({
 		type:"post",
 		url:"/weixin/saveReward",
 		data:obj,
 		dataType:"json",
 		success:function(json){
 			if(json.status == "success"){
 				//alert("此手机号已刮过奖");
 			}else if(json.status == "isExist"){
 				alert("此手机号已刮过奖");
 			}else if(json.status == "inputError"){
 				alert("输入有误");
 			}else if(json.status == "activityIsEnd"){
 				alert("对不起活动已结束");
 			}else{
 				alert("服务器异常，请刷新页面重试");
 				//console.log(json);
 			}
 		}
 	});
	
}

/**
 * 设置分享信息
 */
function setShareInfo(){
	
	//初始化分享
	weixinShareInit(weixinConfig,function(){
		if(isFirstShare){
			setp = 2;
			initGuajiang(awardjson.secondJSON);
			isFirstShare = false;
			saveUserAction(openId,mobile,"Share");
			$("#share-box").hide();
		}
	});
	
}