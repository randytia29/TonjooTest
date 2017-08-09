package com.example.randytia.tonjootest;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.io.InputStream;

/**
 * Created by Randytia on 08/08/2017.
 */

public class TonjooAdapter extends ArrayAdapter<Tonjoo> {

    public TonjooAdapter(Activity context, ArrayList<Tonjoo> tonjoos) {
        super(context, 0, tonjoos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.tonjoo_list_item, parent, false);
        }

        final Tonjoo currentTonjoo = getItem(position);

        TextView firstNameView = (TextView) listItemView.findViewById(R.id.first_name_text_view);
        firstNameView.setText(currentTonjoo.getmFirstName() + " - ");

        TextView lastNameView = (TextView) listItemView.findViewById(R.id.last_name_text_view);
        lastNameView.setText(currentTonjoo.getmLastName());

        TextView genderView = (TextView) listItemView.findViewById(R.id.gender_text_view);
        genderView.setText(currentTonjoo.getmGender());

        TextView emailView = (TextView) listItemView.findViewById(R.id.email_text_view);
        emailView.setText(currentTonjoo.getmEmail());

        TextView imageView = (TextView) listItemView.findViewById(R.id.image);
        imageView.setText(currentTonjoo.getmAvatar());

        return listItemView;
    }
}