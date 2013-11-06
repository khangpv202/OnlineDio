package com.example.OnlineDio.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.OnlineDio.provider.OnlineDioContract;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 11/4/13
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserProfile
{
    private String code;
    private String status;

    private Profile data;

    public Profile getData()
    {
        return data;
    }

    public static class Profile
    {
        private String _id;
        private String id;
        private String facebook_id;
        private String username;
        private String password;
        private String avatar;
        private String cover_image;
        private String display_name;
        private String full_name;
        private String phone;
        private String birthday;
        private String gender;
        private String country_id;
        private String storage_plan_id;
        private String description;
        private String created_at;
        private String updated_at;
        private String sounds;
        private String favorites;
        private String likes;
        private String followings;
        private String audiences;

        public ContentValues getContentValues()
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(OnlineDioContract.Profile.Id, id);
            contentValues.put(OnlineDioContract.Profile.FacebookId, facebook_id);
            contentValues.put(OnlineDioContract.Profile.UserName, username);
            contentValues.put(OnlineDioContract.Profile.Password, password);
            contentValues.put(OnlineDioContract.Profile.Avatar, avatar);
            contentValues.put(OnlineDioContract.Profile.CoverImage, cover_image);
            contentValues.put(OnlineDioContract.Profile.DisplayName, display_name);
            contentValues.put(OnlineDioContract.Profile.FullName, full_name);
            contentValues.put(OnlineDioContract.Profile.Phone, phone);
            contentValues.put(OnlineDioContract.Profile.Birthday, birthday);
            contentValues.put(OnlineDioContract.Profile.Gender, gender);
            contentValues.put(OnlineDioContract.Profile.CountryId, country_id);
            contentValues.put(OnlineDioContract.Profile.StoragePlanId, storage_plan_id);
            contentValues.put(OnlineDioContract.Profile.Description, description);
            contentValues.put(OnlineDioContract.Profile.CreatedAt, created_at);
            contentValues.put(OnlineDioContract.Profile.UpdateAt, updated_at);
            contentValues.put(OnlineDioContract.Profile.Sounds, sounds);
            contentValues.put(OnlineDioContract.Profile.Favorites, favorites);
            contentValues.put(OnlineDioContract.Profile.Likes, likes);
            contentValues.put(OnlineDioContract.Profile.Followings, followings);
            contentValues.put(OnlineDioContract.Profile.Audiences, audiences);
            return contentValues;
        }

        public static Profile fromCursor(Cursor cursor)
        {
            String _id = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile._ID));
            String id = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Id));
            String facebook_id = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.FacebookId));
            String userName = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.UserName));
            String password = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Password));
            String avatar = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Avatar));
            String cover_image = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.CoverImage));
            String display_name = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.DisplayName));
            String full_name = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.FullName));
            String phone = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Phone));
            String birthday = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Birthday));
            String gender = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Gender));
            String country_id = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.CountryId));
            String storage_plan_id = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.StoragePlanId));
            String description = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Description));
            String created_at = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.CreatedAt));
            String updated_at = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.UpdateAt));
            String sounds = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Sounds));
            String favorites = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Favorites));
            String likes = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Likes));
            String followings = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Followings));
            String audiences = cursor.getString(cursor.getColumnIndex(OnlineDioContract.Profile.Audiences));
            return new Profile(_id, id, facebook_id, userName, password, avatar, cover_image, display_name,
                    full_name, phone, birthday, gender, country_id, storage_plan_id, description, created_at, updated_at
                    , sounds, favorites, likes, followings, audiences);
        }

        public void setFieldEditedOnView(String full_name, String phone, String birthday, String gender, String country_id, String description)
        {
            this.full_name = full_name;
            this.phone = phone;
            this.birthday = birthday;
            this.gender = gender;
            this.country_id = country_id;
            this.description = description;
        }

        public Profile(String _id, String id, String facebook_id, String username, String password, String avatar,
                       String cover_image, String display_name, String full_name, String phone, String birthday,
                       String gender, String country_id, String storage_plan_id, String description, String created_at,
                       String updated_at, String sounds, String favorites, String likes, String followings, String audiences)
        {
            this._id = _id;
            this.id = id;
            this.facebook_id = facebook_id;
            this.username = username;
            this.password = password;
            this.avatar = avatar;
            this.cover_image = cover_image;
            this.display_name = display_name;
            this.full_name = full_name;
            this.phone = phone;
            this.birthday = birthday;
            this.gender = gender;
            this.country_id = country_id;
            this.storage_plan_id = storage_plan_id;
            this.description = description;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.sounds = sounds;
            this.favorites = favorites;
            this.likes = likes;
            this.followings = followings;
            this.audiences = audiences;
        }

        @Override
        public String toString()
        {
            return username + "  " + display_name;
        }

        public String get_id()
        {
            return _id;
        }

        public String getId()
        {
            return id;
        }

        public String getFacebook_id()
        {
            return facebook_id;
        }

        public String getUsername()
        {
            return username;
        }

        public String getPassword()
        {
            return password;
        }

        public String getAvatar()
        {
            return avatar;
        }

        public String getCover_image()
        {
            return cover_image;
        }

        public String getDisplay_name()
        {
            return display_name;
        }

        public String getFull_name()
        {
            return full_name;
        }

        public String getPhone()
        {
            return phone;
        }

        public String getBirthday()
        {
            return birthday;
        }

        public String getGender()
        {
            return gender;
        }

        public String getCountry_id()
        {
            return country_id;
        }

        public String getStorage_plan_id()
        {
            return storage_plan_id;
        }

        public String getDescription()
        {
            return description;
        }

        public String getCreated_at()
        {
            return created_at;
        }

        public String getUpdated_at()
        {
            return updated_at;
        }

        public String getSounds()
        {
            return sounds;
        }

        public String getFavorites()
        {
            return favorites;
        }

        public String getLikes()
        {
            return likes;
        }

        public String getFollowings()
        {
            return followings;
        }

        public String getAudiences()
        {
            return audiences;
        }

        /*----------------------setter-----------------------*/

        public void set_id(String _id)
        {
            this._id = _id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public void setFacebook_id(String facebook_id)
        {
            this.facebook_id = facebook_id;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public void setAvatar(String avatar)
        {
            this.avatar = avatar;
        }

        public void setCover_image(String cover_image)
        {
            this.cover_image = cover_image;
        }

        public void setDisplay_name(String display_name)
        {
            this.display_name = display_name;
        }

        public void setFull_name(String full_name)
        {
            this.full_name = full_name;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public void setBirthday(String birthday)
        {
            this.birthday = birthday;
        }

        public void setGender(String gender)
        {
            this.gender = gender;
        }

        public void setCountry_id(String country_id)
        {
            this.country_id = country_id;
        }

        public void setStorage_plan_id(String storage_plan_id)
        {
            this.storage_plan_id = storage_plan_id;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public void setCreated_at(String created_at)
        {
            this.created_at = created_at;
        }

        public void setUpdated_at(String updated_at)
        {
            this.updated_at = updated_at;
        }

        public void setSounds(String sounds)
        {
            this.sounds = sounds;
        }

        public void setFavorites(String favorites)
        {
            this.favorites = favorites;
        }

        public void setLikes(String likes)
        {
            this.likes = likes;
        }

        public void setFollowings(String followings)
        {
            this.followings = followings;
        }

        public void setAudiences(String audiences)
        {
            this.audiences = audiences;
        }
    }

}
