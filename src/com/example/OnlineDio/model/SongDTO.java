package com.example.OnlineDio.model;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 10/11/13
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SongDTO
{
    private String titleOfSong;
    private String nameOfDirector;
    private String numberOfLike;
    private String numberOfComment;
    private String numberOfPostedDay;

    public SongDTO(String titleOfSong, String nameOfDirector, String numberOfLike, String numberOfComment, String numberOfPostedDay)
    {
        this.titleOfSong = titleOfSong;
        this.nameOfDirector = nameOfDirector;
        this.numberOfLike = numberOfLike;
        this.numberOfComment = numberOfComment;
        this.numberOfPostedDay = numberOfPostedDay;
    }

    public String getTitleOfSong()
    {
        return titleOfSong;
    }

    public void setTitleOfSong(String titleOfSong)
    {
        this.titleOfSong = titleOfSong;
    }

    public String getNameOfDirector()
    {
        return nameOfDirector;
    }

    public void setNameOfDirector(String nameOfDirector)
    {
        this.nameOfDirector = nameOfDirector;
    }

    public String getNumberOfLike()
    {
        return numberOfLike;
    }

    public void setNumberOfLike(String numberOfLike)
    {
        this.numberOfLike = numberOfLike;
    }

    public String getNumberOfComment()
    {
        return numberOfComment;
    }

    public void setNumberOfComment(String numberOfComment)
    {
        this.numberOfComment = numberOfComment;
    }

    public String getNumberOfPostedDay()
    {
        return numberOfPostedDay;
    }

    public void setNumberOfPostedDay(String numberOfPostedDay)
    {
        this.numberOfPostedDay = numberOfPostedDay;
    }
}
