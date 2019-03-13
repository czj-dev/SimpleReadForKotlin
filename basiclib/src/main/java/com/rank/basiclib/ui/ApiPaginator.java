package com.rank.basiclib.ui;

import android.util.Pair;

import com.rank.basiclib.rx.transformers.Transformers;
import com.rank.basiclib.utils.ListUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;


/**
 * An object to facilitate loading pages of data from the API.
 *
 * @param <Data>     The type of data returned from the array, e.g. `Project`, `Activity`, etc.
 * @param <Envelope> The type of envelope the API returns for a list of data, e.g. `DiscoverEnvelope`.
 * @param <Params>   The type of params that  can use to make a request. Many times this can just be `Void`.
 */
public final class ApiPaginator<Data, Envelope, Params> {
    private final @NonNull Observable<Object> nextPage;
    private final @NonNull Observable<Params> startOverWith;
    private final @NonNull Function<Envelope, List<Data>> envelopeToListOfData;
    private final @NonNull Function<Params, Observable<Envelope>> loadWithParams;
    private final @NonNull Function<String, Observable<Envelope>> loadWithPaginationPath;
    private final @NonNull Function<Envelope, String> envelopeToMoreUrl;
    private final @NonNull Function<Envelope, Params> envelopeToMoreParams;
    private final @NonNull Function<List<Data>, List<Data>> pageTransformation;
    private final boolean clearWhenStartingOver;
    private final @NonNull BiFunction<List<Data>, List<Data>, List<Data>> concater;
    private final boolean distinctUntilChanged;

    private final @NonNull PublishSubject<Pair<Params, String>> _morePath = PublishSubject.create();
    private final @NonNull PublishSubject<Boolean> _isFetching = PublishSubject.create();

    // Outputs
    public @NonNull
    Observable<List<Data>> paginatedData() {
        return this.paginatedData;
    }

    private final @NonNull Observable<List<Data>> paginatedData;

    public @NonNull
    Observable<Boolean> isFetching() {
        return this.isFetching;
    }

    private final @NonNull Observable<Boolean> isFetching = this._isFetching;

    public @NonNull
    Observable<Integer> loadingPage() {
        return this.loadingPage;
    }

    private final @NonNull Observable<Integer> loadingPage;

    private ApiPaginator(
            final @NonNull Observable<Object> nextPage,
            final @NonNull Observable<Params> startOverWith,
            final @NonNull Function<Envelope, List<Data>> envelopeToListOfData,
            final @NonNull Function<Params, Observable<Envelope>> loadWithParams,
            final @NonNull Function<String, Observable<Envelope>> loadWithPaginationPath,
            final @NonNull Function<Envelope, String> envelopeToMoreUrl,
            @NonNull Function<Envelope, Params> envelopeToMoreParams, final @NonNull Function<List<Data>, List<Data>> pageTransformation,
            final boolean clearWhenStartingOver,
            final @NonNull BiFunction<List<Data>, List<Data>, List<Data>> concater,
            final boolean distinctUntilChanged
    ) {
        this.nextPage = nextPage;
        this.startOverWith = startOverWith;
        this.envelopeToListOfData = envelopeToListOfData;
        this.loadWithParams = loadWithParams;
        this.envelopeToMoreUrl = envelopeToMoreUrl;
        this.envelopeToMoreParams = envelopeToMoreParams;
        this.pageTransformation = pageTransformation;
        this.loadWithPaginationPath = loadWithPaginationPath;
        this.clearWhenStartingOver = clearWhenStartingOver;
        this.concater = concater;
        this.distinctUntilChanged = distinctUntilChanged;

        this.paginatedData = this.startOverWith.switchMap(this::dataWithPagination);
        this.loadingPage = this.startOverWith.switchMap(__ -> nextPage.scan(1, (accum, ___) -> accum + 1));
    }

    public final static class Builder<Data, Envelope, Params> {
        private Observable<Object> nextPage;
        private Observable<Params> startOverWith;
        private Function<Envelope, List<Data>> envelopeToListOfData;
        private Function<Params, Observable<Envelope>> loadWithParams;
        private Function<String, Observable<Envelope>> loadWithPaginationPath;
        private Function<Envelope, String> envelopeToMoreUrl;
        private Function<Envelope, Params> envelopeToMoreParams;
        private Function<List<Data>, List<Data>> pageTransformation;
        private boolean clearWhenStartingOver;
        private BiFunction<List<Data>, List<Data>, List<Data>> concater = ListUtils::concat;
        private boolean distinctUntilChanged;

        /**
         * [Required] An observable that emits whenever a new page of data should be loaded.
         */
        public @NonNull
        Builder<Data, Envelope, Params> nextPage(final @NonNull Observable<Object> nextPage) {
            this.nextPage = nextPage;
            return this;
        }

        /**
         * [Optional] An observable that emits when a fresh first page should be loaded.
         */
        public @NonNull
        Builder<Data, Envelope, Params> startOverWith(final @NonNull Observable<Params> startOverWith) {
            this.startOverWith = startOverWith;
            return this;
        }

        /**
         * [Required] A function that takes an `Envelope` instance and returns the list of data embedded in it.
         */
        public @NonNull
        Builder<Data, Envelope, Params> envelopeToListOfData(final @NonNull Function<Envelope, List<Data>> envelopeToListOfData) {
            this.envelopeToListOfData = envelopeToListOfData;
            return this;
        }

        /**
         * [Required] A function to extract the more URL from an API response envelope.
         */
        public @NonNull
        Builder<Data, Envelope, Params> envelopeToMoreUrl(final @NonNull Function<Envelope, String> envelopeToMoreUrl) {
            this.envelopeToMoreUrl = envelopeToMoreUrl;
            return this;
        }

        /**
         * [Required] A function to extract the more URL from an API response envelope.
         */
        public @NonNull
        Builder<Data, Envelope, Params> envelopeToMoreParams(final @NonNull Function<Envelope, Params> envelopeToMoreParams) {
            this.envelopeToMoreParams = envelopeToMoreParams;
            return this;
        }

        /**
         * [Required] A function that makes an API request with a pagination URL.
         */
        public @NonNull
        Builder<Data, Envelope, Params> loadWithPaginationPath(final @NonNull Function<String, Observable<Envelope>> loadWithPaginationPath) {
            this.loadWithPaginationPath = loadWithPaginationPath;
            return this;
        }

        /**
         * [Required] A function that takes a `Params` and performs the associated network request
         * and returns an `Observable<Envelope>`
         */
        public @NonNull
        Builder<Data, Envelope, Params> loadWithParams(final @NonNull Function<Params, Observable<Envelope>> loadWithParams) {
            this.loadWithParams = loadWithParams;
            return this;
        }

        /**
         * [Optional] Function to transform every page of data that is loaded.
         */
        public @NonNull
        Builder<Data, Envelope, Params> pageTransformation(final @NonNull Function<List<Data>, List<Data>> pageTransformation) {
            this.pageTransformation = pageTransformation;
            return this;
        }

        /**
         * [Optional] Determines if the list of loaded data is cleared when starting over from the first page.
         */
        public @NonNull
        Builder<Data, Envelope, Params> clearWhenStartingOver(final boolean clearWhenStartingOver) {
            this.clearWhenStartingOver = clearWhenStartingOver;
            return this;
        }

        /**
         * [Optional] Determines how two lists are concatenated together while paginating. A regular `ListUtils::concat` is probably
         * sufficient, but sometimes you may want `ListUtils::concatDistinct`
         */
        public @NonNull
        Builder<Data, Envelope, Params> concater(final @NonNull BiFunction<List<Data>, List<Data>, List<Data>> concater) {
            this.concater = concater;
            return this;
        }

        /**
         * [Optional] Determines if the list of loaded data is should be distinct until changed.
         */
        public @NonNull
        Builder<Data, Envelope, Params> distinctUntilChanged(final boolean distinctUntilChanged) {
            this.distinctUntilChanged = distinctUntilChanged;
            return this;
        }

        public @NonNull
        ApiPaginator<Data, Envelope, Params> build() throws RuntimeException {
            // Early error when required field is not set
            if (this.nextPage == null) {
                throw new RuntimeException("`nextPage` is required");
            }
            if (this.envelopeToListOfData == null) {
                throw new RuntimeException("`envelopeToListOfData` is required");
            }
            if (this.loadWithParams == null) {
                throw new RuntimeException("`loadWithParams` is required");
            }
            if (envelopeToMoreParams == null && this.loadWithPaginationPath == null) {
                throw new RuntimeException("`envelopeToMoreParams` or `loadWithPaginationPath` is required");
            }
            if (envelopeToMoreParams == null) {
                envelopeToMoreParams = envelope -> null;
            }
            //在 envelopeToMoreParams 不为空的情况下可以不使用 loadWithPaginationPath
            if (this.loadWithPaginationPath == null) {
                loadWithPaginationPath = s -> Observable.empty();
            }
            if (this.envelopeToMoreUrl == null) {
                envelopeToMoreUrl = envelope -> "";
            }

            // Default params for optional fields
            if (this.startOverWith == null) {
                this.startOverWith = (Observable<Params>) Observable.just(new Object());
            }
            if (this.pageTransformation == null) {
                this.pageTransformation = x -> x;
            }
            if (this.concater == null) {
                this.concater = ListUtils::concat;
            }

            return new ApiPaginator<>(this.nextPage, this.startOverWith, this.envelopeToListOfData, this.loadWithParams,
                    this.loadWithPaginationPath, this.envelopeToMoreUrl, this.envelopeToMoreParams, this.pageTransformation, this.clearWhenStartingOver, this.concater,
                    this.distinctUntilChanged);
        }
    }

    public @NonNull
    static <Data, Envelope, FirstPageParams> Builder<Data, Envelope, FirstPageParams> builder() {
        return new Builder<>();
    }

    /**
     * Returns an observable that emits the accumulated list of paginated data each time a new page is loaded.
     */
    private @NonNull
    Observable<List<Data>> dataWithPagination(final @NonNull Params firstPageParams) {
        final Observable<List<Data>> data = paramsAndMoreUrlWithPagination(firstPageParams)
                .concatMap(this::fetchData)
                .takeUntil((Predicate<List<Data>>) List::isEmpty);

        final Observable<List<Data>> paginatedData = this.clearWhenStartingOver
                ? data.scan(new ArrayList<>(), this.concater)
                : data.scan(this.concater);

        return this.distinctUntilChanged ? paginatedData.distinctUntilChanged() : paginatedData;
    }

    /**
     * Returns an observable that emits the params for the next page of data *or* the more URL for the next page.
     */
    private @NonNull
    Observable<Pair<Params, String>> paramsAndMoreUrlWithPagination(final @NonNull Params firstPageParams) {

        return this._morePath
                .compose(Transformers.takeWhen(this.nextPage))
                .startWith(new Pair<>(firstPageParams, null));
    }

    private @NonNull
    Observable<List<Data>> fetchData(final @NonNull Pair<Params, String> paginatingData) {
        try {
            return (paginatingData.second != null
                    ? this.loadWithPaginationPath.apply(paginatingData.second)
                    : this.loadWithParams.apply(paginatingData.first))
                    .retry(2)
                    .compose(Transformers.neverError())
                    .doOnNext(this::keepMorePath)
                    .map(this.envelopeToListOfData)
                    .map(this.pageTransformation)
                    .doOnSubscribe(disposable -> this._isFetching.onNext(true))
                    .doAfterTerminate(() -> this._isFetching.onNext(false));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void keepMorePath(final @NonNull Envelope envelope) {
        String path = null;
        try {
            URL url = new URL(this.envelopeToMoreUrl.apply(envelope));
            path = pathAndQueryFromURL(url);
        } catch (MalformedURLException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        Params apply = null;
        try {
            apply = this.envelopeToMoreParams.apply(envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this._morePath.onNext(Pair.create(apply, path));
    }

    private @NonNull
    String pathAndQueryFromURL(final @NonNull URL url) {
        return url.getPath() + "?" + url.getQuery();
    }
}
