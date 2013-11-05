package com.example.OnlineDio.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.OnlineDio.R;
import com.example.OnlineDio.accounts.AccountGeneral;
import com.example.OnlineDio.accounts.User;
import com.example.OnlineDio.model.UserProfile;
import com.example.OnlineDio.provider.OnlineDioContract;
import com.example.OnlineDio.util.EmailValidator;
import com.example.OnlineDio.util.StreamUtils;

public class LoginActivity extends AccountAuthenticatorActivity
{
    /**
     * Called when the activity is first created.
     */
    private Button login_btDone;
    private Button login_btBack;
    private EditText login_edEmail;
    private EditText login_edPass;
    private ImageButton login_iv_cancelOfEmail;
    private ImageButton login_iv_cancelOfPass;
    private TextView login_tvForgotPass;
    private boolean typedEmail = false;
    private boolean typedPass = false;
    private String dumEmail = "quachtest@gmail.com";
    private String dumPass = "e10adc3949ba59abbe56e057f20f883e";

    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    private AccountManager mAccountManager;


    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";

    public final static String PARAM_USER_PASS = "USER_PASS";

    private final int REQ_SIGNUP = 1;

    private final String TAG = this.getClass().getSimpleName();


    private String mAuthTokenType;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        mAccountManager = AccountManager.get(getBaseContext());
        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
        {
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }

        if (accountName != null)
        {
            ((TextView) findViewById(R.id.login_et_email)).setText(accountName);
        }
        login_btDone = (Button) findViewById(R.id.login_btDone);
        login_btBack = (Button) findViewById(R.id.login_btBack);
        login_edEmail = (EditText) findViewById(R.id.login_et_email);
        login_edPass = (EditText) findViewById(R.id.login_et_Pass);

        login_iv_cancelOfEmail = (ImageButton) findViewById(R.id.login_ib_cancelOfEmail);
        login_iv_cancelOfPass = (ImageButton) findViewById(R.id.login_ib_cancelOfPass);

        login_edEmail.addTextChangedListener(textChangeListener);
        login_edPass.addTextChangedListener(textChangeListener);

        login_edEmail.setOnFocusChangeListener(focusChange);
        login_edPass.setOnFocusChangeListener(focusChange);

        login_iv_cancelOfEmail.setOnClickListener(cancelOfBtEmail);
        login_iv_cancelOfPass.setOnClickListener(cancelOfBtPass);

        login_btDone.setOnClickListener(new ClickListener());

    }

    private View.OnFocusChangeListener focusChange = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            switch (v.getId())
            {
                case R.id.login_et_email:
                    if (!hasFocus)
                    {
                        login_iv_cancelOfEmail.setVisibility(View.INVISIBLE);
                        if (!login_edEmail.getText().toString().isEmpty() && new EmailValidator().validate(login_edEmail.getText().toString()) == false)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                    else
                    {
                        if (!login_edEmail.getText().toString().equals(""))
                        {
                            login_iv_cancelOfEmail.setVisibility(View.VISIBLE);

                        }
                    }
                    break;

                case R.id.login_et_Pass:
                    if (!hasFocus)
                    {
                        login_iv_cancelOfPass.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        if (!login_edPass.getText().toString().isEmpty())
                        {
                            login_iv_cancelOfPass.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
            }
        }
    };
    private View.OnClickListener cancelOfBtEmail = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            login_edEmail.setText("");
        }
    };
    private View.OnClickListener cancelOfBtPass = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            login_edPass.setText("");
        }
    };
    private TextWatcher textChangeListener = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (!login_edEmail.getText().toString().equals("") && login_edEmail.isFocused() == true)
            {
                typedEmail = true;
                login_iv_cancelOfEmail.setVisibility(View.VISIBLE);
            }
            else
            {
                login_iv_cancelOfEmail.setVisibility(View.INVISIBLE);

            }
            if (!login_edPass.getText().toString().isEmpty() && login_edPass.isFocused() == true)
            {
                login_iv_cancelOfPass.setVisibility(View.VISIBLE);
                typedPass = true;
            }
            else
            {
                login_iv_cancelOfPass.setVisibility(View.INVISIBLE);

            }

            if (new EmailValidator().validate(login_edEmail.getText().toString()) && !login_edEmail.getText().toString().isEmpty() && !login_edPass.getText().toString().isEmpty())
            {
                login_btDone.setEnabled(true);
                login_btDone.setBackgroundDrawable(getResources().getDrawable(R.drawable.soundboard_btn_done));
            }
            else
            {
                login_btDone.setEnabled(false);
                login_btDone.setBackgroundDrawable(getResources().getDrawable(R.drawable.soundboard_btn_done_selected));
            }
        }
    };

    public void validateAccount()
    {

    }

    class ClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.login_btDone:
                    if (new EmailValidator().validate(login_edEmail.getText().toString())/* && login_edEmail.getText().toString().equals(dumEmail) && login_edPass.getText().toString().equals(dumPass)*/)
                    {
                        /*Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
                        startActivity(i);*/
                        submit();
                        //getTokenForAccountCreateIfNeeded(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                        //finish();
                    }

                    else
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        // Setting Dialog Title
                        alertDialog.setTitle("Error Singing In");

                        // Setting Dialog Message
                        alertDialog.setMessage("There is no connection  to the internet ");

                        // Setting OK Button
                        alertDialog.setButton("OK", (DialogInterface.OnClickListener) null);
                        alertDialog.show();
                    }

            }
        }
    }

    boolean check = false;

    public void submit()
    {

        final String userName = ((TextView) findViewById(R.id.login_et_email)).getText().toString();
        final String userPass = ((TextView) findViewById(R.id.login_et_Pass)).getText().toString();

        final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);

        new AsyncTask<String, Void, Intent>()
        {

            @Override
            protected Intent doInBackground(String... params)
            {

                Log.d("OnlineDio", TAG + "> Started authenticating");

                Bundle data = new Bundle();
                try
                {
                    User user = AccountGeneral.sServerAuthenticate.userSignIn(userName, StreamUtils.convertToMd5(userPass), mAuthTokenType);
                    UserProfile.Profile account = new UserProfile.Profile(user.getUser_id(),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
                    getContentResolver().insert(OnlineDioContract.Profile.CONTENT_URI,account.getContentValues());
                    Cursor c = getContentResolver().query(OnlineDioContract.Profile.CONTENT_URI,null,null,null,null);
                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);

                    data.putString(AccountManager.KEY_AUTHTOKEN, user.getAccess_token());

                    // We keep the user's object id as an extra data on the account.
                    // It's used later for determine ACL for the data we send to the Parse.com service
                    Bundle userData = new Bundle();
                    userData.putString(AccountGeneral.USERDATA_USER_OBJ_ID, user.getUser_id());
                    data.putBundle(AccountManager.KEY_USERDATA, userData);

                    data.putString(PARAM_USER_PASS, userPass);

                }
                catch (Exception e)
                {
                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }

                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent)
            {
                if (intent.hasExtra(KEY_ERROR_MESSAGE))
                {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    finishLogin(intent);
                }
            }
        }.execute();


    }

    private void finishLogin(Intent intent)
    {
        Log.d("OnlineDio", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false))
        {
            Log.d("OnlineDio", TAG + "> finishLogin > addAccountExplicitly");
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;

            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, intent.getBundleExtra(AccountManager.KEY_USERDATA));
            mAccountManager.setAuthToken(account, authtokenType, authtoken);

//            mAccountManager.getAuthTokenByFeatures()
        }
        else
        {
            Log.d("OnlineDio", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
       /* try
        {
            sServerAuthenticate.userFeedContent(intent.getStringExtra(AccountManager.KEY_AUTHTOKEN.toString()));
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/
        Toast.makeText(getApplicationContext(), intent.getStringExtra(AccountManager.KEY_AUTHTOKEN).toString(), Toast.LENGTH_SHORT).show();
        finish();
    }


}

