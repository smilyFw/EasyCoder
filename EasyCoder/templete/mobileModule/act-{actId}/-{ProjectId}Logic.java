package com.happyelements.fortuna.lua.activity.act-{actId};

import com.happyelements.fortuna.lua.activity.act-{actId}.config.-{ProjectId}Config;
import com.happyelements.fortuna.lua.common.CommonSet;
import com.happyelements.fortuna.lua.lib.interfaces.IActivityResp;
import org.apache.log4j.Logger;

public class -{ProjectId}Logic {

    public  -{ProjectId}Config config() {return -{ProjectId}Config.getInstance();}

    public  boolean dailyNeedSave(-{ProjectId}Data data, IActivityResp resp, long userId) {
        boolean isReserve = -{ProjectId}Data.isReserveDay(config().getReserve(), -{ProjectId}Config.ActivitySerialId);
        if (isReserve) {
            return false;
        }

        boolean needSave = false;
        final boolean firstLogin = data.isFirstLogin();
        final boolean hadTodayLogin = data.hadTodayLogin();

        if (firstLogin || !hadTodayLogin) {

            if (firstLogin) {

                needSave = true;
                resp.putData("firstLogin", CommonSet.YES);
            }

            if (!hadTodayLogin) {
                 needSave = true;
            }
            data.setLoginTimeToNow();

            data.logAll("每日首次登陆");

        }
        return needSave;
    }
-{methods}
}
