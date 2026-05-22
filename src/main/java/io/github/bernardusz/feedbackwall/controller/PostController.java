package io.github.bernardusz.feedbackwall.controller;

import io.github.bernardusz.feedbackwall.model.Post;
import io.github.bernardusz.feedbackwall.model.PostDetails;
import io.github.bernardusz.feedbackwall.repository.PostRepository;
import io.github.bernardusz.feedbackwall.service.WallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class PostController {
  private final PostRepository postRepository;
  private final WallService wallService;

  public PostController(PostRepository postRepository, WallService wallService) {
    this.postRepository = postRepository;
    this.wallService = wallService;
  }

  @GetMapping // Read - All
  public String list(@RequestParam(required = false) String title,Model model){
    if (title != null && !title.isBlank()){
      model.addAttribute("posts", postRepository.findByTitle(title));
    }
    else {
      model.addAttribute("posts", postRepository.findAll());
    }
    return "index";
  }

  @GetMapping("/{id}") // Read - By ID
  public String show(@PathVariable long id, Model model){
    PostDetails details = wallService.showPost(id);

    model.addAttribute("post", details.post());
    model.addAttribute("comments", details.comments());

    return "post";
  }


  @GetMapping("/new") // Create - Page
  public String newPost(){
    return "new_post";
  }

  @PostMapping("/create") // Create - Action
  public  String createPost(@ModelAttribute("post") Post post){
    postRepository.create(post);
    return "redirect:/";
  }


  @GetMapping("/edit/{id}") // Update - Page
  public String edit(@PathVariable long id, Model model){
    model.addAttribute("post", postRepository.findById(id));
    return "edit_post";
  }

  @PostMapping("/update") // Update - Action
  public String updatePost(@ModelAttribute("post") Post post){
    postRepository.update(post);
    return "redirect:/";
  }


  @PostMapping("/delete/{id}")
  public String deletePost(@PathVariable long id){
    postRepository.delete(id);
    return "redirect:/";
  }

  @GetMapping("/like/{id}")
  public String likePost(@PathVariable long id){
    postRepository.incrementLike(id);
    return "redirect:/" + id;
  }

  @GetMapping("/dislike/{id}")
  public String dislikePost(@PathVariable long id){
    postRepository.incrementDislike(id);
    return "redirect:/" + id;
  }
}
