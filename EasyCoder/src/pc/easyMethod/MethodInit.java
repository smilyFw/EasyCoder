package pc.easyMethod;

import util.FileUtils;
import pc.Cfg;
import util.ParseMethodXmlUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenwen.fu on 2016/1/1.
 */
public class MethodInit {
    public static void auto(String projectId) {
        List<MethodVO> methodVOs = ParseMethodXmlUtil.parseXML(Cfg.METHOD_URL);
        List<MethodReplaceVO> replaceList = resloveMethods(methodVOs);
        //mgr里的method
        createMgrMethod(projectId, replaceList);
        //action里的method
        createActionMethod(projectId, replaceList);
        //生成wiki注释
        autoWiki(projectId, methodVOs);
    }


    /**
     * action里的方法
     *
     * @param projectId
     */
    private static void createActionMethod(String projectId, List<MethodReplaceVO> replaceList) {
        String[] keys = {"des", "methodName", "params", "ProjectId", "projectid", "setParam", "head"};
        String projectIdHeadUp = Cfg.getHeadUp(projectId);
        try {

            String methodStr = FileUtils.readContent(Cfg.METHOD_TEMPLATE_URL + "ActionMethodTemplete");
            String tempMethodStr = "";
            String resultMethodStr = "";
            for (MethodReplaceVO replace : replaceList) {
                tempMethodStr = methodStr;
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];

                    Pattern pattern = Pattern.compile("\\-\\{" + key + "\\}");
                    Matcher matcher = pattern.matcher(tempMethodStr); // 获取 matcher 对象
                    String replaceStr = "";
                    switch (i) {
                        case 0:
                            replaceStr = replace.getComments();
                            break;
                        case 1:
                            replaceStr = replace.getMethodName();
                            break;
                        case 2:
                            replaceStr = replace.getNoTypeParams();
                            break;
                        case 3:
                            replaceStr = projectIdHeadUp;
                            break;
                        case 4:
                            replaceStr = projectId;
                            break;
                        case 5:
                            replaceStr = replace.getSetParams();
                            break;
                        case 6:
                            replaceStr = Cfg.getHead();
                            break;
                    }
                    tempMethodStr = matcher.replaceAll(replaceStr);

                }
                resultMethodStr = resultMethodStr.concat(tempMethodStr + "\n");
            }
            //读 action源文件，将autoMethod替换成自动生成的代码段
            String mgrUrl = "./des/" + projectId + "/action/" + projectIdHeadUp + "Action.java";
            BufferedReader reader1 = new BufferedReader(new FileReader(mgrUrl));
            String mgrStr = "";
            String tempMgrStr = "";
            while ((tempMgrStr = reader1.readLine()) != null) {
                mgrStr = mgrStr.concat(tempMgrStr + "\n");
            }
            reader1.close();

            Pattern pattern1 = Pattern.compile("\\-\\{" + "autoMethod" + "\\}");
            Matcher matcher1 = pattern1.matcher(mgrStr); // 获取 matcher 对象
            mgrStr = matcher1.replaceAll(resultMethodStr);

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

    /**
     * mgr里的method
     *
     * @param projectId
     */
    private static void createMgrMethod(String projectId, List<MethodReplaceVO> replaceList) {
        String[] keys = {"des", "methodName", "params", "returnParams", "ProjectId"};
        String projectIdHeadUp = Cfg.getHeadUp(projectId);
        try {
            String methodStr = FileUtils.readContent(Cfg.METHOD_TEMPLATE_URL + "MethodTemplete");
            String tempMethodStr = "";
            String resultMethodStr = "";
            for (MethodReplaceVO replace : replaceList) {
                if (replace.getMethodName().equals("getHelpReward")
                        || replace.getMethodName().equals("buyFriHelp")
                        || replace.getMethodName().equals("clearHelpCD")) {
                    continue;
                }
                tempMethodStr = methodStr;
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];

                    Pattern pattern = Pattern.compile("\\-\\{" + key + "\\}");
                    Matcher matcher = pattern.matcher(tempMethodStr); // 获取 matcher 对象
                    String repalceStr = "";
                    switch (i) {
                        case 0:
                            repalceStr = replace.getDes();
                            break;
                        case 1:
                            repalceStr = replace.getMethodName();
                            break;
                        case 2:
                            repalceStr = replace.getParams();
                            break;
                        case 3:
                            repalceStr = replace.getReturnParams();
                            break;
                        case 4:
                            repalceStr = projectIdHeadUp;
                            break;
                    }
                    tempMethodStr = matcher.replaceAll(repalceStr);

                }
                resultMethodStr = resultMethodStr.concat(tempMethodStr + "\n");
            }
            //读 mgr源文件，将autoMethod替换成自动生成的代码段
            String mgrUrl = "./des/" + projectId + "/logic/" + projectIdHeadUp + "Mgr.java";
            BufferedReader reader1 = new BufferedReader(new FileReader(mgrUrl));
            String mgrStr = "";
            String tempMgrStr = "";
            while ((tempMgrStr = reader1.readLine()) != null) {
                mgrStr = mgrStr.concat(tempMgrStr + "\n");
            }
            reader1.close();

            Pattern pattern1 = Pattern.compile("\\-\\{" + "autoMethod" + "\\}");
            Matcher matcher1 = pattern1.matcher(mgrStr); // 获取 matcher 对象
            mgrStr = matcher1.replaceAll(resultMethodStr);

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

    /**
     * 构造需要正则替换显示的内容。比如方法注释，方法参数和方法返回的map构造
     *
     * @param methodVOs
     * @return
     */
    private static List<MethodReplaceVO> resloveMethods(List<MethodVO> methodVOs) {
        List<MethodReplaceVO> replaceVOs = new ArrayList<MethodReplaceVO>();
        for (MethodVO methodVO : methodVOs) {
            MethodReplaceVO replaceVO = new MethodReplaceVO();
            replaceVO.setMethodName(methodVO.getName());
            String params = "";
            String noTypeParams = "";
            String setParams = "";
            String des = "\n     *" + methodVO.getComments();//空格是为了格式
            for (MethodParamVO p : methodVO.getRequest()) {
                des = des.concat("\n    *@param " + p.getName() + " " + p.getComments());//空格是为了格式
                params = params.concat(" ," + p.getType() + " " + p.getName());
                noTypeParams = noTypeParams.concat(" ," + p.getName());
                setParams = setParams.concat("        " + getParamsSet(p) + "\n");
            }
            replaceVO.setDes(des);
            replaceVO.setParams(params);
            replaceVO.setNoTypeParams(noTypeParams);
            replaceVO.setComments("     *" + methodVO.getComments());
            if (methodVO.getRequest().size() == 0) {
                replaceVO.setSetParams("");
            } else {
                replaceVO.setSetParams(setParams);
            }


            String returns = "";
            String tempReturn;
            for (MethodParamVO r : methodVO.getResponse()) {
                tempReturn = "resultMap.put(\"" + r.getName() + "\",null);\n";
                returns = (returns.equals("")) ? tempReturn : returns.concat("            " + tempReturn);//为了格式
            }
            replaceVO.setReturnParams(returns);
            replaceVOs.add(replaceVO);
        }
        return replaceVOs;
    }


    private static String getParamsSet(MethodParamVO p) {
        String value = "request.getParameter(\"" + p.getName() + "\")";
        String key = p.getType() + " " + p.getName() + " =";

        if (p.getType().equals("int")) {
            return key + "Integer.parseInt(" + value + ");";
        } else if (p.getType().equals("long")) {
            return key + "Long.parseLong(" + value + ");";
        } else {
            return key + value + ";";
        }
    }


    private static void autoWiki(String projectid, List<MethodVO> methods) {
        System.out.println("h3.后端协议：");
        System.out.println("errorCode: \n{code}\n/*** 元宝不足*/\n" +
                "public static int ERROR_LACK_SHELL = -101;\n" +
                "/*** 活动已结束，提示玩家重整游戏*/\n" +
                "public static int ERROR_ACT_END = -404;\n" +
                "/** 已进入最后领奖阶段，只能领奖 */\n" +
                "public static final int ERROR_CODE_LAST_AWARD_TIME = -403;\n" +
                "/** 已经没有CD了 */\n" +
                "public static final int ERROR_CODE_NO_CD = -402;\n {code}");
        String VERTICAL = " | ";
        String ENTER = " \\\\ ";
        StringBuffer temp = null;
        boolean isFirst = true;
        System.out.println("|| 接口 || 参数 || 返回 || 描述 ||");
        for (MethodVO method : methods) {
            temp = new StringBuffer(isFirst ? VERTICAL + projectid + ".do" + VERTICAL : VERTICAL + VERTICAL);
            isFirst = false;
            temp.append(method.getName() + ENTER);
            //参数
            for (MethodParamVO request : method.getRequest()) {
                temp.append(request.getName() + ":" + request.getType() + ";" + " //" + request.getComments() + ENTER);
            }
            temp.append(VERTICAL + "code:int" + ENTER);
            //返回
            for (MethodParamVO response : method.getResponse()) {
                temp.append(response.getName() + ":" + response.getType() + ";" + " //" + response.getComments() + ENTER);
            }
            temp.append(VERTICAL + method.getComments() + VERTICAL);
            System.out.println(temp.toString());
        }
        System.out.println(VERTICAL + "usrconf.do" + VERTICAL + VERTICAL + "新增:" + ENTER +
                projectid + ENTER + VERTICAL + "活动开关" + VERTICAL);
        System.out.println(VERTICAL + "activity.do" + VERTICAL + VERTICAL + "新增:" + ENTER +
                projectid + ENTER + VERTICAL + "首次弹窗" + VERTICAL);

        System.out.println("h3.数据vo");


    }
}
