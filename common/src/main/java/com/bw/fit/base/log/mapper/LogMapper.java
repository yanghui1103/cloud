package com.bw.fit.base.log.mapper;

import com.bw.fit.base.log.entity.TLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-26 15:45
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Mapper
public interface LogMapper {
    /*****
     * 记录日志
     * @param tLog
     */
    void insert(TLog tLog);

    /*****
     * 翻页日志
     * @param tLog
     * @return
     */
    List<TLog> selectAll(TLog tLog);
}
