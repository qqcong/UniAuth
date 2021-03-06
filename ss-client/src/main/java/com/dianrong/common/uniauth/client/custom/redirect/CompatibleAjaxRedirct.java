package com.dianrong.common.uniauth.client.custom.redirect;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;

import com.dianrong.common.uniauth.client.custom.model.JsonResponseModel;
import com.dianrong.common.uniauth.common.util.HttpRequestUtil;
import com.dianrong.common.uniauth.common.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 该跳转策略兼容处理ajax请求
 * 
 * @author wanglin
 */
@Slf4j
public class CompatibleAjaxRedirct extends DefaultRedirectStrategy {
    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        if (HttpRequestUtil.isAjaxRequest(request) || HttpRequestUtil.isCORSRequest(request)) {
            log.warn("current request is a ajax request, redirect strategy write json value to response. the normal rediret url is {}", url);
            response.setContentType("application/json;charset=UTF-8");
            response.addHeader("Cache-Control", "no-store");
            response.getWriter().write(JsonUtil.object2Jason(JsonResponseModel.success()));
            response.flushBuffer();
            return;
        }
        super.sendRedirect(request, response, url);
    }
}
