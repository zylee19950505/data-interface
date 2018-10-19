package com.xaeport.crossborder.verification.tools;

import com.xaeport.crossborder.verification.entity.VerificationResult;

public class VerificationResultUtil {

    public static VerificationResult setEntryListErrorResult(VerificationResult verificationResult, String errorMsg, String errorFiled, String errorGno) {
//        String entryList = "EntryList.";
        String entryList = "";
        verificationResult.setErrorMsg(errorMsg);
        verificationResult.setErrorFlag(true);
        verificationResult.setErrorFiled(entryList + errorFiled);
        verificationResult.setErrorGno(errorGno);
        return verificationResult;
    }

    public static VerificationResult setEntryHeadErrorResult(VerificationResult verificationResult, String errorMsg, String errorFiled) {
//        String entryHead = "EntryHead.";
        String entryHead = "";
        verificationResult.setErrorMsg(errorMsg);
        verificationResult.setErrorFlag(true);
        verificationResult.setErrorFiled(entryHead + errorFiled);
        return verificationResult;
    }

}
