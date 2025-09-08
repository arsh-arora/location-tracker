package com.arshtraders.fieldtracker.presentation.admin;

import com.arshtraders.fieldtracker.domain.admin.AdminRepository;
import com.arshtraders.fieldtracker.domain.auth.RoleManager;
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
public final class AdminDashboardViewModel_Factory implements Factory<AdminDashboardViewModel> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  private final Provider<RoleManager> roleManagerProvider;

  public AdminDashboardViewModel_Factory(Provider<AdminRepository> adminRepositoryProvider,
      Provider<RoleManager> roleManagerProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
    this.roleManagerProvider = roleManagerProvider;
  }

  @Override
  public AdminDashboardViewModel get() {
    return newInstance(adminRepositoryProvider.get(), roleManagerProvider.get());
  }

  public static AdminDashboardViewModel_Factory create(
      Provider<AdminRepository> adminRepositoryProvider,
      Provider<RoleManager> roleManagerProvider) {
    return new AdminDashboardViewModel_Factory(adminRepositoryProvider, roleManagerProvider);
  }

  public static AdminDashboardViewModel newInstance(AdminRepository adminRepository,
      RoleManager roleManager) {
    return new AdminDashboardViewModel(adminRepository, roleManager);
  }
}
