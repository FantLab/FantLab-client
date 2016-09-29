package org.odddev.fantlab.auth.login;

import org.odddev.fantlab.core.di.Injector;
import org.odddev.fantlab.core.layers.presenter.Presenter;
import org.odddev.fantlab.auth.IAuthProvider;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * @author kenrube
 * @date 30.08.16
 */

public class LoginPresenter extends Presenter<ILoginView> {

    private Subscription mLoginSubscription;

    @Inject
    CompositeSubscription mCompositeSubscription;

    @Inject
    IAuthProvider mAuthProvider;

    LoginPresenter() {
        Injector.getAppComponent().inject(this);
    }

    public void login(String username, String password) {
        mLoginSubscription = mAuthProvider
                .login(username, password)
                .subscribe(
                        this::showResult,
                        throwable -> showError(throwable.getLocalizedMessage()),
                        () -> mCompositeSubscription.remove(mLoginSubscription));
        mCompositeSubscription.add(mLoginSubscription);
    }

    private void showResult(boolean hasCookie) {
        for (ILoginView view : getViews()) {
            view.showLoginResult(hasCookie);
        }
    }

    private void showError(String error) {
        for (ILoginView view : getViews()) {
            view.showError(error);
        }
    }

    @Override
    protected void onDestroy() {
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }
}