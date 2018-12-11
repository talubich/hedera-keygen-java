package com.hedera.sdk.keygen;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.jcajce.provider.asymmetric.X509;
import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

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
		final Certificate[] certificates = new Certificate[]{};
		final PrivateKeyEntry privateKeyEntry = new PrivateKeyEntry(keyPair.getPrivateKey(), certificates);
		final PasswordProtection passwordProtection = new PasswordProtection(passphrase, DEFAULT_PROTECTION_ALGORITHM, null);

		try (FileOutputStream fos = new FileOutputStream(filename)){
			final KeyStore keyStore = KeyStore.getInstance(DEFAULT_KEY_STORE_TYPE);
			keyStore.setEntry(PRIVATE_KEY_ALIAS, privateKeyEntry, passwordProtection);
			keyStore.store(fos, passphrase);
		} catch (final Exception exception) {
			throw new RuntimeException(exception);
		}

		return keyPair;
	}

	public static KeyPair loadKey(final char[] passphrase) {
		return loadKey(passphrase, DEFAULT_KEY_STORE_FILE_NAME);
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

//	public static Certificate createCertificate(PublicKey publicKey, PrivateKey privateKey) {
//		X500Name dn = new X500Name("CN=" + PRIVATE_KEY_ALIAS);
//		X509v1CertificateBuilder builder = new X509v1CertificateBuilder();
//
//	}
}
