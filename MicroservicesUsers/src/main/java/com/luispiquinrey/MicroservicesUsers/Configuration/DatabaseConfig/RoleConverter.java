package com.luispiquinrey.MicroservicesUsers.Configuration.DatabaseConfig;

import com.luispiquinrey.MicroservicesUsers.Enums.Role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.name();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        if (dbData.startsWith("ROLE_")) {
            dbData = dbData.substring(5);
        }
        return Role.valueOf(dbData);
    }
}
