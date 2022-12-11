package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyectoDam.Model.School;
@Repository
    public interface SchoolRepository extends JpaRepository<School, Long> {
    
    
}
