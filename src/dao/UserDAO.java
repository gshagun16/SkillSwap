package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

public class UserDAO {

    // ==========================================
    // ADD NEW USER
    // ==========================================
    public boolean addUser(User user) {

        String sql = """
                INSERT INTO users (name, email, password, bio, location)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getBio());
            statement.setString(5, user.getLocation());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ==========================================
    // LOGIN USER
    // ==========================================
    public User loginUser(String email, String password) {

        String sql = """
                SELECT id, name, email, password, bio, location
                FROM users
                WHERE email = ? AND password = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("bio"),
                        resultSet.getString("location")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}