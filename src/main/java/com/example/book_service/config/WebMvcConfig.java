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
                .allowedOrigins("http://43.203.38.124","http://43.200.64.238:8000") // react 애플리케이션의 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD") // 허용할 HTTP 메서드 설정
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
