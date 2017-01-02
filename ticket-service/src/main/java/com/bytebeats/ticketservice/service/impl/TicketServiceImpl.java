package com.bytebeats.ticketservice.service.impl;

import com.bytebeats.ticketservice.dao.ITicketDao;
import com.bytebeats.ticketservice.domain.Ticket;
import com.bytebeats.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;

/**
 * 通过一张通用的Ticket表来实现分布式ID的持久化，执行update更新语句来获取一批Ticket，
 * 这些获取到的Ticket会在内存中进行分配，分配完之后再从DB获取下一批Ticket。<br>
 * 具体实现：<br>
 * IDGenerator服务启动之初向DB申请一个号段，传入号段长度如 genStep = 10000，DB事务置 MaxID = MaxID + genStep，
 * DB设置成功代表号段分配成功。每次IDGenerator号段分配都通过原子加的方式，待分配完毕后重新申请新号段。
 *
 * @author Ricky Fung
 * @create 2017-01-02 21:56
 */
@Service("ticketService")
public class TicketServiceImpl implements TicketService {

    @Autowired
    private ITicketDao ticketDao;

    @Transactional
    @Override
    public Ticket createIfNotExists(String tag, int step) throws SQLException {
        Ticket ticket = ticketDao.queryByTag(tag);
        if(ticket==null){
            ticket = new Ticket();
            ticket.setTag(tag);
            ticket.setStep(step);
            ticket.setDesc(tag);
            Date date = new Date();
            ticket.setCreateTime(date);
            ticket.setUpdateTime(date);
            long id = ticketDao.insert(ticket);
            if(id<1){
                throw new SQLException(String.format("insert ticket:%s faield", tag));
            }
            ticket.setId(id);
        }
        return ticket;
    }

    @Transactional
    @Override
    public int updateTicket(Ticket ticket) throws SQLException {
        return ticketDao.update(ticket);
    }
}
