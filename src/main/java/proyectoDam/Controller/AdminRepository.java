package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import proyectoDam.Model.Admin;
@Repository
    public interface AdminRepository extends JpaRepository<Admin, Long> {  

   
}
