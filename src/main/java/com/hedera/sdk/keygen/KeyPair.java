package com.hedera.sdk.keygen;

import java.security.PrivateKey;

public interface KeyPair {

    PrivateKey getPrivateKey();

    byte[] getPublicKey();

    byte[] getPublicKeyEncoded();

    byte[] signMessage(byte[] message);

    boolean verifySignature(byte[] message, byte[] signature);
}
