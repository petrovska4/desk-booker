package org.example.restapi.mapper;

import org.example.core.model.Employee;
import org.mapstruct.Named;

import java.util.UUID;

public class CommonMapper {
    @Named("mapIdToUuid")
    public static UUID mapIdToUuid(String id){
        return id != null ? UUID.fromString(id) : null;
    }

    @Named("mapUuidToId")
    public static String mapUuidToId(UUID id){
        return id != null ? id.toString() : null;
    }

//    @Named("mapIdToEmployee")
//    public static Employee mapIdToEmployee(String employeeId) {
//        Employee employee = new Employee();
//        if(employeeId == null){
//            employee.setUuid(null);
//            return employee;
//        }
//        employee.setUuid(UUID.fromString(employeeId));
//
//        return employee;
//    }
}
