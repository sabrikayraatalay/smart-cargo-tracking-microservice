package com.KayraAtalay.utils;

import com.KayraAtalay.dto.response.DtoUser;
import com.KayraAtalay.model.User;
import org.springframework.beans.BeanUtils;

public class DtoConverter {

    public static DtoUser toDto(User user) {
        DtoUser dtoUser = new DtoUser();

        BeanUtils.copyProperties(user, dtoUser);

        return dtoUser;
    }
}
