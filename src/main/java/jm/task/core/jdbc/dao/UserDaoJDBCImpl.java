package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getMySQLConnection();
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS mytestdb.User (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "lastName VARCHAR(255)," +
                    "age TINYINT" +
                    ")";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try (Connection connection = Util.getMySQLConnection();
             Statement statement = connection.createStatement()) {
            String dropTableSQL = "DROP TABLE IF EXISTS mytestdb.User";
            statement.execute(dropTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUserSQL = """
                INSERT INTO mytestdb.User (name, lastname, age) VALUES (?, ?, ?)
                """;
        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(insertUserSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) {
        String deleteUserSQL = "DELETE FROM mytestdb.User WHERE id = ?";

        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(deleteUserSQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException(e1);
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectAllUsersSQL = "SELECT * FROM mytestdb.User";

        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(selectAllUsersSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException(e1);
        }

        return userList;
    }

    public void cleanUsersTable() {
        String cleanTableSQL = "DELETE FROM mytestdb.User";

        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(cleanTableSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException(e1);
        }

    }
}
