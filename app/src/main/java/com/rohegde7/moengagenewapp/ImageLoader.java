package com.rohegde7.moengagenewapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader extends AsyncTask<Void, Void, Bitmap> {

    private String mUrl;
    private ImageView mImageView;

    public ImageLoader(String url, ImageView imageView) {
        mUrl = url;
        mImageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();

            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        try {
            mImageView.setImageBitmap(result);
        } catch (Exception ignore) {
            // Exception here was because bitmap size was greater than the limit
        }
    }
}
