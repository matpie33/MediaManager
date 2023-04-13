package org.media.manager.mapper;

import org.media.manager.dto.AppUserDTO;
import org.media.manager.dto.UserPersonalDTO;
import org.media.manager.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    public AppUser mapUser(AppUserDTO appUserDTO){
        AppUser appUser = new AppUser();
        appUser.setUsername(appUserDTO.getUserName());
        appUser.setPassword(appUserDTO.getPassword());
        appUser.setFirstName(appUserDTO.getPersonalData().getFirstName());
        appUser.setLastName(appUserDTO.getPersonalData().getLastName());
        appUser.setEmail(appUserDTO.getPersonalData().getEmail());
        return appUser;
    }

    public void mapUserPersonalData(AppUser appUser, UserPersonalDTO userPersonalDTO) {
        appUser.setEmail(userPersonalDTO.getEmail());
        appUser.setFirstName(userPersonalDTO.getFirstName());
        appUser.setLastName(userPersonalDTO.getLastName());

    }
}
