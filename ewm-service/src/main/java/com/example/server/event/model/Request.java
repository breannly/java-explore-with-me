package com.example.server.event.model;

import com.example.server.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private LocalDateTime created = LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;
}
