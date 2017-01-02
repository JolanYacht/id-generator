package com.bytebeats.ticketservice.service;

import com.bytebeats.ticketservice.domain.Ticket;
import java.sql.SQLException;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-01-02 21:53
 */
public interface TicketService {

    Ticket createIfNotExists(String tag, int step) throws SQLException;

    int updateTicket(Ticket ticket) throws SQLException;
}
