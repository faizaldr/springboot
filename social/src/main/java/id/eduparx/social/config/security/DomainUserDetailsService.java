package id.eduparx.social.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import id.eduparx.social.repository.UserRepository;


public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository repo;

    public DomainUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByEmail(username).map(DomainUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " Tidak Ditemukan"));
    }

}
