package com.desper.mapper;

public interface Mapper<ApiDtoType, AbstractDAO> {

	void updateEntity(AbstractDAO entity, ApiDtoType dto);

	AbstractDAO createEntity(Class<?> dtoClass);

	ApiDtoType createApiDto(AbstractDAO entity);

}
