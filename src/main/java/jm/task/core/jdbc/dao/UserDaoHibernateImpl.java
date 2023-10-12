package jm.task.core.jdbc.dao;

import com.mysql.cj.xdevapi.SqlResultBuilder;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();

        try {
            session.beginTransaction();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS hibernate_db.User (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "lastName VARCHAR(255)," +
                    "age TINYINT" +
                    ")";
            //Я не смог найти не устаревшего метода для того чтобы
            // создать таблицу с помощью SQL запроса....
            NativeQuery query = session.createNativeQuery(createTableSQL);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();

            String dropTableSQL = "DROP TABLE IF EXISTS hibernate_db.User";
            NativeQuery query = session.createNativeQuery(dropTableSQL);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            User user = new User(name, lastName, age);
            session.persist(user);

            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }

            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            userList = session.createQuery("Select u From User u", User.class)
                            .getResultList();
            session.getTransaction().commit();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        //List<User> userList;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();


            session.getTransaction().commit();
        }
    }
}
