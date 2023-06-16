package br.com.project.wish.handler;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErroView {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private Integer status;
  private String error;
  private String messagem;
  private String path;

  public ErroView() { this.timestamp = LocalDateTime.now(); }

  public ErroView(String messagem) {
    this();
    this.messagem = messagem;
  }

  public ErroView(Integer status, String error, String messagem, String path) {
    this();
    this.status = status;
    this.error = error;
    this.messagem = messagem;
    this.path = path;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessagem() {
    return messagem;
  }

  public void setMessagem(String messagem) {
    this.messagem = messagem;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
