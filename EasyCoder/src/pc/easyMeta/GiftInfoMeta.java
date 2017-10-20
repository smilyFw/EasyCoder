package pc.easyMeta;

/**
 * Created by wenwen.fu on 2016/3/22.
 */
//礼包信息
public class GiftInfoMeta {
    protected String id;
    protected String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getString(){
        return "//"+ desc + "\n" + "public static final int GIFT_" + id + " = " + id +"; \n";
    }
}
