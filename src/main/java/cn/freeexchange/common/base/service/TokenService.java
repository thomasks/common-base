package cn.freeexchange.common.base.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.freeexchange.common.base.identity.IdentityDto;
import cn.freeexchange.common.base.utils.RediskeyUtils;
import io.jsonwebtoken.Claims;

@Service
public class TokenService {
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired 
	private RedisService redisService;
	
	
	//---------------------------------------------------------------------
    public  String makeToken(Long partner,Long openId, Long expSeconds) {
        String openIdStr = String.valueOf(openId);
        String partnerStr = String.valueOf(partner);
        IdentityDto dto = new IdentityDto(partner,openId);
        String subject = JSON.toJSONString(dto);
        String token = jwtService.createJWT(openIdStr, subject, expSeconds);
        String redisKey = RediskeyUtils.makeTokenRedisKey(partnerStr, openIdStr);
        redisService.set(redisKey, token, expSeconds, TimeUnit.SECONDS);
        return token;
    }
    
    
    public String getSysToken(String clientToken) {
		 Claims claims = jwtService.validateJWT(clientToken);
		 IdentityDto identityDto = JSON.parseObject(claims.getSubject(), IdentityDto.class);
		 String openId = String.valueOf(identityDto.getOpenId());
		 String partner = String.valueOf(identityDto.getPartner());
		 String redisKey = RediskeyUtils.makeTokenRedisKey(partner, openId);
		 String _token = redisService.get(redisKey);
		 return _token;
    }
    
    public IdentityDto getTokenIdentity(String token) {
		 Claims claims = jwtService.validateJWT(token);
		 IdentityDto identityDto = JSON.parseObject(claims.getSubject(), IdentityDto.class);
		 return identityDto;
	}
    
    public void removeToken(String token) {
    	 Claims claims = jwtService.validateJWT(token);
    	 IdentityDto mbrDto = JSON.parseObject(claims.getSubject(), IdentityDto.class);
         String rk = RediskeyUtils.makeTokenRedisKey(mbrDto.getPartner().toString(), 
         		mbrDto.getOpenId().toString());
         redisService.delete(rk);
    }

}
