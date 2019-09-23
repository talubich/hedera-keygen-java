package com.hedera.sdk.keygen.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bouncycastle.util.encoders.Hex;

import com.hedera.sdk.bip32.EDBip32KeyChain;
import com.hedera.sdk.bip32.HGCSeed;
import com.hedera.sdk.bip39.Mnemonic;
import com.hedera.sdk.bip39.MnemonicException.MnemonicChecksumException;
import com.hedera.sdk.bip39.MnemonicException.MnemonicLengthException;
import com.hedera.sdk.bip39.MnemonicException.MnemonicWordException;
//import com.hedera.sdk.keyUpdate.SetPublicKey;
import com.hedera.sdk.keygen.CryptoUtils;
import com.hedera.sdk.keygen.EDKeyChain;
import com.hedera.sdk.keygen.EDKeyPair;
import com.hedera.sdk.keygen.KeyChain;
import com.hedera.sdk.keygen.KeyPair;
import com.hedera.sdk.keygen.Reference;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class Controller implements Initializable {

	private String accountNumberString;
	private String oldPubKey;
	private String oldPrivKey;
	private String newRecovery;
	private String nodeAddressPort;
	private String nodeAddress;
	private int nodePort;
	private String nodeAccount;
	private Long nodeAccountNum;
	private long accountNumber = 0;
	private String[] recoveryArray;
	
	private enum keyType {
		SDK
		,WALLET
		,WALLETNEW
	}
	
	private keyType generateKeyType = keyType.WALLETNEW;
	private keyType recoverKeyType = keyType.WALLETNEW;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		textAccountNumber.textProperty().addListener(new ChangeListener() {
			 public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			   buttonConfirm.setDisable(true);
			}
		});
		textCurrentPrivKey.textProperty().addListener(new ChangeListener() {
			 public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			   buttonConfirm.setDisable(true);
			}
		});
		textCurrentPubKey.textProperty().addListener(new ChangeListener() {
			 public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			   buttonConfirm.setDisable(true);
			}
		});
		textKURecoveryWords.textProperty().addListener(new ChangeListener() {
			 public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			   buttonConfirm.setDisable(true);
			}
		});
		textNodeIP.textProperty().addListener(new ChangeListener() {
			 public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			   buttonConfirm.setDisable(true);
			}
		});
		textNodeAccount.textProperty().addListener(new ChangeListener() {
			 public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			   buttonConfirm.setDisable(true);
			}
		});
	}
	
	@FXML
	private ToggleButton keySDK;
	@FXML
	private ToggleButton keyWallet;
	@FXML
	private ToggleButton keyWalletNew;
	@FXML
	private ToggleButton keySDKRecover;
	@FXML
	private ToggleButton keyWalletRecover;
	@FXML
	private ToggleButton keyWalletRecoverNew;
	@FXML
	private TextField textSeed;
	@FXML
	private Button buttonGenerate;
	@FXML
	private TextField textPublicKeyHex;
	@FXML
	private TextField textStatus;
	@FXML
	private Tab tabGenerateKeyPair;
	@FXML
	private Tab tabRecoverKeyPair;
	@FXML
	private Tab tabUpdateMainnet;
	@FXML
	private TextField textPublicKeyHexEnc;
	@FXML
	private TextField textPrivateKeyHex;
	@FXML
	private TextField textPrivateKeyHexEnc;
	@FXML
	private TextField textNotDER;
	@FXML
	private TextArea textRecovery;
	@FXML
	private TextArea textRecoveryBIP;
	@FXML
	private Button buttonClipPubKey;
	@FXML
	private Button buttonClipPubKeyHex;
	@FXML
	private Button buttonClipPrivKey;
	@FXML
	private Button buttonClipPrivKeyHex;
	@FXML
	private Button buttonClipWords;
	@FXML
	private Button buttonClipWordsBIP;
	@FXML
	private Button buttonClipNotDER;
	@FXML
	private TextArea textRecoverFrom;
	@FXML
	private Button buttonRecover;

	@FXML
	private Labeled labelPublicKey;
	@FXML
	private Labeled labelPublicKey2;
	@FXML
	private Labeled labelPrivateKey;
	@FXML
	private Labeled labelPrivateKey2;
	@FXML 
	private Labeled labelPrivateKeyDER;
	@FXML 
	private Labeled labelSeed;
	@FXML
	private Labeled labelRecovery22;
	@FXML
	private Labeled labelRecovery24;
	@FXML
	private Labeled labelRecoverFrom;
	
	@FXML
	private TextField textAccountNumber;
	@FXML
	private TextField textCurrentPubKey;
	@FXML
	private TextField textCurrentPrivKey;
	@FXML
	private TextArea textKURecoveryWords;
	@FXML
	private TextField textNodeIP;
	@FXML
	private TextField textNodeAccount;
	@FXML
	private Button buttonConfirm;
	@FXML
	private Button buttonKeyUpdate;

	@FXML
	private void handleButtonConfirm(ActionEvent event) {
//		buttonConfirm.setDisable(true);
//		String valid = SetPublicKey.update(this.nodeAddress, this.nodePort, this.nodeAccountNum, this.oldPubKey, this.oldPrivKey, this.recoveryArray, this.accountNumber);
//		
//		if (valid.contentEquals("OK")) {
//			textStatus.setText("UPDATE SUCCESSFUL");
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Account Update Successful");
//			alert.setHeaderText(null);
//			alert.setContentText("Your account key has been successfully updated.");
//
//			alert.showAndWait();	
//		} else {
//			textStatus.setText(valid);
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Error During Update");
//			alert.setHeaderText("An error occurred during the account update.");
//			alert.setContentText(valid);
//
//			alert.showAndWait();	
//		}
	}

	@FXML
	private void handleButtoneKeyValidate(ActionEvent event) {

//		textStatus.setText("");
//		buttonConfirm.setDisable(true);
//
//		this.accountNumberString = textAccountNumber.getText();
//		this.oldPubKey = textCurrentPubKey.getText();
//		this.oldPrivKey = textCurrentPrivKey.getText();
//		this.newRecovery = textKURecoveryWords.getText();
//		this.nodeAddressPort = textNodeIP.getText();
//		this.nodeAccount = textNodeAccount.getText();
//		this.accountNumber = 0;
//		
//		if (accountNumberString.contains(".") || accountNumberString.contains(":")) {
//			textStatus.setText("ERROR - account number must not contain dots or colons.");
//			return;
//		}
//		try {  
//			this.accountNumber = Long.parseLong(accountNumberString);  
//		} catch(NumberFormatException e){  
//			textStatus.setText("ERROR - account number is not numeric.");
//			return;
//		}
//
//		if (oldPubKey.length() != 88) {
//			textStatus.setText("ERROR - current public key should be 88 characters long.");
//			return;
//		}
//
//		if (oldPrivKey.length() != 96) {
//			textStatus.setText("ERROR - current private key should be 96 characters long.");
//			return;
//		}
//
//		this.recoveryArray = newRecovery.split(" ");
//		if (this.recoveryArray.length != 22) {
//			this.recoveryArray = newRecovery.split(",");
//			if (this.recoveryArray.length != 22) {
//				textStatus.setText("ERROR - recovery word list should contain 22 space or comma separated words.");
//				return;
//			}
//		}
//		
//		String[] nodeDetails = nodeAddressPort.split(":");
//		if (nodeDetails.length != 2) {
//			textStatus.setText("ERROR - node address format is invalid, should ip:port or host:port");
//			return;
//		}
//		this.nodeAddress = nodeDetails[0];
//		try {  
//			this.nodePort = Integer.parseInt(nodeDetails[1]);
//		} catch(NumberFormatException e){  
//			textStatus.setText("ERROR - node port is not numeric.");
//			return;
//		}
//
//		try {  
//			this.nodeAccountNum = Long.parseLong(nodeAccount);
//		} catch(NumberFormatException e){  
//			textStatus.setText("ERROR - node account number is not numeric.");
//			return;
//		}
//		
//		String valid = SetPublicKey.validate(this.nodeAddress, this.nodePort, this.nodeAccountNum, this.oldPubKey, this.oldPrivKey, this.recoveryArray, this.accountNumber);
//		if (valid.contentEquals("OK")) {
//			buttonConfirm.setDisable(false);
//			textStatus.setText("Validation success, you may proceed with the update.");
//			
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Validation Successful");
//			alert.setHeaderText(null);
//			alert.setContentText("You may proceed with the update if you wish.");
//
//			alert.showAndWait();	
//			
//		} else {
//			textStatus.setText(valid);
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Validation Failed");
//			alert.setHeaderText("Please check your inputs and try again.");
//			alert.setContentText(valid);
//
//			alert.showAndWait();	
//		}
	}

	@FXML
	private void handleTextSelect(Event event) {
		if (event.getSource().getClass() == TextField.class) {
			TextField text = (TextField) event.getSource();
			text.selectAll();
		} else if (event.getSource().getClass() == TextArea.class) {
			TextArea text = (TextArea) event.getSource();
			text.selectAll();
		}
	}
	@FXML
	private void handleButtonRecover(ActionEvent event) throws Exception {
		clearScreen();
		// validate word input
		String wordList = textRecoverFrom.getText();
		wordList = wordList.replace(" ", ",");
		String[] words = wordList.split(",");
		wordList = wordList.replaceAll(",", " ");
		List<String> allWords = Collections
				.synchronizedList(new ArrayList<String>());
		Matcher m = Pattern.compile("[a-zA-Z]+")
				.matcher(wordList.toLowerCase());
		while (m.find()) {
			allWords.add(m.group());
		}
		Reference referenceSeed = null;
		KeyPair keyPair = null;

		if (recoverKeyType == keyType.WALLETNEW) {
			if (words.length == 24) {
				byte[] entropy = new Mnemonic().toEntropy(allWords);
				HGCSeed seed = new HGCSeed(entropy);
				EDBip32KeyChain keyChain = new EDBip32KeyChain(seed);
				KeyPair pair = keyChain.keyAtIndex(0);
		        
				showKeys(pair);
			} else {
				textStatus.setText("Invalid recovery word count - should be 24.");
			}
		} else {
			if (words.length == 22) { // old style word list
				referenceSeed = new Reference(wordList);
	
				KeyChain keyChain = new EDKeyChain(referenceSeed);
				int index = keySDKRecover.isSelected() ? -1 : 0;
				keyPair = keyChain.keyAtIndex(index);
	
				showKeys(keyPair);
			} else if (words.length == 24) { // new word list
	
				byte[] entropy = new Mnemonic().toEntropy(allWords);
				int index = keySDKRecover.isSelected() ? -1 : 0;
				byte[] edSeed = CryptoUtils.deriveKey(entropy, index, 32);
		        EDKeyPair pair = new EDKeyPair(edSeed);
		        
				showKeys(pair);
			} else {
				textStatus.setText("Invalid recovery word count - should be 22 or 24.");
			}
		}
	}

	@FXML
	private void handleTabMainnetUpdate(Event event) {
		clearScreen();
		showResults(false);
	}

	@FXML
	private void handleTabGenerateKeyPair(Event event) {
		clearScreen();
		showResults(true);
	}

	@FXML
	private void handleTabRecoverKeyPair(Event event) {
		clearScreen();
		textRecoverFrom.setText("");
		showResults(true);
	}

	
	
	@FXML
	private void handleKeyRecoverSDK(ActionEvent event) {
		recoverKeyType = keyType.SDK;
		clearScreen();
	}

	@FXML
	private void handleKeyRecoverWallet(ActionEvent event) {
		recoverKeyType = keyType.WALLET;
		clearScreen();
	}
	@FXML
	private void handleKeyRecoverWalletNew(ActionEvent event) {
		recoverKeyType = keyType.WALLETNEW;
		clearScreen();
	}
	@FXML
	private void handleKeySDK(ActionEvent event) {
		generateKeyType = keyType.SDK;
		clearScreen();
	}

	@FXML
	private void handleKeyWallet(ActionEvent event) {
		generateKeyType = keyType.WALLET;
		clearScreen();
	}
	@FXML
	private void handleKeyWalletNew(ActionEvent event) {
		generateKeyType = keyType.WALLETNEW;
		clearScreen();
	}

	@FXML
	private void handleButtonClipText(ActionEvent event) {
		Button button = (Button) event.getSource();
		if (button.getId().contentEquals("0")) {
			copyToClipboard(textPublicKeyHex.getText());
		} else if (button.getId().contentEquals("1")) {
			copyToClipboard(textPublicKeyHexEnc.getText());
		} else if (button.getId().contentEquals("2")) {
			copyToClipboard(textPrivateKeyHex.getText());
		} else if (button.getId().contentEquals("3")) {
			copyToClipboard(textPrivateKeyHexEnc.getText());
		} else if (button.getId().contentEquals("4")) {
			copyToClipboard(textRecovery.getText());
		} else if (button.getId().contentEquals("5")) {
			copyToClipboard(textRecoveryBIP.getText());
		} else if (button.getId().contentEquals("6")) {
			copyToClipboard(textNotDER.getText());
		}
	}

	@FXML
	private void handleButtonGenerate(ActionEvent event) throws Exception {
		Reference referenceSeed = null;
		KeyPair keyPair = null;
		clearScreen();
		int index = -1;
		if ((textSeed.getLength() != 64) && (textSeed.getLength() != 0)) {
			textStatus.setText("Seed length must be 64 hex encoded bytes for ED25519");
		} else {
			if (generateKeyType == keyType.WALLETNEW) {
				HGCSeed seed;
				seed = new HGCSeed(CryptoUtils.getSecureRandomData(32));
				keyPair = new EDBip32KeyChain(seed).keyAtIndex(0);
				showKeys(keyPair);
				Mnemonic mnemonic = new Mnemonic();
				var mnemonicList = mnemonic.toMnemonic(seed.getEntropy());
				String listString = String.join(" ", mnemonicList);
				textRecoveryBIP.setText(listString);
			} else {
				if (generateKeyType == keyType.WALLET) {
					index = 0;
				}
	
				if (textSeed.getLength() == 64) {
					byte[] seedBytes = Hex.decode(textSeed.getText());
					referenceSeed = new Reference(seedBytes);
				} else {
					referenceSeed = new Reference(CryptoUtils.getSecureRandomData(32));
				}
	
				KeyChain keyChain = new EDKeyChain(referenceSeed);
				keyPair = keyChain.keyAtIndex(index);
				showKeys(keyPair);
				textRecovery.setText(referenceSeed.toWords(""," "," "," "," "," ",""));
				Mnemonic mnemonic = new Mnemonic();
				List<String> mnemonicList = mnemonic.toMnemonic(referenceSeed.toBytes());
				String listString = String.join(" ", mnemonicList);
				textRecoveryBIP.setText(listString);
			}
		}
	}

	private void copyToClipboard(String copyThis) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(copyThis);
		clipboard.setContent(content);
	}

	private void clearScreen() {
		try {
			textStatus.setText("");
			textPublicKeyHex.setText("");
			textPublicKeyHexEnc.setText("");
			textPrivateKeyHex.setText("");
			textPrivateKeyHexEnc.setText("");
			textRecovery.setText("");
			textRecoveryBIP.setText("");
			textNotDER.setText("");
		} catch (Exception e) {
			// do nothing
		}

		textSeed.setVisible(generateKeyType != keyType.WALLETNEW);
		labelSeed.setVisible(generateKeyType != keyType.WALLETNEW);
		textRecovery.setVisible(generateKeyType != keyType.WALLETNEW);
		labelRecovery22.setVisible(generateKeyType != keyType.WALLETNEW);
		buttonClipWords.setVisible(generateKeyType != keyType.WALLETNEW);

		try {
			if (labelRecovery24.isVisible()) {
				if (generateKeyType != keyType.WALLETNEW) {
					labelRecovery24.setText("Recovery Words (24)");
				} else {
					labelRecovery24.setText("BIP 32 Recovery Words (24)");
				}
			}
		} catch (Exception e) {
			// do nothing
		}
		try {
			if (labelRecoverFrom.isVisible()) {
				if (recoverKeyType != keyType.WALLETNEW) {
					labelRecoverFrom.setText("Recovery Words (24)");
				} else {
					labelRecoverFrom.setText("BIP 32 Recovery Words (24)");
				}
			}
		} catch (Exception e) {
			// do nothing
		}
	}

	private void showKeys(KeyPair keyPair) {
		textPublicKeyHex.setText(keyPair.getPublicKeyHex());
		textPublicKeyHexEnc.setText(keyPair.getPublicKeyEncodedHex());
		textPrivateKeyHex.setText(keyPair.getPrivateKeyHex());
		textPrivateKeyHexEnc.setText(keyPair.getPrivateKeyEncodedHex());
		textNotDER.setText(keyPair.getSeedAndPublicKeyHex());
	}

	private void showResults(boolean show) {
		try {
			labelPublicKey.setVisible(show);
			labelPublicKey2.setVisible(show);
			labelPrivateKey.setVisible(show);
			labelPrivateKey2.setVisible(show);
			labelPrivateKeyDER.setVisible(show);

			buttonClipPubKey.setVisible(show);
			buttonClipPubKeyHex.setVisible(show);
			buttonClipPrivKey.setVisible(show);
			buttonClipPrivKeyHex.setVisible(show);
			buttonClipNotDER.setVisible(show);

			textPublicKeyHex.setVisible(show);
			textPublicKeyHexEnc.setVisible(show);
			textPrivateKeyHex.setVisible(show);
			textPrivateKeyHexEnc.setVisible(show);
			textNotDER.setVisible(show);
		} catch (Exception e) {
			// do nothing
		}
	}
}
