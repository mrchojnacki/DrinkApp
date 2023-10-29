package pl.coderslab.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserRegisterDTO {
    @NotNull
    @Size(min = 2, max = 30, message = "Username should be at least 2 letters long")
    private String userName;
    @NotNull
    @Email(message = "Enter a real e-mail address")
    private String email;
    @NotNull
    @Size(min=6, message = "Password should be at least 6 characters long")
    private String password;
    private String passwordConfirmation;
    private boolean rememberPassword;

    public UserRegisterDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberPassword() {
        return rememberPassword;
    }

    public void setRememberPassword(boolean rememberPassword) {
        this.rememberPassword = rememberPassword;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRegisterDTO)) return false;
        UserRegisterDTO that = (UserRegisterDTO) o;
        return rememberPassword == that.rememberPassword && userName.equals(that.userName) && email.equals(that.email) && password.equals(that.password) && passwordConfirmation.equals(that.passwordConfirmation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email, password, passwordConfirmation, rememberPassword);
    }
}
