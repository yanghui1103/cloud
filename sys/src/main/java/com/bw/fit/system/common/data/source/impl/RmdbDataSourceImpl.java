package com.bw.fit.system.common.data.source.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bw.fit.system.common.data.source.RmdbDataSource;
import com.bw.fit.system.common.model.RbackException;

@Component(value = "rmdbDataSource")
public class RmdbDataSourceImpl implements RmdbDataSource {

	@Autowired
	private JdbcTemplate jdbcTemplete;

	@Override
	public void insert(String sql, Object object) throws RbackException {

	}

	@Override
	public void update(String sql, Object object) throws RbackException {

	}

	@Override
	public void delete(String sql, Object object) throws RbackException {

	}

	@Override
	public List getListData(String sql, Object object) {
		return null;
	}

	@Override
	public Object getOneData(String sql, Object object) {
		return null;
	}
}
