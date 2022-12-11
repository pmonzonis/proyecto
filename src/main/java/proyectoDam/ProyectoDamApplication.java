package proyectoDam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class ProyectoDamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoDamApplication.class, args);
	}
        
        @EnableWebSecurity
        public class SecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/**").permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable();
	}
}

        
        
        
        @Bean
        public BCryptPasswordEncoder bcryptPasswordEncoder(){
            return new BCryptPasswordEncoder();
        }
}
