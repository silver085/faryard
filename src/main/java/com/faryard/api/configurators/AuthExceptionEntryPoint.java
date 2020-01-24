package com.faryard.api.configurators;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    private String msg;

    public AuthExceptionEntryPoint(String msg) {
        this.msg = msg;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        final Map<String, Object> mapBodyException = new HashMap<>() ;

        mapBodyException.put("error"    , msg);
        mapBodyException.put("message"  ,  msg);
        mapBodyException.put("exception",  msg);
        mapBodyException.put("path"     , httpServletRequest.getServletPath()) ;
        mapBodyException.put("timestamp", (new Date()).getTime()) ;

        httpServletResponse.setContentType("application/json") ;
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;

        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(httpServletResponse.getOutputStream(), mapBodyException) ;
    }
}
