package com.arshtraders.fieldtracker.presentation.punch;

import com.arshtraders.fieldtracker.data.database.dao.PunchDao;
import com.arshtraders.fieldtracker.domain.punch.PunchManager;
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
public final class PunchViewModel_Factory implements Factory<PunchViewModel> {
  private final Provider<PunchManager> punchManagerProvider;

  private final Provider<PunchDao> punchDaoProvider;

  public PunchViewModel_Factory(Provider<PunchManager> punchManagerProvider,
      Provider<PunchDao> punchDaoProvider) {
    this.punchManagerProvider = punchManagerProvider;
    this.punchDaoProvider = punchDaoProvider;
  }

  @Override
  public PunchViewModel get() {
    return newInstance(punchManagerProvider.get(), punchDaoProvider.get());
  }

  public static PunchViewModel_Factory create(Provider<PunchManager> punchManagerProvider,
      Provider<PunchDao> punchDaoProvider) {
    return new PunchViewModel_Factory(punchManagerProvider, punchDaoProvider);
  }

  public static PunchViewModel newInstance(PunchManager punchManager, PunchDao punchDao) {
    return new PunchViewModel(punchManager, punchDao);
  }
}
