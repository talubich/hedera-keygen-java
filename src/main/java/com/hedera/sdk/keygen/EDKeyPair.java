package com.hedera.sdk.keygen;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

import java.security.MessageDigest;
import java.security.Signature;

import org.bouncycastle.util.encoders.Hex;


public class EDKeyPair implements KeyPair {

    private EdDSAPrivateKey privateKey;
    private EdDSAPublicKey publicKey;

    public EDKeyPair(byte[] seed) {
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        EdDSAPrivateKeySpec privateKeySpec = new EdDSAPrivateKeySpec(seed, spec);
        this.privateKey = new EdDSAPrivateKey(privateKeySpec);
        EdDSAPublicKeySpec pubKeySpec = new EdDSAPublicKeySpec(privateKeySpec.getA(), spec);
        this.publicKey = new EdDSAPublicKey(pubKeySpec);
    }

    @Override
    public byte[] getSeedAndPublicKey() {
        byte[] seed = this.privateKey.getSeed();
        byte[] publicKey = this.getPublicKey();

        byte[] key = new byte[seed.length + publicKey.length];
        System.arraycopy(seed, 0, key, 0, seed.length);
        System.arraycopy(publicKey, 0, key, seed.length, publicKey.length);
        return key;
    }
    @Override
    public String getSeedAndPublicKeyHex() {
        return Hex.toHexString(this.getSeedAndPublicKey());
    }
    @Override
    public byte[] getPrivateKey() {
        return this.privateKey.geta();
    }
    @Override
    public String getPrivateKeyHex() {
        return Hex.toHexString(this.getPrivateKey());
    }
    @Override
    public byte[] getPrivateKeySeed() {
        return this.privateKey.getSeed();
    }
    @Override
    public String getPrivateKeySeedHex() {
        return Hex.toHexString(this.getPrivateKeySeed());
    }
    @Override
    public byte[] getPrivateKeyEncoded() {
        return this.privateKey.getEncoded();
    }
    @Override
    public String getPrivateKeyEncodedHex() {
        return Hex.toHexString(this.getPrivateKeyEncoded());
   }

    @Override
    public byte[] getPublicKey() {
        return this.publicKey.getAbyte();
    }
    @Override
    public String getPublicKeyHex() {
        return Hex.toHexString(this.getPublicKey());
    }
    @Override
    public byte[] getPublicKeyEncoded() {
        return this.publicKey.getEncoded();
    }
    @Override
    public String getPublicKeyEncodedHex() {
        return Hex.toHexString(this.getPublicKeyEncoded());
    }

    @Override
    public byte[] signMessage(byte[] message) {
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        try {
            Signature sgr = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
            sgr.initSign(privateKey);
            sgr.update(message);
            return  sgr.sign();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public boolean verifySignature(byte[] message, byte[] signature) {
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        try {
            Signature sgr = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
            sgr.initVerify(publicKey);
            sgr.update(message);
            return sgr.verify(signature);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
