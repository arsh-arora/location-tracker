package com.arshtraders.fieldtracker.sync;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.arshtraders.fieldtracker.data.database.dao.LocationDao;
import com.arshtraders.fieldtracker.data.database.dao.PunchDao;
import com.arshtraders.fieldtracker.data.network.ApiService;
import dagger.internal.DaggerGenerated;
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
public final class SyncWorker_Factory {
  private final Provider<LocationDao> locationDaoProvider;

  private final Provider<PunchDao> punchDaoProvider;

  private final Provider<ApiService> apiServiceProvider;

  public SyncWorker_Factory(Provider<LocationDao> locationDaoProvider,
      Provider<PunchDao> punchDaoProvider, Provider<ApiService> apiServiceProvider) {
    this.locationDaoProvider = locationDaoProvider;
    this.punchDaoProvider = punchDaoProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  public SyncWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, locationDaoProvider.get(), punchDaoProvider.get(), apiServiceProvider.get());
  }

  public static SyncWorker_Factory create(Provider<LocationDao> locationDaoProvider,
      Provider<PunchDao> punchDaoProvider, Provider<ApiService> apiServiceProvider) {
    return new SyncWorker_Factory(locationDaoProvider, punchDaoProvider, apiServiceProvider);
  }

  public static SyncWorker newInstance(Context context, WorkerParameters params,
      LocationDao locationDao, PunchDao punchDao, ApiService apiService) {
    return new SyncWorker(context, params, locationDao, punchDao, apiService);
  }
}
