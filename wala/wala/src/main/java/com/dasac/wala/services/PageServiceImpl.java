package com.dasac.wala.services;

import com.dasac.wala.dtos.PageRequest;
import com.dasac.wala.dtos.PageResponse;
import com.dasac.wala.dtos.PostRequest;

import com.dasac.wala.dtos.PostResponse;
import com.dasac.wala.entities.PageEntity;
import com.dasac.wala.entities.PostEntity;
import com.dasac.wala.exceptions.TitleNotValidException;
import com.dasac.wala.repositories.PageRepository;
import com.dasac.wala.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class PageServiceImpl implements PageService {
    private final PageRepository pageRepository;
    private final UserRepository userRepository;

    @Override
    public PageResponse create(PageRequest page) {
   /* final var entity = new PageEntity();  //create object(entty) to persist in Database
        BeanUtils.copyProperties(page, entity); //compiando las propiedades del argunmto page en la entidad
    final var user = this.userRepository.findById(page.getUserId()).orElseThrow(() -> new RuntimeException("User not found with id: " + page.getUserId())); //search user corresponding t  oage

    entity.setDateCreation(LocalDateTime.now()); //set date nowfecha actual
    entity.setUser(user);//create relationship between users and page**
      entity.setPosts(new ArrayList<>()); //sete empty list
       var pageCreated= this.pageRepository.save(entity); //upsert id exist id  update else insert
        final var response = new PageResponse();  //creato dto  for response
        BeanUtils.copyProperties(pageCreated,response);  //copy properties from entity(pageCreated) in response
        return response;*/
        this.validTitle(page.getTitle());

        // Crea un objeto vacío de PageEntity que se va a guardar en la base de datos
        final var entity = new PageEntity();

        // Copia las propiedades que tengan el mismo nombre de 'page' (DTO) hacia 'entity'
        // En este caso copia: title (userId no existe en PageEntity, se ignora)
        BeanUtils.copyProperties(page, entity);

        // Busca en la base de datos el usuario con el id que vino en el request
        // Si no lo encuentra, lanza una excepción con el mensaje
        final var user = this.userRepository.findById(page.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + page.getUserId()));

        // Le asigna la fecha y hora actual a la entidad
        entity.setDateCreation(LocalDateTime.now());

        // Le asigna el usuario encontrado, creando la relación entre Page y User
        entity.setUser(user);

        // Inicializa la lista de posts vacía para evitar nulls
        entity.setPosts(new ArrayList<>());

        // Guarda la entidad en la base de datos (hace el INSERT) y retorna la entidad ya con su id generado
        var pageCreated = this.pageRepository.save(entity);

        // Crea un objeto vacío de PageResponse que se va a devolver al cliente
        final var response = new PageResponse();

        // Copia las propiedades que coincidan de la entidad guardada hacia el response
        // Copia: title, dateCreation (posts NO se copia porque los tipos son distintos: List<PostEntity> vs List<PostResponse>)
        BeanUtils.copyProperties(pageCreated, response);

        // Convierte la lista de PostEntity a lista de PostResponse manualmente
        // porque BeanUtils no puede hacerlo solo al ser tipos distintos
        final var postResponses = pageCreated.getPosts().stream()
                // Por cada PostEntity en la lista...
                .map(p -> {
                    // ...crea un PostResponse vacío
                    final var postResponse = new PostResponse();
                    // ...copia sus propiedades (dateCreation, content, img)
                    BeanUtils.copyProperties(p, postResponse);
                    // ...retorna el PostResponse ya con sus datos
                    return postResponse;
                }).toList(); // Convierte el stream de vuelta a una lista

        // Asigna la lista de PostResponse al objeto response
        response.setPosts(postResponses);

        // Devuelve el response completo al que llamó este método
        return response;
    }

    @Override
    public PageResponse readByTitle(String title) {
        final var entityResponse = this.pageRepository.findByTitle(title)
                .orElseThrow(()-> new IllegalArgumentException("Title not found"));// find by title and handle errors
         final var response = new PageResponse();// create response object
        BeanUtils.copyProperties(entityResponse,response); // copy properties fron entity

        //Get post responses fron post entity
        final List<PostResponse>postResponses = entityResponse.getPosts()
                .stream()//convert to stream
                .map(postE -> // TRANSFORM POSTENTITY TO POSTRESPONSE
                     PostResponse
                             .builder()
                             .img(postE.getImg())
                                     .content(postE.getContent())
                                             .dateCreation(postE.getDateCreation())
                             .build()
                )
                .toList();// convert to list
        response.setPosts(postResponses); //set list of post
        return response;



    }

    @Override
    public PageResponse update(PageRequest page, String title)
    {
        this.validTitle(page.getTitle());
        final var entityFromDB = this.pageRepository.findByTitle(title)
                .orElseThrow(()-> new IllegalArgumentException("Title not found"));// find by title and handle errors
        entityFromDB.setTitle(page.getTitle()); // update fields from param page

        // Guarda la entidad en la base de datos (hace el INSERT) y retorna la entidad ya con su id generado
        var pageCreated = this.pageRepository.save(entityFromDB);

        // Crea un objeto vacío de PageResponse que se va a devolver al cliente
        final var response = new PageResponse();

        // Copia las propiedades que coincidan de la entidad guardada hacia el response
        // Copia: title, dateCreation (posts NO se copia porque los tipos son distintos: List<PostEntity> vs List<PostResponse>)
        BeanUtils.copyProperties(pageCreated, response);

        return response;
    }

    @Override
    public void delete(String title) {
       // final var entityFromDB = this.pageRepository.findByTitle(title)
       //         .orElseThrow(()-> new IllegalArgumentException("Title not found"));// find by title and handle errors
       // this.pageRepository.delete(entityFromDB);

       // this.pageRepository.deleteById(1l);

        if (this.pageRepository.existsByTitle(title)){

            log.info("Delinting page");
            this.pageRepository.deleteByTitle(title);
        }else {
            log.error("Error to delete");
            throw new IllegalArgumentException("Cant delete beacuse id not exist");
        }

    }

    @Override //dtos(request & response) -> entity | entity to dto
    public PageResponse createPost(PostRequest post, String title) {
//        final var postEntity = new PostEntity();
//        BeanUtils.copyProperties(post, postEntity);
//        postEntity.setDateCreation(LocalDateTime.now());
//        final var page = this.pageRepository.findById(post.getPageId())
//                .orElseThrow(() -> new RuntimeException("Page not found with id: " + post.getPageId()));
//        page.addPost(postEntity);
//        final var pageUpdated = this.pageRepository.save(page);
//        final var response = new PageResponse();
//        BeanUtils.copyProperties(pageUpdated, response);
//        final var postResponses = pageUpdated.getPosts().stream()
//                .map(p -> {
//                    final var postResponse = new PostResponse();
//                    BeanUtils.copyProperties(p, postResponse);
//                    return postResponse;
//                }).toList();
//        response.setPosts(postResponses);
        final var pageToUpdate = this.pageRepository.findByTitle(title)
                .orElseThrow(()-> new IllegalArgumentException("Title not found"));// find by title and handle errors
      final var postEntity = new PostEntity(); //create entitu to insert

      BeanUtils.copyProperties(post, postEntity);  //copy fields from dto to
        postEntity.setDateCreation(LocalDateTime.now());

      pageToUpdate.addPost(postEntity);
      final var responseEntity = this.pageRepository.save(pageToUpdate);  //update

      final var response = new PageResponse(); //create response
      BeanUtils.copyProperties(responseEntity, response); //copy fields from object updated

      final List<PostResponse> postResponses = responseEntity.getPosts() //maps posts from db .-> dto(responses
              .stream()//convert yo stream
              .map(postE->
                      PostResponse
                              .builder()
                              .img(postE.getImg())
                              .content(postE.getContent())
                              .dateCreation(postE.getDateCreation())
                              .build()
                      )
              .toList();
      response.setPosts(postResponses);

        return response;
    }

    @Override
    public void deletePost(Long idPost, String title) {
        final var pageToUpdate = this.pageRepository.findByTitle(title)
                .orElseThrow(()-> new IllegalArgumentException("Title not found"));// find by title and handle errors

        //metodo de busqueda
      final var postToDelete= pageToUpdate.getPosts()
                .stream()
                .filter( post-> post.getId().equals(idPost))
                .findFirst()
                .orElseThrow( ()->new IllegalArgumentException("post id not found"));
        pageToUpdate.removePost(postToDelete);
       this.pageRepository.save(pageToUpdate);
    }



    private void validTitle(String  title){
        if (title.contains("567889") ||title.contains ("1234")){
           throw  new TitleNotValidException("Title cant containd bad words");
        }
    }
}
