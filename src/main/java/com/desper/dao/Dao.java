package com.desper.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<E> {

	Optional<E> findById(Long id);

	List<E> findAll();

	E create(E entity);

	void update(E entity);
	
	boolean exists(Long id);
	
}