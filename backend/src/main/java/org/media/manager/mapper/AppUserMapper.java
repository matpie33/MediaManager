package org.media.manager.mapper;

import org.media.manager.dto.AppUserDTO;
import org.media.manager.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    public AppUser mapUserDTO(AppUserDTO appUserDTO){
        AppUser appUser = new AppUser();
        appUser.setUsername(appUserDTO.getUserName());
        appUser.setPassword(appUserDTO.getPassword());
        appUser.setFirstName(appUserDTO.getFirstName());
        appUser.setLastName(appUserDTO.getLastName());
        appUser.setEmail(appUserDTO.getEmail());
        return appUser;
    }

}
