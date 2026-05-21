package io.github.bernardusz.feedbackwall.model;

import java.time.LocalDateTime;

public record Comment(
  Integer id,
  int postId,
  String content,
  int likeCount,
  int dislikeCount,
  LocalDateTime createdAt
) {}