package pc.easyDao;

import pc.Cfg;

/**
 * Created by wenwen.fu on 2016/1/1.
 */
public class SqlParamVO {
    /**sql语句中常采用的_命名方式*/
    private String sqlParam;
    /**model vo中常采用的驼峰式命名*/
    private String voParam;
    /**数据类型*/
    private String type;
    /**表字段注释*/
    private String comment;

    public String getSqlParam() {
        return sqlParam;
    }

    public void setSqlParam(String sqlParam) {
        this.sqlParam = sqlParam;
    }

    public String getVoParam() {
        return voParam;
    }

    public void setVoParam(String voParam) {
        this.voParam = voParam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /***
     * model.setUid(rs.getInt("uid"));
     * model.setNum1(rs.getInt("num1"));
     * @return
     */
    public String getQueryParse(){
        String paramStr = Cfg.getHeadUp(voParam);
        String typeStr = Cfg.getHeadUp(type);
      return "model.set"+paramStr+"(rs.get"+ typeStr+"(\""+ sqlParam +"\"));";
    }

    public String getSetParams(){
        String paramStr =Cfg.getHeadUp(voParam);
        String typeStr = Cfg.getHeadUp(type);

        return "ps.set"+typeStr+"(++index, model.get"+ paramStr +"());";
    }



}
