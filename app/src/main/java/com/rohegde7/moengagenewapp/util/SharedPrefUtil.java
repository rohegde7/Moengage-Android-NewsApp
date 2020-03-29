package com.rohegde7.moengagenewapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rohegde7.moengagenewapp.pojos.Article;

import java.util.ArrayList;
import java.util.List;

import static com.rohegde7.moengagenewapp.util.Constants.SAVED_ARTICLES;

public class SharedPrefUtil {

    /*
     * Will save individual article in the shared pref if it is not present,
     * else if it is already present then it will delete it, same as the save button works!
     * */
    public static void saveArticleInPref(Context context, Article article) {
        SharedPreferences.Editor editor = context.getSharedPreferences("", Context.MODE_PRIVATE).edit();

        List<Article> savedArticles = getSavedArticlesFromPref(context);

        if (!isArticlePresentInList(savedArticles, article)) savedArticles.add(article);
        else savedArticles.remove(article);

        String articleListString = new Gson().toJson(savedArticles);
        editor.putString(SAVED_ARTICLES, articleListString).commit();
    }

    private static boolean isArticlePresentInList(List<Article> articleList, Article article) {
        for (Article savedArticle : articleList)
            if (savedArticle.equals(article))
                return true;

        return false;
    }

    /*
     * Will delete the saved article from shared pref
     * */
    public static void deleteArticleFromPref(Context context, Article article) {
        SharedPreferences.Editor editor = context.getSharedPreferences("", Context.MODE_PRIVATE).edit();

        List<Article> savedArticles = getSavedArticlesFromPref(context);

        for (Article savedArticle : savedArticles) {
            if (savedArticle.equals(article)) {
                savedArticles.remove(savedArticle);
                break;
            }
        }

        String articleListString = new Gson().toJson(savedArticles);
        editor.putString(SAVED_ARTICLES, articleListString).commit();
    }

    /*
     * Returns back all the saved articles in shared pref
     * */
    public static List<Article> getSavedArticlesFromPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("", Context.MODE_PRIVATE);

        String articleListString = sharedPref.getString(SAVED_ARTICLES, "");

        List<Article> savedArticles = new Gson().fromJson(articleListString, new TypeToken<List<Article>>() {
        }.getType());

        if (savedArticles == null) return new ArrayList<Article>();
        return savedArticles;
    }

    public static void checkIfArticlesAreSaved(Context context, List<Article> articleList) {
        List<Article> savedArticles = getSavedArticlesFromPref(context);

        for (Article newArticle : articleList) {
            newArticle.setSaved(false);

            for (Article savedArticle : savedArticles) {
                if (savedArticle.equals(newArticle)) {
                    newArticle.setSaved(true);
                    break;
                }
            }
        }
    }
}
