package spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import spring.dao.interfaces.IUserDao;
import spring.models.User;

import java.util.List;

/**
 * Created by nshkarupa on 10.12.2015.
 */
public class UserDaoImpl implements IUserDao {

    @Autowired
    SessionFactory sessionFactory;

    Session session = null;
    Transaction transaction = null;


    @SuppressWarnings("unchecked")
    public List findAll() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<User> userList = session.createCriteria(User.class).list();
        transaction.commit();
        session.close();
        return userList;
    }

    public User findByLogin(String login) {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Object o = session.byNaturalId(User.class).using("login", login).load();
        transaction.commit();
        session.close();
        return (User) o;
    }

    public User save(User user) {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
        return user;
    }

    public void delete(User user) {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        sessionFactory.getCurrentSession().delete(user);
        transaction.commit();
        session.close();
    }

    public void update(User user) {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public void deleteAll() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.createQuery("delete from User u").executeUpdate();
        transaction.commit();
        session.close();
    }
}
