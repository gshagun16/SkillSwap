package model;

public class Review {

    private int id;
    private int exchangeId;
    private int reviewerId;
    private int reviewedUserId;
    private int rating;
    private String comment;

    public Review() {
    }

    public Review(
            int id,
            int exchangeId,
            int reviewerId,
            int reviewedUserId,
            int rating,
            String comment
    ) {
        this.id = id;
        this.exchangeId = exchangeId;
        this.reviewerId = reviewerId;
        this.reviewedUserId = reviewedUserId;
        this.rating = rating;
        this.comment = comment;
    }

    public Review(
            int exchangeId,
            int reviewerId,
            int reviewedUserId,
            int rating,
            String comment
    ) {
        this.exchangeId = exchangeId;
        this.reviewerId = reviewerId;
        this.reviewedUserId = reviewedUserId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    public int getReviewedUserId() {
        return reviewedUserId;
    }

    public void setReviewedUserId(int reviewedUserId) {
        this.reviewedUserId = reviewedUserId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}