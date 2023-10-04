package com.growbiz.backend.Role.dto;

import com.growbiz.backend.User.dto.UserDTO;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType name;

    @OneToOne(mappedBy = "role")
    private UserDTO user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }
}
