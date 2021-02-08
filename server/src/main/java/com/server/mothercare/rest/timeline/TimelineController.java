package com.server.mothercare.rest.timeline;

import com.server.mothercare.entities.Image;
import com.server.mothercare.entities.Post;
import com.server.mothercare.entities.User;
import com.server.mothercare.services.ImageService;
import com.server.mothercare.services.PostService;
import com.server.mothercare.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
public class TimelineController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/post/save")
    private ResponseEntity savePost(@RequestBody Post thePost,
//                                    @RequestParam(value = "imageFile",required = false) MultipartFile theImage,
//                                    @RequestParam(value = "text",required = false) String tex,
                                    HttpServletRequest request
    ){
        System.out.println(thePost.getText()+" 111111111  "+thePost.getImage());
        User user= userService.userbyUserName(request.getUserPrincipal().getName());
        imageService.save(thePost.getImage());
        thePost.setUser(user);
        thePost.setDate(new Timestamp(new Date().getTime()));
        postService.save(thePost);
//        User user= userService.userbyUserName(request.getUserPrincipal().getName());
//        Post post;
//        if (theImage == null){
//            post =  new Post(tex, "TEXT", new Timestamp(new Date().getTime() ), user);
//            boolean saved = postService.save(post);
//            return  saved == true? new ResponseEntity("Success", HttpStatus.OK) : new ResponseEntity("FAILURE", HttpStatus.CONFLICT);
//        }else{
//            post =  new Post(tex, "TEXT_IMAGE", new Timestamp(new Date().getTime() ), user);
//            try {
//                Image image = new Image(theImage.getOriginalFilename(), theImage.getContentType(), theImage.getBytes());
//                boolean imSaved = imageService.save(image);
//                post.setImage(image);
//                boolean saved = postService.save(post);
//
//                return saved == true && imSaved== true? new ResponseEntity("Success", HttpStatus.OK) : new ResponseEntity("FAILURE", HttpStatus.CONFLICT);
//            } catch (IOException e) {
//                return new ResponseEntity("IMAGE_ERROR", HttpStatus.BAD_REQUEST);
//            }
//        }
        return null;
    }
    @GetMapping(value = "/post/get")
    private ResponseEntity getPosts(){
        List<Post> posts = null;
        posts = postService.getPosts();
        return posts == null? new ResponseEntity("Failure", HttpStatus.NO_CONTENT): new ResponseEntity(posts, HttpStatus.OK);
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
