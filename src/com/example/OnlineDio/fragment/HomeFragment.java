package com.example.OnlineDio.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.androidquery.AQuery;
import com.example.OnlineDio.R;
import com.example.OnlineDio.accounts.AccountGeneral;
import com.example.OnlineDio.provider.OnlineDioContract;
import com.example.OnlineDio.util.DownloadImageTask;
import com.markupartist.android.widget.PullToRefreshListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 14/10/2013
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class HomeFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener
{
    private ListView lisView;
    private ImageButton home_ibOption;
    private LinearLayout layoutDrawer;
    private SimpleCursorAdapter mAdapter;
    private Cursor homeCursor;
    public static String userID = null;
    private AQuery aq;
    private DrawerLayout drawer;
    private PullToRefreshListView mPullToRefreshListView;
    private int mLoadMore = 0;
    private AccountManager mAccountManager;


    public static Fragment newInstance(Context context)
    {
        HomeFragment f = new HomeFragment();

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAccountManager = AccountManager.get(getActivity().getApplicationContext());

        homeCursor = getActivity().managedQuery(OnlineDioContract.Home.CONTENT_URI, null,
                null, null, null);

        requestSyncWhenEmptyDataInDB();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        aq = new AQuery(getActivity());
        View view = inflater.inflate(R.layout.home, container, false);

        findByIdComponents(view);

        createAdapterForListViewAndBindItems();

//        lisView.setOnItemClickListener(listViewClickedItem);
        home_ibOption.setOnClickListener(homeClickedOption);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mPullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.lvListSongs);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Toast.makeText(getActivity().getApplicationContext(),"do everything u can",Toast.LENGTH_SHORT).show();
                Account account = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
                Bundle bundle = new Bundle();
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                ContentResolver.requestSync(account,OnlineDioContract.AUTHORITY,bundle);
            }
        });
        mPullToRefreshListView.setAdapter(mAdapter);
        mPullToRefreshListView.setOnItemClickListener(listViewClickedItem);
        getLoaderManager().initLoader(0, null,this);
        mPullToRefreshListView.setOnScrollListener(this);

    }

    private void createAdapterForListViewAndBindItems()
    {
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.home_row_of_listview2,
                homeCursor, new String[]{OnlineDioContract.Home.Comments,
                OnlineDioContract.Home.Likes,
                OnlineDioContract.Home.DisplayName,
                OnlineDioContract.Home.Title,
                OnlineDioContract.Home.Avatar}, new int[]{R.id.tvNumberOfComment, R.id.tvNumberOfLiked,
                R.id.tvNameOfDirector, R.id.tvTitleOfSong,
                R.id.ivAvatars});

        mAdapter.setViewBinder(binder);
//        lisView.setAdapter(mAdapter);
    }

    private void setThumbResource(View view, Cursor cursor)
    {
        new DownloadImageTask((ImageView) view.findViewById(R.id.ivAvatars)).execute(cursor.getString(15));
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f)
    {
        try
        {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true)
            {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        }
        catch (FileNotFoundException e)
        {
        }
        return null;
    }

    private SimpleCursorAdapter.ViewBinder binder =
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
                                //setThumbResource(view, cursor);
                                //returns the cached file by url, returns null if url is not cached
                                File file = aq.getCachedFile(cursor.getString(15));

                                if (file != null)
                                {
//                                    Toast.makeText(getActivity(), "not null", Toast.LENGTH_SHORT).show();
                                    ImageView avatar = (ImageView) view.findViewById(R.id.ivAvatars);
                                    avatar.setImageBitmap(decodeFile(file));
                                }
                                else
                                {
//                                    Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                                    setThumbResource(view, cursor);
                                    aq.cache(cursor.getString(15), 0);
                                }
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

    private void requestSyncWhenEmptyDataInDB()
    {
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

    private AdapterView.OnItemClickListener listViewClickedItem = new AdapterView.OnItemClickListener()
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
    };

    private View.OnClickListener homeClickedOption = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            drawer.openDrawer(layoutDrawer);
        }
    };

    private void findByIdComponents(View view)
    {
//        lisView = (ListView) view.findViewById(R.id.lvListSongs);
        home_ibOption = (ImageButton) view.findViewById(R.id.ibOption);
        layoutDrawer = (LinearLayout) getActivity().findViewById(R.id.left_drawer);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.navigation_drawer_layout);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        String limit = "10";
        if(bundle!=null){
            limit = bundle.getString("limit");
        }
        CursorLoader cursorLoader = new CursorLoader(getActivity(),OnlineDioContract.Home.CONTENT_URI,null,null,null,OnlineDioContract.Home._ID+" DESC limit "+limit);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> cursorLoader, Cursor cursor)
    {
        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> cursorLoader)
    {
        mAdapter.changeCursor(null);
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i)
    {
        if (i == SCROLL_STATE_IDLE) {
            if (mPullToRefreshListView.getLastVisiblePosition() >= mPullToRefreshListView
                    .getCount() - 1) {
                mLoadMore++;
                String limit = Integer.toString(mLoadMore * 10 + 10);
                Bundle bundle = new Bundle();
                bundle.putString("limit", limit);
                getLoaderManager().restartLoader(0, bundle, this);
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3)
    {
    }
}
