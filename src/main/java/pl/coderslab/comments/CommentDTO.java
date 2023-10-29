package pl.coderslab.comments;

import java.time.LocalDateTime;

public class CommentDTO {
    private String commentContent;
    private Long noOfLikes;
    private String createdOn;
    private String userName;

    public CommentDTO(String commentContent, Long noOfLikes, String createdOn, String userName) {
        this.commentContent = commentContent;
        this.noOfLikes = noOfLikes;
        this.createdOn = createdOn;
        this.userName = userName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Long getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(Long noOfLikes) {
        this.noOfLikes = noOfLikes;
    }


    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
