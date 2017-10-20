package com.twofish.fishbowl.activity.easter.model;
import com.twofish.fishbowl.common.dao.BaseDaoModel;
import com.twofish.fishbowl.model.AmfSerializable;

/**
 * Copyright ©2009-2017www.happyelements.com, all rights reserved.
 * Create date: 2017-4-24 
 * wenwen.fu
 * 付文文
 * wenwen.fu@happyelements.com
 */

public class Fool2017UserVO extends BaseDaoModel implements java.io.Serializable, AmfSerializable{
	private static final long serialVersionUID = 1L;
    /**玩家uid*/
    private int uid;
    /**水滴*/
    private int waterNum;
    /**体力*/
    private int powerNum;
    /**障碍好友帮助数*/
    private int stopHelpedNum;
    /**被砸数量*/
    private int knockedNum;
    /**领取被砸成就奖励次数*/
    private int gotAchieveNum;
    /**起始索引值*/
    private int startIndex;
    /**当前位置索引值*/
    private int curIndex;
    /**画面能显示的最小索引值*/
    private int mapMinIndex;
    /**画面能显示的最大索引值*/
    private int mapMaxIndex;
    /**获得购鱼机会的保底索引值*/
    private String fishRandomStr;
    /**获得购鱼机会的保底索引值*/
    private int fishRandomIndex;
    /**掉落保底列表*/
    private String gameRandomStr;
    /**掉落保底索引值*/
    private int gameRandomIndex;
    /**领取好友帮助获得的体力的时间戳，单位秒*/
    private int getHelpRewardSecond;
    /**体力的好友帮助列表*/
    private String friendHelpStr;
    /**版本号*/
    private int version;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    public int getWaterNum() {
        return waterNum;
    }

    public void setWaterNum(int waterNum) {
        this.waterNum = waterNum;
    }
    public int getPowerNum() {
        return powerNum;
    }

    public void setPowerNum(int powerNum) {
        this.powerNum = powerNum;
    }
    public int getStopHelpedNum() {
        return stopHelpedNum;
    }

    public void setStopHelpedNum(int stopHelpedNum) {
        this.stopHelpedNum = stopHelpedNum;
    }
    public int getKnockedNum() {
        return knockedNum;
    }

    public void setKnockedNum(int knockedNum) {
        this.knockedNum = knockedNum;
    }
    public int getGotAchieveNum() {
        return gotAchieveNum;
    }

    public void setGotAchieveNum(int gotAchieveNum) {
        this.gotAchieveNum = gotAchieveNum;
    }
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    public int getCurIndex() {
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
    }
    public int getMapMinIndex() {
        return mapMinIndex;
    }

    public void setMapMinIndex(int mapMinIndex) {
        this.mapMinIndex = mapMinIndex;
    }
    public int getMapMaxIndex() {
        return mapMaxIndex;
    }

    public void setMapMaxIndex(int mapMaxIndex) {
        this.mapMaxIndex = mapMaxIndex;
    }
    public String getFishRandomStr() {
        return fishRandomStr;
    }

    public void setFishRandomStr(String fishRandomStr) {
        this.fishRandomStr = fishRandomStr;
    }
    public int getFishRandomIndex() {
        return fishRandomIndex;
    }

    public void setFishRandomIndex(int fishRandomIndex) {
        this.fishRandomIndex = fishRandomIndex;
    }
    public String getGameRandomStr() {
        return gameRandomStr;
    }

    public void setGameRandomStr(String gameRandomStr) {
        this.gameRandomStr = gameRandomStr;
    }
    public int getGameRandomIndex() {
        return gameRandomIndex;
    }

    public void setGameRandomIndex(int gameRandomIndex) {
        this.gameRandomIndex = gameRandomIndex;
    }
    public int getGetHelpRewardSecond() {
        return getHelpRewardSecond;
    }

    public void setGetHelpRewardSecond(int getHelpRewardSecond) {
        this.getHelpRewardSecond = getHelpRewardSecond;
    }
    public String getFriendHelpStr() {
        return friendHelpStr;
    }

    public void setFriendHelpStr(String friendHelpStr) {
        this.friendHelpStr = friendHelpStr;
    }
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
