package com.mealordering.net;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.mealordering.utils.L;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

public class SpiceGetRequest<RESULT> extends
        GoogleHttpClientSpiceRequest<RESULT> {
    private GenericUrl mUrl;
    private static JsonObjectParser mJsonObjectParser;

    public SpiceGetRequest(GenericUrl url, Class<RESULT> clazz) {
        super(clazz);
        mUrl = url;
        mJsonObjectParser = new JacksonFactory().createJsonObjectParser();
    }

    @Override
    public RESULT loadDataFromNetwork() throws Exception {
        L.d("Call request  " + mUrl.toString());

        HttpRequest request = getHttpRequestFactory().buildGetRequest(mUrl);
        request.setParser(mJsonObjectParser);
        return request.execute().parseAs(getResultType());
    }

}
