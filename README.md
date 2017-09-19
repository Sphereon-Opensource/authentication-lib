
<h1 align="center">
  <br>
  <a href="https://www.sphereon.com"><img src="https://sphereon.com/wp-content/uploads/2016/11/sphereon-logo.png" alt="Sphereon" width="200"></a>
  <br>
  Token API library
  <br>
</h1>

<h4 align="center">A Java implementation for the WSO2 <a href="https://wso2.com/api-management/" target="_blank">API Manager</a> and <a href="https://docs.wso2.com/display/AM120/API+Store" target="_blank">API Store</a> .</h4>

<!--<p align="center">
##  <a href="https://gitter.im/amitmerchant1990/electron-markdownify"><img src="https://badges.gitter.im/amitmerchant1990/electron-markdownify.svg"></a>
##</p> -->
<br>

## Key Features
* Simple and clean request and configuration builders
* Configuration manager (optional) with auto-encrypt functionality
* Support for standard property and XML property files
* Support for Spring application.properties  
* System environment support

## How To Use
To clone and use this library application, you'll need [Git](https://git-scm.com), [Maven](https://maven.apache.org/) and your favorite Java IDE. 

```bash
# Clone this repository
> git clone https://github.com/Sphereon/tokenapi-lib.git

# Go into the repository
> cd tokenapi-lib

# Build and install into your Maven repository
> mvn clean install

# Use the artifact in your Maven pom or Gralde build configuration  
        <dependency>
            <groupId>com.sphereon.libs</groupId>
            <artifactId>authentication-api-lib-bundle</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

```


<!-- ## published artifact

You can [find](https://mvnrepository.com) the latest version on mvnrepository.com
-->

## Getting started
Basic usage without configuration
```bash
        AuthenticationApi authenticationApi = new AuthenticationApi.Builder().build();
        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withConsumerKey("<value>")
                .withConsumerSecret("<value>")
                .withScope("<value>")
                .withValidityPeriod(<value>)
                .build();
        TokenResponse tokenResponse = tokenRequest.execute();
```

Using the configuration manager reading from an existing property file
```bash
        ApiConfiguration configuration = new ApiConfiguration.Builder()
                .withApplication("demo-application")
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigFile(new File("./config/authorization.properties"))
                .withAutoEncryptSecrets(true)
                .withAutoEncryptionPassword("<secret>")
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();
                
        AuthenticationApi authenticationApi = new AuthenticationApi.Builder().build();
        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withConfiguration("<value>")
                .withScope("<value>")
                .withValidityPeriod(<value>)
                .build();
        TokenResponse tokenResponse = tokenRequest.execute();
```

The property configuration layout is as follows: (dummy/example values are shown)
```bash
authentication-api.demo-application.gateway-base-url = https://gw.api.cloud.com/
authentication-api.demo-application.persistence-mode = READ_WRITE
authentication-api.demo-application.auto-encrypt = true
authentication-api.demo-application.consumer-key = cx2ReTjbfq5VbnR$f4$JJj8vTH5h
authentication-api.demo-application.consumer-secret = ENC(5GLQ4Y2AfaXvd2tA/ctw5mjOKyHg3TwJo/JTT2Dr4paXYQ7a2P=)
authentication-api.demo-application.grant-type = password
authentication-api.demo-application.username = DemoUser
authentication-api.demo-application.password = ENC(V29i2BP2mBTxaqsDk5HuzZOkZ9Qil5au)
```
Remarks
* When no application is specified the .demo-application part will be omitted
* The values in the configuration files always override the settings done by the API implementor.
* When PersistentMode is set to READ_ONLY no settings from the configuration builder are being written to the property file, except when auto-encrypt = true.  
* When auto-encrypt = true the secret fields will be encrypted if there is write access to the configuration file.  

## Supported grant types
The grant-type property supports the following values:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;client-credential, password, refresh-token, ntlm, kerberos, saml2

 Each of these types have their own set of parameters described here:
 <i>(The parameter names shown are compatible with the property files.)</i>
* ClientCredentialGrant
    - No parameters necessary, only client key and client secret will be used to generate a new token.
    - No refresh token will be returned.
* PasswordGrant
    - Parameters: username and password from the API mananger account are required.
    - A refresh token will be returned which can be used with RefreshTokenGrant.
* RefreshTokenGrant
    - Parameters: refresh-token, a previously obtained refresh token.
    - A new refresh token will be returned.
* NtlmGrant
    - Parameters: windows-token, a pre-generated Windows NTLM token.
* KerberosGrant
    - Parameters: kerberos-realm and kerberos-token
* SAML2Grant
    - Parameters: saml2-assertion

#### License
[Apache2](https://www.apache.org/licenses/LICENSE-2.0) (preliminary)

---

> [Sphereon.com](https://www.sphereon.com) &nbsp;&middot;&nbsp;
> [WSO2](https://wso2.com/api-management) &nbsp;&middot;&nbsp;
