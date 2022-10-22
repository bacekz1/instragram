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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

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
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            if (user.getProfilePicture() == null) {
                user.setProfilePicture("default_profile_picture" + File.separator
                        + "default_profile_picture.jpg");
            }
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(modelMapper.map(user, User.class));
            user.setId(userRepository.findByUsername(user.getUsername()).get().getId());
            return modelMapper.map(user, UserNoPasswordDTO.class);
        } else {
            throw new UserNotCreatedException("Passwords do not match");
        }
    }

    UserOnlyMailAndUsernameDTO loginUser(User user, String password) {
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return modelMapper.map(user, UserOnlyMailAndUsernameDTO.class);
        }
        throw new NoAuthException("Wrong credentials!");
    }

    public UserNoPasswordDTO updateUser(UserUpdateDTO user, int userId) {
        User user1 = getUserById(userId);
        checkPermission(userId, user1);
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
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
    
    public String updateProfilePicture(String uname, MultipartFile file) {
        try {
            User user = userRepository.findByUsername(uname).orElseThrow(() -> new DataNotFoundException("User not found"));
            String ext = Objects.requireNonNull(file.getOriginalFilename()).
                    substring(file.getOriginalFilename().lastIndexOf("."));
            if(!(ext.equals(".jpg") || ext.equals(".png") || ext.equals(".jpeg"))) {
                throw new BadRequestException("Invalid file type");
            }
            String name = "uploads" + File.separator + "pfp" + File.separator + user.getUsername()
                    + File.separator + System.nanoTime() + "." + ext;
            File f = new File(name);
            if(!f.exists()) {
                Files.copy(file.getInputStream(), f.toPath());
            }
            else{
                throw new BadRequestException("The file already exists");
            }
            if(user.getProfilePicture() != null){
                File old = new File(user.getProfilePicture());
                old.delete();
            }
            user.setProfilePicture(name);
            userRepository.save(user);
            return name;
        } catch (IOException e) {
            throw new BadRequestException("Something went wrong");
        }
    }
    //loginUser(n,int id) {


    // check also if ip matches
    //servlet request get remote host req.getSession

    //};

}
