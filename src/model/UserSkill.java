package model;

public class UserSkill {

    private int id;
    private int userId;
    private int skillId;
    private String skillType;

    public UserSkill() {
    }

    public UserSkill(int id, int userId, int skillId, String skillType) {
        this.id = id;
        this.userId = userId;
        this.skillId = skillId;
        this.skillType = skillType;
    }

    public UserSkill(int userId, int skillId, String skillType) {
        this.userId = userId;
        this.skillId = skillId;
        this.skillType = skillType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }
}
