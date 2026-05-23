package io.github.bernardusz.feedbackwall.repository;

import io.github.bernardusz.feedbackwall.model.Comment;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {
  private final JdbcClient jdbcClient;

  public CommentRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  public List<Comment> findByPostId(long postId){
    return jdbcClient.sql(
                      """
                      SELECT *
                      FROM comments
                      WHERE post_id = ?
                      """)
      .param(postId).query(Comment.class).list();
  }

  public Comment findByCommentId(long commentId){
    return jdbcClient.sql("SELECT * FROM comments WHERE id = ?")
      .param(commentId).query(Comment.class).single();
  }

  public void create(long postId, String content){
    jdbcClient.sql("INSERT INTO comments (post_id, content) VALUES (?, ?)")
      .params(postId, content).update();
  }

  public void update(long commentId, String content){
    jdbcClient.sql("UPDATE comments SET content = ? WHERE id = ?")
      .params(content, commentId).update();
  }

  public  void delete(long commentId){
    jdbcClient.sql("DELETE FROM comments WHERE id = ?")
      .param(commentId).update();
  }

  public void incrementLike(long id){
    jdbcClient.sql("UPDATE comments SET like_count = like_count + 1 WHERE id = ?")
      .param(id).update();
  }

  public void incrementDislike(long id){
    jdbcClient.sql("UPDATE comments SET dislike_count = dislike_count + 1 WHERE id = ?")
      .param(id).update();
  }
}
