package pravin.rv.quizzz.service.usermanagement;

import pravin.rv.quizzz.model.User;

public interface RegistrationService {
	User startRegistration(User user);

	User continueRegistration(User user, String token);

	boolean isRegistrationCompleted(User user);
}
