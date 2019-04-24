package com.xaeport.crossborder.verification.datathread;

import java.util.concurrent.ThreadFactory;

public class CrossBorderDataFactory implements ThreadFactory{

    public Thread newThread(Runnable r){
        return new Thread(r);
    }

}
