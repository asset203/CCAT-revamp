package com.asset.ccat.ci_service.models.responses;

import com.asset.ccat.ci_service.adapters.ProductDateAdapter;
import com.asset.ccat.ci_service.adapters.StringAdapter;
import com.asset.ccat.ci_service.adapters.TarrifDateAdapter;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.springframework.cloud.client.loadbalancer.Response;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Response_Code" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *         &lt;element name="TARIFF_INFO">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ServiceClassID" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                   &lt;element name="LanguageID" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                   &lt;element name="creditClearanceDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="serviceRemovalDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="supervisionExpiryDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="serviceFeeExpiryDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="RX_Flag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BillCycle" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                   &lt;element name="Balances">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="RechargeBalance" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                             &lt;element name="CreditLimit" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="RemainingLimit" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                             &lt;element name="ConsumedLimit" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Property">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="pamInformationList" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Summary" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Products">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Product" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ProductID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ProductName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ProductType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ProductStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ProductRECURRENCE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ProductStartDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ProductExpiryDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ProductRenewalDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CurrentDlSpeed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CurrentUlSpeed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Quota" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="QuotaName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="QuotaType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="QuotaUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="MaxQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                       &lt;element name="RemainingQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                       &lt;element name="ConsumedQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                       &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "responseCode",
    "tariffinfo",
    "summary",
    "products"
})
@XmlRootElement(name = "Response")
public class GetPrepaidProfileResponse {

  @XmlElement(name = "Response_Code")
  protected byte responseCode;
  @XmlElement(name = "TARIFF_INFO", required = true)
  protected GetPrepaidProfileResponse.TARIFFINFO tariffinfo;
  @XmlElement(name = "Summary", required = true)
  protected String summary;
  @XmlElement(name = "Products", required = true)
  protected GetPrepaidProfileResponse.Products products;

  /**
   * Gets the value of the responseCode property.
   *
   * @return
   */
  public byte getResponseCode() {
    return responseCode;
  }

  /**
   * Sets the value of the responseCode property.
   *
   * @param value
   */
  public void setResponseCode(byte value) {
    this.responseCode = value;
  }

  /**
   * Gets the value of the tariffinfo property.
   *
   * @return possible object is {@link Response.TARIFFINFO }
   */
  public GetPrepaidProfileResponse.TARIFFINFO getTARIFFINFO() {
    return tariffinfo;
  }

  /**
   * Sets the value of the tariffinfo property.
   *
   * @param value allowed object is {@link Response.TARIFFINFO }
   */
  public void setTARIFFINFO(GetPrepaidProfileResponse.TARIFFINFO value) {
    this.tariffinfo = value;
  }

  /**
   * Gets the value of the summary property.
   *
   * @return possible object is {@link String }
   */
  public String getSummary() {
    return summary;
  }

  /**
   * Sets the value of the summary property.
   *
   * @param value allowed object is {@link String }
   */
  public void setSummary(String value) {
    this.summary = value;
  }

  /**
   * Gets the value of the products property.
   *
   * @return possible object is {@link Response.Products }
   */
  public GetPrepaidProfileResponse.Products getProducts() {
    return products;
  }

  /**
   * Sets the value of the products property.
   *
   * @param value allowed object is {@link Response.Products }
   */
  public void setProducts(GetPrepaidProfileResponse.Products value) {
    this.products = value;
  }

  /**
   * <p>
   * Java class for anonymous complex type.
   *
   * <p>
   * The following schema fragment specifies the expected content contained within this class.
   *
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;sequence>
   *         &lt;element name="Product" maxOccurs="unbounded" minOccurs="0">
   *           &lt;complexType>
   *             &lt;complexContent>
   *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *                 &lt;sequence>
   *                   &lt;element name="ProductID" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="ProductName" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="ProductType" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="ProductStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="ProductRECURRENCE" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="ProductStartDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="ProductExpiryDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="ProductRenewalDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="CurrentDlSpeed" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="CurrentUlSpeed" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="Quota" maxOccurs="unbounded" minOccurs="0">
   *                     &lt;complexType>
   *                       &lt;complexContent>
   *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *                           &lt;sequence>
   *                             &lt;element name="QuotaName" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                             &lt;element name="QuotaType" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                             &lt;element name="QuotaUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                             &lt;element name="MaxQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
   *                             &lt;element name="RemainingQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
   *                             &lt;element name="ConsumedQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
   *                             &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                           &lt;/sequence>
   *                         &lt;/restriction>
   *                       &lt;/complexContent>
   *                     &lt;/complexType>
   *                   &lt;/element>
   *                 &lt;/sequence>
   *               &lt;/restriction>
   *             &lt;/complexContent>
   *           &lt;/complexType>
   *         &lt;/element>
   *       &lt;/sequence>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {
      "product"
  })
  public static class Products {

    @XmlElement(name = "Product")
    protected List<GetPrepaidProfileResponse.Products.Product> product;

    /**
     * Gets the value of the product property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a
     * <CODE>set</CODE> method for the product property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProduct().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Response.Products.Product }
     *
     * @return
     */
    public List<GetPrepaidProfileResponse.Products.Product> getProduct() {
      if (product == null) {
        product = new ArrayList<>();
      }
      return this.product;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ProductID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ProductName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ProductType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ProductStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ProductRECURRENCE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ProductStartDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ProductExpiryDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ProductRenewalDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CurrentDlSpeed" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CurrentUlSpeed" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Quota" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="QuotaName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="QuotaType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="QuotaUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="MaxQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="RemainingQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="ConsumedQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "productId",
        "productName",
        "productType",
        "productStatus",
        "productRecurrence",
        "productStartDate",
        "productExpiryDate",
        "productRenewalDate",
        "currentDlSpeed",
        "currentUlSpeed",
        "fafMemebers",
        "quotas"
    })
    public static class Product {

      @XmlElement(name = "ProductID", required = true)
      protected String productId;
      @XmlElement(name = "ProductName", required = true)
      protected String productName;
      @XmlElement(name = "ProductType", required = true)
      @XmlJavaTypeAdapter(StringAdapter.class)
      protected String productType;
      @XmlElement(name = "ProductStatus", required = true)
      @XmlJavaTypeAdapter(StringAdapter.class)
      protected String productStatus;
      @XmlElement(name = "ProductRECURRENCE", required = true)
      @XmlJavaTypeAdapter(StringAdapter.class)
      protected String productRecurrence;

      @XmlElement(name = "ProductStartDate", required = true)
      @XmlJavaTypeAdapter(ProductDateAdapter.class)
      protected Long productStartDate;

      @XmlElement(name = "ProductExpiryDate", required = true)
      @XmlJavaTypeAdapter(ProductDateAdapter.class)
      protected Long productExpiryDate;

      @XmlElement(name = "ProductRenewalDate", required = true)
      @XmlJavaTypeAdapter(ProductDateAdapter.class)
      protected Long productRenewalDate;

      @XmlElement(name = "CurrentDlSpeed", required = true)
      @XmlJavaTypeAdapter(StringAdapter.class)
      protected String currentDlSpeed;
      @XmlElement(name = "CurrentUlSpeed", required = true)
      @XmlJavaTypeAdapter(StringAdapter.class)
      protected String currentUlSpeed;
      @XmlElement(name = "FAFMemebers")
      protected GetPrepaidProfileResponse.Products.Product.FAFMemebers fafMemebers;
      @XmlElement(name = "Quota")
      protected List<GetPrepaidProfileResponse.Products.Product.Quota> quotas;

      /**
       * Gets the value of the productId property.
       *
       * @return possible object is {@link String }
       */
      public String getProductId() {
        return productId;
      }

      /**
       * Sets the value of the productId property.
       *
       * @param value allowed object is {@link String }
       */
      public void setProductId(String value) {
        this.productId = value;
      }

      /**
       * Gets the value of the productName property.
       *
       * @return possible object is {@link String }
       */
      public String getProductName() {
        return productName;
      }

      /**
       * Sets the value of the productName property.
       *
       * @param value allowed object is {@link String }
       */
      public void setProductName(String value) {
        this.productName = value;
      }

      /**
       * Gets the value of the productType property.
       *
       * @return possible object is {@link String }
       */
      public String getProductType() {
        return productType;
      }

      /**
       * Sets the value of the productType property.
       *
       * @param value allowed object is {@link String }
       */
      public void setProductType(String value) {
        this.productType = value;
      }

      /**
       * Gets the value of the productStatus property.
       *
       * @return possible object is {@link String }
       */
      public String getProductStatus() {
        return productStatus;
      }

      /**
       * Sets the value of the productStatus property.
       *
       * @param value allowed object is {@link String }
       */
      public void setProductStatus(String value) {
        this.productStatus = value;
      }

      /**
       * Gets the value of the productRecurrence property.
       *
       * @return possible object is {@link String }
       */
      public String getProductRecurrence() {
        return productRecurrence;
      }

      /**
       * Sets the value of the productRecurrence property.
       *
       * @param value allowed object is {@link String }
       */
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

      /**
       * Gets the value of the currentDlSpeed property.
       *
       * @return possible object is {@link String }
       */
      public String getCurrentDlSpeed() {
        return currentDlSpeed;
      }

      /**
       * Sets the value of the currentDlSpeed property.
       *
       * @param value allowed object is {@link String }
       */
      public void setCurrentDlSpeed(String value) {
        this.currentDlSpeed = value;
      }

      /**
       * Gets the value of the currentUlSpeed property.
       *
       * @return possible object is {@link String }
       */
      public String getCurrentUlSpeed() {
        return currentUlSpeed;
      }

      /**
       * Sets the value of the currentUlSpeed property.
       *
       * @param value allowed object is {@link String }
       */
      public void setCurrentUlSpeed(String value) {
        this.currentUlSpeed = value;
      }

      /**
       * Gets the value of the fafMemebers property.
       *
       * @return possible object is {@link FAFMemebers }
       */
      public FAFMemebers getFAFMemebers() {
        return fafMemebers;
      }

      /**
       * Sets the value of the fafMemebers property.
       *
       * @param value allowed object is {@link FAFMemebers }
       */
      public void setFAFMemebers(FAFMemebers value) {
        this.fafMemebers = value;
      }

      /**
       * Gets the value of the quota property.
       *
       * <p>
       * This accessor method returns a reference to the live list, not a snapshot. Therefore any
       * modification you make to the returned list will be present inside the JAXB object. This is
       * why there is not a <CODE>set method for the quotas property.
       *
       * <p>
       * For example, to add a new item, do as follows:
       * <pre>
       *    getQuota().add(newItem);
       * <p>
       * Objects of the following type(s) are allowed in the list
       * {@link Response.Products.Product.Quota }
       *
       *
       * @return
       */
      public List<GetPrepaidProfileResponse.Products.Product.Quota> getQuotas() {
        if (quotas == null) {
          quotas = new ArrayList<>();
        }
        return this.quotas;
      }

      /**
       * <p>Java class for anonymous complex type.
       *
       * <p>The following schema fragment specifies the expected content contained within this
       * class.
       *
       * <pre>
       * &lt;complexType>
       *   &lt;complexContent>
       *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
       *       &lt;sequence>
       *         &lt;element name="MSISDN" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
       *       &lt;/sequence>
       *     &lt;/restriction>
       *   &lt;/complexContent>
       * &lt;/complexType>
       * </pre>
       */
      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {
          "msisdn"
      })
      public static class FAFMemebers {

        @XmlElement(name = "MSISDN")
        protected List<String> msisdn;

        /**
         * Gets the value of the msisdn property.
         *
         * <p>
         * This accessor method returns a reference to the live list, not a snapshot. Therefore, any
         * modification you make to the returned list will be present inside the JAXB object. This
         * is why there is not a <CODE>set</CODE> method for the msisdn property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMsisdn().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list {@link String }
         */
        public List<String> getMsisdn() {
          if (msisdn == null) {
            msisdn = new ArrayList<>();
          }
          return this.msisdn;
        }

        public void setMsisdn(List<String> msisdn) {
          this.msisdn = msisdn;
        }
      }

      /**
       * <p>
       * Java class for anonymous complex type.
       *
       * <p>
       * The following schema fragment specifies the expected content contained within this class.
       *
       * <pre>
       * &lt;complexType>
       *   &lt;complexContent>
       *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
       *       &lt;sequence>
       *         &lt;element name="QuotaName" type="{http://www.w3.org/2001/XMLSchema}string"/>
       *         &lt;element name="QuotaType" type="{http://www.w3.org/2001/XMLSchema}string"/>
       *         &lt;element name="QuotaUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
       *         &lt;element name="MaxQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
       *         &lt;element name="RemainingQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
       *         &lt;element name="ConsumedQuota" type="{http://www.w3.org/2001/XMLSchema}float"/>
       *         &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string"/>
       *       &lt;/sequence>
       *     &lt;/restriction>
       *   &lt;/complexContent>
       * &lt;/complexType>
       * </pre>
       */
      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = {
          "quotaName",
          "quotaType",
          "quotaUnit",
          "maxQuota",
          "remainingQuota",
          "consumedQuota",
          "location"
      })
      public static class Quota {

        @XmlElement(name = "QuotaName", required = true)
        @XmlJavaTypeAdapter(StringAdapter.class)
        protected String quotaName;
        @XmlElement(name = "QuotaType", required = true)
        @XmlJavaTypeAdapter(StringAdapter.class)
        protected String quotaType;
        @XmlElement(name = "QuotaUnit", required = true)
        @XmlJavaTypeAdapter(StringAdapter.class)
        protected String quotaUnit;
        @XmlElement(name = "MaxQuota")
        protected float maxQuota;
        @XmlElement(name = "RemainingQuota")
        protected float remainingQuota;
        @XmlElement(name = "ConsumedQuota")
        protected float consumedQuota;
        @XmlElement(name = "Location", required = true)
        @XmlJavaTypeAdapter(StringAdapter.class)
        protected String location;

        /**
         * Gets the value of the quotaName property.
         *
         * @return possible object is {@link String }
         */
        public String getQuotaName() {
          return quotaName;
        }

        /**
         * Sets the value of the quotaName property.
         *
         * @param value allowed object is {@link String }
         */
        public void setQuotaName(String value) {
          this.quotaName = value;
        }

        /**
         * Gets the value of the quotaType property.
         *
         * @return possible object is {@link String }
         */
        public String getQuotaType() {
          return quotaType;
        }

        /**
         * Sets the value of the quotaType property.
         *
         * @param value allowed object is {@link String }
         */
        public void setQuotaType(String value) {
          this.quotaType = value;
        }

        /**
         * Gets the value of the quotaUnit property.
         *
         * @return possible object is {@link String }
         */
        public String getQuotaUnit() {
          return quotaUnit;
        }

        /**
         * Sets the value of the quotaUnit property.
         *
         * @param value allowed object is {@link String }
         */
        public void setQuotaUnit(String value) {
          this.quotaUnit = value;
        }

        /**
         * Gets the value of the maxQuota property.
         *
         * @return
         */
        public float getMaxQuota() {
          return maxQuota;
        }

        /**
         * Sets the value of the maxQuota property.
         *
         * @param value
         */
        public void setMaxQuota(float value) {
          this.maxQuota = value;
        }

        /**
         * Gets the value of the remainingQuota property.
         *
         * @return
         */
        public float getRemainingQuota() {
          return remainingQuota;
        }

        /**
         * Sets the value of the remainingQuota property.
         *
         * @param value
         */
        public void setRemainingQuota(float value) {
          this.remainingQuota = value;
        }

        /**
         * Gets the value of the consumedQuota property.
         *
         * @return
         */
        public float getConsumedQuota() {
          return consumedQuota;
        }

        /**
         * Sets the value of the consumedQuota property.
         *
         * @param value
         */
        public void setConsumedQuota(float value) {
          this.consumedQuota = value;
        }

        /**
         * Gets the value of the location property.
         *
         * @return possible object is {@link String }
         */
        public String getLocation() {
          return location;
        }

        /**
         * Sets the value of the location property.
         *
         * @param value allowed object is {@link String }
         */
        public void setLocation(String value) {
          this.location = value;
        }

      }

    }

  }

  /**
   * <p>
   * Java class for anonymous complex type.
   *
   * <p>
   * The following schema fragment specifies the expected content contained within this class.
   *
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;sequence>
   *         &lt;element name="ServiceClassID" type="{http://www.w3.org/2001/XMLSchema}short"/>
   *         &lt;element name="LanguageID" type="{http://www.w3.org/2001/XMLSchema}byte"/>
   *         &lt;element name="creditClearanceDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
   *         &lt;element name="serviceRemovalDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
   *         &lt;element name="supervisionExpiryDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
   *         &lt;element name="serviceFeeExpiryDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
   *         &lt;element name="RX_Flag" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *         &lt;element name="BillCycle" type="{http://www.w3.org/2001/XMLSchema}short"/>
   *         &lt;element name="Balances">
   *           &lt;complexType>
   *             &lt;complexContent>
   *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *                 &lt;sequence>
   *                   &lt;element name="RechargeBalance" type="{http://www.w3.org/2001/XMLSchema}short"/>
   *                   &lt;element name="CreditLimit" type="{http://www.w3.org/2001/XMLSchema}byte"/>
   *                   &lt;element name="RemainingLimit" type="{http://www.w3.org/2001/XMLSchema}short"/>
   *                   &lt;element name="ConsumedLimit" type="{http://www.w3.org/2001/XMLSchema}short"/>
   *                 &lt;/sequence>
   *               &lt;/restriction>
   *             &lt;/complexContent>
   *           &lt;/complexType>
   *         &lt;/element>
   *         &lt;element name="Property">
   *           &lt;complexType>
   *             &lt;complexContent>
   *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *                 &lt;sequence>
   *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}byte"/>
   *                 &lt;/sequence>
   *               &lt;/restriction>
   *             &lt;/complexContent>
   *           &lt;/complexType>
   *         &lt;/element>
   *         &lt;element name="pamInformationList" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *       &lt;/sequence>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {
      "serviceClassID",
      "languageID",
      "creditClearanceDate",
      "serviceRemovalDate",
      "supervisionExpiryDate",
      "serviceFeeExpiryDate",
      "rxFlag",
      "billCycle",
      "balances",
      "property",
      "pamInformationList"
  })
  public static class TARIFFINFO {

    @XmlElement(name = "ServiceClassID")
    protected short serviceClassID;

    @XmlElement(name = "LanguageID")
    protected int languageID;

    @XmlJavaTypeAdapter(TarrifDateAdapter.class)
    protected Long creditClearanceDate;

    @XmlJavaTypeAdapter(TarrifDateAdapter.class)
    protected Long serviceRemovalDate;

    @XmlJavaTypeAdapter(TarrifDateAdapter.class)
    protected Long supervisionExpiryDate;

    @XmlJavaTypeAdapter(TarrifDateAdapter.class)
    protected Long serviceFeeExpiryDate;

    @XmlElement(name = "RX_Flag", required = true)
    protected String rxFlag;

    @XmlElement(name = "BillCycle")
    protected short billCycle;

    @XmlElement(name = "Balances", required = true)
    protected GetPrepaidProfileResponse.TARIFFINFO.Balances balances;

    @XmlElement(name = "Property", required = true)
    protected GetPrepaidProfileResponse.TARIFFINFO.Property property;

    @XmlElement(required = true)
    protected String pamInformationList;

    /**
     * Gets the value of the serviceClassID property.
     *
     * @return
     */
    public short getServiceClassID() {
      return serviceClassID;
    }

    /**
     * Sets the value of the serviceClassID property.
     *
     * @param value
     */
    public void setServiceClassID(short value) {
      this.serviceClassID = value;
    }

    /**
     * Gets the value of the languageID property.
     *
     * @return
     */
    public int getLanguageID() {
      return languageID;
    }

    /**
     * Sets the value of the languageID property.
     *
     * @param value
     */
    public void setLanguageID(int value) {
      this.languageID = value;
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

    /**
     * Gets the value of the creditClearanceDate property.
     *
     * @return
     */
    public void setRxFlag(String rxFlag) {
      this.rxFlag = rxFlag;
    }

    /**
     * Gets the value of the rxFlag property.
     *
     * @return possible object is {@link String }
     */
    public String getRXFlag() {
      return rxFlag;
    }

    /**
     * Sets the value of the rxFlag property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRXFlag(String value) {
      this.rxFlag = value;
    }

    /**
     * Gets the value of the billCycle property.
     *
     * @return
     */
    public short getBillCycle() {
      return billCycle;
    }

    /**
     * Sets the value of the billCycle property.
     *
     * @param value
     */
    public void setBillCycle(short value) {
      this.billCycle = value;
    }

    /**
     * Gets the value of the balances property.
     *
     * @return possible object is {@link Response.TARIFFINFO.Balances }
     */
    public GetPrepaidProfileResponse.TARIFFINFO.Balances getBalances() {
      return balances;
    }

    /**
     * Sets the value of the balances property.
     *
     * @param value allowed object is {@link Response.TARIFFINFO.Balances }
     */
    public void setBalances(GetPrepaidProfileResponse.TARIFFINFO.Balances value) {
      this.balances = value;
    }

    /**
     * Gets the value of the property property.
     *
     * @return possible object is {@link Response.TARIFFINFO.Property }
     */
    public GetPrepaidProfileResponse.TARIFFINFO.Property getProperty() {
      return property;
    }

    /**
     * Sets the value of the property property.
     *
     * @param value allowed object is {@link Response.TARIFFINFO.Property }
     */
    public void setProperty(GetPrepaidProfileResponse.TARIFFINFO.Property value) {
      this.property = value;
    }

    /**
     * Gets the value of the pamInformationList property.
     *
     * @return possible object is {@link String }
     */
    public String getPamInformationList() {
      return pamInformationList;
    }

    /**
     * Sets the value of the pamInformationList property.
     *
     * @param value allowed object is {@link String }
     */
    public void setPamInformationList(String value) {
      this.pamInformationList = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RechargeBalance" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *         &lt;element name="CreditLimit" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *         &lt;element name="RemainingLimit" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *         &lt;element name="ConsumedLimit" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "rechargeBalance",
        "creditLimit",
        "remainingLimit",
        "consumedLimit"
    })
    public static class Balances {

      @XmlElement(name = "RechargeBalance")
      protected short rechargeBalance;
      @XmlElement(name = "CreditLimit")
      protected byte creditLimit;
      @XmlElement(name = "RemainingLimit")
      protected short remainingLimit;
      @XmlElement(name = "ConsumedLimit")
      protected short consumedLimit;

      /**
       * Gets the value of the rechargeBalance property.
       *
       * @return
       */
      public short getRechargeBalance() {
        return rechargeBalance;
      }

      /**
       * Sets the value of the rechargeBalance property.
       *
       * @param value
       */
      public void setRechargeBalance(short value) {
        this.rechargeBalance = value;
      }

      /**
       * Gets the value of the creditLimit property.
       *
       * @return
       */
      public byte getCreditLimit() {
        return creditLimit;
      }

      /**
       * Sets the value of the creditLimit property.
       *
       * @param value
       */
      public void setCreditLimit(byte value) {
        this.creditLimit = value;
      }

      /**
       * Gets the value of the remainingLimit property.
       *
       * @return
       */
      public short getRemainingLimit() {
        return remainingLimit;
      }

      /**
       * Sets the value of the remainingLimit property.
       *
       * @param value
       */
      public void setRemainingLimit(short value) {
        this.remainingLimit = value;
      }

      /**
       * Gets the value of the consumedLimit property.
       *
       * @return
       */
      public short getConsumedLimit() {
        return consumedLimit;
      }

      /**
       * Sets the value of the consumedLimit property.
       *
       * @param value
       */
      public void setConsumedLimit(short value) {
        this.consumedLimit = value;
      }

    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "name",
        "value"
    })
    public static class Property {

      @XmlElement(name = "Name", required = true)
      protected String name;
      @XmlElement(name = "Value")
      protected byte value;

      /**
       * Gets the value of the name property.
       *
       * @return possible object is {@link String }
       */
      public String getName() {
        return name;
      }

      /**
       * Sets the value of the name property.
       *
       * @param value allowed object is {@link String }
       */
      public void setName(String value) {
        this.name = value;
      }

      /**
       * Gets the value of the value property.
       *
       * @return
       */
      public byte getValue() {
        return value;
      }

      /**
       * Sets the value of the value property.
       *
       * @param value
       */
      public void setValue(byte value) {
        this.value = value;
      }

    }

  }

}
