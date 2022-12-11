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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import proyectoDam.Model.Admin;


/**
 *
 * @author Paula Monzonis Fortea
 */

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
    @Mock
    AdminRepository adminRepository;    
    
    @Mock
    AdminController adminController;
    @Mock
    UserController userController;
    @InjectMocks
    HashMap adminsActius =new HashMap<>();  
    
    @InjectMocks
    Admin admin;   
   
    UserController uc= new UserController();
    
    public AdminControllerTest() {
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
        adminRepository.save(admin);
       
        adminsActius.put("admin1", 10001);    
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    
    
    /**
     * Test para para listar usuarios admin
     */
    @Test
    public void testListAdminOk(){
        System.out.println("\nLista Administradores:");
        //AdminController instance = new AdminController();
       
        
        
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));
        adminsActius.put("admin1", 10001);
        List<Admin> expResult = adminRepository.findAll();
        
        List<Admin> result = adminController.listAdmins(10001);
       
        System.out.print("\n\t"+ adminRepository.findAll()+"\t");
        System.out.print("\n\t"+ adminsActius+"\t");
        System.out.print("\n\tResult "+result);
        assertEquals(expResult, result);      
         
    }
    
    /**
     * Test para para listar usuarios admin
     */
    @Test
    public void testListAdminNopermisAlumne(){
        System.out.println("\nLista Administradores:");
        //AdminController instance = new AdminController();
        int codi= 200001;
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));          
        List<Admin> expResult = adminRepository.findAll();
        List<Admin> result = adminController.listAdmins(codi);
        
        System.out.print("\n\t"+ adminRepository.findAll()+"\t");
        assertEquals(expResult, result);      
         
    }
    /**
     * Test del método registraAdmin de la classe AdminController
     * si el usuario ya existe
     */
    
    @Test
    public void testRegistraAdminExistent() {
        System.out.println("\nRegistra Administrador");
        Admin newAdmin = new Admin();
        int codi=10001;
        newAdmin.setUserName("admin1");
        newAdmin.setPassword("s5rd7g.8");
        newAdmin.setActive(true);
        adminRepository.save(newAdmin);
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));         
        int expResult = 0;
        int result = adminController.registraAdmin(newAdmin,codi);
        assertEquals(expResult, result);        
        
    }
    
     /**
     * Test del metodo registraAdmin de la clase AdminController.
     * si el usuario es nuevo
     */
    
    @Test
    public void testRegistraAdminNou() {
        System.out.println("\nRegistra Administrador");
        Admin newAdmin = new Admin();
        int codi=10001;
        newAdmin.setUserName("admin2");
        newAdmin.setPassword("s6yh84!");
        newAdmin.setActive(true);
        adminsActius.put("admin1", codi);
        adminRepository.save(newAdmin);
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin)); 
                
        //AdminController instance = new AdminController();
        int expResult = 1;
        int result = adminController.registraAdmin(newAdmin,codi);
        assertEquals(expResult, result);
       
        
    }
    

    

    /**
     * Test del método deleteAdminByUsername method de la clase AdminController.
     *  si el usuario existe
     */
    @Test
    public void testDeleteAdminByUsernameExist() {
        System.out.println("\nBorra Administrador por nombre de ususario");
        String username = "admin1";
        int codi=7890;
        //AdminController instance = new AdminController();
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));
        int result= adminController.deleteAdminByUsername(username,codi);
        int expResult = 1;
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test del método deleteAdminByUsername method de la clase AdminController.
     *  si el usuario no existe
     */
    @Test
    public void testDeleteAdminByUsernameNoExist() {
        System.out.println("\nBorra Administrador por nombre de ususario");
        String username = "admin2";
        int codi=7890;
        //AdminController instance = new AdminController();
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));
        int result= adminController.deleteAdminByUsername(username,codi);
        int expResult = 2;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
