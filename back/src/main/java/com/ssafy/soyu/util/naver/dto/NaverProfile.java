package com.ssafy.soyu.util.naver.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverProfile {
    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String email;
        private String nickname;
        private String name;
    }

    public String getEmail() {
        return response.email;
    }

    public String getNickname() {
        return response.nickname;
    }

    public String getName(){
        return response.name;
    }

}
