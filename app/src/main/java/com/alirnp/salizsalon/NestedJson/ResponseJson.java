package com.alirnp.salizsalon.NestedJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseJson {

    @SerializedName("result")
    @Expose
    private List<ResultItems> result = null;

    public List<ResultItems> getResult() {
        return result;
    }

    public void setResult(List<ResultItems> result) {
        this.result = result;
    }

}