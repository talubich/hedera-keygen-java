package com.hedera.sdk.bip32;

import java.util.List;

import com.hedera.sdk.bip39.Mnemonic;
import com.hedera.sdk.keygen.Reference;

public class HGCSeed {

    private static int bip39WordListSize = 24;
    private byte[] entropy; // 32 Bytes

    public HGCSeed(byte[] entropy) {
        this.entropy = entropy;
    }

    public HGCSeed(List<String> mnemonic) throws Exception {
        if (mnemonic.size() == HGCSeed.bip39WordListSize) {
            this.entropy = new Mnemonic().toEntropy(mnemonic);
        } else  {
        	String recoveryWords = "";
    		for (int i = 0; i < mnemonic.size(); i++) {
    			recoveryWords += mnemonic.get(i);
    			recoveryWords += " ";
    		}
        	
            Reference reference = new Reference(recoveryWords);
            this.entropy = reference.toBytes();
        }
    }

    public List<String> toWordsList() throws Exception{
        try {
            return new Mnemonic().toMnemonic(entropy);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public byte[] getEntropy() {
        return entropy;
    }
}
