package pravin.rv.quizzz.service.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pravin.rv.quizzz.exceptions.UserAlreadyExistsException;
import pravin.rv.quizzz.model.RegistrationToken;
import pravin.rv.quizzz.model.TokenType;
import pravin.rv.quizzz.model.User;
import pravin.rv.quizzz.service.UserService;
import pravin.rv.quizzz.service.usermanagement.token.TokenDeliverySystem;
import pravin.rv.quizzz.service.usermanagement.token.TokenServiceRegistration;

@Service
public class RegistrationServiceMail implements RegistrationService {

	private UserService userService;
	private TokenServiceRegistration tokenService;
	private TokenDeliverySystem tokenDeliveryService;

	@Autowired
	public RegistrationServiceMail(UserService userService, TokenServiceRegistration tokenService,
			TokenDeliverySystem tokenDeliveryService) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.tokenDeliveryService = tokenDeliveryService;
	}

	@Override
	public User startRegistration(User user) {
		User newUser;

		try {
			newUser = userService.saveUser(user);
		} catch (UserAlreadyExistsException e) {
			newUser = userService.findByEmail(user.getEmail());
			if (userService.isRegistrationCompleted(newUser)) {
				throw e;
			}
		}

		RegistrationToken mailToken = tokenService.generateTokenForUser(newUser);
		tokenDeliveryService.sendTokenToUser(mailToken, newUser, TokenType.REGISTRATION_MAIL);

		return newUser;
	}

	@Override
	public User continueRegistration(User user, String token) {
		tokenService.validateTokenForUser(user, token);

		userService.setRegistrationCompleted(user);
		tokenService.invalidateToken(token);

		return user;
	}

	@Override
	public boolean isRegistrationCompleted(User user) {
		return userService.isRegistrationCompleted(user);
	}

}
