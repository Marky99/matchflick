package com.marky.matchflick.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "matchflick")
public class MatchflickProperties {

  @NestedConfigurationProperty
  private TokenProperties accessToken;

  @NestedConfigurationProperty
  private TokenProperties refreshToken;
}
