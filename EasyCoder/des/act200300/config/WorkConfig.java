package com.happyelements.fortuna.lua.activity.act200300.config;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.happyelements.fortuna.lua.common.config.*;
import com.twofishes.config.manager.ConfigManager;
import com.twofishes.config.manager.resolver.ConfigPostProcessor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.Map;

@Root(name = "z_200300")
public class WorkConfig extends ActivityBaseConfig implements ConfigPostProcessor {

    public final static int ActivitySerialId = 200300;

    @Element
    private int reserve;// 为1，最后一天只能领奖

    @Element(required = false)
    private String lua; // 前端自定义配置

    @Element
    private int dailyFreeWheel;
    @Element
    private int firstDayDoubleProp;
    @Element
    private int firstDayStrengthenProp;
    @Element
    private int gameCostWheel;
    @ElementList(entry = "item")
    private List<Object> saleList;
    @ElementList(entry = "item")
    private List<Object> satisfactionRewardList;
    @ElementList(entry = "item")
    private List<Object> openBoxOilPaintNumPool;
    @ElementList(entry = "item")
    private List<Object> openBoxOilPaintTypePool;
    @ElementList(entry = "item")
    private List<Object> excFishList;
    @ElementList(entry = "item")
    private List<Object> excOtherList;
    @ElementList(entry = "item")
    private List<Object> gameSetList;


    private JSONObject cache;

    public static WorkConfig getInstance() {
        return ConfigManager.get(WorkConfig.class);
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

    public JSONObject json() {
        return cache;
    }



    @Override
    public void postProcessAfterInit() {
        cache = new JSONObject();
        cache.put("dailyFreeWheel", dailyFreeWheel);
        cache.put("firstDayDoubleProp", firstDayDoubleProp);
        cache.put("firstDayStrengthenProp", firstDayStrengthenProp);
        cache.put("gameCostWheel", gameCostWheel);
        cache.put("saleList", saleList);
        cache.put("satisfactionRewardList", satisfactionRewardList);
        cache.put("openBoxOilPaintNumPool", openBoxOilPaintNumPool);
        cache.put("openBoxOilPaintTypePool", openBoxOilPaintTypePool);
        cache.put("excFishList", excFishList);
        cache.put("excOtherList", excOtherList);
        cache.put("gameSetList", gameSetList);

        }

}
