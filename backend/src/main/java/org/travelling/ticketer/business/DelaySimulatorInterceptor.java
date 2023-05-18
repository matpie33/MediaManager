package org.travelling.ticketer.business;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DelaySimulatorInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws InterruptedException {
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        if (handlerMethod.getMethod().getName().contains("UserPermissions")){
//            Thread.sleep(2000);
//        }
//        if (handlerMethod.getMethod().getName().contains("UserPersonalData")){
//            Thread.sleep(4000);
//        }
        return true;
    }
}
