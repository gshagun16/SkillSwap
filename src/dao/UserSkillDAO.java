package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.UserSkill;

public class UserSkillDAO {

    // ==========================================
    // 1. CHECK IF USER SKILL ALREADY EXISTS
    // ==========================================
    public boolean userSkillExists(UserSkill userSkill) {

        String sql = """
                SELECT id
                FROM user_skills
                WHERE user_id = ?
                AND skill_id = ?
                AND skill_type = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userSkill.getUserId());
            statement.setInt(2, userSkill.getSkillId());
            statement.setString(3, userSkill.getSkillType());

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // ==========================================
    // 2. ADD USER SKILL
    // ==========================================
    public boolean addUserSkill(UserSkill userSkill) {

        String sql = """
                INSERT INTO user_skills (user_id, skill_id, skill_type)
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userSkill.getUserId());
            statement.setInt(2, userSkill.getSkillId());
            statement.setString(3, userSkill.getSkillType());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // ==========================================
    // 3. GET USER SKILLS
    // ==========================================
    public List<UserSkill> getUserSkills(int userId) {

        List<UserSkill> userSkills = new ArrayList<>();

        String sql = """
                SELECT id, user_id, skill_id, skill_type
                FROM user_skills
                WHERE user_id = ?
                ORDER BY id
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                UserSkill userSkill = new UserSkill(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("skill_id"),
                        resultSet.getString("skill_type")
                );

                userSkills.add(userSkill);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return userSkills;
    }


    // ==========================================
    // 4. GET USER SKILL DETAILS
    // ==========================================
    public List<String> getUserSkillDetails(int userId) {

        List<String> skills = new ArrayList<>();

        String sql = """
                SELECT s.skill_name, us.skill_type
                FROM user_skills us
                JOIN skills s ON us.skill_id = s.id
                WHERE us.user_id = ?
                ORDER BY us.skill_type, s.skill_name
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String skillName =
                        resultSet.getString("skill_name");

                String skillType =
                        resultSet.getString("skill_type");

                skills.add(skillType + " : " + skillName);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return skills;
    }


    // ==========================================
    // 5. DELETE USER SKILL
    // ==========================================
    public boolean deleteUserSkill(int id) {

        String sql = """
                DELETE FROM user_skills
                WHERE id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public List<String> searchUsersBySkill(String skillName) {

    List<String> users = new ArrayList<>();

    String sql = """
            SELECT u.name, u.email, u.location,
                   s.skill_name, us.skill_type
            FROM user_skills us
            JOIN users u ON us.user_id = u.id
            JOIN skills s ON us.skill_id = s.id
            WHERE s.skill_name LIKE ?
            ORDER BY u.name
            """;

    try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setString(1, "%" + skillName + "%");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String location = resultSet.getString("location");
            String skill = resultSet.getString("skill_name");
            String type = resultSet.getString("skill_type");

            users.add(
                    "Name: " + name
                    + " | Email: " + email
                    + " | Location: " + location
                    + " | Skill: " + skill
                    + " | Type: " + type
            );
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return users;
}
}

