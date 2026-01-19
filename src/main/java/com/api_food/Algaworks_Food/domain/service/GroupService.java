package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.GroupInput;
import com.api_food.Algaworks_Food.api.dto.output.GroupOutput;
import com.api_food.Algaworks_Food.api.dto.output.PermissionsOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.domain.exception.custom.GroupNameAlreadyExistsException;
import com.api_food.Algaworks_Food.domain.exception.custom.GroupNotFoundException;
import com.api_food.Algaworks_Food.domain.exception.custom.PermissionNotFoundException;
import com.api_food.Algaworks_Food.domain.mapper.GroupMapper;
import com.api_food.Algaworks_Food.domain.mapper.PermissionMapper;
import com.api_food.Algaworks_Food.domain.model.GroupModel;
import com.api_food.Algaworks_Food.domain.model.PermissionModel;
import com.api_food.Algaworks_Food.domain.repository.GroupRepository;
import com.api_food.Algaworks_Food.utils.Formatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final Formatter stringFormatter;
    private final PermissionMapper permissionMapper;
    private final PermissionService permissionService;


    public void verifyGroupName(String name){
        Optional<GroupModel> findGroup = groupRepository.findGroupByName(name);
        if (findGroup.isPresent()){
            throw new GroupNameAlreadyExistsException(name);
        }
    }

    @Transactional
    public GroupOutput addGroup(GroupInput data){

        String name = Formatter.string(data.getName());

        GroupModel newGroup = GroupModel.addGroup(name);

        groupRepository.saveAndFlush(newGroup);

        return groupMapper.toOutput(newGroup);

    }

    public GroupOutput findGroupById(int id){
        GroupModel group = groupRepository.findById(id)
                .orElseThrow(()-> new GroupNotFoundException(id));

        return groupMapper.toOutput(group);
    }

    public List<GroupOutput> listAllGroups(){
        return groupRepository.findAll().stream().map(groupMapper::toOutput).toList();
    }

    @Transactional
    public GroupOutput updateGroup(int id, GroupInput data){
        GroupModel group = this.returnGroupModel(id);

        String name = Formatter.string(data.getName());

        this.verifyGroupName(name);

        group.setName(name);

       groupRepository.saveAndFlush(group);

       return groupMapper.toOutput(group);
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

    public List<PermissionsOutput> listGroupPermissions(int groupId){

        GroupModel group = this.returnGroupModel(groupId);

        List<PermissionModel> permissions = group.getPermissions();

        return permissions.stream().map(permissionMapper::toOutPut).toList();
    }

    @Transactional
    public void removeGroupPermissions(int groupId, int permissionId){
        GroupModel group = this.returnGroupModel(groupId);
        if (group.getPermissions().stream().noneMatch(permission -> permission.getId() == permissionId)){
            throw new PermissionNotFoundException(permissionId, groupId);
        }
        group.getPermissions().removeIf(permission -> permission.getId() == permissionId);

        groupRepository.saveAndFlush(group);
    }

    @Transactional
    public void addGroupPermissions(int groupId, int permissionId){
        GroupModel group = this.returnGroupModel(groupId);
        PermissionModel permission = permissionService.returnPermissionModel(permissionId);

        if (group.getPermissions().stream().noneMatch(perm -> perm.getId() == permissionId)){
            group.getPermissions().add(permission);
            groupRepository.save(group);
        }
    }
}
