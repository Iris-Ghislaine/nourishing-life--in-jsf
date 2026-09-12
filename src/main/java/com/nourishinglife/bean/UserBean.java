package com.nourishinglife.bean;

import com.nourishinglife.dao.UserDAO;
import com.nourishinglife.model.Role;
import com.nourishinglife.model.User;
import com.nourishinglife.util.PasswordUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

/**
 * Full admin CRUD on the User entity: list (Read), add (Create), edit (Update), delete (Delete).
 */
@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable {

    @ManagedProperty("#{authBean}")
    private AuthBean authBean;

    private final UserDAO userDAO = new UserDAO();

    private List<User> users;

    // New-user form
    private String newName;
    private String newEmail;
    private String newPhone;
    private String newPassword;
    private Role newRole = Role.USER;

    private User editingUser;

    public void init() {
        if (users == null) {
            loadAll();
        }
    }

    public void loadAll() {
        users = userDAO.findAll();
    }

    public void createUser() {
        if (userDAO.emailExists(newEmail)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already exists",
                            "Choose a different email address."));
            return;
        }
        User user = new User();
        user.setName(newName);
        user.setEmail(newEmail);
        user.setPhone(newPhone);
        user.setRole(newRole == null ? Role.USER : newRole);
        String salt = PasswordUtil.generateSalt();
        user.setPasswordSalt(salt);
        user.setPasswordHash(PasswordUtil.hash(newPassword == null || newPassword.isEmpty() ? "Changeme1" : newPassword, salt));
        userDAO.create(user);

        newName = null;
        newEmail = null;
        newPhone = null;
        newPassword = null;
        newRole = Role.USER;
        loadAll();
    }

    public void startEdit(User user) {
        this.editingUser = user;
    }

    public void saveEdit() {
        if (editingUser != null) {
            userDAO.update(editingUser);
            editingUser = null;
            loadAll();
            if (authBean.getCurrentUser() != null
                    && authBean.getCurrentUser().getId().equals(editingUser == null ? null : editingUser.getId())) {
                authBean.refreshCurrentUser();
            }
        }
    }

    public void cancelEdit() {
        editingUser = null;
    }

    public void delete(Long id) {
        if (authBean.getCurrentUser() != null && authBean.getCurrentUser().getId().equals(id)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot delete",
                            "You cannot delete the account you are currently logged in as."));
            return;
        }
        userDAO.delete(id);
        loadAll();
    }

    public Role[] getRoles() {
        return Role.values();
    }

    public AuthBean getAuthBean() {
        return authBean;
    }

    public void setAuthBean(AuthBean authBean) {
        this.authBean = authBean;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Role getNewRole() {
        return newRole;
    }

    public void setNewRole(Role newRole) {
        this.newRole = newRole;
    }

    public User getEditingUser() {
        return editingUser;
    }

    public void setEditingUser(User editingUser) {
        this.editingUser = editingUser;
    }
}
