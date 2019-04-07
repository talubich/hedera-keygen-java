package com.hedera.sdk.keygen.ui;

import java.net.URL;
import java.util.ResourceBundle;

import org.bouncycastle.util.encoders.Hex;

//import com.hedera.sdk.keyUpdate.SetPublicKey;
import com.hedera.sdk.keygen.CryptoUtils;
import com.hedera.sdk.keygen.EDKeyChain;
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
	private ToggleButton keySDKRecover;
	@FXML
	private ToggleButton keyWalletRecover;
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
	private TextArea textRecovery;
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
	private void handleButtonRecover(ActionEvent event) {
		clearScreen();
		// validate word input
		String wordList = textRecoverFrom.getText();
		wordList = wordList.replace(" ", ",");
		String[] words = wordList.split(",");

		if (words.length != 22) {
			textStatus.setText("Invalid recovery word count - should be 22.");
		} else {
			Reference referenceSeed = null;
			KeyPair keyPair = null;

			wordList = wordList.replaceAll(",", " ");
			referenceSeed = new Reference(wordList);

			KeyChain keyChain = new EDKeyChain(referenceSeed);
			int index = keySDKRecover.isSelected() ? -1 : 0;
			keyPair = keyChain.keyAtIndex(index);

			showKeys(keyPair);
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
	private void handleKeySDK(ActionEvent event) {
	}

	@FXML
	private void handleKeyWallet(ActionEvent event) {
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
		}
	}

	@FXML
	private void handleButtonGenerate(ActionEvent event) {
		Reference referenceSeed = null;
		KeyPair keyPair = null;
		clearScreen();
		int index = -1;
		if ((textSeed.getLength() != 64) && (textSeed.getLength() != 0)) {
			textStatus.setText("Seed length must be 64 hex encoded bytes for ED25519");
		} else {
			if (keyWallet.isSelected()) {
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
			textRecovery.setText(referenceSeed.toWords("", ",", ",", ",", ",", ",", ""));
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
		} catch (Exception e) {
			// do nothing
		}
	}

	private void showKeys(KeyPair keyPair) {
		textPublicKeyHex.setText(keyPair.getPublicKeyHex());
		textPublicKeyHexEnc.setText(keyPair.getPublicKeyEncodedHex());
		textPrivateKeyHex.setText(keyPair.getPrivateKeyHex());
		textPrivateKeyHexEnc.setText(keyPair.getPrivateKeyEncodedHex());
	}

	private void showResults(boolean show) {
		try {
			labelPublicKey.setVisible(show);
			labelPublicKey2.setVisible(show);
			labelPrivateKey.setVisible(show);
			labelPrivateKey2.setVisible(show);

			buttonClipPubKey.setVisible(show);
			buttonClipPubKeyHex.setVisible(show);
			buttonClipPrivKey.setVisible(show);
			buttonClipPrivKeyHex.setVisible(show);

			textPublicKeyHex.setVisible(show);
			textPublicKeyHexEnc.setVisible(show);
			textPrivateKeyHex.setVisible(show);
			textPrivateKeyHexEnc.setVisible(show);
		} catch (Exception e) {
			// do nothing
		}
	}
}
