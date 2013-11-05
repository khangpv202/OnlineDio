package com.example.OnlineDio.model;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 18/10/2013
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
public class CommentDTO
{
    private int commentIdImage;
    private String commentName;
    private String commentComment;
    private String commentTime;

    public CommentDTO(String commentName, String commentComment, String commentTime)
    {
        this.commentName = commentName;
        this.commentComment = commentComment;
        this.commentTime = commentTime;
    }

    public int getCommentIdImage()
    {
        return commentIdImage;
    }

    public void setCommentIdImage(int commentIdImage)
    {
        this.commentIdImage = commentIdImage;
    }

    public String getCommentName()
    {
        return commentName;
    }

    public void setCommentName(String commentName)
    {
        this.commentName = commentName;
    }

    public String getCommentComment()
    {
        return commentComment;
    }

    public void setCommentComment(String commentComment)
    {
        this.commentComment = commentComment;
    }

    public String getCommentTime()
    {
        return commentTime;
    }

    public void setCommentTime(String commentTime)
    {
        this.commentTime = commentTime;
    }
}
