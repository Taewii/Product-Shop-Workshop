package productshop.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import productshop.domain.entities.Category;
import productshop.domain.entities.Role;

@Configuration
@EnableCaching
public class AppBeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Category -> Category Id
        Converter<Category, Long> converter = ctx ->
                ctx.getSource() == null ? null : ctx.getSource().getId();

        Converter<Role, String> roleToStringConverter = ctx ->
                ctx.getSource() == null ? null : ctx.getSource().authorityAsEnum().name();

        mapper.typeMap(Category.class, Long.class).setConverter(converter);
        mapper.typeMap(Role.class, String.class).setConverter(roleToStringConverter);

        return mapper;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}

