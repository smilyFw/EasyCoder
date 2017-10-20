package com.twofish.fishbowl.request.logic.core;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.twofish.fishbowl.activity.easter.logic.EasterMgr;
import com.twofish.fishbowl.request.model.FishbowlMsg;
import com.twofish.fishbowl.request.model.MsgDataSource;

public class EasterMsgLogic extends MsgLogic {
	@Override
	Map<String, Object> doBatchHandleReceive(int type, int receiverId, List<FishbowlMsg> msgs) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("code", SUCC_CODE);
		List<Integer> friendUids = Lists.newArrayList();
		if (msgs == null || msgs.size() == 0) {
			return result;
		}

		for (FishbowlMsg msg : msgs) {
			if (msg != null) {
				friendUids.add(msg.getSenderId());
			}
		}

		EasterMgr.INSTANCE.receiveHelp(receiverId, friendUids);
		return result;
	}

	@Override
	public MsgDataSource getMsgDataSource() {
		return MsgDataSource.DB;
	}

	@Override
	public int getType() {
		return FishbowlMsg.PEONY_HELP;
	}
}
