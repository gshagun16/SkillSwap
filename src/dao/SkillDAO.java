package dao;

import config.DBConnection;
import model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {

    // GET ALL SKILLS
    public List<Skill> getAllSkills() {

        List<Skill> skills = new ArrayList<>();

        String sql = """
                SELECT id, skill_name, description
                FROM skills
                ORDER BY skill_name
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Skill skill = new Skill(
                        resultSet.getInt("id"),
                        resultSet.getString("skill_name"),
                        resultSet.getString("description")
                );

                skills.add(skill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return skills;
    }


    // GET SKILL BY ID
    public Skill getSkillById(int id) {

        String sql = """
                SELECT id, skill_name, description
                FROM skills
                WHERE id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Skill(
                        resultSet.getInt("id"),
                        resultSet.getString("skill_name"),
                        resultSet.getString("description")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}