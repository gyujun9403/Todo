package study.rsa101.todo.model;

import lombok.Builder;

import java.util.List;

@Builder
public class ResponseDTO<T> {
    private String error;
    private List<T> data;
}
