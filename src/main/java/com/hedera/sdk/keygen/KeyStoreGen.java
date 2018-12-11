package com.hedera.sdk.keygen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;

public class KeyStoreGen {

	private static final String DEFAULT_KEY_STORE_FILE_NAME = "KeyStore.pfx";

	private static final String PRIVATE_KEY_ALIAS = "private.key";

	private static final String DEFAULT_KEY_STORE_TYPE = "PKCS12";

	private static final String DEFAULT_PROTECTION_ALGORITHM = "PBEWithHmacSHA384AndAES_256";

	public static CryptoKeyPair createKeyStore(final char[] passphrase) {
		return createKeyStore(passphrase, DEFAULT_KEY_STORE_FILE_NAME);
	}

	public static CryptoKeyPair createKeyStore(final char[] passphrase, final String filename) {
		final CryptoKeyPair keyPair = new CryptoKeyPair();
		final PrivateKeyEntry privateKeyEntry = new PrivateKeyEntry(keyPair.getPrivateKey(), null);
		final PasswordProtection passwordProtection = new PasswordProtection(passphrase, DEFAULT_PROTECTION_ALGORITHM,
				null);
		try (FileOutputStream fos = new FileOutputStream(filename)) {
			final KeyStore keyStore = KeyStore.getInstance(DEFAULT_KEY_STORE_TYPE);
			keyStore.setEntry(PRIVATE_KEY_ALIAS, privateKeyEntry, passwordProtection);
			keyStore.store(fos, passphrase);
		} catch (final Exception exception) {
			throw new RuntimeException(exception);
		}

		return keyPair;
	}

	public static KeyPair loadKey(final char[] passpharse) {
		return loadKey(passpharse, DEFAULT_KEY_STORE_FILE_NAME);
	}

	public static KeyPair loadKey(final char[] passphrase, final String filename) {
		try (FileInputStream fis = new FileInputStream(filename)) {
			final PasswordProtection passwordProtection = new PasswordProtection(passphrase,
					DEFAULT_PROTECTION_ALGORITHM, null);
			final KeyStore keyStore = KeyStore.getInstance(DEFAULT_KEY_STORE_TYPE);
			keyStore.load(fis, passphrase);
			final PrivateKeyEntry entry = (PrivateKeyEntry) keyStore.getEntry(PRIVATE_KEY_ALIAS, passwordProtection);
			return EDKeyPair.buildFromPrivateKey(entry.getPrivateKey().getEncoded());
		} catch (final Exception exception) {
			throw new RuntimeException(exception);
		}
	}
}
