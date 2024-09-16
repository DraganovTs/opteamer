package homecode.opteamer.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String SECRET_KEY = "opteamer_secret_key"; // Secret must be consistent

    // Extract the username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract specific claims
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes()) // Use byte array for secret key
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("Error parsing claims: " + e.getMessage());
            return null;
        }
    }

    // Check if token has expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, userDetails.getUsername());
        System.out.println("Generated JWT Token: " + token); // Debugging log
        return token;
    }

    // Create token
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiry
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()) // Sign with byte array
                .compact();
    }

    // Validate the token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        System.out.println("Username extracted from JWT: " + username); // Debugging log
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}