package com.KayraAtalay.shared.handler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError<E> {

    private Integer status;

    private ExceptionResponse<E> exception;

}