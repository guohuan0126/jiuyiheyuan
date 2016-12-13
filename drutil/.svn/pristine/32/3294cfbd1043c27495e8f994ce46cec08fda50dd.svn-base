package com.duanrong.util.client;

import org.apache.http.*;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * httpClient响应gzip解压拦截器
 * Created by xiao on 2016/4/22.
 */
public class GzipHttpResponseInterveptor implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            Header ceheader = entity.getContentEncoding();
            if (ceheader != null) {
                HeaderElement[] codecs = ceheader.getElements();
                for (int i = 0; i < codecs.length; i++) {
                    if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                        httpResponse.setEntity(new GzipDecompressingEntity(httpResponse.getEntity()));
                        return;
                    }
                }
            }
        }
    }
}
