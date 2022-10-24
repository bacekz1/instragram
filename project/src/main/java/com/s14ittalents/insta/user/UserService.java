package com.s14ittalents.insta.user;

import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.exception.UserNotCreatedException;
import com.s14ittalents.insta.util.AbstractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.DiscriminatorValue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    UserNoPasswordDTO getUserByUsername(String username) {
        Optional<User> user = Optional.of(userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found")));
        return modelMapper.map(user, UserNoPasswordDTO.class);
    }


    UserNoPasswordDTO createUser(UserRegisterDTO user) {
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
            newUser.setProfilePicture("default_profile_picture" + File.separator
                        + "default_profile_picture.jpg");
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

    UserOnlyMailAndUsernameDTO loginUser(User user, String password) {
        if (checkPasswordMatch(user, password)) {
            return modelMapper.map(user, UserOnlyMailAndUsernameDTO.class);
        }
        throw new NoAuthException("Wrong credentials!");
    }

    @Transactional
    public UserNoPasswordDTO updateUser(UserUpdateDTO user, long userId) {
        
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
        try {
            User user = userRepository.findById(uid)
                    .orElseThrow(() -> new DataNotFoundException("User not found"));
            if(file.isEmpty()) {
                throw new BadRequestException("File is empty");
            }
            String ext = Objects.requireNonNull(file.getOriginalFilename()).
                    substring(file.getOriginalFilename().lastIndexOf("."));
            if(!(ext.equals(".jpg") || ext.equals(".png") || ext.equals(".jpeg"))) {
                throw new BadRequestException("Invalid file type");
            }
            String name = "uploads" + File.separator + "pfp" + File.separator + user.getUsername()
                    + File.separator + System.nanoTime() + ext;
            File f = new File(name);
            if(!f.exists()) {
                Files.copy(file.getInputStream(), f.toPath());
            }
            else{
                throw new BadRequestException("The file already exists");
            }
            if(user.getProfilePicture() != null && !(user.getProfilePicture().equals("default_profile_picture" + File.separator
                    + "default_profile_picture.jpg"))){
                File old = new File(user.getProfilePicture());
                old.delete();
            }
            user.setProfilePicture(name);
            userRepository.save(user);
            return name;
        } catch (IOException e) {
            //response.setStatus(500);
            throw new BadRequestException("Unable to set profile picture at this moment," +
                    " default profile picture will be used");
        }
    }
    

    public String deleteUser(long userId, UserDeleteDTO user) {
        
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
        user.setProfilePicture("default_profile_picture" + File.separator
                + "default_profile_picture.jpg");
        userRepository.save(user);
        return "Default profile picture set";
    }
    
    public String followUser(User userToFollow, long loggedUserId) {
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
    
    //loginUser(n,int id) {


    // check also if ip matches
    //servlet request get remote host req.getSession

    //};
    
}
