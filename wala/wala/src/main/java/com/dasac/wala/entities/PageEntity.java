package com.dasac.wala.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Page")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String title;
    private LocalDateTime dateCreation;   // private LocalDateTime dateCreation;

@OneToOne               //mapeo circular
@JoinColumn(name = "id_User", unique = true)
    private UserEntity user;

//no es bidirecconal
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_page")  //relacion de uno a muchos foreng key dependenca circular
    private List<PostEntity> posts = new ArrayList<>();

    //metodo para añadr post
    public void  addPost(PostEntity post){
        posts.add(post);
    }

    //metodo para remover
    public void removePost(PostEntity post){
        posts.remove(post);
    }
}
