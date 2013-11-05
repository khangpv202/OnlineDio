package com.example.OnlineDio.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.OnlineDio.R;
import com.example.OnlineDio.model.SongDTO;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 10/11/13
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListViewCustomerAdapter extends BaseAdapter
{
    private ArrayList<SongDTO> listData;
    private LayoutInflater layoutInflater;


    public ListViewCustomerAdapter(Context context, ArrayList<SongDTO> listItems)
    {
        this.listData = listItems;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount()
    {
        return listData.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int position)
    {
        return listData.get(position);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int position)
    {
        return position;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.home_row_of_listview2, null);
            holder = new ViewHolder();
            holder.titleOfSong = (TextView) convertView.findViewById(R.id.tvTitleOfSong);
            holder.imge_avata = (ImageView) convertView.findViewById(R.id.ivAvatars);

            holder.nameOfDirector = (TextView) convertView.findViewById(R.id.tvNameOfDirector);
            holder.numberOfComment = (TextView) convertView.findViewById(R.id.tvNumberOfComment);
            holder.numberOfLike = (TextView) convertView.findViewById(R.id.tvNumberOfLiked);
            holder.numberOfPostedDay = (TextView) convertView.findViewById(R.id.tvNumberOfPostedDay);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleOfSong.setText(listData.get(position).getTitleOfSong());
        holder.imge_avata.setImageResource(R.drawable.home_avatasnhac);
        holder.nameOfDirector.setText(listData.get(position).getNameOfDirector());
        holder.numberOfLike.setText(listData.get(position).getNumberOfLike());
        holder.numberOfComment.setText(listData.get(position).getNumberOfComment());
        holder.numberOfPostedDay.setText(listData.get(position).getNumberOfPostedDay());
        return convertView;
    }

    static class ViewHolder
    {
        ImageView imge_avata;
        TextView titleOfSong;
        TextView nameOfDirector;
        TextView numberOfLike;
        TextView numberOfComment;
        TextView numberOfPostedDay;
    }
}
