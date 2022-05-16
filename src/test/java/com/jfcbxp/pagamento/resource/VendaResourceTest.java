package com.jfcbxp.pagamento.resource;

import com.jfcbxp.pagamento.domain.ProdutoVenda;
import com.jfcbxp.pagamento.domain.Venda;
import com.jfcbxp.pagamento.domain.dto.ProdutoVendaDTO;
import com.jfcbxp.pagamento.domain.dto.VendaDTO;
import com.jfcbxp.pagamento.service.VendaService;
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
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class VendaResourceTest {

    private Venda venda;

    private VendaDTO vendaDTO;

    private Optional<Venda> vendaOptional;

    private Page<VendaDTO> vendaDTOPage;

    private Page<Venda> vendaPage;

    private PagedModel<EntityModel<VendaDTO>> vendaDTOPagedModel;

    private List<ProdutoVenda> produtosVenda;

    private List<ProdutoVendaDTO> produtosVendaDTO;

    @InjectMocks
    private VendaResource resource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private VendaService service;

    @Mock
    private PagedResourcesAssembler<VendaDTO> assembler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RequestAttributes mockRequest = new ServletWebRequest(new MockHttpServletRequest("GET", "/venda"));
        RequestContextHolder.setRequestAttributes(mockRequest);
        startVenda();
        startVendaDTO();
    }

    @Test
    void whenFindVendaByIdThenReturnSuccess() {
        when(service.findVendaById(anyInt())).thenReturn(venda);
        when(mapper.map(venda, VendaDTO.class)).thenReturn(vendaDTO);
        VendaDTO response = resource.findVendaById(1);
        assertNotNull(response);
        assertEquals(VendaDTO.class, response.getClass());
        assertEquals(vendaDTO, response);

    }

    @Test
    void whenFindAllVendaThenReturnAListOfVendaDTO() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.ASC, "emissao"));
        when(assembler.toModel(vendaDTOPage)).thenReturn(vendaDTOPagedModel);
        when(mapper.map(venda, VendaDTO.class)).thenReturn(vendaDTO);
        when(service.findAllVenda(pageable)).thenReturn(vendaPage);
        ResponseEntity<?> response = resource.findAllVenda(0, 12, "asc");
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PagedModel.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenCreateProdutoThenReturnSuccess() {
        when(mapper.map(venda, VendaDTO.class)).thenReturn(vendaDTO);
        when(service.findVendaById(anyInt())).thenReturn(venda);
        when(service.createVenda(vendaDTO)).thenReturn(venda);
        VendaDTO response = resource.createVenda(vendaDTO);
        assertNotNull(response);
        assertEquals(VendaDTO.class, response.getClass());

    }

    @Test
    void whenUpdateProdutoThenReturnSuccess() {
        when(mapper.map(venda, VendaDTO.class)).thenReturn(vendaDTO);
        when(service.findVendaById(anyInt())).thenReturn(venda);
        when(service.updateVenda(vendaDTO)).thenReturn(venda);
        VendaDTO response = resource.updateVenda(vendaDTO);
        assertNotNull(response);
        assertEquals(VendaDTO.class, response.getClass());
    }

    @Test
    void whenDeleteProdutoThenReturnSuccess() {
        doNothing().when(service).deleteVenda(anyInt());
        ResponseEntity<VendaDTO> response = resource.deleteVenda(1);
        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
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
        vendaPage = new PageImpl<>(List.of(venda));

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
        vendaDTOPage = new PageImpl<>(List.of(vendaDTO));
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        PagedResourcesAssembler<VendaDTO> vendaDTOAssembler = new PagedResourcesAssembler<VendaDTO>(resolver, null);
        vendaDTOPagedModel = vendaDTOAssembler.toModel(vendaDTOPage);

    }
}