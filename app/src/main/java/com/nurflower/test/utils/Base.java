package com.nurflower.test.utils;

import java.math.BigDecimal;

//it is just helper class
public class Base {

    private String base;
    private BigDecimal input;

    public Base(String base, BigDecimal input) {
        this.base = base;
        this.input = input;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public BigDecimal getInput() {
        return input;
    }

    public void setInput(BigDecimal input) {
        this.input = input;
    }
}
