package com.example.demo.domain.member.entity;

import com.example.demo.domain.tickets.entity.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email"),
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "member", cascade = CascadeType.ALL )
    private List<Authority> authorities;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "member", cascade = CascadeType.ALL )
    private List<Ticket> tickets;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "member", cascade = CascadeType.ALL )
    private List<Ticket> usedTickets;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp regDate;
}
