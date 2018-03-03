/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bueno.common.jpa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

public abstract class GenericDAO<T extends GenericEntity> {

	private Class<T> type;

	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		Type t = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) t;
		type = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	// --------------------------------------------------------------------------------------------------------------

	public T lookup(Long id) {
		return entityManager.find(type, id);
	}

	// --------------------------------------------------------------------------------------------------------------

	public void save(T instancia) {
		entityManager.persist(instancia);
	}

	// --------------------------------------------------------------------------------------------------------------

	public void update(T instancia) {
		entityManager.merge(instancia);
	}

	public void updateBatch(List<T> instancia) {
		for (T object : instancia)
			entityManager.merge(object);
	}

	public void updateNamedQuery(final String name, List<Map.Entry<String, Object>> params) {
		Query query = entityManager.createNamedQuery(name);
		for (Map.Entry<String, Object> param : params) {
			query.setParameter(param.getKey(), param.getValue());
		}
		query.executeUpdate();
	}

	public int executeUpdateByNamedQuery(final String name, List<Map.Entry<String, Object>> params) {
		Query query = entityManager.createNamedQuery(name);

		if (params != null) {
			for (Map.Entry<String, Object> param : params) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}

		return query.executeUpdate();
	}

	// --------------------------------------------------------------------------------------------------------------

	public void delete(T instancia) {
		entityManager.remove(instancia);
	}

	public void deleteById(Long id) {
		final T instancia = this.lookup(id);
		entityManager.remove(instancia);
	}

	// --------------------------------------------------------------------------------------------------------------

	public List<T> getAll() {
		return entityManager.createQuery("FROM ".concat(type.getSimpleName().concat(" ORDER BY id")), type).getResultList();
	}

	// --------------------------------------------------------------------------------------------------------------

	public <E> List<E> findByNamedQuery(final String name, Class<E> returnType) {
		TypedQuery<E> query = entityManager.createNamedQuery(name, returnType);
		return query.getResultList();
	}

	// --------------------------------------------------------------------------------------------------------------

	public List<T> findByNamedQuery(final String name) {
		TypedQuery<T> query = entityManager.createNamedQuery(name, type);
		return query.getResultList();
	}

	public List<T> findByNamedQuery(final String name, List<Map.Entry<String, Object>> params) {
		TypedQuery<T> query = entityManager.createNamedQuery(name, type);
		if (params != null) {
			for (Map.Entry<String, Object> param : params) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		return query.getResultList();
	}

	public List<?> findByNamedQueryNotTyped(final String name) {
		Query query = entityManager.createNamedQuery(name);
		return query.getResultList();
	}

	public List<?> findByNamedQueryNotTyped(final String name, List<Map.Entry<String, Object>> params) {
		Query query = entityManager.createNamedQuery(name);
		if (params != null) {
			for (Map.Entry<String, Object> param : params) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		return query.getResultList();
	}

	public List<T> findByNamedQuery(final String name, List<Map.Entry<String, Object>> params, int maxResult) {
		TypedQuery<T> query = entityManager.createNamedQuery(name, type);
		query.setMaxResults(maxResult);

		if (params != null) {
			for (Map.Entry<String, Object> param : params) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}

		return query.getResultList();
	}

	public List<T> findByNamedQuery(final String name, String paramName, Object paramValue) {
		List<Map.Entry<String, Object>> params = new ArrayList<>();
		params.add(new SimpleEntry<String, Object>(paramName, paramValue));

		return this.findByNamedQuery(name, params);
	}

	public List<T> findByNamedQuery(final String name, String paramName, Object paramValue, int maxResult) {
		List<Map.Entry<String, Object>> params = new ArrayList<>();
		params.add(new SimpleEntry<String, Object>(paramName, paramValue));

		return this.findByNamedQuery(name, params, maxResult);
	}

	// --------------------------------------------------------------------------------------------------------------

	public List<T> findNamedQueryByRange(final String name, Integer startPosition, Integer maxResult) {
		return entityManager.createNamedQuery(name, type).setFirstResult(startPosition).setMaxResults(maxResult).getResultList();
	}

	// --------------------------------------------------------------------------------------------------------------

	public List<T> findByQueryTyped(final String name, List<Map.Entry<String, Object>> params) {
		TypedQuery<T> query = entityManager.createQuery(name, type);
		for (Map.Entry<String, Object> param : params) {
			query.setParameter(param.getKey(), param.getValue());
		}
		return query.getResultList();
	}

	public List<T> findByQueryTyped(final String name, List<Map.Entry<String, Object>> params, int maxResult) {
		TypedQuery<T> query = entityManager.createQuery(name, type);
		query.setMaxResults(maxResult);
		for (Map.Entry<String, Object> param : params) {
			query.setParameter(param.getKey(), param.getValue());
		}
		return query.getResultList();
	}

	// --------------------------------------------------------------------------------------------------------------

	public List<?> findByNativeQuery(final String nativeQuery, List<Map.Entry<String, Object>> params) {
		Query query = entityManager.createNativeQuery(nativeQuery);
		for (Map.Entry<String, Object> param : params) {
			query.setParameter(param.getKey(), param.getValue());
		}
		return query.getResultList();
	}

	// --------------------------------------------------------------------------------------------------------------

	public T findByOneNamedQuery(final String name, List<Map.Entry<String, Object>> params) throws NoResultException {
		try {
			TypedQuery<T> query = entityManager.createNamedQuery(name, type);
			for (Map.Entry<String, Object> param : params) {
				query.setParameter(param.getKey(), param.getValue());
			}
			return query.getSingleResult();
		} catch (NoResultException ex) {
			throw ex;
		}
	}

	public T findByOneNamedQuery(final String queryName, String paramName, Object paramValue) {
		List<Map.Entry<String, Object>> params = new ArrayList<>();
		params.add(new SimpleEntry<String, Object>(paramName, paramValue));

		return this.findByOneNamedQuery(queryName, params);
	}

	public Long findOneIntegerByNamedQuery(final String name) {
		Query query = entityManager.createNamedQuery(name);
		return ((Long) query.getSingleResult()).longValue();
	}

	public Long findOneIntegerByNamedQuery(final String name, List<Map.Entry<String, Object>> params) {
		Query query = entityManager.createNamedQuery(name);
		for (Map.Entry<String, Object> param : params) {
			query.setParameter(param.getKey(), param.getValue());
		}
		return ((Long) query.getSingleResult()).longValue();
	}

	// --------------------------------------------------------------------------------------------------------------

	public EntityType<T> getMetaModelEntityType() {
		Metamodel metaModel = entityManager.getMetamodel();
		return metaModel.entity(type);
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}