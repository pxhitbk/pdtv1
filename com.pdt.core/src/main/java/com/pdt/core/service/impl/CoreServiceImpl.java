package com.pdt.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.pdt.core.dao.impl.GenericDaoImpl;
import com.pdt.core.model.BaseEntity;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.QueryCondition;

@Transactional(rollbackFor = { Exception.class })
public class CoreServiceImpl extends GenericDaoImpl implements CoreService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CoreServiceImpl.class);

	public <F extends BaseEntity, S extends BaseEntity> void deleteAssociation(
			F first, S second, Class<?> associationType) {
	}

	@Override
	public <T extends BaseEntity, R> List<R> findAllValueOfProperty(
			Class<T> entityType, Class<R> resultType, String propertyId) {
		List<R> results = null;
		try {
			entityType.getDeclaredField(propertyId); // just for validate field
			String query = "SELECT e." + propertyId + " FROM " + entityType.getName() + " e";
			results = getEntityManager().createQuery(query, resultType).getResultList();
		} catch (NoSuchFieldException e) {
			LOGGER.error(entityType.getName() + " doesn't contain field names " + propertyId, e);
		} catch (SecurityException e) {
			LOGGER.error(entityType.getName() + ", unsuccess find field " + propertyId, e);
		}
		return results;
	}

	@Override
	public <T extends BaseEntity> List<T> findByProperties(Class<T> entityType, List<QueryCondition> conditions) {
		List<T> results = null;
		if (conditions == null || conditions.isEmpty()) {
			LOGGER.warn("conditions is null or empty.");
			results = findAll(entityType);
		}
		StringBuilder query = new StringBuilder("SELECT e FROM ").append(entityType.getName()).append(" e WHERE ");
		try {
			for (int i = 0; i < conditions.size(); i++) {
				QueryCondition c = conditions.get(i);
				entityType.getDeclaredField(c.getProperty()); // just for validate field
				query.append("e." + c.getProperty()).append(c.getOperator().getOperatorString()).append(c.getValue());
				if (conditions.size() > 1 && i < conditions.size() - 1) {
					query.append(" AND ");
				}
			}
			results = getEntityManager().createQuery(query.toString(), entityType).getResultList();
		} catch (NoSuchFieldException e) {
			LOGGER.error(entityType.getName() + " invalid field name", e);
		} catch (SecurityException e) {
			LOGGER.error(entityType.getName() + ", unsuccess find a field", e);
		}
		return results;
	}

	@Override
	public <T extends BaseEntity> List<T> findByPropertiy(Class<T> entityType, QueryCondition condition) {
		List<T> results = null;
		if (condition == null) {
			LOGGER.warn("condition is null. Find all.");
			results = findAll(entityType);
		}
		StringBuilder query = new StringBuilder("SELECT e FROM ").append(entityType.getName()).append(" e WHERE ");
		try {
			entityType.getDeclaredField(condition.getProperty()); // just for validate field
			query.append(condition.getProperty()).append(condition.getOperator().getOperatorString())
					.append(condition.getValue());
			results = getEntityManager().createQuery(query.toString(), entityType).getResultList();
		} catch (NoSuchFieldException e) {
			LOGGER.error(entityType.getName() + " invalid field name", e);
		} catch (SecurityException e) {
			LOGGER.error(entityType.getName() + ", unsuccess find a field", e);
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.core.service.CoreService#insertOrUpdate(com.pdt.core.model.BaseEntity)
	 */
	@Override
	public <T extends BaseEntity> T insertOrUpdate(T entity) {
		T ret = null;
		if (entity != null) {
			if (entity.getId() == 0)
				ret = insert(entity);
			else
				ret = update(entity);
		}
		if (ret == null)
			LOGGER.warn("There's an unsuccess insert or update action for " + entity.getClass().getName()
					+ ", with id = " + entity.getId());
		return ret;
	}

}
