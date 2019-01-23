# Description
This project provides a command line level utility for generating ED25519 key pairs for use with Hedera Hashgraph.
A new private/public key pair can be generated from a system-generated random seed or a user-provided seed.
It is also possible to recover an existing keypair from a recovery word list, see examples below.

The .jar file can be found in the ```root``` folder of this project.

The project was developed under Java version 10.

# Version

This project is currently at version 1.1

# Invoking the utility

The utility is a Java .jar file which is invoked as follows

```java -jar hedera-sdk-keygen-1.2-run.jar``` followed by optional parameters

**The above would output the following**

Your generated key pair for index -1 is:
************************************************************************
Public key (hex)     : 2cda7382a63419894104f311f3eb2377cdb3d1f4367758aa85571b2318bb3b7c
Public key (enc hex) : 302a300506032b65700321002cda7382a63419894104f311f3eb2377cdb3d1f4367758aa85571b2318bb3b7c

Secret key (hex)     : 3326c5b13a2871c022997c39065be6f752be2b62accb98fa0d64f26df07dcd74
Secret key (enc hex) : 302e020100300506032b6570042204203326c5b13a2871c022997c39065be6f752be2b62accb98fa0d64f26df07dcd74

Seed+PubKey          : 3326c5b13a2871c022997c39065be6f752be2b62accb98fa0d64f26df07dcd742cda7382a63419894104f311f3eb2377cdb3d1f4367758aa85571b2318bb3b7c

Recovery words  : pledge,bumpy,newly,runway,wrist,vicar,tower,extra,church,item,murder,rush,mare,show,rhino,dress,three,immune,issue,forest,roar,trout
************************************************************************

# Utility outputs

The utility outputs:
- a public key as a Hex String (EdDSAPublicKey.getAByte() to HEX)
- the same public key in its canonical encoding as a hex string (EdDSAPublicKey.getEncoded() to HEX)
- a secret key as a Hex String (EdDSAPrivateKey.getSeed() to HEX)
- a secret key in its canonical encoding as a hex string (EdDSAPrivateKey.getEncoded() to HEX)
- the private key seed and public key combined (SuperCop format)
- optionally a comma or space (double quoted) separated list of 22 recovery words

# Optional Input parameters
Although it will run without input parameters, the utility accepts a number of input parameters as follows.

## index
This allows you to specify the index for which you want the key generated. Hedera Wallets currently use index 0, so if you need to recover a Hedera Wallet key using this utility, specify an index of 0. The default index value is not provided is -1.

Example ```java -jar hedera-sdk-keygen-1.2-run.jar -index=0```

**outputs**

Your generated key pair for index 0 is:
************************************************************************
Public key (hex)     : 46613dfb55c99d8cadd7ebdad7faf944cbc40e941efc61f7f61dda84cbf9e64d
Public key (enc hex) : 302a300506032b657003210046613dfb55c99d8cadd7ebdad7faf944cbc40e941efc61f7f61dda84cbf9e64d

Secret key (hex)     : 263d4dc53786aecff818d4e56b4e4df14262b4e8054a515b072a97d148c2db2f
Secret key (enc hex) : 302e020100300506032b657004220420263d4dc53786aecff818d4e56b4e4df14262b4e8054a515b072a97d148c2db2f

Seed+PubKey          : 263d4dc53786aecff818d4e56b4e4df14262b4e8054a515b072a97d148c2db2f46613dfb55c99d8cadd7ebdad7faf944cbc40e941efc61f7f61dda84cbf9e64d

Recovery words  : quiet,darn,taste,cross,unable,major,sunny,lens,upturn,daisy,junk,glance,chord,dome,about,woody,help,garden,skill,alert,fight,socket
************************************************************************

## seed
This enables you to provide your own seed if you do not trust your OS's entropy
*Note, the same seed always returns the same key pair. The seed must be 64 hex encoded bytes.*

Example ```java -jar hedera-sdk-keygen-1.2-run.jar -seed=0123456789012345678901234567890101234567890123456789012345678901```

**outputs**

Your generated key pair for index -1 and own seed is:
************************************************************************
Public key (hex)     : 2b2e7b3196c55c0cd13f1b664fe41502ca599fbfbd47b38b5ef99867c4a84259
Public key (enc hex) : 302a300506032b65700321002b2e7b3196c55c0cd13f1b664fe41502ca599fbfbd47b38b5ef99867c4a84259

Secret key (hex)     : 9b59be42cd59f846e237f463939f3d8c12eac74aaedbb1541bedb731d4a87085
Secret key (enc hex) : 302e020100300506032b6570042204209b59be42cd59f846e237f463939f3d8c12eac74aaedbb1541bedb731d4a87085

Seed+PubKey          : 9b59be42cd59f846e237f463939f3d8c12eac74aaedbb1541bedb731d4a870852b2e7b3196c55c0cd13f1b664fe41502ca599fbfbd47b38b5ef99867c4a84259

Recovery words  : diary,tomb,Gandhi,easily,brush,Mafia,regal,script,kind,prison,dictum,script,kind,prison,diary,tomb,Gandhi,easily,brush,Mafia,regal,senior
************************************************************************


## words
Enables you to provide a list of words (comma or space separated - remember to double quote space-separated lists) to recover a key pair from.

Example ```java -jar hedera-sdk-keygen-1.2-run.jar -words=tend,stale,grace,brim,input,streak,convoy,farce,Norway,left,tiger,kite,idle,mile,warp,ice,canvas,terse,mourn,nobody,thigh,Lucy```

Example ```java -jar hedera-sdk-keygen-1.2-run.jar -words="tend stale grace brim input streak convoy farce Norway left tiger kite idle mile warp ice canvas terse mourn nobody thigh Lucy"```

**outputs**

Your recovered key pair for index -1 is:
************************************************************************
Public key (hex)     : 37c50353fc9bca8f3fa610c63624a59a7ebaf16c05fad2d30e3588f21555edbd
Public key (enc hex) : 302a300506032b657003210037c50353fc9bca8f3fa610c63624a59a7ebaf16c05fad2d30e3588f21555edbd

Secret key (hex)     : 9dd3cf5a07ddd826c56955bd5cf10247af23d3d1332331d179092d291e313fd5
Secret key (enc hex) : 302e020100300506032b6570042204209dd3cf5a07ddd826c56955bd5cf10247af23d3d1332331d179092d291e313fd5

Seed+PubKey          : 9dd3cf5a07ddd826c56955bd5cf10247af23d3d1332331d179092d291e313fd537c50353fc9bca8f3fa610c63624a59a7ebaf16c05fad2d30e3588f21555edbd
************************************************************************

## Combining parameters

You can combine -index and -words or -index and -seed, however if you provide -words and -seed, the seed parameter will be ignored.

## Note about compatibility with the Hedera Wallet

Key recovery from words will be compatible with the wallet-generated key *if you supply an index of 0*
