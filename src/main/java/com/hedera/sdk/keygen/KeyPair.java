package com.hedera.sdk.keygen;

public interface KeyPair {
    byte[] getPrivateKey();
    byte[] getPublicKey();
    byte[] signMessage(byte[] message);
    boolean verifySignature(byte[] message, byte[] signature);
	byte[] getSeedAndPublicKey();
	byte[] getPrivateKeyEncoded();
	byte[] getPublicKeyEncoded();
	String getPrivateKeyEncodedHex();
	String getPublicKeyEncodedHex();
	byte[] getPrivateKeySeed();
	String getPrivateKeySeedHex();
	String getPrivateKeyHex();
	String getPublicKeyHex();
	String getSeedAndPublicKeyHex();
}
