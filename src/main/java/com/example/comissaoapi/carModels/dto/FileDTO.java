package com.example.comissaoapi.carModels.dto;

public class FileDTO {
    private String fileBase64;
    private String fileType;


    public String getFileBase64() {
        return fileBase64;
    }
    public void setFileBase64(String fileBase64) {
        this.fileBase64 = fileBase64;
    }

    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
