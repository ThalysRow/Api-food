package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.GroupCreateDTO;
import com.api_food.Algaworks_Food.exception.custom.GroupNameAlreadyExistsException;
import com.api_food.Algaworks_Food.mapper.GroupMapper;
import com.api_food.Algaworks_Food.model.GroupModel;
import com.api_food.Algaworks_Food.repository.GroupRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final StringFormatter stringFormatter;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper, StringFormatter stringFormatter) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.stringFormatter = stringFormatter;
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
}
