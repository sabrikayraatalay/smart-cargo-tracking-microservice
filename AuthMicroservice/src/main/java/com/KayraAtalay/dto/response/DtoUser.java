package com.KayraAtalay.dto.response;

import com.KayraAtalay.shared.dto.DtoBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser extends DtoBase {

    private String username;

    private String email;

}
