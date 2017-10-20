package mobile.param;

import com.google.common.collect.Lists;
import mobile.MobileCfg;
import mobile.param.MobileParamVO;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pc.Cfg;
import util.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenwen.fu on 2017/4/24.
 */
public class MobileParamInit {
    public static void auto(String projectId, String actId) {
        List<MobileParamVO> params = parseXML(MobileCfg.PARAM_XML_URL);
        createDataParam(params, actId, projectId);
        createToolJsp(projectId, actId, params);
        autoFront(actId, params);
    }

    private static void autoFront(String actId, List<MobileParamVO> params) {
        StringBuilder sb = new StringBuilder("");
        for (MobileParamVO param : params) {
            if (param.getBackOnly() == 0) {
                sb.append("--" + param.getDesc() + "\nself." + param.getName() + " = d." + param.getName()).append("\n");
            }
        }

        //读 源文件，将data替换成自动生成的代码段
        String mgrUrl = "./des/DataAndConf_" + actId + ".lua";
        FileUtils.matchAndReplace(mgrUrl, "config", sb.toString());

    }

    private static void createDataParam(List<MobileParamVO> params, String actId, String projectId) {
        StringBuilder paramSb = new StringBuilder("");
        StringBuilder toClientParamSb = new StringBuilder("");

        StringBuilder logStr = new StringBuilder("");
        StringBuilder toClientStr = new StringBuilder("");

        for (MobileParamVO param : params) {
            paramSb.append(param.getInit());
            logStr.append(param.getName() + ", ");
            if (param.getBackOnly() != 1) {
                toClientParamSb.append(param.getInit());
                toClientStr.append("\n        object.put(\"" + param.getName() + "\", " + param.getName() + ");");
            }
        }

        String[] keys = {"params", "getAndSetMethods", "logParams", "toClientParamSet"};
        String mgrUrl = "./des/act" + actId + "/" + Cfg.getHeadUp(projectId) + "Data.java";

        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(mgrUrl));
            String mgrStr = "";
            String tempMgrStr = "";
            while ((tempMgrStr = reader1.readLine()) != null) {
                mgrStr = mgrStr.concat(tempMgrStr + "\n");
            }
            reader1.close();

            for (String key : keys) {
                Pattern pattern1 = Pattern.compile("\\-\\{" + key + "\\}");
                Matcher matcher1 = pattern1.matcher(mgrStr); // 获取 matcher 对象
                if ("params".equals(key)) {
                    mgrStr = matcher1.replaceAll(paramSb.toString());
                } else if ("getAndSetMethods".equals(key)) {
                    mgrStr = matcher1.replaceAll(getSetMethodStr(params));
                } else if ("logParams".equals(key)) {
                    mgrStr = matcher1.replaceAll(logStr.substring(0, logStr.length() - 2));
                } else if ("toClientParamSet".equals(key)) {
                    mgrStr = matcher1.replaceAll(toClientStr.toString());
                }
            }
            //将处理后的mgr文件内容再重新写入mgr中
            BufferedWriter writer = new BufferedWriter(new FileWriter(mgrUrl));
            writer.write(mgrStr);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("h3.用户数据：");
        System.out.println(projectId + "Data\n{code}\n" + toClientParamSb.toString() + "{code}");

        System.out.println("h3.后端存储数据：");
        System.out.println("\n{code}\n" + FileUtils.readContent(MobileCfg.PARAM_XML_URL) + "{code}");

    }


    private static String getSetMethodStr(List<MobileParamVO> paramVOs) {
        String[] keys = {"Param", "param", "paramType"};
        String setGetContent = FileUtils.readContent(Cfg.METHOD_TEMPLATE_URL + "setGetTemplete");
        String key = "";
        String replaceStr = "";
        String methodStr = "";
        String tempStr = "";
        for (MobileParamVO paramVO : paramVOs) {
            tempStr = setGetContent;
            for (int i = 0; i < keys.length; i++) {
                key = keys[i];
                Pattern pattern = Pattern.compile("\\-\\{" + key + "\\}");
                switch (i) {
                    case 0:
                        replaceStr = Cfg.getHeadUp(paramVO.getName());
                        break;
                    case 1:
                        replaceStr = paramVO.getName();
                        break;
                    case 2:
                        replaceStr = paramVO.getType();
                        break;
                }
                Matcher matcher = pattern.matcher(tempStr); // 获取 matcher 对象
                tempStr = matcher.replaceAll(replaceStr);
            }
            methodStr = methodStr.concat(tempStr);
        }
        return methodStr;
    }

    /**
     * 解析 test/method.xml获得方法列表
     *
     * @return
     * @throws DocumentException
     */
    private static List<MobileParamVO> parseXML(String methodXmlUrl) {
        List<MobileParamVO> methodVOList = new ArrayList<MobileParamVO>();

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(methodXmlUrl));

            Element root = document.getRootElement();
            List<Element> methods = root.elements();
            List<MobileParamVO> paramList = Lists.newArrayList();

            for (Element method : methods) {
                MobileParamVO param = new MobileParamVO();

                param.setName(method.attribute("name").getValue());
                param.setDesc(method.attribute("desc").getValue());
                param.setType(method.attribute("type").getValue());

                if (method.attribute("backOnly") != null) {
                    param.setBackOnly(Integer.parseInt(method.attribute("backOnly").getValue()));
                }

                paramList.add(param);
            }
            return paramList;

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return methodVOList;
    }

    private static void createToolJsp(String projectId, String actId, List<MobileParamVO> params) {
        StringBuilder updateParamSb = new StringBuilder("");
        StringBuilder setParamSb = new StringBuilder("");
        StringBuilder paramNameSb = new StringBuilder("");

        int paramNum = 0;

        for (MobileParamVO param : params) {
            if (param.getBackOnly() != 1) {
                updateParamSb.append("                    update.set" + Cfg.getHeadUp(param.getName())
                        + "(NumberUtils.toInt(request.getParameter(\"" + param.getName() + "\")));\n");

                setParamSb.append("        <tr>\n" +
                        "            <td class=\"alignL red\">" + param.getDesc() + "</td>\n" +
                        "            <td class=\"alignR\"><label><input type=\"number\" id=\"" + param.getName() + "\" value=\"<%=data.get" + Cfg.getHeadUp(param.getName()) + "()%>\"></label>\n" +
                        "            </td>\n" +
                        "        </tr>\n");
            } else {
                setParamSb.append(" <tr>\n" +
                        "            <td class=\"alignL red\">" + param.getDesc() + "</td>\n" +
                        "            <td class=\"alignR\"><%=Integer.toBinaryString(data.get" + Cfg.getHeadUp(param.getName()) + "())%></td>\n" +
                        "        </tr>");
            }
            paramNameSb.append("<th>" + param.getDesc() + "</th>");
            paramNum++;
        }

        String[] keys = {"paramUpdate", "ProjectId", "paramSet", "paramName", "actId", "paramNum"};
        String mgrUrl = "./des/" + actId + ".jsp";

        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(mgrUrl));
            String mgrStr = "";
            String tempMgrStr = "";
            while ((tempMgrStr = reader1.readLine()) != null) {
                mgrStr = mgrStr.concat(tempMgrStr + "\n");
            }
            reader1.close();

            for (String key : keys) {
                Pattern pattern1 = Pattern.compile("\\-\\{" + key + "\\}");
                Matcher matcher1 = pattern1.matcher(mgrStr); // 获取 matcher 对象
                if ("paramUpdate".equals(key)) {
                    mgrStr = matcher1.replaceAll(updateParamSb.toString());
                } else if ("ProjectId".equals(key)) {
                    mgrStr = matcher1.replaceAll(Cfg.getHeadUp(projectId));
                } else if ("paramSet".equals(key)) {
                    mgrStr = matcher1.replaceAll(setParamSb.toString());
                } else if ("paramName".equals(key)) {
                    mgrStr = matcher1.replaceAll(paramNameSb.toString());
                } else if ("actId".equals(key)) {
                    mgrStr = matcher1.replaceAll(actId);
                } else if ("paramNum".equals(key)) {
                    mgrStr = matcher1.replaceAll(paramNum + 1 + "");
                }
            }
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

}
