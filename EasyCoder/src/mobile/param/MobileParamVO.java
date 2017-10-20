package mobile.param;

/**
 * Created by wenwen.fu on 2017/4/24.
 */
public class MobileParamVO {
    private String type;
    private String name;
    private int backOnly;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackOnly() {
        return backOnly;
    }

    public void setBackOnly(int backOnly) {
        this.backOnly = backOnly;
    }

    public MobileParamVO() {
    }

    public String getInit() {
        return "    /**" + desc + "*/\n    " + "private " + type + " " + name + ";\n";
    }
}
