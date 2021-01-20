package com.jx.Dao;

import com.jx.Beans.user;
import org.apache.commons.collections.set.UnmodifiableSet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    void insertUser(user user);

    user SelectUserByName(String username);
}
