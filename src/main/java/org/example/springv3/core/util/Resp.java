package org.example.springv3.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;

//요청에 대한 응답을 위한 클래스. 어떤 타입이 들어올지 모르므로 제네릭을 사용한다.
@AllArgsConstructor
@Data
public class Resp<T> {
    private Integer status;
    private String msg;
    private T body;

    public static <B> Resp<?> ok(B body){
        return new Resp<>(200, "성공", body);
    }

    public static Resp<?> fail(Integer status, String msg){
        return new Resp<>(status, msg, null);
    }
}