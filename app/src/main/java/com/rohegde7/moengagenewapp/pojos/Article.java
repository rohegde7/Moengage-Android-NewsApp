package com.rohegde7.moengagenewapp.pojos;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {

    boolean isViewingModeSaved = false;
    boolean isSaved = false;
    Source source;
    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String publishedAt;
    String content;

    public boolean isViewingModeSaved() {
        return isViewingModeSaved;
    }

    public void setViewingModeSaved(boolean viewingModeSaved) {
        isViewingModeSaved = viewingModeSaved;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        try {
            SimpleDateFormat originalSDF = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newSDF = new SimpleDateFormat("dd MMM yyyy");
            Date originalDate = originalSDF.parse(publishedAt.substring(0, 10));
            return newSDF.format(originalDate);

        } catch (Exception ex) {
            return "";
        }
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /*
     * Overriding this to facilitate the comparison of contents of 2 Article objects
     * */
    @Override
    public boolean equals(@Nullable Object obj) {
        Article article = (Article) obj;

        boolean isEqual = true;

        if (article != null) {
            if (article.source != null && article.source.id != null)
                isEqual &= article.source.id.equals(source.id);

            if (article.source != null && article.source.name != null)
                isEqual &= article.source.name.equals(source.name);

            if (article.author != null)
                isEqual &= article.author.equals(author);

            if (article.title != null)
                isEqual &= article.title.equals(title);

            if (article.description != null)
                isEqual &= article.description.equals(description);

            if (article.url != null)
                isEqual &= article.url.equals(url);

            if (article.urlToImage != null)
                isEqual &= article.urlToImage.equals(urlToImage);

            if (article.publishedAt != null)
                isEqual &= article.publishedAt.equals(publishedAt);

            if (article.content != null)
                isEqual &= article.content.equals(content);

        } else return false;

        return isEqual;
    }
}
