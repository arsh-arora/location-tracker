package com.arshtraders.fieldtracker.di;

import com.arshtraders.fieldtracker.data.network.AdminApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class AuthModule_ProvideAdminApiServiceFactory implements Factory<AdminApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public AuthModule_ProvideAdminApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public AdminApiService get() {
    return provideAdminApiService(retrofitProvider.get());
  }

  public static AuthModule_ProvideAdminApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new AuthModule_ProvideAdminApiServiceFactory(retrofitProvider);
  }

  public static AdminApiService provideAdminApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AuthModule.INSTANCE.provideAdminApiService(retrofit));
  }
}
