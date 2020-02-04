package com.wbo.TwitterManager.exeption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author b.walid
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResponse {

    public enum STATUS {

        SUCCESS, FAILED
    }

    private String status;
    private Object data;
    private String errorCode;
    private String errorMsg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
