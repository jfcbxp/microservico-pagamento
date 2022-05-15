package com.jfcbxp.pagamento.domain.dto;

import com.jfcbxp.pagamento.domain.ProdutoVenda;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VendaDTO extends RepresentationModel<VendaDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = -1381798095197570942L;
    private Integer id;
    private Date emissao;
    private BigDecimal valorTotal;
    private List<ProdutoVenda> produtos;
}
