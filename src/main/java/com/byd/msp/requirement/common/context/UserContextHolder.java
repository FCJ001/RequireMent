package com.byd.msp.requirement.common.context;

public class UserContextHolder {

    private static final ThreadLocal<String> USER_HOLDER = new ThreadLocal<>();

    private static final UserContextHolder INSTANCE = new UserContextHolder();

    private UserContextHolder() {
    }

    public static UserContextHolder getInstance() {
        return INSTANCE;
    }

    public void setUsername(String username) {
        USER_HOLDER.set(username);
    }

    public String getUsername() {
        return USER_HOLDER.get();
    }

    public void clear() {
        USER_HOLDER.remove();
    }
}
