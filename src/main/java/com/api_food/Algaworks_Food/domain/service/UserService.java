package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.UserInput;
import com.api_food.Algaworks_Food.api.dto.input.UserUpdateInput;
import com.api_food.Algaworks_Food.api.dto.input.UserUpdatePasswordInput;
import com.api_food.Algaworks_Food.api.dto.output.GroupOutput;
import com.api_food.Algaworks_Food.api.dto.output.UserOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.*;
import com.api_food.Algaworks_Food.domain.mapper.GroupMapper;
import com.api_food.Algaworks_Food.domain.mapper.UserMapper;
import com.api_food.Algaworks_Food.domain.model.GroupModel;
import com.api_food.Algaworks_Food.domain.model.UserModel;
import com.api_food.Algaworks_Food.domain.repository.UserRepository;
import com.api_food.Algaworks_Food.utils.Formatter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final GroupService groupService;
    private final GroupMapper  groupMapper;


    public UserModel returnUserModel(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void verifyEmailInUse(String email){
        Optional<UserModel> user = userRepository.findUserByEmail(email);

        if (user.isPresent()){
          throw new EmailAlreadyExistsException(email);
        }
    }

    @Transactional
    public UserOutput addNewUser(UserInput input){

        String name = Formatter.string(input.getName());
        String email = Formatter.string(input.getEmail());
        String hashedPassword = passwordEncoder.encode(input.getPassword());

        this.verifyEmailInUse(email);

        UserModel user = UserModel.addUser(name, email, hashedPassword);

        userRepository.saveAndFlush(user);

        return userMapper.toOutput(user);
    }

    public UserOutput findUserById(UUID id){

        return userMapper.toOutput(this.returnUserModel(id));
    }

    public List<UserOutput> listAllUsers(){
        return userRepository.findAll().stream().map(userMapper::toOutput).toList();
    }

    @Transactional
    public UserOutput updateUser(UUID id, UserUpdateInput input){

        UserModel user = this.returnUserModel(id);

        String name = Formatter.string(input.getName());
        String email = Formatter.string(input.getEmail());

        Optional<UserModel> foundUser = userRepository.findUserByEmail(email);

        if(foundUser.isPresent()){
            if (!foundUser.get().getId().equals(user.getId())){
                throw new EmailAlreadyExistsException(email);
            }
        }

        user.setName(name);
        user.setEmail(email);
        user.setDateUpdated(OffsetDateTime.now());

        userRepository.saveAndFlush(user);

        return userMapper.toOutput(user);

    }

    @Transactional
    public void updateUserPassword(UUID id, UserUpdatePasswordInput input){

        UserModel user = this.returnUserModel(id);

        if (!passwordEncoder.matches(input.getCurrentPassword(), user.getPassword())){
            throw new InvalidCurrentPasswordException();
        }

        String hashedNewPassword = passwordEncoder.encode(input.getNewPassword());

        user.setPassword(hashedNewPassword);
        user.setDateUpdated(OffsetDateTime.now());
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void deleteUser(UUID id){
        UserModel user = this.returnUserModel(id);

        if (user.getGroups() != null && !user.getGroups().isEmpty() ||
                user.getOrders() != null && !user.getOrders().isEmpty()){
            throw new EntityInUseException("user", user.getId(), "groups and orders");
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public void addGroupToUser(UUID userId, int groupId){

        UserModel user = this.returnUserModel(userId);

        GroupModel group = groupService.returnGroupModel(groupId);

        if (!user.getGroups().contains(group)){
            user.getGroups().add(group);
            userRepository.save(user);
        }
    }

    public List<GroupOutput> listGroupsFromUser(UUID userId){

        UserModel user = this.returnUserModel(userId);

        return user.getGroups().stream().map(groupMapper::toOutput).toList();

    }

    @Transactional
    public void removeGroupFromUser(UUID userId, int groupId){

        UserModel user = this.returnUserModel(userId);

        GroupModel group = groupService.returnGroupModel(groupId);

        if(user.getGroups().contains(group)){
            user.getGroups().removeIf(group::equals);
        }
    }

    public UserModel verifyUserField(UUID id){
        return userRepository.findById(id).orElseThrow(() -> new BusinessException("user", id));
    }
}
