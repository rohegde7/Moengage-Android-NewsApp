package com.rohegde7.moengage.screens.home;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.rohegde7.moengage.remote.enums.HomeAction;
import com.rohegde7.moengage.remote.pojos.Article;
import com.rohegde7.moengage.remote.pojos.NewsAPIResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.rohegde7.moengage.remote.enums.HomeAction.NEWS_API_ERROR;
import static com.rohegde7.moengage.remote.enums.HomeAction.NEWS_API_FETCHED_SUCCESSFULLY;

/*
 * Class where all the DB and API calls reside
 * */
class HomeRepository {

    private NewsAPIResponse mNewsApiResponse;

    private MutableLiveData<HomeAction> mHomeActionLiveData = new MutableLiveData<>();

    List<Article> getNewsArticles() {
        if (mNewsApiResponse == null) return new ArrayList<>();
        return mNewsApiResponse.getArticles();
    }

    MutableLiveData<HomeAction> getHomeActionLiveData() {
        return mHomeActionLiveData;
    }

    void downloadNews() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL newsUrl = new URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json");
                    HttpURLConnection urlConnection = (HttpURLConnection) newsUrl.openConnection();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null)
                        stringBuilder.append(line).append("\n");

                    bufferedReader.close();

                    final String response = stringBuilder.toString();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            initNewsApiResponse(response);
                        }
                    });

                } catch (Exception ex) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mHomeActionLiveData.setValue(NEWS_API_ERROR);
                        }
                    });
                }
            }
        }).start();
    }

    private void initNewsApiResponse(String response) {
        mNewsApiResponse = new Gson().fromJson(response, NewsAPIResponse.class);
        mHomeActionLiveData.setValue(NEWS_API_FETCHED_SUCCESSFULLY);
    }
}
