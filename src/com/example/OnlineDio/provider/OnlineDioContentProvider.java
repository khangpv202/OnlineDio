package com.example.OnlineDio.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.example.OnlineDio.OnlineDioFramework.rest.RESTfulContentProvider;
import com.example.OnlineDio.OnlineDioFramework.rest.ResponseHandler;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 10/31/13
 * Time: 9:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class OnlineDioContentProvider extends RESTfulContentProvider
{
    private OnlineDioHelper mDatabaseHelper;
    private static final String QUERY_URI = "http://113.160.50.84:1009/testing/ica467/trunk/public/user-rest/";

    private static final int CONTENT_FEED = 1;
    private static final int CONTENT_FEEDS = 2;
    private static final int PROFILE_INFOR = 3;
    private static final int PROFILE_INFORS = 4;


    private static UriMatcher sUriMatcher;

    static
    {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(OnlineDioContract.AUTHORITY,
                "home", CONTENT_FEED);
        // use of the hash character indicates matching of an id
        sUriMatcher.addURI(OnlineDioContract.AUTHORITY,
                "home/*", CONTENT_FEEDS);
        sUriMatcher.addURI(OnlineDioContract.AUTHORITY,
                "profile", PROFILE_INFOR);
        sUriMatcher.addURI(OnlineDioContract.AUTHORITY,
                "profile/*", CONTENT_FEEDS);
    }

    @Override
    public Uri insert(Uri uri, ContentValues cv, SQLiteDatabase db)
    {
        return null;
    }

    @Override
    public SQLiteDatabase getDatabase()
    {
        return null;
    }

    @Override
    protected ResponseHandler newResponseHandler(String requestTag)
    {
        return new ProfileHander(this, requestTag);
    }

    @Override
    public boolean onCreate()
    {
        mDatabaseHelper = new OnlineDioHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        //Cursor queryCursor;

        int match = sUriMatcher.match(uri);
        switch (match)
        {
            case CONTENT_FEED:
            {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(OnlineDioContract.Home.TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case CONTENT_FEEDS:
            {
                int id = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder1 = new SQLiteQueryBuilder();
                builder1.setTables(OnlineDioContract.Home.TABLE_NAME);
                builder1.appendWhere(OnlineDioContract.Home._ID + "=" + id);
                return builder1.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case PROFILE_INFOR:
            {

                asyncQueryRequest(selection, QUERY_URI);
//                return db.query(OnlineDioContract.Profile.TABLE_NAME,projection,null,null,null,null,null);
            }

            default:
                return null;
        }
    }

    @Override
    public String getType(Uri uri)
    {
        final int match = sUriMatcher.match(uri);
        switch (match)
        {
            case CONTENT_FEED:
                return OnlineDioContract.Home.CONTENT_TYPE;
            case CONTENT_FEEDS:
                return OnlineDioContract.Home.CONTENT_ITEM_TYPE;
            case PROFILE_INFOR:
                return OnlineDioContract.Profile.CONTENT_ITEM_TYPE;
            case PROFILE_INFORS:
                return OnlineDioContract.Profile.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int token = sUriMatcher.match(uri);
        switch (token)
        {
            case CONTENT_FEED:
            {
                long id = db.insert(OnlineDioContract.Home.TABLE_NAME, null, values);
                if (id != -1)
                {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return OnlineDioContract.Home.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            case PROFILE_INFOR:
            {
                long id = db.insert(OnlineDioContract.Profile.TABLE_NAME, null, values);
                if (id != -1)
                {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return OnlineDioContract.Profile.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default:
            {
                throw new UnsupportedOperationException("URI: " + uri + " not supported");
            }

        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int token = sUriMatcher.match(uri);
        int rowsDeleted = -1;
        switch (token)
        {
            case (CONTENT_FEED):
                rowsDeleted = db.delete(OnlineDioContract.Home.TABLE_NAME, selection, selectionArgs);
                break;
            case (CONTENT_FEEDS):
                String tvShowIdWhereClause = OnlineDioContract.Home._ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                {
                    tvShowIdWhereClause += " AND " + selection;
                }
                rowsDeleted = db.delete(OnlineDioContract.Home.TABLE_NAME, tvShowIdWhereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = mDatabaseHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case PROFILE_INFORS:
                rowsUpdated = sqlDB.update(OnlineDioContract.Profile.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case PROFILE_INFOR:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(OnlineDioContract.Profile.TABLE_NAME,
                            values,
                            OnlineDioContract.Profile._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(OnlineDioContract.Profile.TABLE_NAME,
                            values,
                            OnlineDioContract.Profile._ID+ "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private static class OnlineDioHelper extends SQLiteOpenHelper
    {
        public static final int DATABASE_VERSION = 1;
        private String SQL_CREATE_HOME =
                "CREATE TABLE " + OnlineDioContract.Home.TABLE_NAME + " (" +
                        OnlineDioContract.Home._ID + " INTEGER PRIMARY KEY," +
                        OnlineDioContract.Home.UserID + " TEXT," +
                        OnlineDioContract.Home.Title + " TEXT," +
                        OnlineDioContract.Home.Thumbnail + " TEXT," +
                        OnlineDioContract.Home.Description + " TEXT," +
                        OnlineDioContract.Home.SoundPath + " TEXT," +
                        OnlineDioContract.Home.Duration + " TEXT," +
                        OnlineDioContract.Home.Played + " TEXT," +
                        OnlineDioContract.Home.Likes + " TEXT," +
                        OnlineDioContract.Home.Viewed + " TEXT," +
                        OnlineDioContract.Home.Comments + " TEXT," +
                        OnlineDioContract.Home.CreatedAt + " TEXT," +
                        OnlineDioContract.Home.UpdatedAt + " TEXT," +
                        OnlineDioContract.Home.Username + " TEXT," +
                        OnlineDioContract.Home.DisplayName + " TEXT," +
                        OnlineDioContract.Home.Avatar + " TEXT)";
        private String SQL_CREATE_PROFILE =
                "CREATE TABLE " + OnlineDioContract.Profile.TABLE_NAME + " (" +
                        OnlineDioContract.Profile._ID + " INTEGER PRIMARY KEY," +
                        OnlineDioContract.Profile.FacebookId + " TEXT," +
                        OnlineDioContract.Profile.Id + " TEXT," +
                        OnlineDioContract.Profile.UserName + " TEXT," +
                        OnlineDioContract.Profile.Password + " TEXT," +
                        OnlineDioContract.Profile.Avatar + " TEXT," +
                        OnlineDioContract.Profile.CoverImage + " TEXT," +
                        OnlineDioContract.Profile.DisplayName + " TEXT," +
                        OnlineDioContract.Profile.FullName + " TEXT," +
                        OnlineDioContract.Profile.Phone + " TEXT," +
                        OnlineDioContract.Profile.Birthday + " TEXT," +
                        OnlineDioContract.Profile.Gender + " TEXT," +
                        OnlineDioContract.Profile.CountryId + " TEXT," +
                        OnlineDioContract.Profile.StoragePlanId + " TEXT," +
                        OnlineDioContract.Profile.Description + " TEXT," +
                        OnlineDioContract.Profile.CreatedAt + " TEXT," +
                        OnlineDioContract.Profile.UpdateAt + " TEXT," +
                        OnlineDioContract.Profile.Sounds + " TEXT," +
                        OnlineDioContract.Profile.Favorites + " TEXT," +
                        OnlineDioContract.Profile.Likes + " TEXT," +
                        OnlineDioContract.Profile.Followings + " TEXT," +
                        OnlineDioContract.Profile.Audiences + " TEXT)";
        private String SQL_DELETE_HOME =
                "DROP TABLE IF EXISTS " + OnlineDioContract.Home.TABLE_NAME;
        private String SQL_DELETE_PROFILE =
                "DROP TABLE IF EXISTS " + OnlineDioContract.Profile.TABLE_NAME;

        public OnlineDioHelper(Context context)
        {
            super(context, OnlineDioContract.ONLINEDIO_DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(SQL_CREATE_HOME);
            db.execSQL(SQL_CREATE_PROFILE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL(SQL_DELETE_HOME);
            db.execSQL(SQL_DELETE_PROFILE);

            onCreate(db);
        }
    }


}
