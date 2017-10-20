package com.happyelements.fortuna.lua.activity.act200300;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.happyelements.fortuna.lua.activity.act200300.config.*;
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

public class WorkData extends ActivityDataInfo {

    private transient final WorkConfig config;

    //版本号，每次更新，默认活动起始时间
    private int version
    //元宝价--购买一个好友帮助
    private int priceBuyHelp
    //需要好友帮助数量
    private int needFriHelpNum

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    public int getPriceBuyHelp() {
        return priceBuyHelp;
    }

    public void setPriceBuyHelp(int priceBuyHelp) {
        this.priceBuyHelp = priceBuyHelp;
    }
    public int getNeedFriHelpNum() {
        return needFriHelpNum;
    }

    public void setNeedFriHelpNum(int needFriHelpNum) {
        this.needFriHelpNum = needFriHelpNum;
    }


    public WorkData() {
        super();
        config = WorkConfig.getInstance();
    }

    public static WorkData fetch(long userId) {
        return fetch(userId, WorkConfig.ActivitySerialId, WorkData.class);
    }

    public JSONObject toClient() {

        JSONObject object = new JSONObject();

        object.put("priceBuyHelp",priceBuyHelp);
        object.put("needFriHelpNum",needFriHelpNum);
        object.put("timeLeft", ActivityUtils.activityTimeLeft(WorkConfig.ActivitySerialId, curCalendar));
        return object;
    }

    public void daily() {
        boolean isReserve = isReserveDay(config.getReserve(), WorkConfig.ActivitySerialId);
        if (isReserve) {
            return;
        }

        final boolean firstLogin = super.isFirstLogin();

        if (firstLogin || !hadTodayLogin()) {

            if (firstLogin) {


                resp.putData("firstLogin",CommonSet.YES);
            }

            if (!this.hadTodayLogin()) {

            }
            setLoginTimeToNow();

            save();

            logAll("每日首次登陆");

        }
    }


     /**
    *打开主面板
    *@param uid 玩家uid
    */
    public void getInfo() {

        resp.putData("mainWindowVO", null);
        resp.putData("meta", null);
        resp.putData("actLeftTime", null);
    }

   /**
    *兑换鱼
    *@param num 兑换鱼数量
    *@param uid 玩家uid
    */
    public void excFish(int num) {

        resp.putData("mainWindowVO", null);
        resp.putData("vos", null);
    }

   /**
    *买鱼
    *@param uid 玩家uid
    */
    public void buyFish() {

        resp.putData("mainWindowVO", null);
        resp.putData("vos", null);
        resp.putData("costShell", null);
        resp.putData("addButtonNum", null);
    }

   /**
    *送鱼
    *@param num 数量
    *@param msg 留言
    *@param fbId 好友fbId
    *@param sendInfo 好友选择器回调信息
    *@param uid 玩家uid
    */
    public void sendFish(int num, String msg, long fbId, String sendInfo) {

        resp.putData("mainWindowVO", null);
        resp.putData("costShell", null);
        resp.putData("addButtonNum", null);
    }

   /**
    *购买游戏机会
    *@param uid 玩家uid
    */
    public void buyGameNum() {

        resp.putData("mainWindowVO", null);
    }

   /**
    *下注
    *@param id 押注的蛋的id
    *@param uid 玩家uid
    */
    public void game(int id) {

        resp.putData("mainWindowVO", null);
        resp.putData("vos", null);
        resp.putData("success", null);
        resp.putData("egg1RunNum", null);
        resp.putData("egg2RunNum", null);
        resp.putData("egg3RunNum", null);
    }

   /**
    *清理帮助cd
    *@param uid 玩家uid
    */
    public void clearHelpCD() {

        resp.putData("mainWindowVO", null);
        resp.putData("costShell", null);
    }

   /**
    *购买好友帮助
    *@param uid 玩家uid
    */
    public void buyFriHelp() {

        resp.putData("mainWindowVO", null);
        resp.putData("costShell", null);
    }

   /**
    *领取好友帮助获得的纽扣
    *@param uid 玩家uid
    */
    public void getHelpReward() {

        resp.putData("mainWindowVO", null);
    }



    @Override
    public WorkConfig config() {
        return config;
    }


    public void logAll(String desc) {
        this.log(desc, version, priceBuyHelp, needFriHelpNum);
    }
}
