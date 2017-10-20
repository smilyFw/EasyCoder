package pc;

import java.util.Date;

/**
 * Created by wenwen.fu on 2016/1/1.
 */
public class Cfg {
    public static final String PACKAGE_NAME = "com.twofish.fishbowl.activity";

    public static final String DAO_INTERFACE_END = "Dao";

    public static final String DAO_END = "DaoDb";

    public static final String MODULE_TEMPLATE_URL = "./templete/module/";
    public static final String METHOD_TEMPLATE_URL = "./templete/method/";
    public static final String META_URL = "./src/pc/meta.xml";
    public static final String METHOD_URL ="./src/pc/method.xml";
    public static final String SQL_URL = "./src/pc/sql";
    public static final String VO_URL = "./src/pc/VO";

    /**
     * 作者注释信息
     *
     * @return
     */
    public static String getHead() {
        Date date = new Date();
        String authorNameEN = "wenwen.fu";
        String authorNameCN = "付文文";
        String authorMail = "wenwen.fu@happyelements.com";
        String headStr = "/**\r\n" +
                " * Copyright ©2009-" + (date.getYear() + 1900) + "www.happyelements.com, all rights reserved.\r\n" +
                " * Create date: " + (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " \r\n" +
                " * " + authorNameEN + "\r\n" +
                " * " + authorNameCN + "\r\n" +
                " * " + authorMail + "\r\n" +
                " */\r\n";
        return headStr;
    }


    public static String getHeadUp(String s){
        return s.substring(0,1).toUpperCase() + s.substring(1, s.length());
    }

    public static String getConst(String s){
        char[] chars = s.toCharArray();
        StringBuffer sb = new StringBuffer("");
        char c;
        for (int i= 0; i< chars.length; i++){
             c = chars[i];
             if(c>=65 && c <= 90){//大写
               sb.append("_");
             }
          sb.append((c+"").toUpperCase());
        }
        return sb.toString();

    }
}
