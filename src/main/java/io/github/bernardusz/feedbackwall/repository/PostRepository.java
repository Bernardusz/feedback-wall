package io.github.bernardusz.feedbackwall.repository;

import io.github.bernardusz.feedbackwall.model.Post;
import io.github.bernardusz.feedbackwall.model.PostSummary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {
  private final JdbcClient jdbcClient;
  public PostRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  public List<PostSummary> findAll(){
    return jdbcClient.sql("""
            SELECT 
            id, title, created_at, like_count, dislike_count, comment_count 
            FROM posts
            ORDER BY created_at ASC
            """)
      .query(PostSummary.class).list();
  }

  public List<PostSummary> findByTitle(String title){
    return jdbcClient
        .sql("""
            SELECT 
            id, title, created_at, like_count, dislike_count, comment_count 
            FROM posts
            WHERE title ILIKE ?
            ORDER BY created_at ASC
            """)
        .param(title)
        .query(PostSummary.class)
        .list();
  }

  public Post findById(Long id){
    return jdbcClient.sql("SELECT * FROM posts WHERE id = ?")
      .param(id).query(Post.class).single();
  }

  public void create(Post post){
    jdbcClient.sql("INSERT INTO posts (title, content) VALUES (?, ?)")
      .params(post.title(), post.content()).update();
  }

  public  void update(Post post){
    jdbcClient.sql("UPDATE posts SET title = ?, content = ? WHERE id = ?")
      .params(post.title(), post.content(), post.id()).update();
  }

  public void delete(long id){
    jdbcClient.sql("DELETE FROM posts WHERE id = ?")
      .param(id).update();
  }

  public void incrementViews(long id){
    jdbcClient.sql("UPDATE posts SET views = views + 1 WHERE id = ?")
      .param(id).update();
  }

  public void incrementLike(long id){
    jdbcClient.sql("UPDATE posts SET like_count = like_count + 1 WHERE id = ?")
      .param(id).update();
  }

  public void incrementDislike(long id){
    jdbcClient.sql("UPDATE posts SET dislike_count = dislike_count + 1 WHERE id = ?")
      .param(id).update();
  }

  public  void incrementComment(long id){
    jdbcClient.sql("UPDATE posts SET comment_count = comment_count + 1 WHERE id = ?")
      .param(id).update();
  }
}
