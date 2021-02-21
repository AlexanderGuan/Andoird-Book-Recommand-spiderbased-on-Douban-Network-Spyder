package com.example.testact.http;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;


public class IOUtils {

    //日志标记
    private static final String TAG = IOUtils.class.getSimpleName();
    //Bitmap转成的文件的文件夹
    private static String BITMAP_TO_FILE_FOLDER = "BitmapCache";
    //压缩Bitmap路径
    public static String COMPRESS_BITMAP_FOLDER = "Compressor";
    //压缩Bitmap路径
    public static String WRITE_FOLDER = "Downloader";
    //文件工具
    public static final String COMPRESS_BITMAP_PATH = getSDCardPath() + File.separator + COMPRESS_BITMAP_FOLDER;
    //要压缩的宽度
    public final static int COMPRESS_WIDTH = 480;
    //要压缩的高度
    public final static int COMPRESS_HEIGHT = 800;
    //压缩质量
    public static int COMPRESS_QUALITY = 80;

    /**
     * 判断内存卡是否存在
     *
     * @return
     */
    public static boolean isExistSDCard() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

    /**
     * 获取内存卡的路径
     *
     * @return
     */
    public static String getSDCardPath() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Documents");
        if (!file.exists()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return file.getParent();
    }

    /**
     * 清除创建的文件夹
     */
    public static void clear() {
        String folders[] = {BITMAP_TO_FILE_FOLDER, COMPRESS_BITMAP_FOLDER, WRITE_FOLDER};
        for (int i = 0; i < folders.length; i++) {
            File folder = new File(getSDCardPath() + File.separator + folders[i]);
            deleteDir(folder);
        }
    }

    /**
     * 创建文件
     * create file
     *
     * @param folderName folder name
     * @param fileName   file name
     * @return
     */
    public static File createFile(String folderName, String fileName) {
        if (fileName == null) {
            Log.i(TAG, "Please check you file name.");
            return null;
        }
        createFolder(folderName);
        File file = new File(getSDCardPath() + File.separator + folderName + File.separator + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 创建新文件夹
     *
     * @return
     */
    public static String createFolder(String folderName) {
        File folder = new File(getSDCardPath() + File.separator + folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder.getAbsolutePath();
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 计算文件夹大小
     *
     * @param file 文件夹
     * @return
     */
    public static double calculateDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += calculateDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“Kb”为单位
                Log.i(TAG, " calculateDirSize " + file.length() + "KB");
                double size = (double) file.length() / 1024;
                return size;
            }
        } else {
            Log.i(TAG, "The file or folder does not exist, please check the path is correct!");
            return 0.0;
        }
    }

    /**
     * 打开文件夹
     *
     * @param context
     * @param path    文件夹路径
     */
    public static void openFolder(Context context, String path) {
        openFolder(context, path, MIME.FOLDER);
    }

    /**
     * 打开文件夹
     *
     * @param context
     * @param path    文件夹路径
     * @param mime    文件类型
     */
    public static void openFolder(Context context, String path, String mime) {
        File file = new File(path);
        if (null == file || !file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), mime);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开文件
     *
     * @param context
     * @param path    文件路径
     * @param mime    文件类型
     */
    public static void openFile(Context context, String path, String mime) {
        File file = new File(path);
        if (null == file || !file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), mime);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建Url文件名称
     *
     * @param url 资源地址
     * @return
     */
    public static String createName(String url) {
        if (url.contains("/") && url.contains(".")) {
            return url.substring(url.lastIndexOf("/") + 1);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return format.format(format) + ".zip";
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     */
    public static String readFile(File file) {
        if (file == null) {
            return "The read file is empty and cannot be read.";
        }
        StringBuilder sb = new StringBuilder("");//定义一个字符串缓存，将字符串存放缓存中
        FileReader reader = null;//定义一个fileReader对象，用来初始化BufferedReader
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
            String content;
            while ((content = bufferedReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                sb.append(content + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 写出文件
     *
     * @param fileName 文件名称，例如：xxx.java
     * @param content  内容
     */
    public static void writeFile(String fileName, String content) {
        writeFile(WRITE_FOLDER, fileName, content);
    }

    /**
     * 写入文件
     *
     * @param folderName 文件夹名字
     * @param fileName   文件名字
     * @param content    文件内容
     */
    public static void writeFile(String folderName, String fileName, String content) {
        File fileDir = new File(getSDCardPath() + File.separator + folderName + File.separator);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File classFile = new File(fileDir.getAbsolutePath(), fileName);
        if (!classFile.exists()) {
            try {
                classFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(classFile));
            pw.print(content);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Assets文件内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 读取文件数据
     *
     * @return
     */
    public static StringBuffer read(String path) {
        StringBuffer stringBuilder = new StringBuffer();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    /**
     * InputStream转Bitmap
     *
     * @param inputStream
     * @return
     */
    public static Bitmap decodeStream(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    /**
     * Bitmap转文件
     *
     * @param bitmap
     * @return
     */
    public static File decodeBitmap(Bitmap bitmap) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
        String fileName = "IMG_" + format.format(new Date()) + ".jpg";
        Log.i(TAG, "bitmapToFile fileName: " + fileName);
        File file = createFile(BITMAP_TO_FILE_FOLDER, fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Bitmap转文件
     *
     * @param bitmap
     * @return
     */
    public static File decodeBitmap(Bitmap bitmap, String fileName) {
        File file = createFile(BITMAP_TO_FILE_FOLDER, fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 文件转Bitmap
     *
     * @param path 文件路径
     * @return
     */
    public static Bitmap decodeFile(String path) {
        if (path == null) {
            Log.e(TAG, " decodeFile path is not exists!");
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            Log.e(TAG, " decodeFile file is not exists!");
            return null;
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    /**
     * 将文件流转成文件
     *
     * @param inputStream 输入流
     * @param path        文件路径
     * @return
     */
    public static File decodeInputStream(InputStream inputStream, String path) {
        File file = new File(path);//文件夹
        if (file.getParentFile().isDirectory() && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入文件操作流程中
        int len;
        byte[] buffer = new byte[2048];
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 文件转字符串
     *
     * @param file
     * @return
     */
    public static String encodeBase64(File file) {
        return encodeBase64(file, false);
    }

    /**
     * 文件转字符串
     *
     * @param file
     * @return
     */
    public static String encodeBase64(File file, boolean isURLEncoder) {
        byte[] bytes = decodeFile(file);
        String stringFile = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
//        stringFile = stringFile.replaceAll("[\\s*\t\n\r]", "");
        if (isURLEncoder) {
            try {
                stringFile = URLEncoder.encode(stringFile, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return stringFile;
    }

    /**
     * Base64转File
     *
     * @param base64
     * @param path
     */
    public static File decodeBase64(String base64, String path) {
        return decodeBase64(base64, path, false);
    }

    /**
     * Base64转File
     *
     * @param base64
     * @param path
     */
    public static File decodeBase64(String base64, String path, boolean isURLDecoder) {
        File file = new File(path);//文件夹
        if (file.getParentFile().isDirectory() && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            byte[] buffer = Base64.decode(isURLDecoder ? URLDecoder.decode(base64, "UTF-8") : base64, Base64.CRLF);
            FileOutputStream out = new FileOutputStream(file);
            out.write(buffer);
            out.close();
        } catch (IOException e) {
            Log.e(TAG, TAG + " base64StringToFile() Exception:" + e.toString());
        }
        return file;
    }

    /**
     * 图片转Base64String
     *
     * @param bitmap
     * @return
     */
    public static String encodeBase64(Bitmap bitmap) {
        return encodeBase64(bitmap, false);
    }

    /**
     * 图片转Base64String
     *
     * @param bitmap
     * @return
     */
    public static String encodeBase64(Bitmap bitmap, boolean isURLEncoder) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 压缩图片并写入数据流中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        // Base64 编码 ，方便网络传输
        String stringBitmap = new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
//        stringBitmap = stringBitmap.replaceAll("[\\s*\t\n\r]", "");
        // 加密（不加密数据库显示不出来）
        if (isURLEncoder) {
            try {
                stringBitmap = URLEncoder.encode(stringBitmap, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBitmap;
    }


    /**
     * Base64转Bitmap
     *
     * @param base64
     * @return
     */
    public static Bitmap decodeBase64(String base64) {
        return decodeBase64(base64, false);
    }

    /**
     * Base64转Bitmap
     *
     * @param base64
     * @return
     */
    public static Bitmap decodeBase64(String base64, boolean isURLDecoder) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(isURLDecoder ? URLDecoder.decode(base64, "UTF-8") : base64, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * File转Bytes
     *
     * @param file
     * @return
     */
    public static byte[] decodeFile(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 压缩图片成文件
     *
     * @param file           压缩文件
     * @param format         压缩格式
     * @param compressedSize 压缩大小
     * @return
     */
    public static File compress(File file, Bitmap.CompressFormat format, int compressedSize) {
        long time = System.currentTimeMillis();
        createFolder(COMPRESS_BITMAP_FOLDER);
        File targetFile = new File(COMPRESS_BITMAP_PATH + File.separator + file.getName());
        if (targetFile.exists()) {
            Log.i(TAG, "compress bitmap is compressed!");
            return targetFile;
        }
        Log.i(TAG, "compress bitmap before file length :" + (file.length() / 1024) + "kb");
        File resultFile = compress(file.getAbsolutePath(), targetFile.getAbsolutePath(), COMPRESS_QUALITY, format, COMPRESS_WIDTH, COMPRESS_HEIGHT);
        while ((resultFile.length() / 1024) > compressedSize) {//压缩到100kb以下
            COMPRESS_QUALITY -= 5;
            resultFile = compress(resultFile.getAbsolutePath(), targetFile.getAbsolutePath(), COMPRESS_QUALITY, format, COMPRESS_WIDTH, COMPRESS_HEIGHT);
        }
        Log.i(TAG, "compress bitmap use time: " + (System.currentTimeMillis() - time) + "ms (" + ((System.currentTimeMillis() - time) / 1000) + "s) , compress after file length " + (resultFile.length() / 1024) + "kb");
        return resultFile;
    }

    /**
     * 压缩图片
     *
     * @param sourcePath     源文件路径
     * @param targetPath     目标路径
     * @param quality        压缩质量
     * @param compressWidth  压缩宽度
     * @param compressHeight 压缩高度
     * @return
     */
    public static File compress(String sourcePath, String targetPath, int quality, Bitmap.CompressFormat format, int compressWidth, int compressHeight) {
        Bitmap bm = inSampleSize(sourcePath, compressWidth, compressHeight);//获取一定尺寸的图片
        int degree = calculateExifRotateAngle(sourcePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = rotateBitmap(bm, degree);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(format, quality, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap inSampleSize(String filePath, int compressWidth, int compressHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高 960
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, compressWidth, compressHeight);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap inSampleSize(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高 960
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, COMPRESS_WIDTH, COMPRESS_HEIGHT);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片旋转角度
     *
     * @param path 图片路径
     * @return
     */
    public static int calculateExifRotateAngle(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     *
     * @param bitmap
     * @param degree
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
