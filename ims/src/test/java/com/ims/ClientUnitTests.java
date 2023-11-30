package com.ims;

import com.ims.mapper.ClientMapper;
import com.ims.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ClientUnitTests {
  @Mock
  private ClientMapper clientMapper;

  @InjectMocks
  private ClientServiceImpl clientServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
//    clientServiceImpl = new ClientServiceImpl();
  }

  @Test
  void testIsValidEmail() {
    assertFalse(clientServiceImpl.isValidEmail(null));
    assertFalse(clientServiceImpl.isValidEmail("invalid"));
    assertFalse(clientServiceImpl.isValidEmail("invalid@"));
    assertFalse(clientServiceImpl.isValidEmail("inva.co"));
    assertFalse(clientServiceImpl.isValidEmail("@cu.com"));

    assertTrue(clientServiceImpl.isValidEmail("test@example.com"));
    assertTrue(clientServiceImpl.isValidEmail("t@ex.co"));
  }

  @Test
  void testIsValidPassword() {
    assertFalse(clientServiceImpl.isValidPassword(null));
    assertFalse(clientServiceImpl.isValidPassword("short"));
    assertFalse(clientServiceImpl.isValidPassword("short1"));
    assertFalse(clientServiceImpl.isValidPassword("12345"));
    assertFalse(clientServiceImpl.isValidPassword("Testtest"));
    assertFalse(clientServiceImpl.isValidPassword("12345678"));
    assertFalse(clientServiceImpl.isValidPassword("Goodpassword1!"));

    assertTrue(clientServiceImpl.isValidPassword("Goodpassword1"));
    assertTrue(clientServiceImpl.isValidPassword("Testtes1"));
  }

  @Test
  void testValidType() {
    assertNull(clientServiceImpl.validateType(null));

    assertEquals(clientServiceImpl.validateType("ReTail"), "retail");
    assertEquals(clientServiceImpl.validateType("retail"), "retail");
    assertEquals(clientServiceImpl.validateType("RETAIL"), "retail");
    assertEquals(clientServiceImpl.validateType("wArEhouse"), "warehouse");
    assertEquals(clientServiceImpl.validateType("WareHouse"), "warehouse");
    assertEquals(clientServiceImpl.validateType("warehouse"), "warehouse");
    assertNull(clientServiceImpl.validateType("warehousee"));
  }

  @Test
  void testClientExist() {
    when(clientMapper.emailExists("existing@example.com")).thenReturn(true);
    when(clientMapper.emailExists("nonexisting@example.com")).thenReturn(false);

    assertTrue(clientServiceImpl.clientExist("existing@example.com"));
    assertFalse(clientServiceImpl.clientExist("nonexisting@example.com"));
  }
}

