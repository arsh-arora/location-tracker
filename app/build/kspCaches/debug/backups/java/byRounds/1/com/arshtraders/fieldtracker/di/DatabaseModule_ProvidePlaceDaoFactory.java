package com.arshtraders.fieldtracker.di;

import com.arshtraders.fieldtracker.data.database.AppDatabase;
import com.arshtraders.fieldtracker.data.database.dao.PlaceDao;
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
public final class DatabaseModule_ProvidePlaceDaoFactory implements Factory<PlaceDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvidePlaceDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PlaceDao get() {
    return providePlaceDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvidePlaceDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvidePlaceDaoFactory(databaseProvider);
  }

  public static PlaceDao providePlaceDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePlaceDao(database));
  }
}
