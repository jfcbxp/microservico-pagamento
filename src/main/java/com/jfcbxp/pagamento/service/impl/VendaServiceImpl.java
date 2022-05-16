package com.jfcbxp.pagamento.service.impl;

import com.jfcbxp.pagamento.domain.ProdutoVenda;
import com.jfcbxp.pagamento.domain.Venda;
import com.jfcbxp.pagamento.domain.dto.VendaDTO;
import com.jfcbxp.pagamento.repository.ProdutoVendaRepository;
import com.jfcbxp.pagamento.repository.VendaRepository;
import com.jfcbxp.pagamento.service.VendaService;
import com.jfcbxp.pagamento.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaServiceImpl implements VendaService {
    public static final String OBJETO_NAO_ENCONTRADO_POR_ESSE_ID = "Objeto não encontrado para esse id";

    @Autowired
    private VendaRepository repository;

    @Autowired
    private ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Venda createVenda(VendaDTO vendaDTO) {
        Venda venda = repository.save(mapper.map(vendaDTO, Venda.class));
        List<ProdutoVenda> produtosSalvos = List.of((ProdutoVenda) vendaDTO.getProdutos().stream().map(p -> mapper.map(p, ProdutoVenda.class)));
        produtoVendaRepository.saveAll(produtosSalvos);
        venda.setProdutos(produtosSalvos);
        return venda;
    }

    @Override
    public Page<Venda> findAllVenda(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Venda findVendaById(Integer id) {
        Optional<Venda> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO_POR_ESSE_ID));
    }

    @Override
    public Venda updateVenda(VendaDTO vendaDTO) {
        findVendaById(vendaDTO.getId());
        return repository.save(mapper.map(vendaDTO, Venda.class));
    }

    @Override
    public void deleteVenda(Integer id) {
        repository.deleteById(findVendaById(id).getId());
    }
}
