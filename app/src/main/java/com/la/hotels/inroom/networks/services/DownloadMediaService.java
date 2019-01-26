package com.la.hotels.inroom.networks.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.la.hotels.inroom.constants.AppConstants;
import com.la.hotels.inroom.utils.FilesUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;

public class DownloadMediaService extends Service {

    private int TIMEOUT_CONNECT_MILLIS = (7 * 60 * 1000);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
//        android.os.Debug.waitForDebugger();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart(Intent intent, int startid) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        /**
         * Here Checking is any file pending to download if Yes--> checcking which type of file is ready to download (Images Or Video Or Default Media ) if nothing to download than i am sending a broadcast to update UI
         *
         * 1. Ads
         * 2. Deals
         * 3. Content Categories
         * 4. Content
         *
         */
        /*ContentDO contentDO = new AdvertismentDBL().getNextFileToDownload();
        if (contentDO != null) {
            downloadFile(contentDO);
        } else {
            contentDO = new DealsDL().getNextFileToDownload();
            if (contentDO != null) {
                downloadFile(contentDO);
            } else {
                    contentDO = new ContentDL().getNextFileToDownload();
                    if (contentDO != null) {
                        downloadFile(contentDO);
                    } else {
                        sendBroadCastSyncCompleted();
                    }
            }

        }*/
        return START_STICKY;
    }

    public synchronized void downloadFile(/*final ContentDO contentDO*/) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    if(new File(FilesUtils.SDCARD_PATH).exists() && AppConstants.MAX_LIMIT_STORAGE.compareTo(BigInteger.valueOf(FilesUtils.getExternalStorageUsedSpace())) < 0)
                        sendBroadCastSyncCompleted();
                    else {
                        File destinatioFile = null, destinatioStorageFile = null;

                       /*if (contentDO != null) {
                            *//**
                             * Checking File file type ,If file type is Image  than file destination path is -->
                             * "/.MyRyd/ImagesAdds/"
                             *//*
                            if (contentDO.getFileType().equalsIgnoreCase(AppConstants.FILE_TYPE_AD)) {
                                String extension = contentDO.getFileUrl().substring(contentDO.getFileUrl().lastIndexOf("."));
                                FilesUtils.createAdsFolderIfNotExist();
                                destinatioFile = new File(FilesUtils.getAdsFolderPath() + contentDO.getFileKey() + extension);
                                FilesUtils.createStorageAdsFolderIfNotExist();
                                destinatioStorageFile = new File(FilesUtils.getStorageAdsFolderPath() + contentDO.getFileKey() + extension);
                            }

                            *//**
                             * Checking File file type ,If file type is Video than file destination path is -->
                             * "/.MyRyd/Content/"
                             *//*
                            else if (contentDO.getFileType().equalsIgnoreCase(AppConstants.FILE_TYPE_CONTENT) || contentDO.getFileType().equalsIgnoreCase(AppConstants.FILE_TYPE_WEB_SERIES)) {
                                String extension = contentDO.getFileUrl().substring(contentDO.getFileUrl().lastIndexOf("."));
                                FilesUtils.createContentsFolderIfNotExist();
                                destinatioFile = new File(FilesUtils.getContentsFolderPath() + contentDO.getFileKey() + extension);
                                FilesUtils.createStorageContentsFolderIfNotExist();
                                destinatioStorageFile = new File(FilesUtils.getStorageContentsFolderPath() + contentDO.getFileKey() + extension);
                            }
                            *//**
                             * Checking File file type ,If file type is Video than file destination path is -->
                             * "/.MyRyd/Deals/"
                             *//*
                            else if (contentDO.getFileType().equalsIgnoreCase(AppConstants.FILE_TYPE_DEALS)) {
                                String extension = contentDO.getFileUrl().substring(contentDO.getFileUrl().lastIndexOf("."));
                                FilesUtils.createDealsFolderIfNotExist();
                                destinatioFile = new File(FilesUtils.getDealsFolderPath() + contentDO.getFileKey() + extension);
                                FilesUtils.createStorageDealsFolderIfNotExist();
                                destinatioStorageFile = new File(FilesUtils.getStorageDealsFolderPath() + contentDO.getFileKey() + extension);
                            }

                            *//**
                             * Checking File exist or not if exist not Downloading else Downloading.
                             *//*
                            if ((contentDO.getIsDownloaded() == 0)) {
                                if (!isFileExist(destinatioFile.getAbsolutePath()) && !isFileExist(destinatioStorageFile.getAbsolutePath())) {
                                    URL url = null;
                                    if (contentDO.getIsDownloaded() == 0)
                                        url = new URL(contentDO.getFileUrl());
                                    else
                                        url = new URL(contentDO.getPopUpUrl());

                                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                    int downloadedPart = 0;

                                    if (destinatioFile.exists()) {
                                        downloadedPart = (int) destinatioFile.length();
                                        urlConnection.setRequestProperty("Range", "bytes=" + downloadedPart + "-");
                                    } else if (destinatioStorageFile.exists()) {
                                        downloadedPart = (int) destinatioStorageFile.length();
                                        urlConnection.setRequestProperty("Range", "bytes=" + downloadedPart + "-");
                                    } else {
                                        urlConnection.setRequestProperty("Range", "bytes=" + downloadedPart + "-");
                                    }
                                    urlConnection.setConnectTimeout(TIMEOUT_CONNECT_MILLIS);
                                    urlConnection.setReadTimeout(TIMEOUT_CONNECT_MILLIS);
                                    urlConnection.connect();
                                    int download_statusCode = urlConnection.getResponseCode();

                                    BigInteger availableSpace = new BigInteger(FilesUtils.getInternalStorageSpaceAvailabilty()).subtract(AppConstants.MAX_AVAILABLE_FREESPACE);
                                    if (new File(FilesUtils.SDCARD_PATH).exists() && availableSpace.compareTo(BigInteger.valueOf(urlConnection.getContentLength())) < 0)// available space is lessthan downloaded file size
                                        destinatioFile = destinatioStorageFile;
                                    switch (download_statusCode) {
                                        case 200:
                                            //  Here i am updating ContentDO object means is that file can Downloaded or Not &  Able to download or not .
                                            //destinatioFile.delete();
                                            // save flags if it is  an add.

                                            FileOutputStream fileOutput = (downloadedPart == 0) ? new FileOutputStream(destinatioFile) : new FileOutputStream(destinatioFile, true);

                                            InputStream inputStream = urlConnection.getInputStream();
                                            long totalSize = urlConnection.getContentLength();
                                            long downloadedSize = 0;
                                            byte[] buffer = new byte[1024];
                                            int bufferLength = 0;
                                            while ((bufferLength = inputStream.read(buffer)) > 0) {
                                                fileOutput.write(buffer, 0, bufferLength);
                                                downloadedSize += bufferLength;
                                                Log.e("aaa", "downloadedSize : " + (downloadedSize * 100) / totalSize + "%");
                                            }
                                            boolean isExtracted = unzipFileUsingLib(destinatioFile.getAbsolutePath(), destinatioFile.getParent() + "/" + contentDO.getFileKey());
                                            if (isExtracted) {
                                                boolean isDeleted = deleteZipfile(destinatioFile.getAbsolutePath());
                                                if (isDeleted) {
                                                    contentDO.setIsDownloaded(1);
                                                    contentDO.setHasToDelete("0");
                                                    contentDO.setIsAbleToDownload(1);
                                                } else {
                                                    contentDO.setIsDownloaded(0);
                                                    contentDO.setHasToDelete("1");
                                                    contentDO.setIsAbleToDownload(0);
                                                }
                                            } else {
                                                contentDO.setIsDownloaded(0);
                                                contentDO.setHasToDelete("1");
                                                contentDO.setIsAbleToDownload(0);
                                            }
                                            fileOutput.close();

                                            break;

                                        case 206://Partially Downloaded
                                            //  Here i am updating ContentDO object means is that file can Downloaded or Not &  Able to download or not .
                                            //destinatioFile.delete();
                                            // save flags if it is  an ad.

                                            FileOutputStream fileOutput1 = (downloadedPart == 0) ? new FileOutputStream(destinatioFile) : new FileOutputStream(destinatioFile, true);

                                            InputStream inputStream1 = urlConnection.getInputStream();
                                            long totalSize1 = urlConnection.getContentLength();
                                            long downloadedSize1 = 0;
                                            byte[] buffer1 = new byte[1024];
                                            int bufferLength1 = 0;
                                            while ((bufferLength1 = inputStream1.read(buffer1)) > 0) {
                                                fileOutput1.write(buffer1, 0, bufferLength1);
                                                downloadedSize1 += bufferLength1;
                                                Log.e("aaa", "downloadedSize : " + (downloadedSize1 * 100) / totalSize1 + "%");
                                            }

                                            boolean isExtracted1 = unzipFileUsingLib(destinatioFile.getAbsolutePath(), destinatioFile.getParent() + "/" + contentDO.getFileKey());
                                            if (isExtracted1) {
                                                boolean isDeleted = deleteZipfile(destinatioFile.getAbsolutePath());
                                                if (isDeleted) {
                                                    contentDO.setIsDownloaded(1);
                                                    contentDO.setHasToDelete("0");
                                                    contentDO.setIsAbleToDownload(1);
                                                } else {
                                                    contentDO.setIsDownloaded(0);
                                                    contentDO.setHasToDelete("1");
                                                    contentDO.setIsAbleToDownload(0);
                                                }
                                            } else {
                                                contentDO.setIsDownloaded(0);
                                                contentDO.setHasToDelete("1");
                                                contentDO.setIsAbleToDownload(0);
                                            }

                                            fileOutput1.close();

                                            break;

                                        case 416:
                                            if (contentDO.getIsDownloaded() == 0) {
                                                contentDO.setIsDownloaded(1);
                                                contentDO.setHasToDelete("0");
                                                contentDO.setIsAbleToDownload(1);
                                            }

                                            break;
                                        default:
                                            *//*
                                             * save flags if it is  an add.
                                             *//*
                                            if (contentDO.getIsDownloaded() == 0) {
                                                contentDO.setIsDownloaded(0);
                                                contentDO.setHasToDelete("1");
                                                contentDO.setIsAbleToDownload(0);
                                            }
                                            break;
                                    }
                                } else {
                                    contentDO.setIsDownloaded(1);
                                    contentDO.setHasToDelete("0");
                                    contentDO.setIsAbleToDownload(1);
                                }
                            } else {
                                *//**
                                 * Here i am updating ContentDO object means is that file can Downloaded or Not &  Able to download or not .
                                 *//*
                                if (contentDO.getIsDownloaded() == 0) {
                                    contentDO.setIsDownloaded(1);
                                    contentDO.setHasToDelete("0");
                                    contentDO.setIsAbleToDownload(1);
                                }
                            }
                        }*/
                        throw new Exception();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    sendBroadCastToFinishREquest();
                    this.cancel(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    sendBroadCastToFinishREquest();
                    this.cancel(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    sendBroadCastToFinishREquest();
                }
                return null;

            }

            protected void onPostExecute(Integer result) {
//                updateFileInfo(contentDO);

                /**
                 * Checking here is there any image to download if Yes -> Fetching file info from DB and passwing here to download .
                 *
                 * 1. Ads
                 * 2. Deals
                 * 3. Content Categories
                 * 4. Content
                 *
                 */

                /*ContentDO contentDO = new AdvertismentDBL().getNextFileToDownload();
                if (contentDO != null) {
                    downloadFile(contentDO,null);
                } else {
                    contentDO = new DealsDL().getNextFileToDownload();
                    if (contentDO != null) {
                        downloadFile(contentDO,null);
                    } else {
                        if (categoryJsonObject != null) {
                            try {
                                JSONObject categoryObject = categoryJsonObject.getJSONObject("nameValuePairs");
                                downloadFile(null,categoryObject);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            contentDO = new ContentDL().getNextFileToDownload();
                            if (contentDO != null) {
                                downloadFile(contentDO,null);
                            } else {
                                sendBroadCastSyncCompleted();
                            }
                        }
                    }
                }*/

            }
        }.execute();
    }

    private void sendBroadCastToFinishREquest() {
        Intent intent = new Intent(AppConstants.ACTION_INTERNET_ISSUE);
        sendBroadcast(intent);
    }
    private void sendBroadCastSyncCompleted() {
        Intent syncingIntet = new Intent(AppConstants.ACTION_SYNC_COMPLETED);
        sendBroadcast(syncingIntet);
    }


    /**
     * Updating File info based on FileType and  isDefaultMedia boolean value.
     *
     * @param contentDO
     */
   /* private void updateFileInfo(ContentDO contentDO) {

        if(contentDO != null) {
            *//**
             * If it is Ad than Update File info in "tblAdvertisments"  Table.
             *//*
            if (contentDO.getFileType().equalsIgnoreCase(AppConstants.FILE_TYPE_AD)) {
                new AdvertismentDBL().insertSingleRecord(contentDO);
            } else if (contentDO.getFileType().equalsIgnoreCase(AppConstants.FILE_TYPE_DEALS)) {
                new DealsDL().insertSingleContent(contentDO);
            }

            *//**
             * If it is Content than Update File info in "tblContent"  Table.
             *//*
            else if (contentDO.getFileType().equalsIgnoreCase(AppConstants.FILE_TYPE_CONTENT) || contentDO.getFileType().equalsIgnoreCase(AppConstants.FILE_TYPE_WEB_SERIES)) {
                new ContentDL().insertSingleContent(contentDO);
            }
        }
    }*/

//    private boolean unzipFileUsingLib(String source, String destination) {
//        try {
//            ZipFile zipFile = new ZipFile(source);
//            zipFile.extractAll(destination);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public boolean deleteZipfile(String filepath) {
        try {
            File file = new File(filepath);
            return file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isFileExist(String filePath) {
        if (filePath.indexOf(".") > 0)
            filePath = filePath.substring(0, filePath.lastIndexOf("."));
        return new File(filePath).exists();
    }

}
