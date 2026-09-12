package com.nourishinglife.dao;

import com.nourishinglife.model.Feedback;
import com.nourishinglife.model.FeedbackStatus;
import com.nourishinglife.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FeedbackDAO {

    public Long create(Feedback feedback) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(feedback);
            tx.commit();
            return feedback.getId();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Feedback findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Feedback.class, id);
        }
    }

    public List<Feedback> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Feedback order by createdAt desc", Feedback.class).list();
        }
    }

    public List<Feedback> findByUser(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Feedback where user.id = :uid order by createdAt desc", Feedback.class)
                    .setParameter("uid", userId)
                    .list();
        }
    }

    public long countAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select count(f) from Feedback f", Long.class).uniqueResult();
        }
    }

    public long countByStatus(FeedbackStatus status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select count(f) from Feedback f where f.status = :status", Long.class)
                    .setParameter("status", status)
                    .uniqueResult();
        }
    }

    public void update(Feedback feedback) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(feedback);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Feedback feedback = session.get(Feedback.class, id);
            if (feedback != null) {
                session.delete(feedback);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
