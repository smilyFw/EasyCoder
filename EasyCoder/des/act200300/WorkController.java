package com.happyelements.fortuna.lua.activity.act200300;

import com.happyelements.fortuna.lua.activity.act200300.model.WorkData;
import com.happyelements.fortuna.lua.common.config.annotation.ControllerParams;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.happyelements.fortuna.lua.common.util.ResponseUtils;
import com.happyelements.fortuna.lua.lib.interfaces.IActivityControler;
import com.happyelements.fortuna.lua.lib.interfaces.IActivityReq;
import com.happyelements.fortuna.lua.lib.interfaces.IActivityResp;
/**
 * Copyright ©2009-2017www.happyelements.com, all rights reserved.
 * Create date: 2017-4-25 
 * wenwen.fu
 * 付文文
 * wenwen.fu@happyelements.com
 */

public class WorkController implements IActivityControler {

    private static final Logger LOG = Logger.getLogger(WorkController.class);

    @Override
    public void actInfo(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;
        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.daily();

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
            ResponseUtils.setConfig(resp, data.config().json());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.actInfo:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }

    /**
    *打开主面板
    */
    
    public void getInfo(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;

        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.getInfo();

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.startGame:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }

  /**
    *兑换鱼
    *@param num 兑换鱼数量
    */
    
    @ControllerParams({"num"})
    public void excFish(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;

        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.excFish(req.getInt("num"));

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.startGame:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }

  /**
    *买鱼
    */
    
    public void buyFish(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;

        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.buyFish();

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.startGame:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }

  /**
    *送鱼
    *@param num 数量
    *@param msg 留言
    *@param fbId 好友fbId
    *@param sendInfo 好友选择器回调信息
    */
    
    @ControllerParams({"num"})
    @ControllerParams({"msg"})
    @ControllerParams({"fbId"})
    @ControllerParams({"sendInfo"})
    public void sendFish(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;

        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.sendFish(req.getInt("num"),req.getString("msg"),req.getLong("fbId"),req.getString("sendInfo"));

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.startGame:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }

  /**
    *购买游戏机会
    */
    
    public void buyGameNum(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;

        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.buyGameNum();

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.startGame:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }

  /**
    *下注
    *@param id 押注的蛋的id
    */
    
    @ControllerParams({"id"})
    public void game(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;

        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.game(req.getInt("id"));

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.startGame:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }

  /**
    *清理帮助cd
    */
    
    public void clearHelpCD(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;

        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.clearHelpCD();

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.startGame:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }

  /**
    *购买好友帮助
    */
    
    public void buyFriHelp(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;

        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.buyFriHelp();

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.startGame:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }

  /**
    *领取好友帮助获得的纽扣
    */
    
    public void getHelpReward(long userId, IActivityReq req, IActivityResp resp) {
        WorkData data = null;

        try {
            ResponseUtils.setFailed(resp);

            data = WorkData.fetch(userId);
            data.setContext(req, resp);
            data.getHelpReward();

            ResponseUtils.setSuccess(resp);
            ResponseUtils.setUserData(resp, data.toClient());
        } catch (Exception e) {
            String dataStr = data != null ? ToStringBuilder.reflectionToString(data) : "data is null";
            LOG.error("WorkController.startGame:" + e.getMessage() + ":" + dataStr, e);
            resp.putData("error", e.getMessage());
        }
    }


}
