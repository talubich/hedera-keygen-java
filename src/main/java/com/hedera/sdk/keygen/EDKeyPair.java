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
    public byte[] getPrivateAndPublicKey() {
        byte[] seed = privateKey.getSeed();
        byte[] publicKey = getPublicKey();

        byte[] key = new byte[seed.length + publicKey.length];
        System.arraycopy(seed, 0, key, 0, seed.length);
        System.arraycopy(publicKey, 0, key, seed.length, publicKey.length);
        return key;
    }

    @Override
    public byte[] getPrivateKey() {
        return privateKey.getSeed();
   }
    @Override
    public byte[] getPrivateKeyEncoded() {
        return privateKey.getEncoded();
   }
    @Override
    public String getPrivateKeyEncodedHex() {
        return Hex.toHexString(privateKey.getEncoded());
   }

    @Override
    public byte[] getPublicKey() {
        return publicKey.getAbyte();
    }
    @Override
    public byte[] getPublicKeyEncoded() {
        return publicKey.getEncoded();
    }
    @Override
    public String getPublicKeyEncodedHex() {
        return Hex.toHexString(publicKey.getEncoded());
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
