package com.ssafy.soyu.util.naver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.naver")
public class NaverProperties {
    private String clientId;
    private String clientSecret;
    private String authorizationGrantType;
    private String state;
    private String redirectUri;
}
