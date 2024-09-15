package tn.esprit.esprobackend.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.esprobackend.entities.user;
import tn.esprit.esprobackend.repositories.userRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {
    private final userRepo userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      /*  Optional<user> user = userRepository.findByEmail(username);
        user.orElseThrow(()->new UsernameNotFoundException("user not found)"));

        return new user();*/
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

    }
}
