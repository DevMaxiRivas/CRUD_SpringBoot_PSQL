package dev.mycrud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
//  con la siguiente anotacion leemos aplication.yml y le damos la raiz del
//  yml es decir datasource.my-connection
    @ConfigurationProperties(prefix = "datasource.my-connection")
//    Creacion de beans especiales que reconoce sprig boot
    public DataSource crudDataSource(){
        return DataSourceBuilder.create().build();
    }

//    Aqui se utiliza inyeccion de dependencia
    @Bean
    public JdbcTemplate crudJdbcTemplate(DataSource crudDataSource){
//      la notacion var me permite no tener que declarar el tipo de dato sino que lo asume del constructor
        var jdbcTemplate = new JdbcTemplate(crudDataSource);
        return jdbcTemplate;
    }

    @Bean
//    Permita que las query sean mas faciles de hacer al trabajar con parametros se podria usar solo
//    jdbcTemplate solamente si se quiere
    public NamedParameterJdbcTemplate crudNamedParameterJdbcTemplate(JdbcTemplate crudJdbcTemplate){
        return new NamedParameterJdbcTemplate(crudJdbcTemplate);
    }

}
