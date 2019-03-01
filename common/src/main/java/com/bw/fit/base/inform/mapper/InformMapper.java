package com.bw.fit.base.inform.mapper;

import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.inform.entity.TInform;
import com.bw.fit.base.inform.model.Inform;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
    public void insert(TInform tInform) throws RbackException;

    /*****
     * 获取一个站内信
     * @param id
     * @return
     */
    public TInform get(String id);

    /*****
     * 发给某个账号站内信
     * @param accountId
     * @return
     */
    public List<TInform> selectByAccount(String accountId);

    /****
     * 站内信记录
     * @param tInform
     * @return
     */
    List<TInform> getInnerMsgs(Inform tInform);

}
