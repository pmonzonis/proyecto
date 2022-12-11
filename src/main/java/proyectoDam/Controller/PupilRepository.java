package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import proyectoDam.Model.Pupil;
@Repository
    public interface PupilRepository extends JpaRepository<Pupil, Long> {  

   
}
