package com.arshtraders.fieldtracker.di;

import com.arshtraders.fieldtracker.data.database.AppDatabase;
import com.arshtraders.fieldtracker.data.database.dao.TeamDao;
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
public final class DatabaseModule_ProvideTeamDaoFactory implements Factory<TeamDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideTeamDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TeamDao get() {
    return provideTeamDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTeamDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTeamDaoFactory(databaseProvider);
  }

  public static TeamDao provideTeamDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTeamDao(database));
  }
}
