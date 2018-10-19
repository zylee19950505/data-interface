package com.xaeport.crossborder.verification.entity;

/**
 * 验证结果
 */
public class VerificationResult {

    private boolean errorFlag = false;// 错误标志

    private String errorMsg;// 错误信息
    private String errorFiled;// 错误字段
    private String errorGno;// 错误序号

    public boolean hasError() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag){
        this.errorFlag = errorFlag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorFiled() {
        return errorFiled;
    }

    public void setErrorFiled(String errorFiled) {
        this.errorFiled = errorFiled;
    }

    public String getErrorGno() {
        return errorGno;
    }

    public void setErrorGno(String errorGno) {
        this.errorGno = errorGno;
    }
}
