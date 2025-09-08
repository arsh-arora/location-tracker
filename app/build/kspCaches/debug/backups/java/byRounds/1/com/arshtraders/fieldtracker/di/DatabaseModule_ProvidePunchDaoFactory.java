package com.arshtraders.fieldtracker.di;

import com.arshtraders.fieldtracker.data.database.AppDatabase;
import com.arshtraders.fieldtracker.data.database.dao.PunchDao;
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
public final class DatabaseModule_ProvidePunchDaoFactory implements Factory<PunchDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvidePunchDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PunchDao get() {
    return providePunchDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvidePunchDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvidePunchDaoFactory(databaseProvider);
  }

  public static PunchDao providePunchDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePunchDao(database));
  }
}
