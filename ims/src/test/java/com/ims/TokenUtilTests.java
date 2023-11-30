package com.ims;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import com.ims.security.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import com.ims.entity.Client;

import javax.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class TokenUtilTests {

  @Mock
  private Environment env;

  private TokenUtil tokenUtil;

  private Client testClient;

  @BeforeEach
  public void setUp() {
    // Mock env
    when(env.getProperty("TOKENSECRETKEY")).thenReturn(
            "supersupersupersupersupersupersupersupersupersupersupersupersecretkey");

    tokenUtil = new TokenUtil(env);

    // Setup test client
    testClient = new Client();
    testClient.setClientType("retail");
    testClient.setClientId(100);
    testClient.setEmail("123@cu.com");
    testClient.setPassword("Test1234");
  }

  @Test
  public void testCreateAndValidateToken() {
    String token = tokenUtil.createToken(testClient);
    assertNotNull(token);
    assertTrue(tokenUtil.validateToken(token));
  }

  @Test
  public void testExtractToken() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn("Bearer dummytoken");

    assertEquals("dummytoken", tokenUtil.extractToken(request));
  }

  @Test
  public void testGetEmail() {
    String token = tokenUtil.createToken(testClient);
    assertEquals(testClient.getEmail(), tokenUtil.getEmail(token));
  }

  @Test
  public void testGetClientType() {
    String token = tokenUtil.createToken(testClient);
    assertEquals(testClient.getClientType(), tokenUtil.getClientType(token));
  }

  @Test
  public void testGetClientId() {
    String token = tokenUtil.createToken(testClient);
    assertEquals(testClient.getClientId(), tokenUtil.getClientId(token));
  }

  @Test
  public void testInvalidToken() {
    tokenUtil.setExp(1);
    String token = tokenUtil.createToken(testClient);
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    assertFalse(tokenUtil.validateToken(token));
    tokenUtil.setExp(3600000); // reset exp for other tests potentially
  }
}