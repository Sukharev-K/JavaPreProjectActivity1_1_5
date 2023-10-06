package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();

        User user1 = new User("Konstantin", "Sukharev", (byte) 26);
        userDaoJDBC.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        System.out.println("User с именем – " + user1.getName() + " добавлен в базу данных");

        User user2 = new User("Yuliya", "Sukhareva", (byte) 26);
        userDaoJDBC.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        System.out.println("User с именем – " + user2.getName() + " добавлен в базу данных");

        User user3 = new User("Tom", "Barrison", (byte) 26);
        userDaoJDBC.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        System.out.println("User с именем – " + user3.getName() + " добавлен в базу данных");

        User user4 = new User("Marta", "Babaeva", (byte) 26);
        userDaoJDBC.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println("User с именем – " + user4.getName() + " добавлен в базу данных");

        List<User> userList = userDaoJDBC.getAllUsers();
        for (User users: userList) {
            System.out.println(users.toString());
        }

        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();


    }
}
