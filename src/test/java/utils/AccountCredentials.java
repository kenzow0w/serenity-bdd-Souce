package utils;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class AccountCredentials {

    Logger LOG = LoggerFactory.getLogger(AccountCredentials.class);
    private final String login;
    private final String password;

    @SneakyThrows
    public AccountCredentials(String role) {
        try {
            login = Evaluator.getVariable(String.format("#{%s.login}", role));
            password = Evaluator.getVariable(String.format("#{%s.password}", role));
            LOG.info("Initialization user with parameters\n Login:[{}] Password:[{}] ", login, password);
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid username profile");
        }
//
//
    }


}
