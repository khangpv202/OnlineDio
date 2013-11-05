package com.example.OnlineDio.fragment;

import android.app.AlertDialog;
import android.content.*;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.OnlineDio.R;
import com.example.OnlineDio.util.CircularImageView;
import com.example.OnlineDio.util.CropOption;
import com.example.OnlineDio.util.CropOptionAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 16/10/2013
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class ContentFragment extends Fragment
{
    private static final String FLAG_DETAIL = "Detail";
    private static final String FLAG_THUMBNAIL = "Thumbnail";
    private static final String FLAG_COMMENT = "Comment";
    private RadioButton content_rbThumbnail;
    private RadioButton content_rbDetail;
    private RadioButton content_rbComment;
    private Handler mHandler = new Handler();
    LinearLayout layoutDrawer;
    private Button content_btBack;
    private DrawerLayout drawer;
    private Button content_btnPlay;
    private CircularImageView mImageView;
    private FrameLayout content_frame_layout;
    boolean check_Detail = false;
    boolean check_Thumbnail = true;
    boolean check_Comment = false;
    private String flag;
    boolean check_play = true;
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    private Button content_btPlayMusic;
    private MediaPlayer mediaPlayer;
    private SeekBar content_SeekbarMusic;

    private final Handler handler = new Handler();

    public static Fragment newInstance(Context context)
    {
        Fragment f = new ContentFragment();
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.content_layout, container, false);
        content_rbComment = (RadioButton) view.findViewById(R.id.content_rbComment);
        content_rbDetail = (RadioButton) view.findViewById(R.id.content_rbDetail);
        content_btnPlay = (Button) view.findViewById(R.id.content_btnPlay);
        content_rbThumbnail = (RadioButton) view.findViewById(R.id.content_rbThumbnail);
        mImageView = (CircularImageView) view.findViewById(R.id.content_imgAvatar);
        content_btBack = (Button) view.findViewById(R.id.content_btBack);
        layoutDrawer = (LinearLayout) getActivity().findViewById(R.id.left_drawer);


        drawer = (DrawerLayout) getActivity().findViewById(R.id.navigation_drawer_layout);
        content_btBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getFragmentManager().popBackStack();
            }
        });
        content_rbComment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                CommentFragment commentFragment = new CommentFragment();
                tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                tx.replace(R.id.content_frame_layout, commentFragment);
                tx.commit();
            }
        });
        content_rbDetail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                DetailFragment detailFragment = new DetailFragment();
                tx.replace(R.id.content_frame_layout, detailFragment);
                tx.commit();
            }
        });
        content_rbThumbnail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                ThumbnailFragment thumbnailFragment = new ThumbnailFragment();
                tx.replace(R.id.content_frame_layout, thumbnailFragment);
                tx.commit();
            }
        });
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        ThumbnailFragment thumbnailFragment = new ThumbnailFragment();
        tx.replace(R.id.content_frame_layout, thumbnailFragment);
        tx.commit();
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener()

        {
            public void onClick(DialogInterface dialog, int item)
            { //pick from camera
                if (item == 0)
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                    try
                    {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    }
                    catch (ActivityNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                { //pick from file
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                }
            }
        }

        );
        final AlertDialog dialog = builder.create();
        mImageView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                dialog.show();
            }
        }

        );

        content_btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case PICK_FROM_CAMERA:
                doCrop();

                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();

                doCrop();

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();

                if (extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");

                    mImageView.setImageBitmap(photo);
                }

                File f = new File(mImageCaptureUri.getPath());

                if (f.exists())
                {
                    f.delete();
                }

                break;

        }
    }

    private void doCrop()
    {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0)
        {
            Toast.makeText(getActivity(), "Can not find image crop app", Toast.LENGTH_SHORT).show();

            return;
        }
        else
        {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1)
            {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            }
            else
            {
                for (ResolveInfo res : list)
                {
                    final CropOption co = new CropOption();

                    co.title = getActivity().getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getActivity().getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(getActivity().getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {
                        startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {

                        if (mImageCaptureUri != null)
                        {
                            getActivity().getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }
}

