package com.mealordering.net;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.SpiceRequest;

public class CachedRequestHelper {
    public static <T> CachedSpiceRequest<T> buildNoCache(
            SpiceRequest<T> spiceRequest, Object requestCacheKey) {
        return buildCachedDuration(spiceRequest, requestCacheKey, DurationInMillis.ALWAYS_EXPIRED);
    }

    public static <T> CachedSpiceRequest<T> buildCacheAlways(
            SpiceRequest<T> spiceRequest, Object requestCacheKey) {
        return buildCachedDuration(spiceRequest, requestCacheKey, DurationInMillis.ALWAYS_RETURNED);
    }

    public static <T> CachedSpiceRequest<T> buildCacheOneMinute(
            SpiceRequest<T> spiceRequest, Object requestCacheKey) {
        return buildCachedDuration(spiceRequest, requestCacheKey, DurationInMillis.ONE_MINUTE);
    }

    public static <T> CachedSpiceRequest<T> buildCacheOneHour(
            SpiceRequest<T> spiceRequest, Object requestCacheKey) {
        return buildCachedDuration(spiceRequest, requestCacheKey, DurationInMillis.ONE_HOUR);
    }

    public static <T> CachedSpiceRequest<T> buildCacheOneDay(
            SpiceRequest<T> spiceRequest, Object requestCacheKey) {
        return buildCachedDuration(spiceRequest, requestCacheKey, DurationInMillis.ONE_DAY);
    }

    public static <T> CachedSpiceRequest<T> buildCacheOneWeek(
            SpiceRequest<T> spiceRequest, Object requestCacheKey) {
        return buildCachedDuration(spiceRequest, requestCacheKey, DurationInMillis.ONE_WEEK);
    }

    public static <T> CachedSpiceRequest<T> buildCachedDuration(SpiceRequest<T> spiceRequest, Object requestCacheKey, long cacheDuration) {
        return new CachedSpiceRequest<T>(spiceRequest, requestCacheKey,
                cacheDuration);
    }
}
