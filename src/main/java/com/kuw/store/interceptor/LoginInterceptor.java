package com.kuw.store.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// 拦截器
public class LoginInterceptor implements HandlerInterceptor {

    /**
     *  检测全局session对象中是否有uid
     *  有则放行，没有的话从定向到登录页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（URL + Controller: 映射）
     * @return 返回值为true则放行，false表示拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // HttpServletRequest对象获取session对象
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null){
            //如果用户没有登录，则从定向到登录页面
            response.sendRedirect("/web/login.html");
            // 结束调用
            return false;
        }
        // 放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
