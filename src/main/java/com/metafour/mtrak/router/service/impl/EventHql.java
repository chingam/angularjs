package com.metafour.mtrak.router.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
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
public class EventHql {

	@PersistenceContext
	EntityManager entityManager;


	protected Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	@Transactional(readOnly=true)
	public void updateDragAndDrop(String code, Integer id) {
		Session session = getCurrentSession();
		String sql="UPDATE com.metafour.mtrak.router.entities.EventLog a SET a.id="+id+" WHERE a.code='"+code+"'";
		Query query=session.createQuery(sql);
		query.executeUpdate();
	}
	
}
