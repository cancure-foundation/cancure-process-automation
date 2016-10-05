package org.cancure.cpa.service;

public interface PasswordNotifier {

    void notify(String mail, String password, String login);
}
