package com.arshtraders.fieldtracker.domain.punch;

import com.arshtraders.fieldtracker.data.database.dao.PlaceDao;
import com.arshtraders.fieldtracker.data.database.dao.PunchDao;
import com.arshtraders.fieldtracker.domain.location.GeocodingService;
import com.google.android.gms.location.FusedLocationProviderClient;
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
public final class PunchManager_Factory implements Factory<PunchManager> {
  private final Provider<FusedLocationProviderClient> fusedLocationClientProvider;

  private final Provider<PunchDao> punchDaoProvider;

  private final Provider<PlaceDao> placeDaoProvider;

  private final Provider<GeocodingService> geocodingServiceProvider;

  public PunchManager_Factory(Provider<FusedLocationProviderClient> fusedLocationClientProvider,
      Provider<PunchDao> punchDaoProvider, Provider<PlaceDao> placeDaoProvider,
      Provider<GeocodingService> geocodingServiceProvider) {
    this.fusedLocationClientProvider = fusedLocationClientProvider;
    this.punchDaoProvider = punchDaoProvider;
    this.placeDaoProvider = placeDaoProvider;
    this.geocodingServiceProvider = geocodingServiceProvider;
  }

  @Override
  public PunchManager get() {
    return newInstance(fusedLocationClientProvider.get(), punchDaoProvider.get(), placeDaoProvider.get(), geocodingServiceProvider.get());
  }

  public static PunchManager_Factory create(
      Provider<FusedLocationProviderClient> fusedLocationClientProvider,
      Provider<PunchDao> punchDaoProvider, Provider<PlaceDao> placeDaoProvider,
      Provider<GeocodingService> geocodingServiceProvider) {
    return new PunchManager_Factory(fusedLocationClientProvider, punchDaoProvider, placeDaoProvider, geocodingServiceProvider);
  }

  public static PunchManager newInstance(FusedLocationProviderClient fusedLocationClient,
      PunchDao punchDao, PlaceDao placeDao, GeocodingService geocodingService) {
    return new PunchManager(fusedLocationClient, punchDao, placeDao, geocodingService);
  }
}
