//package com.ssafy.soyu.global.config;
//
//import com.ssafy.soyu.global.filter.NonMemberExceptionFilter;
//import com.ssafy.soyu.global.filter.JwtAuthenticationFilter;
//import com.ssafy.soyu.util.jwt.JwtTokenProvider;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
//        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new JwtAuthenticationFilter(jwtTokenProvider));
//        registrationBean.addUrlPatterns("/*"); // 모든 URL에 필터를 적용
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<NonMemberExceptionFilter> nonMemberExceptionFilter() {
//        FilterRegistrationBean<NonMemberExceptionFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new NonMemberExceptionFilter());
//        registrationBean.addUrlPatterns("/*"); // 모든 URL에 필터를 적용
//        return registrationBean;
//    }
//}
