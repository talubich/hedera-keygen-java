package com.hedera.sdk.keyUpdate;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.DecoderException;

import com.hedera.sdk.account.HederaAccount;
import com.hedera.sdk.account.HederaAccountUpdateValues;
import com.hedera.sdk.common.HederaKeyPair;
import com.hedera.sdk.common.HederaKeyPair.KeyType;

public class SetPublicKey {

	public static String validate(String nodeAddress, int nodePort, long nodeAccountNum, String oldPubKey, String oldPrivKey, String[] recoveryArray, long accountNumber) {
		
		HederaAccount myAccount = new HederaAccount();
		// setup transaction/query defaults (durations, etc...)
		try {
			myAccount.txQueryDefaults = ExampleUtilities.getTxQueryDefaults(nodeAccountNum, nodeAddress, nodePort,
					accountNumber, oldPubKey, oldPrivKey);
		} catch (InvalidKeySpecException | DecoderException e4) {
			e4.printStackTrace();
			return "FAILED - Exception getting TxQueryDefaults.";
		}

		// validate old key pair
		byte[] messageToTest = "Test with this message".getBytes();

		HederaKeyPair oldKeyPair;
		try {
			oldKeyPair = new HederaKeyPair(KeyType.ED25519, oldPubKey, oldPrivKey);
		} catch (InvalidKeySpecException | DecoderException e3) {
			e3.printStackTrace();
			return "FAILED - Exception instantiating key pair.";
		}

		byte[] signed = oldKeyPair.signMessage(messageToTest);
		try {
			if (!oldKeyPair.verifySignature(messageToTest, signed)) {
				return "FAILED - Old key pair validation.";
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			return "FAILED - Exception in old key pair validation.";
		}

		// optionally validate new key pair

		// new keypair is index 0 (from wallet)
		HederaKeyPair newKeyPair = null;
		try {
			newKeyPair = new HederaKeyPair(KeyType.ED25519, recoveryArray, 0);
		}
		catch (Exception e) {
			return "FAILED - Unable to recover key from word list.";
		}

		signed = newKeyPair.signMessage(messageToTest);
		try {
			if (!newKeyPair.verifySignature(messageToTest, signed)) {
				return "FAILED - New key pair validation.";
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return "FAILED - Exception in new key pair validation.";
		}

		// get the account info and check keys are the same
		myAccount.accountNum = myAccount.txQueryDefaults.payingAccountID.accountNum;
		myAccount.accountKey = oldKeyPair;
		try {
			if (AccountGetInfo.getInfo(myAccount)) {
				if (!myAccount.accountKey.getPublicKeyHex().equals(oldKeyPair.getPublicKeyHex())) {
					return "FAILED - Old public check against account.";
				}
			
			} else {
				return "FAILED - Unable to get account information.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "==> An error occurred, please contact Hedera for help.";
		}
		return "OK";
	}

	public static String update(String nodeAddress, int nodePort, long nodeAccountNum, String oldPubKey, String oldPrivKey, String[] recoveryArray, long accountNumber) {
		HederaAccount myAccount = new HederaAccount();
		// setup transaction/query defaults (durations, etc...)
		try {
			myAccount.txQueryDefaults = ExampleUtilities.getTxQueryDefaults(nodeAccountNum, nodeAddress, nodePort,
					accountNumber, oldPubKey, oldPrivKey);
		} catch (InvalidKeySpecException | DecoderException e4) {
			e4.printStackTrace();
			return "UPDATE FAILED - Exception getting TxQueryDefaults.";
		}

		// setup an object to contain values to update
		HederaAccountUpdateValues updates = new HederaAccountUpdateValues();

		HederaKeyPair oldKeyPair;
		try {
			oldKeyPair = new HederaKeyPair(KeyType.ED25519, oldPubKey, oldPrivKey);
		} catch (InvalidKeySpecException | DecoderException e3) {
			e3.printStackTrace();
			return "UPDATE FAILED - Exception instantiating key pair.";
		}
		HederaKeyPair newKeyPair = null;
		try {
			newKeyPair = new HederaKeyPair(KeyType.ED25519, recoveryArray, 0);
		}
		catch (Exception e) {
			return "UPDATE FAILED - Exception - Unable to recover key from word list.";
		}
		updates.newKey = newKeyPair;
		// account needs old keypair to sign its messages
		myAccount.accountKey = oldKeyPair;
		// update the account
		try {
			myAccount = AccountUpdate.update(myAccount, updates);
		} catch (Exception e) {
			e.printStackTrace();
			return "UPDATE FAILED - Exception during account update.";
		}

		if (myAccount != null) {
			// new private key supplied, check account by getting info
			myAccount.txQueryDefaults.payingKeyPair = newKeyPair;
			try {
				if (AccountGetInfo.getInfo(myAccount)) {
					if (myAccount.accountKey.getPublicKeyHex().equals(newKeyPair.getPublicKeyHex())) {
						return "OK";
					} else {
						return "UPDATE FAILED - NEW public check against account.";
					}
				} else {
					return "UPDATE FAILED - Error during account getInfo.";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "UPDATE FAILED - Exception during account getInfo.";
			}
		} else {
			return "UPDATE FAILED - Account update returned null.";
		}
	}
}