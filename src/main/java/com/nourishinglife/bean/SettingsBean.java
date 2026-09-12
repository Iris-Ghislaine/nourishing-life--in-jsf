package com.nourishinglife.bean;

import com.nourishinglife.dao.UserDAO;
import com.nourishinglife.model.User;
import com.nourishinglife.util.PasswordUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name = "settingsBean")
@ViewScoped
public class SettingsBean implements Serializable {

    @ManagedProperty("#{authBean}")
    private AuthBean authBean;

    private final UserDAO userDAO = new UserDAO();

    private String editName;
    private String editPhone;
    private String newPassword;
    private String confirmNewPassword;

    public void init() {
        User user = authBean.getCurrentUser();
        if (user != null && editName == null) {
            editName = user.getName();
            editPhone = user.getPhone();
        }
    }

    public void saveProfile() {
        User user = authBean.getCurrentUser();
        user.setName(editName);
        user.setPhone(editPhone);
        userDAO.update(user);
        authBean.refreshCurrentUser();
        addMessage(FacesMessage.SEVERITY_INFO, "Profile updated", "Your profile has been saved.");
    }

    public void toggleDarkMode() {
        User user = authBean.getCurrentUser();
        user.setDarkMode(!user.isDarkMode());
        userDAO.update(user);
        authBean.refreshCurrentUser();
    }

    public void toggleNotifications() {
        User user = authBean.getCurrentUser();
        user.setNotificationsEnabled(!user.isNotificationsEnabled());
        userDAO.update(user);
        authBean.refreshCurrentUser();
    }

    public void toggleMedicineReminder() {
        User user = authBean.getCurrentUser();
        user.setMedicineReminder(!user.isMedicineReminder());
        userDAO.update(user);
        authBean.refreshCurrentUser();
    }

    public void saveReminderTime() {
        User user = authBean.getCurrentUser();
        userDAO.update(user);
        authBean.refreshCurrentUser();
        addMessage(FacesMessage.SEVERITY_INFO, "Reminder time saved", "Your medicine reminder time has been updated.");
    }

    public void changePassword() {
        User user = authBean.getCurrentUser();
        if (newPassword == null || newPassword.length() < 6) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Password too short", "Use at least 6 characters.");
            return;
        }
        if (!newPassword.equals(confirmNewPassword)) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match", "Please re-type the new password.");
            return;
        }
        String salt = PasswordUtil.generateSalt();
        user.setPasswordSalt(salt);
        user.setPasswordHash(PasswordUtil.hash(newPassword, salt));
        userDAO.update(user);
        authBean.refreshCurrentUser();
        newPassword = null;
        confirmNewPassword = null;
        addMessage(FacesMessage.SEVERITY_INFO, "Password changed", "Your password has been updated.");
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public AuthBean getAuthBean() {
        return authBean;
    }

    public void setAuthBean(AuthBean authBean) {
        this.authBean = authBean;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    public String getEditPhone() {
        return editPhone;
    }

    public void setEditPhone(String editPhone) {
        this.editPhone = editPhone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
