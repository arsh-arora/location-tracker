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
public final class UserManagementViewModel_Factory implements Factory<UserManagementViewModel> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  public UserManagementViewModel_Factory(Provider<AdminRepository> adminRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
  }

  @Override
  public UserManagementViewModel get() {
    return newInstance(adminRepositoryProvider.get());
  }

  public static UserManagementViewModel_Factory create(
      Provider<AdminRepository> adminRepositoryProvider) {
    return new UserManagementViewModel_Factory(adminRepositoryProvider);
  }

  public static UserManagementViewModel newInstance(AdminRepository adminRepository) {
    return new UserManagementViewModel(adminRepository);
  }
}
