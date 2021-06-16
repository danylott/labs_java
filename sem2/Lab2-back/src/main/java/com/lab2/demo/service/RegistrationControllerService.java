package com.lab2.demo.service;

import com.lab2.demo.converter.UserConverter;
import com.lab2.demo.service.data.RegistrationService;
import com.lab2.demo.dto.UserDTO;
import com.lab2.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationControllerService {
    private final UserConverter userConverter;
    private final RegistrationService registrationService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserDTO save(UserDTO userDTO) {
        User currentUser = userConverter.convertToEntity(userDTO);
        UserDTO savedUserDto = userConverter.convertToDto(registrationService.save(currentUser));
        savedUserDto.setPassword(userDTO.getPassword());
        applicationEventPublisher.publishEvent(savedUserDto);
        return savedUserDto;
    }
}
