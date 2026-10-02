package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Exchange;

public class ExchangeDAO {

    // Create a new exchange
    public boolean createExchange(Exchange exchange) {

        String sql = """
                INSERT INTO exchanges
                (request_id, user1_id, user2_id, skill1_id, skill2_id, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, exchange.getRequestId());
            statement.setInt(2, exchange.getUser1Id());
            statement.setInt(3, exchange.getUser2Id());
            statement.setInt(4, exchange.getSkill1Id());

            // skill2_id is optional.
            // 0 means no second skill was specified,
            // so store SQL NULL instead of 0.
            if (exchange.getSkill2Id() > 0) {
                statement.setInt(5, exchange.getSkill2Id());
            } else {
                statement.setNull(
                        5,
                        java.sql.Types.INTEGER
                );
            }

            statement.setString(6, exchange.getStatus());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // Get exchanges of a user
    public List<Exchange> getUserExchanges(int userId) {

        List<Exchange> exchanges = new ArrayList<>();

        String sql = """
                SELECT id, request_id, user1_id, user2_id,
                       skill1_id, skill2_id, status,
                       started_at, completed_at
                FROM exchanges
                WHERE user1_id = ?
                   OR user2_id = ?
                ORDER BY id DESC
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);
            statement.setInt(2, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int skill2Id = resultSet.getInt("skill2_id");

                // SQL NULL becomes 0 in getInt(),
                // which is fine for our Java model.
                if (resultSet.wasNull()) {
                    skill2Id = 0;
                }

                Exchange exchange = new Exchange(
                        resultSet.getInt("id"),
                        resultSet.getInt("request_id"),
                        resultSet.getInt("user1_id"),
                        resultSet.getInt("user2_id"),
                        resultSet.getInt("skill1_id"),
                        skill2Id,
                        resultSet.getString("status"),
                        resultSet.getTimestamp("started_at"),
                        resultSet.getTimestamp("completed_at")
                );

                exchanges.add(exchange);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return exchanges;
    }

    // Update exchange status
    public boolean updateExchangeStatus(
            int exchangeId,
            String status
    ) {

        String sql = """
                UPDATE exchanges
                SET status = ?
                WHERE id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, status);
            statement.setInt(2, exchangeId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // Complete an exchange
    public boolean completeExchange(int exchangeId) {

        String sql = """
                UPDATE exchanges
                SET status = 'COMPLETED',
                    completed_at = CURRENT_TIMESTAMP
                WHERE id = ?
                AND status = 'ACTIVE'
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, exchangeId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}