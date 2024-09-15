package tn.esprit.esprobackend.services;

import dev.samstevens.totp.code.*;

import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.exceptions.TimeProviderException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import dev.samstevens.totp.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class twoFactorAuthService {

/*
public String generateNewSecret(){
//gener un code secret grace au dependance
    return new DefaultSecretGenerator().generate();
}


    public String QrCodeImageURi(String secret){

   QrData data = new QrData.Builder()
        .label("espro")
        .secret(secret)
        .issuer("coding")
        .algorithm(HashingAlgorithm.SHA1)
        .digits(6)
        .period(250)
        .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData =new byte[0];
        try {
            imageData=generator.generate(data);
        }
        catch (QrGenerationException e)
        {
            log.error("ereur de generation de qr code");
        }

        return Utils.getDataUriForImage(imageData,generator.getImageMimeType());
    }




    public boolean isOtpValid(String secret, String code) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return verifier.isValidCode(secret, code);
    }

    public boolean isOtpNotValid(String secret, String code) {
        return !this.isOtpValid(secret, code);
    }
*/




    private static final int QR_CODE_SIZE = 250; // Default QR code size
    private static final int QR_CODE_MARGIN = 4; // Default QR code margin

    public String generateNewSecret() {
        return new DefaultSecretGenerator().generate();
    }

    public String QrCodeImageURi(String secret) {
        try {
            QrData data = new QrData.Builder()
                    .label("espro")
                    .secret(secret)
                    .issuer("espro")
                    .algorithm(HashingAlgorithm.SHA1)
                    .digits(6)
                    .period(30) // 30-second period for TOTP codes
                    .build();

            QrGenerator generator = new ZxingPngQrGenerator();
            byte[] imageData = generator.generate(data);
            return Utils.getDataUriForImage(imageData, generator.getImageMimeType());
        } catch (QrGenerationException e) {
            log.error("Failed to generate QR code for secret: {}", secret, e);
            return "failed";
        }
    }

    public boolean isOtpValid(String secret, String code) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return verifier.isValidCode(secret, code);
    }

    public boolean isOtpNotValid(String secret, String code) {
        return !this.isOtpValid(secret, code);
    }





}
