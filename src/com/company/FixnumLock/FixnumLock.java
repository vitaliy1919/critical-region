package com.company.FixnumLock;

import java.util.concurrent.locks.Lock;

public interface FixnumLock extends Lock {
    int register();
    void unregister() throws RuntimeException;
    int getId();
}
