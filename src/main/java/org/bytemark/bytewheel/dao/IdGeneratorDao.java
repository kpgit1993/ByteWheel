package org.bytemark.bytewheel.dao;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.bytemark.bytewheel.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorDao {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public Long getCarCategoryId() {
		String q = "select category_seq.nextval from dual";
		EntityManager session = entityManagerFactory.createEntityManager();
		try {
			Long seq = ((BigInteger) session.createNativeQuery(q).getSingleResult()).longValue();
			LoggerUtil.info("Category Id seq = "+seq);
			return seq;
		} finally {
			if (session.isOpen()){
				session.close();
			}
		}
	}
}
