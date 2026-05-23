package io.github.bernardusz.feedbackwall.model;

import java.time.LocalDateTime;

public record Post(
  Integer id,
  String title,
  String content,
  LocalDateTime createdAt,
  int likeCount,
  int dislikeCount,
  int commentCount,
  int views
) {
  public static Post empty() {
    return new Post(null, "", "", null, 0, 0, 0, 0);
  }
}

