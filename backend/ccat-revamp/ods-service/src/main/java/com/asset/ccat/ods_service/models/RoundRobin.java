package com.asset.ccat.ods_service.models;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author mahmoud.shehab
 */
public class RoundRobin {

    private int scheduler = -1;
    private int cap;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public RoundRobin(int schedulerCap) {
        this.cap = schedulerCap;
    }

    public RoundRobin(int initialValue, int schedulerCap) {
        this.scheduler = initialValue;
        this.cap = schedulerCap;
    }

    public int newSequence() {
        ReentrantReadWriteLock.WriteLock wLock = rwLock.writeLock();
        try {
            wLock.lock();
            if (scheduler >= cap) {
                return scheduler = 0;
            }
            return ++scheduler;
        } finally {
            if (wLock.isHeldByCurrentThread()) {
                wLock.unlock();
            }
        }
    }

    public void setCap(int schedulerCap) {
        ReentrantReadWriteLock.WriteLock wLock = rwLock.writeLock();
        try {
            wLock.lock();
            this.cap = schedulerCap;
            if (scheduler > cap) {
                this.scheduler = 0;
            }
        } finally {
            if (wLock.isHeldByCurrentThread()) {
                wLock.unlock();
            }
        }
    }

    public int getValue() {
        ReentrantReadWriteLock.ReadLock rLock = rwLock.readLock();
        try {
            rLock.lock();
            return scheduler;
        } finally {
            rLock.unlock();
        }
    }
}
