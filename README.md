
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
            <artifactId>lib-tokenapi-bundle</artifactId>
            <version>${project.version}</version>
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

#### License

Apache2 (preliminary)

---

> [Sphereon.com](https://www.sphereon.com) &nbsp;&middot;&nbsp;
> [WSO2](https://wso2.com/api-management) &nbsp;&middot;&nbsp;
