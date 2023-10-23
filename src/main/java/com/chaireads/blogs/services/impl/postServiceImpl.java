package com.chaireads.blogs.services.impl;

import com.chaireads.blogs.entities.Category;
import com.chaireads.blogs.entities.Post;
import com.chaireads.blogs.entities.User;
import com.chaireads.blogs.exceptions.ResourceNotFoundException;
import com.chaireads.blogs.payloads.postDto;
import com.chaireads.blogs.payloads.PostResponse;
import com.chaireads.blogs.repositories.categoryRepo;
import com.chaireads.blogs.repositories.postRepo;
import com.chaireads.blogs.repositories.userRepo;
import com.chaireads.blogs.services.postService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class postServiceImpl implements postService {
    @Autowired
    private categoryRepo  cRepo;

    @Autowired
    private userRepo uRepo;

    @Autowired
    private postRepo pRepo;
    @Autowired
    private ModelMapper modelmapper;

    @Override
    public postDto createPost(postDto postdto, Integer cid, Integer uid) {
        User user = this.uRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",uid));

        Category category = this.cRepo.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id",cid));

        Post post = this.modelmapper.map(postdto, Post.class);
        post.setName(postdto.getName());
        post.setContent(postdto.getContent());
        post.setImage("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.pRepo.save(post);
        return this.modelmapper.map(newPost, postDto.class);

    }

    @Override
    public postDto updatePost(postDto postdto, Integer pid) {
        Post post = this.pRepo.findById(pid)
                .orElseThrow(() -> new ResourceNotFoundException("Post","Id",pid));
        post.setName(postdto.getName());
        post.setContent(postdto.getContent());
        post.setImage(postdto.getImage());
        Post updatePost = this.pRepo.save(post);
        return this.modelmapper.map(updatePost, postDto.class);
    }

    @Override
    public void deletePost(Integer pid) {
        Post post = this.pRepo.findById(pid)
                .orElseThrow(() -> new ResourceNotFoundException("Post","Id",pid));
        this.pRepo.delete(post);

    }


    @Override
    public PostResponse getAllPosts(Integer pagenumber, Integer size,String sortBy, String sortOrder) {
        Sort sort = (sortOrder.equalsIgnoreCase("asc"))?sort = Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
//        if(sortOrder.equalsIgnoreCase("asc")){
//            sort = Sort.by(sortBy).ascending();
//        }
//        else{
//            sort = Sort.by(sortBy).descending();
//        }
        Pageable p = PageRequest.of(pagenumber,size,sort);
        Page<Post> pagePost = this.pRepo.findAll(p);

        List<Post> posts = pagePost.getContent();
        List<postDto> postDto = posts.stream().map((post) -> this.modelmapper.map(post,postDto.class))
                .collect(Collectors.toList());

        PostResponse postresponse = new PostResponse();
        postresponse.setContent(postDto);
        postresponse.setPageNumber(pagePost.getNumber());
        postresponse.setPageSize(pagePost.getSize());
        postresponse.setTotalElements(pagePost.getTotalElements());
        postresponse.setTotalPages(pagePost.getTotalPages());
        postresponse.setLastPage(pagePost.isLast());


        return postresponse;
    }


    @Override
    public postDto getPostById(Integer pid) {
        Post post = this.pRepo.findById(pid)
                .orElseThrow(() -> new ResourceNotFoundException("Post","Id",pid));
        return this.modelmapper.map(post, postDto.class);
    }

    //get posts by category
    @Override
    public List<postDto> getAllPostsByCategory(Integer cid) {
        Category category = this.cRepo.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id",cid));
        List<Post> posts = this.pRepo.findByCategory(category);
        List<postDto> postsDto = posts.stream().map((post) -> this.modelmapper.map(post, postDto.class))
                .collect(Collectors.toList());
        return postsDto;
    }

    @Override
    public List<postDto> getAllPostsByUser(Integer uid) {
        User user = this.uRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",uid));
        List<Post> posts = this.pRepo.findByUser(user);
        List<postDto> postsDto = posts.stream().map((post) -> this.modelmapper.map(post, postDto.class))
                .collect(Collectors.toList());
        return postsDto;
    }

    @Override
    public List<postDto> searchPosts(String search) {
        List<Post> posts = this.pRepo.searchByName("%"+search+"%");
        List<postDto> postsDto = posts.stream().map((post) -> this.modelmapper.map(post, postDto.class))
                .collect(Collectors.toList());
        return postsDto;
    }
}
