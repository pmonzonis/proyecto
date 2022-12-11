
package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import proyectoDam.Model.Admin;

@RestController
@Service
public class AdminController {
    @Autowired
    AdminRepository adminRepository;
   
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
   
    UserController uc= new UserController();
    
    static HashMap <String,Integer> adminsActius =new HashMap<>();   
    
    /**
     * Método para dar de alta un nuevo ususario administrador    
     * @param newAdmin
     * @param codi , codigo de sesión del usuario
     * @return 0 si ya esté dado de alta e la base de datos
     * si no existía y lo registramos devueelve 1
     */
    @PostMapping("/admins/register/{codi}")
    public int registraAdmin(@Valid @RequestBody Admin newAdmin ,@PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        int resultado=-1;
        if( permis==1){
            System.out.print("Alta alummno: ");
            List<Admin> admins = adminRepository.findAll();       
            System.out.println("Nuevo usuario: " + newAdmin.toString());        
            boolean existPupil = false;
            for (Admin admin : admins) {            
                //si el alumno ya esta dado de alta en la base de datos devolvemos 0
                if (admin.getUserName().contentEquals(newAdmin.getUserName())) {
                    existPupil = true;
                }                
            } 
            
            if(existPupil) {
                System.out.println("\nEl administrador ya habia sido registrado con anterioridad");
                resultado=0;                
            } else {
                newAdmin.setPassword(bCryptPasswordEncoder.encode(newAdmin.getPassword()));
                adminRepository.save(newAdmin);                
                resultado= 1;                
            }
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");   
          resultado=permis;
        }else{
          System.out.print("\nNo tienes permisos para dar de alta adminsitradores");
          resultado=permis;
        } 
        if (resultado ==1){
            System.out.print("Administrador "+ newAdmin.getUserName()+" registrado");
        }
        return resultado;
    }
    
    /**
     * Método para listar los objetos de la clase Admin
     * @param codi del usuario
     * @return lista de administradores
     */
    @PostMapping("/admins/all/{codi}")
    public List<Admin> listAdmins(@PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        if( permis==1){
            List<Admin> admins = adminRepository.findAll();
            if(admins!=null){
                for (Admin other : admins) {
                    if (other!=null) {                
                        return adminRepository.findAll();
                    }
                }
            }else{
                System.out.print ("Listado vacio");
            }
            
        }else{
            System.out.print("\nNo tienes permisos para listar adminsitradores");
            return null;
        }
        
        return null;
    }
        
    @PostMapping("admins/find")
    public List<Admin> find() {
        System.out.println("Buscando todos los administradores");
    return adminRepository.findAll();
    }
    
    /**
     * Método para eliminar un admin según el nombre de usuario     *
     * @param username
     * @param codi, código de sesión del usuario
     * @return resultad: 1>eliminado, 5> usuario no existe y no se puede eliminar
     * 0> otros errores
     */
    @RequestMapping(value = "/admins/{username}/{codi}", method = RequestMethod.DELETE)
    public int deleteAdminByUsername(@PathVariable String username ,@PathVariable int codi) {
        System.out.print("\n"+username +"\t");
        boolean eliminado= false;
        int resultado=0;
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        if( permis==1){
            List<Admin> admins = adminRepository.findAll();
            for (Admin other :admins) {
                if(eliminado==false){                
                    //si el usuario coincide con un ususraio admin de la base de datos eliminamos
                    if((other.getUserName().contentEquals(username))){
                        adminRepository.delete(other);
                        System.out.print("Eliminado");
                        eliminado=true;                   
                        resultado=1;
                    }else{// sino lo indicamos
                        
                        resultado= 5;
                    }
                }            
            }
        }
        else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");   
          resultado=permis;
        }else{
          System.out.print("\nNo tienes permisos para eliminar administradores");
          resultado=permis;
        } 
        if(resultado==5){
            System.out.print("\nAdministrador" + username + " no existe, no se puede borrar\n");
        }
        
       return resultado;
    }  
    
     /**
     * Método para actualizar  password de un adminsitrador, sólo permitida por el mismo usuario adminsitrador
     * 
     * @param id deladmin a cambiar     
     * @param password nuevo
     * @param codi   de sesión del usuario que quiere realizar la acción
     * @return resultado, valor entero
     *   1, admin modificado correctamente
     *   2, no tienes permisos, eres un profesor
     *   3, no tienes permisos, eres un alumno 
     *   4, no hay sesión iniciada
     *   5 no  existe el admin  y  por tanto no se puede modificar
     *   0 , otros errores
     */
    @PutMapping("/admin/actualizar/{id}/{password}/{codi}")
    public int actualizarPasswordAdmin( @Valid @PathVariable long id,@PathVariable String password, @PathVariable int codi) {  
        int resultado=0;
        boolean exist = false;
        boolean active;
        int permis=uc.comprovarPermisos(codi);        
        System.out.print(codi + "  "+ permis);
        if( permis==1 ){
            System.out.print("----------------\n\tModifica password administrador: ");
            List<Admin> admins = adminRepository.findAll(); 
            if(admins!=null){
                for (Admin ad : admins) {            
                //si el alumno ya esta dado de alta en la base de datos devolvemos 0
                if ((ad.getId()==id) ){                    
                    ad.setPassword(bCryptPasswordEncoder.encode(password));                    
                    adminRepository.saveAndFlush(ad);
                    System.out.print("Administrador modificado correctamente ");
                    exist=true;
                    resultado= 1;
                    }      
                }   
            }
            
            if(exist==false){
                System.out.print("No existe ela dministrador a modificar");
                resultado=5;
            }
        }else{
            System.out.print("\nNo tienes permisos para actualizar el  adminsitrador");
            resultado=permis;
        }
        if(permis==4){
            System.out.print("\nNo hay una sesion iniciada con este codigo");   
            resultado=permis;
        }
        
        
        return resultado;
       
    }    
    
    
    @PostMapping("/admins/register")
    public int registraAdmin1(@Valid @RequestBody Admin newAdmin) {
        //int permis=uc.comprovarPermisos(codi);
        //System.out.print(codi + "  "+ permis);
        int resultado=-1;
        //switch (permis) {
           // case 1:
                System.out.print("\n\tAlta administrador: ");
                List<Admin> admins = adminRepository.findAll();
                    if (!admins.isEmpty()){
                        System.out.println("\n\tNuevo usuario administrador: " + newAdmin.toString());
                        for (Admin admin : admins) {
                            //si el alumno ya esta dado de alta en la base de datos devolvemos 0
                            if (admin.getUserName().contentEquals(newAdmin.getUserName())) {
                                System.out.println("\n\tEl administrador ya habia sido registrado con anterioridad\n");
                                resultado= 0;
                            }else{
                                //newAdmin.setPassword(bCryptPasswordEncoder.encode(newAdmin.getPassword()));
                                System.out.print("Administrador" + newAdmin.getUserName() + " anadido correctamente");
                                // si es nuevo aádimos al repositorio y devolvemos 1
                                adminRepository.save(newAdmin);
                                resultado=1;
                            }
                        }   
                    }else {
                        System.out.print("Administrador" + newAdmin.getUserName() + " anadido correctamente");
                                // si es nuevo aádimos al repositorio y devolvemos 1
                                adminRepository.save(newAdmin);
                                resultado=1;
                    }
                
                
                //break;
            return resultado;
        }
        
        
    }
    


