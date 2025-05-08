package com.example.oauthserver.oauth2.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2FaliureHandler extends SimpleUrlAuthenticationFailureHandler {
}
