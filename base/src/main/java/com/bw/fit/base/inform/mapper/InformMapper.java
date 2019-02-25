package com.bw.fit.base.inform.mapper;

import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.inform.entity.TInform;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-25 17:22
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Mapper
public interface InformMapper {

    /****
     * 新增一条站内信
     * @param tInform
     * @throws RbackException
     */
    void insert(TInform tInform ) throws RbackException;
}
