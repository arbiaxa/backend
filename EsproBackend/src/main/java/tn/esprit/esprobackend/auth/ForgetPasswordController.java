package tn.esprit.esprobackend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprobackend.entities.otpReset;
import tn.esprit.esprobackend.entities.user;
import tn.esprit.esprobackend.repositories.otpResetRepo;
import tn.esprit.esprobackend.repositories.userRepo;
import tn.esprit.esprobackend.security.UserDetailServiceImp;
import tn.esprit.esprobackend.services.EmailService;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/resetpwd")
@RequiredArgsConstructor

//@PreAuthorize("hasAuthority('ADMIN')")
public class ForgetPasswordController {
    @Autowired
    userRepo repo;
@Autowired
     otpResetRepo otprepo;

    private final EmailService emailService;


    private final PasswordEncoder passwordEncoder;

    private final UserDetailServiceImp userDetailss;



    @GetMapping("/verifyMailr/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable("email") String email) {
        System.out.println(email);
        int otp = otpGenerate();
        try {
            UserDetails userDetails = userDetailss.loadUserByUsername(email); // Assurez-vous d'avoir votre service utilisateur (userService) correctement injecté
            user userre = (user) userDetails; // Peut-être que vous devez convertir votre objet UserDetails en un objet user (vérifiez cela)
            System.out.println(userre.getName());
            otpReset oR = new otpReset();
            oR.setExpirationTime(new Date((System.currentTimeMillis() + 70 * 100000)));
            oR.setOtp(otp);
            oR.setUserr(userre);

            System.out.println(oR.getOtp());
            System.out.println(oR.getExpirationTime());
            System.out.println(oR.getUserr());
            System.out.println(otp);

            String text = "This is your OTP to reset your password: " + otp;

            emailService.sendSimpleMailMessage(email, "OTP FOR YOUR FORGOTTEN PASSWORD is  :", text);
            emailService.addOtp(oR);

            return ResponseEntity.ok().body("{\"status\": \"success\", \"message\": \"Email validation sent successfully for user\"}");


        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address");
        }


    }






    /*@GetMapping ("/verifyMailr/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable("email") String email){
System.out.println(email);
int otp =otpGenerate();




     /*   var userr =repo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("please Enter a valid email, "));
*/

      /* user userre=(user) userDetails.loadUserByUsername(email);
        System.out.println(userre.getName());
        otpReset oR= new otpReset();
        oR.setExpirationTime(new Date((System.currentTimeMillis()+70*100000)));
        oR.setOtp(otp);
        oR.setUserr(userre);

        System.out.println(oR.getOtp());
        System.out.println(oR.getExpirationTime());
        System.out.println(oR.getUserr());
        //to, subject,text
        System.out.println(otp);
        String text ="this is your otp to reset your password"+otp;

/*        if(userre==null){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email not FOUND");
        }

        emailService.sendSimpleMailMessage(email, "OTP FOR YOUR FORGOTTEN PASSWORD is  :",text);
        emailService.addOtp(oR);
  // return ResponseEntity.ok("Email validation sent successfully for user ");
     //   return ResponseEntity.ok().body("{\"status\": \"success\", \"message\": \"Email validation sent successfully for user\"}");



  //  }*/




public int otpGenerate(){
Random random = new Random();
return random.nextInt(100,999_000);


}






    @GetMapping("/verifyOTP/{otp}/{email}")
    public ResponseEntity<?> verifyOTP(@PathVariable Integer otp, @PathVariable String email) throws Exception {
        user userr = (user) userDetailss.loadUserByUsername(email);

        var x = otprepo.findByOtpAndUserr(otp, userr)
                .orElseThrow(() -> new UsernameNotFoundException("otd provided not valid, "));

        // Si le OTP a expiré, supprimez-le de la base de données et renvoyez une réponse appropriée
        if (x.getExpirationTime().before(Date.from(Instant.now()))) {
            otprepo.deleteById(x.getIdO());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Collections.singletonMap("message", "OTP has been expired"));
        }

        // Retourner la réponse sous forme de JSON
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("message", "OTP validation sent successfully for user"));
    }





    /*@GetMapping ("/verifyOTP/{otp}/{email}")
    public ResponseEntity<?> verifyOTP(@PathVariable Integer  otp,@PathVariable String email) throws Exception {
        user userr=(user) userDetailss.loadUserByUsername(email);


        var x =otprepo.findByOtpAndUserr(otp,userr)
                .orElseThrow(()-> new UsernameNotFoundException("otd provided not valid, "));


//une fois le otp est expiré il est supprimé de l DB
        if (x.getExpirationTime().before(Date.from(Instant.now()))) {

            otprepo.deleteById(x.getIdO());
            return new ResponseEntity<>("OTP has been expired ", HttpStatus.EXPECTATION_FAILED);
        }



     //   var x=;
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("message", "OTP validation sent successfully for user"));

    }

*/

    @PostMapping ("/change-pwd/{email}")
    public ResponseEntity<?> changePassword(@RequestBody String  password, @PathVariable String email) throws Exception {


      //  var userr =repo.findByEmail(mail).get();
   //  userr.setPassword(passwordEncoder.encode(pwd));
        if (password.length() > 5) {

System.out.println(password);
String passwrd =passwordEncoder.encode(password);
            System.out.println(passwrd);
            System.out.println(email);

        repo.updatePassword(email,passwrd);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Collections.singletonMap("message", "Password chnaged successfully for user"));

        }
        else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Collections.singletonMap("error", "password invalid"));

        }
    }






}
