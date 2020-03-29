package com.rohegde7.moengagenewapp.savedarticles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.rohegde7.moengagenewapp.R;
import com.rohegde7.moengagenewapp.adapter.NewsAdapter;
import com.rohegde7.moengagenewapp.databinding.ActivityHomeBinding;
import com.rohegde7.moengagenewapp.databinding.ActivitySavedArticlesBinding;
import com.rohegde7.moengagenewapp.enums.HomeAction;
import com.rohegde7.moengagenewapp.home.HomeViewModel;
import com.rohegde7.moengagenewapp.pojos.Article;
import com.rohegde7.moengagenewapp.util.SharedPrefUtil;

import java.util.List;

import static com.rohegde7.moengagenewapp.enums.HomeAction.NEWS_API_INITIATED;

public class SavedArticlesActivity extends AppCompatActivity {

    SavedArticlesViewModel mViewModel = new ViewModelProvider.NewInstanceFactory().create(SavedArticlesViewModel.class);

    ActivitySavedArticlesBinding mBinding;

    NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        setUpDataBinding();
        fetchSavedArticles();
    }

    private void initUi() {
        setTitle(mViewModel.title.get());
    }

    private void setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_saved_articles);
        mBinding.setViewModel(mViewModel);
    }

    private void fetchSavedArticles() {
        List<Article> savedArticles = SharedPrefUtil.getSavedArticlesFromPref(this);
        for (Article article : savedArticles) article.setViewingModeSaved(true);
        mNewsAdapter = new NewsAdapter(this, savedArticles);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mBinding.newsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mBinding.newsRecyclerview.setAdapter(mNewsAdapter);
    }
}
