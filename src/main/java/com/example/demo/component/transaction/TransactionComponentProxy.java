package com.example.demo.component.transaction;

import com.example.demo.dao.venta.VentaDao;
import com.example.demo.dto.Transaccion;
import com.example.demo.exception.TransactionVentaException;
import com.example.demo.fixture.MensajeVenta;
import com.mockrunner.jms.DestinationManager;
import com.mockrunner.mock.jms.MockQueue;
import com.mockrunner.mock.jms.MockQueueConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class TransactionComponentProxy implements ComponentProxy<Transaccion>{

    @Autowired
    DestinationManager destinationManager;

    @Autowired
    MockQueueConnectionFactory mockQueueConnectionFactory;

    @Autowired
    MockQueue mockQueue;

    @Autowired
    JmsTemplate jmsTemplate;

    private static final String QUEUE_NAME = "mock";

    @Override
    public Transaccion ejecutar(Transaccion transaccion) {

        Transaccion respuesta = null;
        try{
            mockQueue.addMessage(new MensajeVenta());

            jmsTemplate.convertAndSend(mockQueue.getQueueName(), transaccion);

            Message message = jmsTemplate.receive(QUEUE_NAME);

            if(nonNull(message)){
                respuesta = message.getBody(Transaccion.class);
            }
        }catch (JMSException e){
            throw new TransactionVentaException("500","Se ha producido un error");
        }

        mockQueue.reset();
        mockQueueConnectionFactory.clearConnections();

        return respuesta;
    }

}
