package com.example.ibrah.newapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ibrah on 18/07/2017.
 */

public class AdapterNews extends ArrayAdapter<New> {

    public AdapterNews(Context context, List<New> News) {
        super(context, 0, News);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, parent, false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list
        final New currenNew = getItem(position);

        //text view for the title of the New
        TextView autor = (TextView) listItemView.findViewById(R.id.author);
        if(currenNew.getAuthor()==""){
        autor.setText("Unknown");}else{
            autor.setText(currenNew.getAuthor());
        }
        //text view for the title of the New
        TextView title = (TextView) listItemView.findViewById(R.id.titlee);
        title.setText(currenNew.getTitle());
        //text view for the title of the New
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        TextView timee = (TextView) listItemView.findViewById(R.id.time);
        String[] DATETIME=currenNew.getmDate().split("T");
        date.setText(DATETIME[0]);
        timee.setText(DATETIME[1]);
        TextView Category = (TextView) listItemView.findViewById(R.id.category);
        Category.setText(currenNew.getmSection());

        return listItemView;
    }
}
