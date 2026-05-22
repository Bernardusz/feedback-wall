package io.github.bernardusz.feedbackwall.model;

import java.util.List;

public record PostDetails(
  Post post,
  List<Comment> comments
){}
