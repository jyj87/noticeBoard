package study.noticeBoard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import study.noticeBoard.security.LoginUserArgumentResolver;

import java.util.List;

/**
 * LoginUserArgumentResolver가 인식될 수 있도록 WebMvcConfigurer에 추가
 */
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolver) {
        argumentResolver.add(loginUserArgumentResolver);
    }
}

