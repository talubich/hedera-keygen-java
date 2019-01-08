package com.hedera.sdk.keygen;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public final class KeyGen {

	private final static void printStars() {
		System.out.println("************************************************************************");
	}

	public static void main(String[] args) {
		String keyType = "ED25519";
		
		String seed = "";
		int index = -1;
		List<String> recoveryWords = new ArrayList<String>();

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
				for (int word = 0; word < array.length; word++) {
					recoveryWords.add(array[word]);
				}
				break;
			default:
				System.out.println("Invalid input parameters");
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

		if (recoveryWords.size() == 22) {
			// recover key from words
			try {
				CryptoKeyPair keyPair = new CryptoKeyPair(recoveryWords, index);
				printStars();
				System.out.println(String.format("Your recovered key pair for index %d is:", index));
				System.out.println(String.format("Public key: %s", keyPair.getPublicKeyHex()));
				System.out.println(String.format("(Encoded) %s", keyPair.getPublicKeyEncodedHex()));
				System.out.println(String.format("Secret key: %s", keyPair.getSecretKeyHex()));
				System.out.println(String.format("(Encoded) %s", keyPair.getSecretKeyEncodedHex()));
				printStars();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// generating new key
			if (!seed.equals("")) {
				// build up the seed byte array
				seedBytes = seed.getBytes();
			}
			CryptoKeyPair keyPair = new CryptoKeyPair(seedBytes, index);
			printStars();
			System.out.println(String.format("Your key pair for index %d is:", index));
			System.out.println(String.format("Public key: %s", keyPair.getPublicKeyHex()));
			System.out.println(String.format("(Encoded) %s", keyPair.getPublicKeyEncodedHex()));
			System.out.println(String.format("Secret key: %s", keyPair.getSecretKeyHex()));
			System.out.println(String.format("(Encoded) %s", keyPair.getSecretKeyEncodedHex()));
			System.out.println("Recovery word list:");
			List<String> recovery = keyPair.recoveryWordsList();
			for (int i=0; i<recovery.size(); i++) {
				System.out.print(recovery.get(i));
				if (i<recovery.size()-1) {
					System.out.print(",");
				}
			}
			System.out.println("");
//			System.out.println(keyPair.recoveryWordsList());
			printStars();
		}
	}
}
