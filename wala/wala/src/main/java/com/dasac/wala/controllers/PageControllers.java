package com.dasac.wala.controllers;

import com.dasac.wala.dtos.PageRequest;
import com.dasac.wala.dtos.PageResponse;
import com.dasac.wala.dtos.PostRequest;
import com.dasac.wala.services.PageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController // use to export RESTFULL
@RequestMapping(path ="page") // what to get this controllers
@AllArgsConstructor
public class PageControllers {

    private final PageService pageService;

    @GetMapping(path = "{title}") //use to get data
     public ResponseEntity <PageResponse> getPage( @PathVariable String title){
     return ResponseEntity.ok(this.pageService.readByTitle(title));
     }

    @PostMapping //use to create data
    public ResponseEntity <?> postPage(@RequestBody PageRequest request){
         request.setTitle(this.normalizeTitle(request.getTitle()));

        final var uri = this.pageService.create(request).getTitle();
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PutMapping(path="{title}") //use to update data
    public ResponseEntity <PageResponse> updatePage(
            @PathVariable String title,
            @RequestBody PageRequest request
    ){

        return ResponseEntity.ok(this.pageService.update(request, title));
    }

    @DeleteMapping(path = "{title}") //use to delete data
    public ResponseEntity <Void> deletePage(@PathVariable String title){
        this.pageService.delete(title);
        return ResponseEntity.noContent().build();
    }

    private String normalizeTitle(String title){
        if (title.contains(" ")){
            return title.replaceAll(" ", "-");
        }else {
            return title;
        }

    }

    @PostMapping (path = "post/{title}") //use to create data
    public ResponseEntity <PageResponse> postPage(
            @RequestBody PostRequest request,
           @PathVariable String title
    ){

        return ResponseEntity.ok(this.pageService.createPost(request,title));
    }


    @DeleteMapping(path = "post/{title}/{idPost}") //use to delete data
    public ResponseEntity <Void> deletePage(
            @PathVariable String title,
            @PathVariable Long idPost){
        this.pageService.deletePost(idPost,title);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "img/upload")
    public ResponseEntity<String> upload(
            @RequestParam(value= "file")MultipartFile file){
        try {

      final var pathUrl ="C:\\Users\\LENOVO\\Downloads\\wala (2)\\wala\\wala\\src\\main\\resources\\static\\img";
      final var path= Paths.get(pathUrl);

      if (!Files.exists(path)){
          Files.createDirectory(path);
      }
      final var fullname= pathUrl +File.separator+ file.getOriginalFilename();
      final var destination = new File(fullname);

      file.transferTo(destination);

            return ResponseEntity.ok("Upload success on: "+ fullname);
        }catch (IOException e){
            return  ResponseEntity.internalServerError().body("Cant upload img");
        }


    }

}

