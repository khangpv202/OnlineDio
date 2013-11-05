package com.example.OnlineDio.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.OnlineDio.R;
import com.example.OnlineDio.model.SongDTO;
import com.example.OnlineDio.provider.OnlineDioContract;
import com.example.OnlineDio.util.DownloadImageTask;

import java.util.ArrayList;

public class HomeActivity extends Activity
{
    /**
     * Called when the activity is first created.
     */
    ListView lisView;
    private ImageButton home_ibOption;
    SimpleCursorAdapter mAdapter;
    Cursor homeCursor;
    private AccountManager mAccountManager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        lisView = (ListView) findViewById(R.id.lvListSongs);
        ArrayList<SongDTO> listItems = buildData();
        mAccountManager = AccountManager.get(getBaseContext());
        homeCursor = managedQuery(OnlineDioContract.Home.CONTENT_URI, null,
                null, null, null);
        mAdapter = new SimpleCursorAdapter(this, R.layout.home_row_of_listview2,
                homeCursor, new String[]{OnlineDioContract.Home.Comments,
                OnlineDioContract.Home.Likes,
                OnlineDioContract.Home.DisplayName,
                OnlineDioContract.Home.Title,
                OnlineDioContract.Home.Avatar}, new int[]{R.id.tvNumberOfComment, R.id.tvNumberOfLiked,
                R.id.tvNameOfDirector, R.id.tvTitleOfSong,
                R.id.ivAvatars});
//        getDataFromDB();
        SimpleCursorAdapter.ViewBinder savb =
                new SimpleCursorAdapter.ViewBinder()
                {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int i)
                    {
                        switch (i)
                        {
                            case 15:
                                if (cursor.getString(15) != null)
                                {
                                    setThumbResource(view, cursor);
                                }
                                break;
                            case 10:
                                TextView numberOfComment = (TextView)
                                        view.findViewById(R.id.tvNumberOfComment);
                                String comment = cursor.getString(i);
                                numberOfComment.setText("Comment: " + comment);

                                break;
                            case 8:
                                TextView numberOfLiked = (TextView)
                                        view.findViewById(R.id.tvNumberOfLiked);
                                String liked = cursor.getString(i);
                                numberOfLiked.setText("Like: " + liked);
                                break;
                            case 14:
                                TextView nameDisplay = (TextView)
                                        view.findViewById(R.id.tvNameOfDirector);
                                nameDisplay.setText(cursor.getString(14));
                                break;
                            case 2:
                                TextView title = (TextView)
                                        view.findViewById(R.id.tvTitleOfSong);
                                title.setText(cursor.getString(2));
                                break;
                        }

                        return true;
                    }
                };

        mAdapter.setViewBinder(savb);

//        lisView.setAdapter(new ListViewCustomerAdapter(this, listItems));
        lisView.setAdapter(mAdapter);
        ImageButton home_ibOption = (ImageButton) findViewById(R.id.ibOption);

        home_ibOption.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(HomeActivity.this, LauchActivity.class);
                startActivity(i);
            }
        });

    }

    private void setThumbResource(View view, Cursor cursor)
    {
        new DownloadImageTask((ImageView) view.findViewById(R.id.ivAvatars)).execute(cursor.getString(3));
    }

    private ArrayList<SongDTO> buildData()
    {
        ArrayList<SongDTO> listResult = new ArrayList<SongDTO>();
        SongDTO item = new SongDTO("Sound Of Silence", "Mr. Bean", "Liked: 100", "Comment: 9", "s days ago");
        listResult.add(item);
        listResult.add(item);
        listResult.add(item);
        listResult.add(item);
        listResult.add(item);
        listResult.add(item);
        listResult.add(item);
        listResult.add(item);
        return listResult;
    }
}
