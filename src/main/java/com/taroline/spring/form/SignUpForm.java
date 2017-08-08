package com.taroline.spring.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class SignUpForm {
    @Pattern(regexp = "^\\w{3,32}$", message = "3-32文字の半角英数字で設定してください")
    private String username;

    @Size(min = 8, max = 255, message = "8-255文字で設定してください")
    private String password;

    @Size(min = 8, max = 255, message = "8-255文字で設定してください")
    private String confirmPassword;

    @Email
    @Size(min = 3, max = 255, message = "3-255文字で設定してください")
    private String mailAddress;

    @AssertTrue(message = "確認用のパスワードが一致しません")
    public boolean isSamePassword() {
        if (this.password != null && this.password.trim() != "") {
            return this.password.equals(this.confirmPassword);
        }
        else {
            return false;
        }
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

}
