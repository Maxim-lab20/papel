package com.example.papel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts", schema = "papel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq_generator")
    @SequenceGenerator(name = "post_seq_generator", sequenceName = "papel.post_sequence", allocationSize = 1)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}