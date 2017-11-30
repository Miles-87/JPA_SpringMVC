package pl.mmichonski.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by Mateusz on 2017-06-26.
 */
@Configuration
@EnableTransactionManagement //automatycznie zarzadza transakcjami
@PropertySource("classpath:pl.mmichonski/application.properties") //tworzymy dowiazanie do pliku
//properties ktory bedzie przechowywal konfiguracje ustawien hibernate oraz bazy danych
public class JpaConfiguration {
    //tworzymy spacjalna zmienna dzieki ktorej bedziemy 'wyciagac' dane z pliku properties
    private Environment environment;

    @Autowired
    public JpaConfiguration(Environment environment) {
        this.environment = environment;
    }

    //czas na konfiguracje  kilku beanow, ktore pozwola zestawic polaczenie z db z poziomu jpa/hibernate
    //i w dodatku jeszcze bedzie automatycznie skonfigurwana tranzakcyjnosc

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        ds.setUrl(environment.getRequiredProperty("jdbc.url"));
        ds.setUsername(environment.getRequiredProperty("jdbc.username"));
        ds.setPassword(environment.getRequiredProperty("jdbc.password"));
        return ds;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter()
    {
        //tutaj podpinamy JPA Adapter-a czyli u nas jest to Hibernate
        return new HibernateJpaVendorAdapter();
    }

    //teraz bedzie bean ktory zwroci obiekt Prperties
    //ten obiekt bedzie zawieral w sobie ustawienia konfiguracyjne JPA
    public Properties jpaProperties()
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return  properties;
    }

    //ten bean na podstawie powyzszej mapy properties wygeneruje obiekt ktory bedzie reprezentowal
    //EntityManager
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
    {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[]{"pl.mmichonski"});
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;

    }

    //podpinay EntityManager do tranzakcyjnosci
    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
    {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}
