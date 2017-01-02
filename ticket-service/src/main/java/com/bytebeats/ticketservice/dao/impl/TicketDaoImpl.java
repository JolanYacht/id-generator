package com.bytebeats.ticketservice.dao.impl;

import com.bytebeats.ticketservice.dao.ITicketDao;
import com.bytebeats.ticketservice.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-01-02 21:37
 */
@Repository("ticketDao")
public class TicketDaoImpl implements ITicketDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long insert(Ticket ticket) {
        String sql = "INSERT INTO tb_ticket(tag, max_id, step, desc, create_time, update_time) VALUES (?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, ticket.getTag(), ticket.getMaxId(), ticket.getStep(),
                ticket.getDesc(), ticket.getCreateTime(), ticket.getUpdateTime());
    }

    @Override
    public Ticket queryByTag(String tag) {

        List<Ticket> list = jdbcTemplate.query("SELECT * FROM tb_order", new RowMapper<Ticket>() {
            @Override
            public Ticket mapRow(ResultSet rs, int i) throws SQLException {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
                ticket.setTag(rs.getString("tag"));
                ticket.setMaxId(rs.getLong("max_id"));
                ticket.setStep(rs.getInt("step"));
                ticket.setDesc(rs.getString("desc"));
                ticket.setCreateTime(rs.getTimestamp("create_time"));
                ticket.setUpdateTime(rs.getTimestamp("update_time"));
                return ticket;
            }
        });

        if(list!=null && list.size()==1){
            return list.get(0);
        }

        throw new IllegalArgumentException(String.format("tag:%s has more than one records", tag));
    }

    @Override
    public int update(Ticket ticket) {
        String sql = "UPDATE tb_ticket SET max_id=?, update_time=? WHERE id=?";
        return jdbcTemplate.update(sql, ticket.getMaxId(), ticket.getUpdateTime(), ticket.getId());
    }

    @Override
    public int delete(long id) {
        String sql = "DELETE FROM tb_ticket WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }
}
