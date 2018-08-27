package com.desper.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
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

	@Override
	public boolean exists(Long id) {
		return findById(id).isPresent();
	}
}