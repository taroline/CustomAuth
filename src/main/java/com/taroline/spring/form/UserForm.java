package com.taroline.spring.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserForm {
    private String id;

    @NotBlank
    @Pattern(regexp = "^\\w{3,32}$", message = "3-32文字の半角英数字で設定してください")
    private String username;

    private String password;

    private String confirmPassword;

    @AssertTrue(message = "パスワードは8-255文字で設定してください")
    public boolean isValidPassword() {
        if (StringUtils.isNotEmpty(this.password) || StringUtils.isNotEmpty(this.confirmPassword)) {
            return (this.password.trim().length() >= 8 && this.password.trim().length() <= 255)
                    && (this.confirmPassword.trim().length() >= 8 && this.confirmPassword.trim().length() <= 255);
        }
        // 空は許可
        else {
            return true;
        }
    }

    @AssertTrue(message = "確認用のパスワードが一致しません")
    public boolean isSamePassword() {
        if (StringUtils.isNotEmpty(this.password) || StringUtils.isNotEmpty(this.confirmPassword)) {
            return (this.password.equals(this.confirmPassword));
        }
        // 空は許可
        else {
            return true;
        }
    }

    @NotBlank
    @Email
    @Size(min = 3, max = 255, message = "3-255文字で設定してください")
    private String mailAddress;

    private String enabled;

    private String isAdmin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}