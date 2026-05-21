package io.github.bernardusz.feedbackwall.model;

import java.time.LocalDateTime;

public record PostSummary(
  Integer id,
  String title,
  LocalDateTime createdAt,
  int likeCount,
  int dislikeCount,
  int commentCount,
  int views
) {}
