package com.algaworks.algamoney.api.repository.projection;

import com.algaworks.algamoney.api.model.EntryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntryResume {

    private Long id;
    private String description;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private BigDecimal value;
    private EntryType type;
    private String category;
    private String person;
}
