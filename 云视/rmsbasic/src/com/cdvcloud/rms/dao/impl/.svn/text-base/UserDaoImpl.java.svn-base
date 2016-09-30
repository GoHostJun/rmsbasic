package com.cdvcloud.rms.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.IUserDao;
import com.cdvcloud.rms.domain.User;
@Repository
public class UserDaoImpl implements IUserDao {
	@Autowired
	private BasicDao basicDao;

	@Override
	public Map<String, Object> findOne(Map<String, Object> filter) {
		return basicDao.findOne(User.COLLECTION, filter);
	}

	@Override
	public Map<String, Object> findOne(String id) {
		return basicDao.findOne(User.COLLECTION, id);
	}

	@Override
	public long updateUserByset(Map<String, Object> filter, Map<String, Object> update) {
		return basicDao.updateOneBySet(User.COLLECTION, filter, update,true);
	}


	
	
}
