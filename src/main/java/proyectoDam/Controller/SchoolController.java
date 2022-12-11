
package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


import proyectoDam.Model.*;
@RestController

public class SchoolController {
    @Autowired
    SchoolRepository schoolRepository;    
    @Autowired
    PupilRepository pupilRepository;
    @Autowired
    TeacherRepository teacherRepository;
    
    UserController uc= new UserController();
    
    /**
     * Método para añadir escuela a la base de datos 
     * si la escuela ya existe devuelve 0
     * si la añadimos devuelve 1
     * @param newSchool
     * @param codi
     * @return resultado
     */
    @PostMapping("/schools/register/{codi}")
    public int registerSchool(@Valid @RequestBody School newSchool, @PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        int resultado=-1;
        
        if( permis==1){
            List<School> schools = schoolRepository.findAll();       
            System.out.println("\nColegio: " + newSchool.getName());        
            System.out.print(schools);
            if (schools.isEmpty()){
                //schoolRepository.save(newSchool);
                resultado=1;
            }else{
                for (School school : schools) {            
                    String name= school.getName();
                    String nameNew= newSchool.getName();
                    if(name.contentEquals(nameNew)){                    
                        resultado=0;                    
                    }else{                    
                        //schoolRepository.save(newSchool);
                        resultado=1;
                    }                
               }      
            }
               
            
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");   
          resultado=permis;
        }else{
          System.out.print("\nNo tienes permisos para dar de alta escuelas");
          resultado=permis;
        }
        if (resultado==1){
            schoolRepository.save(newSchool);
            System.out.print("\nColegio " + newSchool.getName() + " anadido correctamente\n");
        }else if (resultado==0){
            System.out.println("\nLa escuela "+ newSchool.getName()+" ya existe\n");
        }
        
        return resultado;
    }
    
    /**
     * Método para eliminar una escuela en concreto
     * devuelve 0 si se ha podido borrar
     * en acso contrario devuelve 1
     * @param school
     * @return resultado
     */
    @DeleteMapping("/schools/deleted/{codi}")
    public int deleteSchool(@Valid @RequestBody School school, @PathVariable int codi) {
        int resultado=0;
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        if( permis==1){
            List<School> schools = schoolRepository.findAll();
            System.out.println("\nColegio: " + school.getName());  
            for (School schoold :schools) {
                String name= schoold.getName();
                String address= schoold.getAddress();
                String nameDel= school.getName();
                String addressDel= schoold.getAddress();

                if ((name.contentEquals(nameDel))&&(address.contentEquals(addressDel))) {
                    schoolRepository.delete(school);               
                    System.out.println("\nLa escuela "+ school.getName()+" borrada\n");
                    resultado=0;
                    return resultado;
                }
            }
            System.out.print("\nColegio " + school.getName() + " no existe, no se puede borrar\n");
            resultado=1;
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");   
          resultado=permis;
        }else{
          System.out.print("\nNo tienes permisos para dar de eliminar escuelas");
          resultado=permis;
        }
        
        return resultado;
    }
    
    /**Método que nos retorna los datos de una escuela
     * junto con el listado de los alumnos y los profesores que pertenecen a una escuela determinada
     *
     * @param school
     * @return
     */
    @PostMapping("/schoolpupilsteachers/{name}")
    public List<School> listPupilsTeacherSchool(School school) {
        List<School> schools = schoolRepository.findAll();
        
        List <School> schoolList= new ArrayList<School>();
        List <Pupil> pupilList= new ArrayList<Pupil>();
        List <Pupil> pupils= pupilRepository.findAll();
        List <Teacher> teachers = teacherRepository.findAll();
        List <Teacher> teacherList= new ArrayList<Teacher>();
        
        
        for (School other : schools) {
            System.out.print("jil----------");
            if (other!=null) {
                if(other.getName().contentEquals(school.getName())){                     
                  for(Pupil pup:pupils){
                      if (pup!=null){
                          if (pup.getSchool().getName().contentEquals(school.getName())){
                               pupilList.add(pup);
                               //other.setPupilsv(pupilList);
                               //schoolList.add(other);
                               //System.out.print("Lista alumnos"+  other.getPupilsv().toString());
                               
                          }
                      }                      
                  }
                  for (Teacher tech:teachers){
                      if (tech!=null){
                          /*if(tech.getSchool().contentEquals(school.getName())){
                              teacherList.add(tech);
                              other.setTeachersv(teacherList);
                              System.out.print("Lista profesores"+  other.getTeachersv().toString());
                          }*/
                      }
                  }
                  schoolList.add(other);
                  for (School nu:schoolList){
                        System.out.print("\n\t"+schoolList);
                        
                  }
                  System.out.print("\n\t"+pupils.toString());                  
                }
            }       
            return schoolList;
        }        
        return null;
    }
    
    
    
    /**
     * Metodo que permite eliminar un colegio por su nombre     
     * @param name
     * @return 1 exito, 5 no existe el colegio, -1 otros errores
     */
    @RequestMapping(value = "/schooldelete/{name}/{codi}", method = RequestMethod.DELETE)
    public int deleteSchoolByName(@PathVariable String name,@PathVariable int codi) {
        System.out.print(name);
        int resultado= -1;
        int permis=uc.comprovarPermisos(codi);
        System.out.print(codi + "  "+ permis);
        if( permis==1){
            List<School> schools = schoolRepository.findAll();
            for (School other :schools) {
            
                String otherName=other.getName();
                System.out.print(otherName+"\n");
                if(otherName.contentEquals(name)){
                    schoolRepository.delete(other);
                    System.out.print("Eliminado");
                    resultado=1;
                }else{
                     
                    resultado=5;
                }
            }            
        }else if(permis==4){
          System.out.print("\nNo hay una sesion iniciada con este codigo");   
          resultado=permis;
        }else{
          System.out.print("\nNo tienes permisos para dar de eliminar escuelas");
          resultado=permis;
        }
        if (resultado==5){
            System.out.print("\nColegio " + name + " no existe, no se puede borrar\n");
        }
       return resultado;
    }
      
    /**
     * Metodo para listar todas las escuelas
     * @param codi de sesión
     * @return school
     */
    @PostMapping("/schools/all/{codi}")
     public List<School> listSchool(@PathVariable int codi) {
        List<School> schools = schoolRepository.findAll();
        int permis=uc.comprovarPermisos(codi);
        switch (permis) {
            case 1:
                for (School other :schools) {
                    if (other!=null) {
                        return schoolRepository.findAll();
                    }
                }   break;
            case 4:
                System.out.print("\nNo hay una sesion iniciada con este codigo");
                break;
            default:
                System.out.print("\nNo tienes permisos para listar escuelas");
                break;
        }
        
        return null;
    }
    
    /**
     * Método para eliminar todas las escuelas 
     * @param codi de sesion
     * @return 0 si exito, 1 en acso contrario
     */
    @DeleteMapping("/school/deleteall/{codi}")
    public int deleteAllSchools(@PathVariable int codi) {
        int permis=uc.comprovarPermisos(codi);
        int resultado= -1;
        System.out.print(codi + "  "+ permis);
        switch (permis) {
            case 1:
                if (!schoolRepository.equals(null)){
                    List<School> schools = schoolRepository.findAll();
                    schoolRepository.deleteAll();
                    return 0;
                }   break;
            case 4:
                System.out.print("\nNo hay una sesion iniciada con este codigo");
                resultado=permis;
                break;
            default:
                System.out.print("\nNo tienes permisos para dar de eliminar escuelas");
                resultado=5;
                break;
        }
       resultado=1;
       return resultado;
    }
    
     /**
     * Método para actualizar una escuela
     * @param school
     * @param codi de sesion
     * @return 0 si exito, 1 en acso contrario
     */
 
    @PutMapping("/school/actualizar/{codi}")
    public int actualizarSchool( @RequestBody School school, @PathVariable int codi) {  
        int resultado=0;
        boolean exist =false;
        System.out.print("----------------\n\tModifica escuela: ");
        int permis=uc.comprovarPermisos(codi);
        if(permis==1){
            List<School> schools = schoolRepository.findAll();  
            for (School sc: schools) {            
                
                if ((sc.getName().contentEquals(school.getName())) && (exist ==false)) {
                    school.setId(sc.getId());
                    schoolRepository.saveAndFlush(school);
                    System.out.print("Escuela modificada correctamente ");
                    resultado= 1;
                    exist=true;
                }         
            }   
            if(exist==false){
                System.out.print("No existe la escuela a modificar");
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

