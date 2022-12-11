
package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import proyectoDam.Model.Play;
import proyectoDam.Model.Pupil;
import proyectoDam.Model.School;
import proyectoDam.Model.Teacher;


@RestController
@Service
public class PupilController {
    @Autowired
    PupilRepository pupilRepository;
     
    
    @Autowired
    PlayRepository playRepository;
     
    UserController uc= new UserController();
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    
    static HashMap <String,Integer> alumnesActius =new HashMap<>();
         
    /**
     * Método para dar de alta un nuevo alumno 
     * @param newPupil
     * @param codi sesion usuario
     * @return 0 si ya esté dado de alta en la base de datos
     * 1 si no existía y lo registramos
     */
    @PostMapping("/pupils/register/{codi}")
    public int registraAlumno(@Valid @RequestBody Pupil newPupil,@PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        int resultado=-1;
        System.out.print(codi + "  "+ permis);
        if( permis==1 || permis ==2){
            System.out.print("Alta alummno: ");
            List<Pupil> pupils = pupilRepository.findAll();       
            System.out.println("Nuevo usuario: " + newPupil.toString());        
            boolean existPupil = false;
            for (Pupil pupil : pupils) {            
                //si el alumno ya esta dado de alta en la base de datos devolvemos 0
                if (pupil.getUserName().contentEquals(newPupil.getUserName())) {
                    existPupil = true;
                }                
            } 
            
            if(existPupil) {
                System.out.println("\nEl alumno ya habia sido registrado con anterioridad");
                resultado=0;                
            } else {
                newPupil.setPassword(bCryptPasswordEncoder.encode(newPupil.getPassword()));
                pupilRepository.save(newPupil);                
                resultado= 1;                
            }
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");   
          resultado=permis;
        }else{
          System.out.print("\nNo tienes permisos para dar de alta alumnos");
          resultado=permis;
        } 
        if (resultado ==1){
            System.out.print("Alumno "+ newPupil.getUserName()+" registrado");
        }
        return resultado;
    }
    
    /**
     * Método para listar los objetos de la clase Pupils (alumnos)
     * @param codi usuario
     * @return lista de alumnos
     */
    @PostMapping("/pupils/all/{codi}")
    public List<Pupil> listPupils(@PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        
        System.out.print(codi + "  "+ permis);
        if( permis==1 || permis ==2){
            List<Pupil> pupils = pupilRepository.findAll();
            if(pupils!=null){
                for (Pupil other : pupils) {
                    if (other!=null) {                
                        return pupilRepository.findAll();
                    }
                }
            }else{
                System.out.print ("Listado vacio");
            }
            
        }else{
            System.out.print("\nNo tienes permisos para listar los alumnos");
        }
        
        return null;
    }
        
    /**
     * Método que nos permite obtener un listado de los alumnos según al colegio que asistan
     * @param school
     * @param codi
     * @return listado alumnos
     */
    @PostMapping("/pupilsbyschool/{name}/{codi}")
    public List<Pupil> listPupilsBySchool(School school,@PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        
        System.out.print(codi + "  "+ permis);
        if( permis==1 || permis ==2){
            List<Pupil> pupils = pupilRepository.findAll();
            List <Pupil> pupilSchool= new ArrayList<Pupil>();
            //List <Pupil> pupilS= new ArrayList<>();
            for (Pupil other : pupils) {// comprobamos si de la lista de alumnos coincide la escuela que bsucamos
                if (other!=null) {
                    if(other.getSchool().equals(school.getName())){                  
                      pupilSchool.add(other); 
                    }
                }            
            }
            for (Pupil nu:pupilSchool){
                System.out.print("\n\t"+pupilSchool);
                return pupilSchool;
            }
        }
        System.out.print("\nNo tienes permisos para listar los alumnos");
        
        return null;
    }
    
    
    
    
    
    
    @PostMapping("pupils/find")
    public List<Pupil> find() {
        System.out.println("Buscando todos los alumnos");
        return pupilRepository.findAll();
    }
    
    
    
    /**
     * Método para eliminar un alumno según el nombre de usuario     *
     * @param username
     * @param codi usuario
     * @return resultado, 1 éxito, 2 no existe en la base de datos y por tanto no se 
     * puede borrar, 0 otros errores
     */
    @RequestMapping(value = "/pupils/{username}/{codi}", method = RequestMethod.DELETE)
    public int deletePupilByUsername(@PathVariable String username,@PathVariable int codi ) {
        System.out.print("\n"+username +"\t");
        boolean eliminado= false;
        int resultado=0;
        int permis=uc.comprovarPermisos(codi);        
        System.out.print(codi + "  "+ permis);
        if( permis==1 || permis ==2){
            List<Pupil> pupils = pupilRepository.findAll();
            for (Pupil other :pupils) {
                if(eliminado==false){
                    String otherName=other.getName();
                    //String otherSurname=other.getSurname();
                    
                    //si el ususario de tipo de alumno existe en la base de datos lo eliminamos
                    if((other.getUserName().contentEquals(username))){
                        pupilRepository.delete(other);
                        System.out.print("Eliminado");
                        eliminado=true; 
                        resultado=1;

                    }else{// en caso contrario mostramos aviso
                         
                        resultado=2;
                    }
                }            
            }
            
       }else {
            System.out.print("\nNo tienes permisos para eliminar  alumnos");        }
       if (resultado==2){
           System.out.print("\nAlumno" + username + " no existe, no se puede borrar\n");
       }
        return resultado;
        
    } 
    
    /**
     * Método para actualizar  password de un alumno, sólo permitida por el mismo usuario o un adminsitrador
     * 
     * @param id del alumno a cambiar
     * @param username del alumno
     * @param password nuevo
     * @param codi   de sesión del usuario que quiere realizar la acción
     * @return resultado, valor entero
     *   1, alumno modificado correctamente
     *   2, no tienes permisos, eres un profesor
     *   3, no tienes permisos, eres un alumno diferente
     *   4, no hay sesión iniciada
     *   5 no  existe el alumno y  por tanto no se puede modificar
     *   0 , otros errores
     */
    @PutMapping("/pupilsactualizar/{id}/{username}/{password}/{codi}")
    public int actualizarPasswordPupil( @Valid @PathVariable long id,@PathVariable String username,@PathVariable String password, @PathVariable int codi) {  
        int resultado=0;
        boolean exist = false;
        int permis=uc.comprovarPermisos(codi);        
        System.out.print(codi + "  "+ permis);
        
         boolean mateix=false;
        for (String user:alumnesActius.keySet()){
             int valor = alumnesActius.get(username);
             if(valor==codi){
                 mateix=true;
             }
            
        }
        
        if( permis==1 || mateix==true){
            System.out.print("----------------\n\tModifica alummno: ");
            List<Pupil> pupils = pupilRepository.findAll();  
            if(pupils!=null){
                for (Pupil pup : pupils) {            
                //si el alumno ya esta dado de alta en la base de datos devolvemos 0
                    if (pup.getId()==id){
                        pup.setPassword(bCryptPasswordEncoder.encode(password));
                        pupilRepository.saveAndFlush(pup);
                        System.out.print("Alumno modificado correctamente ");
                        exist=true;
                        resultado= 1;
                    }      
                }   
            }            
            if(exist==false){
                System.out.print("No existe el alumno a modificar");
                resultado=5;
            }
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");          
        }else{
            resultado= permis;
            System.out.print("\nNo tienes permisos");
        }  
        return resultado;
    } 

    
    
    /**
     * Método para actualizar  puntuación de un alumno, sólo permitida por un adminsitrador
     * o un profesor
     * @param id del alumno a actualizar
     * @param score nueva puntuación
     * @param codi   de sesión del usuario que quiere realizar la acción
     * @return resultado, valor entero
     *   1, Puntuación actualizada correctamente por parte del admin
     *   2, Puntuación actualizada correctamente por parte del profesor
     *   3, no tienes permisos, eres un alumno diferente
     *   4, no hay sesión iniciada
     *   5 no  existe el alumno y  por tanto no se puede modificar
     *   0 , otros errores
     */
    
    @PutMapping("/pupilsactualizarpuntuacion/{id}/{score}/{codi}")
    public int actualizarPuntuacionPupil( @Valid @PathVariable long id,@PathVariable long score, @PathVariable int codi) {  
        int resultado=0;
        boolean exist = false;
        int permis=uc.comprovarPermisos(codi);        
        System.out.print(codi + "  "+ permis);        
         
        
        
        if( permis==1 || permis==2 ){
            System.out.print("----------------\n\tModifica alummno: ");
            List<Pupil> pupils = pupilRepository.findAll();  
            if(pupils!=null){
                for (Pupil pup : pupils) {            
                //si el alumno ya esta dado de alta en la base de datos devolvemos 0
                    if (pup.getId()==id){
                        
                        pup.setScore(score);
                        //pupilRepository.save(pup);
                        pupilRepository.saveAndFlush(pup);
                        System.out.print("Puntuación actualizada correctamente ");
                        exist=true;
                        resultado= permis;
                    }      
                }   
            }
            
            if(exist==false){
                System.out.print("No existe el alumno ");
                resultado=5;
            }
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");          
        }else{
            resultado= permis;
            System.out.print("\nNo tienes permisos");
        }  
        return resultado;
    } 

    
  /**
     * Método para asignar un juego a un alumno
     * 
     * @param id del alumno a quien se le asigna el juego
     * @param idplay id del juego a a signar
     * @param codi   de sesión del usuario que quiere realizar la acción
     * @return resultado, valor entero
     *   1, alumno modificado correctamente
     *   2, no tienes permisos, eres un profesor
     *   3, no tienes permisos, eres un alumno diferente
     *   4, no hay sesión iniciada
     *   5 no  existe el alumno y  por tanto no se puede modificar
     *   0 , otros errores
     */
    
 @PutMapping("/asignarplay/{id}/{idplay}/{codi}")
    public int asignarJuego(@Valid @PathVariable long id, @PathVariable long idplay, @PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        int resultado =0;
        boolean existid=false;
        if (permis==1 || permis ==2){                        
            Pupil pupilbuscat=pupilRepository.getById(id);
            if(pupilbuscat!=null){
                Play playbuscat=playRepository.getById(idplay);
                if(playbuscat!=null){ 
                    if(pupilbuscat.getPlays() == null) {
                        pupilbuscat.setPlays(new HashSet<Play>());
                    }
                    pupilbuscat.getPlays().add(playbuscat);
                    pupilRepository.save(pupilbuscat);
                    resultado =permis;
                }
            }    
        } else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");          
        }else {
            resultado=permis;
            System.out.print("No tienes permisos");
        }        
        return resultado;    
    }  
    
    
    
    @PutMapping("/pupilsactualizarplay/{id}/{codi}")
    public int actualizarUsernamePupil( @Valid @PathVariable long id, @PathVariable int codi , @RequestBody Play newplay ) {  
        int resultado=0;
        boolean exist = false;
        int permis=uc.comprovarPermisos(codi);        
        System.out.print(codi + "  "+ permis);        
         
        
        
        if( permis==1 ){
            System.out.print("----------------\n\tModifica alummno: ");
            List<Pupil> pupils = pupilRepository.findAll();  
            if(pupils!=null){
                for (Pupil pup : pupils) {            
                //si el alumno ya esta dado de alta en la base de datos devolvemos 0
                    if (pup.getId()==id){
                        Set <Play> plays=(Set <Play>) playRepository.findAll();
                        plays.add(newplay);
                        pup.setPlays(plays);
                        //pupilRepository.save(pup);
                        pupilRepository.saveAndFlush(pup);
                        System.out.print("Alumno modificado correctamente ");
                        exist=true;
                        resultado= 1;
                    }      
                }   
            }
            
            if(exist==false){
                System.out.print("No existe el alumno a modificar");
                resultado=5;
            }
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");          
        }else{
            resultado= permis;
            System.out.print("\nNo tienes permisos");
        }  
        return resultado;
    } 
}


