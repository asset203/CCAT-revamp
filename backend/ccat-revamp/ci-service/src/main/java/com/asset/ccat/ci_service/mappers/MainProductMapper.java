package com.asset.ccat.ci_service.mappers;

import com.asset.ccat.ci_service.models.responses.GetMainProductResponse;
import com.asset.ccat.ci_service.models.shared.MainProductModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class MainProductMapper {

    public GetMainProductResponse map(MainProductModel mainProductModel, HashMap<String, String> cachedProductTypes) {
        GetMainProductResponse mainProductResponse = new GetMainProductResponse();

        mainProductResponse.setType(cachedProductTypes.get(mainProductModel.getType()));

        ArrayList<GetMainProductResponse.Bucket> buckets = new ArrayList<>();
        mainProductResponse.setBucket(buckets);

        for (MainProductModel.Bucket bucket : mainProductModel.getBucket()) {
            GetMainProductResponse.Bucket flatBucket = new GetMainProductResponse.Bucket();
            GetMainProductResponse.Bucket.BucketReport bucketReport = new GetMainProductResponse.Bucket.BucketReport();
            GetMainProductResponse.Bucket.ProductDetails productDetails = new GetMainProductResponse.Bucket.ProductDetails();

            //set usageType and type
            flatBucket.setUsageType(bucket.getUsageType());
            flatBucket.setType(bucket.getType());

            // set remaining amount
            if (bucket.getBucketBalance() != null && !bucket.getBucketBalance().isEmpty()) {
                bucketReport.setUnit(bucket.getBucketBalance().get(0).getRemainingValue().getUnits());
                bucketReport.setRemainingUnits(bucket.getBucketBalance().get(0).getRemainingValue().getAmount());
            }

            // set consumed ammount
            if (bucket.getBucketCounter() != null && !bucket.getBucketCounter().isEmpty()) {
                bucketReport.setConsumedUnits(bucket.getBucketCounter().get(0).getValue().getAmount());
            }

            // set total amount
            bucketReport.setTotalUnits(bucketReport.getConsumedUnits() + bucketReport.getRemainingUnits());

            flatBucket.setBucketReport(bucketReport);

            // set product details
            if(Objects.nonNull(bucket.getProduct())){
                productDetails.setId(bucket.getProduct().get(0).getId());
                productDetails.setName(bucket.getProduct().get(0).getName());
                productDetails.setType(bucket.getProduct().get(0).getBaseType());
            }


            flatBucket.setProduct(productDetails);

            buckets.add(flatBucket);
        }
        return mainProductResponse;
    }

}
