package desafioInclude.projetoBack.repository;

import desafioInclude.projetoBack.entity.Aluguel;
import desafioInclude.projetoBack.entity.enums.StatusAluguel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AluguelRepository extends JpaRepository<Aluguel,Long> {

     List<Aluguel> findByStatusAluguel(StatusAluguel statusAluguel);
}
