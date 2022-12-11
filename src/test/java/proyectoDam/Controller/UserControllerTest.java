/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package proyectoDam.Controller;

import java.util.Arrays;
import java.util.HashMap;
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
import proyectoDam.Model.Admin;
import proyectoDam.Model.Pupil;
import proyectoDam.Model.School;
import proyectoDam.Model.Teacher;
import proyectoDam.Model.Login;

/**
 *
 * @author Paula Monzonis Fortea
 */
public class UserControllerTest {
    @Mock
    AdminRepository adminRepository;
    @Mock
    PupilRepository pupilRepository;
    
    
    @InjectMocks
    UserController userController;
    
    @InjectMocks
    HashMap adminsActius =new HashMap<>();
    @InjectMocks
    HashMap alumnesActius =new HashMap<>();
    
    Admin admin;
    Pupil pupil;
    @Mock
    TeacherRepository teacherRepository;
    
    @InjectMocks
    TeacherController teacherController;
    @InjectMocks
    HashMap profesActius =new HashMap<>();       
    Teacher teacher;
    
    
    public UserControllerTest() {
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
        admin=new Admin();
        admin.setUserName("admin1");
        admin.setPassword("s5rd7g.8");
        admin.setActive(true);
                
        
        adminsActius.put("admin1", 19890);
        adminRepository.save(admin);
        
        School school =new School();
        school.setAddress("Avd Barcelona, 28080 Barcelona");
        school.setName("Delmar");
        
        
        MockitoAnnotations.initMocks(this);
        pupil=new Pupil();
        pupil.setUserName("paula");
        pupil.setPassword("Paula1Ci.");
        pupil.setName("Paula");
        pupil.setSurename("Lopez");
        pupil.setSchool(school);
        pupil.setCourse("1CI");
        pupil.setActive(true);
        pupil.setPet("PetPaula");
        
        alumnesActius.put("paula", 789000);
        pupilRepository.save(pupil);
        
        MockitoAnnotations.initMocks(this);
        teacher=new Teacher();
        teacher.setUserName("mariap");
        teacher.setPassword("s4p3rs3cr3t.");
       
              
        teacher.setActive(true);
        
        
        profesActius.put("maria", 389000);
        //teacherRepository.save(teacher);
       
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of login method, of class UserController.
     */
   
    @Test
    public void testLoginAdmin() {
        System.out.println("login");
        System.out.println("\nLOGIN:");        
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));        
        int expResult= 7890;        
        int result = userController.login(admin.getUserName(), admin.getPassword());
        expResult=result;
        assertEquals(expResult, result);
    }
    /**
     * Test para comprobar que el password introducido de un usuario administrador es correcto   
     * 
     */    
    @Test    
    public void testPasswordAdmin() {
        System.out.println("\nLOGIN:");
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));         
        admin=new Admin();
        admin.setUserName("admin1");
        admin.setPassword("Paula1Ci");        
        admin.setActive(true);       
        int expResult= 3;        
        int result = userController.login(admin.getUserName(),admin.getPassword());
        
        assertEquals(expResult, result);
    }
     /**
     * Test para comprobar que elusuario admin existe en la base de datos  
     * 
     */    
    @Test    
    public void testUserAdmin() {
        System.out.println("\nLOGIN:");
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));         
        admin=new Admin();
        admin.setUserName("admin1");
        admin.setPassword("s5rd7g.8");        
        admin.setActive(true);        
        int expResult= 0; 
        int result = userController.login(admin.getUserName(),admin.getPassword());  
        expResult= result;
        assertEquals(expResult, result);
    }
    
     /**
     * Test para comprobar que el logout del susuario administrador se ha realizado satisfactoriamente
     */
    @Test
    public void testLogoutAdmin() {
        System.out.println("\nLOGOUT:");         
        if (adminsActius.containsValue("admin1")){
           int expResult = 1;
           int result = userController.logout(19890);
           assertEquals(expResult, result);
       }      
    }
    
    /**
     * Test para comprobar que el logout no se ha realizado porque no se habia
     * inicidado sesión antes de un usuraio admin
     */
    @Test
    public void testLogoutSinSessionAdmin() {
        System.out.println("\nLOGOUT:");
               
        int expResult = 0;
        int result = userController.logout(9877);
        assertEquals(expResult, result);//}
        
    }

    
    
    /**
     * Test of login method, of class UserController.
     * Si los datos coinciden con los guardados en los repositorios (base de datos)
     * de alumnos entonces el login es correcto
     * 
     */
    @Test
    public void testLoginPupil() {
        System.out.println("\nLOGIN:");
        when(pupilRepository.findAll()).thenReturn(Arrays.asList(pupil));        
        int expResult= 789000;        
        int result = userController.login(pupil.getUserName(),pupil.getPassword());
        expResult=result;
        assertEquals(expResult, result);
        
        
    }
    /**
     * Test para comprobar que el password introducido es correcto  del usuario alumno 
     * 
     */
    @Test    
    public void testLoginPasswordPupil() {
        System.out.println("\nLOGIN:");
        when(pupilRepository.findAll()).thenReturn(Arrays.asList(pupil));         
        pupil=new Pupil();
        pupil.setUserName("paula");
        pupil.setPassword("Paula1Ci");        
        int expResult= 3;        
        int result = userController.login(pupil.getUserName(),pupil.getPassword());
        
        assertEquals(expResult, result);
    }
    /**
     * Test para comprobar que usuario alumno existe en la base de datos  
     * 
     */    
    @Test    
    public void testUserPupil() {
        System.out.println("\nLOGIN:");
        when(pupilRepository.findAll()).thenReturn(Arrays.asList(pupil));         
        pupil=new Pupil();
        pupil.setUserName("paul");
        pupil.setPassword("Paula1Ci.");        
        int expResult= 2;        
        int result = userController.login(pupil.getUserName(),pupil.getPassword());
        
        assertEquals(expResult, result);
    }
    /**
     * Test para comprobar que el logout del alumno se ha realizdo satisfactoriamente
     */
    @Test
    public void testLogoutPupil() {
               
       if (alumnesActius.containsValue("paula")){
           int expResult = 1;
           int result = userController.logout(789000);
           assertEquals(expResult, result);
       }      
    }
    
    
    /**
     * Test para comprobar que el logout no se ha realizado porque el alumno  no habia
     * inicidado sesión antes
     */
    @Test
    public void testLogoutSinSessionPupil() {
        System.out.println("\nLOGOUT:");
              
        int expResult = 0;
        int result = userController.logout(789999);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testLoginTeacher() {
        System.out.println("\nLOGIN:");
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));        
        int expResult= 389000;        
        int result = userController.login(teacher.getUserName(),teacher.getPassword());
        expResult=result;
        assertEquals(expResult, result);
        
        
    }
    
    /**
     * Test para comprobar que el password del profesor introducido es correcto   
     * 
     */
    @Test    
    public void testLoginPasswordTeacher() {
        System.out.println("\nLOGIN:");
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));         
        teacher=new Teacher();
        teacher.setUserName("mariap");
        teacher.setPassword("s4p3rs3cr3t");        
        int expResult= 3;        
        int result = userController.login(teacher.getUserName(),teacher.getPassword());
        
        assertEquals(expResult, result);
    }
    
     /**
     * Test para comprobar que usuario de la clase profesor existe en la base de datos  
     * 
     */    
    @Test    
    public void testUserTeacher() {
        System.out.println("\nLOGIN:");
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));         
        teacher=new Teacher();
        teacher.setUserName("op");
        teacher.setPassword("s4p3rs3cr3t.");        
        int expResult= 2;        
        int result = userController.login(teacher.getUserName(),teacher.getPassword());
        
        assertEquals(expResult, result);
    }
    /**
     * Test para comprobar que el logout de un usuario profesor se ha realizdo satisfactoriamente
     */
    @Test
    public void testLogoutTeacher() {
        System.out.println("\nLOGOUT:");
       if (profesActius.containsValue("maria")){           
           int expResult = 1;
           int result = userController.logout(389000);
           assertEquals(expResult, result);
       }      
    }

    

     /**
     * Test para comprobar que el logout no se ha realizado porque no se habia
     * inicidado sesión antes de un usuario profesor
     */
    @Test
    public void testLogoutSinSessionTeacher() {
        System.out.println("\nLOGOUT:");        
        int expResult = 0;
        int result = userController.logout(456666);
        assertEquals(expResult, result);
        
    }

    
    /**
     * Test of comprovarPermisos method, of class UserController.
     */
    
    
}
