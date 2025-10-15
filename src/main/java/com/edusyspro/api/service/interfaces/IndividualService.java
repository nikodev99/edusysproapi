package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.auth.user.UserType;
import com.edusyspro.api.dto.IndividualUser;
import com.edusyspro.api.dto.custom.IndividualBasic;

import java.util.List;

public interface IndividualService {
    IndividualUser getLoginUser(Long personalId, UserType type);
    List<IndividualBasic> getSearchedUserPersonalInfo(String searchKeyword);
}
