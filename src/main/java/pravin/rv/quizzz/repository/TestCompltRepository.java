package pravin.rv.quizzz.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pravin.rv.quizzz.model.CompletedTest;



@Repository("TestCompltRepository")
public interface TestCompltRepository extends JpaRepository<CompletedTest, Long> {

	
}
