package org.odddev.fantlab.core.di;

import org.odddev.fantlab.auth.AuthProvider;
import org.odddev.fantlab.auth.IAuthProvider;
import org.odddev.fantlab.award.AwardsProvider;
import org.odddev.fantlab.award.IAwardsProvider;
import org.odddev.fantlab.home.HomeProvider;
import org.odddev.fantlab.home.IHomeProvider;

import dagger.Module;
import dagger.Provides;

/**
 * @author kenrube
 * @since 15.09.16
 */

@Module
public class ProvidersModule {

    @Provides
    IAuthProvider provideAuthProvider() {
        return new AuthProvider();
    }

    @Provides
    IAwardsProvider provideAwardsProvider() {
        return new AwardsProvider();
    }

    @Provides
    IHomeProvider provideHomeProvider() {
        return new HomeProvider();
    }
}
