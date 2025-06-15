package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.UserCreateDTO;
import com.api_food.Algaworks_Food.dto.list.UserListDTO;
import com.api_food.Algaworks_Food.dto.update.UserUpdateDTO;
import com.api_food.Algaworks_Food.dto.update.UserUpdatePasswordDTO;
import com.api_food.Algaworks_Food.exception.custom.EmailAlreadyExistsException;
import com.api_food.Algaworks_Food.exception.custom.InvalidCurrentPasswordException;
import com.api_food.Algaworks_Food.exception.custom.UserNotFoundException;
import com.api_food.Algaworks_Food.mapper.UserMapper;
import com.api_food.Algaworks_Food.model.UserModel;
import com.api_food.Algaworks_Food.repository.UserRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final StringFormatter stringFormatter;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, UserMapper userMapper, StringFormatter stringFormatter) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.stringFormatter = stringFormatter;
    }

    public void findUserByEmail(String email){
        Optional<UserModel> user = userRepository.findUserByEmail(email.trim());

        if (user.isPresent()){
          throw new EmailAlreadyExistsException(email.trim());
        }
    }

    @Transactional
    public UserCreateDTO addNewUser(UserCreateDTO data){
        this.findUserByEmail(data.getEmail());
        data.setName(stringFormatter.stringFormated(data.getName()));
        String hashedPassword = passwordEncoder.encode(data.getPassword());
        data.setPassword(hashedPassword);

        UserModel userModel = userMapper.toCreateModel(data);
        userModel.setDateCreated(OffsetDateTime.now());
        userModel.setDateUpdated(OffsetDateTime.now());

        UserModel createUser = userRepository.save(userModel);
        return userMapper.toCreateDTO(createUser);
    }

    public UserListDTO findUserById(UUID id){
        UserModel user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        return userMapper.toListDTO(user);
    }

    public List<UserListDTO> listAllUsers(){
        return userRepository.findAll().stream().map(userMapper::toListDTO).toList();
    }

    public UserUpdateDTO updateUser(UUID id, UserUpdateDTO data){
        UserModel user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));

        Optional<UserModel> findEmail = userRepository.findUserByEmail(data.getEmail());

        if(findEmail.isPresent()){
            if (!findEmail.get().getId().equals(user.getId())){
                throw new EmailAlreadyExistsException(data.getEmail());
            }
        }

        String nameFormated = stringFormatter.stringFormated(data.getName());
        user.setDateUpdated(OffsetDateTime.now());
        user.setName(nameFormated);
        user.setEmail(data.getEmail());

        UserModel saveUser = userRepository.save(user);
        return userMapper.toUpdateDTO(saveUser);
    }

    @Transactional
    public void updateUserPassword(UUID id, UserUpdatePasswordDTO data){
        UserModel user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        if (!passwordEncoder.matches(data.getCurrentPassword(), user.getPassword())){
            throw new InvalidCurrentPasswordException();
        }
        String hashedNewPassword = passwordEncoder.encode(data.getNewPassword());
        user.setPassword(hashedNewPassword);
        user.setDateUpdated(OffsetDateTime.now());
        userRepository.save(user);
    }

}
