package com.hedera.sdk.keyUpdate;


import java.security.spec.InvalidKeySpecException;
import org.apache.commons.codec.DecoderException;
import org.slf4j.LoggerFactory;
import com.hedera.sdk.common.HederaAccountID;
import com.hedera.sdk.common.HederaDuration;
import com.hedera.sdk.common.HederaKeyPair;
import com.hedera.sdk.common.HederaKeyPair.KeyType;
import com.hedera.sdk.common.HederaTransactionAndQueryDefaults;
import com.hedera.sdk.node.HederaNode;


public class ExampleUtilities {
	final static ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(ExampleUtilities.class);

	public static HederaTransactionAndQueryDefaults getTxQueryDefaults(long nodeAccountNum, String nodeAddress, int nodePort, long accountNum, String oldPubKey, String oldPrivKey) throws InvalidKeySpecException, DecoderException {
		// setup a set of defaults for query and transactions
		HederaTransactionAndQueryDefaults txQueryDefaults = new HederaTransactionAndQueryDefaults();

		// setup node account ID
		HederaAccountID nodeAccountID = new HederaAccountID(0, 0, nodeAccountNum);
		// setup node
		HederaNode node = new HederaNode(nodeAddress, nodePort, nodeAccountID);
		
		// setup paying account
		HederaAccountID payingAccountID = new HederaAccountID(0, 0, accountNum);
		
		// setup paying keypair
		txQueryDefaults.payingKeyPair = new HederaKeyPair(KeyType.ED25519, oldPubKey, oldPrivKey);
		
		txQueryDefaults.memo = "KeyUpdate";
		txQueryDefaults.node = node;
		txQueryDefaults.payingAccountID = payingAccountID;
		txQueryDefaults.transactionValidDuration = new HederaDuration(120, 0);
		
		return txQueryDefaults;
	}
	
	public static void showResult(String result) {
		String stars = "***********************************************************************************************";
		String log = String.format("%s\n%s\n%s\n%s\n%s", "", stars, result, stars, "");
		logger.info(log);
	}
	
}
