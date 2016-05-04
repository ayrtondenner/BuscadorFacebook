package br.com.ufg.buscadorfacebook.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class PhotoValidation {
    public static void PhotoSet(final GraphResponse response, final ImageView imageView, final Activity context)
    {
        try {

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String caminho = response.getConnection().toString().split("://")[1];

                    Picasso picasso = Picasso.with(context);
                    RequestCreator requestCreator = picasso.load("https://" + caminho);

                    requestCreator.into(imageView);

                    imageView.setTag(caminho);
                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
