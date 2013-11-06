package com.example.OnlineDio.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import com.example.OnlineDio.R;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 21/10/2013
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
public class CommentPostActivity extends FragmentActivity
{
    private Button comment_btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_post_layout);
        comment_btCancel = (Button) findViewById(R.id.comment_btCancel);
        comment_btCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
