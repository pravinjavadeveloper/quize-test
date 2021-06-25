package pravin.rv.quizzz.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pravin.rv.quizzz.exceptions.ResourceUnavailableException;
import pravin.rv.quizzz.exceptions.UnauthorizedActionException;
import pravin.rv.quizzz.model.Quiz;
import pravin.rv.quizzz.model.User;
import pravin.rv.quizzz.model.support.Response;
import pravin.rv.quizzz.model.support.Result;

public interface QuizService {
	Quiz save(Quiz quiz, User user);
	
	Page<Quiz> findAll(Pageable pageable);

	Page<Quiz> findAllPublished(Pageable pageable);

	Page<Quiz> findQuizzesByUser(User user, Pageable pageable);

	Quiz find(Long id) throws ResourceUnavailableException;

	Quiz update(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	Page<Quiz> search(String query, Pageable pageable);

	Result checkAnswers(Quiz quiz, List<Response> answersBundle);

	void publishQuiz(Quiz quiz);
}
