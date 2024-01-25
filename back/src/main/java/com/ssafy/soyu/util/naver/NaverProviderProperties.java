package com.ssafy.soyu.util.naver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.naver")
public class NaverProviderProperties {
    private String authorizationUri;
    private String tokenUri;
    private String userInfoUri;
}
