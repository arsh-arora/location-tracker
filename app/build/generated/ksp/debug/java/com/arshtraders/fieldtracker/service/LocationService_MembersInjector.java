package com.arshtraders.fieldtracker.service;

import com.arshtraders.fieldtracker.data.database.dao.LocationDao;
import com.google.android.gms.location.FusedLocationProviderClient;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class LocationService_MembersInjector implements MembersInjector<LocationService> {
  private final Provider<LocationDao> locationDaoProvider;

  private final Provider<FusedLocationProviderClient> fusedLocationClientProvider;

  public LocationService_MembersInjector(Provider<LocationDao> locationDaoProvider,
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    this.locationDaoProvider = locationDaoProvider;
    this.fusedLocationClientProvider = fusedLocationClientProvider;
  }

  public static MembersInjector<LocationService> create(Provider<LocationDao> locationDaoProvider,
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    return new LocationService_MembersInjector(locationDaoProvider, fusedLocationClientProvider);
  }

  @Override
  public void injectMembers(LocationService instance) {
    injectLocationDao(instance, locationDaoProvider.get());
    injectFusedLocationClient(instance, fusedLocationClientProvider.get());
  }

  @InjectedFieldSignature("com.arshtraders.fieldtracker.service.LocationService.locationDao")
  public static void injectLocationDao(LocationService instance, LocationDao locationDao) {
    instance.locationDao = locationDao;
  }

  @InjectedFieldSignature("com.arshtraders.fieldtracker.service.LocationService.fusedLocationClient")
  public static void injectFusedLocationClient(LocationService instance,
      FusedLocationProviderClient fusedLocationClient) {
    instance.fusedLocationClient = fusedLocationClient;
  }
}
