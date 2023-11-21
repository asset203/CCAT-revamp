package com.asset.ccat.gateway.models.shared;

import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class MainProductModel {

    private List<Bucket> bucket;
    private String type;

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

    public static class Bucket {

        private String usageType;
        private List<BucketBalance> bucketBalance;
        private List<BucketCounter> bucketCounter;
        private List<Product> product;
        private String type;

        public String getUsageType() {
            return usageType;
        }

        public void setUsageType(String usageType) {
            this.usageType = usageType;
        }

        public List<BucketBalance> getBucketBalance() {
            return bucketBalance;
        }

        public void setBucketBalance(List<BucketBalance> bucketBalance) {
            this.bucketBalance = bucketBalance;
        }

        public List<BucketCounter> getBucketCounter() {
            return bucketCounter;
        }

        public void setBucketCounter(List<BucketCounter> bucketCounter) {
            this.bucketCounter = bucketCounter;
        }

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct(List<Product> product) {
            this.product = product;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class BucketValue {

            private Integer amount;
            private String units;

            public Integer getAmount() {
                return amount;
            }

            public void setAmount(Integer amount) {
                this.amount = amount;
            }

            public String getUnits() {
                return units;
            }

            public void setUnits(String units) {
                this.units = units;
            }

        }

        public static class BucketBalance {

            private BucketValue remainingValue;
            private String type;

            public BucketValue getRemainingValue() {
                return remainingValue;
            }

            public void setRemainingValue(BucketValue remainingValue) {
                this.remainingValue = remainingValue;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

        }

        public static class BucketCounter {

            private String level;
            private BucketValue value;
            private String type;

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public BucketValue getValue() {
                return value;
            }

            public void setValue(BucketValue value) {
                this.value = value;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

        }

        public static class Product {

            private Integer id;
            private String name;
            private String baseType;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBaseType() {
                return baseType;
            }

            public void setBaseType(String baseType) {
                this.baseType = baseType;
            }

        }

    }

}
