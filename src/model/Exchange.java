package model;

import java.sql.Timestamp;

public class Exchange {

    private int id;
    private int requestId;
    private int user1Id;
    private int user2Id;
    private int skill1Id;
    private int skill2Id;
    private String status;
    private Timestamp startedAt;
    private Timestamp completedAt;

    public Exchange() {
    }

    public Exchange(
            int id,
            int requestId,
            int user1Id,
            int user2Id,
            int skill1Id,
            int skill2Id,
            String status,
            Timestamp startedAt,
            Timestamp completedAt
    ) {
        this.id = id;
        this.requestId = requestId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.skill1Id = skill1Id;
        this.skill2Id = skill2Id;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
    }

    public Exchange(
            int requestId,
            int user1Id,
            int user2Id,
            int skill1Id,
            int skill2Id
    ) {
        this.requestId = requestId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.skill1Id = skill1Id;
        this.skill2Id = skill2Id;
        this.status = "ACTIVE";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    public int getSkill1Id() {
        return skill1Id;
    }

    public void setSkill1Id(int skill1Id) {
        this.skill1Id = skill1Id;
    }

    public int getSkill2Id() {
        return skill2Id;
    }

    public void setSkill2Id(int skill2Id) {
        this.skill2Id = skill2Id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt) {
        this.startedAt = startedAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }
}