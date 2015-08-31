package com.pdt.core.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.pdt.core.model.BaseEntity;

/**
 *
 * <p>
 * Interface provides all generic method for select or manipulate base entities
 * <p>
 *
 * @author hungpx
 *
 * @version 0.1
 *
 */
public interface GenericDao {

	<T extends BaseEntity> T findById(Class<T> type, long id);

	<T extends BaseEntity> List<T> findAll(Class<T> type);

	<T extends BaseEntity> T insert(T entity);

	<T extends BaseEntity> List<T> insert(final List<T> entities);

	<T extends BaseEntity> void remove(T entity);

	<T extends BaseEntity> T update(T entity);

	/**
	 * find all id of model
	 *
	 * @param type
	 *            class type of entity to find ids list
	 * @return list of ids
	 */
	<T extends BaseEntity> List<Long> findAllIds(Class<T> type);

	EntityManager getEntityManager();

	/**
	 * @param type
	 * @param id
	 * @return
	 */
	<T extends BaseEntity> int deleteById(Class<T> type, Long id);
}
