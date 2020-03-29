package com.rohegde7.moengage.screens.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rohegde7.moengage.R;
import com.rohegde7.moengage.remote.adapter.NewsAdapter;
import com.rohegde7.moengage.databinding.ActivityHomeBinding;
import com.rohegde7.moengage.remote.enums.HomeAction;
import com.rohegde7.moengage.remote.pojos.Article;
import com.rohegde7.moengage.screens.savedarticles.SavedArticlesActivity;
import com.rohegde7.moengage.remote.util.SharedPrefUtil;
import com.rohegde7.moengage.remote.util.UiUtil;

import java.util.List;

import static com.rohegde7.moengage.remote.enums.HomeAction.NEWS_API_INITIATED;

public class HomeActivity extends AppCompatActivity {

    /*
     * Same instance of mViewModel will be maintained throughout the lifecycle of
     * this activity
     * */
    HomeViewModel mViewModel = new ViewModelProvider.NewInstanceFactory().create(HomeViewModel.class);

    ActivityHomeBinding mBinding;

    NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        setUpDataBinding();
        observerHomeActionLiveData();
        startDownloadingNews();
        observeViewSavedArticlesLiveData();
        initSwipeRefreshLayout();
    }

    private void initUi() {
        setTitle(mViewModel.title.get());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        initRecyclerView();
    }

    private void setUpDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mBinding.setViewmodel(mViewModel);
    }

    /*
     * Observers the live data in the repository
     * */
    private void observerHomeActionLiveData() {
        mViewModel.getHomeActionLiveData().observe(this, new Observer<HomeAction>() {
            @Override
            public void onChanged(HomeAction homeAction) {
                handleLiveDataChange(homeAction);
            }
        });
    }

    private void startDownloadingNews() {
        mViewModel.getHomeActionLiveData().setValue(NEWS_API_INITIATED);
        mViewModel.downloadNews();
    }

    private void observeViewSavedArticlesLiveData() {
        mViewModel.getViewSavedArticlesLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    onViewSavedArticleButtonClicked();
                    mViewModel.getViewSavedArticlesLiveData().setValue(false);
                }
            }
        });
    }

    private void initSwipeRefreshLayout() {
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startDownloadingNews();
                mBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void onViewSavedArticleButtonClicked() {
        if (SharedPrefUtil.getSavedArticlesFromPref(HomeActivity.this).size() != 0)
            startActivityForResult(new Intent(HomeActivity.this,
                    SavedArticlesActivity.class), 1);

        else Toast.makeText(HomeActivity.this,
                "You do not have any saved articles!",
                Toast.LENGTH_SHORT).show();
    }

    private void handleLiveDataChange(HomeAction homeAction) {
        switch (homeAction) {

            case NEWS_API_INITIATED:
                UiUtil.displayProgress(this, "Downloading news...");
                break;

            case NEWS_API_ERROR:
                UiUtil.hideProgress();
                Toast.makeText(this, "Unable to fetch news!", Toast.LENGTH_SHORT).show();
                break;

            case NEWS_API_FETCHED_SUCCESSFULLY:
                UiUtil.hideProgress();
                initRecyclerView();
                break;
        }
    }

    private void initRecyclerView() {
        if (mNewsAdapter == null) setUpRecyclerView();
        else updateRecyclerView();
    }

    private void setUpRecyclerView() {
        List<Article> articleList = mViewModel.getNewsArticles();
        SharedPrefUtil.checkIfArticlesAreSaved(this, articleList);

        mNewsAdapter = new NewsAdapter(this, articleList);
        mBinding.newsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mBinding.newsRecyclerview.setAdapter(mNewsAdapter);
    }

    private void updateRecyclerView() {
        List<Article> articleList = mViewModel.getNewsArticles();
        SharedPrefUtil.checkIfArticlesAreSaved(this, articleList);
        mNewsAdapter.setItems(articleList);
    }
}
