package com.satransfert.money;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.modele.Partenaire;
import com.satransfert.money.modele.User;
import com.satransfert.money.repository.CompteRepository;
import com.satransfert.money.repository.PartenaireRepository;
import com.satransfert.money.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.Date;

@SpringBootApplication
public class MoneyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MoneyApplication.class, args);
    }
    @Autowired

    PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartenaireRepository partenaireRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Override
    public void run(String...args)throws Exception{
      //  System.out.println(encoder.encode("passer"));

     /* userRepository.save(new User("binetou ba","binette","binette@gmail.com","passer","774100144","dakar","actif", "ben.jpg"));
        userRepository.save(new User("papa mama","papa555","papaaa@gmail.com","passer","778963214","dakar","actif", "papa.jpg"));
        userRepository.save(new User("faty ali","faty545","fatypa55@gmail.com","passer","778965412","dakar","actif", "faty.jpg"));

   /*    userRepository.findAll().forEach(c->{
            System.out.println(c.getName());
                }
                );*/

     //   partenaireRepository.save(new Partenaire("453458","partenaire5&co","partenaire5","339855244","partenaire5@gmailcom","dakar","actif"));
     /*   partenaireRepository.save(new Partenaire("854225","partenaire2&co","partenaire2","339874563","partenaire2@gmailcom","dakar","actif"));
        partenaireRepository.save(new Partenaire("846252","partenaire3&co","partenaire3","333214569","partenaire3@gmailcom","dakar","actif"));

        partenaireRepository.findAll().forEach(c->{
            System.out.println(c.getRaisonSociale());
                }
                );

*/
    /* Partenaire partenaire=new Partenaire();
     partenaire.setId(1L);
    compteRepository.save(new Compte
            ("23423424",0,partenaire,new Date()));*/
    }

   @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/").allowedOrigins("http://localhost:4200");
            }
        };
    }

}
