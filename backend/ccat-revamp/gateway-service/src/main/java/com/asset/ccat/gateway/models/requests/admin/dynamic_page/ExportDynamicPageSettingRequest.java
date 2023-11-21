package com.asset.ccat.gateway.models.requests.admin.dynamic_page;

import org.springframework.web.multipart.MultipartFile;
import com.asset.ccat.gateway.models.requests.BaseRequest;

public class ExportDynamicPageSettingRequest extends BaseRequest {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "ExportDynamicPageSettingRequest{" +
                "file=" + file +
                '}';
    }
}
