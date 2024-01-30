package com.ssafy.soyu.util.soyu;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "soyu")
public class SoyuProperties {
    private String bankName;
    private String accountNumber;

}
