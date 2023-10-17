package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("delete from User where id = :id")
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            userList = session.createQuery("SELECT u from User u ", User.class).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.getStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("DELETE from User ").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.getStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
