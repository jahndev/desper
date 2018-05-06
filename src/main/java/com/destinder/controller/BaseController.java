package com.destinder.controller;

import com.destinder.api.Identifiable;
import com.destinder.core.Entity;
import com.destinder.dao.Dao;
import com.destinder.errors.EntityNotFoundException;
import com.destinder.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseController<ApiDtoType extends Identifiable, EntityType extends Entity<?>> {

	private final Dao<EntityType> dao;
	private final Mapper<ApiDtoType, EntityType> mapper;

	public BaseController(Dao<EntityType> dao, Mapper<ApiDtoType, EntityType> mapper) {
		this.dao = dao;
		this.mapper = mapper;
	}

	public ApiDtoType create(ApiDtoType dto) {
		EntityType entity = getMapper().createEntity(dto.getClass());
		getMapper().updateEntity(entity, dto);

		getDao().create(entity);

		return getMapper().createApiDto(entity);
	}

	public ApiDtoType findById(Long id) throws EntityNotFoundException {
		Optional<EntityType> entityOpt = getDao().findById(id);
		entityOpt.orElseThrow(EntityNotFoundException::new);

		EntityType entity = entityOpt.get();
		return getMapper().createApiDto(entity);
	}

	public List<ApiDtoType> findAll() {
		List<EntityType> entities = getDao().findAll();

		return entities.stream().map(getMapper()::createApiDto).collect(Collectors.toList());
	}

	public void update(ApiDtoType dto) throws EntityNotFoundException {
		Optional<EntityType> entityOpt = getDao().findById(dto.getId());
		entityOpt.orElseThrow(EntityNotFoundException::new);

		EntityType entity = entityOpt.get();
		getMapper().updateEntity(entity, dto);
		getDao().update(entity);
	}

	protected Dao<EntityType> getDao() {
		return this.dao;
	}

	protected Mapper<ApiDtoType, EntityType> getMapper() {
		return this.mapper;
	}

}
