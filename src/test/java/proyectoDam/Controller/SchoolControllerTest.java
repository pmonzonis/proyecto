/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package proyectoDam.Controller;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import proyectoDam.Model.Admin;
import proyectoDam.Model.School;

/**
 *
 * @author Paula Monzonis Fortea
 */
public class SchoolControllerTest {
     @Mock
    SchoolRepository schoolRepository;
    
    @InjectMocks
    SchoolController schoolController;
    
          
    School school;
    
    public SchoolControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        school=new School();
        school.setId(1);
        school.setName("DelMar");
        school.setAddress("Avd Meridional, 8, 98768 Caceres");  
        
        schoolRepository.save(school);
    }
    
    @AfterEach
    public void tearDown() {
    }

    
    /**
     * Test del método registraSchool de la classe SchoolController
     * si la escuela ya existe
     */
    
    @Test
    public void testRegistraSchoolExistent() {
        System.out.println("\nRegistra School");
        School newSchool= new School();        
        
        newSchool.setName("DelMar");
        newSchool.setAddress("Avd Meridional, 8, 98768 Caceres");        
        
        int codi= 10001;
        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school));         
        int expResult = 0;
        int result = schoolController.registerSchool(newSchool, codi);
        assertEquals(expResult, result);        
        
    }
    /**
     * Test del metodo registraSchool de la clase SchoolController.
     * si la escuela es nueva
     */
    
    @Test
    public void testRegistraSchoolNou() {
        System.out.println("\nRegistra School");
        int codi= 10001;
        School newSchool= new School(); 
        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school));
        newSchool.setName("Cheun");
        newSchool.setAddress("carrer Principal, 12500 Castello, ");   
        int expResult = 1;
        int result = schoolController.registerSchool(newSchool, codi);
        assertEquals(expResult, result);
        
    }
     /**
     * Test del método deleteSchoolByUsername method de la clase SchoolController.
     *  si el colegio existe
     */
    @Test
    public void testDeleteSchoolBynameExist() {
        System.out.println("\nBorra esculea por nombre");
        String name = "DelMar";
        //AdminController instance = new AdminController();
        int codi= 10001;
        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school));
        int result= schoolController.deleteSchoolByName(name,codi);
        int expResult = 1;
        assertEquals(expResult, result);
        
    }
    /**
     * Test del método deleteSchoolByUsername method de la clase SchoolController.
     *  si el colegio no existe
     */
    @Test
    public void testDeleteSchoolBynameNoExist() {
        System.out.println("\nBorra esculea por nombre");
        String name = "Mio";
        int codi= 10001;
        //AdminController instance = new AdminController();
        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school));
        int result= schoolController.deleteSchoolByName(name, codi);
        int expResult = 2;
        assertEquals(expResult, result);
        
    }
    
     /**
     * Test del método deleteSchool por escuela method de la clase SchoolController.
     *  si el colegio no existe
     */
    @Test
    public void testDeleteSchool_SchoolExist() {
        System.out.println("\nBorra escuela ");
        int codi= 10001;
        //AdminController instance = new AdminController();
        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school));
        int result= schoolController.registerSchool(school,codi);
        int expResult = 0;
        assertEquals(expResult, result);
        
    }
     /**
     * Test del método deleteSchool por escuela method de la clase SchoolController.
     *  si el colegio no existe
     */
    @Test
    public void testDeleteSchool_SchoolNoExist() {
        System.out.println("\nBorra escuela ");
        School newSchool=new School();
        newSchool.setId(2);
        newSchool.setName("Kio");
        newSchool.setAddress("Plaza baja//, 8, 98768 Caceres");  
        
        int codi= 10001;
        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school));
        int result= schoolController.registerSchool(newSchool,codi);
        int expResult = 1;
        assertEquals(expResult, result);
        
    }
     /**
     * Test of listSchool method, of class SchoolController
     */
    @Test
    public void testListSchool() {
        System.out.println("listSchool");    
        int codi= 10001;
        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school));          
        List<School> expResult = schoolRepository.findAll();
        List<School> result = schoolController.listSchool(codi);
        System.out.print(schoolRepository.findAll());
        assertEquals(expResult, result);      
    }   

    /**
     * Test of deleteAllSchools method, of class SchoolController.
     */
    @Test
    public void testDeleteAllSchools() {
        System.out.println("deleteAllSchools"); 
        int codi= 10001;
        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school));          
        //List<School> expResult = schoolRepository.findAll();
        int expResult= schoolController.deleteAllSchools(codi);
        int result=0;
        assertEquals(expResult, result); 
    }
    
}
