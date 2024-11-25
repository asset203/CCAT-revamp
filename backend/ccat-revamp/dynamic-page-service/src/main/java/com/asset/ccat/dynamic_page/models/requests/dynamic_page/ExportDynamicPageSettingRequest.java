package com.asset.ccat.dynamic_page.models.requests.dynamic_page;

import com.asset.ccat.dynamic_page.models.requests.BaseRequest;
import org.springframework.web.multipart.MultipartFile;

public class ExportDynamicPageSettingRequest extends BaseRequest {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
