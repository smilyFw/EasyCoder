package mobile.module;

import mobile.MobileCfg;
import pc.Cfg;
import util.FileUtils;
import pc.easyModule.MethodObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenwen.fu on 2017/4/24.
 */
public class MobileModuleInit {
    private static Map<String, String> alternativeMap;

    public static void makeModuleDir(String projectId, String actId) {
        alternativeMap = new HashMap<String, String>();
        alternativeMap.put("ProjectId",  Cfg.getHeadUp(projectId));
        alternativeMap.put("actId", actId);
        alternativeMap.put("head", Cfg.getHead());
        analysisFolder();
    }

    private static String analysisFolder() {
        String daoFolder = "";
        ArrayList<File> files = FileUtils.getListFiles(MobileCfg.MODULE_TEMPLATE_URL);
        File desFolder = new File("./des");
        for (File f : files) {
            FileUtils.replaceCopy(f, desFolder, MethodObject.function(new Object() {
                /**
                 * 匹配替换
                 *
                 * @param str
                 * @return
                 */
                private String replaceString(String str) {
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
