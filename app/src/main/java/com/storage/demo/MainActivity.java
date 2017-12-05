package com.storage.demo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.os.Environment.DIRECTORY_MOVIES;

/**
 * 测试机是红米pro
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "SJY";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        getEnvironmentFile();

        //        getContextFile();

        //        getSpace();

//        getExternal();

//        Log.d(TAG, getExtendedMemoryPath(this));

    }

    private void getExternal() {
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(basePath, "AAAA/BBB");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "进入内置外部存储操作");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(TAG, "创建的路径：" + file.getAbsolutePath());
    }

    /**
     * 获取扩展sd卡路径
     *
     * @param mContext
     * @return
     */
    private static String getExtendedMemoryPath(Context mContext) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "没有扩展sd卡";
    }


    private void getSpace() {

        //经测试，参数不管用context还是Environment，结果没影响
        StatFs statFs = new StatFs(this.getExternalCacheDir().getPath());
        long blockSize = statFs.getBlockSizeLong();

        long availableBlocksLongs = statFs.getAvailableBlocksLong();
        long freeBlocksLong = statFs.getFreeBlocksLong();
        long BlocksLong = statFs.getBlockCountLong();

        //1.返回值：每一个存储块的大小尺寸: 4096
        Log.d(TAG, "每一个存储块的大小尺寸：" + blockSize);


        //2.返回值：该app外部存储可用的存储块总数：5148216
        Log.d(TAG, "该app外部存储可用的存储块总数：" + availableBlocksLongs);

        //3.返回值：剩余外部存储可用的存储块总数:5185080
        Log.d(TAG, "剩余外部存储可用的存储块总数：" + freeBlocksLong);

        //4.返回值： 外部存储总的存储块数量：14059344
        Log.d(TAG, "外部存储总的存储块数量" + BlocksLong);


        //5-1.返回值：外部存储应用程序可用空间=20110M
        Log.d(TAG, "外部存储app可用空间=" + statFs.getAvailableBytes() / 1024 / 1024 + "M");

        //5-2.返回值：外部存储应用程序可用空间=20110M
        Log.d(TAG, "外部存储app可用空间=" + blockSize * availableBlocksLongs / 1024 / 1024 + "M");

        //6-1.返回值： 外部存储系统可用空间20254M
        Log.d(TAG, "外部存储系统可用空间" + statFs.getFreeBytes() / 1024 / 1024 + "M");

        //6-2.返回值： 外部存储系统可用空间20254M
        Log.d(TAG, "外部存储系统可用空间" + blockSize * freeBlocksLong / 1024 / 1024 + "M");

        //7-1.返回值： 外部存储总空间53G
        Log.d(TAG, "外部存储总空间" + statFs.getTotalBytes() / 1024 / 1024 / 1024 + "G");

        //7-2.返回值： 外部存储总空间53G
        Log.d(TAG, "外部存储总空间" + blockSize * BlocksLong / 1024 / 1024 / 1024 + "G");


    }

    private void getContextFile() {
        try {

            //1.返回结果：/data/user/0/com.storage.demo/cache
            Log.d(TAG, "getCacheDir()=" + this.getCacheDir().toString());


            //2.返回结果：/data/user/0/com.storage.demo/code_cache
            Log.d(TAG, "getCodeCacheDir()=" + this.getCodeCacheDir().toString());

            //3.返回结果：没有支持的LEVEL24设备，就不写了
            //        Log.d(TAG, "getDataDir()=" + this.getDataDir().toString());

            //4.返回结果：/data/user/0/com.storage.demo/databases/sjy.db
            Log.d(TAG, "getDatabasePath()=" + this.getDatabasePath("sjy.db").toString());

            //5.返回结果：
            Log.d(TAG, "getDir()=" + this.getDir("sjy.db", MODE_PRIVATE).toString());

            //6.返回结果：/storage/emulated/0/Android/data/com.storage.demo/cache
            Log.d(TAG, "getExternalCacheDir()=" + this.getExternalCacheDir().toString());

            //7.返回结果：/storage/emulated/0/Android/data/com.storage.demo/cache
            File[] CacheDirs = this.getExternalCacheDirs();
            StringBuffer CacheDirsbuffer = new StringBuffer();
            for (int i = 0; i < CacheDirs.length; i++) {
                CacheDirsbuffer.append(CacheDirs[0].toString());
                CacheDirsbuffer.append("\n");
            }
            Log.d(TAG, "getExternalCacheDirs()=" + CacheDirsbuffer.toString());

            //8.返回结果： /storage/emulated/0/Android/data/com.storage.demo/files/DCIM
            Log.d(TAG, this.getExternalFilesDir(Environment.DIRECTORY_DCIM).toString());

            //9.返回结果：/storage/emulated/0/Android/data/com.storage.demo/files/Movies
            File[] FilesDirs = this.getExternalFilesDirs(DIRECTORY_MOVIES);
            StringBuffer FilesDirsbuffer = new StringBuffer();
            for (int i = 0; i < FilesDirs.length; i++) {
                FilesDirsbuffer.append(FilesDirs[0].toString());
                FilesDirsbuffer.append("\n");
            }
            Log.d(TAG, "getExternalFilesDirs()=" + FilesDirsbuffer.toString());


            //10.返回结果：/storage/emulated/0/Android/media/com.storage.demo
            File[] files = this.getExternalMediaDirs();
            StringBuffer MediaDirsbuffer = new StringBuffer();
            for (int i = 0; i < files.length; i++) {
                MediaDirsbuffer.append(files[0].toString());
                MediaDirsbuffer.append("\n");
            }
            Log.d(TAG, "getExternalMediaDirs()=" + MediaDirsbuffer.toString());

            //11.返回结果：/data/user/0/com.storage.demo/files/unknown.db
            Log.d(TAG, "getFileStreamPath()=" + this.getFileStreamPath("unknown.db").toString());

            //12.返回结果：/data/user/0/com.storage.demo/files
            Log.d(TAG, "getFilesDir()=" + this.getFilesDir().toString());

            //13.返回结果：/data/user/0/com.storage.demo/no_backup
            Log.d(TAG, "getNoBackupFilesDir()=" + this.getNoBackupFilesDir().toString());

            //14.返回结果：/storage/emulated/0/Android/obb/com.storage.demo
            Log.d(TAG, "getObbDir()=" + this.getObbDir().toString());

            //15.返回结果：/storage/emulated/0/Android/obb/com.storage.demo
            File[] ObbDirs = this.getObbDirs();
            StringBuffer ObbDirsbuffer = new StringBuffer();
            for (int i = 0; i < ObbDirs.length; i++) {
                ObbDirsbuffer.append(ObbDirs[0].toString());
                ObbDirsbuffer.append("\n");
            }
            Log.d(TAG, "getObbDirs()=" + ObbDirsbuffer.toString());


        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }
    }

    private void getEnvironmentFile() {

        //1.返回结果： /data
        Log.d(TAG, Environment.getDataDirectory().toString());

        //2.返回结果： /cache
        Log.d(TAG, Environment.getDownloadCacheDirectory().toString());

        //3.返回结果： /storage/emulated/0
        Log.d(TAG, Environment.getExternalStorageDirectory().toString());

        //4.返回结果：mounted
        Log.d(TAG, Environment.getExternalStorageState().toString());

        //5.返回结果:mounted
        Log.d(TAG, Environment.getExternalStorageState(new File(Environment.getExternalStorageDirectory(), "demo.png")).toString());

        //6.返回结果:system
        Log.d(TAG, Environment.getRootDirectory().toString());

        //7.返回结果:false
        Log.d(TAG, Environment.isExternalStorageEmulated() + "");

        //8.返回结果:false
        Log.d(TAG, Environment.isExternalStorageEmulated(new File(Environment.getExternalStorageDirectory(), "demo.png")) + "");

        //9.返回结果:false 表示是内置内存卡
        Log.d(TAG, Environment.isExternalStorageRemovable() + "");

        //10.返回结果:false 表示是内置内存卡
        Log.d(TAG, Environment.isExternalStorageRemovable(new File(Environment.getExternalStorageDirectory(), "demo.png")) + "");

        /**
         * 十大共有目录
         */
        //11-1.返回结果：storage/emulated/0/Music
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString());

        //11-2.返回结果：storage/emulated/0/Pictures
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());

        //11-3.返回结果：storage/emulated/0/Alarms
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).toString());

        //11-4.返回结果：storage/emulated/0/DCIM
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());

        //11-5.返回结果：storage/emulated/0/Documents
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString());

        //11-6.返回结果：storage/emulated/0/Download
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());

        //11-7.返回结果：storage/emulated/0/Movies
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).toString());

        //11-8.返回结果：storage/emulated/0/Notifications
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).toString());

        //11-9.返回结果：storage/emulated/0/Podcasts
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).toString());

        //11-10.返回结果：storage/emulated/0/Ringtones
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).toString());


    }
}
