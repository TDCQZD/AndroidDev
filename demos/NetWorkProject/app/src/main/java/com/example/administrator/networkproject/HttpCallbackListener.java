package com.example.administrator.networkproject;

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);

}