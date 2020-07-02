package com.algaworks.algamoney.api.token;

import com.algaworks.algamoney.api.config.properties.AlgamoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Autowired
    private AlgamoneyApiProperty algamoneyApiProperty;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getMethod().getName().equals("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
                                             MediaType selectedContentType,
                                             Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                             ServerHttpRequest request, ServerHttpResponse response) {

        var req = ((ServletServerHttpRequest) request).getServletRequest();
        var resp = ((ServletServerHttpResponse) response).getServletResponse();
        var token = (DefaultOAuth2AccessToken) body;

        String refreshToken = body.getRefreshToken().getValue();
        addRefreshTokenCookie(refreshToken, req, resp);
        removeRefreshTokenCookie(token);

        return body;
    }

    private void removeRefreshTokenCookie(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }

    private void addRefreshTokenCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
        var refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(algamoneyApiProperty.getSecurity().isEnableHttps());
        refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
        refreshTokenCookie.setMaxAge(2592000);

        resp.addCookie(refreshTokenCookie);
    }
}
