package com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author marwa.elshawarby
 */
public class BatchInstallRequest extends BaseRequest {

    private Integer serviceClassId;
    private String fileName;
    private String fileExt;
    private MultipartFile file;

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

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

    @Override
    public String toString() {
        return "FileName :[" + fileName + "]"
                + "FileExt:[" + fileExt + "] "
                + "ServiceClase:[" + serviceClassId + "]";
    }
}
