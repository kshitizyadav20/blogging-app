package com.Kshitiz.blog.services.impl;

import com.Kshitiz.blog.entities.User;
import com.Kshitiz.blog.exceptions.ResourceNotFoundException;
import com.Kshitiz.blog.payloads.UserDto;
import com.Kshitiz.blog.repositories.UserRepo;
import com.Kshitiz.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;   // ✅ FIX: Inject encoder

    // CREATE USER (ENCODES PASSWORD)
    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);

        // ✅ FIX: Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    // UPDATE USER (ENCODES PASSWORD)
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        // ✅ FIX: Encode updated password also
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return this.userRepo.findAll()
                .stream()
                .map(this::userToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    // Mappers
    public User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }
}
