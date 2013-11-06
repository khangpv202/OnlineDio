package com.example.OnlineDio.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 10/31/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class OnlineDioContract
{
    public static final String AUTHORITY = "com.example.OnlineDio.provider";
    public static final String ACCOUNT_TYPE = "com.example.OnlineDio";
    public static final String ONLINEDIO_DATABASE_NAME = "onlinedio.db";

    public static final class Home implements BaseColumns
    {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.home.contentfeeds";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.home.contentfeed";

        public static final Uri CONTENT_URI = Uri.parse("content://" +
                AUTHORITY + "/" + Home.TABLE_NAME);

        public static final String TABLE_NAME = "home";

        public static final String UserID = "user_id";
        public static final String Title = "title";
        public static final String Thumbnail = "thumbnail";
        public static final String Description = "description";
        public static final String SoundPath = "sound_path";
        public static final String Duration = "duration";
        public static final String Played = "played";
        public static final String Likes = "likes";
        public static final String Viewed = "viewed";
        public static final String Comments = "comments";
        public static final String CreatedAt = "created_at";
        public static final String UpdatedAt = "updated_at";
        public static final String Username = "username";
        public static final String DisplayName = "display_name";
        public static final String Avatar = "avatar";

        public static String getContentType()
        {
            return CONTENT_TYPE;
        }

        public static String getContentItemType()
        {
            return CONTENT_ITEM_TYPE;
        }

        public static Uri getContentUri()
        {
            return CONTENT_URI;
        }

        public static String getTableName()
        {
            return TABLE_NAME;
        }

        public static String getUserID()
        {
            return UserID;
        }

        public static String getTitle()
        {
            return Title;
        }

        public static String getThumbnail()
        {
            return Thumbnail;
        }

        public static String getDescription()
        {
            return Description;
        }

        public static String getSoundPath()
        {
            return SoundPath;
        }

        public static String getDuration()
        {
            return Duration;
        }

        public static String getPlayed()
        {
            return Played;
        }

        public static String getLikes()
        {
            return Likes;
        }

        public static String getViewed()
        {
            return Viewed;
        }

        public static String getComments()
        {
            return Comments;
        }

        public static String getCreatedAt()
        {
            return CreatedAt;
        }

        public static String getUpdatedAt()
        {
            return UpdatedAt;
        }

        public static String getUsername()
        {
            return Username;
        }

        public static String getDisplayName()
        {
            return DisplayName;
        }

        public static String getAvatar()
        {
            return Avatar;
        }


    }

    public static final class Profile implements BaseColumns
    {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.home.profileinformations";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.home.profileinformation";

        public static final Uri CONTENT_URI = Uri.parse("content://" +
                AUTHORITY + "/" + Profile.TABLE_NAME);

        public static final String TABLE_NAME = "profile";

        public static final String Id = "id";
        public static final String FacebookId = "facebook_id";
        public static final String UserName = "username";
        public static final String Password = "password";
        public static final String Avatar = "avatar";
        public static final String CoverImage = "cover_image";
        public static final String DisplayName = "display_name";
        public static final String FullName = "full_name";
        public static final String Phone = "phone";
        public static final String Birthday = "birthday";
        public static final String Gender = "gender";
        public static final String CountryId = "country_id";
        public static final String StoragePlanId = "storage_plan_id";
        public static final String Description = "description";
        public static final String CreatedAt = "created_at";
        public static final String UpdateAt = "updated_at";
        public static final String Sounds = "sounds";
        public static final String Favorites = "favorites";
        public static final String Likes = "likes";
        public static final String Followings = "followings";
        public static final String Audiences = "audiences";
    }
}
