/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.redis.repository;

import com.asset.ccat.gateway.redis.model.LockingAdministration;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author wael.mohamed
 */
//@Repository
public interface LockingAdministrationRepository extends CrudRepository<LockingAdministration, String> {
}
