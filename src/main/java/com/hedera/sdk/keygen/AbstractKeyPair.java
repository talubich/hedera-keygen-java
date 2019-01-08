package com.hedera.sdk.keygen;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.util.encoders.Hex;

abstract class AbstractKeyPair implements KeyPair {

	protected Seed seed = null;

	protected PublicKey pubKey = null;
	protected PrivateKey privKey = null;
	protected byte[] publicKey = new byte[0];
	protected byte[] secretKey = new byte[0];
	
	@Override
	public void setPublicKey(PublicKey publicKey) {
		this.pubKey = publicKey;
	}
	
	@Override
	public void setSecretKey(PrivateKey secretKey) {
		this.privKey = secretKey;
	}

	@Override
	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}
	
	@Override
	public void setSecretKey(byte[] secretKey) {
		this.secretKey = secretKey;
	}
	
	@Override
	public byte[] getPublicKeyEncoded() {
		return this.pubKey.getEncoded();
	}
	@Override
	public byte[] getPublicKey() {
		return this.publicKey;
	}
	@Override
	public PublicKey getPublic() {
		return this.pubKey;
	}
	@Override
	public String getPublicKeyEncodedHex() {
		return Hex.toHexString(this.getPublicKeyEncoded());
	}
	@Override
	public String getPublicKeyHex() {
		return Hex.toHexString(this.publicKey);
	}
	
	@Override
	public byte[] getSecretKeyEncoded() {
		return this.privKey.getEncoded();
	}
	@Override
	public byte[] getSecretKey() {
		return this.secretKey;
	}
	@Override
	public PrivateKey getSecret() {
		return this.privKey;
	}
	@Override
	public String getSecretKeyEncodedHex() {
		return Hex.toHexString(this.getSecretKeyEncoded());
	}
	@Override
	public String getSecretKeyHex() {
		return Hex.toHexString(this.secretKey);
	}

	@Override
	abstract public byte[] signMessage(byte[] message);

	@Override
	abstract public boolean verifySignature(byte[] message, byte[] signature);

}
