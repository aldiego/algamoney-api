package com.algaworks.algamoney.api.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
@EqualsAndHashCode
@ToString
public class ResourceCreatedEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long id;

    public ResourceCreatedEvent(Object source, HttpServletResponse response, Long id) {
        super(source);
        this.response = response;
        this.id = id;
    }
}
