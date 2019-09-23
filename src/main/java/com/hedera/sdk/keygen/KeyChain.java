package com.hedera.sdk.keygen;

public interface KeyChain {
    KeyPair keyAtIndex(int index) throws Exception;

}