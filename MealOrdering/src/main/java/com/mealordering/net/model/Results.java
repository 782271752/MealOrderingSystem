package com.mealordering.net.model;

import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Anthonit on 2014/4/21.
 */
public class Results<T> implements Serializable {
    @Key("Result")
    protected int result;
    @Key("Data")
    protected List<T> data;

    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "";
                break;
            case 1:
                message = "";
                break;
        }
        return message;
    }

    public boolean isSuccess() {
        return result == 1;
    }

    public boolean isDefrind() {return result == 2;}

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Results{" +
                "result=" + result +
                ", data=" + data +
                '}';
    }
}
