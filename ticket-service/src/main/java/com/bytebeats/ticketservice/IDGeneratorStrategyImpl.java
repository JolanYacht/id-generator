package com.bytebeats.ticketservice;

import com.bytebeats.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-01-02 22:33
 */
public class IDGeneratorStrategyImpl implements IDGeneratorStrategy {

    @Autowired
    private TicketService ticketService;

    @Override
    public Long genId(String tag) {

        return null;
    }
}
