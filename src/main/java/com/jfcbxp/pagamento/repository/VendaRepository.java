package com.jfcbxp.pagamento.repository;

import com.jfcbxp.pagamento.domain.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda,Integer> {

}
