package pravin.rv.quizzz.service.usermanagement.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pravin.rv.quizzz.model.ForgotPasswordToken;
import pravin.rv.quizzz.repository.ForgotPasswordTokenRepository;
import pravin.rv.quizzz.service.usermanagement.utils.TokenGenerator;

@Service
public class TokenServiceForgotPassword extends TokenServiceAbs<ForgotPasswordToken> {

	@Value("${quizzz.tokens.forgot_password.timeout}")
	private Integer expirationTimeInMinutes = 86400;

	@Autowired
	public TokenServiceForgotPassword(ForgotPasswordTokenRepository forgotPasswordTokenRepository,
			TokenGenerator tokenGenerator) {
		super(tokenGenerator, forgotPasswordTokenRepository);
	}

	@Override
	protected ForgotPasswordToken create() {
		return new ForgotPasswordToken();
	}

	@Override
	protected int getExpirationTimeInMinutes() {
		return expirationTimeInMinutes;
	}

	public void setExpirationTimeInMinutes(Integer expirationTimeInMinutes) {
		this.expirationTimeInMinutes = expirationTimeInMinutes;
	}

}
