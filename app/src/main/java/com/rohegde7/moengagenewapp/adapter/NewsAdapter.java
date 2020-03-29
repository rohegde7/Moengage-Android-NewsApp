package com.rohegde7.moengagenewapp.adapter;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rohegde7.moengagenewapp.R;
import com.rohegde7.moengagenewapp.pojos.Article;
import com.rohegde7.moengagenewapp.util.SharedPrefUtil;
import com.rohegde7.moengagenewapp.util.UiUtil;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Activity mContext;
    private List<Article> mArticles;

    public NewsAdapter(Activity context, List<Article> articles) {
        mContext = context;
        mArticles = articles;
    }

    public void setItems(List<Article> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = mArticles.get(position);

        setUpItemViewAccordingToNewsData(article, holder);
        setUpWebViewToBeOpenedOnClick(article.getUrl(), holder);
        setUpSaveImageView(article, holder);
        addSaveImageOnClickListener(holder, position);
        setUpDeleteImageViewListener(holder, position);
//        new ImageLoader(article.getUrlToImage(), holder.newsImage).execute();
    }

    private void setUpItemViewAccordingToNewsData(Article article, NewsViewHolder holder) {
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        holder.datePosted.setText(article.getPublishedAt());
        holder.url.setText(article.getUrl());
    }

    private void setUpSaveImageView(Article article, NewsViewHolder holder) {
        if (article.isViewingModeSaved()) {
            holder.saveImageView.setVisibility(View.GONE);
            holder.deleteImageView.setVisibility(View.VISIBLE);
        } else if (article.isSaved())
            holder.saveImageView.setImageResource(R.drawable.ic_bookmark_pink_24);
        else holder.saveImageView.setImageResource(R.drawable.ic_bookmark_empty_24dp);
    }

    private void setUpWebViewToBeOpenedOnClick(final String url, NewsViewHolder holder) {
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtil.startWebView(mContext, url);
            }
        });
    }

    private void addSaveImageOnClickListener(final NewsViewHolder holder, final int position) {
        holder.saveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefUtil.saveArticleInPref(mContext, mArticles.get(position));

                Article article = mArticles.get(position);
                article.setSaved(!article.isSaved());

                changeSavedImageBasesOnWhetherItIsSaved(article.isSaved(), holder);
            }
        });
    }

    private void changeSavedImageBasesOnWhetherItIsSaved(boolean isSaved, final NewsViewHolder holder) {
        if (isSaved) holder.saveImageView.setImageResource(R.drawable.ic_bookmark_pink_24);
        else holder.saveImageView.setImageResource(R.drawable.ic_bookmark_empty_24dp);
    }

    private void setUpDeleteImageViewListener(final NewsViewHolder holder, final int position) {
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article deletedArticle = mArticles.get(position);
                SharedPrefUtil.deleteArticleFromPref(mContext, deletedArticle);
                deleteArticleFromList(deletedArticle);

                if (mArticles.size() == 0) mContext.finish();
            }
        });
    }

    private void deleteArticleFromList(Article deletedArticle) {
        for (Article article : mArticles)
            if (article.equals(deletedArticle)) {
                mArticles.remove(article);
                notifyDataSetChanged();
                break;
            }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        CardView root;
        ImageView newsImage;
        TextView title;
        TextView author;
        TextView datePosted;
        TextView url;
        ImageView saveImageView;
        ImageView deleteImageView;

        public NewsViewHolder(View view) {
            super(view);

            root = view.findViewById(R.id.root);
            newsImage = view.findViewById(R.id.news_image);
            title = view.findViewById(R.id.news_title_textview);
            author = view.findViewById(R.id.author_name_textview);
            datePosted = view.findViewById(R.id.date_posted_textview);
            url = view.findViewById(R.id.url_textview);
            saveImageView = view.findViewById(R.id.save_imageview);
            deleteImageView = view.findViewById(R.id.delete_imageview);
        }
    }
}
