package com.talentmind.repository;

import com.talentmind.entity.Department;
import com.talentmind.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void findBySalaireGreaterThanEqual_ReturnsMatchingEmployees() {
        // Préparation des données
        Department department = createAndSaveDepartment("IT");

        // Employé avec salaire supérieur ou égal au seuil
        createAndSaveEmployee("El Bakali", "Sohayb", "sohayb@gmail.com", 7000.0, department); // Salaire modifié pour être >= seuil

        // Employé avec salaire inférieur au seuil
        createAndSaveEmployee("Mr", "Hamza", "hamza@gmail.com", 3000.0, department); // Salaire modifié pour être < seuil

        // Exécution de la méthode testée
        double seuil = 5000.0;
        List<Employee> result = employeeRepository.findBySalaireGreaterThanEqual(seuil);

        // Vérification des résultats
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("El Bakali");
        assertThat(result.get(0).getSalaire()).isGreaterThanOrEqualTo(seuil);
    }

    // Méthodes utilitaires pour simplifier la création d'objets
    private Department createAndSaveDepartment(String nom) {
        Department department = new Department();
        department.setNom(nom);
        return departmentRepository.save(department);
    }

    private Employee createAndSaveEmployee(String nom, String prenom, String email,
                                           Double salaire, Department department) {
        Employee employee = new Employee();
        employee.setNom(nom);
        employee.setPrenom(prenom);
        employee.setEmail(email);
        employee.setSalaire(salaire);
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }
}