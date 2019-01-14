package com.mohamed14riad.rssnewsreader.data.network;

import com.mohamed14riad.rssnewsreader.data.models.Feed;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {
    @GET
    Observable<Feed> getNews(@Url String url);
}
