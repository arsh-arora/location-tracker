package com.arshtraders.fieldtracker.presentation.home;

import com.arshtraders.fieldtracker.data.database.dao.LocationDao;
import com.arshtraders.fieldtracker.data.database.dao.PunchDao;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<PunchDao> punchDaoProvider;

  private final Provider<LocationDao> locationDaoProvider;

  public HomeViewModel_Factory(Provider<PunchDao> punchDaoProvider,
      Provider<LocationDao> locationDaoProvider) {
    this.punchDaoProvider = punchDaoProvider;
    this.locationDaoProvider = locationDaoProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(punchDaoProvider.get(), locationDaoProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<PunchDao> punchDaoProvider,
      Provider<LocationDao> locationDaoProvider) {
    return new HomeViewModel_Factory(punchDaoProvider, locationDaoProvider);
  }

  public static HomeViewModel newInstance(PunchDao punchDao, LocationDao locationDao) {
    return new HomeViewModel(punchDao, locationDao);
  }
}
