package com.bytebeats.ticketservice.dao;

import com.bytebeats.ticketservice.domain.Ticket;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-01-02 21:32
 */
public interface ITicketDao {

    long insert(Ticket ticket);

    Ticket queryByTag(String tag);

    int update(Ticket ticket);

    int delete(long id);
}
