package pravin.rv.quizzz.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import pravin.rv.quizzz.exceptions.ResourceUnavailableException;
import pravin.rv.quizzz.exceptions.UnauthorizedActionException;
import pravin.rv.quizzz.exceptions.UserAlreadyExistsException;
import pravin.rv.quizzz.model.User;

public interface UserService extends UserDetailsService {
	User saveUser(User user) throws UserAlreadyExistsException;

	User find(Long id) throws ResourceUnavailableException;;

	User findByEmail(String email) throws ResourceUnavailableException;

	User findByUsername(String username) throws ResourceUnavailableException;

	User updatePassword(User user, String password) throws ResourceUnavailableException;

	void delete(Long user_id) throws UnauthorizedActionException, ResourceUnavailableException;

	User setRegistrationCompleted(User user) throws ResourceUnavailableException;

	boolean isRegistrationCompleted(User user) throws ResourceUnavailableException;

}