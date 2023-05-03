package uk.devoxx.tacke_eventual_consistency.data.custom;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "uk.devoxx.tacke_eventual_consistency.data",
        repositoryBaseClass = ExtendedRepositoryImpl.class, repositoryFactoryBeanClass = MyRepositoryFactoryBean.class)
public class ExtendedRepositoryConfiguration {

}
