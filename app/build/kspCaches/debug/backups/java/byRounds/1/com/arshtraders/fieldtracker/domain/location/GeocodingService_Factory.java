package com.arshtraders.fieldtracker.domain.location;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class GeocodingService_Factory implements Factory<GeocodingService> {
  private final Provider<Context> contextProvider;

  public GeocodingService_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public GeocodingService get() {
    return newInstance(contextProvider.get());
  }

  public static GeocodingService_Factory create(Provider<Context> contextProvider) {
    return new GeocodingService_Factory(contextProvider);
  }

  public static GeocodingService newInstance(Context context) {
    return new GeocodingService(context);
  }
}
