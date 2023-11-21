package com.asset.ccat.balancedisputemapperservice.comparator;

import com.asset.ccat.balancedisputemapperservice.defines.Defines.BALANCE_DISPUTE;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdTransactionDetailsComparator implements Comparator<String> {

  private final SimpleDateFormat formatter;

  @Autowired
  public BdTransactionDetailsComparator() {
    this.formatter = new SimpleDateFormat(BALANCE_DISPUTE.DATE_FORMAT_PATTERN);
  }

  @Override
  public int compare(String d1, String d2) {
    if (Objects.nonNull(d1) && Objects.nonNull(d2) && d1.matches("\\d{2}/\\d{2}/\\d{4}")
        && d2.matches("\\d{2}/\\d{2}/\\d{4}")) {
      //Valid date format
      Date date1 = null;
      Date date2 = null;
      try {
        date1 = formatter.parse(d1);
        date2 = formatter.parse(d2);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      assert date1 != null;
      return date1.compareTo(date2);
    }
    return d1.compareTo(d2);
  }
}
