package com.yallahnsafro.yallahnsafrobackend.services.implimentation;
import com.yallahnsafro.yallahnsafrobackend.controllers.UserController;
import com.yallahnsafro.yallahnsafrobackend.entities.UserEntity;
import com.yallahnsafro.yallahnsafrobackend.repositories.UserRepository;
import com.yallahnsafro.yallahnsafrobackend.security.SecurityConstants;
import com.yallahnsafro.yallahnsafrobackend.services.EmailSenderService;
import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.Utils;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getPassword(),new ArrayList<>());
    }
    @Value("${url}")
    private String url;
    @Value("${login.token.expiry_minutes}")
    private int restPassword_expiring_min;
    @Value("${verify.email.token.expiry_minutes}")
    private int verify_email_expiring_min;
    @Value("${app.email}")
    private String appEmail;
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils util;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailSenderService emailSender;





    @Override
    public UserDto registerUser(UserDto userDto) {
        //check if user data exists by email
        UserEntity checkUser = userRepository.findByEmail(userDto.getEmail());
        if (checkUser != null){
            throw new RuntimeException("email already exists");
        }
        //Generate userID, we have created a class utils and defined a method generate
        userDto.setUserId(util.generateUserId(32));
        //enabled
        userDto.setEnabled(true);
        userDto.setLocked(false);
        //Encrypt password
        String encryptedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);
        //end of encryption
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);

        //generate Confirmation Token
        String verificationToken = Jwts.builder()
                .setSubject(userEntity.getEmail())
                .setExpiration(DateUtils.addMinutes(new Date(), verify_email_expiring_min))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();
        //save confirmation token
        userEntity.setVerification_token(verificationToken);
        userEntity.setEmail_verification_status(false);

        UserEntity newUserEntity = userRepository.save(userEntity);

        // TODO: send
        String verificationLink = url + "verifyEmail?verificationToken=" + verificationToken;

        String emailVerificationHtmlMsg = "<!-- In Container -->\n"
                + "<table class=\"in_container\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n"
                + "  <tr>\n"
                + "    <td>\n"
                + "      <!-- About  -->\n"
                + "      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" bgcolor=\"ffffff\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#ffffff;\">\n"
                + "        <tr><td height=\"65px\" width=\"100%\" style=\"height:65px;\"></td></tr>\n"
                + "        <tr>\n"
                + "          <td align=\"center\">\n"
                + "            <table class=\"full_width_600\" width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;text-align:center;\">\n"
                + "              <tr>\n"
                + "                <td>\n"
                + "                  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;\">\n"
                + "                    <tr>\n"
                + "                      <td align=\"left\" style=\" text-align:left; color: #676f84; font-family: 'Open Sans', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 600; line-height:30px;\"> <span style=\"color:black;\">Hello</span> " + newUserEntity.getFirstname() + " " + newUserEntity.getLastname() + ",</td>\n"
                + "                    </tr>\n"
                + "                  </table>\n"
                + "                </td>\n"
                + "              </tr>\n"
                + "              <tr><td height=\"15px\" width=\"100%\" style=\"height:15px;\"></td></tr>\n"
                + "              <tr>\n"
                + "                <td>\n"
                + "                  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 600px; margin:0 auto;border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;\">\n"
                + "                    <tr>\n"
                + "                      <td style=\"text-align:left; color: black; font-family: 'Open Sans', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 600; line-height: 20px;\">\n"
                + "Your email address has been successfully registered. Please click the following link to verify your email:\n"
                + "<br>\n"
                + "<div style=\"text-align:center; background-color: #00bcd4; color: white;padding: 15px 25px; text-align: center; text-decoration: none; display: inline-block;\">\n"
                + "  <a style=\"color:white;\" href=" + verificationLink + ">Verify Email</a>\n"
                + "</div>\n"
                + "<br>\n"
                + "Thank you for using our service!\n"
                + "                      </td>\n"
                + "                    </tr>\n"
                + "                  </table>\n"
                + "                </td>\n"
                + "              </tr>\n"
                + "            </table>\n"
                + "          </td>\n"
                + "        </tr>\n"
                + "        <tr><td height=\"65px\" width=\"100%\" style=\"height:65px;\"></td></tr>\n"
                + "      </table>\n"
                + "      <!-- End About  -->\n"
                + "\n"
                + "      <!-- Bottom -->\n"
                + "      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\" align=\"center\" background=\"http://ryanhallmedia.com/thirdspace/img/email/lastback.jpg\" bgcolor=\"3d424e\" style=\"background-image:url('img/lastback.jpg'); background-size: cover; -webkit-background-size: cover; -moz-background-size: cover -o-background-size: cover; background-position: bottom center; background-repeat: no-repeat; background-color: #00bcd4; border-radius: 0px 0px 2px 2px;\">\n"
                + "        <tr>\n"
                + "          <td>\n"
                + "            <!--  Caption  -->\n"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n"
                + "              <tr>\n"
                + "                <td>\n"
                + "                  <table width=\"100%\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;\">\n"
                + "                    <tr>\n"
                + "                      <td>\n"
                + "                        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;\">\n"
                + "                          <tr>\n"
                + "                            <td align=\"left\" style=\" text-align:left; color: #fff; font-family: 'Open Sans', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 700;line-height:24px;letter-spacing:0.5px;\"> Best regards,</td>\n"
                + "                          </tr>\n"
                + "                        </table>\n"
                + "                      </td>\n"
                + "                    </tr>\n"
                + "                  </table>\n"
                + "                </td>\n"
                + "              </tr>\n"
                + "\n"
                + "            </table>\n"
                + "          </td>\n"
                + "        </tr>\n"
                + "      </table>\n"
                + "      <!-- Bottom -->\n"
                + "    </td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<!-- End In Container -->";

        try {
            emailSender.send(newUserEntity.getEmail(),"Verification email YallahNssafro.ma",appEmail,emailVerificationHtmlMsg);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        UserDto newUserDto = new UserDto();
        BeanUtils.copyProperties(newUserEntity, newUserDto);
        return (newUserDto);
    }



    @Override
    public boolean verifyEmail(String verificationToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.TOKEN_SECRET)
                .parseClaimsJws(verificationToken)
                .getBody();

        String email = claims.getSubject();
        UserEntity userToVerify = userRepository.findByEmail(email);
        userToVerify.setEmail_verification_status(true);
        userToVerify.setVerification_token(null);
        userRepository.save(userToVerify);
        return true;
    }




    @Override
    public UserDto getUserById(long userId) throws UsernameNotFoundException {
            UserEntity foundUserEntity = new UserEntity();
            foundUserEntity = userRepository.findById(userId).orElse(null);
            UserDto foundUserDto = new UserDto();
            if (foundUserEntity == null) {
                throw new UsernameNotFoundException ("notfound");
            }
            BeanUtils.copyProperties(foundUserEntity, foundUserDto);
            return foundUserDto;
    }

    @Override
    public UserDto updateUser(UserDto userDtoUpdated) {
        UserDto userDtoAfterUpdate = new UserDto();
        UserEntity userEntityUpdate = new UserEntity();
        UserEntity userEntityAfterUpdate = new UserEntity();

        userEntityUpdate = userRepository.findById(userDtoUpdated.getId()).orElse(null);
        long id = userDtoUpdated.getId();
        if (userEntityUpdate != null) {
            BeanUtils.copyProperties(userDtoUpdated, userEntityUpdate);
            userEntityAfterUpdate = userEntityUpdate;
            BeanUtils.copyProperties(userEntityAfterUpdate, userDtoAfterUpdate);
            return (userDtoAfterUpdate);
        }

        return null;
    }

    @Override
    public boolean deleteUser(String userId) {
        UserEntity userFound = userRepository.findByUserId(userId);

        if (userFound!= null){
            userRepository.delete(userFound);
            return true;
        } else throw new UsernameNotFoundException(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        UserDto temp = new UserDto();
        List<UserEntity> allUsersEntity = (List<UserEntity>) userRepository.findAll();
        List<UserDto> allUsersDto = new ArrayList<>();
        for (UserEntity userEntity : allUsersEntity){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            allUsersDto.add(userDto);
        }
        return (allUsersDto);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity,userDto);

        return userDto;
    }

    public UserDto getUserForLogin(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null)
            throw new UsernameNotFoundException(email);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity,userDto);

        return userDto;
    }



    @Override
    public boolean forgotPassword(String email) {
        UserEntity userExists = userRepository.findByEmail(email);
        if (userExists != null){
            String verificationToken = Jwts.builder()
                    .setSubject(email)
                    .setExpiration(DateUtils.addMinutes(new Date(), restPassword_expiring_min))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                    .compact();

            userExists.setVerification_token(verificationToken);
            userRepository.save(userExists);

            String passwordResetLink = url + "newPassword?verificationToken=" + verificationToken;

            String htmlMsg = "      <!-- In Container -->\n"
                    + "      <table class=\"in_container\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n"
                    + "        <tr>\n"
                    + "          <td>\n"
                    + "            <!-- About  -->\n"
                    + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" bgcolor=\"ffffff\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#ffffff;\">\n"
                    + "              <tr><td height=\"65px\" width=\"100%\" style=\"height:65px;\"></td></tr>\n"
                    + "              <tr>\n"
                    + "                <td align=\"center\">\n"
                    + "                  <table class=\"full_width_600\" width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;text-align:center;\">\n"
                    + "                    <tr>\n"
                    + "                      <td>\n"
                    + "                        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;\">\n"
                    + "                          <tr>\n"
                    + "                            <td align=\"left\" style=\" text-align:left; color: #676f84; font-family: 'Open Sans', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 600; line-height:30px;\"> <span style=\"color:black;\">Bonjour</span> " + userExists.getFirstname() + " " + userExists.getLastname() + ",</td>\n"
                    + "                          </tr>\n"
                    + "                        </table>\n"
                    + "                      </td>\n"
                    + "                    </tr>\n"
                    + "                    <tr><td height=\"15px\" width=\"100%\" style=\"height:15px;\"></td></tr>\n"
                    + "                    <tr>\n"
                    + "                      <td>\n"
                    + "                        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 600px; margin:0 auto;border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;\">\n"
                    + "                          <tr>\n"
                    + "                            <td style=\"text-align:left; color: black; font-family: 'Open Sans', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 600; line-height: 20px;\">\n"
                    + "Une réinitialisation du mot de passe de votre compte a été demandée. Cliquez sur le lien ci-dessous pour modifier votre mot de passe.\n"
                    + "															<br>\n"
                    + " <div style=\"text-align:center; background-color: #00bcd4; color: white;padding: 15px 25px; text-align: center; text-decoration: none; display: inline-block;\" > <a style=\"color:white;\"  href=" + passwordResetLink + ">Changer votre mot de passe</a> </div> \n"
                    + "															<br>\n"
                    + "															<br>\n"
                    + "Remarque : ce lien est valable pendant " + restPassword_expiring_min + "min. Après expiration de ce délai, vous devrez soumettre une nouvelle demande de réinitialisation de mot de passe.\n"
                    + "                            </td>\n"
                    + "                          </tr>\n"
                    + "                        </table>\n"
                    + "                      </td>\n"
                    + "                    </tr>\n"
                    + "                  </table>\n"
                    + "                </td>\n"
                    + "              </tr>\n"
                    + "              <tr><td height=\"65px\" width=\"100%\" style=\"height:65px;\"></td></tr>\n"
                    + "            </table>\n"
                    + "            <!-- End About  -->\n"
                    + "\n"
                    + "            <!-- Bottom -->\n"
                    + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\" align=\"center\" background=\"http://ryanhallmedia.com/thirdspace/img/email/lastback.jpg\" bgcolor=\"3d424e\" style=\"background-image:url('img/lastback.jpg'); background-size: cover; -webkit-background-size: cover; -moz-background-size: cover -o-background-size: cover; background-position: bottom center; background-repeat: no-repeat; background-color: #00bcd4; border-radius: 0px 0px 2px 2px;\">\n"
                    + "              <tr>\n"
                    + "                <td>\n"
                    + "                  <!--  Caption  -->\n"
                    + "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n"
                    + "                    <tr>\n"
                    + "                      <td>\n"
                    + "                        <table width=\"100%\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;\">\n"
                    + "                          <tr>\n"
                    + "                            <td>\n"
                    + "                              <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border:0;\">\n"
                    + "                                <tr>\n"
                    + "                                  <td align=\"left\" style=\" text-align:left; color: #fff; font-family: 'Open Sans', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 700;line-height:24px;letter-spacing:0.5px;\"> Cordialement,</td>\n"
                    + "                                </tr>\n"
                    + "                              </table>\n"
                    + "                            </td>\n"
                    + "                          </tr>\n"
                    + "\n"
                    + "                        </table>\n"
                    + "                      </td>\n"
                    + "                    </tr>\n"
                    + "                  </table>\n"
                    + "                  <!--  End Caption  -->\n"
                    + "                </td>\n"
                    + "              </tr>\n"
                    + "            </table>\n"
                    + "            <!-- Bottom -->\n"
                    + "          </td>\n"
                    + "        </tr>\n"
                    + "      </table>\n"
                    + "      <!-- End In Container -->";

            try {
                emailSender.send(userExists.getEmail(),"Password Reset email YallahNssafro.ma",appEmail,htmlMsg);
                return true;
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return false;
    }

    @Override
    public boolean resetPassword(String newPassword, String verificationToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.TOKEN_SECRET)
                .parseClaimsJws(verificationToken)
                .getBody();

        String email = claims.getSubject();
        UserEntity userFound = userRepository.findByEmail(email);

        if (userFound != null){
            userFound.setVerification_token(null);
            userFound.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(userFound);
            return true;
        }



        return false;
    }

    @Override
    public boolean disableUser(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null){
            user.setEnabled(false);
            user.setLocked(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean enableUser(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null){
            user.setEnabled(true);
            user.setLocked(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }


}
