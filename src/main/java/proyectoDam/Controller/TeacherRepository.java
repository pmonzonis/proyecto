package proyectoDam.Controller;

/**
 *
 * @author Paula Monzonis Fortea
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyectoDam.Model.Teacher;
@Repository
    public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
