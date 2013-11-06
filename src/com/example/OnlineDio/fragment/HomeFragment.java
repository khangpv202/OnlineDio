package com.example.OnlineDio.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.OnlineDio.R;
import com.example.OnlineDio.provider.OnlineDioContract;
import com.example.OnlineDio.util.DownloadImageTask;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 14/10/2013
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class HomeFragment extends Fragment
{
    ListView lisView;
    private ImageButton home_ibOption;
    private LinearLayout layoutDrawer;
    SimpleCursorAdapter mAdapter;
    Cursor homeCursor;
    public static String userID = null;


    public static Fragment newInstance(Context context)
    {
        HomeFragment f = new HomeFragment();

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        homeCursor = getActivity().managedQuery(OnlineDioContract.Home.CONTENT_URI, null,
                null, null, null);

        if (homeCursor.getCount() == 0)
        {
            AccountManager accountManager = AccountManager.get(getActivity().getApplicationContext());
            Account account = accountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE)[0];
            Bundle bundle = new Bundle();
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            ContentResolver.requestSync(account, OnlineDioContract.AUTHORITY, bundle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.home, container, false);
        lisView = (ListView) view.findViewById(R.id.lvListSongs);

        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.home_row_of_listview2,
                homeCursor, new String[]{OnlineDioContract.Home.Comments,
                OnlineDioContract.Home.Likes,
                OnlineDioContract.Home.DisplayName,
                OnlineDioContract.Home.Title,
                OnlineDioContract.Home.Avatar}, new int[]{R.id.tvNumberOfComment, R.id.tvNumberOfLiked,
                R.id.tvNameOfDirector, R.id.tvTitleOfSong,
                R.id.ivAvatars});
        AccountManager accountManager = AccountManager.get(getActivity().getApplicationContext());
        Account account = accountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE)[0];
        /*if (!ContentResolver.isSyncActive(account, OnlineDioContract.AUTHORITY) && !ContentResolver.isSyncPending(account, OnlineDioContract.AUTHORITY))
        {
            refreshDataOfListView();
        }*/
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
        home_ibOption = (ImageButton) view.findViewById(R.id.ibOption);
        layoutDrawer = (LinearLayout) getActivity().findViewById(R.id.left_drawer);
        //ArrayList<SongDTO> listItems = buildData();
        //lisView.setAdapter(new ListViewCustomerAdapter(getActivity(), listItems));
        lisView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id)
            {
                Toast.makeText(getActivity(), pos + " " + id, Toast.LENGTH_SHORT).show();
                FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.navigation_main_FrameLayout, new ContentFragment());
                tx.addToBackStack(null);
                tx.commit();
            }
        });
        final DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.navigation_drawer_layout);
        home_ibOption.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                drawer.openDrawer(layoutDrawer);
            }
        });

        return view;
    }

    private void setThumbResource(View view, Cursor cursor)
    {
        new DownloadImageTask((ImageView) view.findViewById(R.id.ivAvatars)).execute(cursor.getString(15));
    }

   /* private void refreshDataOfListView()
    {

        homeCursor = getActivity().managedQuery(OnlineDioContract.Home.CONTENT_URI, null,
                null, null, null);
        mAdapter.changeCursor(homeCursor);
    }*/

}
