package com.asset.ccat.user_management.models.requests.user;

import com.asset.ccat.user_management.models.requests.BaseRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author marwa.elshawarby
 */
public class FileUploadRequest extends BaseRequest {

    private String fileName;
    private String fileExt;
    private MultipartFile file;
    private String operationType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
