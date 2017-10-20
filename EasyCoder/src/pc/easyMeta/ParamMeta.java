package pc.easyMeta;

import pc.Cfg;

/**
 * Created by wenwen.fu on 2016/3/22.
 */
//参数信息
public class ParamMeta {
    //参数名
    private String name;
    //参数值
    private String value;
    //参数描述
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getString(){
        return "//"+ desc + "\n" + "public static final String VALUE_KEY_" + Cfg.getConst(name) + " = \"" + name +"\"; \n";
    }
}
