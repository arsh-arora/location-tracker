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
public final class SupabaseAuthRepository_Factory implements Factory<SupabaseAuthRepository> {
  private final Provider<SupabaseClient> supabaseClientProvider;

  public SupabaseAuthRepository_Factory(Provider<SupabaseClient> supabaseClientProvider) {
    this.supabaseClientProvider = supabaseClientProvider;
  }

  @Override
  public SupabaseAuthRepository get() {
    return newInstance(supabaseClientProvider.get());
  }

  public static SupabaseAuthRepository_Factory create(
      Provider<SupabaseClient> supabaseClientProvider) {
    return new SupabaseAuthRepository_Factory(supabaseClientProvider);
  }

  public static SupabaseAuthRepository newInstance(SupabaseClient supabaseClient) {
    return new SupabaseAuthRepository(supabaseClient);
  }
}
