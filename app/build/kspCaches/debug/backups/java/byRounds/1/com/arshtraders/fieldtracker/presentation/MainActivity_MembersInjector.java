package com.arshtraders.fieldtracker.presentation;

import com.arshtraders.fieldtracker.domain.auth.TokenManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<TokenManager> tokenManagerProvider;

  public MainActivity_MembersInjector(Provider<TokenManager> tokenManagerProvider) {
    this.tokenManagerProvider = tokenManagerProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<TokenManager> tokenManagerProvider) {
    return new MainActivity_MembersInjector(tokenManagerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectTokenManager(instance, tokenManagerProvider.get());
  }

  @InjectedFieldSignature("com.arshtraders.fieldtracker.presentation.MainActivity.tokenManager")
  public static void injectTokenManager(MainActivity instance, TokenManager tokenManager) {
    instance.tokenManager = tokenManager;
  }
}
