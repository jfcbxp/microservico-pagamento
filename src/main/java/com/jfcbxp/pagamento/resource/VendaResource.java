package com.jfcbxp.pagamento.resource;

import com.jfcbxp.pagamento.domain.dto.VendaDTO;
import com.jfcbxp.pagamento.service.VendaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/venda")
public class VendaResource {
    public static final String ID = "/{id}";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_X_YAML = "application/x-yaml";
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private VendaService service;
    @Autowired
    private PagedResourcesAssembler<VendaDTO> assembler;

    @GetMapping(value = ID, produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML})
    public VendaDTO findVendaById(@PathVariable Integer id) {
        VendaDTO response = mapper.map(service.findVendaById(id), VendaDTO.class);
        response.add(linkTo(methodOn(VendaResource.class).findVendaById(id)).withSelfRel());
        return response;
    }

    @GetMapping(produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML})
    public ResponseEntity<?> findAllVenda(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "limit", defaultValue = "12") int limit,
                                          @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "emissao"));
        Page<VendaDTO> vendas = service.findAllVenda(pageable).map(p -> mapper.map(p, VendaDTO.class));

        vendas.stream()
                .forEach(p -> p.add(linkTo(methodOn(VendaResource.class).findVendaById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<VendaDTO>> pagedModel = assembler.toModel(vendas);
        return ResponseEntity.ok().body(pagedModel);
    }

    @PostMapping(produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML},
            consumes = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML})
    public VendaDTO createVenda(@RequestBody VendaDTO vendaDTO) {
        VendaDTO response = mapper.map(service.createVenda(vendaDTO), VendaDTO.class);
        response.add(linkTo(methodOn(VendaResource.class).findVendaById(response.getId())).withSelfRel());
        return response;
    }

    @PutMapping(produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML},
            consumes = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML})
    public VendaDTO updateVenda(@RequestBody VendaDTO vendaDTO) {
        VendaDTO response = mapper.map(service.updateVenda(vendaDTO), VendaDTO.class);
        response.add(linkTo(methodOn(VendaResource.class).findVendaById(response.getId())).withSelfRel());
        return response;
    }

    @DeleteMapping(ID)
    public ResponseEntity<VendaDTO> deleteVenda(@PathVariable Integer id) {
        service.deleteVenda(id);
        return ResponseEntity.noContent().build();
    }
}
