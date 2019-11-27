package com.component.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by zhanglong and since  2019/11/26  5:32 下午
 *
 * @description: 描述
 */
public final class WebUtils {

    private static final ThreadLocal<WebContext> threadContext = new ThreadLocal();

    private WebUtils() {}

    public static WebContext getContext() {
        WebContext webContext = threadContext.get();
        return webContext;
    }

    public static HttpServletRequest getRequest() {
        WebContext webContext = getContext();
        return webContext == null ? null : webContext.request;
    }

    public static HttpServletResponse getResponse() {
        WebContext webContext = getContext();
        return webContext == null ? null : webContext.response;
    }

    public static void bindContext(HttpServletRequest request, HttpServletResponse response) {
        threadContext.set(new WebContext(request, response));
    }

    public static void clearContext() {
        threadContext.remove();
    }

//    public static String getBaseUrl() {
//        if (!ContextUtils.isWeb()) {
//            return "";
//        } else {
//            WebServer webServer = ContextUtils.getConfigurableWebServerApplicationContext().getWebServer();
//            return webServer == null ? "" : "http://" + NetworkUtils.getIpAddress() + ":" + webServer.getPort() + PropertyUtils.getPropertyCache("server.servlet.context-path", "");
//        }
//    }
    public static class WebContext {

        private HttpServletRequest request;
        private HttpServletResponse response;

        WebContext(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }
    }
}
