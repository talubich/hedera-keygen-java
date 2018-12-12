package com.hedera.sdk.keygen;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcECContentSignerBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;

public class KeyStoreGen {

	private static final String DEFAULT_KEY_STORE_FILE_NAME = "KeyStore.pfx";

	private static final String PRIVATE_KEY_ALIAS = "private.key";

	private static final String DEFAULT_KEY_STORE_TYPE = "PKCS12";

	private static final String DEFAULT_PROTECTION_ALGORITHM = "PBEWithHmacSHA384AndAES_256";

	private static final int ONE_HUNDRED_YEARS_IN_DAYS = 24 * 365 * 100;

	public static CryptoKeyPair createKeyStore(final char[] passphrase) {
		return createKeyStore(passphrase, DEFAULT_KEY_STORE_FILE_NAME);
	}

	public static CryptoKeyPair createKeyStore(final char[] passphrase, final String filename) {
		try (FileOutputStream fos = new FileOutputStream(filename)){
			final CryptoKeyPair keyPair = new CryptoKeyPair();
			final Certificate[] certificates = new Certificate[]{ createCertificate(keyPair.getPublicKey(), keyPair.getPrivateKey()) };
			final PrivateKeyEntry privateKeyEntry = new PrivateKeyEntry(keyPair.getPrivateKey(), certificates);
			final PasswordProtection passwordProtection = new PasswordProtection(passphrase, DEFAULT_PROTECTION_ALGORITHM, null);
			final KeyStore keyStore = KeyStore.getInstance(DEFAULT_KEY_STORE_TYPE);
			keyStore.setEntry(PRIVATE_KEY_ALIAS, privateKeyEntry, passwordProtection);
			keyStore.store(fos, passphrase);
			return keyPair;
		} catch (final Exception exception) {
			throw new RuntimeException(exception);
		}
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

	public static Certificate createCertificate(PublicKey publicKey, PrivateKey privateKey) throws IOException, OperatorCreationException, CertificateException {
		final X500Name dn = new X500Name("CN=" + PRIVATE_KEY_ALIAS);
		final AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find("SHA1withRSA");
		final AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder().find(sigAlgId);
		final AsymmetricKeyParameter privateKeyAsymKeyParam = PrivateKeyFactory.createKey(privateKey.getEncoded());
		final SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
		final ContentSigner sigGen = new BcECContentSignerBuilder(sigAlgId, digAlgId).build(privateKeyAsymKeyParam);
		final Date from = new Date();
		final Date to = new Date(from.getTime() + ONE_HUNDRED_YEARS_IN_DAYS * 86400000L);
		final BigInteger sn = new BigInteger(64, new SecureRandom());

		X509v1CertificateBuilder v1CertGen = new X509v1CertificateBuilder(dn, sn, from, to, dn, subPubKeyInfo);
		X509CertificateHolder certificateHolder = v1CertGen.build(sigGen);
		return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certificateHolder);
	}
}
