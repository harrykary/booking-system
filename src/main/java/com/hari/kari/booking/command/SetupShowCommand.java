package com.hari.kari.booking.command;

import com.hari.kari.booking.model.SetupShowCommandParam;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.model.UserContext;
import com.hari.kari.booking.service.ShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetupShowCommand implements Command<SetupShowCommandParam> {

    @Autowired
    private ShowService showService;
    private SetupShowCommandParam setupShowCommandParam;


    @Override
    public CommandResult execute() {
        UserContext currentContext = UserContext.UserContextManager.getCurrentUserContext();
        if(currentContext!=null && UserType.ADMIN.equals(currentContext.getUser().getUserType())){
            String showId = showService.createShow(getParams().getShowId(),getParams().getNumOfRows(),getParams().getNumOfSeats(), getParams().getCancelWindowMinutes());
            log.info("Show {} created", showId);
            return CommandResult.createSuccessResult("Show " + showId +" created. " );
        }else{
            log.warn("User Should login and should have Admin Role");
            return CommandResult.createFailureResult("User Should login and should have Admin Role" );
        }

    }

    @Override
    public void setParams(SetupShowCommandParam setupShowCommandParam) {
        this.setupShowCommandParam = setupShowCommandParam;
    }

    @Override
    public SetupShowCommandParam getParams() {
        return setupShowCommandParam;
    }


}