package com.pdt.core.service;

import java.util.List;

import com.pdt.core.dao.GenericDao;
import com.pdt.core.model.BaseEntity;
import com.pdt.core.util.QueryCondition;

/**
 * Interface define all common service
 *
 * @author hungpx
 *
 */
public interface CoreService extends GenericDao {

	/**
	 * Find all value of a field (property or column) of an entity.
	 * @param entityType
	 * @param resultType
	 * @param propertyId field name
	 * @param E entity object that contain property to find
	 * @param R return result type
	 * @return
	 */
	<E extends BaseEntity, R> List<R> findAllValueOfProperty(Class<E> entityType, Class<R> resultType, String propertyId);

	/**
	 * @param entityType
	 * @param conditions
	 * @return
	 */
	<T extends BaseEntity> List<T> findByProperties(Class<T> entityType, List<QueryCondition> conditions);

	/**
	 * @param entityType
	 * @param condition
	 * @return
	 */
	<T extends BaseEntity> List<T> findByPropertiy(Class<T> entityType, QueryCondition condition);

	/**
	 * Check the id of entity and decide insert or update to database.
	 * @param entity
	 * @return
	 */
	<T extends BaseEntity> T insertOrUpdate(T entity);

}
