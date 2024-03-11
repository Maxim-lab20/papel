package com.example.papel.mapper;

import com.example.papel.entity.UserEntity;
import com.papel.openapi.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", ignore = true)
    UserEntity toUserEntity(UserDto userDto);

    UserDto toUserDto(UserEntity userEntity);
}
