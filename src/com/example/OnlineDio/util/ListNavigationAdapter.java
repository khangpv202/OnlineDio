package com.example.OnlineDio.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.OnlineDio.R;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 14/10/2013
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
public class ListNavigationAdapter extends ArrayAdapter<String>
{
    private final Context context;
    private final String[] values;

    public ListNavigationAdapter(Context context, String[] values)
    {
        super(context, R.layout.navigation_layout_row, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.navigation_layout_row, parent, false);
        TextView navigation_tvMenu = (TextView) rowView.findViewById(R.id.navigation_tvMenu);
        ImageView navigation_imgIcon = (ImageView) rowView.findViewById(R.id.navigation_imgIcon);
        ImageView navigation_imgArow = (ImageView) rowView.findViewById(R.id.navigation_imgArow);
        ImageView navigation_suport = (ImageView) rowView.findViewById(R.id.navigation_imgSuport);
        navigation_tvMenu.setText(values[position]);
        // Change the icon for Windows and iPhone
        String s = values[position];
        if (s.equals("Home"))
        {
            navigation_imgIcon.setImageResource(R.drawable.navigation_homeicon);
        }
        else if (s.equals("Favorite"))
        {
            navigation_imgIcon.setImageResource(R.drawable.navigation_favoriteicon);
        }
        else if (s.equals("Following"))
        {
            navigation_imgIcon.setImageResource(R.drawable.navigation_folowicon);

        }
        else if (s.equals("Audience"))
        {
            navigation_imgIcon.setImageResource(R.drawable.navigation_audienceicon);

        }
        else if (s.equals("Genres"))
        {
            navigation_imgIcon.setImageResource(R.drawable.navigation_genresicon);

        }
        else if (s.equals("Setting"))
        {
            navigation_imgIcon.setImageResource(R.drawable.navigation_settingicon);
        }
        else if (s.equals("Help Center"))
        {
            navigation_imgIcon.setImageResource(R.drawable.navigation_helpicon);
            navigation_suport.setImageResource(R.drawable.navigation_suporticon);
        }
        else if (s.equals("Sign Out"))
        {
            navigation_imgIcon.setImageResource(R.drawable.navigation_singouticon);
        }
        navigation_imgArow.setImageResource(R.drawable.navigation_nextimage);
        return rowView;
    }
}
