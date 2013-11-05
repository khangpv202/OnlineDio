package com.example.OnlineDio.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.OnlineDio.R;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 17/10/2013
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public class DetailFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        return view;
    }
}
