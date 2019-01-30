package com.bw.fit.system.user.mapper;

import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.user.entity.TUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {

    /****
     * 获取用户列表
     * @param u
     * @return
     */
    public List<TUser> getUsers(TUser u);
    /***
     * 根据id获取用户资料
     * @param id
     * @return
     */
    public TUser get(String id);
    /***
     * 根据code获取用户资料
     * @param code
     * @return
     */
    public TUser getByCode(String code);
    /****
     * 新增
     * @param tu
     * @throws RbackException
     */
    public void insert(TUser tu) throws RbackException;
    public void update(TUser tu) throws RbackException;
    public void delete(String id) throws RbackException;
}
