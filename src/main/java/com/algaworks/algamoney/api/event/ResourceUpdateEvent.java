package com.algaworks.algamoney.api.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
@EqualsAndHashCode
@ToString
public class ResourceUpdateEvent extends ApplicationEvent {

    private final HttpServletResponse response;
    private final Long id;

    public ResourceUpdateEvent(Object source, HttpServletResponse response, Long id) {
        super(source);
        this.response = response;
        this.id = id;
    }
}
