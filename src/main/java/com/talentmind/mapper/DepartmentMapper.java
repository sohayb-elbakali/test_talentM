package com.talentmind.mapper;

import org.mapstruct.Mapper;

import com.talentmind.dto.DepartmentDto;
import com.talentmind.entity.Department;


import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentDto toDto(Department department);
    Department toEntity(DepartmentDto departmentDto);
    List<DepartmentDto> toDtoList(List<Department> departments);
}
