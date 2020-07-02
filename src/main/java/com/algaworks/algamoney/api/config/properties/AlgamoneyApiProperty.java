package com.algaworks.algamoney.api.config.properties;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

    private String permittedOrigin;

    @Setter(AccessLevel.NONE)
    private final Security security = new Security();

    @Data
    public static class Security {
        private boolean enableHttps;
    }
}
