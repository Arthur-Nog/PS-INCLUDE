package desafioInclude.projetoBack.security;

import desafioInclude.projetoBack.entity.Admin;
import desafioInclude.projetoBack.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    @Value("${api.security.admin.login}")
    private String login;
    @Value("${api.security.admin.senha}")
    private String senha;


    public AdminSeeder(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String ... args) {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setLogin(login);
            admin.setSenha(passwordEncoder.encode(senha));
            adminRepository.save(admin);
        }
    }
}
