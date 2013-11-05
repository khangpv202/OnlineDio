package com.example.OnlineDio.accounts;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 20/03/13
 * Time: 18:11
 */
public class AccountGeneral
{

    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "com.example.OnlineDio";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = "OnlineDio";

    /**
     * User data fields
     */
    public static final String USERDATA_USER_OBJ_ID = "userObjectId";   //Parse.com object id

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an OnlineDio account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an OnlineDio account";

    public static final ServerAuthenticate sServerAuthenticate = new ParseComServer();
}
