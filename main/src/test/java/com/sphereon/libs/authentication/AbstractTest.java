package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.*;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Assert;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractTest {

    protected static final String APPLICATION_NAME = "expiring-tokens";

    protected static final String SPHEREON_AUTH_PROPERTIES = "sphereon-auth.properties";
    protected static final String SPHEREON_AUTH_XML = "sphereon-auth.xml";


    protected ApiConfiguration createPropertyFileConfiguration(final String configFile) {
        File standaloneConfigFile = new File("./config/" + configFile);
        standaloneConfigFile.delete();
        return new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigFile(standaloneConfigFile)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .withAutoEncryptSecrets(true)
                .withAutoEncryptionPassword("UnitTestPassword")
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .build();
    }


    protected ApiConfiguration loadPropertyFileConfiguration(final String configFile) {
        return new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigFile(new File("./config/" + configFile))
                .withAutoEncryptSecrets(true)
                .withAutoEncryptionPassword("UnitTestPassword")
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();
    }


    protected void changeGrantValues(ApiConfiguration loadedConfig) {
        switch (loadedConfig.getDefaultGrant().getGrantType()) {
            case CLIENT_CREDENTIALS:
                break;
            case REFRESH_TOKEN:
                RefreshTokenGrant refreshTokenGrant = (RefreshTokenGrant) loadedConfig.getDefaultGrant();
                refreshTokenGrant.setRefreshToken("New refreshTokenGrant");
                break;
            case PASSWORD:
                PasswordGrant passwordGrant = (PasswordGrant) loadedConfig.getDefaultGrant();
                passwordGrant.setPassword("New password");
                break;
            case NTLM:
                NtlmGrant ntlmGrant = (NtlmGrant) loadedConfig.getDefaultGrant();
                ntlmGrant.setWindowsToken("New windows token");
                break;
            case KERBEROS:
                KerberosGrant kerberosGrant = (KerberosGrant) loadedConfig.getDefaultGrant();
                kerberosGrant.setKerberosToken("New kerberos token");
                break;
            case SAML2:
                SAML2Grant saml2Grant = (SAML2Grant) loadedConfig.getDefaultGrant();
                saml2Grant.setAssertion("New assertion");
                break;
        }
    }


    protected void assertPropertyValues(File propertiesFile, String... keyValues) throws Exception {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setFile(propertiesFile));
        FileBasedConfiguration config = builder.getConfiguration();
        for (String keyValue : keyValues) {
            int firstEqToken = keyValue.indexOf("=");
            Assert.assertTrue(firstEqToken > -1);
            String key = keyValue.substring(0, firstEqToken);
            String value = keyValue.substring(firstEqToken + 1);
            Assert.assertEquals("The result for key " + key + " does not match", value, config.getString(key));
        }
    }


    // ===> Major hack, use only in tests <===
    @SuppressWarnings("unchecked")
    public static <K, V> void setEnv(final String key, final String value) throws Exception {
        try {
            final Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            final Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            final boolean environmentAccessibility = theEnvironmentField.isAccessible();
            theEnvironmentField.setAccessible(true);

            final Map<K, V> env = (Map<K, V>) theEnvironmentField.get(null);

            if (SystemUtils.IS_OS_WINDOWS) {
                if (value == null) {
                    env.remove(key);
                } else {
                    env.put((K) key, (V) value);
                }
            } else {
                final Class<K> variableClass = (Class<K>) Class.forName("java.lang.ProcessEnvironment$Variable");
                final Method convertToVariable = variableClass.getMethod("valueOf", String.class);
                final boolean conversionVariableAccessibility = convertToVariable.isAccessible();
                convertToVariable.setAccessible(true);

                final Class<V> valueClass = (Class<V>) Class.forName("java.lang.ProcessEnvironment$Value");
                final Method convertToValue = valueClass.getMethod("valueOf", String.class);
                final boolean conversionValueAccessibility = convertToValue.isAccessible();
                convertToValue.setAccessible(true);

                if (value == null) {
                    env.remove(convertToVariable.invoke(null, key));
                } else {
                    // we place the new value inside the map after conversion so as to
                    // avoid class cast exceptions when rerunning this code
                    env.put((K) convertToVariable.invoke(null, key), (V) convertToValue.invoke(null, value));

                    // reset accessibility to what they were
                    convertToValue.setAccessible(conversionValueAccessibility);
                    convertToVariable.setAccessible(conversionVariableAccessibility);
                }
            }
            // reset environment accessibility
            theEnvironmentField.setAccessible(environmentAccessibility);

            // we apply the same to the case insensitive environment
            final Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            final boolean insensitiveAccessibility = theCaseInsensitiveEnvironmentField.isAccessible();
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            // Not entirely sure if this needs to be casted to ProcessEnvironment$Variable and $Value as well
            final Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
            if (value == null) {
                // remove if null
                cienv.remove(key);
            } else {
                cienv.put(key, value);
            }
            theCaseInsensitiveEnvironmentField.setAccessible(insensitiveAccessibility);
        } catch (final ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Failed setting environment variable <" + key + "> to <" + value + ">", e);
        } catch (final NoSuchFieldException e) {
            // we could not find theEnvironment
            final Map<String, String> env = System.getenv();
            Stream.of(Collections.class.getDeclaredClasses())
                    // obtain the declared classes of type $UnmodifiableMap
                    .filter(c1 -> "java.util.Collections$UnmodifiableMap".equals(c1.getName()))
                    .map(c1 -> {
                        try {
                            return c1.getDeclaredField("m");
                        } catch (final NoSuchFieldException e1) {
                            throw new IllegalStateException("Failed setting environment variable <" + key + "> to <" + value + "> when locating in-class memory map of environment", e1);
                        }
                    })
                    .forEach(field -> {
                        try {
                            final boolean fieldAccessibility = field.isAccessible();
                            field.setAccessible(true);
                            // we obtain the environment
                            final Map<String, String> map = (Map<String, String>) field.get(env);
                            if (value == null) {
                                // remove if null
                                map.remove(key);
                            } else {
                                map.put(key, value);
                            }
                            field.setAccessible(fieldAccessibility);
                        } catch (final ConcurrentModificationException e1) {
                        } catch (final IllegalAccessException e1) {
                            throw new IllegalStateException("Failed setting environment variable <" + key + "> to <" + value + ">. Unable to access field!", e1);
                        }
                    });
        }
    }


    protected void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis() + 1000);
        } catch (InterruptedException e) {
        }
    }
}
