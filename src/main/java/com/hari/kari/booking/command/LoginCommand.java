package com.hari.kari.booking.command;

import com.hari.kari.booking.model.User;
import com.hari.kari.booking.model.LoginCommandParam;
import com.hari.kari.booking.model.UserContext;
import com.hari.kari.booking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginCommand implements Command<LoginCommandParam> {

    @Autowired
    private UserService userService;
    private LoginCommandParam loginCommandParam;


    @Override
    public CommandResult execute() {
        CommandResult result;
        User user = userService.getUser(loginCommandParam.getUserName());
        if(user!= null){
            UserContext context = new UserContext(user);
            UserContext.UserContextManager.setUserContext(context);
            result = CommandResult.createSuccessResult("User Logged in Successful");
            log.info("User after Login {}", context.getUser());
        } else{
            UserContext.UserContextManager.setUserContext(null);
            result = CommandResult.createFailureResult("No User found");
        }
        return result;
    }

    @Override
    public void setParams(LoginCommandParam loginCommandParam) {
        this.loginCommandParam = loginCommandParam;
    }

    @Override
    public LoginCommandParam getParams() {
        return loginCommandParam;
    }


}