package com.api_food.Algaworks_Food.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "groups")
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "group_permissions",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<PermissionModel> permissions;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private List<UserModel> users;

    public static GroupModel addGroup(String name) {
        GroupModel group = new GroupModel();
        group.setName(name);
        return group;
    }
}
