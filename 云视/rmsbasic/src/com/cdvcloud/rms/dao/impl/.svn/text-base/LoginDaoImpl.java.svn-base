package com.cdvcloud.rms.dao.impl;

import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.cdvcloud.rms.dao.ILoginDao;
import com.cdvcloud.rms.domain.Global;
@Repository
public class LoginDaoImpl extends BasicDao implements ILoginDao {

	@Override
	public Document findOneDocument(Map<String, Object> filter) {
		return super.findOneDocument(Global.GLOBAL, filter);
	}

}
