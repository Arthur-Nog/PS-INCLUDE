package desafioInclude.projetoBack.repository;

import desafioInclude.projetoBack.entity.Veiculo;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {

    Boolean existsByPlaca(String placa);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    @Query("SELECT v from Veiculo v WHERE v.placa = :placa")
    Optional<Veiculo> findByPlacaForUpdate(@Param("placa") String placa);
    Optional<Veiculo> findByPlaca(String placa);
}
