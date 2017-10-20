
package com.twofish.fishbowl.activity.easter.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.twofish.fishbowl.action.base.BaseDispatchAction;
import com.twofish.fishbowl.util.Amf3Util;
import com.twofish.fishbowl.util.Amf3UtilWrapper;
import com.twofish.fishbowl.activity.easter.logic.EasterMgr;

/**
 * Copyright ©2009-2017www.happyelements.com, all rights reserved.
 * Create date: 2017-4-24 
 * wenwen.fu
 * 付文文
 * wenwen.fu@happyelements.com
 */

public class EasterAction extends BaseDispatchAction {
    /**
     *打开主面板
    */
    public ActionForward getInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = (Integer) request.getAttribute("hostid");

        Map<String, Object> resultMap = EasterMgr.INSTANCE.getInfo(hostId);
        Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("rt", Amf3Util.serializeObject(resultMap));
        Amf3UtilWrapper.writeFlashStream(response, rt);
        return null;
    }

    /**
     *兑换鱼
    */
    public ActionForward excFish(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = (Integer) request.getAttribute("hostid");
        int num =Integer.parseInt(request.getParameter("num"));

        Map<String, Object> resultMap = EasterMgr.INSTANCE.excFish(hostId ,num);
        Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("rt", Amf3Util.serializeObject(resultMap));
        Amf3UtilWrapper.writeFlashStream(response, rt);
        return null;
    }

    /**
     *买鱼
    */
    public ActionForward buyFish(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = (Integer) request.getAttribute("hostid");

        Map<String, Object> resultMap = EasterMgr.INSTANCE.buyFish(hostId);
        Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("rt", Amf3Util.serializeObject(resultMap));
        Amf3UtilWrapper.writeFlashStream(response, rt);
        return null;
    }

    /**
     *送鱼
    */
    public ActionForward sendFish(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = (Integer) request.getAttribute("hostid");
        int num =Integer.parseInt(request.getParameter("num"));
        String msg =request.getParameter("msg");
        long fbId =Long.parseLong(request.getParameter("fbId"));
        String sendInfo =request.getParameter("sendInfo");

        Map<String, Object> resultMap = EasterMgr.INSTANCE.sendFish(hostId ,num ,msg ,fbId ,sendInfo);
        Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("rt", Amf3Util.serializeObject(resultMap));
        Amf3UtilWrapper.writeFlashStream(response, rt);
        return null;
    }

    /**
     *购买游戏机会
    */
    public ActionForward buyGameNum(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = (Integer) request.getAttribute("hostid");

        Map<String, Object> resultMap = EasterMgr.INSTANCE.buyGameNum(hostId);
        Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("rt", Amf3Util.serializeObject(resultMap));
        Amf3UtilWrapper.writeFlashStream(response, rt);
        return null;
    }

    /**
     *下注
    */
    public ActionForward game(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = (Integer) request.getAttribute("hostid");
        int id =Integer.parseInt(request.getParameter("id"));

        Map<String, Object> resultMap = EasterMgr.INSTANCE.game(hostId ,id);
        Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("rt", Amf3Util.serializeObject(resultMap));
        Amf3UtilWrapper.writeFlashStream(response, rt);
        return null;
    }

    /**
     *清理帮助cd
    */
    public ActionForward clearHelpCD(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = (Integer) request.getAttribute("hostid");

        Map<String, Object> resultMap = EasterMgr.INSTANCE.clearHelpCD(hostId);
        Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("rt", Amf3Util.serializeObject(resultMap));
        Amf3UtilWrapper.writeFlashStream(response, rt);
        return null;
    }

    /**
     *购买好友帮助
    */
    public ActionForward buyFriHelp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = (Integer) request.getAttribute("hostid");

        Map<String, Object> resultMap = EasterMgr.INSTANCE.buyFriHelp(hostId);
        Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("rt", Amf3Util.serializeObject(resultMap));
        Amf3UtilWrapper.writeFlashStream(response, rt);
        return null;
    }

    /**
     *领取好友帮助获得的纽扣
    */
    public ActionForward getHelpReward(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        int hostId = (Integer) request.getAttribute("hostid");

        Map<String, Object> resultMap = EasterMgr.INSTANCE.getHelpReward(hostId);
        Map<String, Object> rt = new HashMap<String, Object>();
        rt.put("rt", Amf3Util.serializeObject(resultMap));
        Amf3UtilWrapper.writeFlashStream(response, rt);
        return null;
    }


}
