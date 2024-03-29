package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.service.MemberService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        if (httpServletRequest.getSession() != null) {
            String userAgent = httpServletRequest.getHeader("user-agent");
            String ip = httpServletRequest.getRemoteAddr();

            memberService.saveLoginHistory(authentication.getName(), ip, userAgent);
        }

        httpServletResponse.sendRedirect("/");
    }
}
