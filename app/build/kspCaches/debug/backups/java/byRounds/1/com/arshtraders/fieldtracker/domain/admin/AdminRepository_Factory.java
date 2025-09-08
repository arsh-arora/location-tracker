package com.arshtraders.fieldtracker.domain.admin;

import com.arshtraders.fieldtracker.data.database.dao.AuditLogDao;
import com.arshtraders.fieldtracker.data.database.dao.TeamDao;
import com.arshtraders.fieldtracker.data.database.dao.UserDao;
import com.arshtraders.fieldtracker.data.network.AdminApiService;
import com.arshtraders.fieldtracker.domain.auth.RoleManager;
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
public final class AdminRepository_Factory implements Factory<AdminRepository> {
  private final Provider<AdminApiService> adminApiServiceProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<TeamDao> teamDaoProvider;

  private final Provider<AuditLogDao> auditLogDaoProvider;

  private final Provider<RoleManager> roleManagerProvider;

  public AdminRepository_Factory(Provider<AdminApiService> adminApiServiceProvider,
      Provider<UserDao> userDaoProvider, Provider<TeamDao> teamDaoProvider,
      Provider<AuditLogDao> auditLogDaoProvider, Provider<RoleManager> roleManagerProvider) {
    this.adminApiServiceProvider = adminApiServiceProvider;
    this.userDaoProvider = userDaoProvider;
    this.teamDaoProvider = teamDaoProvider;
    this.auditLogDaoProvider = auditLogDaoProvider;
    this.roleManagerProvider = roleManagerProvider;
  }

  @Override
  public AdminRepository get() {
    return newInstance(adminApiServiceProvider.get(), userDaoProvider.get(), teamDaoProvider.get(), auditLogDaoProvider.get(), roleManagerProvider.get());
  }

  public static AdminRepository_Factory create(Provider<AdminApiService> adminApiServiceProvider,
      Provider<UserDao> userDaoProvider, Provider<TeamDao> teamDaoProvider,
      Provider<AuditLogDao> auditLogDaoProvider, Provider<RoleManager> roleManagerProvider) {
    return new AdminRepository_Factory(adminApiServiceProvider, userDaoProvider, teamDaoProvider, auditLogDaoProvider, roleManagerProvider);
  }

  public static AdminRepository newInstance(AdminApiService adminApiService, UserDao userDao,
      TeamDao teamDao, AuditLogDao auditLogDao, RoleManager roleManager) {
    return new AdminRepository(adminApiService, userDao, teamDao, auditLogDao, roleManager);
  }
}
