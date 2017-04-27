package com.bytebeats.uid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class IdGeneratorBuilder {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private String zkAddress;
    private String namespace;
    private long workId;
    private long epoch;
    private String epochStr;

    public IdGeneratorBuilder namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public IdGeneratorBuilder zkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
        return this;
    }

    public IdGeneratorBuilder workId(long workId) {
        this.workId = workId;
        return this;
    }

    public IdGeneratorBuilder epoch(long epoch) {
        this.epoch = epoch;
        return this;
    }

    public IdGeneratorBuilder epochStr(String epochStr) {
        this.epochStr= epochStr;
        return this;
    }

    public IdGenerator build() {
        if(epochStr!=null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Date date = sdf.parse(epochStr);
                this.epoch = date.getTime();
            } catch (ParseException e) {
                throw new RuntimeException("epochStr format invalid:"+DATE_FORMAT);
            }
        }

        return new SnowflakeIdGenerator(workId, epoch);
    }

}
