package com.hedera.sdk.keygen;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyPair {

	void setPublicKey(PublicKey publicKey);

	void setSecretKey(PrivateKey secretKey);

	void setPublicKey(byte[] publicKey);

	void setSecretKey(byte[] secretKey);

	byte[] getPublicKeyEncoded();

	byte[] getPublicKey();

	PublicKey getPublic();

	String getPublicKeyEncodedHex();

	String getPublicKeyHex();

	byte[] getSecretKey();

	byte[] getSecretKeyEncoded();

	PrivateKey getSecret();

	String getSecretKeyEncodedHex();

	String getSecretKeyHex();

	byte[] signMessage(byte[] message);

	boolean verifySignature(byte[] message, byte[] signature);
}
