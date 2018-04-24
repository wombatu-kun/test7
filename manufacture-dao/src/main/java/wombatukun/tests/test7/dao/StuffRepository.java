package wombatukun.tests.test7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wombatukun.tests.test7.entity.Stuff;

@Repository
public interface StuffRepository extends JpaRepository<Stuff, Long> {

	Stuff findByName(String name);

}
