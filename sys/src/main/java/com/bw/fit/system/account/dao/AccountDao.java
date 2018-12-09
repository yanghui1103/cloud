package com.bw.fit.system.account.dao;

import com.bw.fit.system.account.entity.TAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 19:09
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Mapper
public interface AccountDao {

    TAccount getByLogName(TAccount tAccount);
}
