package com.example.papel.mapper;

import com.example.papel.entity.PostEntity;
import com.example.papel.entity.UserEntity;
import com.papel.openapi.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "userEntity", source = "userId", qualifiedByName = "mapUserIdToUserEntity")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapOffsetDateTimeToLocalDateTime")
    PostEntity toPostEntity(PostDto postDto);

    @Mapping(target = "userId", source = "userEntity.userId")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToOffsetDateTime")
    PostDto toPostDto(PostEntity postEntity);

    @Named("mapOffsetDateTimeToLocalDateTime")
    default LocalDateTime mapOffsetDateTimeToLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toLocalDateTime();
    }

    @Named("mapLocalDateTimeToOffsetDateTime")
    default OffsetDateTime mapLocalDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC);
    }

    @Named("mapUserIdToUserEntity")
    default UserEntity mapUserIdToUserEntity(Long userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        return userEntity;
    }

}

