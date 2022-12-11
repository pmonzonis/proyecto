
package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;





import proyectoDam.Model.*;
@RestController
public class TeacherController {
    
    @Autowired    
    TeacherRepository teacherRepository;
    @Autowired    
    SchoolRepository schoolRepository;
    
    UserController uc= new UserController();
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    static HashMap <String,Integer> profeActius =new HashMap<>();
   
    /** Método para dar de alta un profesor      
     * @param newTeacher
     * @param codi siendo el código de sesión
     * @return resultado,
     * Si lo intenta anadir un usuario administrador y
     * el profesor existe devuelve 0,si lo anadimos devuelve 1
     * Si el no habiamos iniciado sesión previamente devuelve 4
     * Si el usuario que lo intenta anadir no es administrador devuelve :
     * 2 (profesor) o 3 (alumno)
     * otros errores -1;
     */
    @PostMapping("/teachers/register/{codi}")
    public int registraProfesor(@Valid @RequestBody Teacher newTeacher, @PathVariable int codi) {
      int permis=uc.comprovarPermisos(codi);
      int resultado=-1;
      System.out.print(codi + "  "+ permis);
      if( permis==1){
            System.out.print("Alta profesor: ");
            List<Teacher> teachers = teacherRepository.findAll(); 
            System.out.println("Nuevo profesor: " + newTeacher.getUserName());
            boolean existTeacher=false;            
            for (Teacher other : teachers) {   
                System.out.print(other.getUserName());
                //si el alumno ya esta dado de alta en la base de datos devolvemos 0
                if (other.getUserName().contentEquals(newTeacher.getUserName())) {
                    System.out.println("\nEl profesor ya existe");
                    existTeacher=true;
                   
                }
            }
            if(existTeacher){
                System.out.println("\nEl administrador ya habia sido registrado con anterioridad");
                resultado=0; 
            }else{
                newTeacher.setPassword(bCryptPasswordEncoder.encode(newTeacher.getPassword()));
                teacherRepository.save(newTeacher);
                    resultado =1;
            }
      }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");   
          resultado=permis;
      }else{
          System.out.print("\nNo tienes permisos para dar de alta profesores");
          resultado=permis;
      }   
        
      return resultado;
    }
    
    /**
     * Método para listar los profesores
     * @param codi (código de sesión del usuario administrador)      
     * @return lista
     */
    @PostMapping("/teachers/all/{codi}")
    public List<Teacher> listTeacher(@PathVariable int codi) {
       
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        if( permis==1){
            List<Teacher> teachers = teacherRepository.findAll();
            if(teachers!=null){
                for (Teacher other :teachers) {
                    if (other!=null) {                
                        return teacherRepository.findAll();
                    }
                }               
            }else{
                System.out.print ("Listado vacio");
            }        
      }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");          
      }else{
          System.out.print("\nNo tienes permisos");
      }      
      return null;
    }
    
    /**
     * Método que elimina un profesor según su id
     * @param id
     * @param codi código de sesión del usuario administrador
     * @return resultado, si eliminamos el profesor devuelve 1 en caso contrario 2
     * otros errores 0
     */
    @RequestMapping(value = "/teachers/{id}/{codi}", method = RequestMethod.DELETE)
    public int deleteTeacherById(@PathVariable long id , @PathVariable int codi) {
        System.out.print("\n"+id +"\t");
        boolean eliminado= false;
        int resultado=0;
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        if( permis==1){
            List<Teacher> teachers =teacherRepository.findAll();
            for (Teacher other :teachers) {
                if(eliminado==false){
                    Long otherName=other.getId();
                    //String otherSurname=other.getSurame();
                    System.out.print("\n"+otherName+"\t\n");
                    if((otherName==id)){// si existe dicho Id eliminamos
                        teacherRepository.delete(other);
                        System.out.print("Eliminado");
                        eliminado=true;                   
                        resultado=1;
                    }else{//en caso contrario asignamos  valor 2                        
                        resultado=5;
                    }
                }            
           }
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");          
        }else{
          System.out.print("\nNo tienes permisos");
        }    
        if(resultado==5){
            System.out.print("\nProfesor con id  " + id + " no existe, no se puede borrar\n");
        }
        return resultado;
    } 
    
    /**
     * Método  que elimina un profesor según su nombre de usuario
     * @param username     * 
     * @param codi código de sesión del usuario administrador
     * @return resultado, si lo eliminamos devuelve 1, en caso contrario 2, otros errores 0
     */
    @RequestMapping(value = "/teachers/delete/{username}/{codi}", method = RequestMethod.DELETE)
    public int deleteTeacherByUsername(@PathVariable String username , @PathVariable int codi) {
        System.out.print("\n"+username +"\t");
        boolean eliminado= false;
        int resultado=0;
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        if( permis==1){
            List<Teacher> teachers = teacherRepository.findAll();
            for (Teacher other :teachers) {
                if(eliminado==false){                 
                    if((other.getUserName().contentEquals(username))){
                        teacherRepository.delete(other);
                        System.out.print("Eliminado");
                        eliminado=true; 
                        resultado=1;
                    //en caso contrario mostramos mensaje
                    }else{

                        resultado=2;
                    }
                }

           }
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");          
        }else{
          System.out.print("\nNo tienes permisos");
        }
        if(resultado==2){
            System.out.print("\nProfesor con nombre de susuario " + username + " no existe, no se puede borrar\n");
        }
        
        
       return resultado;        
    } 
    
   @RequestMapping("/teacherforschoolasig/{school}/{teacher}")
   @ResponseBody
   
    public List<Teacher> asignarSchool(@PathVariable String school , @PathVariable String teacher) {
        List<Teacher> teachers = teacherRepository.findAll();
        List <Teacher> ListTeacherSchool= new ArrayList<Teacher>();
        List<School> schools = schoolRepository.findAll();
        List <School> ListSchool= new ArrayList<School>();
       
        boolean trobat=false;
        for (School other : schools) {
            System.out.print("Nom"+other.getName());
            if (other!=null) {
                if(trobat==false){
                    if(other.getName().contentEquals(school)){
                        System.out.print("Nom"+other.getName());
                        for (Teacher tc:teachers){
                            if(tc!=null){
                                if(tc.getUserName().contentEquals(teacher)){                           
                                ListTeacherSchool.add(tc);
                                trobat=true;
                                System.out.print("Hollllla"+ ListSchool);
                            }
                                System.out.print("-----------");
                            }
                        
                    }
                  //teacherSchool.add(); 
                }
                System.out.print("------mmmmmm-----");
                }
                
            }  
            System.out.print("------hhhhhhhhhm-----");
            return ListTeacherSchool;
        }
        
        return ListTeacherSchool;
    }
    
    
    /**
     * Método para actualizar  password de un profesor, sólo permitida por el mismo usuario o un adminsitrador
     * 
     * @param id del profesor a cambiar
     * @param name del profesor
     * @param password nuevo
     * @param codi   de sesión del usuario que quiere realizar la acción
     * @return resultado, valor entero
     *   1, alumno modificado correctamente
     *   2, no tienes permisos, eres un profesor diferente
     *   3, no tienes permisos, eres un alumno 
     *   4, no hay sesión iniciada
     *   5 no  existe el alumno y  por tanto no se puede modificar
     *   0 , otros errores
     */
    @PutMapping("/teacher/actualizar/{id}/{name}/{password}/{codi}")
    public int actualizarPasswordTeacher( @Valid @PathVariable long id,@PathVariable String name,@PathVariable String password, @PathVariable int codi) {  
        int resultado=0;
        boolean exist= false;
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        boolean mateix=false;
        for (String user:profeActius.keySet()){
             int valor = profeActius.get(name);
             if(valor==codi){
                 mateix=true;
             }
            
        }
        if( permis==1|| (permis==2 && mateix==true)){
            System.out.print("----------------\n\tModifica  password profesor: ");
            List<Teacher> teachers = teacherRepository.findAll(); 
                if (teachers!=null){
                    for (Teacher tech : teachers) {            
                //si el alumno ya esta dado de alta en la base de datos devolvemos 0
                    if (tech.getId()==id) {
                        
                        tech.setPassword(bCryptPasswordEncoder.encode(password));
                        teacherRepository.saveAndFlush(tech);
                        System.out.print("Profesor modificado correctamente ");
                        exist=true;
                        resultado= 1;
                    }         
                } 
            }            
            if(exist==false){
                System.out.print("No existe el profesor a modificar");
                resultado=5;
            }
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");          
        }else{
          System.out.print("\nNo tienes permisos");
          resultado=permis;
        }  
        return resultado;
    }    
    
    @PutMapping("/teacherforschool/{id}/{name}/{codi}")
    public int asignarSchool(@Valid @PathVariable long id, @PathVariable String name, @PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        int resultado =-1;
        if (permis==1 || permis ==2){
            List<Teacher> teachers = teacherRepository.findAll();
            Set<Teacher> setTeacher = new HashSet<>(teachers);
            
            List<School> schools = schoolRepository.findAll();
            Set<School> setSchool = new HashSet<>(schools);
            //List <School> ListSchool= new ArrayList<School>();
            if (teachers !=null && schools!=null){
                for (School other : schools) {
                    if (other!=null) {
                        if(other.getName().contentEquals(name)){ 
                            for (Teacher tc:teachers){
                                if(tc.getId()==id){
                                   setSchool.add(other);
                                   setTeacher.add(tc);
                                   resultado=1;                                    
                                }
                            }                  
                        }
                    }  
                }                    
            }            
        } else {
            resultado=2;
            System.out.print("No tienes permisos");
        }        
        return resultado;
    }
    
     @PutMapping("/teacherforschoolww/{id}/{name}/{codi}")
    public Set <Teacher> devolverTeachersBySchool(@Valid @PathVariable long id, @PathVariable String name, @PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        int resultado =-1;
        if (permis==1 || permis ==2){
            List<Teacher> teachers = teacherRepository.findAll();
            Set<Teacher> setTeacher = new HashSet<>(teachers);
            
            List<School> schools = schoolRepository.findAll();
            //List <School> ListSchool= new ArrayList<School>();
            if (teachers !=null && schools!=null){
                for (School other : schools) {
                    if (other!=null) {
                        if(other.getName().contentEquals(name)){ 
                            for (Teacher tc:teachers){
                                if(tc.getId()==id){
                                    
                                    //setTeacher.add(tc);
                                    //other.setTeachers( );
                                    resultado=1;
                                    Iterator <Teacher> itr= setTeacher.iterator();
                                    while (itr.hasNext()){
                                        Teacher next= itr.next();
                                        System.out.print(next);
                                    }
                                    return setTeacher;
                                }
                            }                  
                        }
                    }  
                }                    
            }           
        } else {
            resultado=2;
            System.out.print("No tienes permisos");
        }        
        return null;
    }
}


