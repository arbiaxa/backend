package tn.esprit.esprobackend.auth;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprobackend.entities.position;
import tn.esprit.esprobackend.entities.user;
import tn.esprit.esprobackend.repositories.userRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//import tn.esprit.esprobackend.config.RegisterRequest;
//import com.helloIftekhar.springJwt.service.AuthenticationService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import tn.esprit.esprobackend.security.UserDetailServiceImp;
import tn.esprit.esprobackend.services.UserServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthenticationController {
  private final  AuthenticationService authService;
    private final UserDetailServiceImp userDetailss;
    private final userRepo userRepository;
    private final UserServiceImpl userService;
  //  auth/positions

    @GetMapping("/notasaa")
    public ResponseEntity <? > notAffectedPos() {
        //   return userService.retriveNotAffected();
        return ResponseEntity.ok( userService.retriveNotAffected());
    }
    @GetMapping("/notassaa")
    public ResponseEntity <? > notAffectedPosList(){
        //   return userService.retriveNotAffected();
        return ResponseEntity.ok( userService.getPositionsByIds());
    }




    @GetMapping("/positionss")
    public List<Object[]> groupPositionsByUserCount() {
        return userService.groupPositionsByUserCountt();
    }


    @GetMapping("/disabled-count")
    public ResponseEntity<Long> getDisabledAccountCount() {
        long disabledCount = userService.getDisabledAccountCount();
        return ResponseEntity.ok(disabledCount);
    }



    @GetMapping("/enabled-count")
    public ResponseEntity<Long> getEnabledAccountCount() {
        long enabledCount = userService.getEnabledAccountCount();
        return ResponseEntity.ok(enabledCount);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Si des erreurs de validation sont présentes, construisez une réponse appropriée avec les messages d'erreur
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        // Si les données sont valides, procédez à l'enregistrement de l'utilisateur
        AuthenticationResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }





    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
}






    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode(@RequestBody VerifRequest verifRequest )
    {
        return ResponseEntity.ok(authService.verifCode(verifRequest));
    }






    @PostMapping("/updatemfa/{idU}")
    public ResponseEntity<?> updateMfaEnabled(@PathVariable("idU") Long idU, @RequestBody String mfaEnabled) {
        // Convertir la chaîne en boolean
        boolean mfaEnabledValue = Boolean.parseBoolean(mfaEnabled);
     System.out.println(mfaEnabled);
        System.out.println(mfaEnabledValue);

       user userr=userService.retrieveUser(idU);

        System.out.println("try it ");
        if (userr.getEmail() != null) {
            userRepository.updateMfaEnabled(userr.getEmail(), mfaEnabledValue);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Collections.singletonMap("message", "MFA state changed successfully"));
        } else {
            System.out.println("MFA problem not set");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Collections.singletonMap("error", "MFA problem not set"));
        }
    }


    @GetMapping("/checkTwoFactorAuth")
    public ResponseEntity<Boolean> checkTwoFactorAuth(@RequestParam String email) {
        // Ici, vous pouvez mettre en œuvre la logique pour vérifier si l'authentification à deux facteurs est nécessaire.
        // Par exemple, vous pouvez consulter votre base de données pour savoir si l'utilisateur avec l'email donné a activé l'authentification à deux facteurs.
        // Je vais vous fournir un exemple simple qui retourne toujours true pour activer l'authentification à deux facteurs.

        // Dans cet exemple, nous supposons que l'authentification à deux facteurs est toujours nécessaire.
        return ResponseEntity.ok(true);
    }


}

