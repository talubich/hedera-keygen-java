package com.hedera.sdk.keygen;

import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCSException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class KeyGenTest {

	@Test
	public void create() throws NoSuchAlgorithmException, IOException, OperatorCreationException, PKCSException {
		// index -1
		final CryptoKeyPair cryptoKeyPair = new CryptoKeyPair(-1);
		// check private and public are different
		assertFalse(Arrays.equals(cryptoKeyPair.getPrivateKey(), cryptoKeyPair.getPublicKey()));
		// get word list
		String[] words = cryptoKeyPair.recoveryWordsArray();
		// check length
		assertEquals(22, words.length);
		
		final CryptoKeyPair cryptoRecoveredKeyPair = new CryptoKeyPair(words, -1);
		// check private/public as the same as origin
		assertArrayEquals(cryptoKeyPair.getPublicKey(), cryptoRecoveredKeyPair.getPublicKey());
		assertArrayEquals(cryptoKeyPair.getPrivateKey(), cryptoRecoveredKeyPair.getPrivateKey());

		// check different index results in different keys
		final CryptoKeyPair cryptoRecoveredKeyPairOtherIndex = new CryptoKeyPair(words, 0);
		assertFalse(Arrays.equals(cryptoRecoveredKeyPair.getPublicKey(), cryptoRecoveredKeyPairOtherIndex.getPublicKey()));
		assertFalse(Arrays.equals(cryptoRecoveredKeyPair.getPrivateKey(), cryptoRecoveredKeyPairOtherIndex.getPrivateKey()));
	}
}
