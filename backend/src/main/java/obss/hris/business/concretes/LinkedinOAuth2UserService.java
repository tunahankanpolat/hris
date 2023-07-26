package obss.hris.business.concretes;

import obss.hris.business.abstracts.CandidateService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.model.entity.Candidate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class LinkedinOAuth2UserService extends DefaultOAuth2UserService {

    private final String userEmailUri = "https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))";
    private final String rapidApi = "https://linkedin-profiles-and-company-data.p.rapidapi.com/profile-details";
    private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {};

    private RestOperations restOperations;

    private CandidateService candidateService;

    private ModelMapperService modelMapperService;

    @Autowired
    public LinkedinOAuth2UserService(CandidateService candidateService, ModelMapperService modelMapperService) {
        super();
        this.candidateService = candidateService;
        this.modelMapperService = modelMapperService;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }

    public void getUserDataFromRapidApi(){
        MultiValueMap headers = new LinkedMultiValueMap<String,String>();
        headers.add("Accept", "application/json");
        headers.add("X-RapidAPI-Key", "6baf1f8933msh621c403aef25854p10ceb6jsn5c738a4ce569");
        headers.add("X-RapidAPI-Host", "linkedin-profiles-and-company-data.p.rapidapi.com");

        Map body = new HashMap<String, Object>();
        body.put("profile_id", "tunahankanpolat");
        body.put("profile_type", "personal");
        body.put("contact_info", false);
        body.put("recommendations", false);
        body.put("related_profiles", false);

        URI uri = URI.create(rapidApi);
        RequestEntity request = new RequestEntity(body, headers, HttpMethod.POST, uri);
        ResponseEntity<Map<String, Object>> response = this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
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
        jsonObject.put("linkedinId", jsonObject.get("id"));
        jsonObject.remove("id");
        JSONObject emailJson = new JSONObject(emailMap);
        jsonObject.put("firstName", getFirstName(jsonObject));
        jsonObject.put("lastName", getLastName(jsonObject));
        jsonObject.put("profilePicture", getProfilePicture(jsonObject));
        jsonObject.put("email", getEmail(emailJson));

        Candidate candidate = createCandidateIfNoExist(jsonObject.toMap());
        jsonObject.put("candidateId", candidate.getCandidateId());
        return new DefaultOAuth2User(oAuth2User.getAuthorities(), jsonObject.toMap(), "candidateId");
    }

    private Candidate createCandidateIfNoExist(Map<String, Object> attributes){
        Candidate candidate = this.candidateService.getCandidateByLinkedinId(attributes.get("linkedinId").toString());

        if(candidate == null){
            candidate = this.modelMapperService.forCreate().map(attributes, Candidate.class);
            this.candidateService.createCandidate(candidate);
        }
        return candidate;
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
