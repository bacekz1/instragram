package com.s14ittalents.insta.user;

import com.s14ittalents.insta.exception.*;
import com.s14ittalents.insta.util.AbstractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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


    UserNoPasswordDTO getUserByUsernameToDTO(String username) {
        Optional<User> user = Optional.of(userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found")));
        return modelMapper.map(user, UserNoPasswordDTO.class);
    }


    UserNoPasswordDTO createUser(UserRegisterDTO user) {
        validateEmail(user.getEmail());
        validateUsername(user.getUsername());
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
        if (Objects.equals(user.getPassword(), user.getConfirmPassword())) {
            User newUser = new User();
            newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            newUser.setUsername(user.getUsername());
            newUser.setEmail(user.getEmail());
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setBio(user.getBio());
            newUser.setDateOfBirth(user.getDateOfBirth());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setProfilePicture(DEFAULT_PROFILE_PICTURE);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(newUser);
            if(user.getProfilePicture() != null) {
                updateProfilePicture(user.getProfilePicture(), getUserId(newUser.getUsername()));
            }
            return modelMapper.map(newUser, UserNoPasswordDTO.class);
        } else {
            throw new UserNotCreatedException("Passwords do not match");
        }
    }

    UserOnlyMailAndUsernameDTO loginUser(UserLoginDTO user1) {
        if(user1.getUsername().indexOf('@') != -1) {
            validateEmail(user1.getUsername());
        } else {
            validateUsername(user1.getUsername());
        }
        validatePassword(user1.getPassword());
        checkIfUserExists(user1.getUsername());
        User user= getUserByUsername(user1.getUsername());
        if(user.isBanned()) {
            throw new BadRequestException("You are banned");
        }


        if (checkPasswordMatch(user, user1.getPassword())) {
            return modelMapper.map(user, UserOnlyMailAndUsernameDTO.class);
        }
        throw new NoAuthException("Wrong credentials!");
    }

    @Transactional
    public UserNoPasswordDTO updateUser(UserUpdateDTO user, long userId) {
        
        validateEmail(user.getEmail());
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        User user1 = getUserById(userId);
        checkPermission(userId, user1);
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("This email is already in use");
        }
        user1.setEmail(user.getEmail());
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("This username is already taken");
        }
        user1.setUsername(user.getUsername());
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
        return modelMapper.map(user1, UserNoPasswordDTO.class);

    }

    public User encodePassword(User user, String password) {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }

    public boolean checkPasswordMatch(User user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }
    
    public String updateProfilePicture(MultipartFile file, long uid) {
        if(file.getSize()>mb*5){
            throw new BadRequestException("File size is too big, must be below 5MB");
        }
        try {
            User user = getUserById(uid);
            if(file.isEmpty()) {
                throw new BadRequestException("File is empty");
            }
            String ext = Objects.requireNonNull(file.getOriginalFilename()).
                    substring(file.getOriginalFilename().lastIndexOf("."));
            if(!(ext.equals(".jpg") || ext.equals(".png") || ext.equals(".jpeg"))) {
                throw new BadRequestException("Invalid file type");
            }
            
            String dirName = PATH_TO_STATIC + "pfp";
            String fileName = dirName + File.separator + System.nanoTime() + "-" + uid + "-" + ext;
            File dir = new File(dirName);
            File f = new File(fileName);
            dir.mkdirs();
            if(!f.exists()) {
                Files.copy(file.getInputStream(), f.toPath());
            }
            else{
                throw new FileException("The file already exists");
            }
            if(user.getProfilePicture() != null && !(user.getProfilePicture().equals(DEFAULT_PROFILE_PICTURE))){
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
    

    public String deleteUser(long userId, UserDeleteDTO user) {
    
        validatePassword(user.getPassword());
        validatePassword(user.getConfirmPassword());
        User userToDelete = getUserById(userId);
        String password = user.getPassword();
        System.out.println(password);
        String passwordConfirm = user.getConfirmPassword();
        System.out.println(passwordConfirm);
        if(!password.equals(passwordConfirm)) {
            throw new BadRequestException("Passwords do not match");
        }
        if(!(checkPasswordMatch(userToDelete, user.getPassword()))) {
            throw new BadRequestException("Wrong password");
        }
        //checkPermission(userId, userToDelete);
        userToDelete.setDeleted(true);
        String replaceInDeleted = String.valueOf((userToDelete.getId()));
        userToDelete.setUsername(replaceInDeleted);
        userToDelete.setEmail(replaceInDeleted+"-"+LocalDateTime.now());
        userToDelete.setFirstName(replaceInDeleted);
        userToDelete.setLastName(replaceInDeleted);
        userToDelete.setPassword(replaceInDeleted);
        userToDelete.setGender(replaceInDeleted);
        userToDelete.setProfilePicture(replaceInDeleted);
        userToDelete.setPhoneNum(replaceInDeleted);
        userToDelete.setBio(replaceInDeleted);
        userToDelete.setDateOfBirth(null);
        userToDelete.setVerified(false);
        userRepository.save(userToDelete);
        return "User deleted";
    }
    
    public String setDefaultProfilePicture(long loggedUserId) {
        User user = getUserById(loggedUserId);
        checkPermission(loggedUserId, user);
        user.setProfilePicture(DEFAULT_PROFILE_PICTURE);
        userRepository.save(user);
        return "Default profile picture set";
    }
    
    protected Optional<User> checkIfUserExists(String usermame) {
        return Optional.of(userRepository.findByUsername(usermame)
                .orElseGet(() -> userRepository.findByEmail(usermame)
                        .orElseThrow(() -> new DataNotFoundException("User not found"))));
    }
    
    public String followUser(String username, long loggedUserId) {
        User userToFollow = userRepository.findByUsername(username.toLowerCase().trim())
                .orElseThrow(() -> new DataNotFoundException("Follow unsuccessful, user not found"));
        if(userToFollow.getId() == loggedUserId) {
            throw new BadRequestException("You cannot follow yourself");
        }
        User user = getUserById(loggedUserId);
        if(userToFollow.getId() == loggedUserId) {
            throw new BadRequestException("You cannot follow yourself");
        }
        if(user.getFollowing().contains(userToFollow)) {
            user.getFollowing().remove(userToFollow);
            userRepository.save(user);
            return "User "+ userToFollow.getUsername() +" unfollowed";
        }else {
            user.getFollowing().add(userToFollow);
            userRepository.save(user);
            return "User "+ userToFollow.getUsername() +" followed";
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
    
    public String banUser(long loggedUserId, String username) {
        User user = getUserById(loggedUserId);
        if(user.getId() == loggedUserId) {
            throw new BadRequestException("You cannot ban yourself");
        }
        if(!user.getEmail().split("@")[1].equals("admin.instagram.com")) {
            throw new BadRequestException("You do not have permission to ban users");
        }
        User userToBan = getUserByUsername(username);
        if(user.isBanned()){
            user.setBanned(false);
            userRepository.save(userToBan);
            return "User's "+ username +" ban has been lifted";
        }else {
            user.setBanned(true);
            userRepository.save(userToBan);
            return "User "+ username +" has been banned";
        }

    }
    
    public long getIdFromMailUsernameDTO(UserOnlyMailAndUsernameDTO user1) {
        return getUserByUsername(user1.getUsername()).getId();
    }
}
