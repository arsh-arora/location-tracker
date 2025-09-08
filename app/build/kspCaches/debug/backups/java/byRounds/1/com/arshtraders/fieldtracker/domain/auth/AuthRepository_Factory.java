package com.arshtraders.fieldtracker.domain.auth;

import android.content.Context;
import com.arshtraders.fieldtracker.data.database.dao.UserDao;
import com.arshtraders.fieldtracker.data.supabase.SupabaseAuthRepository;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<SupabaseAuthRepository> supabaseAuthRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<Context> contextProvider;

  public AuthRepository_Factory(Provider<SupabaseAuthRepository> supabaseAuthRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider, Provider<UserDao> userDaoProvider,
      Provider<Context> contextProvider) {
    this.supabaseAuthRepositoryProvider = supabaseAuthRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.userDaoProvider = userDaoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(supabaseAuthRepositoryProvider.get(), tokenManagerProvider.get(), userDaoProvider.get(), contextProvider.get());
  }

  public static AuthRepository_Factory create(
      Provider<SupabaseAuthRepository> supabaseAuthRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider, Provider<UserDao> userDaoProvider,
      Provider<Context> contextProvider) {
    return new AuthRepository_Factory(supabaseAuthRepositoryProvider, tokenManagerProvider, userDaoProvider, contextProvider);
  }

  public static AuthRepository newInstance(SupabaseAuthRepository supabaseAuthRepository,
      TokenManager tokenManager, UserDao userDao, Context context) {
    return new AuthRepository(supabaseAuthRepository, tokenManager, userDao, context);
  }
}
