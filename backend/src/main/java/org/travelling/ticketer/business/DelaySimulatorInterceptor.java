package org.travelling.ticketer.business;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DelaySimulatorInterceptor implements HandlerInterceptor {
    @SuppressWarnings("FieldCanBeLocal")
    private final boolean enabled = false;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws InterruptedException {
        if (!enabled){
            return true;
        }

        if (handler instanceof HandlerMethod){

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethod().getName().contains("UserPermissions")){
                Thread.sleep(2000);
            }
            else if (handlerMethod.getMethod().getName().contains("UserPersonalData")){
                Thread.sleep(4000);
            }
            else{
                Thread.sleep(3000);
            }
        }

        return true;
    }
}
