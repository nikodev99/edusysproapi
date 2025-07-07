package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.auth.user.UserType;
import com.edusyspro.api.dto.IndividualUser;

public interface IndividualService {
    IndividualUser getLoginUser(Long personalId, UserType type);
}
