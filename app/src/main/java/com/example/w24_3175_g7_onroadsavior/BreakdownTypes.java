package com.example.w24_3175_g7_onroadsavior;

public class BreakdownTypes {

    String breakdownType;

    int breakdownIcon;

    public BreakdownTypes(String breakdownType, int breakdownIcon) {
        this.breakdownType = breakdownType;
        this.breakdownIcon = breakdownIcon;
    }

    public String getBreakdownType() {
        return breakdownType;
    }

    public void setBreakdownType(String breakdownType) {
        this.breakdownType = breakdownType;
    }

    public int getBreakdownIcon() {
        return breakdownIcon;
    }

    public void setBreakdownIcon(int breakdownIcon) {
        this.breakdownIcon = breakdownIcon;
    }
}
