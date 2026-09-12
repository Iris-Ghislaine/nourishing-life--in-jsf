package com.nourishinglife.bean;

import com.nourishinglife.dao.UserDAO;
import com.nourishinglife.model.Role;
import com.nourishinglife.model.User;
import com.nourishinglife.util.PasswordUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ManagedBean(name = "authBean")
@SessionScoped
public class AuthBean implements Serializable {

    /** Emails matching this (case-insensitive) become admins at registration time. */
    public static final String ADMIN_EMAIL = "health@gmail.com";

    private final UserDAO userDAO = new UserDAO();

    private User currentUser;

    // --- Sign in form fields ---
    private String signInEmail;
    private String signInPassword;

    // --- Sign up form fields ---
    // Bean Validation (JSR-303) annotations - type 2 of the 3 required validation types.
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String signUpName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String signUpEmail;

    private String signUpPhone;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String signUpPassword;

    private String signUpConfirmPassword;

    public String signIn() {
        User user = userDAO.findByEmail(signInEmail == null ? "" : signInEmail.trim());
        if (user == null || !PasswordUtil.matches(signInPassword, user.getPasswordSalt(), user.getPasswordHash())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials",
                            "Email or password is incorrect."));
            return null;
        }
        this.currentUser = user;
        signInPassword = null;
        return "/home.xhtml?faces-redirect=true";
    }

    public String signUp() {
        String email = signUpEmail == null ? "" : signUpEmail.trim();
        if (userDAO.emailExists(email)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already registered",
                            "Please sign in instead, or use a different email."));
            return null;
        }

        User user = new User();
        user.setEmail(email);
        user.setName(signUpName);
        user.setPhone(signUpPhone);
        user.setRole(ADMIN_EMAIL.equalsIgnoreCase(email) ? Role.ADMIN : Role.USER);

        String salt = PasswordUtil.generateSalt();
        user.setPasswordSalt(salt);
        user.setPasswordHash(PasswordUtil.hash(signUpPassword, salt));

        userDAO.create(user);
        this.currentUser = user;

        signUpPassword = null;
        signUpConfirmPassword = null;
        return "/home.xhtml?faces-redirect=true";
    }

    public String logout() {
        currentUser = null;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "/signin.xhtml?faces-redirect=true";
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    /** Used by index.xhtml's f:viewAction to route to the right landing page. */
    public String indexRedirect() {
        return isAuthenticated() ? "/home.xhtml?faces-redirect=true" : "/signin.xhtml?faces-redirect=true";
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == Role.ADMIN;
    }

    /** Called after Settings updates the DB row, to refresh the session copy. */
    public void refreshCurrentUser() {
        if (currentUser != null) {
            currentUser = userDAO.findById(currentUser.getId());
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getSignInEmail() {
        return signInEmail;
    }

    public void setSignInEmail(String signInEmail) {
        this.signInEmail = signInEmail;
    }

    public String getSignInPassword() {
        return signInPassword;
    }

    public void setSignInPassword(String signInPassword) {
        this.signInPassword = signInPassword;
    }

    public String getSignUpName() {
        return signUpName;
    }

    public void setSignUpName(String signUpName) {
        this.signUpName = signUpName;
    }

    public String getSignUpEmail() {
        return signUpEmail;
    }

    public void setSignUpEmail(String signUpEmail) {
        this.signUpEmail = signUpEmail;
    }

    public String getSignUpPhone() {
        return signUpPhone;
    }

    public void setSignUpPhone(String signUpPhone) {
        this.signUpPhone = signUpPhone;
    }

    public String getSignUpPassword() {
        return signUpPassword;
    }

    public void setSignUpPassword(String signUpPassword) {
        this.signUpPassword = signUpPassword;
    }

    public String getSignUpConfirmPassword() {
        return signUpConfirmPassword;
    }

    public void setSignUpConfirmPassword(String signUpConfirmPassword) {
        this.signUpConfirmPassword = signUpConfirmPassword;
    }
}
