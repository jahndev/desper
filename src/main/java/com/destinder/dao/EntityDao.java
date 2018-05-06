package com.destinder.dao;

import com.google.inject.Inject;
import com.mysema.query.jpa.JPAQueryBase;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class EntityDao<T> extends AbstractDAO<T> implements Dao<T> {

	@Inject
	public EntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Optional<T> findById(Long id) {
		return ofNullable(get(id));
	}

	@Override
	public List<T> findAll() {
		return list(criteria());
	}

	@Override
	public T create(T t) {
		return persist(t);
	}

	@Override
	public void update(T t) {
		persist(t);
	}

	protected JPAQueryBase<HibernateQuery> getHibernateQuery() {
		return new HibernateQuery(currentSession());
	}

	@Override
	public boolean exists(Long id) {
		Long count = (Long) currentSession()
				.createCriteria(getEntityClass())
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.rowCount())
				.uniqueResult();
		return count > 0;
	}

}