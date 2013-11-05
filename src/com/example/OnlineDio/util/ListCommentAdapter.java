package com.example.OnlineDio.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.OnlineDio.R;
import com.example.OnlineDio.model.CommentDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 18/10/2013
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class ListCommentAdapter extends ArrayAdapter<CommentDTO>
{
    private Context context;

    public ListCommentAdapter(Context context, int resource, List<CommentDTO> objects)
    {
        super(context, resource, objects);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder
    {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvComment;
        TextView tvTime;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        CommentDTO rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.comment_row_custom, null);
            holder = new ViewHolder();

            holder.ivAvatar = (ImageView) convertView.findViewById(R.id.list_comment_avata);
            holder.tvName = (TextView) convertView.findViewById(R.id.list_comment_name);
            holder.tvComment = (TextView) convertView.findViewById(R.id.list_comment_comment);
            holder.tvTime = (TextView) convertView.findViewById(R.id.list_comment_time);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivAvatar.setImageResource(R.drawable.content_avatatest);
        holder.tvName.setText(rowItem.getCommentName());
        holder.tvComment.setText((rowItem.getCommentComment()));
        holder.tvTime.setText(rowItem.getCommentTime());
        return convertView;
    }
}
