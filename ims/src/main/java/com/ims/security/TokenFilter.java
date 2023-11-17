package com.ims.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ims.constants.ClientConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class TokenFilter extends OncePerRequestFilter {

  @Autowired
  private TokenUtil tokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String requestUri = request.getRequestURI();

    // Bypass token validation for client-related endpoints
    if (requestUri.startsWith("/client")) {
      filterChain.doFilter(request, response);
      return;
    }

    // To make sure we only check the token for supported endpoints
    List<String> supportedPrefix = Arrays.asList("/location", "/item", "/itemLocation", "/order");
    boolean supported = false;
    for (String prefix : supportedPrefix) {
      if (requestUri.startsWith(prefix)) {
        supported = true;
        break;
      }
    }
    if (!supported) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.setContentType("text/plain");
      response.getWriter().write("Endpoint not provided by service.");
      response.getWriter().flush();
      return;
    }

    String token = request.getHeader("Authorization");
    boolean valid = false;

    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
      valid = tokenUtil.validateToken(token);
      String type = tokenUtil.getClientType(token);
      System.out.print(type);
    }

    if (!valid) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("text/plain");
      response.getWriter().write(ClientConstants.INVALID_TOKEN);
      response.getWriter().flush();
    } else {
      String clientType = tokenUtil.getClientType(token);
      UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(null, null,
                      Collections.singletonList(new SimpleGrantedAuthority(clientType)));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);
    }
  }
}
