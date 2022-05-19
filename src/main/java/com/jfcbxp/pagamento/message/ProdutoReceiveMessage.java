package com.jfcbxp.pagamento.message;

import com.jfcbxp.pagamento.domain.Produto;
import com.jfcbxp.pagamento.domain.dto.ProdutoDTO;
import com.jfcbxp.pagamento.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProdutoReceiveMessage {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ModelMapper mapper;

    @RabbitListener(queues = {"${crud.rabbitmq.queue}"})
    public void receive(@Payload ProdutoDTO produtoDTO) {

        repository.save(mapper.map(produtoDTO, Produto.class));

    }
}
