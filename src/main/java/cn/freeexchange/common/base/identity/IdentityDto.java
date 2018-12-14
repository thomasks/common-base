package cn.freeexchange.common.base.identity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IdentityDto {
	
	private Long partner;
	
	private Long openId;
	
	
	protected IdentityDto() {
		
	}

	public IdentityDto(Long partner, Long openId) {
		this.partner = partner;
		this.openId = openId;
	}
	
	
	
}
