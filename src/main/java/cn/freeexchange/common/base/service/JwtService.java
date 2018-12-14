package cn.freeexchange.common.base.service;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;

import cn.freeexchange.common.base.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by qiuliujun on 2018/9/11
 */
@Service
@Slf4j
public class JwtService {

	private static String default_salt_key = "PXRvbW15UTMwNTA1MDQwMTE5KEFCQykr";

	public String createJWT(String id, String subject, long expSeconds) {
		if(expSeconds < 60) expSeconds = 1800; //默认30分钟过期
		JwtBuilder builder = Jwts.builder()
				.setId(id)
				.setSubject(subject)
				.setIssuer("FE")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expSeconds * 1000))
				.signWith(SignatureAlgorithm.HS256, generalKey());
		return builder.compact();
	}

	public Claims validateJWT(String jwtStr) {
		try {
			return parseJWT(jwtStr);
		} catch (ExpiredJwtException e) {
			throw new BusinessException("token_error01");
		} catch (SignatureException e) {
			throw new BusinessException("token_error02");
		} catch (Exception e) {
			throw new BusinessException("token_error02");
		}
	}

	private SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(default_salt_key);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}

	private Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(jwt)
			.getBody();
	}
}