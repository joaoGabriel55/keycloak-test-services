package services;

import org.keycloak.admin.client.Keycloak;

public class AuthService {
    public static final String REALM_APP = "sgeol-middleware";
    public static final String KEYCLOAK_BASE_PATH = "http://localhost:8080/auth";
    public static final String USERNAME = "sgeoladmin";
    public static final String PASSWORD = "123456";
    public static final String CLIENT_ID = "admin-cli";
    public static final String SECRET = "9cdf7da2-1d35-4391-ba95-e2a52ef49591";

    public static Keycloak adminAuthByToken(String token) {
        return Keycloak.getInstance(KEYCLOAK_BASE_PATH, REALM_APP, CLIENT_ID, token);
    }

    public static Keycloak adminAuth() {
        return Keycloak.getInstance(KEYCLOAK_BASE_PATH, REALM_APP, USERNAME, PASSWORD, CLIENT_ID, SECRET);
    }
}
