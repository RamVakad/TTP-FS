package com.example.assesment.converter;

import com.example.assesment.model.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.getAuthority();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return Role.getRoleFromString(dbData);
    }

}