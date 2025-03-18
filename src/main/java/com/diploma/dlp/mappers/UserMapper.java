package com.diploma.dlp.mappers;

import com.diploma.dlp.dto.CreateUserDTO;
import com.diploma.dlp.dto.UserDTO;
import com.diploma.dlp.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserDTO createUserDTO);
    UserDTO toUserDTO(User user);
}
