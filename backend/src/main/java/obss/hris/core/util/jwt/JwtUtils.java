package obss.hris.core.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final String AUTHORITIES_KEY = "authorities";

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean hasClaim(String token, String claimName){
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public String extractAuthority(String token) {
        List<Claims> authorities = extractAllClaims(token).get(AUTHORITIES_KEY, ArrayList.class);
        if(authorities == null || authorities.isEmpty()){
            return null;
        }
        LinkedHashMap<String, Object> authority = (LinkedHashMap) authorities.get(0);
        return authority.get("authority").toString();
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,userDetails);
    }

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, authentication);
    }

    private String createToken(Map<String, Object> claims, Authentication authentication) {
        String username = ((DefaultOAuth2User) authentication.getPrincipal()).getName();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .claim(AUTHORITIES_KEY, authentication.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims){
        return createToken(claims,userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .claim(AUTHORITIES_KEY, userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenValid(String token, OAuth2User oAuth2User){
        final String username = extractUsername(token);
        return (username.equals(oAuth2User.getName()) && !isTokenExpired(token));
    }
}
