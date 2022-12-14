package com.s14ittalents.insta.user;

import com.s14ittalents.insta.comment.CommentService;
import com.s14ittalents.insta.exception.*;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.PostService;
import com.s14ittalents.insta.story.StoryService;
import com.s14ittalents.insta.user.dto.*;
import com.s14ittalents.insta.util.AbstractService;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.s14ittalents.insta.exception.Constant.*;

@Service
public class UserService extends AbstractService {
    
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    PostService postService;
    @Autowired
    StoryService storyService;
    @Autowired
    CommentService commentService;
    @Autowired
    private JavaMailSender mailSender;
    


    UserNoPasswordDTO getUserByUsernameToDTO(String username) {
        User user = getUserByUsername(username);
        checkPermission(user);
        return modelMapper.map(user, UserNoPasswordDTO.class);
    }
    UserNoPasswordDTO getUserByIdToDTO(long id) {
        User user = getUserById(id);
        checkPermission(user);
        return modelMapper.map(user, UserNoPasswordDTO.class);
    }


    @Transactional
    UserNoPasswordDTO createUser(UserRegisterDTO user, String siteURL) {
        validatePassword(user.getPassword());
        validatePassword(user.getConfirmPassword());

        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new UserNotCreatedException("Username already exists");
                });
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new UserNotCreatedException("Email already exists");
                });
            User newUser = new User();
            checkIfPasswordAndConfirmPasswordMatch(user.getPassword().trim(), user.getConfirmPassword().trim());
            newUser.setPassword(encodePassword(user.getPassword().trim()));
            newUser.setUsername(validateUsername(user.getUsername()));
            newUser.setEmail(validateEmail(user.getEmail()));
            if(user.getBio() != null) {
                newUser.setBio(validateBio(user.getBio()));
            }
            newUser.setDateOfBirth(validateDateOfBirth(user.getDateOfBirth()));
            newUser.setFirstName(validateName(user.getFirstName(), "First"));
            newUser.setLastName(validateName(user.getLastName(), "Last"));
            newUser.setGender(validateGender(user.getGender()));
            newUser.setPhoneNum(validatePhoneNum(user.getPhoneNum()));
            newUser.setProfilePicture(DEFAULT_PROFILE_PICTURE);
            
            String randomCode = RandomString.make(64);
            newUser.setVerificationCode(randomCode);
            newUser.setVerified(false);
            sendVerificationEmail(newUser, siteURL);
            newUser.setCreatedAt(LocalDateTime.now());
            User userToCheckProfilePicture = userRepository.save(newUser);
            if (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()) {
                updateProfilePicture(user.getProfilePicture(), getUserId(userToCheckProfilePicture.getUsername()));
            }
            return modelMapper.map(newUser, UserNoPasswordDTO.class);
    }
    
    @Transactional
    String fixForgottenPassword(UserOnlyMailAndUsernameDTO user, String siteURL) {
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        User userToFix = userRepository.findByUsername(user.getUsername())
                .orElseGet(() -> userRepository.findByEmail(user.getEmail())
                        .orElseThrow(() -> new UserNotCreatedException(USER_NOT_FOUND)));
            sendForgottenPasswordEmail(userToFix, siteURL);
            userRepository.save(userToFix);
            return "Please check your email and use the code to change your password";
    }
    
    
    void sendVerificationEmail(User user, String siteURL) {
        new Thread(() -> {
            try {
                String toAddress = user.getEmail();
                String fromAddress = "itTalentsInstagramProject@gmail.com";
                String senderName = "ItTalents Project";
                String subject = "Please verify your registration";
                String content = "Dear [[name]],<br>" + "Please click the link below to verify your " +
                        "registration:<br>" + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank " +
                        "you,<br>" + "-ItTalents Team.";
                
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                
                helper.setFrom(fromAddress, senderName);
                helper.setTo(toAddress);
                helper.setSubject(subject);
                
                content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
                String verifyURL = siteURL + "/users/verify?code=" + user.getVerificationCode();
                
                content = content.replace("[[URL]]", verifyURL);
                
                helper.setText(content, true);
                
                mailSender.send(message);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new UserNotCreatedException(ERROR_WHILE_SENDING_VERIFICATION_EMAIL);
            }
        }).start();
    }
    
    void sendForgottenPasswordEmail(User user, String siteURL) {
        new Thread(() -> {
            try {
                String toAddress = user.getEmail();
                String fromAddress = "itTalentsInstagramProject@gmail.com";
                String senderName = "ItTalents Project";
                String subject = "Forgotten Password";
                String content = "Dear [[name]],<br>" + "Please click the link below to verify its' you " + "so you " +
                        "can set your new password:<br>" + "<h3><a href=\"[[URL]]\" target=\"_self\">GET " +
                        "CODE</a></h3>" + "Thank you,<br>" + "-ItTalents Team.";
                
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                
                helper.setFrom(fromAddress, senderName);
                helper.setTo(toAddress);
                helper.setSubject(subject);
                
                content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
                String forgottenPassword = siteURL + "/users/passwordRecovery?code=" + user.getVerificationCode();
                
                content = content.replace("[[URL]]", forgottenPassword);
                
                helper.setText(content, true);
                
                mailSender.send(message);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new UserNotCreatedException("Error while sending forgotten password email");
            }
        }).start();
    }
    
    public boolean verify(String verificationCode){
        Optional<User> user = userRepository.getUserByVerificationCode(verificationCode);
        
        if(user.isPresent()){
            user.get().setVerified(true);
            userRepository.save(user.get());
            return true;
        } else {
            throw new UserNotCreatedException("Invalid code");
        }
    }
    public boolean setNewPasswordForForgottenPassword(String verificationCode,UserPasswordResetDTO userNewPassword){
        Optional<User> user = userRepository.getUserByVerificationCode(verificationCode);
        
        if(user.isPresent()){
            if (userNewPassword.getPassword() == null || userNewPassword.getConfirmPassword() == null
                    || userNewPassword.getPassword().isBlank() || userNewPassword.getConfirmPassword().isBlank()) {
                throw new BadRequestException("You must enter your old password to confirm password change" +
                        " and enter new password and confirm it, if you do not want to change your password," +
                        " leave the fields empty");
            }
            validatePassword(userNewPassword.getPassword());
            validatePassword(userNewPassword.getConfirmPassword());
            checkIfPasswordAndConfirmPasswordMatch(userNewPassword.getPassword(), userNewPassword.getConfirmPassword());
            user.get().setPassword(encodePassword(userNewPassword.getPassword()));
            userRepository.save(user.get());
            return true;
        } else {
            throw new UserNotCreatedException("Invalid code");
        }
    }

    UserOnlyMailAndUsernameDTO loginUser(UserLoginDTO user1) {
        if (user1.getUsername().indexOf('@') != -1) {
            validateEmail(user1.getUsername());
        } else {
            validateUsername(user1.getUsername());
        }
        validatePassword(user1.getPassword());
        User user = checkIfUserExistsLogin(user1.getUsername());
        if(!user.isVerified()){
            throw new NoAuthException("You have not verified your account, please check your email");
        }
        if (user.isBanned()) {
            throw new NoAuthException("You are banned");
        }
        user.setDeactivated(false);
        if (checkPasswordMatch(user, user1.getPassword())) {
            return modelMapper.map(user, UserOnlyMailAndUsernameDTO.class);
        }else {
            throw new NoAuthException("Wrong credentials!");
        }
    }

    @Transactional
    public UserNoPasswordDTO updateUserCredentials(UserUpdateDTO user, long userId) {
        User user1 = getUserById(userId);
        checkPermission(user1);
        if (!checkPasswordMatch(user1, user.getPassword())) {
            throw new NoAuthException("Wrong password, enter correct password to confirm changes");
        }
        if (user.getNewPassword() == null || user.getConfirmPassword() == null
                || user.getNewPassword().isBlank() || user.getConfirmPassword().isBlank()) {
            throw new BadRequestException("You must enter your old password to confirm password change" +
                    " and enter new password and confirm it, if you do not want to change your password," +
                    " leave the fields empty");
        }
        if(user.getEmail() != null){
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new BadRequestException("This email is already in use");
            }
            user1.setEmail(validateEmail(user.getEmail()));
        }
        if(user.getUsername() != null){
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new BadRequestException("This username is already taken");
            }
            user1.setUsername(validateUsername(user.getUsername()));
        }
        UserChangePasswordDTO userChangePasswordDTO = modelMapper.map(user, UserChangePasswordDTO.class);
        userChangePasswordDTO.setOldPassword(user.getPassword());
        changePassword(userChangePasswordDTO,userId);
        userRepository.save(user1);
        return modelMapper.map(user1, UserNoPasswordDTO.class);
    }
    
    

    public User encodePassword(User user, String password) {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }
    
    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
    
    @Transactional
    public UserNoPasswordDTO editUserInfo(UserEditDTO user, long userId) {
        User user1 = getUserById(userId);
        checkPermission(getUserById(userId), user1);
        if (user.getFirstName() != null && !user.getFirstName().isBlank()) {
            user1.setFirstName(validateName(user.getFirstName(), "First"));
        }
        if (user.getLastName() != null && !user.getLastName().isBlank()) {
            user1.setLastName(validateName(user.getLastName(), "Last"));
        }
        if (user.getBio() != null && !user.getBio().isBlank()) {
            user1.setBio(validateBio(user.getBio()));
        }
        if (user.getDateOfBirth() != null) {
            user1.setDateOfBirth(validateDateOfBirth(user.getDateOfBirth()));
        }
        if (user.getProfilePicture() != null) {
            updateProfilePicture(user.getProfilePicture(), userId);
        }
        if (user.getGender() != null && !user.getGender().isBlank()) {
            user1.setGender(validateGender(user.getGender()));
        }
        if (user.getPhoneNum() != null && !user.getPhoneNum().isBlank()) {
            user1.setPhoneNum(validatePhoneNum(user.getPhoneNum()));
        }
        updateProfilePicture(user.getProfilePicture(), userId);
        userRepository.save(user1);
        return modelMapper.map(user1, UserNoPasswordDTO.class);
    }
    
    
    public String changePassword(UserChangePasswordDTO user, long userId) {
        if (user.getNewPassword() == null || user.getConfirmPassword() == null || user.getOldPassword() == null) {
            throw new NoAuthException("All fields are required");
        }
        User user1 = getUserById(userId);
        checkPermission(getUserById(userId), user1);
        if (checkPasswordMatch(user1, user.getOldPassword().trim())) {
            if (user.getOldPassword().equals(user.getNewPassword())) {
                throw new NoAuthException("New password cannot be the same as the old one");
            }
            validatePassword(user.getNewPassword());
            checkIfPasswordAndConfirmPasswordMatch(user.getNewPassword(), user.getConfirmPassword());
            user1.setPassword(bCryptPasswordEncoder.encode(user.getNewPassword()));
            userRepository.save(user1);
            return "Password changed successfully";
        }
        throw new NoAuthException("Wrong password");
    }


    private void checkIfPasswordAndConfirmPasswordMatch(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword)) {
            throw new NoAuthException("Passwords do not match");
        }
    }

    public boolean checkPasswordMatch(User user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    public String updateProfilePicture(MultipartFile file, long uid) {
        if (file.getSize() > mb * 5) {
            throw new BadRequestException("File size is too big, must be below 5MB");
        }
        try {
            User user = getUserById(uid);
            if (file.isEmpty()) {
                throw new BadRequestException("File is empty");
            }
            String ext = Objects.requireNonNull(file.getOriginalFilename()).
                    substring(file.getOriginalFilename().lastIndexOf("."));
            if (!(ext.equals(".jpg") || ext.equals(".png") || ext.equals(".jpeg"))) {
                throw new BadRequestException("Invalid file type");
            }

            String dirName = PATH_TO_STATIC + "pfp";
            String fileName = dirName + File.separator + System.nanoTime() + "-" + uid + "-" + ext;
            File dir = new File(dirName);
            File f = new File(fileName);
            dir.mkdirs();
            if (!f.exists()) {
                Files.copy(file.getInputStream(), f.toPath());
            } else {
                throw new FileException("The file already exists");
            }
            if (user.getProfilePicture() != null && !(user.getProfilePicture().equals(DEFAULT_PROFILE_PICTURE))) {
                File old = new File(user.getProfilePicture());
                old.delete();
            }
            user.setProfilePicture(fileName);
            userRepository.save(user);
            return fileName;
        } catch (IOException e) {
            throw new FileException("Unable to set profile picture at this moment," +
                    " default profile picture will be used");
        }
    }

    @Transactional
    public String deleteUser(long userId, UserDeleteDTO adminAuth, String account, boolean isUsername) {
        User admin = validateIfUserIsAdminByEmail(userId);
    
        User userToDelete = isUsername ? getUserByUsername(account) : getUserByEmail(account);
    
        checkAdminPassword(adminAuth, admin);
        userToDelete.setUsername(REPLACE_IN_DELETED);
        encodePassword(userToDelete,(REPLACE_IN_DELETED + System.nanoTime()));
        userToDelete.setEmail(REPLACE_IN_DELETED);
        userToDelete.setFirstName("");
        userToDelete.setLastName("");
        userToDelete.setGender("");
        userToDelete.setProfilePicture("");
        userToDelete.setPhoneNum("");
        userToDelete.setBio("");
        userToDelete.setDateOfBirth(LocalDate.parse("1900-01-01"));
        
        userToDelete.setVerificationCode(RandomString.make(13));
        userToDelete.getFollowing().clear();
        userToDelete.getFollowers().clear();
        
        
        userToDelete.getPosts().forEach(p-> {
            p.getLikes().clear();
            postRepository.save(p);
        }
        );
        for(Post post : postRepository.findAllByOwner(userToDelete)){
            postService.deletePostCommentsWhenDeletingUser(post);
        }
        userToDelete.setDeleted(true);
        userToDelete.setVerified(false);
        userToDelete.setDeactivated(true);
        userRepository.save(userToDelete);
        return "User "+ account +" deleted";
    }
    
    private void checkAdminPassword(UserDeleteDTO user, User admin) {
        String password = validatePassword(user.getPasswordOfAdmin());
        String passwordConfirm = validatePassword(user.getConfirmPasswordOfAdmin());
        if (!password.equals(passwordConfirm)) {
            throw new BadRequestException("Passwords do not match");
        }
        if (!(checkPasswordMatch(admin, user.getPasswordOfAdmin()))) {
            throw new NoAuthException("Wrong credentials!");
        }
    }
    
    private User getUserByEmail(String account) {
        return userRepository.findByEmail(account)
                .orElseThrow(() -> new NoAuthException("User with email " + account + " not found"));
    }
    
    public String setDefaultProfilePicture(long loggedUserId) {
        User user = getUserById(loggedUserId);
        user.setProfilePicture(DEFAULT_PROFILE_PICTURE);
        userRepository.save(user);
        return "Default profile picture set";
    }
    
    
    protected User checkIfUserExistsLogin(String usermame) {
        return userRepository.findByUsername(usermame)
                .orElseGet(() -> userRepository.findByEmail(usermame)
                        .orElseThrow(() -> new NoAuthException("Wrong credentials!")));
    }
    public String followUser(String username, long loggedUserId) {
        User userToFollow = userRepository.findByUsername(username.toLowerCase().trim())
                .orElseThrow(() -> new DataNotFoundException("Follow unsuccessful, user not found"));
        User user = getUserById(loggedUserId);
        if (userToFollow.getId() == loggedUserId) {
            throw new BadRequestException("You cannot follow yourself");
        }
        if (user.getFollowing().contains(userToFollow)) {
            user.getFollowing().remove(userToFollow);
            userRepository.save(user);
            return "User " + userToFollow.getUsername() + " unfollowed";
        } else {
            user.getFollowing().add(userToFollow);
            userRepository.save(user);
            return "User " + userToFollow.getUsername() + " followed";
        }
    }

    public List<UserOnlyMailAndUsernameDTO> getFollowing(long userId) {
        User user = getUserById(userId);
        List<User> followedUser = user.getFollowing();
        return followedUser.stream().map(u -> modelMapper.map(u, UserOnlyMailAndUsernameDTO.class))
                .collect(Collectors.toList());
    }

    public List<UserOnlyMailAndUsernameDTO> getFollowers(long userId) {
        User user = getUserById(userId);
        List<User> followers = user.getFollowers();
        return followers.stream().map(u -> modelMapper.map(u, UserOnlyMailAndUsernameDTO.class))
                .collect(Collectors.toList());
    }

    public String banUser(long loggedUserId, String username, Long id) {
        validateIfUserIsAdminByEmail(loggedUserId);
        User userToBan;
        if (id != null) {
            userToBan = getUserById(id);
        } else {
            userToBan = getUserByUsername(username);
        }
        if (userToBan.getId() == loggedUserId) {
            throw new BadRequestException("You cannot ban yourself");
        }
        userToBan.setBanned(!userToBan.isBanned());
        userRepository.save(userToBan);
        return userToBan.isBanned() ? "User " + userToBan.getUsername() + " has been banned"
                : "User's " + userToBan.getUsername() + " ban has been lifted";
    }

    public long getIdFromMailUsernameDTO(UserOnlyMailAndUsernameDTO user1) {
        return getUserByUsername(user1.getUsername()).getId();
    }
    
    public String deactivateUser(long userId, UserDeactivateDTO user) {
        User userToDeactivate = getUserById(userId);
        String password = validatePassword(user.getPassword());
        String passwordConfirm = validatePassword(user.getConfirmPassword());
        if (!password.equals(passwordConfirm)) {
            throw new BadRequestException("Passwords do not match");
        }
        if (!(checkPasswordMatch(userToDeactivate, user.getPassword()))) {
            throw new BadRequestException("Wrong password");
        }
        userToDeactivate.setDeactivated(true);
        userRepository.save(userToDeactivate);
        return "User " + userToDeactivate.getUsername() + " deactivated";
    }
    
}
