package com.hedera.sdk.bip32;

import com.google.common.base.Joiner;
import com.hedera.sdk.bip39.Mnemonic;
import com.hedera.sdk.keygen.EDKeyPair;
import com.hedera.sdk.keygen.KeyChain;
import com.hedera.sdk.keygen.KeyPair;

public class EDBip32KeyChain implements KeyChain {
    private HGCSeed hgcSeed;

    public EDBip32KeyChain(HGCSeed seed){
        hgcSeed = seed;
    }

    public KeyPair keyAtIndex(int index) throws Exception {
    	var wordsAsList = hgcSeed.toWordsList();
    	var words = Joiner.on(" ").join(wordsAsList);
    	
        var seed = Mnemonic.generateSeed(words, "");
        var ckd = SLIP10.deriveEd25519PrivateKey(seed, 44, 3030, 0, 0, index);
        EDKeyPair pair = new EDKeyPair(ckd);
        return pair;
    }

}