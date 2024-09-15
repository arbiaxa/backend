package tn.esprit.esprobackend.auth;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import tn.esprit.esprobackend.entities.Role;
import tn.esprit.esprobackend.entities.user;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprobackend.repositories.userRepo;
import tn.esprit.esprobackend.security.JwtService;
import tn.esprit.esprobackend.security.UserDetailServiceImp;
import tn.esprit.esprobackend.services.twoFactorAuthService;

import java.security.SecureRandom;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final userRepo repository;
    private final JwtService jwtService;
    private final UserDetailServiceImp userDetailss;
   // private final EmailService email;
private final AuthenticationManager authenticationManager;
private  final twoFactorAuthService tfaService;


    public AuthenticationResponse register(RegisterRequest request) {
//default role
        var userRole = "USER";
        var userr = user.builder()
                .nameU(request.getNameU())
                .surnameU(request.getSurnameU())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(userRole))
                .telnum(request.getTelnum())
                .img(request.getImg())
                .accountLocked(false)
                .enabled(true)
                .mfaEnabled(false)
                .secretKey(null)
                .build();

        user savedUser = repository.save(userr);
        var claims = new HashMap<String,Object>();
        claims.put("nameU", savedUser.getNameU());


        String jwt = jwtService.generateToken(claims,savedUser);


        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwt, savedUser.getNameU(), savedUser.getSurnameU(), savedUser.getRole(), savedUser.getIdU(), savedUser.isMfaEnabled(),null);

        return authenticationResponse;


     /*   user userr = new user();
        userr.setNameU(request.getNameU());
        userr.setSurnameU(request.getSurnameU());
        userr.setEmail(request.getEmail());
        userr.setPassword(passwordEncoder.encode(request.getPassword()));
        userr.setRole(Role.valueOf(userRole));
        userr.setTelnum(request.getTelnum());
        userr.setImg(request.getImg());
        userr.setAccountLocked(false);
        userr.setEnabled(false);
*/

    }

    /*
        private void sendValidationEmail(user u) {
            //var newToken = generateAndSaveActvationToken();
         le code qui sera envoyé
        var newToken = generateActivationCode(6);


    }

    private String generateAndSaveActvationToken() {
String generatedToken = generateActivationCode(6);
//String token = jwtService.generateToken();
return null;
    }

    private String generateActivationCode(int length) {

        String character = "0123456789";//elemnts qui seront generer le code
        StringBuilder codeBuilder =new StringBuilder();
        //randomvalue
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i<length;i++)
        {//nekhou index
            int randomIndex= secureRandom.nextInt(character.length());
           ///construire le code
            codeBuilder.append(character.charAt(randomIndex));
        }
        return codeBuilder.toString();


    }*/




    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        try {
            var auth = authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())

            );

            var claims = new HashMap<String, Object>();
            var user = (user) auth.getPrincipal();

//j'ajout ece code: a chaque login j'update le code
          if(user.isMfaEnabled())
            {
                //update le secret
                repository.updateSecretKey(user.getEmail(),tfaService.generateNewSecret());
                System.out.println(tfaService.QrCodeImageURi(user.getSecretKey()));

              return new AuthenticationResponse("","","",null,null,true,tfaService.QrCodeImageURi(user.getSecretKey()));


            }

            claims.put("nameU", user.getNameU());
            String jwt = jwtService.generateToken(claims, user);

            return new AuthenticationResponse(jwt,user.getNameU(),user.getSurnameU(),user.getRole(), user.getIdU(), user.isMfaEnabled(),tfaService.QrCodeImageURi(user.getSecretKey()));
        }


        catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }




    }

    public AuthenticationResponse verifCode(VerifRequest verifRequest) {
      // user userr=userRepo.findByEmail(verifRequest.getEmail());
        UserDetails userDetails = userDetailss.loadUserByUsername(verifRequest.getEmail()); // Assurez-vous d'avoir votre service utilisateur (userService) correctement injecté
        user userre = (user) userDetails; // Peut-être que vous devez convertir votre objet UserDetails en un objet user (vérifiez cela)
        System.out.println(userre.getName());
        System.out.println(userre.getSecretKey());
        System.out.println(verifRequest.getCode());

        if (tfaService.isOtpNotValid(userre.getSecretKey(), verifRequest.getCode())) {

            throw new BadCredentialsException("Code is not correct");
        }

            System.out.println("validcode");
            var claims = new HashMap<String, Object>();
            // var user = (user) auth.getPrincipal();
            String jwt = jwtService.generateToken(claims, userre);



            return new AuthenticationResponse(jwt,userre.getNameU(),userre.getSurnameU(),userre.getRole(), userre.getIdU(), userre.isMfaEnabled(),tfaService.QrCodeImageURi(userre.getSecretKey()));



    }





    public AuthenticationResponse verifyCode(
            VerifRequest verificationRequest
    ) {
        UserDetails userDetails = userDetailss.loadUserByUsername(verificationRequest.getEmail()); // Assurez-vous d'avoir votre service utilisateur (userService) correctement injecté
        user userre = (user) userDetails;
        if (tfaService.isOtpNotValid(userre.getSecretKey(), verificationRequest.getCode())) {

            throw new BadCredentialsException("Code is not correct");
        }


        System.out.println("validcode");
        var claims = new HashMap<String, Object>();
        // var user = (user) auth.getPrincipal();
        String jwt = jwtService.generateToken(claims, userre);



        return new AuthenticationResponse(jwt,userre.getNameU(),userre.getSurnameU(),userre.getRole(), userre.getIdU(), userre.isMfaEnabled(),tfaService.QrCodeImageURi(userre.getSecretKey()));

    }





}
