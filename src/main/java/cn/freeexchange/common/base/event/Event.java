package cn.freeexchange.common.base.event;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Event {
	
	//USER_SIGNUP,
	private String eventType;
	
	@SuppressWarnings("rawtypes")
	private Map content;
	
	
	protected Event() {
		
	}
	
	@SuppressWarnings("rawtypes")
	public Event(String eventType,Map content) {
		this.eventType = eventType;
		this.content = content;
	}
}
