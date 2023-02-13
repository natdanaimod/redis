package co.th.natdanai.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository {


    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(final String key,final String value, int ttl, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, ttl, unit);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public String findByKey(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean isExistKey(final String key) {
        return redisTemplate.hasKey(key);
    }

}
