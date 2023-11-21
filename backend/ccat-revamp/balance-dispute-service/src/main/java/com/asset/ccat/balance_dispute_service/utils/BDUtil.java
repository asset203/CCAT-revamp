package com.asset.ccat.balance_dispute_service.utils;

import org.springframework.stereotype.Component;

@Component
public class BDUtil {

  public static Integer isInteger(String string) {
    try {
      return Integer.parseInt(string);
    } catch (Exception ex) {
      return null;
    }
  }

  public static Double isDouble(String string) {
    try {
      return Double.parseDouble(string);
    } catch (Exception ex) {
      return null;
    }
  }
}
