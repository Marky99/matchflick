package com.marky.matchflick.security;

import com.marky.matchflick.domain.Profile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Implementation of this class provides basic operation related to {@link Authentication} class
 * such as membership and role checking.
 */
public interface PrincipalHolder {

  /**
   * Method returns currently logged in principal bound to executing thread
   *
   * @return specific implementation of {@link Authentication} based on Spring Security
   * authorization flow.
   */
  Authentication getAuthentication();

  /**
   * Method returns id of currently logged-in user.
   *
   * @return id of currently logged-in user
   */
  UUID getUserId();

  /**
   * Method returns {@link Jwt} representation of current principal, from which all claims and
   * fields can be accessed if necessary.
   *
   * @return current principal in form of {@link Jwt} object
   */
  Jwt getPrincipal();

  String getUserIdForLogging();

  /**
   * Method reads specific claim from current principal.
   *
   * @param claim key of any claim in jwt token
   * @param clazz class type of claim in jwt token
   * @param <T>   type of claim
   * @return optional holding requested claim, empty optional otherwise
   */
  @NonNull
  <T> Optional<T> getClaim(@NonNull String claim, @NonNull Class<T> clazz);

  /**
   * Method reads specific <b>string</b> claim from current principal
   *
   * @param claim key of claim in jwt token
   * @return optional holding requested claim, empty optional otherwise
   */
  @NonNull
  default Optional<String> getClaim(@NonNull String claim) {
    return getClaim(claim, String.class);
  }

  /**
   * Get the user from the database, using the user id from the principal holder.
   *
   * @return A new instance of QUser with the id set to the userId of the principalHolder.
   */
  default Profile getUser() {
    final var u = new Profile();
    u.setId(getUserId());
    return u;
  }

  default boolean isMe(@NonNull Profile user) {
    return isMe(user.getId());
  }

  default boolean isMe(@NonNull UUID userId) {
    return getUserId().equals(userId);
  }
}