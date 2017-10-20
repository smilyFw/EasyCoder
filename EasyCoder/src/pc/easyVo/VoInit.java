package pc.easyVo;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import pc.Cfg;
import pc.easyDao.SqlInit;
import pc.easyDao.SqlParamVO;
import util.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenwen.fu on 2017/5/18.
 */
public class VoInit {

    public static void auto(String projectid) {
        try {
            List<SqlParamVO> paramList = getParamList();
            createModelFile(paramList, projectid);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取建表语句信息列表
     *
     * @return
     * @throws IOException
     */
    private static List<SqlParamVO> getParamList() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(Cfg.VO_URL));
        String s = "";
        String temp = "";
        List<String> tempList = null;
        List<SqlParamVO> paramList = Lists.newArrayList();
        while ((s = in.readLine()) != null) {
            //private int normal;//粽子
            tempList = Lists.newArrayList(Splitter.on(";").omitEmptyStrings().split(s));
            if (tempList != null && tempList.size() > 0) {
                tempList = Lists.newArrayList(Splitter.on(" ").omitEmptyStrings().split(tempList.get(0)));
            }
            if (tempList != null && tempList.size() >= 2) {
                SqlParamVO sqlParam = new SqlParamVO();
                sqlParam.setType(tempList.get(tempList.size() - 2));//获取数据类型
                sqlParam.setVoParam(tempList.get(tempList.size() - 1));//参数名
                paramList.add(sqlParam);
            }

        }
        in.close();
        return paramList;
    }

    /**
     * 生成model数据vo
     *
     * @param projectid
     */

    private static void createModelFile(List<SqlParamVO> paramList, String projectid) {
        String[] keys = {"head", "className", "packageName", "params", "methods"};
        String packageName = Cfg.PACKAGE_NAME + "." + projectid;
        String content = FileUtils.readContent(Cfg.METHOD_TEMPLATE_URL + "NewModelVoTemplete");

        String param = FileUtils.readContent(Cfg.VO_URL);

        String tempStr = "";
        //生成vo
        String key = "";

        String fileName = "";
        tempStr = content;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            Pattern pattern = Pattern.compile("\\-\\{" + key + "\\}");
            String replaceStr = "";
            switch (i) {
                case 0:
                    replaceStr = Cfg.getHead();
                    break;
                case 1:
                    fileName = Cfg.getHeadUp(projectid) + "UserVO";
                    replaceStr = fileName;
                    System.out.println(replaceStr + "\n" + "{code}\n");
                    break;
                case 2:
                    replaceStr = packageName;
                    break;
                case 3:
                    replaceStr = replaceStr.concat(param);
                    System.out.println(param + "\n" + "{code}\n");
                    break;
                case 4:
                    replaceStr = SqlInit.getSetMethodStr(paramList);
                    break;
            }
            Matcher matcher = pattern.matcher(tempStr); // 获取 matcher 对象
            tempStr = matcher.replaceAll(replaceStr);
        }
        //写文件
        FileUtils.WriteFile("./des/" + projectid + "/model/" + fileName + ".java", tempStr);
    }

}
