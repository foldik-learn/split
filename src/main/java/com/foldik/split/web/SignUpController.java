package com.foldik.split.web;

import com.foldik.split.persistence.UserRepository;
import com.foldik.split.persistence.model.Role;
import com.foldik.split.persistence.model.User;
import com.foldik.split.web.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignUpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public SignUpController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String index() {
        return "signUp";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@ModelAttribute UserDTO userDTO) {
        LOGGER.info("{}", userDTO);
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return "redirect:/home";
    }
}
