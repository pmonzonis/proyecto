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
import static proyectoDam.Controller.PupilController.alumnesActius;
import proyectoDam.Model.Pupil;

/**
 *
 * @author Paula Monzonis Fortea
 */
public class PupilControllerTest {
    
    @Mock
    PupilRepository pupilRepository;
    
    @InjectMocks
    PupilController pupilController;
    @InjectMocks
    HashMap alumnesActius =new HashMap<>();       
    Pupil pupil;
    
    public PupilControllerTest() {
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
        pupil=new Pupil();
        pupil.setUserName("paula");
        pupil.setPassword("Paula1Ci.");
        pupil.setName("Paula");
        pupil.setSurename("Lopez");
        //pupil.setSchool("Delmar");
        pupil.setCourse("1CI");
        pupil.setActive(true);
        pupil.setPet("PetPaula");
        
        alumnesActius.put("paula", 789000);
        pupilRepository.save(pupil);
            
    }
    
    @AfterEach
    public void tearDown() {
    }
      
    
    /**
     * Test del método registerUser de la classe PupilController.
     * test para comprobar que si el susuario ya existe no se le permite darlo de alta
     */
    @Test
    public void testRegitraAlumnoExistente() {
        System.out.println("\nRegistra Alumno");
        Pupil newPupil= new Pupil();
        
        int codi=789000;
        newPupil.setUserName("paula");
        newPupil.setPassword("nu8734,");
        newPupil.setActive(true);
        pupilRepository.save(newPupil);
        when(pupilRepository.findAll()).thenReturn(Arrays.asList(pupil)); 
                
        //AdminController instance = new AdminController();
        int expResult = 0;
        int result = pupilController.registraAlumno(newPupil, codi);
        assertEquals(expResult, result);
    }

    
     /**
     * Testcdel metodo registraAdminNou de la clase AdminController.
     * si el usuario es nuevo
     */
    
    @Test
    public void testRegistraAlumneNou() {
        System.out.println("\nRegistra Alumno");
        Pupil newPupil = new Pupil();
        int codi=789000;
        
        newPupil.setUserName("perejil");
        newPupil.setPassword("s6yh84!");
        newPupil.setActive(true);
        pupilRepository.save(newPupil);
        when(pupilRepository.findAll()).thenReturn(Arrays.asList(pupil)); 
                
        //AdminController instance = new AdminController();
        int expResult = 1;
        int result = pupilController.registraAlumno(newPupil, codi);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
     /**
     * Test del método deletePupilByUsername de la classe AdminController.
     * si el usuario existe
     */
    @Test
    public void testDeletePupilByUsernameExist() {
        System.out.println("\nBorra Alumno por nombre de ususario");
        String username = "paula";        
        int codi=789000;
        
        when(pupilRepository.findAll()).thenReturn(Arrays.asList(pupil));
        System.out.print(pupilRepository.findAll());
        int result= pupilController.deletePupilByUsername(username,codi);
        int expResult = 1;
        assertEquals(expResult, result);
        
    }
    
     /**
     * Test del método deletePupilByUsername method de la clase AdminController.
     *  si el usuario no existe
     */
    @Test
    public void testDeletePupilByUsernameNoExist() {
        System.out.println("\nBorra Alumno por nombre de ususario");
        String username = "perejil876";
        
        int codi=789000;
        //AdminController instance = new AdminController();
        when(pupilRepository.findAll()).thenReturn(Arrays.asList(pupil));
        int result= pupilController.deletePupilByUsername(username, codi);
        int expResult = 2;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of listUser method, of class PupilController.
     */
    @Test
    public void testListPupil() {
        System.out.println("listPupils");       
        int codi=789000;
        when(pupilRepository.findAll()).thenReturn(Arrays.asList(pupil));          
        List<Pupil> expResult = pupilRepository.findAll();
        List<Pupil> result = pupilController.listPupils(codi);
        System.out.print(pupilRepository.findAll());
        assertEquals(expResult, result);      
    }   
    
    
}
