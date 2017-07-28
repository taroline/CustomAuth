package com.taroline.spring;

public class CommonConstant {
    public enum UserStatus {
        ENABLED, DISABLED;
        public String getValue(){return String.valueOf((ordinal() + 1));}
    }
}
