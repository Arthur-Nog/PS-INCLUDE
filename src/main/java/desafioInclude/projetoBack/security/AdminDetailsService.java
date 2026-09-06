package desafioInclude.projetoBack.security;

import desafioInclude.projetoBack.entity.Admin;
import desafioInclude.projetoBack.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Admin não encontrado"));
        return User.withUsername(admin.getLogin())
                .password(admin.getSenha())
                .authorities("ROLE_ADMIN")
                .build();
    }
}
