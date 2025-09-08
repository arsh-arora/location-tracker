package com.arshtraders.fieldtracker.di;

import com.arshtraders.fieldtracker.data.database.AppDatabase;
import com.arshtraders.fieldtracker.data.database.dao.AuditLogDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideAuditLogDaoFactory implements Factory<AuditLogDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideAuditLogDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public AuditLogDao get() {
    return provideAuditLogDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideAuditLogDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideAuditLogDaoFactory(databaseProvider);
  }

  public static AuditLogDao provideAuditLogDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAuditLogDao(database));
  }
}
