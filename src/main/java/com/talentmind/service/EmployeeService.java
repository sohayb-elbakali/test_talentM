package com.talentmind.service;




import com.talentmind.dto.EmployeeDto;

import com.talentmind.entity.Employee;

import com.talentmind.mapper.EmployeeMapper;
import com.talentmind.repository.DepartmentRepository;

import com.talentmind.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeDto> getAllEmployees() {
        return employeeMapper.toDtoList(employeeRepository.findAll());
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé non trouvé"));
        return employeeMapper.toDto(employee);
    }

    public List<EmployeeDto> getEmployeesByDepartmentId(Long departmentId) {
        return employeeMapper.toDtoList(employeeRepository.findByDepartmentId(departmentId));
    }

    public List<EmployeeDto> getEmployeesBySalaireMin(Double montant) {
        return employeeMapper.toDtoList(employeeRepository.findBySalaireGreaterThanEqual(montant));
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getDepartmentId() != null) {
            departmentRepository.findById(employeeDto.getDepartmentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Département non trouvé"));
        }

        Employee employee = employeeMapper.toEntity(employeeDto);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if (!employeeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé non trouvé");
        }

        if (employeeDto.getDepartmentId() != null) {
            departmentRepository.findById(employeeDto.getDepartmentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Département non trouvé"));
        }

        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setId(id);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé non trouvé");
        }
        employeeRepository.deleteById(id);
    }
}