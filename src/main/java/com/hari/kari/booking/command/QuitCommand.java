package com.hari.kari.booking.command;

import com.hari.kari.booking.model.EmptyParam;
import com.hari.kari.booking.model.UserContext;
import org.springframework.stereotype.Component;

@Component
public class QuitCommand implements Command<EmptyParam> {

    private EmptyParam quitCommandParam;

    @Override
    public CommandResult execute() {
        //UserContext.UserContextManager.setUserContext(null);
        System.exit(1);
        return CommandResult.createSuccessResult("User Successfully logged out");
    }

    @Override
    public void setParams(EmptyParam quitCommandParam) {
        this.quitCommandParam = quitCommandParam;
    }

    @Override
    public EmptyParam getParams() {
        return quitCommandParam;
    }


}