
<h1 align="center">
  <br>
  <a href="https://www.sphereon.com"><img src="https://sphereon.com/content/themes/sphereon/assets/img/logo.svg" alt="Sphereon" width="400"></a>
  <br>
  Sphereon API and WSO2 Authentication library
  <br>
</h1>

<h4 align="center">A Java implementation for performing authentication against the WSO2 <a href="https://wso2.com/api-management/" target="_blank">API Manager</a> and <a href="https://docs.wso2.com/display/AM210" target="_blank">API Store</a>.</h4>

You can use this Java library to perform authentication against the Sphereon API gateway (WSO2).


## Key Features
* Simple and clean request and configuration builders
* Request (refresh) tokens with multiple supported credential types
* Configuration manager (optional) with auto-encrypt functionality
    * Support for standard property and XML property files
    * Support for Spring application.properties  
    * Support for System environment variables

## How To Use
To clone and use this library application, you'll need [Git](https://git-scm.com), [Maven](https://maven.apache.org/) and your favorite Java IDE. 

To build locally:
```bash
# Clone this repository
> git clone https://github.com/Sphereon-OpenSource/authentication-lib.git

# Go into the repository
> cd authentication-lib

# Build and install into your Maven repository
> mvn clean install

```

Or use the published versions in our Maven repository:
```xml
# Use the artifact in your Maven pom or Gralde build configuration  
    <repositories>
        <repository>
            <id>sphereon-public</id>
            <name>Sphereon Public</name>
            <url>https://nexus.qa.sphereon.com/repository/sphereon-public/</url>
         </repository>
    </repositories>
...
    <dependency>
        <groupId>com.sphereon.public</groupId>
        <artifactId>authentication-lib-main</artifactId> <!-- Use authentication-lib-osgi for OSGI bundle  -->
        <version>0.1.7</version>
    </dependency>
```


<!-- ## published artifact

You can [find](https://mvnrepository.com) the latest version on mvnrepository.com
-->

## Getting started
Basic usage without configuration
```java
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
```java
        ApiConfiguration configuration = new ApiConfiguration.Builder()
                .withApplication("demo-application")
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigFile(new File("./config/authorization.properties"))
                .withAutoEncryptSecrets(true)
                .withAutoEncryptionPassword("<secret>")
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();
                
        AuthenticationApi authenticationApi = new AuthenticationApi.Builder()
            .withConfiguration(configuration)
            .build();
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
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;client-credential, password, refresh-token, ntlm, kerberos, saml2

 Each of these types have their own set of parameters described here:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>(The parameter names shown are compatible with the property files.)</i>
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
[Apache2](https://www.apache.org/licenses/LICENSE-2.0)

---

> [Sphereon.com](https://www.sphereon.com) &nbsp;&middot;&nbsp;
> [WSO2](https://wso2.com/api-management) &nbsp;&middot;&nbsp;
