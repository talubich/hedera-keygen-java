package com.hedera.sdk.keygen;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Hex;

public class EDKeyPair extends AbstractKeyPair {

	private EdDSAPrivateKey edPrivateKey;
	private EdDSAPublicKey edPublicKey;

	public EDKeyPair(final byte[] seed) {
		final EdDSAParameterSpec spec = getEdDSAParameterSpec();
		final EdDSAPrivateKeySpec privateKeySpec = new EdDSAPrivateKeySpec(seed, spec);
		this.edPrivateKey = new EdDSAPrivateKey(privateKeySpec);
		this.secretKey = edPrivateKey.geta();
		this.privKey = edPrivateKey;
		final EdDSAPublicKeySpec pubKeySpec = new EdDSAPublicKeySpec(privateKeySpec.getA(), spec);
		this.edPublicKey = new EdDSAPublicKey(pubKeySpec);
		this.publicKey = edPublicKey.getAbyte();
		this.pubKey = edPublicKey;
	}

	public EDKeyPair(final byte[] publicKey, final byte[] privateKey) {
		try {
			final PKCS8EncodedKeySpec encodedPrivKey = new PKCS8EncodedKeySpec(privateKey);
			final X509EncodedKeySpec encodedPubKey = new X509EncodedKeySpec(publicKey);
			this.edPrivateKey = new EdDSAPrivateKey(encodedPrivKey);
			this.secretKey = edPrivateKey.geta();
			this.privKey = edPrivateKey;
			this.edPublicKey = new EdDSAPublicKey(encodedPubKey);
			this.publicKey = edPublicKey.getAbyte();
			this.pubKey = edPublicKey;
		} catch (final InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	public static EDKeyPair buildFromPrivateKey(final byte[] privateKey) {
		try {
			final EDKeyPair edKeyPair = new EDKeyPair();
			final PKCS8EncodedKeySpec encodedPrivKey = new PKCS8EncodedKeySpec(privateKey);
			edKeyPair.edPrivateKey = new EdDSAPrivateKey(encodedPrivKey);
			edKeyPair.edPublicKey = new EdDSAPublicKey(
					new EdDSAPublicKeySpec(edKeyPair.edPrivateKey.getAbyte(), getEdDSAParameterSpec()));
			return edKeyPair;
		} catch (final Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void setPublicKey(final byte[] publicKey) {
		try {
			final X509EncodedKeySpec encodedPubKey = new X509EncodedKeySpec(publicKey);
			this.edPublicKey = new EdDSAPublicKey(encodedPubKey);
			this.publicKey = edPublicKey.getAbyte();
			this.pubKey = this.edPublicKey;
		} catch (final InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] getSecretKey() {
		return this.edPrivateKey.getAbyte();
	}
	@Override
	public PrivateKey getSecret() {
		return this.edPrivateKey;
	}
	@Override
	public String getSecretKeyHex() {
		return Hex.toHexString(this.edPrivateKey.getAbyte());
	}
	@Override
	public byte[] getSecretKeyEncoded() {
		return this.edPrivateKey.getEncoded();
	}
	@Override
	public String getSecretKeyEncodedHex() {
		return Hex.toHexString(this.edPrivateKey.getEncoded());
	}

	@Override
	public PublicKey getPublic() {
		return this.edPublicKey;
	}
	@Override
	public byte[] getPublicKey() {
		return this.edPublicKey.getAbyte();
	}
	@Override
	public String getPublicKeyHex() {
		return Hex.toHexString(this.edPublicKey.getAbyte());
	}
	@Override
	public byte[] getPublicKeyEncoded() {
		return this.edPublicKey.getEncoded();
	}
	@Override
	public String getPublicKeyEncodedHex() {
		return Hex.toHexString(this.edPublicKey.getEncoded());
	}

	public void setSecretKey(final byte[] secretKey) {
		try {
			final PKCS8EncodedKeySpec encodedPrivKey = new PKCS8EncodedKeySpec(secretKey);
			this.edPrivateKey = new EdDSAPrivateKey(encodedPrivKey);
			this.privKey = edPrivateKey;
		} catch (final InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] signMessage(final byte[] message) {
		try {
			final Signature sgr = new EdDSAEngine();
			sgr.initSign(edPrivateKey);
			sgr.update(message);
			return sgr.sign();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean verifySignature(byte[] message, byte[] signature) {
		try {
			final Signature sgr = new EdDSAEngine();
			sgr.initVerify(edPublicKey);
			sgr.update(message);
			return sgr.verify(signature);

		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private EDKeyPair() {
	}

	private static EdDSAParameterSpec getEdDSAParameterSpec() {
		return EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
	}
}
