package com.arshtraders.fieldtracker.presentation.tracking;

import android.app.Application;
import com.arshtraders.fieldtracker.data.database.dao.LocationDao;
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
public final class TrackingViewModel_Factory implements Factory<TrackingViewModel> {
  private final Provider<Application> applicationProvider;

  private final Provider<LocationDao> locationDaoProvider;

  public TrackingViewModel_Factory(Provider<Application> applicationProvider,
      Provider<LocationDao> locationDaoProvider) {
    this.applicationProvider = applicationProvider;
    this.locationDaoProvider = locationDaoProvider;
  }

  @Override
  public TrackingViewModel get() {
    return newInstance(applicationProvider.get(), locationDaoProvider.get());
  }

  public static TrackingViewModel_Factory create(Provider<Application> applicationProvider,
      Provider<LocationDao> locationDaoProvider) {
    return new TrackingViewModel_Factory(applicationProvider, locationDaoProvider);
  }

  public static TrackingViewModel newInstance(Application application, LocationDao locationDao) {
    return new TrackingViewModel(application, locationDao);
  }
}
