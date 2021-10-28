package com.ttn.bootcamp.enums;

public enum UserRole {
    CUSTOMER(1),
    SELLER(2),
    ADMIN(3);

    private int type;

    UserRole(int type) {
        this.type = type;
    }

    public static UserRole getUserRole(int type) {
        for (UserRole role : UserRole.values()) {
            if (role.type == type)
                return role;
        }
        return UserRole.CUSTOMER;
    }
}
