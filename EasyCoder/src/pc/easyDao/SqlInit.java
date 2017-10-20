package pc.easyDao;

import com.google.common.collect.Lists;
import util.FileUtils;
import pc.Cfg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenwen.fu on 2016/1/1.
 */
public class SqlInit {
    private static String BLANK = "                ";

    public static void auto(String projectid) {
        try {
            List<SqlVO> sqls = getSqlList();
            //生成model数据vo
            createModelFile(sqls, projectid);
            //生成接口
            createInterfaceFile(sqls, projectid);
            //生成dao
            createDaoFile(sqls, projectid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * dao 文件
     *
     * @param sqls
     * @param projectid
     */
    private static void createDaoFile(List<SqlVO> sqls, String projectid) {
        String[] keys = {"packageName", "head", "voType", "voName", "tableName", "queryParse",
                "insertParamStr", "updateParamStr", "setInsertParam", "setUpdateParam", "daoName", "daoInterfaceName"};

        String packageName = Cfg.PACKAGE_NAME + "." + projectid;
        String content = FileUtils.readContent(Cfg.METHOD_TEMPLATE_URL + "DaoTemplete");
        String tempStr = "";
        String key = "";
        String replaceStr = "";
        for (SqlVO sql : sqls) {
            tempStr = content;
            for (int i = 0; i < keys.length; i++) {
                key = keys[i];
                Pattern pattern = Pattern.compile("\\-\\{" + key + "\\}");
                switch (i) {
                    case 0:
                        replaceStr = packageName;
                        break;
                    case 1:
                        replaceStr = Cfg.getHead();
                        break;
                    case 2:
                        replaceStr = sql.getVOType();
                        break;
                    case 3:
                        replaceStr = sql.getVOName();
                        break;
                    case 4:
                        replaceStr = sql.getTableName();
                        break;
                    case 5:
                        replaceStr = getQueryParse(sql.getParams());
                        break;
                    case 6:
                        replaceStr = getInsertParams(sql.getParams());
                        break;
                    case 7:
                        replaceStr = getUpdateParams(sql.getParams());
                        break;
                    case 8:
                        replaceStr = getSetInsertParams(sql.getParams());
                        break;
                    case 9:
                        replaceStr = getSetInsertParams(sql.getParams())
                                + BLANK + "ps.setInt(++index, model.getUid());";
                        break;
                    case 10:
                        replaceStr = sql.getDaoName();
                        break;
                    case 11:
                        replaceStr = sql.getDaoInterfaceName();
                        break;
                }
                Matcher matcher = pattern.matcher(tempStr); // 获取 matcher 对象
                tempStr = matcher.replaceAll(replaceStr);
            }

            FileUtils.checkDirExistThenCreate("./des/" + projectid + "/dao/");
            FileUtils.WriteFile("./des/" + projectid + "/dao/" + sql.getDaoName() + ".java", tempStr);
        }
    }


    /**
     * ps.setInt(1, model.getNum1());
     *
     * @param paramVOs
     * @return
     */
    private static String getSetInsertParams(List<SqlParamVO> paramVOs) {
        String string = BLANK + "int index = 0; \n";
        for (SqlParamVO param : paramVOs) {
            string = string.concat(BLANK + param.getSetParams() + "\n");
        }
        return string;
    }

    /**
     * set num1=?, num2=?, interact_num=?
     *
     * @param paramVOs
     * @return
     */
    private static String getUpdateParams(List<SqlParamVO> paramVOs) {
        String str = "";
        for (SqlParamVO param : paramVOs) {
            str = str.concat(param.getSqlParam() + " = ?,");
        }
        return str.substring(0, str.length() - 1);
    }

    /**
     * (beauty_state,field_num,is_first) values (?,?,?,?,?,)
     *
     * @param paramVOs
     * @return
     */
    private static String getInsertParams(List<SqlParamVO> paramVOs) {
        String str = "";
        String commas = "";
        for (SqlParamVO param : paramVOs) {
            str = str.concat(param.getSqlParam() + ",");
            commas = commas.concat("?,");
        }
        return "(" + str.substring(0, str.length() - 1) + ") values (" + commas.substring(0, commas.length() - 1) + ")";
    }

    /**
     * model.setUid(rs.getInt("uid"));
     *
     * @param paramVOs
     * @return
     */
    private static String getQueryParse(List<SqlParamVO> paramVOs) {
        String str = "";
        for (SqlParamVO param : paramVOs) {
            str = str.concat("                    " + param.getQueryParse() + "\n");
        }
        return str;
    }

    /**
     * 生成接口文件
     *
     * @param sqls
     * @param projectid
     */
    private static void createInterfaceFile(List<SqlVO> sqls, String projectid) {
        String[] keys = {"packageName", "head", "voType", "voName", "daoInterfaceName"};

        String packageName = Cfg.PACKAGE_NAME + "." + projectid;
        String content = FileUtils.readContent(Cfg.METHOD_TEMPLATE_URL + "DaoInterfaceTemplete");
        String tempStr = "";
        //生成vo
        String key = "";
        String replaceStr = "";
        for (SqlVO sql : sqls) {
            tempStr = content;
            for (int i = 0; i < keys.length; i++) {
                key = keys[i];
                Pattern pattern = Pattern.compile("\\-\\{" + key + "\\}");
                switch (i) {
                    case 0:
                        replaceStr = packageName;
                        break;
                    case 1:
                        replaceStr = Cfg.getHead();
                        break;
                    case 2:
                        replaceStr = sql.getVOType();
                        break;
                    case 3:
                        replaceStr = sql.getVOName();
                        break;
                    case 4:
                        replaceStr = sql.getDaoInterfaceName();
                        break;
                }
                Matcher matcher = pattern.matcher(tempStr); // 获取 matcher 对象
                tempStr = matcher.replaceAll(replaceStr);
            }

            FileUtils.checkDirExistThenCreate("./des/" + projectid + "/dao/");
            //写文件
            FileUtils.WriteFile("./des/" + projectid + "/dao/" + sql.getDaoInterfaceName() + ".java", tempStr);
        }

    }

    /**
     * 生成model数据vo
     *
     * @param projectid
     */
    private static void createModelFile(List<SqlVO> sqls, String projectid) {
        String[] keys = {"head", "className", "packageName", "params", "methods"};
        String packageName = Cfg.PACKAGE_NAME + "." + projectid;
        String content = FileUtils.readContent(Cfg.METHOD_TEMPLATE_URL + "ModelVoTemplete");

        String tempStr = "";
        //生成vo
        String key = "";

        for (SqlVO sql : sqls) {
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
                        replaceStr = sql.getVOType();
                        break;
                    case 2:
                        replaceStr = packageName;
                        break;
                    case 3:
                        String ParamStr = "";
                        for (SqlParamVO paramVO : sql.getParams()) {
                            ParamStr = ParamStr.concat("    /**" + paramVO.getComment() + "*/\n    private " + paramVO.getType() + " " +
                                    paramVO.getVoParam() + ";\n");
                        }
                        replaceStr = replaceStr.concat(ParamStr);
                        System.out.println(sql.getVOType() + "\n" + "{code}\n" + ParamStr + "\n{code}\n ");
                        break;
                    case 4:
                        replaceStr = getSetMethodStr(sql.getParams());
                        break;
                }
                Matcher matcher = pattern.matcher(tempStr); // 获取 matcher 对象
                tempStr = matcher.replaceAll(replaceStr);
            }
            //写文件
            FileUtils.WriteFile("./des/" + projectid + "/model/" + sql.getVOType() + ".java", tempStr);

        }
    }


    /**
     * 获取建表语句信息列表
     *
     * @return
     * @throws IOException
     */
    private static List<SqlVO> getSqlList() throws IOException {
        Pattern paramPattern = Pattern.compile("`.*`");
        BufferedReader in = new BufferedReader(new FileReader(Cfg.SQL_URL));
        List<SqlVO> sqls = Lists.newArrayList();
        String s = "";
        String temp = "";
        SqlVO sql = null;
        while ((s = in.readLine()) != null) {
            if (s.indexOf("PRIMARY") == -1 && s.indexOf("KEY") == -1) {
                Matcher paramMatcher = paramPattern.matcher(s);
                if (paramMatcher.find()) {
                    temp = paramMatcher.group();//匹配到的``内容
                    temp = temp.substring(1, temp.length() - 1);
                    if (s.indexOf("CREATE TABLE") != -1 || s.indexOf("create table") != -1) {//表名
                        //新建vo
                        sql = new SqlVO();
                        sql.setTableName(temp);
                        sqls.add(sql);
                    } else {//参数
                        SqlParamVO sqlParam = new SqlParamVO();
                        sqlParam.setType(getParamType(s));//获取数据类型
                        sqlParam.setSqlParam(temp.concat(""));//clone param, 获取_命名
                        String[] paramClips = temp.split("_");
                        String param = paramClips[0];
                        for (int i = 1; i < paramClips.length; i++) {
                            param = param.concat(Cfg.getHeadUp(paramClips[i]));//将_的命名变成驼峰式命名
                        }
                        sqlParam.setVoParam(param);//获取驼峰式命名
                        sqlParam.setComment(getParamComment(s));//参数注释
                        sql.addParam(sqlParam);
                    }
                }
            }
        }
        in.close();
        return sqls;
    }

    /**
     * 获取参数的类型
     *
     * @param s
     * @return
     */
    private static String getParamType(String s) {
        String type = "";
        if (s == null || s.equals("")) {
            return type;
        }
        if (s.indexOf("bigint(") > -1) {
            type = "long";
        } else if (s.indexOf("int(") > -1) {
            type = "int";
        } else if (s.indexOf("char(") > -1) {
            type = "String";
        } else if (s.indexOf("timestamp") > -1) {
            type = "Date";
        }
        return type;
    }

    private static String getParamComment(String s) {
        String comment = "";
        if (s == null || s.equals("")) {
            return comment;
        }
        if (s.indexOf("'") != -1) {
            Pattern paramPattern = Pattern.compile("'.*'");
            Matcher paramMatcher = paramPattern.matcher(s);
            if (paramMatcher.find()) {
                comment = paramMatcher.group();//匹配到的''内容
                comment = comment.substring(1, comment.length() - 1);
            }
        }
        return comment;
    }

    public static String getSetMethodStr(List<SqlParamVO> paramVOs) {
        String[] keys = {"Param", "param", "paramType"};
        String setGetContent = FileUtils.readContent(Cfg.METHOD_TEMPLATE_URL + "setGetTemplete");
        String key = "";
        String replaceStr = "";
        String methodStr = "";
        String tempStr = "";
        for (SqlParamVO paramVO : paramVOs) {
            tempStr = setGetContent;
            for (int i = 0; i < keys.length; i++) {
                key = keys[i];
                Pattern pattern = Pattern.compile("\\-\\{" + key + "\\}");
                switch (i) {
                    case 0:
                        replaceStr = Cfg.getHeadUp(paramVO.getVoParam());
                        break;
                    case 1:
                        replaceStr = paramVO.getVoParam();
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
}
