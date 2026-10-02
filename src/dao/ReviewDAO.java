package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Review;

public class ReviewDAO {

    // =====================================================
    // ADD REVIEW
    // =====================================================

    public boolean addReview(Review review) {

        // -------------------------------------------------
        // 1. Check whether exchange exists and is COMPLETED
        // -------------------------------------------------

        String validationSql = """
                SELECT user1_id, user2_id
                FROM exchanges
                WHERE id = ?
                AND status = 'COMPLETED'
                """;

        // -------------------------------------------------
        // 2. Check duplicate review
        // -------------------------------------------------

        String duplicateSql = """
                SELECT id
                FROM reviews
                WHERE exchange_id = ?
                AND reviewer_id = ?
                """;

        // -------------------------------------------------
        // 3. Insert review
        // -------------------------------------------------

        String insertSql = """
                INSERT INTO reviews
                (exchange_id, reviewer_id, reviewed_user_id, rating, comment)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement validationStatement =
                        connection.prepareStatement(validationSql)
        ) {

            // =================================================
            // CHECK EXCHANGE
            // =================================================

            validationStatement.setInt(
                    1,
                    review.getExchangeId()
            );

            ResultSet resultSet =
                    validationStatement.executeQuery();

            if (!resultSet.next()) {

                System.out.println(
                        "Invalid exchange or exchange is not completed."
                );

                return false;
            }

            int user1Id =
                    resultSet.getInt("user1_id");

            int user2Id =
                    resultSet.getInt("user2_id");

            // =================================================
            // CHECK REVIEWER PARTICIPATION
            // =================================================

            if (review.getReviewerId() != user1Id
                    && review.getReviewerId() != user2Id) {

                System.out.println(
                        "You are not a participant of this exchange."
                );

                return false;
            }

            // =================================================
            // DETERMINE OTHER USER
            // =================================================

            int expectedReviewedUserId;

            if (review.getReviewerId() == user1Id) {

                expectedReviewedUserId = user2Id;

            } else {

                expectedReviewedUserId = user1Id;
            }

            // =================================================
            // CHECK REVIEWED USER
            // =================================================

            if (review.getReviewedUserId()
                    != expectedReviewedUserId) {

                System.out.println(
                        "You can only review the other participant."
                );

                return false;
            }

            // =================================================
            // PREVENT SELF REVIEW
            // =================================================

            if (review.getReviewerId()
                    == review.getReviewedUserId()) {

                System.out.println(
                        "You cannot review yourself."
                );

                return false;
            }

            // =================================================
            // CHECK DUPLICATE REVIEW
            // =================================================

            try (
                    PreparedStatement duplicateStatement =
                            connection.prepareStatement(
                                    duplicateSql
                            )
            ) {

                duplicateStatement.setInt(
                        1,
                        review.getExchangeId()
                );

                duplicateStatement.setInt(
                        2,
                        review.getReviewerId()
                );

                ResultSet duplicateResult =
                        duplicateStatement.executeQuery();

                if (duplicateResult.next()) {

                    System.out.println(
                            "You have already reviewed this exchange."
                    );

                    return false;
                }
            }

            // =================================================
            // VALIDATE RATING
            // =================================================

            if (review.getRating() < 1
                    || review.getRating() > 5) {

                System.out.println(
                        "Rating must be between 1 and 5."
                );

                return false;
            }

            // =================================================
            // INSERT REVIEW
            // =================================================

            try (
                    PreparedStatement insertStatement =
                            connection.prepareStatement(
                                    insertSql
                            )
            ) {

                insertStatement.setInt(
                        1,
                        review.getExchangeId()
                );

                insertStatement.setInt(
                        2,
                        review.getReviewerId()
                );

                insertStatement.setInt(
                        3,
                        review.getReviewedUserId()
                );

                insertStatement.setInt(
                        4,
                        review.getRating()
                );

                insertStatement.setString(
                        5,
                        review.getComment()
                );

                int rows =
                        insertStatement.executeUpdate();

                return rows > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // =====================================================
    // GET REVIEWS RECEIVED BY USER
    // =====================================================

    public List<Review> getReviewsForUser(
            int userId
    ) {

        List<Review> reviews =
                new ArrayList<>();

        String sql = """
                SELECT id,
                       exchange_id,
                       reviewer_id,
                       reviewed_user_id,
                       rating,
                       comment
                FROM reviews
                WHERE reviewed_user_id = ?
                ORDER BY id DESC
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Review review =
                        new Review(
                                resultSet.getInt("id"),
                                resultSet.getInt("exchange_id"),
                                resultSet.getInt("reviewer_id"),
                                resultSet.getInt("reviewed_user_id"),
                                resultSet.getInt("rating"),
                                resultSet.getString("comment")
                        );

                reviews.add(review);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return reviews;
    }

    // =====================================================
    // GET REVIEWS GIVEN BY USER
    // =====================================================

    public List<Review> getReviewsGivenByUser(
            int userId
    ) {

        List<Review> reviews =
                new ArrayList<>();

        String sql = """
                SELECT id,
                       exchange_id,
                       reviewer_id,
                       reviewed_user_id,
                       rating,
                       comment
                FROM reviews
                WHERE reviewer_id = ?
                ORDER BY id DESC
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Review review =
                        new Review(
                                resultSet.getInt("id"),
                                resultSet.getInt("exchange_id"),
                                resultSet.getInt("reviewer_id"),
                                resultSet.getInt("reviewed_user_id"),
                                resultSet.getInt("rating"),
                                resultSet.getString("comment")
                        );

                reviews.add(review);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return reviews;
    }
}