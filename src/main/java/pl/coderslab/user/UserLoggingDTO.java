package pl.coderslab.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoggingDTO {
    @NotNull
    @Size(min = 2, max = 30, message="Has to be at least 2 letters long")
    private String loggingMethod;
    private String password;
    private boolean rememberPassword;

    public UserLoggingDTO(String loggingMethod, String password, boolean rememberPassword) {
        this.loggingMethod = loggingMethod;
        this.password = password;
        this.rememberPassword = rememberPassword;
    }

    public UserLoggingDTO() {
    }

    public String getLoggingMethod() {
        return loggingMethod;
    }

    public void setLoggingMethod(String loggingMethod) {
        this.loggingMethod = loggingMethod;
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
}
