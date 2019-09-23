package com.hedera.sdk.keygen;

public class EDKeyChain implements KeyChain {
    private Reference hgcSeed;

    public EDKeyChain(Reference seed){
        hgcSeed = seed;
    }

    @Override
    public KeyPair keyAtIndex(int index) {
        byte[] edSeed = CryptoUtils.deriveKey(hgcSeed.toBytes(), index, 32);
        EDKeyPair pair = new EDKeyPair(edSeed);
        return pair;
    }
}

