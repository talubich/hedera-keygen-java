package com.hedera.sdk.keygen;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;

import com.google.common.io.BaseEncoding;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public final class KeyGen {

	private final static void printStars() {
		System.out.println("************************************************************************");
	}

	public static void main(String[] args) {
		String seed = "";
		int index = -1;
		String wordList = "";

		for (int i=0; i<args.length; i++) {
			// remove all spaces from parameter
			String input = args[i].replaceAll(" ", "");
			// get parameter and value
			String[] paramValue = input.split("=");
			// check there is a parameter/value pair
			if (paramValue.length != 2) {
				System.out.println("Parameter and value must be separated by an equal (=) sign");
				System.out.println("Invalid Input detected at:" + args[i]);
				System.exit(3);
			}
			String param = paramValue[0];
			String value = paramValue[1];
			
			switch (param) {
			case "-index":
				index = Integer.parseInt(value);
				break;
			case "-seed":
				seed = value;
				if (seed.length() != 32) {
					System.out.println("Seed length must be 32 bytes for ED25519 (not hex encoded)");
					System.exit(3);
				}
				break;
			case "-words":
				String[] array = value.split(",");
				if (array.length != 22) {
					System.out.println("Invalid recovery word count - should be 22, got " + array.length);
					System.exit(3);
				}
				wordList = value.replaceAll(",", " ");
				break;
			default:
				System.out.println("Invalid input parameter(s) - " + param);
				System.out.println("Should be");
				System.out.println("* no parameters - generates an ED25519 key at index -1");
				System.out.println("* -index=indexvalue - generates an ED25519 key at index indexvalue, must be greater than or equal to -1");
				System.out.println("* -seed=seedvalue - 32 bytes to seed the key generation with");
				System.out.println("* -words=22 recovery words separated by commas");
				System.out.println("Example: -index=-1 -words=word1,word2,word3...,word22");
				System.out.println("Note");
				System.out.println("* The -index parameter is optional, it defaults to -1, for Hedera Wallet Compliant key recovery use index 0");
				System.out.println("* The -seed parameter is optional, a seed will be generated automatically if not supplied");
				System.out.println("* The -words parameter is optional, it is only required if recovering an existing key pair from a word list");
				System.out.println("* Finally, the list of recovery words overrides the seed parameter value (e.g. seed will be ignored if words supplied");
				
				System.exit(4);
			}
		}

		byte[] seedBytes = null;
		Reference referenceSeed = new Reference(CryptoUtils.getSecureRandomData(32));

		
		if (!wordList.equals("")) {
			if (!seed.equals("")) {
				System.out.println("*** Recovery words provided, ignoring seed parameter.");
			}
			// recover key from words
			referenceSeed = new Reference(wordList);
			System.out.println(String.format("Your recovered key pair for index %d is:", index));
		} else {
			System.out.println(String.format("Your generated key pair for index %d is:", index));
		}

		KeyChain keyChain = new EDKeyChain(referenceSeed);
		KeyPair keyPair = keyChain.keyAtIndex(index);
		
		printStars();
		System.out.println(String.format("Public key      : %s", bytesToString(keyPair.getPublicKey())));
		System.out.println(String.format("Public key(enc) : %s", keyPair.getPublicKeyEncodedHex()));
		System.out.println("");
		System.out.println(String.format("Secret key      : %s", bytesToString(keyPair.getPrivateKey())));
		System.out.println(String.format("Secret key(enc) : %s", keyPair.getPrivateKeyEncodedHex()));
		System.out.println("");
		System.out.println(String.format("Combined        : %s", bytesToString(keyPair.getPrivateAndPublicKey())));
		if (wordList.equals("")) {
			// not recovering, show recovery word list
			System.out.println("");
			System.out.println(referenceSeed.toWords("Recovery words  : ", ",", ",", ",", ",", ",", ""));
		}
		printStars();
		
		// tests
		EdDSAPrivateKey edPrivateKey;
		EdDSAPublicKey edPublicKey;

		EdDSAPublicKeySpec encodedPubKey = new EdDSAPublicKeySpec(keyPair.getPublicKey(), EdDSANamedCurveTable.ED_25519_CURVE_SPEC);
		edPublicKey = new EdDSAPublicKey(encodedPubKey);
		System.out.println(Hex.toHexString(edPublicKey.getAbyte()));
		System.out.println(Hex.toHexString(edPublicKey.getEncoded()));
		
		EdDSAPrivateKeySpec encodedPrivKey = new EdDSAPrivateKeySpec(keyPair.getPrivateKey(), EdDSANamedCurveTable.ED_25519_CURVE_SPEC);
		edPrivateKey = new EdDSAPrivateKey(encodedPrivKey);
		System.out.println(Hex.toHexString(edPrivateKey.geta()));
		System.out.println(Hex.toHexString(edPrivateKey.getEncoded()));

	}
	
    private static String bytesToString(byte[] bytes) {
        return BaseEncoding.base16().lowerCase().encode(bytes);
    }
}
