package com.hedera.sdk.keygen;

import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCSException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class KeyStoreGenTests {

	@Test
	public void createAndLoad() throws NoSuchAlgorithmException, IOException, OperatorCreationException, PKCSException {
		final char[] passphrase = "HederaIsAwesome".toCharArray();
		try (final ByteArrayOutputStream ostream = new ByteArrayOutputStream()) {

			final CryptoKeyPair cryptoKeyPair = new CryptoKeyPair();

			KeyStoreGen.writeKey(cryptoKeyPair.getPrivateKey(), ostream, passphrase);

			final java.security.KeyPair keyPair;

			final byte[] outputBytes = ostream.toByteArray();

			try (final InputStream istream = new ByteArrayInputStream(outputBytes)) {
				 keyPair = KeyStoreGen.loadKey(istream, passphrase);
			}

			assertArrayEquals(cryptoKeyPair.getPublicKey().getEncoded(), keyPair.getPublic().getEncoded());
			assertArrayEquals(cryptoKeyPair.getPrivateKey().getEncoded(), keyPair.getPrivate().getEncoded());

			final CryptoKeyPair recoveredKeyPair = new CryptoKeyPair(cryptoKeyPair.recoveryWordsList());

			assertArrayEquals(cryptoKeyPair.getPublicKey().getEncoded(), recoveredKeyPair.getPublicKey().getEncoded());
			assertArrayEquals(cryptoKeyPair.getPrivateKey().getEncoded(), recoveredKeyPair.getPrivateKey().getEncoded());
		}
	}
}
