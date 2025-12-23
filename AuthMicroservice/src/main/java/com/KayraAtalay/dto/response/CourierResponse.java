package com.KayraAtalay.dto.response;

import com.KayraAtalay.shared.dto.DtoBase;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CourierResponse extends DtoBase {
    private String fullName;
    @Email
    private String email;
    private String phoneNumber;
}
