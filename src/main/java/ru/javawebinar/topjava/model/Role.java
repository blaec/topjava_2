package ru.javawebinar.topjava.model;

public enum Role {
    ROLE_USER, ROLE_ADMIN;

    private Role role;

    Role() {}

    Role(Role role) {
        this.role = role;
    }


    public Role getRole() {
        return role;
    }
}