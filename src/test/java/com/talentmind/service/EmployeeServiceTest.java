package com.talentmind.service;

import com.talentmind.dto.EmployeeDto;
import com.talentmind.entity.Employee;
import com.talentmind.mapper.EmployeeMapper;
import com.talentmind.repository.DepartmentRepository;
import com.talentmind.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @DisplayName("Test de récupération de tous les employés")
    void testGetAllEmployees() {
        // 1. PRÉPARATION
        // Créer deux employés pour le test
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setNom("Bakali");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setNom("Sohayb");

        List<Employee> employees = List.of(employee1, employee2);

        // Créer deux DTOs correspondants
        EmployeeDto dto1 = new EmployeeDto();
        dto1.setId(1L);
        dto1.setNom("Bakali");

        EmployeeDto dto2 = new EmployeeDto();
        dto2.setId(2L);
        dto2.setNom("Sohayb");

        List<EmployeeDto> dtos = List.of(dto1, dto2);

        // Définir le comportement des mocks
        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeMapper.toDtoList(employees)).thenReturn(dtos);

        // 2. EXÉCUTION
        List<EmployeeDto> result = employeeService.getAllEmployees();

        // 3. VÉRIFICATION
        assertEquals(2, result.size());
        assertEquals("Bakali", result.get(0).getNom());
        assertEquals("Sohayb", result.get(1).getNom());

        // Vérifier que les méthodes ont été appelées
        verify(employeeRepository).findAll();
        verify(employeeMapper).toDtoList(employees);
    }

    @Test
    @DisplayName("Test de recherche d'employé avec ID invalide")
    void testGetEmployeeByIdInvalid() {
        // 1. PRÉPARATION
        Long id = 99L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // 2. EXÉCUTION & VÉRIFICATION
        // Vérifier que l'exception est bien lancée
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> employeeService.getEmployeeById(id)
        );

        // Vérifier le message d'erreur
        assertTrue(exception.getMessage().contains("Employé non trouvé"));

        // Vérifier que la méthode a été appelée
        verify(employeeRepository).findById(id);
    }

    @Test
    @DisplayName("Test de recherche d'employé avec ID valide")
    void testGetEmployeeByIdValid() {
        // 1. PRÉPARATION
        Long id = 1L;

        // Créer un employé
        Employee employee = new Employee();
        employee.setId(id);
        employee.setNom("Bakali");
        employee.setEmail("bakali@example.com");

        // Créer le DTO correspondant
        EmployeeDto dto = new EmployeeDto();
        dto.setId(id);
        dto.setNom("Bakali");
        dto.setEmail("bakali@example.com");

        // Définir le comportement des mocks
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(dto);

        // 2. EXÉCUTION
        EmployeeDto result = employeeService.getEmployeeById(id);

        // 3. VÉRIFICATION
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Bakali", result.getNom());
        assertEquals("bakali@example.com", result.getEmail());
    }
}