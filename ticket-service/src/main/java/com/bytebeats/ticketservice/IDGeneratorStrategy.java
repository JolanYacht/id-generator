package com.bytebeats.ticketservice;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-01-02 22:18
 */
public interface IDGeneratorStrategy {

    Long genId(String tag);
}
