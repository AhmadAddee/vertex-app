package com.example.vertx_app;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.authorization.Authorization;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User implements io.vertx.ext.auth.User {

    private String username;

    private String password;

    private String full_name;

    private String image_url;

    private String profile;

    private int age;

    private Set<Authorities> authorities = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    //To give users appropriate roles
    public Set<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this == null || getClass() != o.getClass()) return false;
        User userDb = (User) o;
        return username.equals(userDb.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }


  @Override
  public JsonObject attributes() {
    return this.principal();
  }

  /**
   * @param authorization
   * @param handler
   * @deprecated
   */
  @Override
  public io.vertx.ext.auth.User isAuthorized(Authorization authorization, Handler<AsyncResult<Boolean>> handler) {
    return this;
  }

  @Override
  public JsonObject principal() {
    return this.principal();
  }

  /**
   * @param authProvider
   * @deprecated
   */
  @Override
  public void setAuthProvider(AuthProvider authProvider) {

  }

  @Override
  public io.vertx.ext.auth.User merge(io.vertx.ext.auth.User user) {
    return this;
  }

  @Override
  public String toString() {
    return "User{" +
           "username='" + username + '\'' +
           ", password='" + password + '\'' +
           ", full_name='" + full_name + '\'' +
           ", image_url='" + image_url + '\'' +
           ", profile='" + profile + '\'' +
           ", age=" + age +
           ", authorities=" + authorities +
           '}';
  }
}
