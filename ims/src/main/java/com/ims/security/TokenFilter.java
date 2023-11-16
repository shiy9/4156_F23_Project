package com.ims.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ims.constants.ClientConstants;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Bypass token validation for specific paths since this filter is being applied to all
    // requests
    if ("/client/login".equals(requestUri) || "/client/register".equals(requestUri) ||
        "/client/clienttestdelete".equals(requestUri)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = request.getHeader("Authorization");
    boolean valid = false;

    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
      valid = tokenUtil.validateToken(token);
    }

    if (!valid) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("text/plain");
      response.getWriter().write(ClientConstants.TOKEN_EXPIRED);
      response.getWriter().flush();
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
