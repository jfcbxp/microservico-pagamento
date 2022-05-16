package com.jfcbxp.pagamento.service.impl;

import com.jfcbxp.pagamento.domain.ProdutoVenda;
import com.jfcbxp.pagamento.domain.Venda;
import com.jfcbxp.pagamento.domain.dto.ProdutoVendaDTO;
import com.jfcbxp.pagamento.domain.dto.VendaDTO;
import com.jfcbxp.pagamento.repository.ProdutoVendaRepository;
import com.jfcbxp.pagamento.repository.VendaRepository;
import com.jfcbxp.pagamento.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class VendaServiceImplTest {

    public static final String OBJETO_NAO_ENCONTRADO_POR_ESSE_ID = "Objeto não encontrado para esse id";

    @InjectMocks
    private VendaServiceImpl service;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ProdutoVendaRepository produtoVendaRepository;

    @Mock
    private ModelMapper mapper;

    private Venda venda;

    private VendaDTO vendaDTO;

    private List<ProdutoVenda> produtosVenda;

    private List<ProdutoVendaDTO> produtosVendaDTO;

    private Optional<Venda> vendaOptional;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startVenda();
        startVendaDTO();
    }

    @Test
    void whenCreateVendaThenReturnSuccess() {
        when(vendaRepository.save(any())).thenReturn(venda);
        when(produtoVendaRepository.saveAll(any())).thenReturn(produtosVenda);
        when(mapper.map(vendaDTO, Venda.class)).thenReturn(venda);

        Venda response = service.createVenda(vendaDTO);
        List<ProdutoVenda> produtosSalvos = response.getProdutos().stream().map(p -> {
                    p.setVenda(response);
                    return p;
                }
        ).collect(Collectors.toList());
        produtoVendaRepository.saveAll(produtosSalvos);
        response.setProdutos(produtosSalvos);

        assertNotNull(response);
        assertEquals(Venda.class, response.getClass());
        assertEquals(venda, response);
        assertNotNull(response.getProdutos());
        assertEquals(venda.getProdutos(), response.getProdutos());


    }

    @Test
    void whenFindAllVendaThenReturnAnListOfPageVenda() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.ASC, "emissao"));
        when(vendaRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(venda)));
        Page<Venda> response = service.findAllVenda(pageable);
        assertNotNull(response);
        assertEquals(Venda.class, response.getContent().get(0).getClass());

    }

    @Test
    void whenFindVendaByIdThenReturnAnVendaInstance() {
        when(vendaRepository.findById(anyInt())).thenReturn(vendaOptional);

        Venda response = service.findVendaById(1);
        assertNotNull(response);
        assertEquals(Venda.class, response.getClass());
        assertEquals(response, venda);
    }

    @Test
    void whenFindVendaByIdThenReturnAnObjectNotFoundException() {
        when(vendaRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO_POR_ESSE_ID));
        Throwable exception = assertThrows(ObjectNotFoundException.class, () -> {
            service.findVendaById(1);
        });
        assertEquals(ObjectNotFoundException.class, exception.getClass());
        assertEquals(OBJETO_NAO_ENCONTRADO_POR_ESSE_ID, exception.getMessage());


    }

    @Test
    void whenUpdateVendaThenReturnSuccess() {
        when(vendaRepository.findById(anyInt())).thenReturn(vendaOptional);
        when(vendaRepository.save(mapper.map(vendaDTO, Venda.class))).thenReturn(venda);

        Venda response = service.updateVenda(vendaDTO);
        assertNotNull(response);
        assertEquals(Venda.class, response.getClass());

    }

    @Test
    void whenDeleteProdutoThenReturnSuccess() {
        when(vendaRepository.findById(anyInt())).thenReturn(vendaOptional);
        doNothing().when(vendaRepository).deleteById(anyInt());
        service.deleteVenda(1);
        verify(vendaRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void whenDeleteProdutoWithObjectNotFoundException() {
        when(vendaRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO_POR_ESSE_ID));
        Throwable exception = assertThrows(ObjectNotFoundException.class, () -> {
            service.deleteVenda(1);
        });
        assertEquals(ObjectNotFoundException.class, exception.getClass());
        assertEquals(OBJETO_NAO_ENCONTRADO_POR_ESSE_ID, exception.getMessage());
    }

    private void startVenda() {
        venda = new Venda();
        venda.setEmissao(new Date());
        venda.setId(1);
        venda.setValorTotal(BigDecimal.TEN);

        ProdutoVenda p1 = new ProdutoVenda();
        p1.setVenda(venda);
        p1.setId(1);
        p1.setIdProduto(1);
        p1.setQuantidade(BigDecimal.ONE);

        ProdutoVenda p2 = new ProdutoVenda();
        p2.setVenda(venda);
        p2.setId(2);
        p2.setIdProduto(2);
        p2.setQuantidade(BigDecimal.ONE);
        produtosVenda = List.of(p1, p2);


        venda.setProdutos(produtosVenda);

        vendaOptional = Optional.of(venda);

    }

    private void startVendaDTO() {
        vendaDTO = new VendaDTO();
        vendaDTO.setEmissao(new Date());
        vendaDTO.setId(1);
        vendaDTO.setValorTotal(BigDecimal.TEN);

        ProdutoVendaDTO p1 = new ProdutoVendaDTO();
        p1.setId(1);
        p1.setIdProduto(1);
        p1.setQuantidade(BigDecimal.ONE);

        ProdutoVendaDTO p2 = new ProdutoVendaDTO();
        p2.setId(2);
        p2.setIdProduto(2);
        p2.setQuantidade(BigDecimal.ONE);

        produtosVendaDTO = List.of(p1, p2);

        vendaDTO.setProdutos(produtosVendaDTO);

    }


}