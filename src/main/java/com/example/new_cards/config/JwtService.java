package com.example.new_cards.config;

import com.example.new_cards.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "/VzS7Z0snz35CBtzxtXxMP3C1D/Eske96Dmp6hK2smKZ9wJhcVf91zNZIxxa9itm8dwkFwasUlWqmHQFWc9wNQN7ha44Rkcfmgl3m0Ed0g2BQR13hC+0xR3iLYjO/t50XJdeQ34rCAzHpUdBhvYmqCzHbCtNL4N70F3uzc6IX8AmIdvj2bjR58KQjyqv4MM+HW/TND8DNtvf8fRm9V+/d4vQo9i7tciIufWS3plitbpB9617qLLWUcXwWb1OGtVi7uCSf29UaQhNeMuWtP3ODwWdMLN8y2fCKo+bXnnxMGqG7NStMipfm6xGjPbBXPto788Tv8j4HbPY1TeZKX95YqTkgHdnYhNkgvP8Go43CCM=";


    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> myClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(myClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000 * 60 * 24)) // Expire in a day
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(User userDetails) {
        Map claims = new HashMap(); //TODO: Fix role
        claims.put("ROLE", userDetails.getRole());
        return generateToken(claims, userDetails);
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(jwtToken); // is the username in token == to userDetails
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
