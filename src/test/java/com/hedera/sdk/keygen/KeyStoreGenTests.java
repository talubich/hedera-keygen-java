package com.hedera.sdk.keygen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyStoreGenTests {

	@Test
	public void createAndLoad() {
		final char[] passphrase = "HederaIsAwesome".toCharArray();
		final CryptoKeyPair cryptoKeyPair = KeyStoreGen.createKeyStore(passphrase);

		final KeyPair keyPair = KeyStoreGen.loadKey(passphrase);

		assertEquals(cryptoKeyPair.getPublicKey(), keyPair.getPublicKey());
		assertEquals(cryptoKeyPair.getPrivateKey(), keyPair.getPrivateKey());
	}
}
