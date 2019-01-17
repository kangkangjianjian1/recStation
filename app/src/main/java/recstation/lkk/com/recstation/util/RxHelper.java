package recstation.lkk.com.recstation.util;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import zuo.biao.library.base.BaseActivity;

public class RxHelper<T> {
    private String mMessage;

    public RxHelper(String message){
        mMessage = message;
    }

    //子线程运行，主线程回调
    public Observable.Transformer<T, T> io_main(final BaseActivity context) {
        return new Observable.Transformer<T, T>() {

            @Override
            public Observable<T> call(Observable<T> tObservable) {

                Observable<T> observable = (Observable<T>) tObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                DialogHelper.showProgressDlg(context, mMessage);
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());

                return observable;

            }
        };
    }

}
