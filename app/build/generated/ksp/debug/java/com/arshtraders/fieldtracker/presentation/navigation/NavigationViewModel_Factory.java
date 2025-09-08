package com.arshtraders.fieldtracker.presentation.navigation;

import com.arshtraders.fieldtracker.domain.auth.RoleManager;
import com.arshtraders.fieldtracker.domain.auth.TokenManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class NavigationViewModel_Factory implements Factory<NavigationViewModel> {
  private final Provider<RoleManager> roleManagerProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public NavigationViewModel_Factory(Provider<RoleManager> roleManagerProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.roleManagerProvider = roleManagerProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public NavigationViewModel get() {
    return newInstance(roleManagerProvider.get(), tokenManagerProvider.get());
  }

  public static NavigationViewModel_Factory create(Provider<RoleManager> roleManagerProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new NavigationViewModel_Factory(roleManagerProvider, tokenManagerProvider);
  }

  public static NavigationViewModel newInstance(RoleManager roleManager,
      TokenManager tokenManager) {
    return new NavigationViewModel(roleManager, tokenManager);
  }
}
