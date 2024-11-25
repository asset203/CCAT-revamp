package com.asset.ccat.gateway.models.customer_care;

import java.util.ArrayList;
import java.util.List;

public class GetPrepaidProfileResponse {

  protected byte responseCode;
  protected GetPrepaidProfileResponse.TARIFFINFO tariffinfo;
  protected String summary;
  protected GetPrepaidProfileResponse.Products products;

  public byte getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(byte value) {
    this.responseCode = value;
  }

  public GetPrepaidProfileResponse.TARIFFINFO getTARIFFINFO() {
    return tariffinfo;
  }

  public void setTARIFFINFO(GetPrepaidProfileResponse.TARIFFINFO value) {
    this.tariffinfo = value;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String value) {
    this.summary = value;
  }

  public GetPrepaidProfileResponse.Products getProducts() {
    return products;
  }

  public void setProducts(GetPrepaidProfileResponse.Products value) {
    this.products = value;
  }

  public static class Products {

    protected List<GetPrepaidProfileResponse.Products.Product> product;

    public List<GetPrepaidProfileResponse.Products.Product> getProduct() {
      if (product == null) {
        product = new ArrayList<>();
      }
      return this.product;
    }

    public static class Product {

      protected String productId;
      protected String productName;
      protected String productType;
      protected String productStatus;
      protected String productRecurrence;
      protected Long productStartDate;
      protected Long productExpiryDate;
      protected Long productRenewalDate;
      protected String currentDlSpeed;
      protected String currentUlSpeed;
      protected GetPrepaidProfileResponse.Products.Product.FAFMemebers fafmemebers;
      protected List<GetPrepaidProfileResponse.Products.Product.Quota> quotas;

      public String getProductId() {

        return productId;
      }

      public void setProductId(String value) {
        this.productId = value;
      }

      public String getProductName() {
        return productName;
      }

      public void setProductName(String value) {
        this.productName = value;
      }

      public String getProductType() {
        return productType;
      }

      public void setProductType(String value) {
        this.productType = value;
      }

      public String getProductStatus() {
        return productStatus;
      }

      public void setProductStatus(String value) {
        this.productStatus = value;
      }

      public String getProductRecurrence() {
        return productRecurrence;
      }

      public void setProductRecurrence(String value) {
        this.productRecurrence = value;
      }

      public Long getProductStartDate() {
        return productStartDate;
      }

      public void setProductStartDate(Long productStartDate) {
        this.productStartDate = productStartDate;
      }

      public Long getProductExpiryDate() {
        return productExpiryDate;
      }

      public void setProductExpiryDate(Long productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
      }

      public Long getProductRenewalDate() {
        return productRenewalDate;
      }

      public void setProductRenewalDate(Long productRenewalDate) {
        this.productRenewalDate = productRenewalDate;
      }

      public String getCurrentDlSpeed() {
        return currentDlSpeed;
      }

      public void setCurrentDlSpeed(String value) {
        this.currentDlSpeed = value;
      }

      public String getCurrentUlSpeed() {
        return currentUlSpeed;
      }

      public void setCurrentUlSpeed(String value) {
        this.currentUlSpeed = value;
      }

      public GetPrepaidProfileResponse.Products.Product.FAFMemebers getFafmemebers() {
        return fafmemebers;
      }

      public void setFafmemebers(
          GetPrepaidProfileResponse.Products.Product.FAFMemebers fafmemebers) {
        this.fafmemebers = fafmemebers;
      }

      public List<GetPrepaidProfileResponse.Products.Product.Quota> getQuotas() {
        if (quotas == null) {
          quotas = new ArrayList<>();
        }
        return this.quotas;
      }

      public void setQuotas(List<Quota> quotas) {
        this.quotas = quotas;
      }

      public static class FAFMemebers {

        protected List<String> msisdn;

        public List<String> getMsisdn() {
          return msisdn;
        }

        public void setMsisdn(List<String> msisdn) {
          this.msisdn = msisdn;
        }
      }

      public static class Quota {

        protected String quotaName;
        protected String quotaType;
        protected String quotaUnit;
        protected float maxQuota;
        protected float remainingQuota;
        protected float consumedQuota;
        protected String location;

        public String getQuotaName() {
          return quotaName;
        }

        public void setQuotaName(String quotaName) {
          this.quotaName = quotaName;
        }

        public String getQuotaType() {
          return quotaType;
        }

        public void setQuotaType(String quotaType) {
          this.quotaType = quotaType;
        }

        public String getQuotaUnit() {
          return quotaUnit;
        }

        public void setQuotaUnit(String quotaUnit) {
          this.quotaUnit = quotaUnit;
        }

        public float getMaxQuota() {
          return maxQuota;
        }

        public void setMaxQuota(float maxQuota) {
          this.maxQuota = maxQuota;
        }

        public float getRemainingQuota() {
          return remainingQuota;
        }

        public void setRemainingQuota(float remainingQuota) {
          this.remainingQuota = remainingQuota;
        }

        public float getConsumedQuota() {
          return consumedQuota;
        }

        public void setConsumedQuota(float consumedQuota) {
          this.consumedQuota = consumedQuota;
        }

        public String getLocation() {
          return location;
        }

        public void setLocation(String location) {
          this.location = location;
        }

      }

    }

  }

  public static class TARIFFINFO {

    protected short serviceClassID;
    protected int languageID;
    protected Long creditClearanceDate;
    protected Long serviceRemovalDate;
    protected Long supervisionExpiryDate;
    protected Long serviceFeeExpiryDate;
    protected String rxFlag;
    protected short billCycle;
    protected GetPrepaidProfileResponse.TARIFFINFO.Balances balances;
    protected GetPrepaidProfileResponse.TARIFFINFO.Property property;
    protected String pamInformationList;

    public short getServiceClassID() {
      return serviceClassID;
    }

    public void setServiceClassID(short serviceClassID) {
      this.serviceClassID = serviceClassID;
    }

    public int getLanguageID() {
      return languageID;
    }

    public void setLanguageID(int languageID) {
      this.languageID = languageID;
    }

    public Long getCreditClearanceDate() {
      return creditClearanceDate;
    }

    public void setCreditClearanceDate(Long creditClearanceDate) {
      this.creditClearanceDate = creditClearanceDate;
    }

    public Long getServiceRemovalDate() {
      return serviceRemovalDate;
    }

    public void setServiceRemovalDate(Long serviceRemovalDate) {
      this.serviceRemovalDate = serviceRemovalDate;
    }

    public Long getSupervisionExpiryDate() {
      return supervisionExpiryDate;
    }

    public void setSupervisionExpiryDate(Long supervisionExpiryDate) {
      this.supervisionExpiryDate = supervisionExpiryDate;
    }

    public Long getServiceFeeExpiryDate() {
      return serviceFeeExpiryDate;
    }

    public void setServiceFeeExpiryDate(Long serviceFeeExpiryDate) {
      this.serviceFeeExpiryDate = serviceFeeExpiryDate;
    }

    public String getRxFlag() {
      return rxFlag;
    }

    public void setRxFlag(String rxFlag) {
      this.rxFlag = rxFlag;
    }

    public short getBillCycle() {
      return billCycle;
    }

    public void setBillCycle(short billCycle) {
      this.billCycle = billCycle;
    }

    public Balances getBalances() {
      return balances;
    }

    public void setBalances(Balances balances) {
      this.balances = balances;
    }

    public Property getProperty() {
      return property;
    }

    public void setProperty(Property property) {
      this.property = property;
    }

    public String getPamInformationList() {
      return pamInformationList;
    }

    public void setPamInformationList(String pamInformationList) {
      this.pamInformationList = pamInformationList;
    }

    public static class Balances {

      protected short rechargeBalance;
      protected byte creditLimit;
      protected short remainingLimit;
      protected short consumedLimit;

      public short getRechargeBalance() {
        return rechargeBalance;
      }

      public void setRechargeBalance(short rechargeBalance) {
        this.rechargeBalance = rechargeBalance;
      }

      public byte getCreditLimit() {
        return creditLimit;
      }

      public void setCreditLimit(byte creditLimit) {
        this.creditLimit = creditLimit;
      }

      public short getRemainingLimit() {
        return remainingLimit;
      }

      public void setRemainingLimit(short remainingLimit) {
        this.remainingLimit = remainingLimit;
      }

      public short getConsumedLimit() {
        return consumedLimit;
      }

      public void setConsumedLimit(short consumedLimit) {
        this.consumedLimit = consumedLimit;
      }

    }

    public static class Property {

      protected String name;
      protected byte value;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public byte getValue() {
        return value;
      }

      public void setValue(byte value) {
        this.value = value;
      }

    }

  }

}
