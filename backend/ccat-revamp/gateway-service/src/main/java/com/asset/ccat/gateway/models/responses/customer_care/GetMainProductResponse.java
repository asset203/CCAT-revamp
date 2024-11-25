package com.asset.ccat.gateway.models.responses.customer_care;

import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class GetMainProductResponse {

    private List<Bucket> bucket;
    private String type;

    public static class Bucket {

        private String usageType;
        private String type;
        private BucketReport bucketReport;
        private ProductDetails product;

        public static class BucketReport {

            private String unit;
            private Integer totalUnits;
            private Integer consumedUnits;
            private Integer remainingUnits;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public Integer getTotalUnits() {
                return totalUnits;
            }

            public void setTotalUnits(Integer totalUnits) {
                this.totalUnits = totalUnits;
            }

            public Integer getConsumedUnits() {
                return consumedUnits;
            }

            public void setConsumedUnits(Integer consumedUnits) {
                this.consumedUnits = consumedUnits;
            }

            public Integer getRemainingUnits() {
                return remainingUnits;
            }

            public void setRemainingUnits(Integer remainingUnits) {
                this.remainingUnits = remainingUnits;
            }
        }

        public static class ProductDetails {

            private String id;
            private String name;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

        }

        public String getUsageType() {
            return usageType;
        }

        public void setUsageType(String usageType) {
            this.usageType = usageType;
        }

        public BucketReport getBucketReport() {
            return bucketReport;
        }

        public void setBucketReport(BucketReport bucketReport) {
            this.bucketReport = bucketReport;
        }

        public ProductDetails getProduct() {
            return product;
        }

        public void setProduct(ProductDetails product) {
            this.product = product;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public List<Bucket> getBucket() {
        return bucket;
    }

    public void setBucket(List<Bucket> bucket) {
        this.bucket = bucket;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
