package com.bw.fit.system.user.service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.user.entity.TUser;
import com.bw.fit.system.user.model.User;
import com.github.pagehelper.Page;

public interface UserService {

    /***
     * 新增用户
     * @param user
     * @return
     * @throws RbackException
     */
    public JSONObject add(User user) throws RbackException;
    /***
     * 删除
     * @param id
     * @return
     * @throws RbackException
     */
    public JSONObject delete(String id) throws RbackException ;
    /****
     * 获取单个用户资料
     * @param id
     * @return
     */
    public User get(String id);

    public Page<TUser> all(User user);

    public User getByCode(String code);

    /****
     * 新增用户通过sap接口
     * @param user 用户信息
     * @param account 账户信息
     * @param accountId 账户id
     * @param positionId 岗位id
     * @param orgId 组织id
     * @throws RbackException
     */
    public void createUserBySAP(TUser user, Account account, String accountId, String positionId, String orgId) throws RbackException ;
}
