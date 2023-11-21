package com.asset.ccat.lookup_service.models.requests.call_activity_admin;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;
import org.springframework.web.multipart.MultipartFile;

public class UploadActivitiesFileRequest extends BaseRequest {


    private String fileName;
    private String fileExt;
    private MultipartFile file;

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
        return "UploadActivitiesFileRequest{" +
                "fileName='" + fileName + '\'' +
                ", fileExt='" + fileExt + '\'' +
                ", file=" + file +
                '}';
    }
}
