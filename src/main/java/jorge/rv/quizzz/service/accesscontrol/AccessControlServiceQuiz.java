package pravin.rv.quizzz.service.accesscontrol;

import org.springframework.stereotype.Service;

import pravin.rv.quizzz.exceptions.UnauthorizedActionException;
import pravin.rv.quizzz.model.AuthenticatedUser;
import pravin.rv.quizzz.model.Quiz;

@Service("AccessControlQuiz")
public class AccessControlServiceQuiz extends AccessControlServiceUserOwned<Quiz> {

	/*
	 * As long as the user is authenticated, it can create a Quiz.
	 */
	@Override
	public void canUserCreateObject(AuthenticatedUser user, Quiz object) throws UnauthorizedActionException {
		if (user == null) {
			throw new UnauthorizedActionException();
		}
	}

}
