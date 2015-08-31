package com.pdt.core.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.pdt.core.dao.GenericDao;
import com.pdt.core.model.BaseEntity;

/**
 *
 * <p>
 * Simple implemented generic DAO for CRUD
 * </p>
 *
 * @author hungpx
 */
@Transactional(rollbackFor = { Exception.class })
public class GenericDaoImpl implements GenericDao {
	@PersistenceContext(unitName = "pdt-webapp")
	private EntityManager entityManager;

	public GenericDaoImpl() {
	}

	@Override
	public <T extends BaseEntity> T findById(Class<T> type, long id) {
		return entityManager.find(type, id);
	}

	@Override
	public <T extends BaseEntity> List<T> findAll(Class<T> type) {
		return entityManager.createQuery("SELECT e from " + type.getName() + " e", type).getResultList();
	}

	@Override
	public <T extends BaseEntity> T insert(T entity) {
		entity.setCreatedDate(Calendar.getInstance().getTime());
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public <T extends BaseEntity> void remove(T entity) {
		if (entity != null && entity.getId() != null && !entityManager.contains(entity))
			entity = entityManager.merge(entity);
		entityManager.remove(entity);
	}

	@Override
	public <T extends BaseEntity> T update(T entity) {
		return entityManager.merge(entity);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public <T extends BaseEntity> List<T> insert(List<T> entities) {
		List<T> result = new ArrayList<>();
		for (T entity : entities) {
			result.add(insert(entity));
		}
		return result;
	}

	@Override
	public <T extends BaseEntity> List<Long> findAllIds(Class<T> type) {
		return entityManager.createQuery("SELECT e.id FROM " + type.getName() + " e", Long.class).getResultList();
	}

	@Override
	public <T extends BaseEntity> int deleteById(Class<T> type, Long id) {
		return entityManager.createQuery(String.format("DELETE FROM %s e WHERE e.id = :id", type.getName()))
				.setParameter("id", id).executeUpdate();
	}
}
