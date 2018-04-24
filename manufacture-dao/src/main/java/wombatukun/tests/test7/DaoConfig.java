package wombatukun.tests.test7;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "wombatukun.tests.test7.dao")
@EntityScan(basePackages = "wombatukun.tests.test7.entity")
public class DaoConfig {

}
