package com.asset.usagesimulator.models;

import lombok.Data;

@Data
public class BonusAndDedicatedAdjustmentModel {
    public String TRANS_TYPE;
    public String TRANS_CODE;
    public Integer DA_IDS;
    public Integer DA_AMOUNTS;
    public String TRX_DATE;
}
