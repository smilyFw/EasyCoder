
package com.twofish.fishbowl.activity.easter.logic;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.apache.log4j.Logger;
import com.twofish.fishbowl.constEnum.ErrorCode;
import com.twofish.fishbowl.util.ActMetaMgr;
import com.happyelements.config.model.ActMeta;
/**
 * Copyright ©2009-2017www.happyelements.com, all rights reserved.
 * Create date: 2017-4-24 
 * wenwen.fu
 * 付文文
 * wenwen.fu@happyelements.com
 */

public enum EasterMgr {
    INSTANCE;

    public static final Logger logger = Logger.getLogger(EasterMgr.class);
    private static final String CODE = "code";
    private static final int CODE_SUCC = 1;

    /**
     *打开主面板
    *@param uid 玩家uid
    */
    public Map<String, Object> getInfo(int uid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
             //TODO
            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO",null);
            resultMap.put("meta",null);
            resultMap.put("actLeftTime",null);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        }catch (Exception e) {
            logger.error("EasterMgr.getInfo()",e);
        }
        return resultMap;
    }

    /**
     *兑换鱼
    *@param num 兑换鱼数量
    *@param uid 玩家uid
    */
    public Map<String, Object> excFish(int uid ,int num) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
             //TODO
            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO",null);
            resultMap.put("vos",null);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        }catch (Exception e) {
            logger.error("EasterMgr.excFish()",e);
        }
        return resultMap;
    }

    /**
     *买鱼
    *@param uid 玩家uid
    */
    public Map<String, Object> buyFish(int uid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
             //TODO
            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO",null);
            resultMap.put("vos",null);
            resultMap.put("costShell",null);
            resultMap.put("addButtonNum",null);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        }catch (Exception e) {
            logger.error("EasterMgr.buyFish()",e);
        }
        return resultMap;
    }

    /**
     *送鱼
    *@param num 数量
    *@param msg 留言
    *@param fbId 好友fbId
    *@param sendInfo 好友选择器回调信息
    *@param uid 玩家uid
    */
    public Map<String, Object> sendFish(int uid ,int num ,String msg ,long fbId ,String sendInfo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
             //TODO
            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO",null);
            resultMap.put("costShell",null);
            resultMap.put("addButtonNum",null);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        }catch (Exception e) {
            logger.error("EasterMgr.sendFish()",e);
        }
        return resultMap;
    }

    /**
     *购买游戏机会
    *@param uid 玩家uid
    */
    public Map<String, Object> buyGameNum(int uid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
             //TODO
            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO",null);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        }catch (Exception e) {
            logger.error("EasterMgr.buyGameNum()",e);
        }
        return resultMap;
    }

    /**
     *下注
    *@param id 押注的蛋的id
    *@param uid 玩家uid
    */
    public Map<String, Object> game(int uid ,int id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
             //TODO
            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO",null);
            resultMap.put("vos",null);
            resultMap.put("success",null);
            resultMap.put("egg1RunNum",null);
            resultMap.put("egg2RunNum",null);
            resultMap.put("egg3RunNum",null);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        }catch (Exception e) {
            logger.error("EasterMgr.game()",e);
        }
        return resultMap;
    }

    /**
     *清理帮助cd
    *@param uid 玩家uid
    */
    public Map<String, Object> clearHelpCD(int uid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
             //TODO
            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO",null);
            resultMap.put("costShell",null);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        }catch (Exception e) {
            logger.error("EasterMgr.clearHelpCD()",e);
        }
        return resultMap;
    }

    /**
     *购买好友帮助
    *@param uid 玩家uid
    */
    public Map<String, Object> buyFriHelp(int uid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
             //TODO
            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO",null);
            resultMap.put("costShell",null);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        }catch (Exception e) {
            logger.error("EasterMgr.buyFriHelp()",e);
        }
        return resultMap;
    }

    /**
     *领取好友帮助获得的纽扣
    *@param uid 玩家uid
    */
    public Map<String, Object> getHelpReward(int uid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
             //TODO
            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO",null);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        }catch (Exception e) {
            logger.error("EasterMgr.getHelpReward()",e);
        }
        return resultMap;
    }



    /**
     * 是否在活动期间
    *
    * @return
    */
    private void checkIsOpen(ActMeta actMeta) {
        checkArgument(ActMetaMgr.isOpen(actMeta), ErrorCode.ERROR_ACT_END);}

   /**
    * 在领奖阶段
    *
    * @return
    */
   public void checkNotInRewardTime(ActMeta actMeta) {
        checkArgument(!ActMetaMgr.isInRewardTime(actMeta), ErrorCode.ERROR_CODE_LAST_AWARD_TIME);}


   public void flush(int uid) {}

   public ActMeta getActMeta(){
        return ActMetaMgr.getActMeta(EasterCfg.ACT_ID);}


   /**
    * 接受好友帮助
    *
    * @param receiverId
    * @param senderIds
    */
   public void receiveHelp(int receiverId, List<Integer> senderIds){
        if (ActMetaMgr.isOpen(EasterCfg.ACT_ID)) {

        }
    }



}
