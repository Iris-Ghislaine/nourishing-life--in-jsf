package com.nourishinglife.listener;

import com.nourishinglife.bean.AuthBean;
import com.nourishinglife.dao.UserDAO;
import com.nourishinglife.model.Role;
import com.nourishinglife.model.User;
import com.nourishinglife.util.HibernateUtil;
import com.nourishinglife.util.PasswordUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Seeds a default admin account on first startup so the app is immediately usable.
 * Default login: health@gmail.com / Admin@123 (documented in README.md).
 */
public class AppStartupListener implements ServletContextListener {

    private static final String DEFAULT_ADMIN_PASSWORD = "Admin@123";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HibernateUtil.getSessionFactory(); // force early init so startup errors surface immediately

        UserDAO userDAO = new UserDAO();
        if (!userDAO.emailExists(AuthBean.ADMIN_EMAIL)) {
            User admin = new User();
            admin.setEmail(AuthBean.ADMIN_EMAIL);
            admin.setName("Health Admin");
            admin.setRole(Role.ADMIN);
            String salt = PasswordUtil.generateSalt();
            admin.setPasswordSalt(salt);
            admin.setPasswordHash(PasswordUtil.hash(DEFAULT_ADMIN_PASSWORD, salt));
            userDAO.create(admin);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.shutdown();
    }
}
