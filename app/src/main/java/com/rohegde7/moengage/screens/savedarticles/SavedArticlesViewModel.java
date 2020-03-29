package com.rohegde7.moengage.screens.savedarticles;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.rohegde7.moengage.MoEngageApplication;
import com.rohegde7.moengage.R;

public class SavedArticlesViewModel extends ViewModel {

    ObservableField<String> title = new ObservableField<>(MoEngageApplication.getAppContext().getResources().getString(R.string.saved_articles_page_title));
}
