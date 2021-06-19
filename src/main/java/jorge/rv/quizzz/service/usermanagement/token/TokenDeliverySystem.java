package pravin.rv.quizzz.service.usermanagement.token;

import org.springframework.scheduling.annotation.Async;

import pravin.rv.quizzz.model.TokenModel;
import pravin.rv.quizzz.model.TokenType;
import pravin.rv.quizzz.model.User;

public interface TokenDeliverySystem {
	@Async
	void sendTokenToUser(TokenModel token, User user, TokenType tokenType);
}
