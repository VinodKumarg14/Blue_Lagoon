package com.la.hotels.inroom.listeners;


public interface DownloadFileTaskListener {
    void onSuccess();

    void onFailure(String reason);
}
