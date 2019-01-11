package com.hedera.sdk.keygen;

public interface KeyPair {
    byte[] getPrivateKey();
    byte[] getPublicKey();
    byte[] signMessage(byte[] message);
    boolean verifySignature(byte[] message, byte[] signature);
	byte[] getPrivateAndPublicKey();
	byte[] getPrivateKeyEncoded();
	byte[] getPublicKeyEncoded();
	String getPrivateKeyEncodedHex();
	String getPublicKeyEncodedHex();
}
