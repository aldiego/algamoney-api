package com.algaworks.algamoney.api.repository.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntryFilter {

    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDateTo;
}
