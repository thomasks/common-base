package cn.freeexchange.common.base.req;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class RequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5874188819059512111L;
	
	public static final String HEADER_PARTNER_ID = "partner";// 合伙人编号
	public static final String HEADER_SERVICE_CODE = "serviceCode";	//服务名称
	public static final String HEADER_BAAS = "baas";
	public static final String HEADER_AREA_ID = "areaId";// 会员所属分区号
	public static final String HEADER_SIGN = "sign";// 签名
	public static final String HEADER_VERSION = "version";// 版本号
	public static final String HEADER_TOKEN = "token";// 登陆token
	public static final String HEADER_TICKET_NO = "ticketno"; // 票据号
	public static final String HEADER_REF_ID = "refId";	//关联id
	public static final String HEADER_TRACE_NO = "traceNo";	//关联id
	public static final String HEADER_NOTIFY_URL = "notifyUrl";	//关联id

	private String service;
	private Date requestTime;
	private String traceNO;
}
