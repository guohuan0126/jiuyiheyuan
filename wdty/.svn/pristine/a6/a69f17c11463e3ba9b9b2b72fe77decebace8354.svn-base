//验证手机号是否正确
var checkMobile = function(s){
	var regu =/^0?1[3|4|5|7|8][0-9]\d{8}$/;
	var re = new RegExp(regu);
	if (re.test(s)) {
	        return true;
	    }else{
	       return false;
	    }
};
//判断是否为空
var isNull = function ( str ){
	if ( str == "" ) return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
};
var baseUrl = "http://wx.duanrong.com/";
var mWebUrl = "http://m.duanrong.com/";
//微信分享信息设置
var weixinShareInit = function(config,callback){
    //初始化微信参数
    weixinUtil.init(config);
    //设置分享URL
    wx.ready(function(){
	   	 var title = "518就来短融网现金好礼刮不停~不要错过！";
	   	 var url = baseUrl+"weixin/index.htm";
	   	 var imageUrl = baseUrl+"images/share.png";
	   	 var desc = "咱俩平时这么铁，发现这个好活动立马就告诉你！";
       	 weixinUtil.setShareBut(title,url,imageUrl,desc,callback);
    });
};
/**
 * 加载领取列表
 */
var initPage = function(){
	window.scrollTo(0,document.body.scrollHeight/5);
};

//shakenum:抖动的次数，shakeDistance：抖动的距离
jQuery.fn.Shake = function (shakenum , shakeDistance) {
	this.each(function () {
		var jSelf = $(this);
		jSelf.css({ position: 'relative' });
		for (var x = 1; x <= shakenum; x++) {
			jSelf.animate({ left: (-shakeDistance) }, 50)
				.animate({ left: shakeDistance }, 50)
					.animate({ left: 0 }, 50);
			}
		});
		return this;
	}
var user = null;
//跳转登录页
function tiaozhuan(){
   if(user != null){
	   window.location.href=mWebUrl+"memberLogin"; 
   }else{
	   window.location.href=mWebUrl+"regist";
   }
  
}

/**
 * 生成uuid
 */
function getUUID(){
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}
/**
 * 保存用户动作
 * @returns
 */
function saveUserAction(openId,mobile,action){
	
	$.post("/weixin/saveUserAction",{"openId":openId,"mobile":mobile,"action":action});
}