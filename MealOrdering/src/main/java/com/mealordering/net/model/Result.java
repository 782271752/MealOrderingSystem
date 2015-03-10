package com.mealordering.net.model;

import com.google.api.client.util.Key;

import java.io.Serializable;

/**
 * Created by Anthonit on 2014/4/21.
 */
public class Result<T> implements Serializable {
    @Key("Result")
    protected int result;
    @Key("Data")
    protected T data;

    public boolean isSuccess() {
        return result == 1;
    }

    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "读取失败!";
                break;
            case 1:
                message = "";
                break;
        }
        return message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                ", data=" + data +
                '}';
    }
}
