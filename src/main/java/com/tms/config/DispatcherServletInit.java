package com.tms.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Этот метод возвращает массив классов конфигурации корневого контекста приложения
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    // Этот метод возвращает массив классов конфигурации Dispatcher Servlet (DS)
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    // Этот метод возвращает массив URL для DS
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
