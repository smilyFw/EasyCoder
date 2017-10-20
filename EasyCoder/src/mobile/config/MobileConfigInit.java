package mobile.config;

import com.sun.xml.internal.ws.api.model.MEP;
import mobile.MobileCfg;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pc.Cfg;
import pc.easyMethod.MethodVO;
import util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenwen.fu on 2017/4/24.
 */
public class MobileConfigInit {
    public static void auto(String projectId, String actId) {
        parseXML(MobileCfg.META_XML_URL, projectId, actId);
        autoWiki();
    }

    private static void autoFront(List<MethodVO> methodVOList) {
        for (MethodVO methodVO : methodVOList) {

        }
    }

    private static void autoWiki() {
        System.out.println("h3.配置\n{code}");
        System.out.println(FileUtils.readContent(MobileCfg.META_XML_URL));
        System.out.println("{code}");
    }


    public static void parseXML(String methodXmlUrl, String projectId, String actId) {

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(methodXmlUrl));

            Element root = document.getRootElement();
            List<Element> methods = root.elements();

            StringBuilder paramSb = new StringBuilder("");
            StringBuilder putCacheSb = new StringBuilder("");
            StringBuilder getMethodSb = new StringBuilder("");
            StringBuilder config = new StringBuilder("");

            for (Element method : methods) {
                if (method.getName().equals("lua") || method.getName().equals("dcId") || method.getName().equals("reserve")) {
                    continue;
                }
                if (method.content().size() > 1) {
                    paramSb.append("    @ElementList(entry = \"item\")\n    private List<Object> " + method.getName() + ";\n");
                } else {
                    paramSb.append("    @Element\n    private int " + method.getName() + ";\n");

                }
                putCacheSb.append("        cache.put(\"" + method.getName() + "\", " + method.getName() + ");\n");

                config.append("self.CFG_"
                        + Cfg.getConst(method.getName()) + " = d." + method.getName()+"\n");
            }

            String mgrUrl = "./des/act" + actId + "/config/" + Cfg.getHeadUp(projectId) + "Config.java";
            FileUtils.matchAndReplace(mgrUrl, "params", paramSb.toString());
            FileUtils.matchAndReplace(mgrUrl, "putInCache", putCacheSb.toString());

            //读 源文件，将data替换成自动生成的代码段
            FileUtils.matchAndReplace("./des/DataAndConf_" + actId + ".lua", "data", config.toString());

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
