package com.metafour.mtrak.router.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author    Minhajul Sarkar<polash.pbt@gmail.com>
 * @version   1.0.00
 * @since     1.0.00
 * 
 */
@Repository
public class CommentHql {

	@PersistenceContext
	EntityManager entityManager;


	protected Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	@Transactional(readOnly=true)
	public void updateDragAndDrop(String code, Integer id) {
		Session session = getCurrentSession();
		String sql="UPDATE EventLog SET id="+id+" WHERE code="+code;
		session.createSQLQuery(sql);
	}
	
}
