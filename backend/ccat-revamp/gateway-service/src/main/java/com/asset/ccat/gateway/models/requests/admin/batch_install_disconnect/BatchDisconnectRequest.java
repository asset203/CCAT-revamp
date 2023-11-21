package com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author marwa.elshawarby
 */
public class BatchDisconnectRequest extends BaseRequest {

    private String fileName;
    private String fileExt;
    private MultipartFile file;
    private Boolean validateDisconnection;

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

    public Boolean getValidateDisconnection() {
        return validateDisconnection;
    }

    public void setValidateDisconnection(Boolean validateDisconnection) {
        this.validateDisconnection = validateDisconnection;
    }

    @Override
    public String toString() {
        return "FileName :[" + fileName + "] "
                + "FileExt:[" + fileExt + "] ";
    }
}
