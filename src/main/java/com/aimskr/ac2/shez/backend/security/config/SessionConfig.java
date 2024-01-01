package com.aimskr.ac2.kakao.backend.security.config;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 중복 로그인 처리를 위한 리스너
 */
@WebListener
public class SessionConfig implements HttpSessionListener {

    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public synchronized static String getSessionidCheck(String type,String compareId){
        String result = "";
        for( String key : sessions.keySet() ){
            HttpSession value = sessions.get(key);
            if(value != null &&  value.getAttribute(type) != null
                    && value.getAttribute(type).toString().equals(compareId) ){
                result =  key.toString();
            }
        }
        removeSessionForDoubleLogin(result);
        return result;
    }

    private static void removeSessionForDoubleLogin(String userId){
        if(userId != null && userId.length() > 0){
            sessions.get(userId).invalidate();
            sessions.remove(userId);
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        sessions.put(hse.getSession().getId(), hse.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        if(sessions.get(hse.getSession().getId()) != null){
            sessions.get(hse.getSession().getId()).invalidate();
            sessions.remove(hse.getSession().getId());
        }
    }
}