
package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/skillswap_db";

    private static final String USER = "root";

    // Password is taken from an environment variable
    // instead of being stored directly in the source code.
    private static final String PASSWORD =
            System.getenv("SKILLSWAP_DB_PASSWORD");

    public static Connection getConnection() throws Exception {

        if (PASSWORD == null || PASSWORD.isEmpty()) {
            throw new Exception(
                    "SKILLSWAP_DB_PASSWORD environment variable is not set."
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    public static void main(String[] args) {

        try {

            Connection connection = getConnection();

            System.out.println(
                    "Database connected successfully!"
            );

            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Database connection failed!"
            );

            e.printStackTrace();
        }
    }
}
