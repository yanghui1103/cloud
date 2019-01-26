package com.bw.fit.component.flow.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper {
    int gets(int arg);
}
