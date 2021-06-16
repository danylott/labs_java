package com.lab2.demo.service;

import com.lab2.demo.converter.UserConverter;
import com.lab2.demo.service.data.UserService;
import com.lab2.demo.dto.UserDTO;
import com.lab2.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserControllerService {
    private final UserService userService;
    private final UserConverter userConverter;

    public List<UserDTO> findByEmail(String email) {
        List<UserDTO> userDTOList = new ArrayList<>();
        Optional<User> user = userService.findUserByEmail(email);
        user.ifPresent(value -> userDTOList.add(userConverter.convertToDto(user.get())));

        return userDTOList;
    }
}
