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
				if (seed.length() != 64) {
					System.out.println("Seed length must be 64 hex encoded bytes for ED25519");
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
				System.out.println("* -seed=seedvalue - 64 hex encoded bytes to seed the key generation with");
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

		Reference referenceSeed = new Reference(CryptoUtils.getSecureRandomData(32));
		
		if (!wordList.equals("")) {
			if (!seed.equals("")) {
				System.out.println("*** Recovery words provided, ignoring seed parameter.");
			}
			// recover key from words
			referenceSeed = new Reference(wordList);
			System.out.println(String.format("Your recovered key pair for index %d is:", index));
		} else if (!seed.equals("")) {
			// recover key from seed
			byte[] seedBytes = Hex.decode(seed);
			referenceSeed = new Reference(seedBytes);
			System.out.println(String.format("Your generated key pair for index %d and own seed is:", index));
			
		} else {
			System.out.println(String.format("Your generated key pair for index %d is:", index));
		}

		KeyChain keyChain = new EDKeyChain(referenceSeed);
		KeyPair keyPair = keyChain.keyAtIndex(index);
		
		printStars();
		System.out.println(String.format("Public key (hex)     : %s", keyPair.getPublicKeyHex()));
		System.out.println(String.format("Public key (enc hex) : %s", keyPair.getPublicKeyEncodedHex()));
		System.out.println("");
//		System.out.println(String.format("Secret key (hex)     : %s", keyPair.getPrivateKeyHex()));
		System.out.println(String.format("Secret key (hex)     : %s", keyPair.getPrivateKeySeedHex()));
		System.out.println(String.format("Secret key (enc hex) : %s", keyPair.getPrivateKeyEncodedHex()));
		System.out.println("");
//		System.out.println(String.format("Secret Seed (hex)   : %s", keyPair.getPrivateKeySeedHex()));
		System.out.println(String.format("Seed+PubKey (hex)    : %s", keyPair.getSeedAndPublicKeyHex()));
		if (wordList.equals("")) {
			// not recovering, show recovery word list
			System.out.println("");
			System.out.println(referenceSeed.toWords("Recovery words  : ", ",", ",", ",", ",", ",", ""));
		}
		printStars();
	}
	
}
