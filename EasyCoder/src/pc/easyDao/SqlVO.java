package pc.easyDao;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import pc.Cfg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenwen.fu on 2016/1/1.
 */
public class SqlVO {
    /**sql的表名*/
    private String tableName;
    /**sql中的参数*/
    private List<SqlParamVO> params;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<SqlParamVO> getParams() {
        return params;
    }

    public void addParam(SqlParamVO paramVO){
        this.params.add(paramVO);
    }
    public SqlVO(){
        this.params = new ArrayList<SqlParamVO>();
    }

    public String getDaoName(){
       return getStandardName()+ Cfg.DAO_END;
    }

    public String getDaoInterfaceName(){
        return "I" + getStandardName() + Cfg.DAO_INTERFACE_END;
    }
    public String getVOType(){
        return getStandardName()+"VO";
    }

    public String getVOName(){
        String voType = getStandardName();
        return voType.substring(0,1).toLowerCase() + voType.substring(1, voType.length());
    }

    private String getStandardName(){
        String voName = "";
        List<String> temp = strToList(tableName);
        for (String s:temp){
            voName = voName.concat(Cfg.getHeadUp(s));
        }
        return voName;
    }

    public static List<String> strToList(String str){
        return Lists.newArrayList(Splitter.on("_").omitEmptyStrings().split(str));
    }
}
