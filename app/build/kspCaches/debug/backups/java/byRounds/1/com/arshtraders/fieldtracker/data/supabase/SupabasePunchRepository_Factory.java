package com.arshtraders.fieldtracker.data.supabase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.SupabaseClient;
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
public final class SupabasePunchRepository_Factory implements Factory<SupabasePunchRepository> {
  private final Provider<SupabaseClient> supabaseClientProvider;

  private final Provider<SupabaseAuthRepository> authRepositoryProvider;

  public SupabasePunchRepository_Factory(Provider<SupabaseClient> supabaseClientProvider,
      Provider<SupabaseAuthRepository> authRepositoryProvider) {
    this.supabaseClientProvider = supabaseClientProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public SupabasePunchRepository get() {
    return newInstance(supabaseClientProvider.get(), authRepositoryProvider.get());
  }

  public static SupabasePunchRepository_Factory create(
      Provider<SupabaseClient> supabaseClientProvider,
      Provider<SupabaseAuthRepository> authRepositoryProvider) {
    return new SupabasePunchRepository_Factory(supabaseClientProvider, authRepositoryProvider);
  }

  public static SupabasePunchRepository newInstance(SupabaseClient supabaseClient,
      SupabaseAuthRepository authRepository) {
    return new SupabasePunchRepository(supabaseClient, authRepository);
  }
}
