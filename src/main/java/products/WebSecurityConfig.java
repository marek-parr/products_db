package products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Value("${spring.queries.users-query}")
    private String userQuery;
    @Value("${spring.queries.role-query}")
    private String roleQuery;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(roleQuery);
    }



    /*@Bean
    @Override
    public UserDetailsService userDetailsService(){
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("pass")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }*/

}
