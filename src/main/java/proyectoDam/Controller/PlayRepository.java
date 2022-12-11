package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import proyectoDam.Model.Play;
@Repository
    public interface PlayRepository extends JpaRepository<Play, Long> {  

   
}
