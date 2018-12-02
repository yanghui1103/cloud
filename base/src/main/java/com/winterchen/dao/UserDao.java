package com.winterchen.dao;

import com.winterchen.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    int insert(UserDomain record);
}
