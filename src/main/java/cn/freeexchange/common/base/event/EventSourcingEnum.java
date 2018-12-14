package cn.freeexchange.common.base.event;

import lombok.Getter;

@Getter
public enum EventSourcingEnum {
	
	USER_EVENT_SOURCING(1,"USER_EVENT_SOURCING","用户事件源"),
	MEMEBER_EVENT_SOURCING(2,"MEMBER_EVENT_SOURCING","会员事件源"),
	ACCOUNT_EVENT_SOURCING(3,"ACCOUNT_EVENT_SOURCING","账户事件源"),
	COUPON_EVENT_SOURCING(4,"COUPON_EVENT_SOURCING","券事件源");
	
	private Integer id;
	private String code;
    private String name;
    
    private EventSourcingEnum(Integer id,String code,String name) {
		this.id = id;
		this.name = name;
		this.code = code;
	}
    
    
	
    
    

}
