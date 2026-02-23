package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.auth.user.UserType;
import com.edusyspro.api.dto.IndividualUser;
import com.edusyspro.api.dto.custom.IndividualBasic;
import com.edusyspro.api.model.Address;

import java.util.List;
import java.util.UUID;

public interface IndividualService {
    IndividualUser getLoginUser(Long personalId, UserType type);
    IndividualUser getLoginUser(UUID schoolId, Long personalId, UserType type);
    List<IndividualBasic> getSearchedUserPersonalInfo(String searchKeyword);
    Address getAddress(Long personalId);
}
