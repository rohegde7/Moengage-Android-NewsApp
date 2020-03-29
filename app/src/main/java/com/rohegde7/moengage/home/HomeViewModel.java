package com.rohegde7.moengage.home;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rohegde7.moengage.MoEngageApplication;
import com.rohegde7.moengage.R;
import com.rohegde7.moengage.enums.HomeAction;
import com.rohegde7.moengage.pojos.Article;

import java.util.List;

/*
 * Class where all the business logic and observables reside
 * */
public class HomeViewModel extends ViewModel {

    HomeRepository mRepository = new HomeRepository();

    MutableLiveData<Boolean> viewSavedArticles = new MutableLiveData<>();

    // We do not need title to be observable as of now, but if we make proper use of data binding then it is useful
    ObservableField<String> title = new ObservableField<>(MoEngageApplication.getAppContext().getResources().getString(R.string.home_page_title));

    void downloadNews() {
        mRepository.downloadNews();
    }

    MutableLiveData<HomeAction> getHomeActionLiveData() {
        return mRepository.getHomeActionLiveData();
    }

    MutableLiveData<Boolean> getViewSavedArticlesLiveData() {
        return viewSavedArticles;
    }

    List<Article> getNewsArticles() {
        return mRepository.getNewsArticles();
    }

    public void onViewSavedArticleButtonClicked() {
        viewSavedArticles.setValue(true);
    }
}
