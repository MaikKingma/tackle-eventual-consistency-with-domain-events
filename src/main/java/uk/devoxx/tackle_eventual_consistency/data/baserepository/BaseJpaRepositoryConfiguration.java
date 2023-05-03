package uk.devoxx.tackle_eventual_consistency.data.baserepository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "uk.devoxx.tackle_eventual_consistency.data",
        repositoryBaseClass = BaseJpaRepositoryImpl.class, repositoryFactoryBeanClass = BaseJpaRepositoryFactoryBean.class)
public class BaseJpaRepositoryConfiguration {

}
