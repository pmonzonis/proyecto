
package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import static proyectoDam.Controller.AdminController.adminsActius;
import static proyectoDam.Controller.PupilController.alumnesActius;
import static proyectoDam.Controller.TeacherController.profeActius;
import proyectoDam.Model.*;



@RestController
@Service
public class UserController {
    
    
    @Autowired
    AdminRepository adminRepository;
    
    @Autowired
    PupilRepository pupilRepository;
    
    @Autowired
    TeacherRepository teacherRepository;
    
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    Pupil pupil = new Pupil();
    Admin admin = new Admin();
    Teacher teacher = new Teacher();
    
    boolean correcte=false;
    /**
     * Metodo par logearse, si el usario existe devuelve código único de sesión      
     * @param us
     * @param  
     * @return numero entero
     * > codigo alfanumerico aleatorio único de sesión
     * valores comprendidos entre:
     * si es admin 10000,299999
     * si es teacher 200000,499999
     * si es pupil 500000,899999
     * >1 Si intenta volver a logearse y ya lo estaba(sesión iniciada)
     * >2 Si el usuario no existe
     * >3 Si la contrasena es incorrecta
     */
    @PostMapping("/loginnou/{usuario}/{contra}")
    
    public int login(@Valid @PathVariable String usuario, @PathVariable String contra) {
        
        String user="";
        //String usuario= us.getUserName();
        //String password=us.getPassword();
        //System.out.print(us.getClass());
        System.out.print("\n"+adminRepository.findAll());        
        System.out.print("\n"+pupilRepository.findAll());
        System.out.print("\n"+teacherRepository.findAll());
        System.out.print("\nAdmin actius"+adminsActius);
        
        
        
            List<Teacher> teachers = teacherRepository.findAll(); 
            
            
            for (Teacher other : teachers) { 
                String usuarioOther= other.getUserName();
                String passwordOther= other.getPassword();
                System.out.print("\t"+usuarioOther);
                if (bCryptPasswordEncoder.matches(contra, passwordOther)){
                     correcte=true;
                }

                // si el usuario existe y ha introucido bien al contrasena
                if(other.getUserName().contentEquals(usuario)&& correcte==true){
                    
                    int codi=0;
                    System.out.print("\nHello");
                    
                    //sino habia iniciado sesión antes generamos y guardamos código único 
                    if(!profeActius.containsKey(usuario)){ 
                                

                        Random random = new Random();
                        codi= random.nextInt(200000,499999);
                        user= other.getUserName();                
                        System.out.print("\nSesion iniciada " + user+ "\n");
                        profeActius.put(user,codi);  

                        return codi;
                    }else {//si hay una sesion activa de este usuario devuelve 1
                       System.out.print("\n" + usuarioOther+ " ya tienes uns sesion iniciada");                    
                       return 1;
                    }                

                }else{//si ha introducido mal el password
                    if ((usuarioOther.equals(usuario)&& (!passwordOther.equals(contra)))){
                        System.out.print("\n Password incorrecto\n");
                        return 3;
                    }
                }            
            } 
        
        List<Pupil> pupils = pupilRepository.findAll();
            
        for (Pupil other : pupils) { 
            String usuarioOther= other.getUserName();
            String passwordOther= other.getPassword();
            System.out.print("\t"+usuarioOther);
        
            if (bCryptPasswordEncoder.matches(contra, passwordOther)){
                correcte=true;
            }
            if(other.getUserName().contentEquals(usuario)&& correcte==true){// si el usuario existe y ha introucido bien al contrasena
                int codi=0;
                
                if(!alumnesActius.containsKey(usuario)){   //sino habia iniciado sesión antes generamos y guardamos código único             
               
                    Random random = new Random();
                    codi= random.nextInt(500000,899999);
                    user= other.getUserName();                
                    System.out.print("\n\tSesion iniciada " + user+ "\n\n");
                    alumnesActius.put(user, codi); 
                    System.out.print(alumnesActius);
                    
                    return codi;
                }else {//si hay una sesion activa de este usuario devuelve 1
                   System.out.print("\n\t" + usuarioOther+ " ya tienes uns sesion iniciada\n\n");                    
                   return 1;
                }                
            
            }else{//si ha introducido mal el password
                if ((usuarioOther.equals(usuario)&& (!passwordOther.equals(contra)))){
                    System.out.print("\n\t Password incorrecto\n\n");
                    return 3;
                }
            }            
        }  
        List<Admin> admins = adminRepository.findAll();      
               
          
        for (Admin other : admins) { 
            String usuarioOther= other.getUserName();
            String passwordOther= other.getPassword();
            System.out.print("\tListado usuario admin : \n Usuario: "+usuarioOther +"\t contrasena: " +passwordOther);
            System.out.print("\nUSUARIO"+ usuario + "\tcontra : "+ contra );
            
            if (bCryptPasswordEncoder.matches(contra, passwordOther)){
                correcte=true;
            }
            if(usuarioOther.contentEquals(usuario)&& correcte==true){//&& (passwordOther.contentEquals(password))){// si el usuario existe y ha introucido bien al contrasena
                int codi=0;
                System.out.print("Coincideix");
               
                    if(!adminsActius.containsKey(usuario)){   //sino habia iniciado sesión antes generamos y guardamos código único             
               
                    Random random = new Random();
                    codi= random.nextInt(10000,199999);
                    user= other.getUserName();                
                    System.out.print("\n\tSesion iniciada " + user+ "\n\n");
                    adminsActius.put(user, codi);  
                    System.out.print(adminsActius);
                    
                    return codi;
                    }else {//si hay una sesion activa de este usuario devuelve 1
                       System.out.print("\n\t" + usuarioOther+ " ya tienes uns sesion iniciada\n\n");                    
                       return 1;
                    }          
                    
                
                     
            
            }else{//si ha introducido mal el password
                if ((usuarioOther.equals(usuario)&& (!passwordOther.equals(contra)))){
                    System.out.print("\n\t Password incorrecto\n\n");
                    return 3;
                }
            }            
        }   
        
        // Si el usuario no se encuentra en la base de datos (no existe)
        System.out.print("\nUsuario incorrecto");
        return 2;
    } 
 
    /**
     * Método para cerrar sesión
     * Si cerramos la aplicación la sesión se cierra, no se guarda
     * @param codi
     * @return numero entero
     * si se ha cerrado correctamnete devuelve 1
     * otros errores 0 
     */
    @PostMapping("/logout/{codi}")
    public int logout(@PathVariable int codi) {
        System.out.print("\n--------------------------------------\n");
        //Comprobamos si existe código de sesión de usuario alumno
        for ( String user:alumnesActius.keySet()){
            int valor = alumnesActius.get(user);           
            if (valor==codi){// si existe eliminamos
                alumnesActius.remove(user);
                 System.out.print( "////////\nAlumnes actius: "+alumnesActius);
                 System.out.print("\n\t "+ user + "has cerrado sesión correctamente");
                 return 1;
            }
        }
        //Comprobamos si existe código de sesión de usuario profesor
        for ( String user:profeActius.keySet()){
            int valor = profeActius.get(user);            
            if (valor==codi){// si existe eliminamos
                profeActius.remove(user);
                 System.out.print( "////////\nPofesors actius: "+profeActius);
                 System.out.print("\n\t "+ user + "has cerrado sesión correctamente");
                 return 1;
            }
        }
        //Comprobamos si existe código de sesión de usuario administrador
        for ( String user:adminsActius.keySet()){
            int valor = adminsActius.get(user);            
            if (valor==codi){// si existe eliminamos
                adminsActius.remove(user);
                 System.out.print( "////////\nAdmin actius: "+adminsActius);
                 System.out.print("\n\t "+ user + "has cerrado sesión correctamente");
                 return 1;
            }
        }
        System.out.print("No han una sesion iniciada con este codigo");
        return 0;
    }    
    
    /**
     *
     * @param codi
     * @return
     */
    public int comprovarPermisos( int codi){
        int permiso=4;
        for ( String user:alumnesActius.keySet()){
            int valor = alumnesActius.get(user);           
            if (valor==codi){//comprovamos que el usuario esta activo y no ha cerrado sesión  (esta en el listado de usuarios alctivos)
                //asignamos permiso
               permiso=3;
            }            
        }
        //Comprobamos si existe código de sesión de usuario profesor
        for ( String user:profeActius.keySet()){
            int valor = profeActius.get(user);            
            if (valor==codi){//comprovamos que el usuario esta activo y no ha cerrado sesión  (esta en el listado de usuarios alctivos)
                //asignamos permiso
               permiso=2;
            }           
        }
        //Comprobamos si existe código de sesión de usuario administrador
        for ( String user:adminsActius.keySet()){
            
            int valor = adminsActius.get(user);            
            if (valor==codi){//comprovamos que el usuario esta activo y no ha cerrado sesión  (esta en el listado de usuarios activos)
                //asignamos permiso
               permiso=1;
            }           
        }        
        return permiso;
    }
    
    
//Desencriptació de la contrasenya amb Xor  
    public String desencriptar(String contrasenya){
        String resultat = "";
        char[] cont = contrasenya.toCharArray();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(contrasenya.length());
        for (int i = 0; i <cont.length; i++ ){
            char c = (char)(cont[i] ^ 12);
            resultat = resultat + c;
        }
        System.out.println("resultat: " + resultat);
        return resultat; 
    }
    
    
    @GetMapping("/hello")
    public void hello(){
        String resultat="hola";
        System.out.println("resultat: " + resultat);
         
    }
}
    
    