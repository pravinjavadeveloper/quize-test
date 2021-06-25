package pravin.rv.quizzz.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pravin.rv.quizzz.model.CompletedTest;
import pravin.rv.quizzz.repository.TestCompltRepository;


@Service("TestCompltService")
@Transactional
public class TestCompltServiceImpl implements TestCompltService{
	

	private static final Logger logger = LoggerFactory.getLogger(TestCompltServiceImpl.class);
	@Autowired
	private TestCompltRepository testCompltRepository;

	@Override
	public void save(CompletedTest completedTest) {
		logger.info("msg"+completedTest.toString());
		testCompltRepository.save(completedTest);
		
	}

	
	
}
