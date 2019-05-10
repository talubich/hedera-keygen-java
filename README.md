# Description

This project provides a  level utility for generating ED25519 key pairs for use with Hedera Hashgraph. It also enables mainnet keys to be updated in the event that they weren't properly setup.

A new private/public key pair can be generated from a system-generated random seed or a user-provided seed.
It is also possible to recover an existing keypair from a recovery word list, see examples below.

The .jar file can be found in the ```root``` folder of this project.

The project was developed under Java version 10, the UI was built using Scene Builder and JavaFX. In order to build this locally you will need to follow instructions here:

SceneBuilder version 11.0.0: https://gluonhq.com/products/scene-builder/
JavaFX for Eclipse: http://www.eclipse.org/efxclipse/install.html (version 3.3.0 was used for this project).

## Version

This project is currently at version 1.3

## Prerequisites

This application requires java 10 to be installed and your JAVA_HOME to point to the java 10 installation.

## Invoking the utility

The utility is a Java .jar file which is invoked as follows

```java -jar hedera-sdk-keygen-1.3-run.jar``` this will launch a UI from which you will be able to perform a number of operations

## Generate a new key pair

Choose which key index you'd like to generate a key for, -1 is the default used by the java sdk (v0.2.x) while the mobile wallet and java sdk (0.3.x) uses index 0.

You may optionally provide a seed if you do not trust your operating system's entropy. *Note, the same seed always returns the same key pair. The seed must be 64 hex encoded bytes.*

Clicking on the `generate` button will generate a new key pair and output the following:

* Public Key in hex and hex encoded (ASN.1) formats
* Private Key in hex and hex encoded (ASN.1) formats
* List of recovery words (these can be used later to recover a lost key pair if necessary)

## Recover a key pair

From the second tab, you are able to recover a key pair from a list of 22 or 24 words. The words may be supplied either separated with a space ( ) or comma (,).
You may specify the index of the key you wish to recover, -1 for default java SDK compatibility (v0.2.x), 0 for mobile wallet compatibility (0.3.x).

## Update a mainnet account key - work in progress

This capability exists purely for the purpose of enabling mainnet Hedera account holders to re-synchronise their mainnet account public key with the mobile wallet application generated key.
Indeed, in some instances, mainnet accounts have been created with a self-generated key and this is not compatible with the iOS and Android wallet applications.

`DO NOT USE THIS FACILITY FOR ANY OTHER PURPOSE`

### Warning

The utility updates your mainnet account's public key. You must be 100% certain of the data you input into the utility before you proceed, mistakes cannot be reversed.

### Before you start

You will require the following information in order to proceed

* Your mainnet account number
* Your current mainnet account public key in Hex Encoded format (302xxx)
* Your current mainnet account private key in Hex Encoded format (302xxx)
* The list of 22 or 24 recovery words from the wallet application, separated by spaces or commas

### What does the utility do

It updates your mainnet account's public key with the one which is known to your wallet.

* It prompts you for inputs and validates them where possible
* It validates that the supplied public/private key can successfully sign and verify a test message (this purely validates that the public/private key pair is correct)
* It validates that the supplied list of words can recover a keypair
* It validates that it can successfully sign and verify a test message with the recovered keypair
* It queries the network for your account information and compares the returned public key with the current mainnet public key you have supplied

In the event any of the above checks fails, the utility quits. You will have to re-run and correct inputs if necessary.

If the above checks were successful, the utility will prompt you to confirm you wish to proceed with the update. If you proceed, the utility will:

* Update your mainnet account with the new public key
* Query the network for your account information and compares your account's public key with your recovered public key to ensure they are the same.

*Note, in the event of an error at any of the above stages, the utility will stop and display a status message*

### Cost of running the utility

Since the utility performs 2 queries (getInfo on account) and 1 transaction, some hBar will be deducted from your account. Unsuccessful validations of current key against that supplied will result in a deduction, ensure you have the correct information to start with.

### Overriding defaults

By default, the utility communicates with a set node on mainnet (35.237.200.180, port 50211, node account 3.
You can override this default if you wish by providing a valid alernative IP address, port and node account number.

## Building the jar yourself

For some reason, `mvn install` doesn't generate a runnable jar file. Rather it does, but running the file with `java -jar` results in a class def not found. As a result, the jar file in this repository was built using Eclipse's export facility. This may be resolved in the future, however a runnable jar file is necessary for non developers.