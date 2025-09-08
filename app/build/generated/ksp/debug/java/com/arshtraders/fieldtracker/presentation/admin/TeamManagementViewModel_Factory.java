package com.arshtraders.fieldtracker.presentation.admin;

import com.arshtraders.fieldtracker.domain.admin.AdminRepository;
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
public final class TeamManagementViewModel_Factory implements Factory<TeamManagementViewModel> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  public TeamManagementViewModel_Factory(Provider<AdminRepository> adminRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
  }

  @Override
  public TeamManagementViewModel get() {
    return newInstance(adminRepositoryProvider.get());
  }

  public static TeamManagementViewModel_Factory create(
      Provider<AdminRepository> adminRepositoryProvider) {
    return new TeamManagementViewModel_Factory(adminRepositoryProvider);
  }

  public static TeamManagementViewModel newInstance(AdminRepository adminRepository) {
    return new TeamManagementViewModel(adminRepository);
  }
}
