package pc.easyMeta;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pc.Cfg;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenwen.fu on 2016/3/22.
 */
public class MetaInit {

    /**
     * 解析 test/method.xml获得方法列表
     *
     * @return
     * @throws DocumentException
     */
    public static void auto(String projectId) {

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(Cfg.META_URL));

            Element root = document.getRootElement();
            Element act = (Element) root.elements().get(0);
            int actId = Integer.parseInt(act.attribute("id").getValue());
            List<Element> giftAndPramList = act.elements();
            StringBuffer str = new StringBuffer("//活动id"+   "\n" + "public static final int ACT_ID  = " + actId +"; \n");
            GiftInfoMeta tempInfo;
            ParamMeta tempParam;
            for (Element e : giftAndPramList) {
                if (e.getName().equals("gift_list")) {
                    for (Element giftE : (List<Element>) e.elements()) {
                        tempInfo = new GiftInfoMeta();
                        tempInfo.setId(giftE.attribute("id").getValue());
                        tempInfo.setDesc(giftE.attribute("desc").getValue());
                        str.append(tempInfo.getString());
                    }

                } else if (e.getName().equals("param_list")) {
                    for (Element paramE : (List<Element>) e.elements()) {
                        tempParam = new ParamMeta();
                        tempParam.setName(paramE.attribute("name").getValue());
                        tempParam.setValue(paramE.attribute("value").getValue());
                        tempParam.setDesc(paramE.attribute("desc").getValue());
                        str.append(tempParam.getString());
                    }
                }else if(e.getName().equals("pool_list")){
                    for (Element pool : (List<Element>) e.elements()) {
                        tempInfo = new PoolInfoMeta();
                        tempInfo.setId(pool.attribute("id").getValue());
                        tempInfo.setDesc(pool.attribute("desc").getValue());
                        str.append(tempInfo.getString());
                    }
                }
            }


            String[] keys = {"params", "ProjectId", "head"};
            String projectIdHeadUp = Cfg.getHeadUp(projectId);
            try {
                //读 mgr源文件，将autoMethod替换成自动生成的代码段
                String mgrUrl = "./des/" + projectId + "/logic/" + projectIdHeadUp + "Cfg.java";
                BufferedReader reader1 = new BufferedReader(new FileReader(mgrUrl));
                String cfgStr = "";
                String tempMgrStr = "";
                while ((tempMgrStr = reader1.readLine()) != null) {
                    cfgStr = cfgStr.concat(tempMgrStr + "\n");
                }
                reader1.close();

                String tempStr = "";
                String key = "";
                for (int i = 0; i < keys.length; i++) {
                    key = keys[i];
                    switch (i) {
                        case 0:
                            tempStr = str.toString();
                            break;
                        case 1:
                            tempStr = projectIdHeadUp;
                            break;
                        case 2:
                            tempStr = Cfg.getHead();
                            break;
                    }
                    Pattern pattern1 = Pattern.compile("\\-\\{" + key + "\\}");
                    Matcher matcher1 = pattern1.matcher(cfgStr); // 获取 matcher 对象
                    cfgStr = matcher1.replaceAll(tempStr);
                }

                //将处理后的mgr文件内容再重新写入mgr中
                BufferedWriter writer = new BufferedWriter(new FileWriter(mgrUrl));
                writer.write(cfgStr);
                writer.close();

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }

    }
}
