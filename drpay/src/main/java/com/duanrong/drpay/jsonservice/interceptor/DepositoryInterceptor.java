package com.duanrong.drpay.jsonservice.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.duanrong.drpay.trusteeship.helper.sign.Sign;
import util.Log;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 存管通签名拦截器， 用于拦截存管通服务器通知，进行签名校验
 *
 * @author xiao
 * @version 1.0.0
 * @datetime 2016-12-8 9:27
 */
public class DepositoryInterceptor implements HandlerInterceptor {

    @Resource
    Log log;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object o)
            throws Exception {
        String data = request.getParameter("respData");
        String sign = request.getParameter("sign");
        log.infoLog("接收到存管系统S2S通知",sign+"," + data);
        if(StringUtils.isAnyBlank(data, sign)) {
            log.errLog("存管通验签错误", "响应参数错误, sign=" + sign + ", respData=" + data);
            return false;
        }
        return Sign.verify(sign, data);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object o, ModelAndView modelAndView)
            throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object o, Exception e) throws Exception {

    }
}
