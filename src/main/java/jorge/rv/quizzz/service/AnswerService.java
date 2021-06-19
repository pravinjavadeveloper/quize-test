package pravin.rv.quizzz.service;

import java.util.List;

import pravin.rv.quizzz.exceptions.ResourceUnavailableException;
import pravin.rv.quizzz.exceptions.UnauthorizedActionException;
import pravin.rv.quizzz.model.Answer;
import pravin.rv.quizzz.model.Question;

public interface AnswerService {
	Answer save(Answer answer) throws UnauthorizedActionException;

	Answer find(Long id) throws ResourceUnavailableException;

	Answer update(Answer newAnswer) throws UnauthorizedActionException, ResourceUnavailableException;

	void delete(Answer answer) throws UnauthorizedActionException, ResourceUnavailableException;

	List<Answer> findAnswersByQuestion(Question question);

	int countAnswersInQuestion(Question question);
}
