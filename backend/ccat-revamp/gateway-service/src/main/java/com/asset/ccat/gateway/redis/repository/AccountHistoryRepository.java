package com.asset.ccat.gateway.redis.repository;

import com.asset.ccat.gateway.redis.model.SubscriberActivityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AccountHistoryRepository {

    private final String TABLE_KEY = "ACCOUNT_HISTORY";
    private final HashOperations<String, Integer, SubscriberActivityModel> hashOperations;
    private final RedisTemplate<String, SubscriberActivityModel> redisTemplate;

    @Autowired
    public AccountHistoryRepository(RedisTemplate<String, SubscriberActivityModel> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public List<SubscriberActivityModel> findBySubscriber(String msisdn) {
        return hashOperations.values(TABLE_KEY + "_" + msisdn);
    }

    public SubscriberActivityModel findById(String msisdn, Integer id) {
        return hashOperations.get(TABLE_KEY + "_" + msisdn, id);
    }

//    public List<SubscriberActivityModel> findBySubscriberAndActivityType(String subscriber, String activityType){
//        hashOperations.g
//    }

    public void saveAll(String msisdn, HashMap<Integer, SubscriberActivityModel> history) {
        hashOperations.putAll(TABLE_KEY + "_" + msisdn, history);
    }

    public void deleteBySubscriber(String msisdn) {
        redisTemplate.delete(TABLE_KEY + "_" + msisdn);
    }


}
