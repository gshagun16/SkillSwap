package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.SkillRequest;

public class SkillRequestDAO {


    // ==========================================
    // SEND REQUEST
    // ==========================================
    public boolean sendRequest(SkillRequest request) {

        String sql = """
                INSERT INTO skill_requests
                (requester_id, receiver_id, skill_id, message)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, request.getRequesterId());
            statement.setInt(2, request.getReceiverId());
            statement.setInt(3, request.getSkillId());
            statement.setString(4, request.getMessage());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // ==========================================
    // GET RECEIVED REQUESTS
    // ==========================================
    public List<SkillRequest> getReceivedRequests(int receiverId) {

        List<SkillRequest> requests = new ArrayList<>();

        String sql = """
                SELECT id, requester_id, receiver_id,
                       skill_id, message, status
                FROM skill_requests
                WHERE receiver_id = ?
                ORDER BY id DESC
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, receiverId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                SkillRequest request = new SkillRequest(
                        resultSet.getInt("id"),
                        resultSet.getInt("requester_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getInt("skill_id"),
                        resultSet.getString("message"),
                        resultSet.getString("status")
                );

                requests.add(request);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return requests;
    }


    // ==========================================
    // GET SENT REQUESTS
    // ==========================================
    public List<SkillRequest> getSentRequests(int requesterId) {

        List<SkillRequest> requests = new ArrayList<>();

        String sql = """
                SELECT id, requester_id, receiver_id,
                       skill_id, message, status
                FROM skill_requests
                WHERE requester_id = ?
                ORDER BY id DESC
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, requesterId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                SkillRequest request = new SkillRequest(
                        resultSet.getInt("id"),
                        resultSet.getInt("requester_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getInt("skill_id"),
                        resultSet.getString("message"),
                        resultSet.getString("status")
                );

                requests.add(request);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return requests;
    }


    // ==========================================
    // UPDATE REQUEST STATUS
    // ==========================================
    public boolean updateRequestStatus(int requestId, String status) {

        String sql = """
                UPDATE skill_requests
                SET status = ?
                WHERE id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, status);
            statement.setInt(2, requestId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
    
    public boolean acceptOrRejectRequest(int requestId, String status) {

    String sql = """
            UPDATE skill_requests
            SET status = ?
            WHERE id = ?
            AND status = 'PENDING'
            """;

    try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setString(1, status);
        statement.setInt(2, requestId);

        int rows = statement.executeUpdate();

        return rows > 0;

    } catch (Exception e) {

        e.printStackTrace();
        return false;
    }
}
}