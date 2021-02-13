package com.cybertek.entity;

import com.cybertek.entity.common.UserPrincipal;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class BaseEntityListener extends AuditingEntityListener {

    @PrePersist
    private void onPrePersist(BaseEntity baseEntity){

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setInsertDateTime(LocalDateTime.now());
        baseEntity.setLastUpdateDateTime(LocalDateTime.now());
        baseEntity.setInsertUserId(1l);
        baseEntity.setLastUpdateUserId(1l);

        if(authentication!=null && !authentication.getName().equals("anonymousUser")){
            long id = Long.parseLong(authentication.getName());
            baseEntity.setInsertUserId(id);
            baseEntity.setLastUpdateUserId( id);
        }

    }

    @PreUpdate
    private void onPreUpdate(BaseEntity baseEntity){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setLastUpdateDateTime(LocalDateTime.now());
        baseEntity.setLastUpdateUserId(1l);

        if(authentication!=null && !authentication.getName().equals("anonymousUser")){
            long id = Long.parseLong(authentication.getName());
            baseEntity.setLastUpdateUserId(id);
        }


    }


}
