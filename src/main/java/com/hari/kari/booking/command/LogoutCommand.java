package com.hari.kari.booking.command;

import com.hari.kari.booking.model.EmptyParam;
import com.hari.kari.booking.model.UserContext;
import org.springframework.stereotype.Component;

@Component
public class LogoutCommand implements Command<EmptyParam> {

    private EmptyParam logoutCommandParam;

    @Override
    public CommandResult execute() {
        UserContext.UserContextManager.setUserContext(null);
        return CommandResult.createSuccessResult("User Successfully logged out");
    }

    @Override
    public void setParams(EmptyParam logoutCommandParam) {
        this.logoutCommandParam = logoutCommandParam;
    }

    @Override
    public EmptyParam getParams() {
        return logoutCommandParam;
    }


}