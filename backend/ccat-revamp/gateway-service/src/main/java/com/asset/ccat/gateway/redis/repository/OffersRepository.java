package com.asset.ccat.gateway.redis.repository;

import com.asset.ccat.gateway.redis.model.OffersRedisModel;
import org.springframework.data.repository.CrudRepository;

public interface OffersRepository extends CrudRepository<OffersRedisModel, String> {
}
