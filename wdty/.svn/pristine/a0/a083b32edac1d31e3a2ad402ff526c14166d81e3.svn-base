   var weixinUtil = (function(){
      var appId = "hello";
      var jsApiList = ["onMenuShareTimeline","onMenuShareAppMessage","onMenuShareQQ","onMenuShareWeibo"];
  	  //初始化
  	  var init = function(config){
  		 //初始化微信参数
  	     var obj = $.parseJSON(config);
  	     appId = obj.appId;
	     obj =  $.extend(obj,{
			    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			    jsApiList: jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			}); 
	     wx.config(obj);
  	  };
      
	  //设置微信入口URL
	  var getOpenURL = function(url,state){
			
			return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
			       +appId+"&redirect_uri="
			       +url+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
	  };
      //设置分享URL
	  var setShareBut = function (titleStr,urlStr,imageUrlStr,descStr,callback){
		        
	         urlStr = encodeURI(urlStr);
			 urlStr = getOpenURL(urlStr,"duanrong");
			 var sharObj = {title:titleStr,link:urlStr,imgUrl:imageUrlStr,desc:descStr,success:callback};
			 //var sharObj2 = {title:titleStr,link:urlStr,imgUrl:imageUrlStr,desc:descStr,success:function(){}};
			//1.分享到朋友圈
				wx.onMenuShareTimeline(sharObj);
			//2.分享给朋友
			    wx.onMenuShareAppMessage(sharObj);
			//3.分享到QQ
			    wx.onMenuShareQQ(sharObj);
			//4.分享到腾讯微博
			    wx.onMenuShareWeibo(sharObj);
		 };
	   
	  return {
		  init:init,
		  setShareBut:setShareBut,
		  getOpenURL:getOpenURL
	  };
  	  
  })();