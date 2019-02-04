package com.bw.fit.component.form.mapper;

import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.form.entity.TForm;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 16:52
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Mapper
public interface FormMapper {

    /****
     * 增加一条表单记录
     * @param tForm
     * @throws RbackException
     */
    void insert(TForm tForm) throws RbackException;
}
