package com.ssafy.soyu.util.payaction;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "pay.action")
public class PayActionProperties {
    private String apiKey;
    private String secretKey;
    private String storeId;
    private String orderUri;
}
