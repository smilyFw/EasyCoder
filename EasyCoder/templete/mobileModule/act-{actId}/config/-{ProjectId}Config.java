package com.happyelements.fortuna.lua.activity.act-{actId}.config;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.happyelements.fortuna.lua.common.config.*;
import com.twofishes.config.manager.ConfigManager;
import com.twofishes.config.manager.resolver.ConfigPostProcessor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Root(name = "z_-{actId}")
public class -{ProjectId}Config extends ActivityBaseConfig implements ConfigPostProcessor {

    public final static int ActivitySerialId = -{actId};

    @Element
    private int reserve;// 为1，最后一天只能领奖

    @Element(required = false)
    private String lua; // 前端自定义配置

-{params}
    private JSONObject cache;

    public static -{ProjectId}Config getInstance() {
        return ConfigManager.get(-{ProjectId}Config.class);
    }


    @Override
    public int getReserve() {
        return reserve;
    }

    public String getLua() {
        return lua;
    }


    public String getDcId() {
        return dcId;
    }



    @Override
    public void postProcessAfterInit() {
        Map cache = new HashMap();
        cache.put("reserve", reserve);
        cache.put("lua", lua);
-{putInCache}
        super.setJson(JSON.toJSONString(cache));
        }

}
