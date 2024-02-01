package com.asset.ccat.gateway.models.requests.admin.transaction;

import com.asset.ccat.gateway.constants.LinkType;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author wael.mohamed
 */
public class UpdateTransactionLinkRequest extends BaseRequest {

    private Integer typeId;
    private Integer codeId;
    private LinkType linkType;
    private String description;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UpdateTransactionLinkRequest{" +
                "typeId=" + typeId +
                ", codeId=" + codeId +
                ", linkType=" + linkType +
                ", description='" + description + '\'' +
                '}';
    }
}
