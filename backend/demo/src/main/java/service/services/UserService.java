package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import service.entity.User;
import service.exception.UserException;
import service.repository.UserRepository;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public void createUser(User newUser){
        userRepository.save(newUser);
    }
    public Boolean existByUsername(String username)
    {
        User user = userRepository.findByUsername(username);
        if(user!=null){
            return true;
        }
        return false;
    }
    public Boolean existByEmail(String email)
    {
        User user = userRepository.findByEmail(email);
        if(user!=null){
            return true;
        }
        return false;
    }
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserDetails user = (UserDetails) userRepository.findByUsername(username);
        if(user ==null){
            throw  new UsernameNotFoundException(username +" not found");
        }
        return user;
    }

}