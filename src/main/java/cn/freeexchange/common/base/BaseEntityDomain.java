package cn.freeexchange.common.base;

import java.math.BigDecimal;
import java.util.Date;

// class BaseEntityDomain
public class BaseEntityDomain {

	protected Long id;// 主键
	protected Date createTime = new Date();// 创建时间
	protected Date updateTime = new Date();// 最后更新时间
	protected Long version = 0l;// 版本号
	
	protected Long memberId;//拥有人编号
	protected BigDecimal amount;//金额
	protected Boolean enable;//是否有效
	protected String code;//编码
	protected String name;//实体名称
	
	
	//BaseEntityDomain
	public BaseEntityDomain(Long id) {
        this.id = id;
    }
	
	// BaseEntityDomain
	public BaseEntityDomain() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
