package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/* * Antiguamente usabamos clases llamadas DAO usando el patron(DAO) para interactuar con la base de datos ahora usamos interfaces
    * con JpaRepository que incluye metodos para interactuar con la base de datos. Este patron es llamado (Repositorio) */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // * Metodo de Spring Boot que nos permite editarlo a nuestra necesidad "find"
    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            select m from Medico m
            where m.activo= true
            and
            m.especialidad=:especialidad
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.fecha=:fecha
            )
            order by rand()
            limit 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);


    @Query("""
            select m.activo
            from Medico m
            where m.id=:idMedico
            """)
    Boolean findActivoById(Long idMedico);
}
