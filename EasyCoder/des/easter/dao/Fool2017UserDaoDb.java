package com.twofish.fishbowl.activity.easter.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.twofish.fishbowl.util.CacheUtil;
import org.apache.log4j.Logger;

import com.google.inject.Singleton;
import com.twofish.core.dao.OpUniq;
import com.twofish.core.dao.OpUpdate;
import com.twofish.fishbowl.activity.easter.model.Fool2017UserVO;
import com.twofish.fishbowl.dao.db.BaseDao;
import com.twofish.fishbowl.util.MemcacheConstant;
import com.twofish.util.Time;

/**
 * Copyright ©2009-2017www.happyelements.com, all rights reserved.
 * Create date: 2017-4-24 
 * wenwen.fu
 * 付文文
 * wenwen.fu@happyelements.com
 */

@Singleton
public class Fool2017UserDaoDb extends BaseDao implements IFool2017UserDao{

    private final static Logger logger = Logger.getLogger(Fool2017UserDaoDb.class);

    private static final String CACHE_PREFIX = "fool2017_user_";

    private static final String DB_INSTANCE_NAME = "fishfish2";

    private static final String DB_TABLE_NAME = "fool2017_user";

    private String getKey(Object cacheBy) {
        return MemcacheConstant.KEY_PREFIX + CACHE_PREFIX + cacheBy;
    }

    private String getTableName(Integer shardBy) {
        return shardBy != null ? DB_TABLE_NAME + "_" + Integer.valueOf(shardBy % 100).toString() : DB_TABLE_NAME;
    }

    @Override
    public Fool2017UserVO query(final int uid){
        Object obj = CacheUtil.getCache(getKey(uid));
        Fool2017UserVO r = (Fool2017UserVO) obj;
        if (r != null) {
            return r;
        }
        String sqlStr = "select * from " + getTableName(null) + " where uid=?";
        OpUniq op = new OpUniq(sqlStr, DB_INSTANCE_NAME) {
            @Override
            public void setParam(PreparedStatement ps) throws SQLException {
                ps.setInt(1, uid);
            }
            public Object parse(ResultSet rs) throws SQLException {
                try {
                    Fool2017UserVO model = new Fool2017UserVO();
                    model.setUid(rs.getInt("uid"));
                    model.setWaterNum(rs.getInt("water_num"));
                    model.setPowerNum(rs.getInt("power_num"));
                    model.setStopHelpedNum(rs.getInt("stop_helped_num"));
                    model.setKnockedNum(rs.getInt("knocked_num"));
                    model.setGotAchieveNum(rs.getInt("got_achieve_num"));
                    model.setStartIndex(rs.getInt("start_index"));
                    model.setCurIndex(rs.getInt("cur_index"));
                    model.setMapMinIndex(rs.getInt("map_min_index"));
                    model.setMapMaxIndex(rs.getInt("map_max_index"));
                    model.setFishRandomStr(rs.getString("fish_random_str"));
                    model.setFishRandomIndex(rs.getInt("fish_random_index"));
                    model.setGameRandomStr(rs.getString("game_random_str"));
                    model.setGameRandomIndex(rs.getInt("game_random_index"));
                    model.setGetHelpRewardSecond(rs.getInt("get_help_reward_second"));
                    model.setFriendHelpStr(rs.getString("friend_help_str"));
                    model.setVersion(rs.getInt("version"));

                    return model;
                } catch (Exception e) {
                    logger.error("query Fool2017UserVO from DB error.", e);
                }
                return null;
            }
        };
        r = (Fool2017UserVO) super.queryUnique(op);
        if (r != null) {
            CacheUtil.setCache(getKey(uid), r);
        }
        return r;
    }


    @Override
    public boolean insert(final Fool2017UserVO model){
        String sqlStr = "insert into " + getTableName(null) + " (uid,water_num,power_num,stop_helped_num,knocked_num,got_achieve_num,start_index,cur_index,map_min_index,map_max_index,fish_random_str,fish_random_index,game_random_str,game_random_index,get_help_reward_second,friend_help_str,version) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        OpUpdate op = new OpUpdate(sqlStr, DB_INSTANCE_NAME) {
            @Override
            public void setParam(PreparedStatement ps) throws SQLException {
                int index = 0; 
                ps.setInt(++index, model.getUid());
                ps.setInt(++index, model.getWaterNum());
                ps.setInt(++index, model.getPowerNum());
                ps.setInt(++index, model.getStopHelpedNum());
                ps.setInt(++index, model.getKnockedNum());
                ps.setInt(++index, model.getGotAchieveNum());
                ps.setInt(++index, model.getStartIndex());
                ps.setInt(++index, model.getCurIndex());
                ps.setInt(++index, model.getMapMinIndex());
                ps.setInt(++index, model.getMapMaxIndex());
                ps.setString(++index, model.getFishRandomStr());
                ps.setInt(++index, model.getFishRandomIndex());
                ps.setString(++index, model.getGameRandomStr());
                ps.setInt(++index, model.getGameRandomIndex());
                ps.setInt(++index, model.getGetHelpRewardSecond());
                ps.setString(++index, model.getFriendHelpStr());
                ps.setInt(++index, model.getVersion());

            }
        };
        boolean r = super.insertReturnId(op) > 0;
        if(r){
           CacheUtil.setCache(getKey(model.getUid()),model);
        }
        return r;
    }

    @Override
    public boolean update(final Fool2017UserVO model){
        String sqlStr = "update " + getTableName(null) + " set uid = ?,water_num = ?,power_num = ?,stop_helped_num = ?,knocked_num = ?,got_achieve_num = ?,start_index = ?,cur_index = ?,map_min_index = ?,map_max_index = ?,fish_random_str = ?,fish_random_index = ?,game_random_str = ?,game_random_index = ?,get_help_reward_second = ?,friend_help_str = ?,version = ? where uid=?";
        OpUpdate op = new OpUpdate(sqlStr, DB_INSTANCE_NAME) {
            @Override
            public void setParam(PreparedStatement ps) throws SQLException {
                int index = 0; 
                ps.setInt(++index, model.getUid());
                ps.setInt(++index, model.getWaterNum());
                ps.setInt(++index, model.getPowerNum());
                ps.setInt(++index, model.getStopHelpedNum());
                ps.setInt(++index, model.getKnockedNum());
                ps.setInt(++index, model.getGotAchieveNum());
                ps.setInt(++index, model.getStartIndex());
                ps.setInt(++index, model.getCurIndex());
                ps.setInt(++index, model.getMapMinIndex());
                ps.setInt(++index, model.getMapMaxIndex());
                ps.setString(++index, model.getFishRandomStr());
                ps.setInt(++index, model.getFishRandomIndex());
                ps.setString(++index, model.getGameRandomStr());
                ps.setInt(++index, model.getGameRandomIndex());
                ps.setInt(++index, model.getGetHelpRewardSecond());
                ps.setString(++index, model.getFriendHelpStr());
                ps.setInt(++index, model.getVersion());
                ps.setInt(++index, model.getUid());
            }
        };
        boolean r = super.update(op) > 0;
        if(r){
            CacheUtil.setCache(getKey(model.getUid()), model);
        }
        return r;
    }

    @Override
    public void delete(final int uid){
        String sqlStr = "delete from " + getTableName(null) + " where uid=?";
        OpUpdate op = new OpUpdate(sqlStr, DB_INSTANCE_NAME) {
            @Override
            public void setParam(PreparedStatement ps) throws SQLException {
                ps.setInt(1, uid);
            }
        };
        super.update(op);
        CacheUtil.deleteCache(getKey(uid));
    }

}
