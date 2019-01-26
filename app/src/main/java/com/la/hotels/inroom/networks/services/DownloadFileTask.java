package com.la.hotels.inroom.networks.services;

import android.content.Context;
import android.os.AsyncTask;

import com.la.hotels.inroom.listeners.DownloadFileTaskListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFileTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private int TIMEOUT_CONNECT_MILLIS = (60 * 1000 * 5);
    private int TIMEOUT_READ_MILLIS = TIMEOUT_CONNECT_MILLIS - 5000;
    private String fileUrl;
    private File destinationFile;
    private boolean isFileDownloaded = false;
    private DownloadFileTaskListener downloadFileTaskListener;

    public DownloadFileTask(Context context, String fileUrl, File destinationFile, DownloadFileTaskListener downloadFileTaskListener) {
        this.context = context;
        this.destinationFile = destinationFile;
        this.fileUrl = fileUrl;
        this.downloadFileTaskListener = downloadFileTaskListener;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT_READ_MILLIS);
            urlConnection.setReadTimeout(TIMEOUT_READ_MILLIS);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            int download_statusCode = urlConnection.getResponseCode();
            switch (download_statusCode) {
                case 200:
                    destinationFile.delete();
                    FileOutputStream fileOutput = new FileOutputStream(destinationFile);
                    InputStream inputStream = urlConnection.getInputStream();
                    long totalSize = urlConnection.getContentLength();
                    long downloadedSize = 0;
                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;
                    while ((bufferLength = inputStream.read(buffer, 0, buffer.length)) >= 0) {
//                        Log.e("Downloaded",""+(downloadedSize/totalSize));
                        fileOutput.write(buffer, 0, bufferLength);
                        downloadedSize += bufferLength;
                    }
                    fileOutput.close();
                    isFileDownloaded = true;
                    break;
                default:
                    downloadFileTaskListener.onFailure("DOWNLOAD FAILED ERROR CODE :  " + download_statusCode+", url -"+fileUrl);
                    break;
            }
        } catch (FileNotFoundException e) {
            isFileDownloaded = false;
            downloadFileTaskListener.onFailure("Error: " + "Destination file not exist");
            e.printStackTrace();
        } catch (IOException e) {
            isFileDownloaded = false;
            downloadFileTaskListener.onFailure("Error: " + "Network Error");
            e.printStackTrace();
        } catch (Exception e) {
            isFileDownloaded = false;
            downloadFileTaskListener.onFailure("Error: " + e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException e) {
                isFileDownloaded = false;
                downloadFileTaskListener.onFailure("Error: " + e.toString());
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (isFileDownloaded)
            downloadFileTaskListener.onSuccess();
        else
            downloadFileTaskListener.onFailure("Some error occurred");
    }
}