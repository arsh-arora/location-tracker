package com.arshtraders.fieldtracker.di;

import com.arshtraders.fieldtracker.data.network.AuthApiService;
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
public final class AuthModule_ProvideAuthApiServiceFactory implements Factory<AuthApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public AuthModule_ProvideAuthApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public AuthApiService get() {
    return provideAuthApiService(retrofitProvider.get());
  }

  public static AuthModule_ProvideAuthApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new AuthModule_ProvideAuthApiServiceFactory(retrofitProvider);
  }

  public static AuthApiService provideAuthApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AuthModule.INSTANCE.provideAuthApiService(retrofit));
  }
}
