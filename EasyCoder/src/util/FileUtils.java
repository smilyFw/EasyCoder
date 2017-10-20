package util;

import pc.easyModule.MethodObject;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenwen.fu on 2016/1/1.
 */
public class FileUtils {


    /**
     * 获得所有的文件
     *
     * @param o
     * @return
     */
    public static ArrayList<File> getListFiles(Object o) {
        File directory = null;
        if (o instanceof File) {
            directory = (File) o;
        } else {
            directory = new File(o.toString());
        }

        ArrayList<File> files = new ArrayList<File>();

        if (directory.isFile()) {
            files.add(directory);

        } else if (directory.isDirectory()) {
            File[] directoryArr = directory.listFiles();
            for (File a : directoryArr) {
                files.add(a);
            }
        }

        return files;
    }

    /**
     * 将文件（夹）移动到令一个文件夹
     *
     * @param resFileUrl       源文件（夹）
     * @param objFolderFileUrl 目标文件夹
     * @throws IOException 异常时抛出
     */
    public static void move(String resFileUrl, String objFolderFileUrl) throws IOException {
        File resFile = new File(resFileUrl);
        copy(resFile, new File(objFolderFileUrl));
        delete(resFile);
    }

    /**
     * 复制文件（夹）到一个目标文件夹
     *
     * @param resFile 源文件（夹）
     * @param des     目标文件夹
     * @throws IOException 异常时抛出
     */
    public static void copy(File resFile, File des) {
        if (!resFile.exists()) {
            return;
        }
        if (!des.exists()) {
            des.mkdirs();
        }
        if (resFile.isFile()) {
            File objFile = new File(des.getPath() + File.separator + resFile.getName());
            try {
                //复制文件到目标地
                InputStream ins = new FileInputStream(resFile);
                FileOutputStream outs = new FileOutputStream(objFile);
                byte[] buffer = new byte[1024 * 512];
                int length;
                while ((length = ins.read(buffer)) != -1) {
                    outs.write(buffer, 0, length);
                }
                ins.close();
                outs.flush();
                outs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // File.separator 与系统有关的默认名称分隔符。此字段被初始化为包含系统属性 file.separator 值的第一个字符。
            // 在 UNIX 系统上，此字段的值为 '/'；在 Microsoft Windows 系统上，它为 '\\'。
            String objFolder = des.getPath() + File.separator + resFile.getName();
            new File(objFolder).mkdirs();

            for (File sf : resFile.listFiles()) {
                copy(sf, new File(objFolder));
            }
        }
    }






    /**
     * 复制文件（夹）到一个目标文件夹,并且替换路径中的关键字
     *
     * @param res 源文件（夹）
     * @param des 目标文件夹
     * @throws IOException 异常时抛出
     */
    public static void replaceCopy(File res, File des, MethodObject replaceFunc) {
        if (!res.exists()) {
            return;
        }
        if (!des.exists()) {
            des.mkdirs();
        }
        if (res.isFile()) {
            File objFile = new File((String) replaceFunc.invoke(des.getPath() + File.separator + res.getName()));
            try {
                BufferedReader reader = null;
                BufferedWriter writer = null;

                reader = new BufferedReader(new FileReader(res));
                writer = new BufferedWriter(new FileWriter(objFile));
                String tempStr = "";

                while ((tempStr = reader.readLine()) != null){
                    writer.write(replaceFunc.invoke(tempStr) + "\n");
                }
                reader.close();
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String objFolder = (String) replaceFunc.invoke(des.getPath() + File.separator + res.getName());
            new File(objFolder).mkdirs();

            for (File sf : res.listFiles()) {
                replaceCopy(sf, new File(objFolder), replaceFunc);
            }
        }
    }


    public static void replacePackage(File res, String newPackage){
        try {
            BufferedReader reader = null;
            BufferedWriter writer = null;

            reader = new BufferedReader(new FileReader(res));
            writer = new BufferedWriter(new FileWriter(res));
            boolean isFirst = true;
            //包名一般写在第一行，所以将第一行替换成新的后，即可退出
            String tempStr = null;
            while ((tempStr = reader.readLine()) != null){
                if(isFirst) {
                    writer.write(newPackage + "\n");
                    isFirst = false;
                }else{
                    writer.write(tempStr + "\n");
                }
            }
            reader.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除文件（夹）
     *
     * @param file 文件（夹）
     */
    public static void delete(File file) {
        if (!file.exists()) return;
        if (file.isFile()) {
            file.delete();
        } else {
            for (File f : file.listFiles()) {
                delete(f);
            }
            file.delete();
        }
    }


    /**
     * 读取文件内容
     * @param fileUrl
     * @return
     */
    public static String readContent(String fileUrl){
        String tempMethodStr = "";
        String tempStr = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileUrl));
            while ((tempStr = reader.readLine()) != null) {
                tempMethodStr = tempMethodStr.concat(tempStr + "\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  tempMethodStr;
    }


    public static void WriteFile(String fileUrl, String content){
        BufferedWriter  writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileUrl));
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void matchAndReplace(String mgrUrl, String key, String content){
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(mgrUrl));
            String mgrStr = "";
            String tempMgrStr = "";
            while ((tempMgrStr = reader1.readLine()) != null) {
                mgrStr = mgrStr.concat(tempMgrStr + "\n");
            }
            reader1.close();

            Pattern pattern1 = Pattern.compile("\\-\\{" + key + "\\}");
            Matcher matcher1 = pattern1.matcher(mgrStr); // 获取 matcher 对象
            mgrStr = matcher1.replaceAll(content);

            //将处理后的mgr文件内容再重新写入mgr中
            BufferedWriter writer = new BufferedWriter(new FileWriter(mgrUrl));
            writer.write(mgrStr);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkDirExistThenCreate(String dir){
        //写文件
        File des = new File(dir);
        if (!des.exists()) {
            des.mkdirs();
        }
    }
}
