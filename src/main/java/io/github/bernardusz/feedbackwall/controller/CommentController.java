package io.github.bernardusz.feedbackwall.controller;

import io.github.bernardusz.feedbackwall.repository.CommentRepository;
import io.github.bernardusz.feedbackwall.service.WallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
  private final WallService wallService;
  private final CommentRepository commentRepository;

  public CommentController(
    WallService wallService,
    CommentRepository commentRepository
  ) {
    this.wallService = wallService;
    this.commentRepository = commentRepository;
  }

  @PostMapping("/posts/{postId}/comments")
  public ResponseEntity<Void> createComment(
    @PathVariable long postId,
    @RequestBody CommentPayload comment
  ) {
    wallService.addCommentToPost(postId, comment.content());
    return ResponseEntity.ok().build();
  }

  @PutMapping("/comments/{commentId}")
  public ResponseEntity<Void> updateComment(
    @PathVariable long commentId,
    @RequestBody CommentPayload comment
  ){
    commentRepository.update(commentId, comment.content());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable long commentId) {
    wallService.deleteComment(commentId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/comments/{commentId}/like")
  public ResponseEntity<Void> likeComment(@PathVariable long commentId) {
    commentRepository.incrementLike(commentId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/comments/{commentId}/dislike")
  public ResponseEntity<Void> dislikeComment(@PathVariable long commentId) {
    commentRepository.incrementDislike(commentId);
    return ResponseEntity.ok().build();
  }
}

record CommentPayload(String content) {}
