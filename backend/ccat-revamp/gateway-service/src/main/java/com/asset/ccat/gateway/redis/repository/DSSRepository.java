package com.asset.ccat.gateway.redis.repository;

import com.asset.ccat.gateway.redis.model.DSSRedisModel;
import org.springframework.data.repository.CrudRepository;

public interface DSSRepository extends CrudRepository<DSSRedisModel, String> {
}
