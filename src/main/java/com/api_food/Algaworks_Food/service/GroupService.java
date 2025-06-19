package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.GroupCreateDTO;
import com.api_food.Algaworks_Food.dto.list.GroupListDTO;
import com.api_food.Algaworks_Food.dto.list.PermissionListDTO;
import com.api_food.Algaworks_Food.dto.update.GroupUpdateDTO;
import com.api_food.Algaworks_Food.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.exception.custom.GroupNameAlreadyExistsException;
import com.api_food.Algaworks_Food.exception.custom.GroupNotFoundException;
import com.api_food.Algaworks_Food.exception.custom.PermissionNotFoundException;
import com.api_food.Algaworks_Food.mapper.GroupMapper;
import com.api_food.Algaworks_Food.mapper.PermissionMapper;
import com.api_food.Algaworks_Food.model.GroupModel;
import com.api_food.Algaworks_Food.model.PermissionModel;
import com.api_food.Algaworks_Food.repository.GroupRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final StringFormatter stringFormatter;
    private final PermissionMapper permissionMapper;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper, StringFormatter stringFormatter, PermissionMapper permissionMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.stringFormatter = stringFormatter;
        this.permissionMapper = permissionMapper;
    }

    public void verifyGroupName(String name){
        Optional<GroupModel> findGroup = groupRepository.findGroupByName(name);
        if (findGroup.isPresent()){
            throw new GroupNameAlreadyExistsException(name);
        }
    }

    @Transactional
    public GroupCreateDTO addGroup(GroupCreateDTO data){
        String nameFormated = stringFormatter.stringFormated(data.getName());
        this.verifyGroupName(nameFormated);

        GroupModel createGroup = groupMapper.toCreateModel(data);
        createGroup.setName(nameFormated);
        GroupModel saveGroup = groupRepository.save(createGroup);
        return groupMapper.toCreateDTO(saveGroup);
    }

    public GroupListDTO findGroupById(int id){
        GroupModel group = groupRepository.findById(id)
                .orElseThrow(()-> new GroupNotFoundException(id));

        return groupMapper.toListDTO(group);
    }

    public List<GroupListDTO> listAllGroups(){
        return groupRepository.findAll().stream().map(groupMapper::toListDTO).toList();
    }

    @Transactional
    public GroupUpdateDTO updateGroup(int id, GroupUpdateDTO data){
        GroupModel group = this.returnGroupModel(id);
        String nameFormated = stringFormatter.stringFormated(data.getName());
        this.verifyGroupName(nameFormated);

        group.setName(nameFormated);
        GroupModel saveGroup = groupRepository.save(group);
        return groupMapper.toUpdateDTO(saveGroup);
    }

    @Transactional
    public void deleteGroup(int id){
        GroupModel group = this.returnGroupModel(id);

        if(group.getPermissions() != null && !group.getPermissions().isEmpty() ||
            group.getUsers() != null && !group.getUsers().isEmpty()){
            throw new EntityInUseException(group.getName(), group.getId(), "permissions and users");
        }

        groupRepository.deleteById(id);
    }

    public GroupModel returnGroupModel(int id){
        return groupRepository.findById(id).orElseThrow(()-> new GroupNotFoundException(id));
    }

    public List<PermissionListDTO> listGroupPermissions(int groupId){
        GroupModel group = this.returnGroupModel(groupId);
        List<PermissionModel> permissions = group.getPermissions();
        return permissions.stream().map(permissionMapper::toListDTO).toList();
    }

    @Transactional
    public void removeGroupPermissions(int groupId, int permissionId){
        GroupModel group = this.returnGroupModel(groupId);
        if (group.getPermissions().stream().noneMatch(permission -> permission.getId() == permissionId)){
            throw new PermissionNotFoundException(permissionId, groupId);
        }
        group.getPermissions().removeIf(permission -> permission.getId() == permissionId);
        groupRepository.save(group);
    }
}
