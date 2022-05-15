package com.jfcbxp.pagamento.repository;

import com.jfcbxp.pagamento.domain.ProdutoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoVendaRepository extends JpaRepository<ProdutoVenda, Integer> {
}
