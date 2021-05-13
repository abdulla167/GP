package com.server.mothercare.rest.timeline;

import com.server.mothercare.entities.*;
import com.server.mothercare.entities.post.Comment;
import com.server.mothercare.entities.post.Like;
import com.server.mothercare.entities.post.Post;
import com.server.mothercare.services.ImageService;
import com.server.mothercare.services.PostService;
import com.server.mothercare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
                                    HttpServletRequest request
    ){
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
    @GetMapping(value = "/post/get/{first}")
    private ResponseEntity getPosts(@PathVariable int first){
        List<Post> posts = null;
        posts = postService.getPosts(first);
        return posts == null? new ResponseEntity("Failure", HttpStatus.NO_CONTENT): new ResponseEntity(posts, HttpStatus.OK);
    }

    @PostMapping(value = "/comment/{theId}")
    private ResponseEntity saveComment(@PathVariable int theId,
                                       @RequestBody Comment theComment,
                                       HttpServletRequest request){

        Post post = postService.getPostById(theId);
        User user= userService.userbyUserName(request.getUserPrincipal().getName());
        if (post == null){
            return new ResponseEntity("\"Failure\"", HttpStatus.NO_CONTENT);
        }else {
            theComment.setDate(new Timestamp(new Date().getTime()));
            theComment.setUser(user);
            post.addComment(theComment);
            boolean commentSaved = postService.saveComment(theComment);
            boolean saved = postService.update(post);
            System.out.println(saved + " " + post.getComments().get(0));
            return saved == false && commentSaved == false ? new ResponseEntity("\"Failure\"", HttpStatus.NO_CONTENT): new ResponseEntity(post, HttpStatus.OK);
        }
    }

    @PostMapping(value = "like/{postId}")
    public ResponseEntity addLike(@PathVariable int postId, HttpServletRequest request){
        User user= userService.userbyUserName(request.getUserPrincipal().getName());
        Like theLike = new Like();
        theLike.setUser(user);
        Post thePost = postService.getPostById(postId);
        thePost.addLikes(theLike);
        boolean saved =postService.update(thePost);
        return saved== true? new ResponseEntity(thePost, HttpStatus.OK):new ResponseEntity("\"Failure\"", HttpStatus.CONFLICT);
    }

    @GetMapping(value = "liked_posts")
    public ResponseEntity likedPosts(HttpServletRequest request){
        User user= userService.userbyUserName(request.getUserPrincipal().getName());
        List<Post> likedPosts = postService.likedPosts(user);
        return likedPosts != null? new ResponseEntity(likedPosts, HttpStatus.OK):new ResponseEntity("\"Failure\"", HttpStatus.CONFLICT);
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
