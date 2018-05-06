package com.destinder.mapper;

public interface Mapper<ApiDtoType, EntityType> {

	void updateEntity(EntityType entity, ApiDtoType dto);

	EntityType createEntity(Class<?> dtoClass);

	ApiDtoType createApiDto(EntityType entity);

}
