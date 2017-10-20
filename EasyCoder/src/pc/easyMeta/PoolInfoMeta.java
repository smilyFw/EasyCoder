package pc.easyMeta;

/**
 * Created by wenwen.fu on 2017/8/7.
 */
public class PoolInfoMeta extends GiftInfoMeta{
    @Override
    public String getString(){
        return "//"+ desc + "\n" + "public static final int POOL_" + id + " = " + id +"; \n";
    }
}
