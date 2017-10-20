
package com.twofish.fishbowl.activity.-{projectId}.logic;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import com.twofish.fishbowl.activity.base.ActDataMgr;
import com.twofish.fishbowl.activity.-{projectId}.model.-{ProjectId}UserVO;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.apache.log4j.Logger;
import com.twofish.fishbowl.constEnum.ErrorCode;
import com.twofish.fishbowl.util.ActMetaMgr;
import com.happyelements.config.model.ActMeta;
import com.twofish.fishbowl.constEnum.CommonSet;
import com.twofish.fishbowl.logic.MoneyMgr;
import com.twofish.fishbowl.loginreward.logic.LoginMarkMgr;
import com.twofish.fishbowl.util.FunctionUtil;
import com.twofish.fishbowl.util.StringUtil;
import com.twofish.fishbowl.util.Time;
import com.twofish.fishbowl.model.ConsumeRecord;
import com.twofish.fishbowl.model.ConsumeRecordType;
-{head}
public enum -{ProjectId}Mgr {
    INSTANCE;

    public static final Logger logger = Logger.getLogger(-{ProjectId}Mgr.class);
    private static final String CODE = "code";
    private static final int CODE_SUCC = 1;

-{autoMethod}

/**
     * 清理帮助cd
     *
     * @param uid 玩家uid
     */
    public Map<String, Object> clearHelpCD(int uid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);
            checkNotInRewardTime(actMeta);

            -{ProjectId}UserVO userVO = getUser(uid);
            checkArgument(userVO.getHelpCDLeftTime() > 0, ErrorCode.ERROR_NEW_DAY);

            int priceClearCD = ActMetaMgr.getIntValue(actMeta, -{ProjectId}Cfg.VALUE_KEY_PRICE_CLEAR_HELP_C_D);
            checkArgument(MoneyMgr.isShellEnough(uid, priceClearCD), ErrorCode.ERROR_LACK_SHELL);

            userVO.setGetHelpRewardTime(0);
            updateUser(uid, userVO);

            int costShell = MoneyMgr.actConsumeShellAndDCAndReturnCost(uid, priceClearCD, ConsumeRecordType.-{projectId}_clear_cd,
                    1, ConsumeRecord.CURRENCY_GOLD, priceClearCD, actMeta.getDcId(), null, null);

            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO", userVO);
            resultMap.put("costShell", costShell);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        } catch (Exception e) {
            logger.error("-{ProjectId}Mgr.clearHelpCD()", e);
        }
        return resultMap;
    }

    /**
     * 购买好友帮助
     *
     * @param uid 玩家uid
     */
    public Map<String, Object> buyFriHelp(int uid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);

            checkNotInRewardTime(actMeta);

            -{ProjectId}UserVO user = getUser(uid);
            int needHelpNum = ActMetaMgr.getIntValue(actMeta, -{ProjectId}Cfg.VALUE_KEY_NEED_FRI_HELP_NUM);

            List<String> helpedList = FunctionUtil.strToList(user.getFriendHelpStr());
            int shouldBuyNum = needHelpNum - helpedList.size();
            checkState(shouldBuyNum > 0, "help is full");

            int priceBuyHelp = ActMetaMgr.getIntValue(actMeta, -{ProjectId}Cfg.VALUE_KEY_PRICE_BUY_HELP);
            int costShell = priceBuyHelp * shouldBuyNum;

            checkArgument(MoneyMgr.isShellEnough(uid, costShell), ErrorCode.ERROR_LACK_SHELL);

            for (int i = 0; i < shouldBuyNum; i++) {
                helpedList.add(CommonSet.SHELL_BUY_FRI_HELP_FLAG + "");
            }


            user.setFriendHelpStr(FunctionUtil.listToStr(helpedList));
            updateUser(uid, user);

            costShell = MoneyMgr.actConsumeShellAndDCAndReturnCost(uid, costShell, ConsumeRecordType.-{projectId}_buy_help, 1,
                    ConsumeRecord.CURRENCY_GOLD, priceBuyHelp, actMeta.getDcId(), null, null);

            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO", user);
            resultMap.put("costShell", costShell);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        } catch (Exception e) {
            logger.error("-{ProjectId}Mgr.buyFriHelp()", e);
        }
        return resultMap;
    }

    /**
     * 领取好友帮助获得的体力
     *
     * @param uid 玩家uid
     */
    public Map<String, Object> getHelpReward(int uid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(CODE, ErrorCode.ERROR_NOT_KNOWN);
        try {
            ActMeta actMeta = getActMeta();
            checkIsOpen(actMeta);

            -{ProjectId}UserVO user = getUser(uid);
            int needHelpNum = ActMetaMgr.getIntValue(actMeta, -{ProjectId}Cfg.VALUE_KEY_NEED_FRI_HELP_NUM);
            List<String> helpedList = FunctionUtil.strToList(user.getFriendHelpStr());
            checkState(helpedList.size() >= needHelpNum, "help not full");
            checkState(user.getHelpCDLeftTime() == 0, "in cd");

            int getEnergyFromHelp = ActMetaMgr.getIntValue(actMeta, -{ProjectId}Cfg.VALUE_KEY_NUM_GET_FROM_FRI_HELP);

            user.setEnergy(user.getEnergy() + getEnergyFromHelp);
            user.setGetHelpRewardTime((int) (LoginMarkMgr.getCurrentTime() / Time.SECOND));
            user.setFriendHelpStr("");
            updateUser(uid, user);

            resultMap.put(CODE, CODE_SUCC);
            resultMap.put("mainWindowVO", user);

        } catch (IllegalStateException e) {
            resultMap.put("msg", e.getMessage());
        } catch (IllegalArgumentException e) {
            resultMap.put(CODE, Integer.parseInt(e.getMessage()));
        } catch (Exception e) {
            logger.error("-{ProjectId}Mgr.getHelpReward()", e);
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
        return ActMetaMgr.getActMeta(-{ProjectId}Cfg.ACT_ID);}


   /**
    * 接受好友帮助
    *
    * @param receiverId
    * @param senderIds
    */
   public void receiveHelp(int receiverId, List<Integer> senderIds){
       if (ActMetaMgr.isOpen(-{ProjectId}Cfg.ACT_ID)) {
            ActMeta actMeta = getActMeta();
            if (ActMetaMgr.isOpen(actMeta)) {
                -{ProjectId}UserVO user = getUser(receiverId);
                int needFriHelpNum = ActMetaMgr.getIntValue(actMeta, -{ProjectId}Cfg.VALUE_KEY_NEED_FRI_HELP_NUM);
                List<Integer> helpedList = StringUtil.strToIntegerList(user.getFriendHelpStr());
                if (helpedList.size() < needFriHelpNum) {
                    for (int i = 0; (i < senderIds.size()) && (helpedList.size() < needFriHelpNum); i++) {
                        helpedList.add(senderIds.get(i));
                    }

                    user.setFriendHelpStr(FunctionUtil.listToStr(helpedList));
                    updateUser(receiverId, user);

                }
            }
        }
    }

   public -{ProjectId}UserVO getUser(int uid) {
        return ActDataMgr.INSTANCE.getActUserData(uid, -{ProjectId}Cfg.ACT_ID, -{ProjectId}UserVO.class);
   }

   public boolean updateUser(int uid, -{ProjectId}UserVO userVO) {
        return ActDataMgr.INSTANCE.updateActUserData(uid, -{ProjectId}Cfg.ACT_ID, userVO);
   }

}
