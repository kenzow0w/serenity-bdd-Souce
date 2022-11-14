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
    public AccountCredentials(String log, String pass) {
        try {
            login = log;
            password = pass;
            Evaluator.setVariable("login", login);
            Evaluator.setVariable("password", password);
            LOG.info("Инициализирован пользователь с параметрами\n Логин:[{}] Пароль:[{}] ", login, password);
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid username profile");
        }
//
//
    }


}
