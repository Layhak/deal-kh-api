package co.istad.dealkh.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Objects;

/**
 * KeyUtils is a utility class for managing RSA key pairs for access and refresh tokens.
 * It handles the generation, storage, and retrieval of RSA public and private keys.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Component} - Indicates that this class is a Spring component and a candidate for component scanning and dependency injection.</li>
 * <li>{@link Value} - Injects values from application properties.</li>
 * </ul>
 * </p>
 */
@Component
public class KeyUtils {

    private final Environment environment;

    /**
     * Constructs a new KeyUtils with the specified environment.
     *
     * @param environment the environment to use for retrieving active profiles
     */
    public KeyUtils(Environment environment) {
        this.environment = environment;
    }

    @Value("${ACCESS_TOKEN_PRIVATE_KEY_PATH}")
    private String accessTokenPrivateKey;
    @Value("${ACCESS_TOKEN_PUBLIC_KEY_PATH}")
    private String accessTokenPublicKey;
    @Value("${REFRESH_TOKEN_PRIVATE_KEY_PATH}")
    private String refreshTokenPrivateKey;
    @Value("${REFRESH_TOKEN_PUBLIC_KEY_PATH}")
    private String refreshTokenPublicKey;

    private KeyPair _accessTokenKeyPair;
    private KeyPair _refreshTokenKeyPair;

    /**
     * Retrieves the key pair for access tokens. If the key pair is not already loaded, it loads the key pair from the specified file paths.
     *
     * @return the key pair for access tokens
     */
    private KeyPair getAccessTokenKeyPair() {
        if (Objects.isNull(_accessTokenKeyPair)) {
            _accessTokenKeyPair = getKeyPair(accessTokenPublicKey, accessTokenPrivateKey);
        }
        return _accessTokenKeyPair;
    }

    /**
     * Retrieves the key pair for refresh tokens. If the key pair is not already loaded, it loads the key pair from the specified file paths.
     *
     * @return the key pair for refresh tokens
     */
    private KeyPair getRefreshTokenKeyPair() {
        if (Objects.isNull(_refreshTokenKeyPair)) {
            _refreshTokenKeyPair = getKeyPair(refreshTokenPublicKey, refreshTokenPrivateKey);
        }
        return _refreshTokenKeyPair;
    }

    /**
     * Loads a key pair from the specified file paths or generates a new key pair if the files do not exist.
     *
     * @param publicKeyPath  the path to the public key file
     * @param privateKeyPath the path to the private key file
     * @return the loaded or generated key pair
     * @throws RuntimeException if an error occurs while loading or generating the key pair
     */
    private KeyPair getKeyPair(String publicKeyPath, String privateKeyPath) {
        KeyPair keyPair;
        File publicKeyFile = new File(publicKeyPath);
        File privateKeyFile = new File(privateKeyPath);
        if (publicKeyFile.exists() && privateKeyFile.exists()) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                // Read key from file and create a public key with encryption
                byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
                // X509 -> used for public key
                EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

                // Create a private key
                byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

                keyPair = new KeyPair(publicKey, privateKey);
                return keyPair;

            } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException ex) {
                throw new RuntimeException(ex);
            }

        } else {
            if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
                throw new RuntimeException("Public and private key doesn't exist!");
            }
        }

        File directory = new File("access-refresh-token-keys");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            // Generate a key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();

            try (FileOutputStream fos = new FileOutputStream(publicKeyFile)) {
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
                fos.write(keySpec.getEncoded());
            }
            try (FileOutputStream fos = new FileOutputStream(privateKeyFile)) {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
                fos.write(keySpec.getEncoded());
            }

        } catch (NoSuchAlgorithmException | IOException ex) {
            throw new RuntimeException(ex);
        }

        return keyPair;
    }

    /**
     * Retrieves the public key for access tokens.
     *
     * @return the public key for access tokens
     */
    public RSAPublicKey getAccessTokenPublicKey() {
        return (RSAPublicKey) getAccessTokenKeyPair().getPublic();
    }

    /**
     * Retrieves the private key for access tokens.
     *
     * @return the private key for access tokens
     */
    public RSAPrivateKey getAccessTokenPrivateKey() {
        return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate();
    }

    /**
     * Retrieves the private key for refresh tokens.
     *
     * @return the private key for refresh tokens
     */
    public RSAPrivateKey getRefreshTokenPrivateKey() {
        return (RSAPrivateKey) getRefreshTokenKeyPair().getPrivate();
    }

    /**
     * Retrieves the public key for refresh tokens.
     *
     * @return the public key for refresh tokens
     */
    public RSAPublicKey getRefreshTokenPublicKey() {
        return (RSAPublicKey) getRefreshTokenKeyPair().getPublic();
    }
}
