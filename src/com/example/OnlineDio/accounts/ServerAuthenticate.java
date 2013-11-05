package com.example.OnlineDio.accounts;

import android.accounts.AccountManager;
import com.example.OnlineDio.provider.dao.HomeShows;

import java.util.List;

/**
 * User: udinic
 * Date: 3/27/13
 * Time: 2:35 AM
 */
public interface ServerAuthenticate
{
    public User userSignUp(final String name, final String email, final String pass, String authType) throws Exception;

    public User userSignIn(final String user, final String pass, String authType) throws Exception;

    public List<HomeShows.HomeShow> userFeedContent(final String token, AccountManager accountManager);
}
