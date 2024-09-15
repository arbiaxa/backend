package tn.esprit.esprobackend.security;
;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
  // public String Key;
    private  static  final long EXPIRATION_TIME = 86400000; //24hours or 86400000 milisecs


    public String generateToken(Map<String,Object> extraclaims,UserDetails userDetails){


      var authorities =userDetails.getAuthorities()
              .stream()
              .map(GrantedAuthority::getAuthority)
              .toList();

        return Jwts
                .builder()
                .setClaims(extraclaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("authorities",authorities)
                .signWith(getKeySignIn(), SignatureAlgorithm.HS256)
                .compact();
    }

    //methode pour decoder l key puis le signé par l algo hmac =>create hmac key
                public SecretKey getKeySignIn(){
               byte[] Decode = Decoders.BASE64.decode("843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3");
               return Keys.hmacShaKeyFor(Decode);


    }

   /* private String extractAllClaims (String token)
    {
        return Jwts
                .parser()
                .verifyWith(getKeySignIn())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .toString();
    }
*/



    private Claims extractAllClaims (String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKeySignIn())
                .build()
                .parseClaimsJws(token)
                .getBody();
                //.toString();
    }


    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        final Claims claims =extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }




    /*private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(getKeySignIn()).build().parseSignedClaims(token).getPayload());
    }
*/

   /* private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        //MEMBER, ADMIN
        return String.join(",", authoritiesSet);
    }*/


    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }


    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }







    /*public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKeySignIn())
                .compact();
    }*/


}
