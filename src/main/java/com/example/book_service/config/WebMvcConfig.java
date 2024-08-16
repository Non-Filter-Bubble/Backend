package com.example.book_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    /* CORS 설정 추가*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // React CORS 설정
        registry.addMapping("/**")  // 모든 경로에 대해 CORS 적용
//                .allowedOrigins("http://s3-nonfilterbubble-react.s3-website.ap-northeast-2.amazonaws.com") // react 애플리케이션의 도메인
                .allowedOrigins("http://s3-nonfilterbubble-react.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000")
//                .allowedOrigins("http://13.209.250.36:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS", "HEAD") // 허용할 HTTP 메서드 설정
                .allowedHeaders("*")  // 모든 헤더를 허용
                .exposedHeaders("Authorization")
                .allowCredentials(true);    // 자격 증명 허용 여부 설정

    }
}

//        // FastAPI CORS 설정
//        registry.addMapping("/**")
//                .allowedOrigins("http://43.200.64.238") // FastAPI 애플리케이션의 도메인
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")// 허용할 HTTP 메서드 설정
//                .allowCredentials(true);    // 자격 증명 허용 여부 설정
//    }
//
//}
