package com.owncloud.android.utils;

import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import java.io.IOException;
import java.security.KeyPair;

import androidx.annotation.VisibleForTesting;

/**
 * copied & modified from:
 * https://github.com/awslabs/aws-sdk-android-samples/blob/master/CreateIotCertWithCSR/src/com/amazonaws/demo/csrcert/CsrHelper.java
 * accessed at 31.08.17
 * Original parts are licensed under the Apache License, Version 2.0: http://aws.amazon.com/apache2.0
 * Own parts are licensed under GPLv3+.
 */

public final class CsrHelper {

    private CsrHelper() {
        // utility class -> private constructor
    }

    /**
     * Generate CSR with PEM encoding
     *
     * @param keyPair the KeyPair with private and public keys
     * @param userId  userId of CSR owner
     * @return PEM encoded CSR string
     * @throws IOException               thrown if key cannot be created
     * @throws OperatorCreationException thrown if contentSigner cannot be build
     */
    public static String generateCsrPemEncodedString(KeyPair keyPair, String userId)
            throws IOException, OperatorCreationException {
        PKCS10CertificationRequest csr = CsrHelper.generateCSR(keyPair, userId);
        byte[] derCSR = csr.getEncoded();
        return "-----BEGIN CERTIFICATE REQUEST-----\n" + android.util.Base64.encodeToString(derCSR,
                android.util.Base64.NO_WRAP) + "\n-----END CERTIFICATE REQUEST-----";
    }

    /**
     * Create the certificate signing request (CSR) from private and public keys
     *
     * @param keyPair the KeyPair with private and public keys
     * @param userId  userId of CSR owner
     * @return PKCS10CertificationRequest with the certificate signing request (CSR) data
     * @throws IOException               thrown if key cannot be created
     * @throws OperatorCreationException thrown if contentSigner cannot be build
     */
    @VisibleForTesting
    public static PKCS10CertificationRequest generateCSR(KeyPair keyPair, String userId) throws IOException,
        OperatorCreationException {
        String principal = "CN=" + userId;
        AsymmetricKeyParameter privateKey = PrivateKeyFactory.createKey(keyPair.getPrivate().getEncoded());
        AlgorithmIdentifier signatureAlgorithm = new DefaultSignatureAlgorithmIdentifierFinder().find("SHA1WITHRSA");
        AlgorithmIdentifier digestAlgorithm = new DefaultDigestAlgorithmIdentifierFinder().find("SHA-1");
        ContentSigner signer = new BcRSAContentSignerBuilder(signatureAlgorithm, digestAlgorithm).build(privateKey);

        PKCS10CertificationRequestBuilder csrBuilder = new JcaPKCS10CertificationRequestBuilder(new X500Name(principal),
                                                                                                keyPair.getPublic());
        ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
        extensionsGenerator.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
        csrBuilder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, extensionsGenerator.generate());

        return csrBuilder.build(signer);
    }
}
