package io.github.bernardusz.feedbackwall.service;

import io.github.bernardusz.feedbackwall.model.Comment;
import io.github.bernardusz.feedbackwall.model.Post;
import io.github.bernardusz.feedbackwall.model.PostDetails;
import io.github.bernardusz.feedbackwall.repository.CommentRepository;
import io.github.bernardusz.feedbackwall.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WallService {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  public  WallService(PostRepository postRepository, CommentRepository commentRepository) {
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
  }

  @Transactional
  public void addCommentToPost(long postId, String content) {
    commentRepository.create(postId, content);
    postRepository.incrementComment(postId);
  }

  @Transactional
  public PostDetails showPost(long postId) {
    postRepository.incrementViews(postId);

    Post post = postRepository.findById(postId);
    List<Comment> comments = commentRepository.findByPostId(postId);

    return  new PostDetails(post, comments);
  }

  @Transactional
  public void deleteComment(long commentId) {
    Comment comment = commentRepository.findByCommentId(commentId);
    postRepository.decrementComment(comment.postId());
    commentRepository.delete(commentId);
  }
}
