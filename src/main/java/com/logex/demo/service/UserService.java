package com.logex.demo.service;

import com.logex.demo.config.exception.RecordNotFoundException;
import com.logex.demo.config.exception.ServiceException;
import com.logex.demo.dto.AuthenticationRequestDto;
import com.logex.demo.dto.UserDto;
import com.logex.demo.enums.*;
import com.logex.demo.model.Employee;
import com.logex.demo.model.User;
import com.logex.demo.repository.RoleRepository;
import com.logex.demo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private RoleRepository roleRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    private UserRepository userRepository;

    public void saveNupdateUser(UserDto userDto) {
        try {
            User user = new User();
            if(userDto.getId() != null) {
                user = userRepository.findById(userDto.getId()).get();
                if(user==null)
                    throw new RecordNotFoundException("User not found");
                userDto.setPassword(user.getPassword());

            }
            else{
                userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            }
            BeanUtils.copyProperties(userDto,user);
            user.setRole(roleRepository.findByRoleName(UserType.values()[userDto.getRoleId()]));
            user.setEnabled(true);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    public Boolean authenticate(AuthenticationRequestDto authenticationDto) {
        Boolean flag = false;
        try {
            if(authenticationDto != null) {

                User user = findUserByUsername(authenticationDto.getUsername());

                if (bCryptPasswordEncoder.matches(authenticationDto.getPassword(), user.getPassword())) {

                    List<GrantedAuthority> grantedAuthorities = convertList(new ArrayList<String>() {{
                        add(user.getRole().getRoleName().name());
                    }}, role -> new SimpleGrantedAuthority(role));

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            user.getEmail(), null, grantedAuthorities);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    flag = true;
                }
            }
            return flag;
        }
        catch (Exception e){
            return flag;
        }

    }

    public String checkUserAuthenticate(ModelAndView modelAndView) {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            if(!authentication.getPrincipal().toString().equalsIgnoreCase("anonymoususer")) {
                return "redirect:/employee";
                //                modelAndView.addObject("employee",new Employee());
//                modelAndView.addObject("jobStatus", JobStatus.keyValues);
//                modelAndView.addObject("licenseClass", LicenseClass.keyValues);
//                modelAndView.addObject("employeeTypes", EmployeeType.keyValues);
//                modelAndView.addObject("verifiedTypes", VerifiedType.keyValues);
//                modelAndView.setViewName("employee");
            } else{
                return "login.html";
//                modelAndView.setViewName("login");
//                modelAndView.addObject("authenticationRequest",new AuthenticationRequestDto());
            }
//            return modelAndView;
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(),e);
        }

        }


    public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }

    public User findUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }


    public UserDto getUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setAddress(user.getAddress());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findOne(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user!=null){
            userRepository.delete(user);
        }
    }

    public List<User> findUserByRole(){

        return userRepository.findAllByRole_RoleName(UserType.ADMIN);
    }

    public String login(AuthenticationRequestDto authenticationRequestDto, Model model) {
        if(authenticate(authenticationRequestDto)){
                return "redirect:/dashboard";
        }
        else{
            model.addAttribute("loginError", true);
            return "login.html";
        }

    }
}
