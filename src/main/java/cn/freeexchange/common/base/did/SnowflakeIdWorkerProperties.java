package cn.freeexchange.common.base.did;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("snowflake-id-worker")
public class SnowflakeIdWorkerProperties {
	 
	
	/** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;
	
    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

}
