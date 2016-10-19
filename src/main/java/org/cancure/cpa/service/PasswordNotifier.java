package org.cancure.cpa.service;

import org.cancure.cpa.persistence.entity.User;

public interface PasswordNotifier {

    void notify(User user, String password, Boolean resetPassword);
}
