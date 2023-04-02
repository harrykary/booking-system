package com.hari.kari.booking.model;

public class UserContext {
    private User user;

    public UserContext(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public static class UserContextManager{

        private static UserContext userContext;

        public static UserContext getCurrentUserContext(){
            return userContext;
        }

        public static void setUserContext(UserContext context){
            userContext = context;
        }
    }
}
