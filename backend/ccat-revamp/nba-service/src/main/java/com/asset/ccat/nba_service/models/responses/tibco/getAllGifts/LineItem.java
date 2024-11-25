package com.asset.ccat.nba_service.models.responses.tibco.getAllGifts;

public class LineItem {
    ValidityPeriod validityPeriod;
    Quantity quantity;
    Specification specification;

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }
}
