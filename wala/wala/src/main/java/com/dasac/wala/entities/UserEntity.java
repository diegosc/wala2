package com.dasac.wala.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AppUser") //AppUser   = App_User
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String age;
    private String mail;
    private String password;


    //creando una dependencia circular
    @OneToOne (mappedBy = "user" , cascade = CascadeType.ALL,orphanRemoval = true)  //con esta indcamos que esta mapeado en PageEntty
    private PageEntity page;

}
