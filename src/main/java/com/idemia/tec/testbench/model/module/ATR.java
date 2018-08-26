package com.idemia.tec.testbench.model.module;

import com.idemia.tec.testbench.model.RunModule;

public class ATR extends RunModule {

    private String atr;

    public ATR() {}

    public ATR(int moduleNumber, String moduleName, boolean executed) {
        super(moduleNumber, moduleName, executed);
    }

    public ATR(int moduleNumber, String moduleName, boolean executed, String atr) {
        super(moduleNumber, moduleName, executed);
        this.atr = atr;
    }

    public String getAtr() {
        return atr;
    }

    public void setAtr(String atr) {
        this.atr = atr;
    }

}
