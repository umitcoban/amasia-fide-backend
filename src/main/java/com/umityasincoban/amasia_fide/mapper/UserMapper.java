package com.umityasincoban.amasia_fide.mapper;

import com.umityasincoban.amasia_fide.dto.RegisterDTO;
import com.umityasincoban.amasia_fide.dto.UserDTO;
import com.umityasincoban.amasia_fide.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User registerDtoToUser(RegisterDTO registerDTO);

    @Mapping(source = "userId", target = "id")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "userId", target = "id")
    List<UserDTO> userToUsersDTO(List<User> users);

    User userToUserDTO(UserDTO userDTO);

}
