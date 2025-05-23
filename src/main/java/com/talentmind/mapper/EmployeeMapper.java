package com.talentmind.mapper;

import com.talentmind.dto.EmployeeDto;
import com.talentmind.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDto toDto(Employee employee);

    @Mapping(source = "departmentId", target = "department.id")
    Employee toEntity(EmployeeDto employeeDto);

    List<EmployeeDto> toDtoList(List<Employee> employees);
}