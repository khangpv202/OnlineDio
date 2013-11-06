package com.example.OnlineDio.provider.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.OnlineDio.provider.OnlineDioContract;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kpv
 * Date: 11/2/13
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeShows
{
    private String code;
    private String status;
    private List<HomeShow> data;

    public List<HomeShow> getData()
    {
        return data;
    }

    public void setData(List<HomeShow> data)
    {
        this.data = data;
    }


    public HomeShow fromCursor(Cursor curHomeShow)
    {
        String id = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home._ID));
        String userId = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.UserID));
        String title = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Title));
        String thumbnail = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Thumbnail));
        String description = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Description));
        String sound_path = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.SoundPath));
        String duration = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Duration));
        String played = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Played));
        String created_at = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.CreatedAt));
        String updated_at = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.UpdatedAt));
        String likes = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Likes));
        String viewed = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Viewed));
        String comments = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Comments));
        String username = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Username));
        String display_name = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.DisplayName));
        String avatar = curHomeShow.getString(curHomeShow.getColumnIndex(OnlineDioContract.Home.Avatar));

        return new HomeShow(userId, id, title, thumbnail, description, sound_path, duration, played, likes, viewed, comments, created_at, updated_at, username, display_name, avatar);
    }

    public class HomeShow implements Serializable
    {

        public String user_id;
        public String id;
        public String title;
        public String thumbnail;
        public String description;
        public String sound_path;
        public String duration;
        public String played;
        public String likes;
        public String viewed;
        public String comments;
        public String created_at;
        public String updated_at;
        public String username;
        public String display_name;
        public String avatar;

        public HomeShow(String user_id, String id, String title, String thumbnail, String description, String sound_path, String duration, String played, String likes, String viewed, String comments, String created_at, String updated_at, String username, String display_name, String avatar)
        {
            this.user_id = user_id;
            this.id = id;
            this.title = title;
            this.thumbnail = thumbnail;
            this.description = description;
            this.sound_path = sound_path;
            this.duration = duration;
            this.played = played;
            this.likes = likes;
            this.viewed = viewed;
            this.comments = comments;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.username = username;
            this.display_name = display_name;
            this.avatar = avatar;

        }

        public ContentValues getContentValues()
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(OnlineDioContract.Home._ID, id);
            contentValues.put(OnlineDioContract.Home.UserID, user_id);
            contentValues.put(OnlineDioContract.Home.Title, title);
            contentValues.put(OnlineDioContract.Home.Thumbnail, thumbnail);
            contentValues.put(OnlineDioContract.Home.Description, description);
            contentValues.put(OnlineDioContract.Home.SoundPath, sound_path);
            contentValues.put(OnlineDioContract.Home.Played, played);
            contentValues.put(OnlineDioContract.Home.CreatedAt, created_at);
            contentValues.put(OnlineDioContract.Home.UpdatedAt, updated_at);
            contentValues.put(OnlineDioContract.Home.Likes, likes);
            contentValues.put(OnlineDioContract.Home.Viewed, viewed);
            contentValues.put(OnlineDioContract.Home.Comments, comments);
            contentValues.put(OnlineDioContract.Home.Username, username);
            contentValues.put(OnlineDioContract.Home.DisplayName, display_name);
            contentValues.put(OnlineDioContract.Home.Avatar, avatar);
            return contentValues;
        }

        public String getUser_id()
        {
            return user_id;
        }

        public String getId()
        {
            return id;
        }

        public String getTitle()
        {
            return title;
        }

        public String getThumbnail()
        {
            return thumbnail;
        }

        public String getDescription()
        {
            return description;
        }

        public String getSound_path()
        {
            return sound_path;
        }

        public String getDuration()
        {
            return duration;
        }

        public String getPlayed()
        {
            return played;
        }

        public String getLikes()
        {
            return likes;
        }

        public String getViewed()
        {
            return viewed;
        }

        public String getComments()
        {
            return comments;
        }

        public String getCreated_at()
        {
            return created_at;
        }

        public String getUpdated_at()
        {
            return updated_at;
        }

        public String getUsername()
        {
            return username;
        }

        public String getDisplay_name()
        {
            return display_name;
        }

        public String getAvatar()
        {
            return avatar;
        }

        //-----------------------------------------------

        public void setUser_id(String user_id)
        {
            this.user_id = user_id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public void setThumbnail(String thumbnail)
        {
            this.thumbnail = thumbnail;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public void setSound_path(String sound_path)
        {
            this.sound_path = sound_path;
        }

        public void setDuration(String duration)
        {
            this.duration = duration;
        }

        public void setPlayed(String played)
        {
            this.played = played;
        }

        public void setLikes(String likes)
        {
            this.likes = likes;
        }

        public void setViewed(String viewed)
        {
            this.viewed = viewed;
        }

        public void setComments(String comments)
        {
            this.comments = comments;
        }

        public void setCreated_at(String created_at)
        {
            this.created_at = created_at;
        }

        public void setUpdated_at(String updated_at)
        {
            this.updated_at = updated_at;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public void setDisplay_name(String display_name)
        {
            this.display_name = display_name;
        }

        public void setAvatar(String avatar)
        {
            this.avatar = avatar;
        }
    }
}
