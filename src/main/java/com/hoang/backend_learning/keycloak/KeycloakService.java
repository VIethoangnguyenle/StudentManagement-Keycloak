package com.hoang.backend_learning.keycloak;

import com.hoang.backend_learning.dto.ErrorsDto;
import com.hoang.backend_learning.dto.PhoneNumberDto;
import com.hoang.backend_learning.dto.RefreshTokenDto;
import com.hoang.backend_learning.dto.RestResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.json.JSONObject;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Slf4j
@PropertySource("classpath:application.properties")
public class KeycloakService {
    @Value("${keycloak.credentials.secret}")
    private String secretKey;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String authUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak-admin.username}")
    private String username;

    @Value("${keycloak-admin.password}")
    private String password;

    private Keycloak keycloakAdmin;

    //Init keycloak role Admin
    @Bean
    private void initKeycloakService() {
        keycloakAdmin = KeycloakBuilder.builder()
            .serverUrl(authUrl)
            .realm("master") // fit
            .clientId("admin-cli") // fit
            .username("admin")
            .password("18081999")
            .resteasyClient(
                new ResteasyClientBuilder()
                    .connectionPoolSize(10).build()
            )
            .build();
    }

    public RestResponseDto<Object> createUser(String loginName, String password, String email, String name) {

        //Set attribute for new user
        UserRepresentation user = new UserRepresentation();
        user.setFirstName(name);
        user.setEmail(email);
        user.setUsername(loginName);
        user.setEmailVerified(false);
        user.setEnabled(true);
        //Access to realm SpringBootKeycloak
        RealmResource realmResource = keycloakAdmin.realm(realm);

        // Response REST
        RestResponseDto<Object> restResponse = new RestResponseDto<>();

        //Class manage all user
        UsersResource usersResource = realmResource.users();

        try (Response response = usersResource.create(user)){
            // create success
            if (response.getStatus() == 201) {

                //Get userId
                String userId = CreatedResponseUtil.getCreatedId(response);

                // Set type create user by password (credential page)
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setTemporary(false);
                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                credentialRepresentation.setValue(password);

                //Manage 1 user
                UserResource userResource = usersResource.get(userId);
                userResource.resetPassword(credentialRepresentation);

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("userId", userId);
                restResponse.success(jsonObject.toMap());
            }

            // Case phone number is used
            else if (response.getStatus() == 409) {
                restResponse.badRequest(ErrorsDto.newBuilder().addUsedField("phoneNumber").build());
            }

            else {
                JSONObject result = new JSONObject().put("error", "unKnown");
                restResponse.badRequest(result);
            }
        }
        catch (Exception exception) {
            log.error("log error naaaa:" + exception.getLocalizedMessage());
        }
        return restResponse;
    }

    public void detUserEmail(String userId, String email) {

        // Get user by userId
        UserResource userResource = keycloakAdmin.realm(realm).users().get(userId);

        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setEmail(email);
        userResource.update(userRepresentation);
    }

    public String getUserIdByEmail(String email) {
        log.trace("Get userId by email");
        UsersResource usersResource = keycloakAdmin.realm(realm).users();

        // List all users
        List<UserRepresentation> users = usersResource.search(null, null, null, email, null, null);

        //Stream al users than filter by email
        UserRepresentation userId = users.stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst()
            .orElse(null);
        return userId != null ? userId.getId() : null;
    }

    public String getUserIdByPhoneNumber(PhoneNumberDto phoneNumber) {
        log.trace("Get userId by phone number");
        UsersResource usersResource = keycloakAdmin.realm(realm).users();

        //List all users
        List<UserRepresentation> users = usersResource.search(phoneNumber.getInternationNumber());

        UserRepresentation userId = users.stream()
            .filter(userRepresentation -> userRepresentation.getUsername().equals(phoneNumber.getInternationNumber()))
            .findFirst()
            .orElse(null);
        return userId != null ? userId.getId() : null;
    }

    //Function to get Json Web Token
    public AccessTokenResponseCustom getUserJWT(String username, String password) {
        Keycloak keycloakUser = KeycloakBuilder.builder()
            .serverUrl(authUrl)
            .realm(realm)
            .username(username)
            .password(password)
            .clientId(clientId)
            .clientSecret(secretKey)
            .build();

        return new AccessTokenResponseCustom(keycloakUser.tokenManager().getAccessToken());
    }

    public boolean invalidateToken(RefreshTokenDto dto, String token) {
        log.trace("Start invalid");
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("client_id", "springboot-login");
        requestParams.add("client_secret", secretKey);
        requestParams.add("refresh_token", dto.getRefreshToken());
        requestParams.add("revoke_tokens_on_logout ", "true");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestParams, headers);
        String url = authUrl + "/realms/" + realm + "/protocol/openid-connect/logout?redirect_uri=encodedRedirectUri";

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForEntity(url, request, Object.class);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}


/*
UserResource: Dùng quản lý tính năng của 1 user (sendVerifyEMail, execute function, update change,......)
UsersResource: QUản lý toàn bộ User trong Realm (Ađ, delete,...., lấy User)
UserRepresentation: Quản lý thuộc tính của 1 user. DÙng để set lại các thuộc tính khi thay đổi
 */
