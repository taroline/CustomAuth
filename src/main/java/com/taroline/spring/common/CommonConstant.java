package com.taroline.spring.common;

public class CommonConstant {
    /**
     * ユーザーステータス(有効/無効)
     */
    public enum UserStatus {
        /**
         * ユーザーステータス:有効
         */
        ENABLED("1"),

        /**
         * ユーザーステータス:無効
         */
        DISABLED("2");

        private final String statusCode;

        private UserStatus(final String statusCode) {
            this.statusCode = statusCode;
        }

        public String getValue() {
            return this.statusCode;
        }
    }

    /**
     * 管理者フラグ
     */
    public enum AdminFlag {
        /**
         * 管理者
         */
        IS_ADMIN("1"),

        /**
         * 管理者以外
         */
        ISNOT_ADMIN("2");

        private final String isAdmin;

        private AdminFlag(final String isAdmin) {
            this.isAdmin = isAdmin;
        }

        public String getValue() {
            return this.isAdmin;
        }
    }
}
