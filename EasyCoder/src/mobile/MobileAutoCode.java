package mobile;

import mobile.config.MobileConfigInit;
import mobile.module.MobileModuleInit;
import mobile.param.MobileParamInit;
import mobile.protocol.MobileProtocolInit;

/**
 * Created by wenwen.fu on 2017/4/24.
 */
public class MobileAutoCode {
    public static void main(String[] args) {
        //协议名称配置
        String projectid = "FancyBall";
        String actId = "200377";
        MobileModuleInit.makeModuleDir(projectid, actId);
        MobileProtocolInit.auto(projectid, actId);
        MobileParamInit.auto(projectid, actId);
        MobileConfigInit.auto(projectid, actId);
    }
}
