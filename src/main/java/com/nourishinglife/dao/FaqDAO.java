package com.nourishinglife.dao;

import com.nourishinglife.model.Faq;
import com.nourishinglife.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FaqDAO {

    public Long create(Faq faq) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(faq);
            tx.commit();
            return faq.getId();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<Faq> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Faq order by createdAt desc", Faq.class).list();
        }
    }
}
