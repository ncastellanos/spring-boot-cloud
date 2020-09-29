package academy.digitallab.store.security.domain.service;

import academy.digitallab.store.security.domain.repository.entity.Authority;
import academy.digitallab.store.security.domain.repository.entity.User;
import academy.digitallab.store.security.domain.repository.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository  userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById( username).orElse(null);

        List<GrantedAuthority> authorities =buildAuhorities(user.getAuthorities());

        return buildUser(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUser(User user , List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildAuhorities(List<Authority> authorities){
        Set<GrantedAuthority> auths = new HashSet<>();
        for(Authority authority: authorities){
            auths.add(new SimpleGrantedAuthority(authority.getRole()));
        }
        return new ArrayList<GrantedAuthority>(auths);
    }
}
