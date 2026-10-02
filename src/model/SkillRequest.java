package model;

public class SkillRequest {

    private int id;
    private int requesterId;
    private int receiverId;
    private int skillId;
    private String message;
    private String status;

    public SkillRequest() {
    }

    public SkillRequest(
            int id,
            int requesterId,
            int receiverId,
            int skillId,
            String message,
            String status
    ) {
        this.id = id;
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.skillId = skillId;
        this.message = message;
        this.status = status;
    }

    public SkillRequest(
            int requesterId,
            int receiverId,
            int skillId,
            String message
    ) {
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.skillId = skillId;
        this.message = message;
        this.status = "PENDING";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }


    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }


    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}