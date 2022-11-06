package com.example.stats.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "views")
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_id")
    private Long id;

    @NotNull
    @Size(max = 100)
    private String app;

    @NotNull
    @Size(max = 100)
    private String uri;

    @NotNull
    @Size(max = 100)
    private String ip;

    private LocalDateTime timestamp;
}
