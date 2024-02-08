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

    public static final String LOADING_USER_BY_USERNAME = "%s :: Loading user by username: {} :: %s";
    public static final String LOADED_USER = "%s :: User loaded successfully: {} :: %s";

    public static final String FINDING_USER_BY_ID = "%s :: Finding user by ID: {} :: %s";
    public static final String FINDING_USER_BY_JWT = "%s :: Finding user profile by JWT  :: %s";

    public static final String ATTEMPT_TO_SIGN_UP_WITH_MAIL = "%s :: User attempting to sign up with email: {} :: %s";
    public static final String SIGN_UP_SUCCESSFUL = "%s :: User attempting to sign up with email: {} :: %s";
    public static final String ATTEMPT_TO_LOGIN_WITH_MAIL = "%s :: User attempting to log in with email: {} :: %s";
    public static final String SIGN_IN_SUCCESSFUL = "%s :: Sign-in successful for user: {} :: %s";

    public static final String RECEIVED_GET_REQUEST_TO_FIND_USER_BY_ID = "%s :: Received a GET request to find user by ID: {} :: %s";
    public static final String RECEIVED_GET_REQUEST_TO_FIND_USER_USING_JWT = "%s :: Received a GET request to find user profile using JWT :: %s";
}
