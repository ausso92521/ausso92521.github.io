package tech.leftii.animaltracker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Record used so that we can set Rsa keys for JWT encoding/decoding in application.properties file
 * @param publicKey Public Key set in application.properties
 * @param privateKey Private Key set in application.properties
 */
@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
