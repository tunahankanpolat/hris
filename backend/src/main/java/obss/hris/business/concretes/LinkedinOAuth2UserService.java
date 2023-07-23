package obss.hris.business.concretes;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;

import java.net.URI;
import java.util.Map;

public class LinkedinOAuth2UserService extends DefaultOAuth2UserService {

    private final String userEmailUri = "https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))";

    private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {};

    private RestOperations restOperations;

    public LinkedinOAuth2UserService() {
        super();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        MultiValueMap headers = new LinkedMultiValueMap<String,String>();
        headers.add("Accept", "application/json");
        headers.add("Authorization", "Bearer " + userRequest.getAccessToken().getTokenValue());
        URI uri = URI.create(userEmailUri);
        RequestEntity request = new RequestEntity(headers, HttpMethod.GET, uri);

        ResponseEntity<Map<String, Object>> response = getResponse(userRequest, request);
        Map<String, Object> emailMap = response.getBody();


        Map<String, Object> map = oAuth2User.getAttributes();
        oAuth2User.getName();
        JSONObject jsonObject = new JSONObject(map);
        JSONObject emailJson = new JSONObject(emailMap);
        jsonObject.put("firstName", getFirstName(jsonObject));
        jsonObject.put("lastName", getLastName(jsonObject));
        jsonObject.put("profilePicture", getProfilePicture(jsonObject));
        jsonObject.put("email", getEmail(emailJson));

        return new DefaultOAuth2User(oAuth2User.getAuthorities(), jsonObject.toMap(), "firstName");
    }

    private String getFirstName(JSONObject jsonObject) {
        try{
            return jsonObject.getJSONObject("firstName").getJSONObject("localized")
                    .getString(jsonObject.getJSONObject("firstName")
                            .getJSONObject("preferredLocale").getString("language")
                            + "_" + jsonObject.getJSONObject("firstName")
                            .getJSONObject("preferredLocale").getString("country"));
        }catch (Exception e){
            return null;
        }
    }

    private String getLastName(JSONObject jsonObject) {
        try{
            return jsonObject.getJSONObject("lastName").getJSONObject("localized")
                    .getString(jsonObject.getJSONObject("lastName")
                            .getJSONObject("preferredLocale").getString("language")
                            + "_" + jsonObject.getJSONObject("lastName")
                            .getJSONObject("preferredLocale").getString("country"));
        }catch (Exception e){
            return null;
        }
    }

    private String getProfilePicture(JSONObject jsonObject) {
        try{
            int size = jsonObject.getJSONObject("profilePicture").getJSONObject("displayImage~").getJSONArray("elements").length();
            JSONObject element = (JSONObject) jsonObject.getJSONObject("profilePicture").getJSONObject("displayImage~")
                    .getJSONArray("elements").get(size - 1);
            int identifiersSize = element.getJSONArray("identifiers").length();
            JSONObject identifier = element.getJSONArray("identifiers").getJSONObject(identifiersSize - 1);
            return identifier.getString("identifier");
        }catch (Exception e){
            return null;
        }
    }

    private String getEmail(JSONObject jsonObject) {
        try{
            int size = jsonObject.getJSONArray("elements").length();
            JSONObject element = (JSONObject) jsonObject.getJSONArray("elements").get(size - 1);

            return element.getJSONObject("handle~").getString("emailAddress");
        }catch (Exception e){
            return null;
        }
    }

    private ResponseEntity<Map<String, Object>> getResponse(OAuth2UserRequest userRequest, RequestEntity<?> request) {
        try {
            return this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
        }
        catch (OAuth2AuthorizationException ex) {
            OAuth2Error oauth2Error = ex.getError();
            StringBuilder errorDetails = new StringBuilder();
            errorDetails.append("Error details: [");
            errorDetails.append("UserInfo Uri: ")
                    .append(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri());
            errorDetails.append(", Error Code: ").append(oauth2Error.getErrorCode());
            if (oauth2Error.getDescription() != null) {
                errorDetails.append(", Error Description: ").append(oauth2Error.getDescription());
            }
            errorDetails.append("]");
            oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: " + errorDetails.toString(),
                    null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        }
        catch (UnknownContentTypeException ex) {
            String errorMessage = "An error occurred while attempting to retrieve the UserInfo Resource from '"
                    + userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri()
                    + "': response contains invalid content type '" + ex.getContentType().toString() + "'. "
                    + "The UserInfo Response should return a JSON object (content type 'application/json') "
                    + "that contains a collection of name and value pairs of the claims about the authenticated End-User. "
                    + "Please ensure the UserInfo Uri in UserInfoEndpoint for Client Registration '"
                    + userRequest.getClientRegistration().getRegistrationId() + "' conforms to the UserInfo Endpoint, "
                    + "as defined in OpenID Connect 1.0: 'https://openid.net/specs/openid-connect-core-1_0.html#UserInfo'";
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE, errorMessage, null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        }
        catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: " + ex.getMessage(), null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        }
    }
}
