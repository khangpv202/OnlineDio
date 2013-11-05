package com.example.OnlineDio.model;

import android.content.ContentValues;
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

        public ContentValues getContentValues(){
            ContentValues contentValues = new ContentValues();
            contentValues.put(OnlineDioContract.Profile.Id,id);
            contentValues.put(OnlineDioContract.Profile.FacebookId,facebook_id);
            contentValues.put(OnlineDioContract.Profile.UserName,username);
            contentValues.put(OnlineDioContract.Profile.Password,password);
            contentValues.put(OnlineDioContract.Profile.Avatar,avatar);
            contentValues.put(OnlineDioContract.Profile.CoverImage,cover_image);
            contentValues.put(OnlineDioContract.Profile.DisplayName,display_name);
            contentValues.put(OnlineDioContract.Profile.FullName,full_name);
            contentValues.put(OnlineDioContract.Profile.Phone,phone);
            contentValues.put(OnlineDioContract.Profile.Birthday,birthday);
            contentValues.put(OnlineDioContract.Profile.Gender,gender);
            contentValues.put(OnlineDioContract.Profile.CountryId,country_id);
            contentValues.put(OnlineDioContract.Profile.StoragePlanId,storage_plan_id);
            contentValues.put(OnlineDioContract.Profile.Description,description);
            contentValues.put(OnlineDioContract.Profile.CreatedAt,created_at);
            contentValues.put(OnlineDioContract.Profile.UpdateAt,updated_at);
            contentValues.put(OnlineDioContract.Profile.Sounds,sounds);
            contentValues.put(OnlineDioContract.Profile.Favorites,favorites);
            contentValues.put(OnlineDioContract.Profile.Likes,likes);
            contentValues.put(OnlineDioContract.Profile.Followings,followings);
            contentValues.put(OnlineDioContract.Profile.Audiences,audiences);
         return contentValues;
        }

        public Profile(String id, String facebook_id, String username, String password, String avatar, String cover_image, String display_name, String full_name, String phone, String birthday, String gender, String country_id, String storage_plan_id, String description, String created_at, String updated_at, String sounds, String favorites, String likes, String followings, String audiences)
        {
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
            return username+"  "+display_name;
        }
    }

}
