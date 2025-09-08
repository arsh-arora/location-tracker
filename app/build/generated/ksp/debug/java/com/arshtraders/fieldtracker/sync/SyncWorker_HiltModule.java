package com.arshtraders.fieldtracker.sync;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = SyncWorker.class
)
public interface SyncWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.arshtraders.fieldtracker.sync.SyncWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(SyncWorker_AssistedFactory factory);
}
