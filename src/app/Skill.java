package model;

public class Skill {

    private int id;
    private String skillName;
    private String description;

    public Skill() {
    }

    public Skill(int id, String skillName, String description) {
        this.id = id;
        this.skillName = skillName;
        this.description = description;
    }

    public Skill(String skillName, String description) {
        this.skillName = skillName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}