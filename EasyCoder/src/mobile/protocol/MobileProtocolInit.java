package mobile.protocol;

import mobile.MobileCfg;
import pc.Cfg;
import pc.easyMethod.MethodParamVO;
import pc.easyMethod.MethodReplaceVO;
import pc.easyMethod.MethodVO;
import util.FileUtils;
import util.ParseMethodXmlUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenwen.fu on 2017/4/24.
 */
public class MobileProtocolInit {
    public static void auto(String projectId, String actId) {
        List<MethodVO> methodVOs = ParseMethodXmlUtil.parseXML(MobileCfg.METHOD_XML_URL);
        List<MethodReplaceVO> replaceList = resloveMethods(methodVOs);
        createControllerMethod(projectId, actId, replaceList);
        createLogicMethod(projectId, actId, replaceList);
        autoWiki(methodVOs);
        createFront(actId, methodVOs);
    }

    /**
     * 生成前端代码
     *
     * @param methodVOs
     */
    private static void createFront(String actId, List<MethodVO> methodVOs) {
        String[] keys = {"des", "methodName", "requestParam1", "requestParam2", "responseParams"};

        String methodStr = FileUtils.readContent(MobileCfg.FRONT_METHOD_TEMPLATE_URL + "NetMethodTemplete");
        String tempMethodStr = "";
        String resultMethodStr = "";
        for (MethodVO replace : methodVOs) {

            StringBuilder methodRequestParam = new StringBuilder("");
            StringBuilder methodRequestParam1 = new StringBuilder("");
            StringBuilder des = new StringBuilder("--" + replace.getComments());
            for (MethodParamVO requestParam : replace.getRequest()) {
                des = des.append("\n").append("--").append(requestParam.getName()).append(":").append(requestParam.getComments());
                methodRequestParam = methodRequestParam.append(requestParam.getName()).append(",");
                methodRequestParam1 = methodRequestParam1.append(requestParam.getName()).append("=").append(requestParam.getName()).append(",");
            }

            StringBuilder methodResponseParam = new StringBuilder("");

            for (MethodParamVO response : replace.getResponse()) {
                methodResponseParam = methodResponseParam.append("--").append(response.getComments()).append("\n\t\t").
                        append("local ").append(response.getName()).append(" = ").append("respData.").append(response.getName()).append("\n\t\t");
            }

            tempMethodStr = methodStr;
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];

                Pattern pattern = Pattern.compile("\\-\\{" + key + "\\}");
                Matcher matcher = pattern.matcher(tempMethodStr); // 获取 matcher 对象
                String repalceStr = "";
                switch (i) {
                    case 0:
                        repalceStr = des.toString();
                        break;
                    case 1:
                        repalceStr = replace.getName();
                        break;
                    case 2:
                        repalceStr = methodRequestParam.toString();
                        break;
                    case 3:
                        if (methodRequestParam1.length() > 0) {
                            repalceStr = methodRequestParam1.toString().substring(0, methodRequestParam1.length() - 1);
                        }
                        break;
                    case 4:
                        if (methodResponseParam.length() > 0) {
                            repalceStr = methodResponseParam.toString().substring(0, methodResponseParam.length() - 1);
                        }
                        break;
                }
                tempMethodStr = matcher.replaceAll(repalceStr);

            }
            resultMethodStr = resultMethodStr.concat(tempMethodStr + "\n\n");
        }
        //读 lua源文件，将autoMethod替换成自动生成的代码段
        String mgrUrl = "./des/Net_" + actId + ".lua";
        FileUtils.matchAndReplace(mgrUrl, "autoMethod", resultMethodStr);
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
            String extra = "";

            String noTypeParams = "";
            String setParams = "";
            String comment = "";
            String des = "\n     * " + methodVO.getComments();//空格是为了格式
            for (MethodParamVO p : methodVO.getRequest()) {
                noTypeParams = noTypeParams.concat(" ," + p.getName());
                setParams = setParams.concat(getParamsSet(p) + ",");
                params = params.concat(p.getType() + " " + p.getName() + ", ");
                extra = extra.concat("\"" + p.getName() + "\",");
                comment = comment.concat(p.getName() + ":" + p.getComments() + " ");
            }
            replaceVO.setDes(des);
            if (extra.length() > 0) {
                replaceVO.setExtra("\n    @ControllerParams({" + extra.substring(0, extra.length() - 1) + "})//" + comment);
            } else {
                replaceVO.setExtra("");
            }
            replaceVO.setNoTypeParams(noTypeParams);
            replaceVO.setComments("     *" + methodVO.getComments());
            if (!params.equals("")) {
                replaceVO.setParams(", " + params.substring(0, params.length() - 2));//，和空格
            } else {
                replaceVO.setParams("");
            }
            if (methodVO.getRequest().size() == 0) {
                replaceVO.setSetParams("");
            } else {
                replaceVO.setSetParams(setParams.substring(0, setParams.length() - 1));
            }


            String returns = "";
            String tempReturn = "";
            for (MethodParamVO r : methodVO.getResponse()) {
                if ("config".equals(r.getName())) {
                    tempReturn = "\n        resp.putData(\"" + r.getName() + "\", data.config());";
                } else if (!"userData".equals(r.getName()) && !"result".equals(r.getName())) {//不用生成userData, result的代码，模板里已有
                    tempReturn = "\n        resp.putData(\"" + r.getName() + "\", null);";
                }
                returns = (returns.equals("")) ? tempReturn : returns.concat(tempReturn);//为了格式
            }
            replaceVO.setReturnParams(returns);
            replaceVOs.add(replaceVO);
        }
        return replaceVOs;
    }

    private static String getParamsSet(MethodParamVO p) {
        if (p.getType().equals("String")) {
            return "req.getString(\"" + p.getName() + "\")";
        } else {
            return "req.get" + Cfg.getHeadUp(p.getType()) + "Value(\"" + p.getName() + "\")";
        }
    }

    private static void createControllerMethod(String projectId, String actId, List<MethodReplaceVO> replaceList) {
        String[] keys = {"des", "injectParams", "methodName", "ProjectId", "methodParams", "actId"};
        String projectIdHeadUp = Cfg.getHeadUp(projectId);

        String methodStr = FileUtils.readContent(MobileCfg.METHOD_TEMPLATE_URL + "ControllerMethodTemplete");
        String tempMethodStr = "";
        String resultMethodStr = "";
        for (MethodReplaceVO replace : replaceList) {
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
                        repalceStr = replace.getExtra();
                        break;
                    case 2:
                        repalceStr = replace.getMethodName();
                        break;
                    case 3:
                        repalceStr = projectIdHeadUp;
                        break;
                    case 4:
                        repalceStr = replace.getSetParams();
                        if (!repalceStr.equals("")) {
                            repalceStr = ", " + repalceStr;
                        }
                        break;
                    case 5:
                        repalceStr = actId;
                }
                tempMethodStr = matcher.replaceAll(repalceStr);

            }
            resultMethodStr = resultMethodStr.concat(tempMethodStr + "\n\n");
        }
        //读 Controller源文件，将autoMethod替换成自动生成的代码段
        String mgrUrl = "./des/act" + actId + "/controller/" + projectIdHeadUp + "Controller.java";
        FileUtils.matchAndReplace(mgrUrl, "autoMethod", resultMethodStr);
    }


    private static void createLogicMethod(String projectId, String actId, List<MethodReplaceVO> replaceList) {
        String[] keys = {"des", "methodName", "params", "returnParams", "ProjectId"};
        String projectIdHeadUp = Cfg.getHeadUp(projectId);

        String methodStr = FileUtils.readContent(MobileCfg.METHOD_TEMPLATE_URL + "DataMethodTemplete");
        String tempMethodStr = "";
        String resultMethodStr = "";
        for (MethodReplaceVO replace : replaceList) {
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
                        repalceStr = Cfg.getHeadUp(projectId);
                        break;

                }
                tempMethodStr = matcher.replaceAll(repalceStr);

            }
            resultMethodStr = resultMethodStr.concat(tempMethodStr + "\n");
        }
        //读 Controller源文件，将autoMethod替换成自动生成的代码段
        String mgrUrl = "./des/act" + actId + "/" + projectIdHeadUp + "Logic.java";
        FileUtils.matchAndReplace(mgrUrl, "methods", resultMethodStr);
    }


    private static void autoWiki(List<MethodVO> methods) {
        System.out.println("h3.后端协议：");
        System.out.println("h5.{color:#993300}每个协议都有userData(用户数据)和result(1:成功, 0:失败)这两个返回值，下面就不写了{color}\n");
        String VERTICAL = " | ";
        String ENTER = " \\\\ ";
        StringBuffer temp = null;
        System.out.println("|| 接口 || 接口说明 || 参数 || 参数说明 || 返回值 || 返回说明 ||");
        for (MethodVO method : methods) {
            temp = new StringBuffer(VERTICAL + method.getName() + VERTICAL);
            temp.append(method.getComments() + ENTER);
            //参数
            StringBuilder paramSb = new StringBuilder("");
            StringBuilder descSb = new StringBuilder("");
            for (MethodParamVO request : method.getRequest()) {
                paramSb.append(request.getName() + ":" + request.getType() + ";" + ENTER);
                descSb.append(request.getComments() + ENTER);
            }

            temp.append(VERTICAL + paramSb.toString() + VERTICAL + descSb.toString() + VERTICAL);
            //返回
            StringBuilder responseDesc = new StringBuilder("");
            for (MethodParamVO response : method.getResponse()) {
                temp.append(response.getName() + ":" + response.getType() + ";" + ENTER);
                responseDesc.append(response.getComments() + ENTER);
            }
            temp.append(VERTICAL + responseDesc.toString() + VERTICAL);
            System.out.println(temp.toString());
        }

    }

}
