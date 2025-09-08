package com.arshtraders.fieldtracker.domain.auth;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class RoleManager_Factory implements Factory<RoleManager> {
  private final Provider<TokenManager> tokenManagerProvider;

  public RoleManager_Factory(Provider<TokenManager> tokenManagerProvider) {
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public RoleManager get() {
    return newInstance(tokenManagerProvider.get());
  }

  public static RoleManager_Factory create(Provider<TokenManager> tokenManagerProvider) {
    return new RoleManager_Factory(tokenManagerProvider);
  }

  public static RoleManager newInstance(TokenManager tokenManager) {
    return new RoleManager(tokenManager);
  }
}
