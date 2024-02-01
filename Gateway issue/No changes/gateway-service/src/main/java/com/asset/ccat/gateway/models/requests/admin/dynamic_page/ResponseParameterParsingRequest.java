package com.asset.ccat.gateway.models.requests.admin.dynamic_page;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class ResponseParameterParsingRequest extends BaseRequest {

    private Integer type;
    private String fileContent;
    private String rootName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    @Override
    public String toString() {
        return "ResponseParameterParsingRequest{" +
                "type=" + type +
                ", fileContent='" + fileContent + '\'' +
                ", rootName='" + rootName + '\'' +
                '}';
    }
}
