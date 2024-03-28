package com.wan.enumeration;

public enum FavoriteType {
    PRODUCT("product"),
    STORE("store");

    private String target;

    FavoriteType(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return target;
    }
}
