package com.server.mothercare.rest.timeline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.util.JSONArrayUtils;
import com.server.mothercare.entities.*;
import com.server.mothercare.entities.post.Blog;
import com.server.mothercare.entities.post.Comment;
import com.server.mothercare.services.BlogService;
import com.server.mothercare.services.ImageService;
import com.server.mothercare.services.PostService;
import com.server.mothercare.services.UserService;
import netscape.javascript.JSObject;
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
import javax.websocket.server.PathParam;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
public class TimelineController {

    @Autowired
    private PostService postService;
    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;


    @PostMapping(value = "/blog/save")
    private ResponseEntity saveBlog(@RequestBody Blog theBlog,
                                    HttpServletRequest request
    ){
        User user= userService.userbyUserName(request.getUserPrincipal().getName());
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
//        try {
//            //String json = mapper.writeValueAsString(blogs);
//            //System.out.println(json);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return response;
    }

    @PostMapping(value = "/comment/{theId}")
    private ResponseEntity saveComment(@PathVariable int theId,
                                       @RequestBody Comment theComment,
                                       HttpServletRequest request){

        Blog blog = blogService.getBlogById(theId);
        User user= userService.userbyUserName(request.getUserPrincipal().getName());
        if (blog == null){
            return new ResponseEntity("\"Failure\"", HttpStatus.NO_CONTENT);
        }else {
            theComment.setDate(new Timestamp(new Date().getTime()));
            theComment.setUser(user);
            blog.addComment(theComment);
            boolean commentSaved = blogService.saveComment(theComment);
            return  commentSaved == false ? new ResponseEntity("\"Failure\"", HttpStatus.NO_CONTENT): new ResponseEntity(blog, HttpStatus.OK);
        }
    }

//    @PostMapping(value = "/commentToComment/{commentId}")
//    private ResponseEntity saveCommentToComment(@PathVariable int commentId,
//                                                @RequestBody Comment theComment,
//                                                HttpServletRequest request){
//
//        User user= userService.userbyUserName(request.getUserPrincipal().getName());
//        Comment commentDB = postService.getCommentById(commentId);
//        theComment.setDate(new Timestamp(new Date().getTime()));
//        theComment.setUser(user);
//        commentDB.addComment(theComment);
//        boolean commentSaved = postService.saveComment(theComment);
////            commentSaved = postService.updateComment(commentDB);
//        return  commentSaved == false ? new ResponseEntity("\"Failure\"", HttpStatus.NO_CONTENT): new ResponseEntity(theComment, HttpStatus.OK);
//    }


//    @PostMapping(value = "like/{postId}")
//    public ResponseEntity addLike(@PathVariable int postId, HttpServletRequest request){
//        User user= userService.userbyUserName(request.getUserPrincipal().getName());
//        Like theLike = new Like();
//        theLike.setUser(user);
//        Post thePost = postService.getPostById(postId);
//        thePost.addLikes(theLike);
//        boolean saved =postService.update(thePost);
//        return saved== true? new ResponseEntity(thePost, HttpStatus.OK):new ResponseEntity("\"Failure\"", HttpStatus.CONFLICT);
//    }

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
