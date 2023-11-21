/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset.ccat.lookup_service.database.dao.SequenceTableDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;

/**
 *
 * @author wael.mohamed
 */
@Service
public class SequenceTableService {

    @Autowired
    private SequenceTableDao sequenceDao;

    public List<Integer> getDedAccountSequence() throws LookupException {
        return sequenceDao.getDedAccountSequence();
    }

    public List<Integer> getAccumulatorSequence() throws LookupException {
        List<Integer> seqList = sequenceDao.getAccumulatorSequence();
        Map<String, List<Integer>> seq = new HashMap<>();
        seq.put("Ids", seqList);
        return sequenceDao.getAccumulatorSequence();
    }

    public Map<String, List<Integer>> getAccumulatorSequenceMap() throws LookupException {
        List<Integer> seqList = sequenceDao.getAccumulatorSequence();
        Map<String, List<Integer>> seq = new HashMap<>();
        seq.put("Ids", seqList);
        return seq;
    }

    public Map<String, List<Integer>> getDedAccountSequenceMap() throws LookupException {
        List<Integer> seqList = sequenceDao.getDedAccountSequence();
        Map<String, List<Integer>> seq = new HashMap<>();
        seq.put("Ids", seqList);
        return seq;
    }
}
