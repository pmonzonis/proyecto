/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package proyectoDam.Controller;

import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import proyectoDam.Model.Pupil;
import proyectoDam.Model.Teacher;

/**
 *
 * @author Paula Monzonis Fortea
 */
public class TeacherControllerTest {
    @Mock
    TeacherRepository teacherRepository;
    
    @InjectMocks
    TeacherController teacherController;
    @InjectMocks
    HashMap profesActius =new HashMap<>();       
    Teacher teacher;
    
    public TeacherControllerTest() {
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
        teacher=new Teacher();
        teacher.setUserName("mariap");
        teacher.setPassword("s4p3rs3cr3t.");              
        teacher.setActive(true);
        teacher.setId(1);
        
        
        profesActius.put("mariap", 389000);
        
    }
    
    @AfterEach
    public void tearDown() {
        
    }

   

     /**
     * Test del método registerUser de la classe PupilController.
     * test para comprobar que si el susuario ya existe no se le permite darlo de alta
     */
    @Test  
    public void testRegitraTeacherExistente() {
        System.out.println("\nRegistra Alumno");
        Teacher newTeacher= new Teacher();
        
        int codi=10001;
        newTeacher.setUserName("mariap");
        newTeacher.setPassword("nu8734,");
        newTeacher.setActive(true);
        teacherRepository.save(newTeacher);
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher)); 
                
        //AdminController instance = new AdminController();
        int expResult = 0;
        int result = teacherController.registraProfesor(teacher, codi);
        assertEquals(expResult, result);
    }

    /**
     * Testcdel metodo registraAdminNou de la clase AdminController.
     * si el usuario es nuevo
     */
    
    @Test  
    public void testRegistraTeacherNou() {
        System.out.println("\nRegistra Alumno");
        Teacher newTeacher = new Teacher();
        int codi=10001;
        
        newTeacher.setUserName("perejil");
        newTeacher.setPassword("s6yh84!");
        newTeacher.setActive(true);
        teacherRepository.save(newTeacher);
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher)); 
                
        //AdminController instance = new AdminController();
        int expResult = 1;
        int result = teacherController.registraProfesor(newTeacher,codi);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    /**
     * Test del método deletePupilByUsername de la classe AdminController.
     * si el usuario existe
     */
    @Test
    public void testDeleteTeacherByUsernameExist() {
        System.out.println("\nBorra Porfesor por nombre de ususario");
        String username = "mariap";  
        int codi=10001;
        
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));
        System.out.print(teacherRepository.findAll());
        int result= teacherController.deleteTeacherByUsername(username,codi);
        int expResult = 1;
        assertEquals(expResult, result);
        
    }
     
    @Test
    public void testDeleteTeacherByUsernameNoExist() {
        System.out.println("\nBorra Profesor por nombre de ususario");
        String username = "perejil876";
        
        int codi=10001;
        //AdminController instance = new AdminController();
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));
        int result= teacherController.deleteTeacherByUsername(username,codi);
        int expResult = 2;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
     /**
     * Test del método listUser method de la clase PupilController.
     */
    @Test   
    public void testListTeacher() {
        System.out.println("Lista de profesores");    
        int codi=10001;
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));          
        List<Teacher> expResult = teacherRepository.findAll();
        List<Teacher> result = teacherController.listTeacher(codi);
        System.out.print(teacherRepository.findAll());
        assertEquals(expResult, result);      
    }   
    /**
     * Test del método deleteTeacherlByUsername de la classe TeacherController.
     * si el usuario existe
     */
    /*@Test   ok
    public void testDeleteTeacherByIdExist() {
        System.out.println("\nBorra Profesor por Id");         
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));
        long id= teacher.getId();
        System.out.print(teacherRepository.findAll());
        int result= teacherController.deleteTeacherById(id);
        int expResult = 1;
        assertEquals(expResult, result);
        
    }
    /**
     * Test del método deleteTeacherByUsername de la classe TeacherController.
     * si el usuario existe
     */
   @Test  
    public void testDeleteTeacherByIdNoExist() {
        System.out.println("\nBorra Profesor por Id");    
        long id =789;
        int codi=10001;
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));
        System.out.print(teacherRepository.findAll());
        int result= teacherController.deleteTeacherById(id, codi);
        int expResult = 2;
        assertEquals(expResult, result);
        
    }  
    
}

