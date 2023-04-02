package com.hari.kari.booking.command;

import lombok.Getter;

public class CommandResult {
    public enum COMMAND_STATUS{
        SUCCESS,
        FAILURE
    };

    @Getter
    private final String message;

    public COMMAND_STATUS commandStatus;


    public CommandResult(String message, COMMAND_STATUS commandStatus) {
        this.message = message;
        this.commandStatus = commandStatus;
    }

    public static CommandResult createSuccessResult(String message){
        return new CommandResult( message, COMMAND_STATUS.SUCCESS);
    }
    public static CommandResult createFailureResult(String message){
        return new CommandResult( message, COMMAND_STATUS.FAILURE);
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "message='" + message + '\'' +
                ", commandStatus=" + commandStatus +
                '}';
    }
}
