package com.mealordering.net;

import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.common.collect.Maps;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import java.util.Map;

import roboguice.util.temp.Ln;


public class SpicePostRequest<RESULT> extends
        GoogleHttpClientSpiceRequest<RESULT> {
    private GenericUrl mUrl;
    private HttpContent mHttpContent;
    private static JsonObjectParser mJsonObjectParser = new JacksonFactory().createJsonObjectParser();


    public SpicePostRequest(GenericUrl url, Class<RESULT> clazz) {
        this(url, url != null ? url.getUnknownKeys() : (Map) null, clazz);
    }

    public SpicePostRequest(GenericUrl url, Map<? extends String, ?> params,
                            Class<RESULT> clazz) {
        this(url, new UrlEncodedContent(params), clazz);
    }

    public SpicePostRequest(GenericUrl url, HttpContent content, Class<RESULT> clazz) {
        super(clazz);
        url.setUnknownKeys(Maps.<String, Object>newHashMap());
        mUrl = url;
        mHttpContent = content == null ? new EmptyContent() : content;
    }

    @Override
    public RESULT loadDataFromNetwork() throws Exception {
        Ln.d("Call request " + mUrl.toString());
        HttpRequest request = getHttpRequestFactory().buildPostRequest(mUrl,
                mHttpContent);
        request.setParser(mJsonObjectParser);

        return request.execute().parseAs(getResultType());
    }
}
