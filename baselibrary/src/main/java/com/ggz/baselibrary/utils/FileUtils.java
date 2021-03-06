package com.ggz.baselibrary.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 文件工具类
 * Created by fangs on 2017/3/22.
 */
public class FileUtils {

    /** 应用 所有 文件 根目录 */
    public static String SAVE_FOLDER = "HJY";

    /** 文件下载目录 */
    public static String DOWN = "down";

    private FileUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 到得文件的放置路径
     * 如果文件目录不存在 会创建
     * @param aModuleName 模块名字 (如："head.img.temp")
     * @return
     * @author fangs
     */
    public static String getPath(String aModuleName) {
        String sp = File.separator;
        String modulePath = aModuleName.replace(".", sp);
        String fDirStr = sp + modulePath + sp;

        File dirpath;

        if (isSDCardEnable()) {
            dirpath = new File(getSDCardPath() + SAVE_FOLDER, fDirStr);
        } else {
            dirpath = Environment.getDataDirectory();
        }

        if (!dirpath.exists() && !dirpath.isDirectory()) {
            dirpath.mkdirs();
        }
        return dirpath.getPath();
    }

    /**
     * 判断指定路径的 文件夹 是否存在，不存在创建文件夹
     *
     * @param filePath
     * @return
     */
    public static File folderIsExists(String filePath) {
        File folder = new File(filePath);
        try {
            if (!folder.exists() || !folder.isDirectory()) {
                folder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return folder;
    }

    /**
     * 判断指定路径的 文件 是否存在，不存在创建文件
     * @param filePath
     * @return
     */
    public static File fileIsExists(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists() || !file.isFile()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    public static void recursionDeleteFile(File file){

        if (!file.exists()) {
            return;
        }

        if(file.isFile()){
            deleteFileSafely(file);//删除当前 文件
            return;
        }

        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(null == childFile || childFile.length == 0){
                deleteFileSafely(file);//删除 当前目录
                return;
            }

            for(File f : childFile){
                recursionDeleteFile(f);
            }

            deleteFileSafely(file);//删除 当前目录
        }
    }


    /**
     * 安全删除文件
     * @param file
     * @return
     */
    public static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    public static String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            LogUtils.d("file", "paramString---->null");
            return str;
        }
        LogUtils.d("file","paramString:"+paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            LogUtils.d("file","i <= -1");
            return str;
        }


        str = paramString.substring(i + 1);
        LogUtils.d("file","paramString.substring(i + 1)------>"+str);
        return str;
    }

    /**
     * 向指定文件写内容  (追加形式写文件)
     * @param path          文件目录(如：fy.com.base)
     * @param inputfileName 文件名（如：log.txt）
     * @param content       准备写入的内容
     */
    public static void fileToInputContent(String path, String inputfileName, String content) {
        StringBuffer sb = new StringBuffer();
        sb.append("\n").append(content).append("\n");

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File dir = new File(getPath(path));
            if (!dir.exists()) {
                dir.mkdir();
            }

            try {
                // 文件目录 + 文件名 String
                String fileName = dir.toString() + File.separator + inputfileName;
                // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                FileWriter writer = new FileWriter(fileName, true);
                writer.write(sb.toString());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取应用 图片保存目录
     * @return
     */
    public static String getImgPath(){
        String takeImageFilePath;

        if (FileUtils.isSDCardEnable()) {
            takeImageFilePath = FileUtils.getSDCardPath() + "pictures/";
        } else {
            takeImageFilePath = Environment.getDataDirectory().getPath() + "/pictures/";
        }

        return takeImageFilePath;
    }


    /**
     * 根据 url 在本地生成一个文件
     *
     * @param url 下载 url【如：http://img5q.duitang.com/uploads/item/201505/01/20150501113308_QNmsf.jpeg】
     * @return
     */
    public static File createFile(String url) {
        String fileName;

        if (url.indexOf("?") == -1){
            fileName = url.substring(url.lastIndexOf("/"));
        } else {
            fileName = url.subSequence(url.lastIndexOf("/"), url.indexOf("?")).toString();
        }

        File file = new File(folderIsExists(DOWN), fileName);

        return FileUtils.fileIsExists(file.getPath());
    }

    /**
     * 根据系统时间、前缀、后缀产生一个文件
     * @param folder    目标文件所在的 文件夹（目录）
     * @param prefix    目标文件的 前缀 (如：IMG_)
     * @param suffix    目标文件的 后缀名（如：.jpg）
     * @return
     */
    public static String createFile(String folder, String prefix, String suffix) {

        folderIsExists(folder);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String filename = prefix + dateFormat.format(new Date(System.currentTimeMillis())) + suffix;

        return new File(folder, filename).getPath();
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return StringUtils.isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
