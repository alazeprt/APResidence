package com.alazeprt.util;


import java.util.Objects;

public enum ResidencePermission {
    Drop(0),
    Egg(1),
    Fish(2),
    Portal(3),
    InteractEntity(4),
    InteractBlock(5),
    InteractContainerBlock(6),
    Place(7),
    Break(8),
    PVP(9);

    private final int id;

    ResidencePermission(int id) {
        this.id = id;
    }

    public static ResidencePermission getById(int id) {
        for (ResidencePermission permission : ResidencePermission.values()) {
            if (permission.id == id) {
                return permission;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public static ResidencePermission getByName(String name) {
        for(ResidencePermission permission : ResidencePermission.values()){
            if(Objects.equals(permission.name(), name)){
                return permission;
            }
        }
        return null;
    }
}