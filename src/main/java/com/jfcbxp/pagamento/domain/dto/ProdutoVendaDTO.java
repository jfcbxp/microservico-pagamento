package com.jfcbxp.pagamento.domain.dto;

import com.jfcbxp.pagamento.domain.Venda;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProdutoVendaDTO extends RepresentationModel<ProdutoVendaDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 9153206754731458307L;
    private Integer id;
    private Integer idProduto;
    private BigDecimal quantidade;
    private Venda venda;
}
