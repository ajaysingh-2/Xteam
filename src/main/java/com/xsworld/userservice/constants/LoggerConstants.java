package com.xsworld.userservice.constants;

public class LoggerConstants {
    public static final String GENERATING_JWT = "%s :: Generating JWT token for authentication: {} :: %s";
    public static final String JWT_GENERATED = "%s :: JWT token generated successfully. :: %s";
    public static final String EXTRACTING_EMAIL_FROM_JWT = "%s :: Extracting email from JWT token. :: %s";
    public static final String EXTRACTED_EMAIL_FROM_JWT = "%s :: Email extracted from JWT token: {} :: %s";
    public static final String POPULATING_AUTHS_FROM_COLLECTIONS = "%s :: Populating authorities from a collection :: %s";
    public static final String AUTHORITIES_POPULATED = "%s :: Authorities populated: {} :: %s";
    public static final String AUTHENTICATION_FAILED = "%s :: Authentication failed: {} :: %s";
    public static final String TOKEN_HAS_EXPIRED = "%s :: Token has expired:  :: %s";
    public static final String INVALID_TOKEN_FORMAT = "Invalid token format: ";
    public static final String INVALID_TOKEN = "Invalid token: ";
    public static final String CONFIGURING_SECURITY_FILTER_CHAIN = "%s :: Configuring security filter chain... :: %s";
    public static final String CONFIGURING_CORS = "%s :: Configuring CORS... :: %s";
    public static final String SECURITY_FILTER_CHAIN_CONFIGURATION_COMPLETED = "%s :: Security filter chain configuration completed. :: %s";
}
