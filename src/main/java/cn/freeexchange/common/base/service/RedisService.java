package cn.freeexchange.common.base.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by qiuliujun on 2018/9/11
 */
@Component
@Slf4j
public class RedisService {

    private static String KEY_PREFIX = "QSL:";

    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        if (key == null || value == null) return;
        redisTemplate.opsForValue().set(KEY_PREFIX + key, value, timeout, unit);
    }

    public <T> T get(String key, Class<T> clazz) {
        try {
            Object value = redisTemplate.opsForValue().get(KEY_PREFIX + key);
            return (value == null) ? null : (T) value;
        } catch (Throwable t) {
            log.error(t.getMessage());
        }
        return null;
    }

    public String get(String key) {
        return get(key, String.class);
    }

    public void set(String key, String key2, Object value, long timeout, TimeUnit unit) {
        String _key = KEY_PREFIX + key;
        redisTemplate.opsForHash().put(_key, key2, value);
        if (timeout == 0) {
            redisTemplate.delete(_key);
        } else {
            redisTemplate.expire(_key, timeout, unit);
        }
    }

    public <T> T get(String key, String key2, Class<T> clazz) {
        try {
            Object value = redisTemplate.opsForHash().get(KEY_PREFIX + key, key2);
            return (value == null) ? null : (T) value;
        } catch (Throwable t) {
            log.error(t.getMessage());
        }
        return null;
    }

    public String get(String key, String key2) {
        return get(key, key2, String.class);
    }

    public List<String> getHashSubKeys(String key) {
        try {
            return (List<String>) redisTemplate.opsForHash().keys(KEY_PREFIX + key).stream().collect(Collectors.toList());
        } catch (Throwable t) {
            log.error(t.getMessage());
        }
        return null;
    }

    public void delete(String key) {
        if (StringUtils.isBlank(key)) return;
        redisTemplate.delete(KEY_PREFIX + key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(KEY_PREFIX + key);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(KEY_PREFIX + key);
    }

    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(KEY_PREFIX + key, timeUnit);
    }

}