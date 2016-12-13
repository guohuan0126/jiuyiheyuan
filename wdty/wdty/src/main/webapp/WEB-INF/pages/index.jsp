<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="base.jsp"%>
<script type="text/javascript">

$(function(){
	//初始化
	activityInit('${openId}','${mobile}','${weixinConfig}');
});

</script>
<c:if test="${!empty json}">
<script type="text/javascript">
$(function(){
	awardjson = ${json};
	setp = "${setp}";
	if(setp == 3){
		showAward(awardjson.firstJSON); //初始化奖品显示
		showAward(awardjson.secondJSON); //初始化奖品显示
		$("#sectionOne").hide();
		$("#showAwardDiv,#sectionTwo").show();
	}else if(setp == 2){
		showResult(awardjson.firstJSON);
		showAward(awardjson.firstJSON); //初始化奖品显示
	}
});
</script>
</c:if>
</head>
<body>
<div>
	<section class="section-top">
    	<div  class="section-top-scroll">
        <div id="li-tabs1" class="swiper-container" >
        	<ul class="swiper-wrapper" >
      	   <c:forEach items="${showGetList }" var="p" >
      	     <li class="swiper-slide" ><c:choose><c:when test="${fn:length(p.mobile) >= 11}"><c:out value="${fn:substring(p.mobile, 0, 3)}****${fn:substring(p.mobile, 7, 11)}" /></c:when><c:otherwise> <c:out value="${p.mobile}" /> </c:otherwise></c:choose>刮中<fmt:formatNumber value="${p.money }" pattern="##.##" />元现金</li>
      	   </c:forEach>
        	</ul>
        	<script type="text/javascript" src="/js/swiper.min.js"></script>
            <script>       
            var swiper = new Swiper('.swiper-container', {
                pagination: '.swiper-pagination',
                paginationClickable: true,
        		autoplay : 8000,
            });
         </script>
        </div></div>
    </section>
    <section><img src="/images/images-2.jpg" width="100%"></section>
    
     <!-- 刮奖区 -->
    <section id="sectionOne" class="section-change"  >
        <c:choose>
          <c:when test="${activityNotEnd }">
                 <!-- 第一步，点击按钮输入手机号 -->
		        <div id="telButDiv" class="index-but" ><input type="button" value="开始刮奖" id="telBut" /></div>
		        <!-- 第三步，刮奖 -->
		        <div id="lotteryContainer" style="display:none;" ></div>
          </c:when>
          <c:otherwise>
             <p class="section-over">活动已结束！</p>
          </c:otherwise>
        </c:choose>
    </section>
    
    <!-- 奖品区 -->
    <section id="sectionTwo" class="section-change section-change1" style="display:none;"  >
    <!-- 第一次刮完后的奖品，老用户 -->
        <div id="redAward" class="index-prize" style="display:none;" >
        	<p>恭喜您获得<b>20元红包</b>奖励已放到<font>12556445556</font>账户</p>
            <div class="index-prize-but">
            	<input value="查看奖品" type="button" id="showAwardBut01" />
                <input value="分享再次刮奖" type="button" class="but-share" id="shareBut01" />
            </div>
        </div>
        
        
      <!-- 第一次刮完奖，新用户 -->
        <div id="moneyAward" class="index-prize" style="display:none;">
        	<p>恭喜您获得<b>2元现金</b></p>
            <p>5天内开通短融网第三方账户即可领取</p>
            <p>现金已充值到<font>12556445556</font>账户</p>
            <!--  index-prize-but2 -->
            <div class="index-prize-but">
            	<input value="查看奖品" type="button" id="showAwardBut02" />
            	<input value="分享再次刮奖" type="button" class="but-share" id="shareBut02" />
            </div>
        </div>
        
         <!-- 第二次刮完后的奖品 -->
        <div id="showAwardDiv" class="index-prize-check prize-check-one"  style="display:none;">
        <!--当只刮一次奖的时候加prize-check-one的类名-->
           <div class="prize-check" style="display: none;" >
            	<span class="span-fl">加息券</span>
                <span class="span-fr">1%加息券</span>
            </div>
            <div class="prize-check" style="display: none;">
            	<span class="span-fl">红包</span>
                <span class="span-fr">20元现金红包</span>
            </div>
            <p style="margin-top: 7px;">奖品已放入${mobile }帐户</p>
            <div class="index-prize-but">
            	<input value="登录" type="button" id="goLoginBut" />
            	<input value="返回" type="button" id="goBackBut" />
            </div>
            
        </div>
        <!--查看奖品 end-->
    </section>
  <section><img src="/images/images-4.jpg" width="100%"></section>
  <section class="section-bottom">
  	<input type="button" id="showRuleBut" />
  </section>
</div>
<!-- 第二步，输入手机号，验证用户 -->
<div class="zhezhao" style="display:none;" id="click-tel">
	<div class="zhezhao-all">
    	<div class="zhezhao-all-tel">
        	<span>手机号：</span><input type="text" id="mobileInput" placeholder="请输入手机号" maxlength="11"  />
        </div>
        <div class="zhezhao-all-but">
        <input type="button" value="开始刮奖" id="checkMobileBut" />
        </div>
        <div id="closePhoneDiv" class="zhezhao-close"><img src="/images/images-14.png" width="28" height="28"></div>
    </div>
</div>

<div class="zhezhao" id="showRuleDiv"  style="display:none;">
	<div class="zhezhao-text">
    	<div class="zhezhao-text-con">
        <div class="text-con">
        	<div class="text-con-top"><img src="/images/images-15.png" width="150" height="49"></div>
       	  <p><span class="num">1、</span><span class="txt">活动时间：2016.5.18--2016.5.20</span></p>
			<p><span class="num">2、</span><span class="txt">奖品设置： 1%加息券，有效期15天</span></p>
            <p class="txt-indent">50元红包，有效期15天</p>
            <p class="txt-indent">30元红包，有效期15天</p>
            <p class="txt-indent">20元红包，有效期15天</p>
            <p class="txt-indent">10元红包，有效期7天</p>
            <p class="txt-indent">随机现金</p>
            <p><span class="num">3、</span><span class="txt">加息券、红包、现金将发放至活动输入的手机号，使用该手机号登陆短融网即可使用；</span></p>
            <p><span class="num">4、</span><span class="txt">现金需用户自领取起5天内开通第三方资金托管后（需用领取现金手机号）才能到账，过期视为放弃奖品；</span></p>
            <p><span class="num">5、</span><span class="txt">加息券、红包查询路径：我的账户--优惠券</span></p>
            <p><span class="num">6、</span><span clas="txt">现金查询路径：我的账户—可用余额</span></p>
            <p><span class="num">7、</span><span class="txt">红包、加息券不可用于新手标、天天赚、10天项目</span></p>
            <p><span class="num">8、</span><span class="txt">同一用户限刮奖2次。（同一手机号、微信号、终端设备、短融网账号、身份证号、银行卡其中任意一项存在相同或非真实有效等情况，均可能被认定为同一用户）</span></p>
            <p><span class="num">9、</span><span class="txt">本次活动最终解释权归短融网所有。</span></p>
        </div>
        </div>
        <div id="showRuleClose" class="zhezhao-close zhezhao-close1"><img src="/images/images-14.png" width="28" height="28"></div>
    </div>
</div>
<!-- 分享到朋友弹层 -->
<div class="zhezhao" style="display:none;" id="share-box">
	<div class="zhezhao-share">
    	<img src="/images/images-16.png" width="110" height="134" />
    </div>
    <p class="share-txt">点击右上角“分享”</p>
</div>
</body>
</html>