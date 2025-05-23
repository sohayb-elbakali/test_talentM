package com.talentmind.service;

import com.talentmind.dto.DepartmentDto;
import com.talentmind.entity.Department;
import com.talentmind.mapper.DepartmentMapper;
import com.talentmind.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentDto> getAllDepartments() {
        return departmentMapper.toDtoList(departmentRepository.findAll());
    }

    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Département non trouvé"));
        return departmentMapper.toDto(department);
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        Department department = departmentMapper.toEntity(departmentDto);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        if (!departmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Département non trouvé");
        }

        Department department = departmentMapper.toEntity(departmentDto);
        department.setId(id);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Département non trouvé");
        }
        departmentRepository.deleteById(id);
    }
}