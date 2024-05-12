package service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service.entity.Appointment;
import service.entity.Compari;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompariRepository extends JpaRepository<Compari, Integer> {
    Optional<Compari> findByNameProduct(String name);
    @Query("SELECT c FROM Compari c WHERE c.nameProduct LIKE %:keyword1% AND c.nameProduct LIKE %:keyword2% AND c.nameProduct LIKE %:keyword3% AND c.price between :infRange and :supRange order by price asc")
    List<Compari> findByNameProductContaining(String keyword1, String keyword2, String keyword3, String infRange, String supRange);

    @Query("SELECT c FROM Compari c WHERE c.nameProduct LIKE 'Procesor%' AND c.nameProduct LIKE '%i7%' AND c.company = 'EMAG' AND c.price between :infRange and :supRange order by c.price asc")
    List<Compari> findByNameProducttEmag(String infRange, String supRange);
    @Query("SELECT c FROM Compari c WHERE c.nameProduct LIKE 'Procesor%' AND c.nameProduct LIKE '%i7%' AND c.company = 'CEL' AND c.price between :infRange and :supRange order by c.price asc")
    List<Compari> findByNameProducttCel(String infRange, String supRange);
    @Query("SELECT c FROM Compari c WHERE c.nameProduct LIKE 'Procesor%' AND c.nameProduct LIKE '%i7%' AND c.company = 'EVOMAG' AND c.price between :infRange and :supRange order by c.price asc")
    List<Compari> findByNameProducttEvomag(String infRange, String supRange);
}
