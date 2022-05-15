package com.jfcbxp.pagamento.domain.dto;


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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProdutoDTO extends RepresentationModel<ProdutoDTO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 7158061130171428766L;

    private Integer id;
    private BigDecimal estoque;
}