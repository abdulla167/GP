package com.server.mothercare.rest.timeline;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mothercare.entities.Like;
import com.server.mothercare.entities.User;
import com.server.mothercare.entities.post.Blog;
import com.server.mothercare.entities.post.Comment;
import com.server.mothercare.services.BlogService;
import com.server.mothercare.services.CommentService;
import com.server.mothercare.services.UserService;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
public class TimelineController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;


    @PostMapping(value = "/blog/save")
    private ResponseEntity saveBlog(@RequestBody Blog theBlog,
                                     Principal userPrincipal
    ){
        User user= userService.userbyUserName(userPrincipal.getName());
        theBlog.setUser(user);
        theBlog.setDate(new Timestamp(new Date().getTime()));
        blogService.save(theBlog);
        return null;
    }

    @GetMapping(value = "/blog/get/{first}")
    private ResponseEntity getBlogs(@PathVariable int first){
        List<Blog> blogs = null;
        blogs = blogService.getBlogs(first);
        ResponseEntity response = blogs == null? new ResponseEntity("Failure", HttpStatus.NO_CONTENT): new ResponseEntity(blogs, HttpStatus.OK);
        ObjectMapper mapper = new ObjectMapper();
        return response;
    }

    @PostMapping(value = "/comment/{theId}")
    private ResponseEntity saveComment(@PathVariable int theId,
                                       @RequestBody Comment theComment,
                                       Principal userPrincipal){

        Blog DbBlog = blogService.getBlogById(theId);
        User user= userService.userbyUserName(userPrincipal.getName());
        if (DbBlog == null){
            return new ResponseEntity("\"Failure\"", HttpStatus.NO_CONTENT);
        }else {
            theComment.setDate(new Timestamp(new Date().getTime()));
            theComment.setUser(user);
            DbBlog.addComment(theComment);
            Blog blog = blogService.update(DbBlog);
            return  blog == null ? new ResponseEntity("\"Failure\"", HttpStatus.NO_CONTENT): new ResponseEntity(blog, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/commentToComment/{commentId}")
    private ResponseEntity saveCommentToComment(@PathVariable int commentId,
                                                @RequestBody Comment theComment,
                                                Principal userPrincipal){

        User user= userService.userbyUserName(userPrincipal.getName());
        Comment commentDB = commentService.getCommentById(commentId);
        theComment.setDate(new Timestamp(new Date().getTime()));
        theComment.setUser(user);
        commentDB.addComment(theComment);
        Comment comment = commentService.update(commentDB);
        return  comment == null ? new ResponseEntity("\"Failure\"", HttpStatus.NO_CONTENT): new ResponseEntity(comment, HttpStatus.OK);
    }


    @PostMapping(value = "like/{blogId}")
    public ResponseEntity addLike(@PathVariable int blogId, java.nio.file.attribute.UserPrincipal userPrincipal){
        User user= userService.userbyUserName(userPrincipal.getName());
        Like theLike = new Like();
        theLike.setUser(user);
        Blog DbBlog = blogService.getBlogById(blogId);
        DbBlog.addLikes(theLike);
        Blog blog = blogService.update(DbBlog);
        return blog== null? new ResponseEntity("\"Failure\"", HttpStatus.CONFLICT) : new ResponseEntity(blog, HttpStatus.OK);
    }

//    @GetMapping(value = "liked_posts")
//    public ResponseEntity likedPosts(HttpServletRequest request){
//        User user= userService.userbyUserName(request.getUserPrincipal().getName());
//        List<Post> likedPosts = postService.likedPosts(user);
//        return likedPosts != null? new ResponseEntity(likedPosts, HttpStatus.OK):new ResponseEntity("\"Failure\"", HttpStatus.CONFLICT);
//    }

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
