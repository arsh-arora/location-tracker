package com.arshtraders.fieldtracker;

import com.arshtraders.fieldtracker.sync.SyncManager;
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
public final class FieldTrackerApplication_MembersInjector implements MembersInjector<FieldTrackerApplication> {
  private final Provider<SyncManager> syncManagerProvider;

  public FieldTrackerApplication_MembersInjector(Provider<SyncManager> syncManagerProvider) {
    this.syncManagerProvider = syncManagerProvider;
  }

  public static MembersInjector<FieldTrackerApplication> create(
      Provider<SyncManager> syncManagerProvider) {
    return new FieldTrackerApplication_MembersInjector(syncManagerProvider);
  }

  @Override
  public void injectMembers(FieldTrackerApplication instance) {
    injectSyncManager(instance, syncManagerProvider.get());
  }

  @InjectedFieldSignature("com.arshtraders.fieldtracker.FieldTrackerApplication.syncManager")
  public static void injectSyncManager(FieldTrackerApplication instance, SyncManager syncManager) {
    instance.syncManager = syncManager;
  }
}
