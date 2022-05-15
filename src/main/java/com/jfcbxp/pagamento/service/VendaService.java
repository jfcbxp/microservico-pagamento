package com.jfcbxp.pagamento.service;

import com.jfcbxp.pagamento.domain.Venda;
import com.jfcbxp.pagamento.domain.dto.VendaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendaService {
    Venda createVenda(VendaDTO vendaDTO);

    Page<Venda> findAllVenda(Pageable pageable);

    Venda findVendaById(Integer id);

    Venda updateVenda(VendaDTO vendaDTO);

    void deleteVenda(Integer id);
}
