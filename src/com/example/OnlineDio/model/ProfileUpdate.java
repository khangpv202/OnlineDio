package com.example.OnlineDio.model;

/**
 * Created with IntelliJ IDEA.
 * User: kpv
 * Date: 11/6/13
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProfileUpdate
{
    private final String id;
    private final String full_name;
    private final String display_name;
    private final String phone;
    private final String birthday;
    private final String gender;
    private final String country_id;
    private final String description;

    public ProfileUpdate(String id, String full_name, String displayName, String phone, String birthday, String gender, String country_id, String description)
    {
        this.id = id;
        this.full_name = full_name;
        this.display_name = displayName;
        this.phone = phone;
        this.birthday = birthday;
        this.gender = gender;
        this.country_id = country_id;
        this.description = description;
    }

    public String getId()
    {
        return id;
    }
}
