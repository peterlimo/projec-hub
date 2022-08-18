package com.example.projecthub;

public class UploadData {
    private String Info,imgURL,docURL,Limit,Actual;
    public void setInfo(String info) {
        Info = info;
    }
    public String getInfo() {
        return Info;
    }

    public void setImgURL(String linkImg) {
        this.imgURL = imgURL;
    }
    public String getImgURL() {
        return imgURL;
    }

    public void setDocURL(Object o) {
        this.docURL = docURL;
    }
    public String getDocURL() {
        return docURL;
    }

    public void setActual(String format) {
        Actual = format;
    }

    public String getActual() {
        return Actual;
    }
    public void setLimit(String limit) {
        Limit = limit;
    }
    public String getLimit() { return Limit; }
}
