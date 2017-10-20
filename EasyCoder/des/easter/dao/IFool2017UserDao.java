package com.twofish.fishbowl.activity.easter.dao;

import com.twofish.fishbowl.activity.easter.model.Fool2017UserVO;
import java.util.Date;

/**
 * Copyright ©2009-2017www.happyelements.com, all rights reserved.
 * Create date: 2017-4-24 
 * wenwen.fu
 * 付文文
 * wenwen.fu@happyelements.com
 */

public interface IFool2017UserDao {

    public boolean insert(Fool2017UserVO model);

    public Fool2017UserVO query(int uid);

    public boolean update(Fool2017UserVO model);

    public void delete(int uid);
}
