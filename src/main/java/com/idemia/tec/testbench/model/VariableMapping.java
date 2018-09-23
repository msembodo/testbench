package com.idemia.tec.testbench.model;

public class VariableMapping {

    private String mappedVariable;
    private String mccVariable;
    private String value;

    public VariableMapping() {}

	public VariableMapping(String mappedVariable, String mccVariable, String value) {
		super();
		this.mappedVariable = mappedVariable;
		this.mccVariable = mccVariable;
		this.value = value;
	}

	public String getMappedVariable() {
		return mappedVariable;
	}

	public void setMappedVariable(String mappedVariable) {
		this.mappedVariable = mappedVariable;
	}

	public String getMccVariable() {
		return mccVariable;
	}

	public void setMccVariable(String mccVariable) {
		this.mccVariable = mccVariable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
