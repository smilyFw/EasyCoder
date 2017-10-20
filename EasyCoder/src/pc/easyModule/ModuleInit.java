package pc.easyModule;

import pc.Cfg;
import util.FileUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenwen.fu on 2015/12/4.
 */
public class ModuleInit {

    private static Map<String, String> alternativeMap;

    public static void makeModuleDir(String projectId) {
        String projectIdHeadUp = Cfg.getHeadUp(projectId);
        String projectIdAllLow = projectId.toLowerCase();
        alternativeMap = new HashMap<String, String>();
        alternativeMap.put("projectid", projectIdAllLow);
        alternativeMap.put("projectId", projectId);
        alternativeMap.put("ProjectId", projectIdHeadUp);
        alternativeMap.put("head", Cfg.getHead());
        analysisFolder();
    }

    private static String analysisFolder() {
        String daoFolder = "";
        ArrayList<File> files = FileUtils.getListFiles(Cfg.MODULE_TEMPLATE_URL);
        File desFolder = new File("./des");
        for (File f : files) {
            FileUtils.replaceCopy(f, desFolder,MethodObject.function(new Object() {
                /**
                 * 匹配替换
                 *
                 * @param str
                 * @return
                 */
                private  String replaceString(String str) {
                    if (str != null) {
                        for (String s : alternativeMap.keySet()) {
                            Pattern pattern = Pattern.compile("\\-\\{" + s + "\\}");
                            Matcher matcher = pattern.matcher(str); // 获取 matcher 对象
                            str = matcher.replaceAll(alternativeMap.get(s).toString());
                        }
                        return str;
                    } else {
                        return "";
                    }
                }
            }));
        }
        return daoFolder;
    }
}
