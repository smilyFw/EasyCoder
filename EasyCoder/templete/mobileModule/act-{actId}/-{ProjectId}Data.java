package com.happyelements.fortuna.lua.activity.act-{actId};

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.happyelements.fortuna.lua.activity.act-{actId}.config.*;
import com.happyelements.fortuna.lua.common.CommonSet;
import com.happyelements.fortuna.lua.common.config.Reward;
import com.happyelements.fortuna.lua.common.config.SalableReward;
import com.happyelements.fortuna.lua.common.config.SaleItem;
import com.happyelements.fortuna.lua.common.index.DistributedUtils;
import com.happyelements.fortuna.lua.common.index.IndexedUtils;
import com.happyelements.fortuna.lua.common.logic.RewardLogic;
import com.happyelements.fortuna.lua.common.logic.SaleLogic;
import com.happyelements.fortuna.lua.common.model.ActivityDataInfo;
import com.happyelements.fortuna.lua.common.util.ActivityUtils;
import com.happyelements.fortuna.lua.common.util.AssertUtils;
import com.happyelements.fortuna.lua.common.util.RandomUtil;
import com.happyelements.fortuna.lua.helper.DcHelper;
import net.sf.json.JSONArray;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class -{ProjectId}Data extends ActivityDataInfo {

-{params}
-{getAndSetMethods}

    public -{ProjectId}Data() {
        super();
    }

    public static -{ProjectId}Data fetch(long userId) {
        return fetch(userId, -{ProjectId}Config.ActivitySerialId, -{ProjectId}Data.class);
    }

    public JSONObject toClient() {

        JSONObject object = new JSONObject();
-{toClientParamSet}
        object.put("timeLeft", ActivityUtils.activityTimeLeft(-{ProjectId}Config.ActivitySerialId, curCalendar));
        return object;
    }

    @Override
    public -{ProjectId}Config config() {
        return -{ProjectId}Config.getInstance();
    }

    public void logAll(String desc) {
        this.log(desc, -{logParams});
    }
}
