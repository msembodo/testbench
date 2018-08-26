package com.idemia.tec.testbench.model;

public class VariableMapping {

    private String mappedVariable;
    private String value;

    public VariableMapping() {}

    public VariableMapping(String mappedVariable, String advSaveVariable) {
        this.mappedVariable = mappedVariable;
        this.value = advSaveVariable;
    }

    public String getMappedVariable() {
        return mappedVariable;
    }

    public void setMappedVariable(String mappedVariable) {
        this.mappedVariable = mappedVariable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
