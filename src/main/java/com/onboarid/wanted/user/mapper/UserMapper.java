package com.onboarid.wanted.user.mapper;

import com.onboarid.wanted.user.dto.UserPostDto;
import com.onboarid.wanted.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User PostDtoToEntity (UserPostDto postDto);
}
